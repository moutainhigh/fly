package com.helome.messagecenter.message.business;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.util.Date;
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
import com.helome.messagecenter.utils.Utils;

/**
 * @description 邀请对方视频会话消息
 * @author beyond.zhang
 */
public class NotAvailable implements Message {

	public static final Short ME = 270;
	private final static Logger logger = LoggerFactory.getLogger(NotAvailable.class);

	public static final Short YOU = 271;
	
	private Short code;
	
	private Long userId;

	private String userName;

	private Long inviteeId;

	private String inviteeName;

	private long sessionId;

	private Long date;

	private Endpoint endpoint;

	private int length;

	
	public NotAvailable() {
		super();
	}

	public NotAvailable(Short code, Long userId, String userName, Long inviteeId,
			String inviteeName) {
		this.code = code;
		this.userId = userId;
		this.userName = userName;
		this.inviteeId = inviteeId;
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


	public Long getInviteeId() {
		return inviteeId;
	}

	public void setInviteeId(Long inviteeId) {
		this.inviteeId = inviteeId;
	}

	public String getInviteeName() {
		return inviteeName;
	}

	public void setInviteeName(String inviteeName) {
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
		return code;
	}

	@Override
	public void onReceived() {
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
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
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
			logger.error("邀请对方视频会话消息异常："+e);
		}
		return str;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
	}

}

