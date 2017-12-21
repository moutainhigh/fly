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
import com.helome.messagecenter.message.base.TxtMessage;
import com.helome.messagecenter.utils.JsonUtils;

public class TestTxtMessage {
	
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
	     
	     Map<String,Object> txtmap = new HashMap<String,Object>();
	     txtmap.put("code", 3);
	     txtmap.put("senderId", senderId);
	     txtmap.put("receiverId", senderId);//现上报后自己发给自己
	     txtmap.put("data", "test:"+senderId);
	     txtmap.put("senderName", "test:"+senderId);
	     txtmap.put("receiverName", "test:"+senderId);
	     String txtjsonStr = JsonUtils.stringify(txtmap);
	     values.add(txtjsonStr);
		
	}

	@After
	public void tearDown() throws Exception {
		//删除缓存里的数据
		/*MemCachedUtil.putCommunicateNum(senderId, receiverId);
		MemCachedUtil.putCommunicateTotal(receiverId);*/
		Context.removeIdToToken(senderId);
	}

	@Test
	public void test() {
		 try {
				new WebSocketClient(uri,values).run();
				Thread.sleep(3000);
				Message msg = MessageWareHouse.getById(senderId);
				Assert.assertNotNull(msg);
				TxtMessage receivedTxtMessage = (TxtMessage)msg;
				Assert.assertEquals("test:"+senderId, receivedTxtMessage.getData());
				Context.deregister(senderId, msg);
			} catch (Exception e) {
				Assert.fail();
			}
	}

}
