package com.helome.messagecenter.message.business;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

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
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.Utils;

public class TransferMessage implements Message {
	private final static Logger logger = LoggerFactory.getLogger(TransferMessage.class);

	public static final short CODE = 110;

	private Long senderId;

	private Long receiverId;

	private Long date;

	private Endpoint endpoint;

	private int length;

	public String senderName;

	public String receiverName;

	private double amount;

	private byte currency;

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@Override
	public short getCode() {
		return TransferMessage.CODE;
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


	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public byte getCurrency() {
		return currency;
	}

	public void setCurrency(byte currency) {
		this.currency = currency;
	}

	@Override
	public void onReceived() {
		List<Endpoint> toEndpoints = Context.getEndpoints(receiverId);
		if (toEndpoints != null) {
			synchronized(toEndpoints){
				for(Endpoint toEndpoint : toEndpoints){
					if (toEndpoint instanceof SocketEndpoint) {
						toEndpoint.getContext().channel().writeAndFlush(toBinary());
					} 
					else {
						toEndpoint.getContext().channel()
								.writeAndFlush(new TextWebSocketFrame(toJson()));
					}
				}
			}
		}
	}

	@Override
	public ByteBuf toBinary() {
		this.length = 2 + 8 + 8 + 6 + 8 + 1 + 2 
				+ Utils.getUTF8StringLength(senderName)
				+ Utils.getUTF8StringLength(receiverName);
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		byte[] dateByte = Utils.unsigned48ToBytes(date);
		byte[] senderNameBytes = senderName.getBytes(CharsetUtil.UTF_8);
		byte[] consumeNameByte = receiverName.getBytes(CharsetUtil.UTF_8);
		buffer.writeLong(senderId);
		buffer.writeByte(senderNameBytes.length);
		buffer.writeBytes(senderNameBytes);
		buffer.writeLong(receiverId);
		buffer.writeByte(consumeNameByte.length);
		buffer.writeBytes(consumeNameByte);
		buffer.writeBytes(dateByte);
		buffer.writeDouble(amount);
		buffer.writeByte(currency);

		return buffer;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("senderId", senderId);
		map.put("receiverId", receiverId);
		map.put("date", date);
		map.put("senderName", senderName);
		map.put("receiverName", receiverName);
		map.put("amount", amount);
		map.put("currency", currency);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("TransferMessage 消息异常："+e);
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
		date = Utils.from48Unsigned(bytes);
	//	date = new Date().getTime();
		amount = buffer.readDouble();
		currency = buffer.readByte();
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		senderId = map.get("senderId").asLong();
		receiverId = map.get("receiverId").asLong();
		date = map.get("date").asLong();
		senderName = map.get("senderName").asText();
		receiverName = map.get("receiverName").asText();
		amount = map.get("amount").asDouble();
		currency = (byte) map.get("currency").asInt();
		this.length = 2 + 8 + 8 + 6 + 8 + 1 + 2 
				+ Utils.getUTF8StringLength(senderName)
				+ Utils.getUTF8StringLength(receiverName);
	}


	
	
	
}
