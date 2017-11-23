package com.helome.messagecenter.message.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.message.Message;

public class ResponseUserMCMessage implements Message {

	public static final short CODE = 1;
	
	public static final byte IDENTITY = 77;
	
	private Long senderId;

	private Long receiveId;
	
	private String realIp;

	private String proxyIp;
	
	private Endpoint endpoint;
	
	public String getRealIp() {
		return realIp;
	}

	public void setRealIp(String realIp) {
		this.realIp = realIp;
	}

	public String getProxyIp() {
		return proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	private int length;
	


	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Long receiveId) {
		this.receiveId = receiveId;
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

	public  short getCode() {
		return CODE;
	}

	@Override 
	public void onReceived() {
	
	}

	@Override
	public ByteBuf toBinary() {
		UnpooledByteBufAllocator byteBufAllocator =
	            new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());
		byte[] realIpBytes = realIp.getBytes(CharsetUtil.UTF_8);
		byte[] proxyIpBytes = proxyIp.getBytes(CharsetUtil.UTF_8);
		ByteBuf buffer = byteBufAllocator.buffer();
		buffer.writeByte(IDENTITY);
		buffer.writeInt(2+8+8+2+realIpBytes.length+proxyIpBytes.length);
		buffer.writeShort(CODE);
		buffer.writeLong(senderId);
		buffer.writeLong(receiveId);
		buffer.writeByte(realIpBytes.length);
		buffer.writeBytes(realIpBytes);
		buffer.writeByte(proxyIpBytes.length);
		buffer.writeBytes(proxyIpBytes);
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
	}

	@Override
	public String toJson() {
		return null;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {

	}

}
