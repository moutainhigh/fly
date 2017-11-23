package com.helome.messagecenter.message.business;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.util.Date;
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
 * @description 音频会话请求超时
 * @author beyond.zhang
 */
public class AudioTimeoutMessage implements Message {

	public static final short CODE = 223;
	private final static Logger logger = LoggerFactory.getLogger(AudioTimeoutMessage.class);

	private Long userId;

	private String userName;

	private Long inviteeId;

	private String inviteeName;

	private long sessionId;

	private Long date;

	private Endpoint endpoint;

	private int length;
	
	public AudioTimeoutMessage() {
		super();
	}

	public AudioTimeoutMessage(Long userId, String userName, Long inviteeId,
			String inviteeName) {
		this.userId = userId;
		this.inviteeId = inviteeId;
		this.userName = userName;
		this.inviteeName = inviteeName;
		this.date = new Date().getTime();
		this.length = 2 + 8 + 8 + 2 + 6 + 4
				+ userName.getBytes(CharsetUtil.UTF_8).length
				+ userName.getBytes(CharsetUtil.UTF_8).length;
	}

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

	public Long getinviteeId() {
		return inviteeId;
	}

	public void setinviteeId(Long inviteeId) {
		this.inviteeId = inviteeId;
	}

	public String getinviteeName() {
		return inviteeName;
	}

	public void setinviteeName(String inviteeName) {
		this.inviteeName = inviteeName;
	}


	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
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

	public short getCode() {
		return CODE;
	}

	@Override
	public void onReceived() {
		List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
		List<Endpoint> toEndpoints = Context.getEndpoints(inviteeId);
		if (fromEndpoints == null) {
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
		byte[] dateByte = Utils.unsigned48ToBytes(date);
		byte[] userNameBytes = userName.getBytes(CharsetUtil.UTF_8);
		byte[] inviteeNameBytes = inviteeName.getBytes(CharsetUtil.UTF_8);
		buffer.writeLong(userId);
		buffer.writeByte(userNameBytes.length);
		buffer.writeBytes(userNameBytes);
		buffer.writeLong(inviteeId);
		buffer.writeByte(inviteeNameBytes.length);
		buffer.writeBytes(inviteeNameBytes);
		buffer.writeBytes(dateByte);
		buffer.writeInt(new Long(sessionId).intValue());
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		userId = buffer.readLong();
		short userNameLen = buffer.readUnsignedByte();
		byte[] userNameBytes = new byte[userNameLen];
		buffer.readBytes(userNameBytes);
		userName = new String(userNameBytes, CharsetUtil.UTF_8);
		inviteeId = buffer.readLong();
		short inviteeNameLen = buffer.readUnsignedByte();
		byte[] inviteeNameBytes = new byte[inviteeNameLen];
		buffer.readBytes(inviteeNameBytes);
		inviteeName = new String(inviteeNameBytes, CharsetUtil.UTF_8);
		byte[] bytes = new byte[6];
		buffer.readBytes(bytes);
		//date = Utils.from48Unsigned(bytes);
		date = new Date().getTime();
		sessionId = buffer.readUnsignedInt();

		
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("userId", userId);
		map.put("inviteeId", inviteeId);
		map.put("date", date);
		map.put("userName", userName);
		map.put("inviteeName", inviteeName);
		map.put("sessionId", sessionId);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("音频会话请求超时消息code(223)异常："+e);
		}
		return str;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		userId = map.get("userId").asLong();
		inviteeId = map.get("inviteeId").asLong();
		date = map.get("date").asLong();
		userName = map.get("userName").asText();
		inviteeName = map.get("inviteeName").asText();
		sessionId = map.get("sessionId").asInt();
		this.length = 2 + 8 + 8 + 6 + 4 + 2 * 1
				+ Utils.getUTF8StringLength(userName)
				+ Utils.getUTF8StringLength(inviteeName);
	}

}
