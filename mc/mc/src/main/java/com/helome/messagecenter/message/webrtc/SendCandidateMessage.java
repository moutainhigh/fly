package com.helome.messagecenter.message.webrtc;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
 * @description 发送Candidate
 * @author beyond.zhang
 */
public class SendCandidateMessage implements Message {
	private final static Logger logger = LoggerFactory
			.getLogger(SendCandidateMessage.class);

	public static final short CODE = 232;

	private Long senderId;

	private Long receiverId;

	private int sessionId;

	private ObjectNode candidate;

	private Endpoint endpoint;

	private int length;
	
	private String receiverIds;

	
	

	public String getReceiverIds() {
		return receiverIds;
	}

	public void setReceiverIds(String receiverIds) {
		this.receiverIds = receiverIds;
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

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public ObjectNode getCandidate() {
		return candidate;
	}

	public void setCandidate(ObjectNode candidate) {
		this.candidate = candidate;
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
		List<Endpoint> fromEndpoints = Context.getEndpoints(senderId);
		List<Endpoint> toEndpoints = null;
        if(receiverIds!=null){
       	 toEndpoints = Context.getGroupEndpoints(receiverIds);
		}else{
			toEndpoints = Context.getEndpoints(receiverId);
		}

		logger.info("收到 candidate消息  from {} to {} 耗时：{}  毫秒", senderId,receiverId);
		System.out.println("收到 candidate消息  from "+senderId+" to "+receiverId+" 耗时：{}  毫秒");

		if (fromEndpoints == null) {
			Utils.close(endpoint.getContext());
		} else {
			if (toEndpoints != null) {
				synchronized (toEndpoints) {
					for (Endpoint toEndpoint : toEndpoints) {
						if (toEndpoint instanceof SocketEndpoint) {
							toEndpoint.getContext().channel()
									.writeAndFlush(toBinary());
						} else {
							toEndpoint
									.getContext()
									.channel()
									.writeAndFlush(
											new TextWebSocketFrame(toJson()));
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
		String str = "";
		try {
			str = JsonUtils.stringify(candidate);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		byte[] candidateBytes = str.getBytes(CharsetUtil.UTF_8);
		buffer.writeLong(senderId);
		buffer.writeLong(receiverId);
		buffer.writeInt(sessionId);
		buffer.writeShort(candidateBytes.length);
		buffer.writeBytes(candidateBytes);
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		senderId = buffer.readLong();
		receiverId = buffer.readLong();
		sessionId = buffer.readInt();
		short candidateLength = buffer.readShort();
		byte[] candidatebytes = new byte[candidateLength];
		buffer.readBytes(candidatebytes);
		String str = new String(candidatebytes, CharsetUtil.UTF_8);
		try {
			candidate = JsonUtils.generate(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("senderId", senderId);
		map.put("receiverId", receiverId);
		map.put("sessionId", sessionId);
		map.put("receiverIds", receiverIds);
		map.put("candidate", candidate);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
		}
		return str;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		senderId = map.get("senderId").asLong();
		receiverId = map.get("receiverId").asLong();
		sessionId = map.get("sessionId").asInt();
		candidate = (ObjectNode) map.get("candidate");
		if(map.containsKey("receiverIds")){
			receiverIds=	map.get("receiverIds").asText();
		}
		this.length = 2 + 8 + 8 + 4 + 2
				+ Utils.getUTF8StringLength(candidate.toString());
	}

}
