package com.helome.messagecenter.message.business;

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

public class CreateSessionFailMessage implements Message {

	public static final short CODE = 202;
	private final static Logger logger = LoggerFactory.getLogger(CreateSessionFailMessage.class);

	private Endpoint endpoint;

	private int length;

	@Override
	public short getCode() {
		return CreateSessionFailMessage.CODE;
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
		/*
		 * Endpoint fromEndpoint = Context.getEndpoint(userId); if(fromEndpoint
		 * == null || fromEndpoint != endpoint){ Utils.close(endpoint.getContext());
		 * }else{ if(toEndpoint != null){ if(toEndpoint instanceof
		 * SocketEndpoint){ ChannelFuture future =
		 * toEndpoint.getContext().channel().writeAndFlush(toBinary()); }else{
		 * ChannelFuture future =
		 * toEndpoint.getContext().channel().writeAndFlush(new
		 * TextWebSocketFrame(toJson())); } } }
		 */
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
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("CreateSessionFailMessage 消息异常："+e);
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
