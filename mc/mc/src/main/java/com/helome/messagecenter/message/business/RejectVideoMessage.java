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
 * 
 * @description 拒绝视频会话消息
 * @author beyond.zhang
 */
public class RejectVideoMessage implements Message {

	public static final short CODE = 212;
	private final static Logger logger = LoggerFactory.getLogger(RejectVideoMessage.class);

	private Long userId;

	private String userName;

	private Long inviterId;

	private String inviterName;

	private long sessionId;

	private Long date;

	private Endpoint endpoint;

	private int length;

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

	public Long getInviterId() {
		return inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}

	public String getInviterName() {
		return inviterName;
	}

	public void setInviterName(String inviterName) {
		this.inviterName = inviterName;
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
		logger.debug("收到拒绝视频消息 userId {},inviterId {}",userId,inviterId);
		List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
		List<Endpoint> toEndpoints = Context.getEndpoints(inviterId);
		if (fromEndpoints == null ) {
			Utils.close(endpoint.getContext());
		} else {
			try {
				Context.TIMEOUT_MANAGER.deregister(Context.ID_TO_SLOT.remove(inviterId));
				Context.deregisterVA(inviterId,userId);
			} catch (Exception e) {
				logger.error("收到拒绝视频消息异常",e);
			}
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
		byte[] inviterNameBytes = inviterName.getBytes(CharsetUtil.UTF_8);
		buffer.writeLong(userId);
		buffer.writeByte(userNameBytes.length);
		buffer.writeBytes(userNameBytes);
		buffer.writeLong(inviterId);
		buffer.writeByte(inviterNameBytes.length);
		buffer.writeBytes(inviterNameBytes);
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
		inviterId = buffer.readLong();
		short inviterNameLen = buffer.readUnsignedByte();
		byte[] inviterNameBytes = new byte[inviterNameLen];
		buffer.readBytes(inviterNameBytes);
		inviterName = new String(inviterNameBytes, CharsetUtil.UTF_8);

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
		map.put("inviterId", inviterId);
		map.put("date", date);
		map.put("userName", userName);
		map.put("inviterName", inviterName);
		map.put("sessionId", sessionId);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("拒绝视频会话消息异常："+e);
		}
		return str;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		userId = map.get("userId").asLong();
		inviterId = map.get("inviterId").asLong();
		date = map.get("date").asLong();
		userName = map.get("userName").asText();
		inviterName = map.get("inviterName").asText();
		sessionId = map.get("sessionId").asInt();
		this.length = 2 + 8 + 8 + 6 + 4 + 2 * 1
				+ Utils.getUTF8StringLength(inviterName)
				+ Utils.getUTF8StringLength(userName);
	}


	
	
}
