package com.helome.messagecenter.message.business;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.util.Date;
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
import com.helome.messagecenter.utils.Utils;

public class CreateSessionMessage implements Message {

	public static final short CODE = 200;
	private final static Logger logger = LoggerFactory.getLogger(CreateSessionMessage.class);

	private Long userId;

	private Long date;

	private Endpoint endpoint;

	private int length;

	public String userName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	@Override
	public short getCode() {
		return CreateSessionMessage.CODE;
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
		buffer.writeLong(userId);
		byte[] dateByte = Utils.unsigned48ToBytes(date);
		byte[] userNameBytes = userName.getBytes(CharsetUtil.UTF_8);
		buffer.writeByte(userNameBytes.length);
		buffer.writeBytes(userNameBytes);
		buffer.writeBytes(dateByte);

		return buffer;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("userId", userId);
		map.put("userName", userName);
		map.put("date", date);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("CreateSessionMessage 消息异常"+e);
		}
		return str;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		userId = buffer.readLong();
		byte userNameLen = buffer.readByte();
		byte[] userNameBytes = new byte[userNameLen];
		buffer.readBytes(userNameBytes);
		userName = new String(userNameBytes, CharsetUtil.UTF_8);
		byte[] bytes = new byte[6];
		buffer.readBytes(bytes);
		//date = Utils.from48Unsigned(bytes);
		date = new Date().getTime();

	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		userId = map.get("userId").asLong();
		userName = map.get("userName").asText();
		date = map.get("date").asLong();
	}

}
