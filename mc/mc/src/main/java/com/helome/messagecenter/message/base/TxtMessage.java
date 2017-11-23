package com.helome.messagecenter.message.base;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.net.URISyntaxException;
import java.util.Date;
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
import com.helome.messagecenter.core.WebRTCEndpoint;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.DateUtil;
import com.helome.messagecenter.utils.HttpUtils;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.MemCachedUtil;
import com.helome.messagecenter.utils.Utils;

/**
 * @description 文本消息
 * @author beyond.zhang
 */
public class TxtMessage implements Message {
	private final static Logger logger = LoggerFactory
			.getLogger(TxtMessage.class);

	public static final short CODE = 3;

	private Long senderId;

	private Long receiverId;

	private String data;

	private Endpoint endpoint;

	private int length;

	public String senderName;

	public String receiverName;

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@Override
	public short getCode() {
		return TxtMessage.CODE;
	}

	@Override
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int getLength() {
		return length;
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

	@Override
	public void setEndpoint(Endpoint Endpoint) {
		this.endpoint = Endpoint;
	}

	@Override
	public void onReceived() {
		logger.info("收到文本聊天消息from {} to {}", senderId, receiverId);
		List<Endpoint> fromEndpoints = Context.getEndpoints(senderId);
		List<Endpoint> toEndpoints = Context.getEndpoints(receiverId);
		if (senderId == 0) {// 0代表系统消息 不用存聊天记录里
			// 发送消息到聊天记录
			if (toEndpoints == null) {
				MemCachedUtil.putSysMessageNum(receiverId);
			} else {
				synchronized (toEndpoints) {
					for (Endpoint toEndpoint : toEndpoints) {
						if (toEndpoint instanceof SocketEndpoint) {
							toEndpoint.getContext().channel()
									.writeAndFlush(toBinary());
						} else {
							toEndpoint
									.getContext()
									.channel()
									.writeAndFlush(
											new TextWebSocketFrame(toJson()));
						}
					}
				}
			}
		} else {
			long begin=System.currentTimeMillis();
			//sendChats();
			long end=System.currentTimeMillis();
			long time = end -begin;
			logger.info("保存聊天消息{} from {} to {} 耗时：{}  毫秒", data,senderId,receiverId,time);
			boolean isInChat = false;
			/*if (fromEndpoints == null && senderId != 0) {
				Utils.close(endpoint.getContext());
			} else {*/
			logger.info("收到聊天消息{} from {} to {} ", data,senderId,receiverId);

				if (toEndpoints != null) {
					synchronized (toEndpoints) {
						for (Endpoint toEndpoint : toEndpoints) {
							if (toEndpoint instanceof WebRTCEndpoint) {
								Long id = Context.ENDPOINTS_TO_PEER
										.get(toEndpoint);
								if (senderId.equals(id)) {
									isInChat = true;
								}
							}
						}

						if (!isInChat) {
							MemCachedUtil.putCommunicateNum(senderId,
									receiverId);
							MemCachedUtil.putCommunicateTotal(receiverId);
							// MemCachedUtil.putCommunicateMessage(receiverId,
							// this);
						}
						for (Endpoint toEndpoint : toEndpoints) {
							if (toEndpoint instanceof SocketEndpoint) {
								ChannelFuture future =	toEndpoint.getContext().channel().writeAndFlush(toBinary());
								logger.info("发送聊天消息{} from {} to {} 结果{}", data,senderId,receiverId,future);

							} else {
								ChannelFuture future =	toEndpoint.getContext().channel().writeAndFlush(new TextWebSocketFrame(toJson()));
								logger.info("发送收到聊天消息{} from {} to {} 结果{}", data,senderId,receiverId,future);

							}
						}
					}
				} else {
					// MemCachedUtil.putCommunicateMessage(receiverId, this);
					MemCachedUtil.putCommunicateNum(senderId, receiverId);
					MemCachedUtil.putCommunicateTotal(receiverId);
					logger.info("发送收到聊天消息{} from {} to {} 没有找到接受者 {} 注册信息没有发出", data,senderId,receiverId,receiverId);

				}
			}
//		}
	}

	private String sendChats() {
		String response = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> subMap = new HashMap<String, Object>();
			subMap.put("subType", "text");
			subMap.put("data", data);
			map.put("product", "helome");
			map.put("type", 1);
			map.put("from", senderId + "");
			map.put("to", receiverId + "");
			map.put("contentType", 1); 
			map.put("sendTime", DateUtil.getDate());
			map.put("content", JsonUtils.stringify(subMap));
			String jsonStr = JsonUtils.stringify(map);
			response = HttpUtils.sendChatRequest(jsonStr);
			logger.debug("保存{},结果{}", jsonStr, response);
		} catch (JsonProcessingException e) {
			logger.error("文本消息异常：" + e);
		} catch (URISyntaxException e) {
			logger.error("文本消息异常：" + e);
		}
		return response;

	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		buffer.writeLong(senderId);
		byte[] senderNameBytes = senderName.getBytes(CharsetUtil.UTF_8);
		byte[] consumeNameByte = receiverName.getBytes(CharsetUtil.UTF_8);
		buffer.writeByte(senderNameBytes.length);
		buffer.writeBytes(senderNameBytes);
		buffer.writeLong(receiverId);
		buffer.writeByte(consumeNameByte.length);
		buffer.writeBytes(consumeNameByte);
		byte[] bytes = data.getBytes(CharsetUtil.UTF_8);
		buffer.writeShort(bytes.length);
		buffer.writeBytes(bytes);
		return buffer;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("senderId", senderId);
		map.put("receiverId", receiverId);
		map.put("data", data);
		map.put("senderName", senderName);
		map.put("receiverName", receiverName);
		map.put("sendTime", new Date().getTime());
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("文本消息异常：" + e);
		}
		return str;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		senderId = buffer.readLong();
		short senderNameLen = buffer.readUnsignedByte();
		byte[] senderNameBytes = new byte[senderNameLen];
		buffer.readBytes(senderNameBytes);
		senderName = new String(senderNameBytes, CharsetUtil.UTF_8);
		receiverId = buffer.readLong();
		short consumeNameLen = buffer.readUnsignedByte();
		byte[] consumeNameBytes = new byte[consumeNameLen];
		buffer.readBytes(consumeNameBytes);
		receiverName = new String(consumeNameBytes, CharsetUtil.UTF_8);
		int len = buffer.readUnsignedShort();
		byte[] bytes = new byte[len];
		buffer.readBytes(bytes);
		data = new String(bytes, CharsetUtil.UTF_8);
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		senderId = map.get("senderId").asLong();
		receiverId = map.get("receiverId").asLong();
		data = map.get("data").asText();
		senderName = map.get("senderName").asText();
		receiverName = map.get("receiverName").asText();
		length = 22 + Utils.getUTF8StringLength(data)
				+ Utils.getUTF8StringLength(senderName)
				+ +Utils.getUTF8StringLength(receiverName);
	}

}
