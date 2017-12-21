package com.helome.messagecenter.message.business;

import io.netty.buffer.ByteBuf;

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
 * @description 邀请对方视频会话消息
 * @author beyond.zhang
 */
public class InviteVideoMessageSuccess implements Message {

	public static final short CODE = 215;
	private final static Logger logger = LoggerFactory.getLogger(InviteVideoMessageSuccess.class);

	private Endpoint endpoint;

	private int length;

	public void onReceived() {
	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("邀请对方视频会话消息异常："+e);
		}
		return str;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
	}

	@Override
	public short getCode() {
		return CODE;
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

}
