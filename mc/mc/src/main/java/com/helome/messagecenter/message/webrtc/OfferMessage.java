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

public class OfferMessage implements Message {
	private final static Logger logger = LoggerFactory
			.getLogger(OfferMessage.class);

	public static final short CODE = 230;

	private Long senderId;

	private Long receiverId;

	private int sessionId;

	private ObjectNode sdp;

	private String agent;
	

	private Endpoint endpoint;
	
	private String receiverIds;

	
	private int length;
	
	

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

	public ObjectNode getSdp() {
		return sdp;
	}

	public void setSdp(ObjectNode sdp) {
		this.sdp = sdp;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
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
		logger.info("收到 offer消息  from {} to {} ", senderId,receiverId);
		System.out.println("收到 offer消息  from "+senderId+" to "+receiverId+" 耗时：{}  毫秒");

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
		String str = "";
		try {
			str = JsonUtils.stringify(sdp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		byte[] sdpBytes = str.getBytes(CharsetUtil.UTF_8);
		byte[] agentBytes = agent.getBytes(CharsetUtil.UTF_8);
		buffer.writeLong(senderId);
		buffer.writeLong(receiverId);
		buffer.writeInt(sessionId);
		buffer.writeShort(sdpBytes.length);
		buffer.writeBytes(sdpBytes);
		buffer.writeByte(agentBytes.length);
		buffer.writeBytes(agentBytes);
		return buffer;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		senderId = buffer.readLong();
		receiverId = buffer.readLong();
		sessionId = buffer.readInt();
		short sdpLength = buffer.readShort();
		byte[] sdpytes = new byte[sdpLength];
		buffer.readBytes(sdpytes);
		String str = new String(sdpytes,CharsetUtil.UTF_8);
		try {
			sdp = JsonUtils.generate(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte agentLength = buffer.readByte();
		byte[] agentbytes = new byte[agentLength];
		buffer.readBytes(agentbytes);
		agent = new String(agentbytes, CharsetUtil.UTF_8);
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("senderId", senderId);
		map.put("receiverId", receiverId);
		map.put("sessionId", sessionId);
		map.put("receiverIds", receiverIds);
		map.put("sdp", sdp);
		map.put("agent", agent);
		String str = "";
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
		}
		return str;
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		senderId = map.get("senderId").asLong();
		
		System.err.println("offermsg receiveid="+map.get("receiverId"));
		receiverId = map.get("receiverId").asLong();
		sessionId = map.get("sessionId").asInt();
		if(map.containsKey("receiverIds")){
			receiverIds=	map.get("receiverIds").asText();
		}
		sdp = (ObjectNode) map.get("sdp");
		agent = map.get("agent").asText();
		this.length = 2 + 8 + 8 + 4 + 2 + 1 + Utils.getUTF8StringLength(sdp.toString())
				+ Utils.getUTF8StringLength(agent);
	}

}
