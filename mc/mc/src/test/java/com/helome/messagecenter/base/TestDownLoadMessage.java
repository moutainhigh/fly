package com.helome.messagecenter.base;

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
import com.helome.messagecenter.client.websocket.WebSocketClient;
import com.helome.messagecenter.core.Context;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.message.base.DownLoadMessage;
import com.helome.messagecenter.utils.JsonUtils;

public class TestDownLoadMessage {
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
	     msgmap.put("code", 11);
	     msgmap.put("senderId", senderId);
	     msgmap.put("receiverId", senderId);
	     msgmap.put("senderName", "download:"+senderId);
	     msgmap.put("receiverName", "download:"+senderId);
	     msgmap.put("url", "./test/test.txt");
	     msgmap.put("fileName", "test.txt");
	     msgmap.put("fileSize", senderId);
	     String msgjsonStr = JsonUtils.stringify(msgmap);
	     values.add(msgjsonStr);
	     
	}

	@After
	public void tearDown() throws Exception {
		Context.removeIdToToken(senderId);
	}

	@Test
	public void test() {
		try {
			new WebSocketClient(uri,values).run();
			Thread.sleep(3000);
			Message msg = MessageWareHouse.getById(senderId);
			Assert.assertNotNull(msg);
			DownLoadMessage downLoadMessage = (DownLoadMessage)msg;
			Assert.assertEquals("download:"+senderId,downLoadMessage.getSenderName());
			
		} catch (Exception e) {
			Assert.fail();//服务器未做处理
		}
		
	}

}
