package com.helome.messagecenter.message.base;

import io.netty.buffer.ByteBuf;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.core.MessageFactory;
import com.helome.messagecenter.message.Message;

public class ObtainUserMCMessage implements Message {

	public static final short CODE = 1;
	
	private Long senderId;

	private Long receiveId;
	
	private Endpoint endpoint;
	
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
		ByteBuf buffer = MessageFactory.createByteBuf(this, endpoint.getContext());
		buffer.writeLong(senderId);
		buffer.writeLong(receiveId);
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		senderId = buffer.readLong();
		receiveId = buffer.readLong();
	}

	@Override
	public String toJson() {
		return null;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {

	}

}
