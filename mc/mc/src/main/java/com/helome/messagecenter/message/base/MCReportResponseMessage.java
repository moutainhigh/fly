package com.helome.messagecenter.message.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.internal.PlatformDependent;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.message.Message;

public class MCReportResponseMessage implements Message {

	public static final short CODE = 1;
	
	public static final byte IDENTITY = 77;
	
	private short mcId;
	
	private short result;
	
	private int length;
	
	private Endpoint endpoint;
	
	
	
	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	public short getMcId() {
		return mcId;
	}

	public void setMcId(short mcId) {
		this.mcId = mcId;
	}

	public short getResult() {
		return result;
	}

	public void setResult(short result) {
		this.result = result;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public short getCode() {
		return CODE;
	}

	@Override
	public void onReceived() {
		
	
	}

	@Override
	public ByteBuf toBinary() {
		UnpooledByteBufAllocator byteBufAllocator =
	            new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());
		ByteBuf buffer = byteBufAllocator.buffer();
		buffer.writeByte(IDENTITY);
		buffer.writeInt(2+2+2);
		buffer.writeShort(CODE);
		buffer.writeShort(mcId);
		buffer.writeShort(result);
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
