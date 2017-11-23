package com.helome.messagecenter.business;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.helome.messagecenter.client.utils.CommonsUtils;
import com.helome.messagecenter.client.utils.MessageWareHouse;
import com.helome.messagecenter.client.utils.UUIDUtil;
import com.helome.messagecenter.client.websocket.BlockWebSocketClient;
import com.helome.messagecenter.core.Context;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;

public class TestAcceptChatMessage {
	private URI uri;
	private List<String> values ;
	private Long senderId;
	private Long userId;
	private List<String> receiveValues ;
	@Before
	public void setUp() throws Exception {
		uri = URI.create(CommonsUtils.getAddress());
		 values = new ArrayList<String>();
		 Map<String,Object> map = new HashMap<String,Object>();
		 senderId = UUIDUtil.longUUID();
		 userId = UUIDUtil.longUUID();
		 System.out.println("senderId ="+senderId);
		 System.out.println("userId ="+userId);
		 map.put("code", 1);
		 map.put("id", senderId);
		 map.put("token", "junit:test");
		 map.put("type", 1);
		 String jsonStr = JsonUtils.stringify(map);
	     values.add(jsonStr);
	     
	     Map<String,Object> msgmap = new HashMap<String,Object>();
	     msgmap.put("code", 106);
	     msgmap.put("userId", userId);
	     
	     String msgjsonStr = JsonUtils.stringify(msgmap);
	     values.add(msgjsonStr);
	     
	     receiveValues = new ArrayList<String>();
	     Map<String,Object> maps = new HashMap<String,Object>();
	     maps.put("code", 1);
	     maps.put("id", userId);
	     maps.put("token", "junit:test");
	     maps.put("type", 1);
		 String jsonStrs = JsonUtils.stringify(maps);
		 receiveValues.add(jsonStrs);
		 
		 
		 
		 
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		try {
			BlockWebSocketClient receiveClient =	new BlockWebSocketClient(uri,receiveValues);
			receiveClient.run();
			Thread.sleep(3000);
			BlockWebSocketClient sendClient =	new BlockWebSocketClient(uri,values);
			sendClient.run();
			System.out.println("senderId="+senderId);
			Message msg = MessageWareHouse.getById(userId);
			Assert.assertNotNull(msg);
			Context.deregister(senderId, msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
