package com.helome.messagecenter.message.business;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

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

public class UpdateNumMessage implements Message {

	public static final short CODE = 111;
	private final static Logger logger = LoggerFactory.getLogger(UpdateNumMessage.class);

	private int length;

	private Endpoint endpoint;

	public UpdateNumMessage() {
		this.length = 2;
	}

	@Override
	public short getCode() {
		return UpdateNumMessage.CODE;
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
	
	
	
	@Override
	public void onReceived() {
	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		return buffer;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("UpdateNumMessage 消息异常："+e);
		}
		return str;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
	}

}
