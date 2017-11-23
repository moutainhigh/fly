package com.helome.messagecenter.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helome.messagecenter.message.Message;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

public class MemCachedUtil {
	 private final static Logger logger = LoggerFactory.getLogger("MemCachedUtil.class");
     private static String address  = PropertiesUtils.readValue("memcached.address");
	 private static MemcachedClient c; 
	 private static String MemPublicKey = PropertiesUtils.readValue("memcached.namespace");
	 public static synchronized MemcachedClient getInstance(){
		 if(c==null){ 
			 try {
				c=new MemcachedClient(AddrUtil.getAddresses(address));
			} catch (Exception e) {
				logger.error("Memcached 连接异常："+e);
			}
		 }
		 return c;
	 }
	 
	 public static void putSysMessage(Long userId,Message message){
		 String key = MemPublicKey+"sysMessage"+String.valueOf(userId);
		 MemcachedClient client = getInstance();
		 List<Message> messages = (List<Message>)client.get(key);
		 if(messages==null){
			 messages = new ArrayList<Message>();
			 messages.add(message);
			 client.add(key, 0, messages);
		 }else{
			 messages.add(message);
			 client.add(key, 0, messages);
		 }
	 }
	 
	 public static void putSysMessageNum(Long userId){
		 String key = MemPublicKey+"sysMessageNum"+String.valueOf(userId);
		 MemcachedClient client = getInstance();
		 Long  sysMessageNum = (Long)client.get(key);
		 if(sysMessageNum==null||sysMessageNum==0){
			 sysMessageNum = 1L;
			 client.add(key, 0, sysMessageNum);
		 }else{
			 sysMessageNum = sysMessageNum + 1;
			 client.add(key, 0, sysMessageNum);
		 }
	 }
	 
	 public static void putCommunicateMessage(Long userId,Message message){
		 String key = MemPublicKey+"communicate"+String.valueOf(userId);
		 MemcachedClient client = getInstance();
		 List<Message> messages = (List<Message>)client.get(key);
		 if(messages==null){
			 messages = new ArrayList<Message>();
			 messages.add(message);
			 client.add(key, 0, messages);
		 }else{
			 messages.add(message);
			 client.add(key, 0, messages);
		 }
	 }
	 
	 public static void putCommunicateNum(Long sendId,Long receiveId){
		 String key = MemPublicKey+"communicateNum"+String.valueOf(sendId)+"to"+String.valueOf(receiveId);
		 MemcachedClient client = getInstance();
		 Long  sysMessageNum = (Long)client.get(key);
		 logger.info("************  key:"+key+"  Num :"+sysMessageNum);
		 if(sysMessageNum==null){
			 sysMessageNum = 1L;
			 client.add(key, 0, sysMessageNum);
		 }else{
			 sysMessageNum = sysMessageNum + 1;
			 client.replace(key, 0, sysMessageNum);
		 }
	 }
	 
	 public static void putCommunicateTotal(Long userId){
		 String key = MemPublicKey+"communicateTotal"+String.valueOf(userId);
		 MemcachedClient client = getInstance();
		 Long  communicateTotal = (Long)client.get(key);
		 logger.info("communicateTotal:"+key+"  Num :"+communicateTotal);
		 if(communicateTotal==null){
			 communicateTotal = 1L;
			 client.add(key, 0, communicateTotal);
		 }else{
			 communicateTotal = communicateTotal + 1;
			 client.replace(key, 0, communicateTotal);
		 }
	 }
	 
	 public static void setCommunicateNum(Long sendId,Long receiveId,Long num){
		 String key = MemPublicKey+"communicateNum"+String.valueOf(sendId)+"to"+String.valueOf(receiveId);
		 logger.info("set  Num   key:"+key+"  Num :"+num);
		 MemcachedClient client = getInstance();
		 client.replace(key, 0, num);
	 }
	 
	 public static void setInChat(Long sendId,Long receiveId,Long num){
		 String key = MemPublicKey+"inChat"+String.valueOf(sendId)+"to"+String.valueOf(receiveId);
		 logger.info("set  inChat   key:"+key+"  Num :"+num);
		 MemcachedClient client = getInstance();
		 Long nowNum = (Long)client.get(key);
		 logger.info("key("+key+");id("+nowNum+")nowNum");
		 if(nowNum==null){
			 OperationFuture future = client.add(key, 0, num);
			 logger.info("add key("+key+");id("+nowNum+")nowNum"+"  result :"+future.getStatus()+future.toString());
		 }else{
			 OperationFuture future = client.replace(key, 0, num);
			 logger.info("replace key("+key+");id("+nowNum+")nowNum"+"  result :"+future.getStatus()+future.toString());
		 }
	 }
	 
	 public static void setCommunicateTotal(Long sendId,Long receiveId){
		 try{
		 String key = MemPublicKey+"communicateTotal"+String.valueOf(sendId);
		 String key2 = MemPublicKey+"communicateNum"+String.valueOf(receiveId)+"to"+String.valueOf(sendId);
		 MemcachedClient client = getInstance();
		 Long total = (Long)client.get(key);
		 Long currentNum = (Long)client.get(key2);
		 if(total!=null&&currentNum!=null){
		 Long nowTotal = total - currentNum;
		 client.replace(key, 0, nowTotal);
		 }
		 }catch(Exception e){
			 
		 }
	 }
	 
	 public static void setOnLine(Long userId,String num){
		 String key = MemPublicKey+"MC.user.id"+String.valueOf(userId);
		 logger.info("set  userOnLine   key:"+key+"  Num :"+num);
		 MemcachedClient client = getInstance();
		 client.replace(key, 0, num);
	 }

	 public static void main(String[] args) {
		 MemcachedClient client = getInstance();
		 String key = "test";
		 client.add(key, 300000, "8888");
		 Object obj= client.get(key);
		 System.out.println("-----------"+obj);
		 
	}

}
