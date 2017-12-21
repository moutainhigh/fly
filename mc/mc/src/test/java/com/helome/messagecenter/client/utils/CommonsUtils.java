package com.helome.messagecenter.client.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.helome.messagecenter.client.socket.MessageDateInfo;
import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.message.business.AcceptChatMessage;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.PropertiesUtils;
import com.helome.messagecenter.utils.Utils;

public class CommonsUtils {
	private final static Logger logger = LoggerFactory
			.getLogger(CommonsUtils.class);
    
	public static String getAddress() {
		String wbpath = null;
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			//String ip = inetAddress.getHostAddress();
			String ip ="112.74.94.53";
			String port = PropertiesUtils.readValue("mc.websocket.port");
			wbpath = "ws://" + ip + ":" + port;
			System.out.println(wbpath);
		} catch (Exception e) {
			logger.error("单元测试获取资源文件出错",e);
		}
		return wbpath;
	}
    public static int getSocketPort(){
    	String port = PropertiesUtils.readValue("mc.socket.port");
    	return Integer.parseInt(port);
    }
	public static MessageMetaData getReportMetaData(long id){
		MessageMetaData messageMetaData = new MessageMetaData();
		String token ="junit:test";
		int length = 2+8+1+token.getBytes().length+1;
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);//length 自己算
		MessageDateInfo info3 = new MessageDateInfo("short",1);//code 
		MessageDateInfo info4= new MessageDateInfo("long",id);
		MessageDateInfo info5= new MessageDateInfo("byte",token.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("string",token);
		MessageDateInfo info7= new MessageDateInfo("byte",2);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		return messageMetaData;
	}
	
	public static void main(String[] args) {
        System.out.println(CommonsUtils.getAddress());
	}

	public static MessageMetaData getTxtMetaData(Long senderId, Long senderId2,String data) throws UnsupportedEncodingException {
		MessageMetaData messageMetaData = new MessageMetaData();
		
		String senderName = "test:senderId";
		int length = 2+8+1+8+1+2 +senderName.getBytes().length+senderName.getBytes().length+data.getBytes().length ;
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",3);//code 
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		
		MessageDateInfo info7= new MessageDateInfo("long",senderId2);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		
		MessageDateInfo info10= new MessageDateInfo("short",data.getBytes().length);
		MessageDateInfo info11= new MessageDateInfo("String",data);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info10);
		messageMetaData.add(info11);
		return messageMetaData;
	}

	public static MessageMetaData getOffLineMetaData(Long userId,String userName) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length =2+8+1+userName.getBytes().length ;
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",20);//code 
		
		MessageDateInfo info4 = new MessageDateInfo("long",userId);
		MessageDateInfo info5 = new MessageDateInfo("byte",userName.getBytes().length);
		MessageDateInfo info6 = new MessageDateInfo("string",userName);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		return messageMetaData;
	}
	public static MessageMetaData getCommonTxtMetaData(Long senderId,
			Long senderId2, String data, int i) {
         MessageMetaData messageMetaData = new MessageMetaData();
		String senderName = "test:senderId";
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		int length = 2+8+1+8+1+2 +senderName.getBytes().length+senderName.getBytes().length+data.getBytes().length +6;
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",i);//code 
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",senderId2);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info12= new MessageDateInfo("bytes",dateByte);
		
		MessageDateInfo info10= new MessageDateInfo("short",data.getBytes().length);
		MessageDateInfo info11= new MessageDateInfo("String",data);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info12);
		messageMetaData.add(info10);
		messageMetaData.add(info11);
		return messageMetaData;
	}
	public static MessageMetaData getTransferMetaData(Long senderId,
			Long senderId2, String string, int i) {
		String senderName = "test:senderId";
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		int length = 2 + 8 + 8 + 6 + 8 + 1 + 2 * 1
				+ Utils.getUTF8StringLength(senderName)
				+ Utils.getUTF8StringLength(senderName);
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",110);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",senderId2);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		
		MessageDateInfo info10= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info11= new MessageDateInfo("double",130.2);
		MessageDateInfo info12= new MessageDateInfo("byte",1);
		 MessageMetaData messageMetaData = new MessageMetaData();
		 messageMetaData.add(info1);
		 messageMetaData.add(info2);
		 messageMetaData.add(info3);
		 messageMetaData.add(info4);
		 messageMetaData.add(info5);
		 messageMetaData.add(info6);
		 messageMetaData.add(info7);
		 messageMetaData.add(info8);
		 messageMetaData.add(info9);
		 messageMetaData.add(info10);
		 messageMetaData.add(info11);
		 messageMetaData.add(info12);
		return messageMetaData;
	}
	public static MessageMetaData getAnswerMetaData(Long senderId,
			Long senderId2,  int sessionId) throws IOException {
		 MessageMetaData messageMetaData = new MessageMetaData();
		 Map<String, String> hashMap = new HashMap<String, String>();  
		  hashMap.put("name", "zhang");  
		  hashMap.put("sex", "1");  
		  hashMap.put("login", "Jack");  
		  hashMap.put("password", "123abc"); 
		  ObjectMapper mapper = new ObjectMapper();
		  String userMapJson = mapper.writeValueAsString(hashMap); 
		  ObjectNode node = JsonUtils.generate(userMapJson);
		  String str = JsonUtils.stringify(node);
		  String agent = "agent";
        int length = 2+8+8+4+2+str.getBytes().length+1+agent.getBytes().length;
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",231);
		
		MessageDateInfo info4 = new MessageDateInfo("long",senderId);
		MessageDateInfo info5 = new MessageDateInfo("long",senderId2);
		MessageDateInfo info6 = new MessageDateInfo("int",sessionId);
		
		MessageDateInfo info7 = new MessageDateInfo("short",str.getBytes().length);
		MessageDateInfo info8 = new MessageDateInfo("string",str);
		MessageDateInfo info9 = new MessageDateInfo("byte",agent.getBytes().length);
		MessageDateInfo info10 = new MessageDateInfo("String",agent);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getOfferMetaData(Long senderId,
			Long senderId2,  int sessionId) throws IOException {
		 MessageMetaData messageMetaData = new MessageMetaData();
		 Map<String, String> hashMap = new HashMap<String, String>();  
		  hashMap.put("name", "zhang");  
		  hashMap.put("sex", "1");  
		  hashMap.put("login", "Jack");  
		  hashMap.put("password", "123abc"); 
		  ObjectMapper mapper = new ObjectMapper();
		  String userMapJson = mapper.writeValueAsString(hashMap); 
		  ObjectNode node = JsonUtils.generate(userMapJson);
		  String str = JsonUtils.stringify(node);
		  String agent = "agent";
        int length = 2+8+8+4+2+str.getBytes().length+1+agent.getBytes().length;
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",230);
		
		MessageDateInfo info4 = new MessageDateInfo("long",senderId);
		MessageDateInfo info5 = new MessageDateInfo("long",senderId2);
		MessageDateInfo info6 = new MessageDateInfo("int",sessionId);
		
		MessageDateInfo info7 = new MessageDateInfo("short",str.getBytes().length);
		MessageDateInfo info8 = new MessageDateInfo("string",str);
		MessageDateInfo info9 = new MessageDateInfo("byte",agent.getBytes().length);
		MessageDateInfo info10 = new MessageDateInfo("String",agent);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getSendCandidateMetaData(Long senderId,
			Long senderId2,  int sessionId) throws IOException {
		 MessageMetaData messageMetaData = new MessageMetaData();
		 Map<String, String> hashMap = new HashMap<String, String>();  
		  hashMap.put("name", "zhang");  
		  hashMap.put("sex", "1");  
		  hashMap.put("login", "Jack");  
		  hashMap.put("password", "123abc"); 
		  ObjectMapper mapper = new ObjectMapper();
		  String userMapJson = mapper.writeValueAsString(hashMap); 
		  ObjectNode node = JsonUtils.generate(userMapJson);
		  String candidate = JsonUtils.stringify(node);
        int length = 2+8+8+4+2+candidate.getBytes().length;
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",232);
		
		MessageDateInfo info4 = new MessageDateInfo("long",senderId);
		MessageDateInfo info5 = new MessageDateInfo("long",senderId2);
		MessageDateInfo info6 = new MessageDateInfo("int",sessionId);
		
		MessageDateInfo info7 = new MessageDateInfo("short",candidate.getBytes().length);
		MessageDateInfo info8 = new MessageDateInfo("string",candidate);
		
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		return messageMetaData;
	}
	public static MessageMetaData getAcceptAdoidMetaData(long senderId,long receiverId,int sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		String senderName = "test:senderId";
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+4;
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",221);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info10 = new MessageDateInfo("int",sessionId);
		MessageDateInfo info11 = new MessageDateInfo("bytes",dateByte);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info10);
		messageMetaData.add(info11);
		return messageMetaData;
	}
	public static MessageMetaData getAcceptChat(Long receiveId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8;
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",106);
		
		MessageDateInfo info4 = new MessageDateInfo("long",receiveId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		return messageMetaData;
	}
	public static MessageMetaData getAudioTimeoutMetaData(long senderId,long receiverId,String senderName,long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8+6;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",223);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info10);
		messageMetaData.add(info11);
		return messageMetaData;
	}
	public static MessageMetaData getChatMetaData(long senderId,long receiverId,String senderName) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+6;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",105);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info10= new MessageDateInfo("bytes",dateByte);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getCloseCameraMetaData(long senderId,long receiverId,String senderName,long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8+6;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",241);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info10);
		messageMetaData.add(info11);
		return messageMetaData;
	}
	public static MessageMetaData getAcceptVideoMetaData(Long senderId,Long receiverId,
			String senderName, long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8+6;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",211);
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info10);
		messageMetaData.add(info11);
		return messageMetaData;
	}
	public static MessageMetaData getAcceptConsultEndMetaData(long senderId,long receiverId,String senderName,long consultId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+6+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",141);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info10= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info11= new MessageDateInfo("long",consultId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info10);
		messageMetaData.add(info11);
		return messageMetaData;
	}
	public static MessageMetaData getLeaveChatMetaData(long senderId,long receiverId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+8+6;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",108);
		
		MessageDateInfo info4 = new MessageDateInfo("long",senderId);
		MessageDateInfo info5 = new MessageDateInfo("long",receiverId);
		MessageDateInfo info6 = new MessageDateInfo("bytes",dateByte);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		return messageMetaData;
	}
	public static MessageMetaData getCloseMicMetaData(long senderId,long receiverId,String senderName,long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",251);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getVideoTimeoutMetaData(long senderId,long receiverId,String senderName,long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",213);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getRejectVideoMetaData(long senderId,long receiverId,String senderName,long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",212);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getRejectConsultEndMetaData(Long senderId,Long receiverId,
			String senderName, long consultId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",142);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",consultId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getRejectChatMetaData(Long senderId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8;
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",107);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		return messageMetaData;
	}
	public static MessageMetaData getRejectAudioMetaData(Long senderId,Long receiverId,
			String senderName, long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",222);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getOpenMicMetaData(Long senderId,Long receiverId,
			String senderName, long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",250);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getOpenCameraMetaData(Long senderId,Long receiverId,
			String senderName, long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",240);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getOnlineMetaData(Long senderId,
			Long senderId2) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",160);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("long",senderId2);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		return messageMetaData;
	}
	public static MessageMetaData getNotAvailableMetaData(Long senderId,
			String senderName, long sessionId, int code) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",code);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",senderId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getLackTimeMetaData(Long senderId,Long receiverId,
			String senderName, long consultId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8+1;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",151);
		
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",consultId);
		MessageDateInfo info12= new MessageDateInfo("byte",12);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		messageMetaData.add(info12);
		return messageMetaData;
	}
	public static MessageMetaData getLackBalanceMetaData(Long senderId,Long receiverId,
			String senderName, long consultId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",150);
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",consultId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getInviteVideoMetaData(Long senderId,Long receiverId,
			String senderName, long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",210);
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getInviteAudioMetaData(Long senderId,Long receiverId,
			String senderName, long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",220);
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getHangUpMetaData(Long senderId,Long receiverId,
			String senderName, long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",260);
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getDontHaveMicMetaData(Long senderId,Long receiverId,
			String senderName, long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",252);
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getDontHaveCameraMetaData(Long senderId,Long receiverId,
			String senderName, long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",242);
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getConsultTimeMetaData(Long senderId,Long receiverId,
			String senderName) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",130);
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		return messageMetaData;
	}
	public static MessageMetaData getConsultRejectMetaData(Long senderId,Long receiverId,
			String senderName) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",132);
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		return messageMetaData;
	}
	public static MessageMetaData getConsultEndMetaData(Long senderId,Long receiverId,
			String senderName,long sessionId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",140);
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",sessionId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	public static MessageMetaData getConsultAcceptMetaData(Long senderId,Long receiverId,
			String senderName, long consultId) {
		MessageMetaData messageMetaData = new MessageMetaData();
		int length = 2+8+1+senderName.getBytes().length+8+1+senderName.getBytes().length+8;
		byte[] dateByte = Utils.unsigned48ToBytes(new Date().getTime());
		MessageDateInfo info1 = new MessageDateInfo("byte",77);//Identity
		MessageDateInfo info2 = new MessageDateInfo("int",length);
		MessageDateInfo info3 = new MessageDateInfo("short",131);
		MessageDateInfo info4= new MessageDateInfo("long",senderId);
		MessageDateInfo info5= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info6= new MessageDateInfo("String",senderName);
		MessageDateInfo info7= new MessageDateInfo("long",receiverId);
		MessageDateInfo info8= new MessageDateInfo("byte",senderName.getBytes().length);
		MessageDateInfo info9= new MessageDateInfo("String",senderName);
		MessageDateInfo info11= new MessageDateInfo("bytes",dateByte);
		MessageDateInfo info10= new MessageDateInfo("long",consultId);
		messageMetaData.add(info1);
		messageMetaData.add(info2);
		messageMetaData.add(info3);
		messageMetaData.add(info4);
		messageMetaData.add(info5);
		messageMetaData.add(info6);
		messageMetaData.add(info7);
		messageMetaData.add(info8);
		messageMetaData.add(info9);
		messageMetaData.add(info11);
		messageMetaData.add(info10);
		return messageMetaData;
	}
	
}
