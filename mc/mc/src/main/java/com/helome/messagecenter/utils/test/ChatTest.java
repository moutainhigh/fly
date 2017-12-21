package com.helome.messagecenter.utils.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.helome.messagecenter.utils.DateUtil;
import com.helome.messagecenter.utils.HttpClientUtil;
import com.helome.messagecenter.utils.HttpUtils;
import com.helome.messagecenter.utils.JsonUtils;

public class ChatTest {
	public static final short ORIGINAL = 1;     /** 原文 **/ 
	public static final short TRANSLATION = 2;  /** 译文 **/ 
	static long groupId = 123456;
	/**
	 * @param args
	 * @description TODO
	 * @version 1.0
	 * @author beyond.zhang
	 * @throws IOException 
	 * @update 2014-5-29 上午11:16:37
	 */
	public static void main(String[] args) throws IOException {
		
		/*try {
			addGroupMember(groupId,10019L);
		    addmoremessage();
//			testSearch();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*try {
			String key="5815AC2F8DC647088811FF96EB91D0B3";
			String str = HttpUtils.standardQuery(key, groupId+"");
			System.out.println(str);
			if(str.contains(key)){
				String result = str.split("\\[")[1].split("\\]")[0].split(key)[0].split("data")[1];
				result=result.substring(0, result.indexOf(",")).replaceAll("\"", "").replaceAll("\\\\", "").replaceAll(":", "");
				System.out.println(result);
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		queryRelationship();

	}
	
	private static void addmoremessage() throws IOException {
		
		for(int i=1;i<11;i++){
			Map<String, Object> map = new HashMap<String, Object>();
		    Map<String, Object> subMap = new HashMap<String, Object>();
		    String jsonStr = null;
		    String messageId = getUUID();
		    subMap.put("subType", "chat");
        	subMap.put("messageId", messageId);
            subMap.put("type", ORIGINAL);
            subMap.put("data", "message"+i);
            System.out.println("messageId="+messageId);
            map.put("product", "helome");
			map.put("type", 2);
			map.put("contentType", 1);
			map.put("from", 10010 + "");
			map.put("to", groupId + "");
			map.put("sendTime", DateUtil.getDate());
			map.put("content", JsonUtils.stringify(subMap));
			jsonStr = JsonUtils.stringify(map);
			insert(jsonStr);
		}
		
	}

	public static String insert(String message) throws IOException{
		HttpClientUtil h = new HttpClientUtil();
		h.open("http://172.16.1.75:6060/helome-knowledge-03/chatmessage/send","post");
		h.addParameter("message", message);
		int status = h.send();
		String result = h.getResponseBodyAsString("utf-8");
		System.out.println("result="+result);
		System.out.println("message:"+message);
	    h.close();
	    return result;
	}
  public static String getUUID(){
	  return UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""); 
  }
  
  public static String getMessageByKey(String json) throws IOException{
	  HttpClientUtil h = new HttpClientUtil();
		h.open("http://172.16.1.75:6060/helome-knowledge-03/chatmessage/standardQuery","post");
		h.addParameter("conditions", json);
		int status = h.send();
		System.out.println("json="+json);
		String result = h.getResponseBodyAsString("utf-8");
		System.out.println("result="+result);
	    h.close();
	  return result;
  }
  
  public static void testSearch() throws IOException{
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("product", "helome");
		 map.put("relevantPerson", 123456+"");
		 map.put("dateType", 2);
		 map.put("dateNumber", 2);
		 map.put("contentType", 1);
		 map.put("type", 2);
		 
		 map.put("endDate", "2014-05-30");
		 map.put("keywords", "5815AC2F8DC647088811FF96EB91D0B3");
		 map.put("isincludeGroup", true);
		 map.put("isOnlyIncludeGroup", true);
		String jsonStr = JsonUtils.stringify(map);
		String result = getMessageByKey(jsonStr);
		System.out.println("查询结果："+result); 
		 
  }
  public static String addGroupMember(long groupId,long userId) throws IOException{
	  Map<String, Object> map = new HashMap<String, Object>();
	  String relation = null;
	  map.put("product", "helome");
	  map.put("user", userId+"");
	  map.put("group", groupId+"");
	  map.put("joinTime",DateUtil.getDate());
	  relation = JsonUtils.stringify(map);
	  HttpClientUtil h = new HttpClientUtil();
	  h.open("http://172.16.1.75:6060/helome-knowledge-03/relation/group/add","post");
	  h.addParameter("relation", relation);
	  System.out.println("relation="+relation);
	  int status = h.send();
	  String result = h.getResponseBodyAsString("utf-8");
	  System.out.println("result="+result);
	    h.close();
	  return result;
	  
  }
  
  
  public static void queryRelationship() throws IOException{
	  String conditions = null;
	  Map<String, Object> map = new HashMap<String, Object>();
	  map.put("product", "helome");
	  map.put("relevantPerson", "10010");
	  conditions = JsonUtils.stringify(map);
	  
	  HttpClientUtil h = new HttpClientUtil();
	  h.open("http://172.16.1.75:6060/helome-knowledge-03/chatmessage/queryRelationship","post");
	  h.addParameter("conditions", conditions);
	  int status = h.send();
	  String result = h.getResponseBodyAsString("utf-8");
	  System.out.println("result="+result);
	   h.close();
	  
	  
  }
  
}
