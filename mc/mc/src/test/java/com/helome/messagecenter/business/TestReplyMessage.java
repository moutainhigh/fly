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
import com.helome.messagecenter.message.business.ReplyMessage;
import com.helome.messagecenter.utils.JsonUtils;

public class TestReplyMessage {
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
	     
	     Map<String,Object> replytmap = new HashMap<String,Object>();
	     replytmap.put("code", 101);
	     replytmap.put("senderId", senderId);
	     replytmap.put("receiverId", senderId);
	     replytmap.put("senderName", "test:"+senderId);
	     replytmap.put("receiverName", "test:"+senderId);
	     replytmap.put("content", "reply:"+senderId);
	     replytmap.put("date", new Date());
	     String replyjsonStr = JsonUtils.stringify(replytmap);
	     values.add(replyjsonStr);
	     
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			new WebSocketClient(uri,values).run();
			Thread.sleep(3000);
			Message msg = MessageWareHouse.getById(senderId);
			Assert.assertNotNull(msg);
			ReplyMessage receivedReplyMessage = (ReplyMessage)msg;
			Assert.assertEquals("reply:"+senderId, receivedReplyMessage.getContent());
			Context.deregister(senderId, msg);
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
