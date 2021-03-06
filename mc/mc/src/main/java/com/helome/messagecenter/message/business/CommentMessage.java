package com.helome.messagecenter.message.business;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.util.Date;
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
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.Utils;

/**
 * 
 * @author anyone
 * @Description: 评论消息
 */
public class CommentMessage implements Message {
	private final static Logger logger = LoggerFactory.getLogger(CommentMessage.class);

	public static final short CODE = 100;

	private Long senderId;

	private Long receiverId;

	private Endpoint endpoint;

	private int length;

	public String senderName;

	public String receiverName;

	public String content;

	public Long date;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@Override
	public short getCode() {
		return CommentMessage.CODE;
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
		logger.info("收到评论消息 from {} to {} content: {}",senderId,receiverId,content);
		List<Endpoint> fromEndpoints = Context.getEndpoints(senderId);
		List<Endpoint> toEndpoints = Context.getEndpoints(receiverId);
		if (fromEndpoints == null ) {
			Utils.close(endpoint.getContext());
		} else {
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
		byte[] dateByte = Utils.unsigned48ToBytes(date);
		byte[] senderNameBytes = senderName.getBytes(CharsetUtil.UTF_8);
		byte[] consumeNameByte = receiverName.getBytes(CharsetUtil.UTF_8);
		byte[] contentByte = content.getBytes(CharsetUtil.UTF_8);
		buffer.writeByte(senderNameBytes.length);
		buffer.writeBytes(senderNameBytes);
		buffer.writeLong(receiverId);
		buffer.writeByte(consumeNameByte.length);
		buffer.writeBytes(consumeNameByte);
		buffer.writeBytes(dateByte);
		buffer.writeShort(contentByte.length);
		buffer.writeBytes(contentByte);

		return buffer;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("senderId", senderId);
		map.put("receiverId", receiverId);
		map.put("senderName", senderName);
		map.put("receiverName", receiverName);
		map.put("content", content);
		map.put("date", date);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("评论消息异常："+e);
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
		byte[] bytes = new byte[6];
		buffer.readBytes(bytes);
		//date = Utils.from48Unsigned(bytes);
		date = new Date().getTime();
		int contentLen = buffer.readUnsignedShort();
		byte[] contentBytes = new byte[contentLen];
		buffer.readBytes(contentBytes);
		content = new String(contentBytes, CharsetUtil.UTF_8);
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		senderId = map.get("senderId").asLong();
		receiverId = map.get("receiverId").asLong();
		senderName = map.get("senderName").asText();
		receiverName = map.get("receiverName").asText();
		content = map.get("content").asText();
		date = map.get("date").asLong();
		this.length = 2+8+1+Utils.getUTF8StringLength(senderName)+8+1
				+Utils.getUTF8StringLength(receiverName)+6+2+Utils.getUTF8StringLength(content);
	}


	
}
