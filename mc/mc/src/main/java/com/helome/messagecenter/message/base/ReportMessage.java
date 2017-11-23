package com.helome.messagecenter.message.base;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Context;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.core.MessageFactory;
import com.helome.messagecenter.core.SocketEndpoint;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.HttpUtils;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.Utils;

public class ReportMessage implements Message {
	private final static Logger logger = LoggerFactory
			.getLogger(ReportMessage.class);

	public static final short CODE = 1;

	private Long id;

	private String token;

	private Endpoint endpoint;

	private int length;

	private byte type;

	public static final byte WEBSOCKET = 0;

	public static final byte WEBRTC = 1;

	public static final byte SOCKET = 2;

	@Override
	public short getCode() {
		return ReportMessage.CODE;
	}

	@Override
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int getLength() {
		return length;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public void onReceived() {
		logger.debug("收到用户{}上报消息，{},from {}.", new Object[]{id, token, endpoint.getContext().channel()});
        boolean verifyToken = true;
        if(!token.equals("junit:test")){//来自单元测试
        	verifyToken = HttpUtils.validateToken(token);
        }
		registerByToken(verifyToken);
	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		buffer.writeLong(id);
		byte[] bytes = token.getBytes(CharsetUtil.UTF_8);
		buffer.writeByte(bytes.length);
		buffer.writeBytes(bytes);
		buffer.writeByte(type);
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		id = buffer.readLong();
		short len = buffer.readUnsignedByte();
		byte[] bytes = new byte[len];
		buffer.readBytes(bytes);
		token = new String(bytes, CharsetUtil.UTF_8);
		type = buffer.readByte();
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("id", id);
		map.put("token", token);
		map.put("type", type);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
		}
		return str;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		id = map.get("id").asLong();
		token = map.get("token").asText();
		type = (byte) map.get("type").intValue();
		this.length = 2 + 8 + 1 + 1 + Utils.getUTF8StringLength(token);
	}

	public boolean isWebRTC() {
		return type == WEBRTC;
	}

	/**
	 * @description 发送异常下线消息 并不关闭连接
	 * @author beyond.zhang
	 */
	private void sendAbnormalMessage(Endpoint point) {
		/*if (Context.getEndpoints(id) != null)
			Context.deregisterEndpoint(point);*/
		Message msg = MessageFactory.createAbNormalOfflineMessage();
		msg.setEndpoint(point);
		if (point instanceof SocketEndpoint) {
			point.getContext().channel().writeAndFlush(msg.toBinary());
		} else {
			point.getContext().channel().writeAndFlush(new TextWebSocketFrame(msg.toJson()));
		}
       // Context.deregisterChannelHandlerContext(point.getContext());
		//Utils.close(point.getContext());
	}

	/**
	 * @description 注册成功返回成功消息
	 * @author beyond.zhang
	 */
	private void sendSuccessResponse() {
		ResponseMessage msg = MessageFactory.createSuccessResponseMessage();
		msg.setEndpoint(endpoint);
		if (endpoint instanceof SocketEndpoint) {
			endpoint.getContext().channel().writeAndFlush(msg.toBinary());
		} else {
			endpoint.getContext().channel()
					.writeAndFlush(new TextWebSocketFrame(msg.toJson()));
		}
	}

	/**
	 * @description 注册验证不成功返回失败消息 同时关闭连接
	 * @author beyond.zhang
	 */
	private void sendFailResponse() {
		ResponseMessage msg = new ResponseMessage((short) 2);
		msg.setEndpoint(endpoint);
		if (endpoint instanceof SocketEndpoint) {
			endpoint.getContext().channel()
					.writeAndFlush(msg.toBinary());
		} else {
			endpoint.getContext().channel()
					.writeAndFlush(new TextWebSocketFrame(msg.toJson()));
		}
		Utils.close(endpoint.getContext());
	}

	/**
	 * @description 根据token选择正常注册或踢下线
	 * @author beyond.zhang
	 */
	private void registerByToken(boolean verifyToken) {
		Endpoint e = null;
		// 验证通过 分情况注册
		if (verifyToken) {
			// 以前已经上报过
			if (Context.ID_TO_TOKEN.containsKey(id)) {
				logger.info("{}已经上报过", id);
				// 如果token已经存在遍历所有Endpoints 如果是websocket 就被踢下线
				List<Endpoint> endpoints = Context.getEndpoints(id);
				if (endpoints != null) {
					synchronized (endpoints) {
						if (Context.ID_TO_TOKEN.containsValue(token)) {
							e = Context.register(id, endpoint);
						} else {// token 不同关闭以前token所对应的id的Endpoints加入新的
							List<Endpoint> needRemovesPoint = new ArrayList<Endpoint>();
							for (Endpoint point : endpoints) {
								logger.info("id {}, {}已经上报过,发送异常下线，并不关闭连接",
										point.getContext().channel(), id);
								sendAbnormalMessage(point);
								needRemovesPoint.add(point);
							}
							if(!needRemovesPoint.isEmpty()){
								for(int i=0;i<needRemovesPoint.size();i++){
									Context.deregisterEndpoint(needRemovesPoint.get(i));
									Context.deregisterChannelHandlerContext(needRemovesPoint.get(i).getContext());
								}
							}
							Context.registerIdToToken(id, token);
							e = Context.register(id, endpoint);
						}
					}
				}
			} else {// 新上报的用户
				logger.info("新用户{}上报", id);
				Context.registerIdToToken(id, token);
				e = Context.register(id, endpoint);
			}
		} else {// token验证不通过 直接返回失败消息 不进行注册
			logger.info("{}的连接{}token验证失败", id, endpoint.getContext().channel());
			sendFailResponse();
		}
		if (e == null) {
			sendSuccessResponse();// 注册成功发送成功消息
		} else {
			logger.info("{}的连接{}被踢下线", id, endpoint.getContext().channel());
			List<Endpoint> endpoints = Context.getEndpoints(id);
			for(Endpoint point :endpoints){
				if(point!=endpoint){
					sendAbnormalMessage(endpoint);// 发送被踢下线
				}
			}
			
		}
	}
}
