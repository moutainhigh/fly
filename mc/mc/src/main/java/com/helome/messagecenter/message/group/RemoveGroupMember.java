package com.helome.messagecenter.message.group;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
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
import com.helome.messagecenter.utils.HttpUtils;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.Utils;

public class RemoveGroupMember implements Message {
	private final static Logger logger = LoggerFactory
			.getLogger(RemoveGroupMember.class);

	public static final short CODE = 303;

	private Long groupId;

	private Long memberId;

	private int length;

	private Endpoint endpoint;
	
	@Override
	public short getCode() {
		return RemoveGroupMember.CODE;
	}

	@Override
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int getLength() {
		return length;
	}


	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Override
	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public void onReceived() {
		logger.debug("收到添加用户{}加入群{} 消息.", new Object[]{memberId,groupId});
		Message msg = null;
        if(GroupManager.removeUser(groupId, memberId) == 1){
                msg = MessageFactory.createSuccessFutureMessage();
        }else{
               msg = MessageFactory.createFutureMessage(FutureMessage.STATE_FAIL_OTHER);
        }
        msg.setEndpoint(endpoint);
        if (endpoint instanceof SocketEndpoint) {
            endpoint.getContext().channel().writeAndFlush(msg.toBinary());
        } else {
            endpoint.getContext().channel().writeAndFlush(new TextWebSocketFrame(msg.toJson()));
        }
	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		buffer.writeLong(groupId);
		buffer.writeLong(memberId);
		return buffer;
	}
	@Override
	public void fromBinary(ByteBuf buffer) {
		groupId = buffer.readLong();
		memberId = buffer.readLong();
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("groupId", groupId);
		map.put("memberId", memberId);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
		}
		return str;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		groupId = map.get("groupId").asLong();
		memberId = map.get("memberId").asLong();
		this.length = 2 + 8 + 8 ;
	}


}
