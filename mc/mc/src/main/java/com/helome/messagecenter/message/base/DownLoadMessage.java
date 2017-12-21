package com.helome.messagecenter.message.base;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.core.MessageFactory;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;
/**
 * @description 下载提醒消息
 * @author beyond.zhang   
 */
public class DownLoadMessage implements Message{
	private final static Logger logger = LoggerFactory.getLogger(DownLoadMessage.class);

	public static final short CODE = 11;

	private Long senderId;

	private Long receiverId;
	
	private String senderName;
		
	private String receiverName;

	private Endpoint endpoint;

	private int length;
		
	private String url;
	
	private String fileName;
	
	private Long fileSize;
	
	private Long sendTime;
	
	

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
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

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public  short getCode() {
		return CODE;
	}

	@Override
	public void onReceived() {
	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this, endpoint.getContext());
		byte[] senderNameByte = senderName.getBytes(CharsetUtil.UTF_8);
		byte[] receiverNameByte = receiverName.getBytes(CharsetUtil.UTF_8);
		byte[] fileNameBytes = fileName.getBytes(CharsetUtil.UTF_8);
		byte[] urlByte = url.getBytes(CharsetUtil.UTF_8);
		buffer.writeLong(senderId);
		buffer.writeByte(senderNameByte.length);
		buffer.writeBytes(senderNameByte);
		buffer.writeLong(receiverId);
		buffer.writeByte(receiverNameByte.length);
		buffer.writeBytes(receiverNameByte);
		buffer.writeByte(fileNameBytes.length);
		buffer.writeBytes(fileNameBytes);
		buffer.writeByte(urlByte.length);
		buffer.writeBytes(urlByte);
		buffer.writeLong(fileSize);
		buffer.writeLong(sendTime);
		logger.debug("发送下载提醒消息from："+senderId+";to:"+receiverId);
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
		map.put("url", url);
		map.put("fileName", fileName);
		map.put("fileSize", fileSize);
		map.put("sendTime", sendTime);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("下载消息出错："+e);
		}
		logger.debug("发送下载提醒消息from："+senderId+";to:"+receiverId);
		return str;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
/*		userId = buffer.readLong();
		short userNameLen = buffer.readUnsignedByte();
		byte[] userNameBytes = new byte[userNameLen];
		buffer.readBytes(userNameBytes);
		userName = new String(userNameBytes,CharsetUtil.UTF_8);
		
		short fileNameLen = buffer.readUnsignedByte();
		byte[] fileNameBytes = new byte[fileNameLen];
		buffer.readBytes(fileNameBytes);
		fileName = new String(fileNameBytes,CharsetUtil.UTF_8);
		
		short urlLen = buffer.readUnsignedByte();
		byte[] urlBytes = new byte[urlLen];
		buffer.readBytes(urlBytes);
		url = new String(urlBytes,CharsetUtil.UTF_8);*/
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		senderId = map.get("senderId").asLong();
		/*uri = map.get("userName").asText();
		fileName = map.get("fileName").asText();
		url = map.get("url").asText();
		this.length = 2+8 +3 + Utils.getUTF8StringLength(userName)+Utils.getUTF8StringLength(fileName)+Utils.getUTF8StringLength(url);
	*/
	}


	
	
}
