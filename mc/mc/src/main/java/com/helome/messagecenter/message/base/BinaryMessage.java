package com.helome.messagecenter.message.base;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.io.IOException;
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

public class BinaryMessage implements Message {
	
	private final static Logger logger = LoggerFactory.getLogger(BinaryMessage.class);
	
	public static final short CODE = 4;

	private Long senderId;

	private Long receiverId;

	private byte[] data;

	private Endpoint endpoint;

	private int length;

	private String senderName;

	private String receiverName;

	private String fileName;

	private byte finish;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	@Override
	public short getCode() {
		return BinaryMessage.CODE;
	}

	@Override
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public void setEndpoint(Endpoint Endpoint) {
		this.endpoint = Endpoint;
	}

	public byte getFinish() {
		return finish;
	}

	public void setFinish(byte finish) {
		this.finish = finish;
	}

	@Override
	public void onReceived() {
		List<Endpoint> fromEndpoints = Context.getEndpoints(senderId);
		List<Endpoint> toEndpoints = Context.getEndpoints(receiverId);
		logger.debug("收到文件流消息from："+senderId+";to:"+receiverId);
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
		ByteBuf buffer = MessageFactory.createByteBuf(this, endpoint.getContext());
		byte[] fileNameByte = fileName.getBytes(CharsetUtil.UTF_8);
		buffer.writeLong(senderId);
		byte[] senderNameBytes = senderName.getBytes(CharsetUtil.UTF_8);
		byte[] consumeNameByte = receiverName.getBytes(CharsetUtil.UTF_8);
		buffer.writeByte(senderNameBytes.length);
		buffer.writeBytes(senderNameBytes);
		buffer.writeLong(receiverId);
		buffer.writeByte(consumeNameByte.length);
		buffer.writeBytes(consumeNameByte);
		buffer.writeByte(fileNameByte.length);
		buffer.writeBytes(fileNameByte);
		buffer.writeByte(finish);
		buffer.writeBytes(Utils.unsigned16ToBytes(data.length));
		buffer.writeBytes(data);
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
		map.put("fileName", fileName);
		map.put("finish", finish);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
		}
		return str;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		senderId = buffer.readLong();
		short senderNameLen = buffer.readUnsignedByte();
		byte[] senderNameBytes = new byte[senderNameLen];
		buffer.readBytes(senderNameBytes);
		senderName = new String(senderNameBytes,CharsetUtil.UTF_8);
		receiverId = buffer.readLong();
		short consumeNameLen = buffer.readUnsignedByte();
		byte[] consumeNameBytes = new byte[consumeNameLen];
		buffer.readBytes(consumeNameBytes);
		receiverName = new String(consumeNameBytes,CharsetUtil.UTF_8);
		short fileNameLen = buffer.readUnsignedByte();
		byte[] fileNameBytes = new byte[fileNameLen];
		buffer.readBytes(fileNameBytes);
		fileName = new String(fileNameBytes,CharsetUtil.UTF_8);
		finish = buffer.readByte();
		
		byte[] dataLenBytes = new byte[2];
		buffer.readBytes(dataLenBytes);
		int dataLen = Utils.from16Unsigned(dataLenBytes);
		byte[] dataBytes = new byte[dataLen];
		buffer.readBytes(dataBytes);
		data = dataBytes;
		this.length = 2+8+1+8+1+1+1+2+data.length+Utils.getUTF8StringLength(senderName)+Utils.getUTF8StringLength(receiverName)+Utils.getUTF8StringLength(fileName);
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		senderId = map.get("senderId").asLong();
		receiverId = map.get("receiverId").asLong();
		try {
			data = map.get("data").binaryValue();
		} catch (IOException e) {
			e.printStackTrace();
		}
		senderName = map.get("senderName").asText();
		receiverName = map.get("receiverName").asText();
		fileName = map.get("fileName").asText();
		finish = (byte) map.get("finish").asInt();
		this.length = 2 + 8 + 1 + 8 + 1 + 1 + 1 + 2 + data.length
				+ Utils.getUTF8StringLength(senderName)
				+ Utils.getUTF8StringLength(receiverName)
				+ Utils.getUTF8StringLength(fileName);
	}

}
