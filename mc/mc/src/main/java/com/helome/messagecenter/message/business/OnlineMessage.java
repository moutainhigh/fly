package com.helome.messagecenter.message.business;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.helome.messagecenter.core.Context;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.core.MessageFactory;
import com.helome.messagecenter.core.SocketEndpoint;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.message.base.ResponseMessage;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.Utils;
/**
 * 
 * @description 用户是否在线消息
 * @author beyond.zhang   
 * @update 2014-3-25 下午3:06:17
 */
public class OnlineMessage implements Message {

	public static final short CODE = 160;
	private final static Logger logger = LoggerFactory.getLogger(OnlineMessage.class);

	private Long senderId;

	private Long receiverId;
	
	private int length;
	
	private Endpoint endpoint;

	public OnlineMessage() {
		super();
	}

	public OnlineMessage(Long senderId, Long receiverId) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.length = 2 + 8+8 ;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	public  short getCode() {
		return CODE;
	}
	
	@Override
	public void onReceived() {
		List<Endpoint> fromEndpoints = Context.getEndpoints(senderId);
		List<Endpoint> toEndpoints = Context.getEndpoints(receiverId);
		if (fromEndpoints == null ) {
			sendResponseMessage(toEndpoints,false);
			Utils.close(endpoint.getContext());
		} else {
			sendResponseMessage(toEndpoints,true);
		}
	}

	private void sendResponseMessage(List<Endpoint> toEndpoints, boolean b) {
		if (toEndpoints != null) {
			ResponseMessage msg = null;
			if(b){
				msg = MessageFactory.createSuccessResponseMessage();
			}else{
				msg = MessageFactory.createResponseMessage(Short.parseShort("-1"));
			}
			if (toEndpoints != null) {
				synchronized(toEndpoints){
				for(Endpoint toEndpoint : toEndpoints){
					if (toEndpoint instanceof SocketEndpoint) {
						toEndpoint.getContext().channel().writeAndFlush(toBinary());
					} else {
						toEndpoint.getContext().channel()
								.writeAndFlush(new TextWebSocketFrame(toJson()));
						}
					}
				}
			}
		}
		
	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		buffer.writeLong(senderId);
		buffer.writeLong(receiverId);
		return buffer;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("senderId", senderId);
		map.put("receiverId", receiverId);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("用户是否在线消息异常："+e);
		}
		return str;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		senderId = buffer.readLong();
		receiverId = buffer.readLong();
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		senderId = map.get("senderId").asLong();
		receiverId = map.get("receiverId").asLong();
		length =2+8+8;
	}

	
	

}
