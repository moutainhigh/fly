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

/**
 * @description 异常下线消息
 * @author beyond.zhang
 */
public class AbNormalOfflineMessage implements Message {
	private final static Logger logger = LoggerFactory.getLogger(AbNormalOfflineMessage.class);

	public static final short CODE = 21;

	private Endpoint endpoint;

	private int length;

	public AbNormalOfflineMessage(){
		this.length = 2;
	}

	@Override
	public short getCode() {
		return AbNormalOfflineMessage.CODE;
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
		logger.debug(endpoint.getContext().channel().remoteAddress()+ "异常下线");
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
		}
		logger.debug(endpoint.getContext().channel().remoteAddress()+ "异常下线");
		return str;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
	}

}
