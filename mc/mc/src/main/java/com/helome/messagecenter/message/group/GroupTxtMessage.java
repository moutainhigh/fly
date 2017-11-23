package com.helome.messagecenter.message.group;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
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
import com.helome.messagecenter.core.WebRTCEndpoint;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.DateUtil;
import com.helome.messagecenter.utils.HttpUtils;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.MemCachedUtil;
import com.helome.messagecenter.utils.Utils;

/**
 * @description 文本消息
 * @author beyond.zhang
 */
public class GroupTxtMessage implements Message {
	private final static Logger logger = LoggerFactory
			.getLogger(GroupTxtMessage.class);

	public static final short CODE = 305;
	
	private Endpoint endpoint;
	
	private String messageId;
	
	private String parentId;
	
	private Long senderId;

	private String senderName;
	
	private Long groupId;
	
	private int type;
	
	private String data;
	
	private int length;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public static final short ORIGINAL = 1;     /** 原文 **/ 
	public static final short TRANSLATION = 2;  /** 译文 **/ 
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	@Override
	public short getCode() {
		return GroupTxtMessage.CODE;
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


	@Override
	public void setEndpoint(Endpoint Endpoint) {
		this.endpoint = Endpoint;
	}

	@Override
	public void onReceived() {
		Group group = GroupManager.GROUPS.get(groupId);
		if(group!=null){
			List<Long> members = group.getMembers();
			sendChats(type);
			MemCachedUtil.putCommunicateNum(senderId,groupId);
			for(Long userId :members){
				 List<Endpoint> toEndpoints =	Context.getEndpoints(userId);
				 if(userId.longValue()!=senderId.longValue()){
					 if (toEndpoints != null) {
						 synchronized (toEndpoints) {
							 for (Endpoint toEndpoint : toEndpoints) {
								 if (toEndpoint instanceof SocketEndpoint) {
										toEndpoint.getContext().channel().writeAndFlush(toBinary());
									} else {
										toEndpoint.getContext().channel().writeAndFlush(new TextWebSocketFrame(toJson()));
									}
							 }
						 }
					 }
				 }
			}
		}
		
		
	}

	private String sendChats(int chatType) {
		String response = null;
		String jsonStr = null;
		try {
			if(chatType==ORIGINAL){
				jsonStr =createOriginalChatRecode();
			}else{
				jsonStr =createTranslationChatRecode();
			}
			response = HttpUtils.sendChatRequest(jsonStr);
		} catch (Exception e) {
			logger.error("保存群聊天消息{} from:{} to:{}出错",messageId,senderId,groupId);
		}
		return response;

	}

	private String createTranslationChatRecode() {
		  Map<String, Object> map = new HashMap<String, Object>();
	      Map<String, Object> subMap = new HashMap<String, Object>();
	      String parentData = searchByParentId(parentId+"", groupId+"");
	      String newData = data+"  (" + parentData+")";//译文的聊天记录要取出原文 然后拼一起存入知识库
	      String jsonStr = null;
	        try {
	        	subMap.put("subType", "chat");
	        	subMap.put("messageId", messageId);
	            subMap.put("type", TRANSLATION);
	            subMap.put("data", newData);
	            
				map.put("product", "helome");
				map.put("type", 2);
				map.put("contentType", 1);
				map.put("from", senderId + "");
				map.put("to", groupId + "");
				map.put("sendTime", DateUtil.getDate());
				map.put("content", JsonUtils.stringify(subMap));
				 jsonStr = JsonUtils.stringify(map);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		return jsonStr;
	}

	private String createOriginalChatRecode() {
		 Map<String, Object> map = new HashMap<String, Object>();
	      Map<String, Object> subMap = new HashMap<String, Object>();
	      String jsonStr = null;
	        try {
	        	subMap.put("subType", "chat");
	        	subMap.put("messageId", messageId);
	            subMap.put("type", ORIGINAL);
	            subMap.put("data", data);
	            
				map.put("product", "helome");
				map.put("type", 2);
				map.put("contentType", 1);
				map.put("from", senderId + "");
				map.put("to", groupId + "");
				map.put("sendTime", DateUtil.getDate());
				map.put("content", JsonUtils.stringify(subMap));
				 jsonStr = JsonUtils.stringify(map);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		return jsonStr;
	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = MessageFactory.createByteBuf(this,
				endpoint.getContext());
		byte[] senderNames = senderName.getBytes();
		byte[] datas = data.getBytes();
		buffer.writeBytes(messageId.getBytes());
		buffer.writeBytes(parentId.getBytes());
		buffer.writeLong(senderId);
		buffer.writeByte(senderNames.length);
		buffer.writeBytes(senderNames);
		buffer.writeLong(groupId);
		buffer.writeShort(type);
		buffer.writeShort(datas.length);
		buffer.writeBytes(datas);
		return buffer;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", CODE);
		map.put("messageId", messageId);
		map.put("parentId", parentId);
		map.put("senderId", senderId);
		map.put("senderName", senderName);
		map.put("groupId", groupId);
		map.put("type", type);
		map.put("data", data);
		String str = null;
		try {
			str = JsonUtils.stringify(map);
		} catch (JsonProcessingException e) {
			logger.error("文本消息异常：" + e);
		}
		return str;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		byte[] messageIdBytes = new byte[36];
		buffer.readBytes(messageIdBytes);
		messageId = new String(messageIdBytes,CharsetUtil.UTF_8);
		
		byte[] parentIdBytes = new byte[36];
		buffer.readBytes(parentIdBytes);
		parentId = new String(parentIdBytes,CharsetUtil.UTF_8);
		
		senderId = buffer.readLong();
		short senderNameLength=buffer.readUnsignedByte();
		byte[] senderNameBytes = new byte[senderNameLength];
		buffer.readBytes(senderNameBytes);
		senderName = new String(senderNameBytes,CharsetUtil.UTF_8);
		groupId =  buffer.readLong();
		type = buffer.readUnsignedShort();
		int dataLength = buffer.readUnsignedShort();
		byte[] dataBytes = new byte[dataLength];
		buffer.readBytes(dataBytes);
		data = new String(dataBytes,CharsetUtil.UTF_8);
	}

	@Override
	public void fromMap(Map<String, JsonNode> map) {
		messageId = map.get("messageId").asText();
		parentId = map.get("parentId").asText();
		senderId = map.get("senderId").asLong();
		senderName = map.get("senderName").asText();
		groupId = map.get("groupId").asLong();
		type = map.get("type").asInt();
		data = map.get("data").asText();
		this.length = 2+36 +36 +8 +1+senderName.getBytes().length+8+2+2+data.getBytes().length;
		
	}

	/**
	 * @description 根据父id 解析获取原文
	 */
	private String searchByParentId(String parentId,String groupId){
		String result = null;
		try {
			String str = HttpUtils.standardQuery(parentId,groupId);
			if(str.contains(parentId)){
			    result = str.split("\\[")[1].split("\\]")[0].split(parentId)[0].split("data")[1];
				result=result.substring(0, result.indexOf(",")).replaceAll("\"", "").replaceAll("\\\\", "").replaceAll(":", "");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
