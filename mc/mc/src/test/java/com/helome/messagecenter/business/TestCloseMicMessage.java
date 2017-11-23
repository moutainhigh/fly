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
import com.helome.messagecenter.utils.JsonUtils;

public class TestCloseMicMessage {
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
	     
	     Map<String,Object> msgmap = new HashMap<String,Object>();
	     msgmap.put("code", 251);
	     msgmap.put("senderId", senderId);
	     msgmap.put("receiverId", senderId);
	     msgmap.put("senderName", "CloseMicMessage:"+senderId);
	     msgmap.put("receiverName", "CloseMicMessage:"+senderId);
	     msgmap.put("sessionId", senderId);
	     msgmap.put("date", new Date().getTime());
	     String jsonStrs = JsonUtils.stringify(msgmap);
	     values.add(jsonStrs);
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
			Context.deregister(senderId, msg);
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
