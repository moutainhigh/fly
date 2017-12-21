package com.helome.messagecenter.business;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
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
import com.helome.messagecenter.client.websocket.WebSocketClient;
import com.helome.messagecenter.core.Context;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.message.business.ChatMessage;
import com.helome.messagecenter.utils.JsonUtils;

public class TestChatMessage {
	private URI uri;
	private List<String> values ;
	private Long senderId;
	@Before
	public void setUp() throws Exception {
		uri = URI.create(CommonsUtils.getAddress());
		 values = new ArrayList<String>();
		 Map<String,Object> map = new HashMap<String,Object>();
		 senderId = UUIDUtil.longUUID();
		 map.put("code", 1);
		 map.put("id", senderId);
		 map.put("token", "junit:test");
		 map.put("type", 1);
		 String jsonStr = JsonUtils.stringify(map);
	     values.add(jsonStr);
	     
	     Map<String,Object> chatmap = new HashMap<String,Object>();
	     chatmap.put("code", 105);
	     chatmap.put("senderId", senderId);
	     chatmap.put("receiverId", senderId);
	     chatmap.put("senderName", "chat:"+senderId);
	     chatmap.put("receiverName", "chat:"+senderId);
	     chatmap.put("date", new Date());
	     String chatjsonStr = JsonUtils.stringify(chatmap);
	     values.add(chatjsonStr);
	}

	@After
	public void tearDown() throws Exception {
		//删除缓存里的数据
		/*MemCachedUtil.setCommunicateTotal(senderId ,receiverId);
		MemCachedUtil.setCommunicateNum(receiverId,senderId,0L);
		MemCachedUtil.setInChat(senderId,receiverId,1L);*/
	}

	@Test
	public void test() {
		try {
			new WebSocketClient(uri,values).run();
			Thread.sleep(3000);
			Message msg = MessageWareHouse.getById(senderId);
			Assert.assertNotNull(msg);
			ChatMessage receivedChatMessage = (ChatMessage)msg;
			Assert.assertEquals("chat:"+senderId, receivedChatMessage.getReceiverName());
			Context.deregister(senderId, msg);
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
