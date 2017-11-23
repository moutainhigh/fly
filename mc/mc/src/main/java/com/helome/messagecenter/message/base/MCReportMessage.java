package com.helome.messagecenter.message.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.message.Message;

public class MCReportMessage implements Message {

	public static final short CODE = 1;
	
	public static final byte IDENTITY = 77;

	private short groupId;
	
	private String realIp;

	private String proxyIp;
	
	private Endpoint endpoint;
	
	private short proxySocket;
	
	private short machineWeight;
	
	private int length;
	

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public short getGroupId() {
		return groupId;
	}

	public void setGroupId(short groupId) {
		this.groupId = groupId;
	}

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

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	public short getProxySocket() {
		return proxySocket;
	}

	public void setProxySocket(short proxySocket) {
		this.proxySocket = proxySocket;
	}

	public short getMachineWeight() {
		return machineWeight;
	}

	public void setMachineWeight(short machineWeight) {
		this.machineWeight = machineWeight;
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
	/*	buffer.writeShort(CODE);
		buffer.writeShort(mcId);
		buffer.writeShort(result);*/
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		
		groupId = buffer.readShort();
		byte realIpLen = buffer.readByte();
		byte[] realIpBytes = new byte[realIpLen];
		buffer.readBytes(realIpBytes);
		realIp = new String(realIpBytes, CharsetUtil.UTF_8);
		
		byte proxyIpLen = buffer.readByte();
		byte[] proxyIpBytes = new byte[proxyIpLen];
		buffer.readBytes(proxyIpBytes);
		proxyIp = new String(proxyIpBytes, CharsetUtil.UTF_8);
		
		proxySocket = buffer.readShort();
		machineWeight = buffer.readShort();

	}

	@Override
	public String toJson() {
		return null; 
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		
	} 

}
