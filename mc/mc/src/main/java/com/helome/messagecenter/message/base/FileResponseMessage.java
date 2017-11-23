package com.helome.messagecenter.message.base;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.core.MessageFactory;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;

public class FileResponseMessage implements Message {
	public static final short CODE = 8;
	private final static Logger logger = LoggerFactory.getLogger(FileResponseMessage.class);
	

	private int packageNo;
	
	private Long dateTime;
	
	private int state;

	private Endpoint endpoint;
	
	private int length;
	
	public static final short SUCCESS = 0;	

	public FileResponseMessage(int packageNo, Long dateTime,int state){
		this.packageNo = packageNo;
		this.dateTime = dateTime;
		this.state = state;
	}
	public FileResponseMessage() {
		super();
	}

	public FileResponseMessage(short state) {
		this.state = state;
		this.length = 4;
	}

	public int getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	@Override
	public short getCode() {
		return CODE;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public void onReceived() {

	}

	
	public int getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(int packageNo) {
		this.packageNo = packageNo;
	}

	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}


	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		buffer.writeShort(state);
		buffer.writeLong(dateTime);
		buffer.writeInt(packageNo);
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		 state =  buffer.readUnsignedShort();
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("state", state);
		map.put("packageNo", packageNo);
		map.put("dateTime", dateTime);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("返回消息异常："+e);
		}
		return str;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
	}

}
