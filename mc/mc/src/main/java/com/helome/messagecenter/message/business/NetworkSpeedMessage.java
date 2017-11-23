package com.helome.messagecenter.message.business;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.helome.messagecenter.core.Context;
import com.helome.messagecenter.core.Endpoint;
import com.helome.messagecenter.core.MessageFactory;
import com.helome.messagecenter.core.SocketEndpoint;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.Utils;
/**
 * 
 * @package com.helome.messagecenter.message.business
 * @description 网速延迟
 */
public class NetworkSpeedMessage implements Message {

	public static final short CODE = 260;
	private final static Logger logger = LoggerFactory.getLogger(NetworkSpeedMessage.class);

	private Long senderId;

	private Long receiverId;

	private Endpoint endpoint;

	private int length;

	private long key;


	public long getKey() {
		return key;
	}

	public void setKey(long key) {
		this.key = key;
	}

	@Override
	public short getCode() {
		return NetworkSpeedMessage.CODE;
	}

	@Override
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int getLength() {
		return length;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	@Override
	public void setEndpoint(Endpoint Endpoint) {
		this.endpoint = Endpoint;
	}

	@Override
	public void onReceived() {
		List<Endpoint> fromEndpoints = Context.getEndpoints(senderId);
		List<Endpoint> toEndpoints = Context.getEndpoints(receiverId);
		if (fromEndpoints == null ) {
			Utils.close(endpoint.getContext());
		} else {
			if (toEndpoints != null) {
				synchronized(toEndpoints){
				for(Endpoint toEndpoint : toEndpoints){
					if (toEndpoint instanceof SocketEndpoint) {
						toEndpoint.getContext().channel().writeAndFlush(toBinary());
					} else {
						toEndpoint.getContext().channel()
								.writeAndFlush(new TextWebSocketFrame(toJson()));
						}
					}
				}
			}
		}
	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		buffer.writeLong(senderId);
		buffer.writeLong(receiverId);
		buffer.writeInt(new Long(key).intValue());

		return buffer;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("senderId", senderId);
		map.put("receiverId", receiverId);
		map.put("key", key);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("网速延迟消息异常："+e);
		}
		return str;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		senderId = buffer.readLong();
		receiverId = buffer.readLong();
		key = buffer.readUnsignedInt();

	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		senderId = map.get("senderId").asLong();
		receiverId = map.get("receiverId").asLong();
		key = map.get("key").asInt();
		this.length = 2 + 8 + 8 + 4;
	}

}
