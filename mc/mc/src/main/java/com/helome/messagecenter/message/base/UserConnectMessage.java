package com.helome.messagecenter.message.base;

import io.netty.buffer.ByteBuf;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.core.MessageFactory;
import com.helome.messagecenter.message.Message;

public class UserConnectMessage implements Message {

	public static final short CODE = 1;

	private short groupId;
	
	private short mcId;

	private Long userId;
	
	private Endpoint endpoint;
	
	private int length;
	


	public short getGroupId() {
		return groupId;
	}

	public void setGroupId(short groupId) {
		this.groupId = groupId;
	}

	public short getMcId() {
		return mcId;
	}

	public void setMcId(short mcId) {
		this.mcId = mcId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
		buffer.writeShort(groupId);
		buffer.writeShort(mcId);
		buffer.writeLong(userId);
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		groupId = buffer.readShort();
		mcId = buffer.readShort();
		userId = buffer.readLong();
	}

	@Override
	public String toJson() {
		return null;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {

	}

}
