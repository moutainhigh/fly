package com.helome.messagecenter.client.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @title SocketClient.java
 * @package com.helome.test.client.socket
 * @description TODO
 * @author ZhangHuaRong   
 * @update 2014-4-26 下午10:32:09
 * @version V1.0  
 * Copyright (c)2012 chantsoft-版权所有
 */
public class SocketClient {
	
	private Socket socket;
	 
	 private int port;
	 
	 private DataOutputStream output;
	 
	 private DataInputStream input;
	 
	 private String  ip;
	 
	private  Map<String,Object> datas = new HashMap<String,Object>();
	
	private List<Map<String,Object>> list = new CopyOnWriteArrayList<Map<String,Object>>();

	public SocketClient( String ip,int port) {
		super();
		this.port = port;
		this.ip = ip;
		output = null;
		input =null;
	}
	
	public List<Map<String, Object>> getList() {
		return list;
	}

	public void connect(){
		try {
			socket = new Socket(ip,port);
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DataOutputStream getOutput() {
		return output;
	}

	public DataInputStream getInput() {
		return input;
	}
	
	public void writeDate(MessageMetaData messageMetaData) throws IOException{
		LinkedList<MessageDateInfo> dataQueue =messageMetaData.getDataQueue();
		output.flush();
		for(MessageDateInfo data:dataQueue){
			if(data.getType().equalsIgnoreCase("String")){
				output.writeBytes((String)data.getValue());
			}else if(data.getType().equalsIgnoreCase("byte")){
				output.writeByte((Integer)data.getValue());
			}else if(data.getType().equalsIgnoreCase("int")){
				output.writeInt((Integer)data.getValue());
			}else if(data.getType().equalsIgnoreCase("long")){
				output.writeLong((Long)data.getValue());
			}else if(data.getType().equalsIgnoreCase("short")){
				output.writeShort((Integer)data.getValue());
			}else if(data.getType().equalsIgnoreCase("double")){
				output.writeDouble((Double)data.getValue());
			}else if(data.getType().equalsIgnoreCase("bytes")){
				byte[] date = (byte[]) data.getValue();
				output.write(date);
			}
		}
	}
	
	
	public Map<String,Object> readDate() throws IOException{
		try {
			int identity = input.readByte();
			int length = input.readInt();
			short code = input.readShort();
			datas.put("identity", identity);
			datas.put("length", length);
			datas.put("code", code);
			analyse(code,datas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}
	public Map<String,Object> readDateBlock(int stopCode) throws IOException{
		
		boolean flag = true;
		while(flag){
		Map<String,Object> data = new HashMap<String,Object>();
		try {
			
			int identity = input.readByte();
			int length = input.readInt();
			int realyLength=input.available();
			System.out.println("realyLength="+realyLength);
			short code = input.readShort();
			data.put("identity", identity);
			data.put("length", length);
			data.put("code", code);
			data.put("realyLength", realyLength);
			analyse(code,data);
			System.out.println("data = "+data);
			list.add(data);
			 if(stopCode==code){
				 try {
					Thread.sleep(2000);
					flag =false;
					 disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
         
         
		}
		
		return datas;
	}
	
	public void readDateBlock() throws IOException{
		Map<String,Object> date = new HashMap<String,Object>();
		boolean flag = true;
		while(flag){
			try {
				int identity = input.readByte();
				int length = input.readInt();
				short code = input.readShort();
				System.out.println("from service code="+code);
				date.put("identity", identity);
				date.put("length", length);
				date.put("code", code);
				analyse(code,date);
				System.out.println("收到消息："+date);
				list.add(date);
			}catch (EOFException e) {
				flag = false;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void cleanDate(){
		datas.clear();
	}
	
	private void analyse(short code, Map<String, Object> datas) throws IOException {
		if(code==0){
			short state = input.readShort();
			datas.put("state", state);
			System.out.println("注册返回state="+state);
		}else if(code==100){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] bytes = new byte[6];
			 input.read(bytes);
			 datas.put("time", bytes);
			 short dataLength =	input.readShort();
		     byte[] databuffer=new byte[dataLength];
		     input.read(databuffer);
		     String data = new String(databuffer,"UTF-8");
		     datas.put("data", data);
			
		}else if(code==20){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			System.out.println("收到正常下线消息from："+senderId+"  senderName="+senderName);
		}else if(code==3){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			 short dataLength =	input.readShort();
		     byte[] databuffer=new byte[dataLength];
		     input.read(databuffer);
		     String data = new String(databuffer,"UTF-8");
		     datas.put("data", data);
		}else if(code==2){
			
		}else if(code==101){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] timebuffer=new byte[6];
		    	input.read(timebuffer);
			 datas.put("data", timebuffer);
			 short dataLength =	input.readShort();
		     byte[] databuffer=new byte[dataLength];
		     input.read(databuffer);
		     String data = new String(databuffer,"UTF-8");
		     datas.put("content", data);
			
		}else if(code==110){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] timebuffer=new byte[6];
		    input.read(timebuffer);
			datas.put("data", timebuffer);
			 
			double amount = input.readDouble();
			datas.put("amount", amount);
			byte currency = input.readByte();
			datas.put("currency", currency);
			
		}else if(code==231){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
			
			short sdplength = input.readShort();
			byte[] sdpbuffer=new byte[sdplength];
		    input.read(sdpbuffer);
		    String sdp = new String(sdpbuffer,"utf-8");
		    datas.put("sdp", sdp);
		    byte agentLength = input.readByte();
		    byte[] agentbuffer=new byte[agentLength];
		    input.read(agentbuffer);
		    String agent = new String(agentbuffer,"utf-8");
		    datas.put("agent", agent);
			
		}else if(code==230){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
			
			short sdplength = input.readShort();
			byte[] sdpbuffer=new byte[sdplength];
		    input.read(sdpbuffer);
		    String sdp = new String(sdpbuffer,"utf-8");
		    datas.put("sdp", sdp);
		    byte agentLength = input.readByte();
		    byte[] agentbuffer=new byte[agentLength];
		    input.read(agentbuffer);
		    String agent = new String(agentbuffer,"utf-8");
		    datas.put("agent", agent);
			
		}else if(code==232){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
			
			short candidatelength = input.readShort();
			byte[] candidatebuffer=new byte[candidatelength];
		    input.read(candidatebuffer);
		    String candidate = new String(candidatebuffer,"utf-8");
		    datas.put("candidate", candidate);
		  
			
		}else if(code==221){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			
			long sessionId = input.readLong();
			datas.put("sessionId", sessionId);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			
		}else if(code==106){
			long userId=input.readLong();
			datas.put("userId", userId);
		}else if(code==223){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			
			long sessionId = input.readLong();
			datas.put("sessionId", sessionId);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
		}else if(code==111){
			//105 消息附带只有个code
		}else if(code==105){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
		}else if(code==241){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
		}else if(code==211){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
		}else if(code==141){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			long consultId = input.readLong();
			datas.put("consultId", consultId);
		}else if(code==108){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
		}else if(code==251){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==213){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==212){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==142){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			long consultId = input.readLong();
			datas.put("consultId", consultId);
		}else if(code==107){
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
		}else if(code==222){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==250){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==240){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==160){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
		}else if(code==270 || code==271){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==151){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int consultId = input.readInt();
			datas.put("consultId", consultId);
		}else if(code==150){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int consultId = input.readInt();
			datas.put("consultId", consultId);
		}else if(code==210){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==220){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==260){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==252){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==242){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			int sessionId = input.readInt();
			datas.put("sessionId", sessionId);
		}else if(code==130 || code==132 ){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
		}else if(code==140 || code==131){
			long senderId=input.readLong();
			datas.put("senderId", senderId);
			byte senderNameLength = input.readByte();
			byte[] senderNamebuffer=new byte[senderNameLength];
			input.read(senderNamebuffer);
			String senderName = new String(senderNamebuffer,"utf-8");
			datas.put("senderName", senderName);
			long receiverId=input.readLong();
			datas.put("receiverId", receiverId);
			byte receiverNameLength = input.readByte();
			byte[] receiverNamebuffer=new byte[receiverNameLength];
			input.read(receiverNamebuffer);
			String receiverName = new String(receiverNamebuffer,"utf-8");
			datas.put("receiverName", receiverName);
			byte[] date = new byte[6];
			input.read(date);
			datas.put("date", date);
			long consultId=input.readLong();
			datas.put("consultId", consultId);
		}
		
	}
	

	public void disconnect() throws IOException{
		if(socket!=null){
			socket.close();
		}
		if(output!=null){
			output.close();
		}
		if(input!=null){
			input.close();
		}
	}
	
	
	

}
