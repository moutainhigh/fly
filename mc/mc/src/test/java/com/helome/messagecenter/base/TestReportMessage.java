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
import com.helome.messagecenter.utils.JsonUtils;

public class TestReportMessage {

	private URI uri;
	private List<String> values ;
	private Long senderId;
	@Before
	public void setUp() throws Exception {
	/*	InetAddress inetAddress = InetAddress.getLocalHost();
		String ip = inetAddress.getHostAddress();
	//	String port = PropertiesUtils.readValue("mc.websocket.port");
		String port = 9096+"";
		//  ws://172.16.2.235:8001
		String wbpath = "ws://"+ip+":"+port;*/
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
		
	}
	
	
	@Test
	public void test() {
	        try {
				new WebSocketClient(uri,values).run();
				Thread.sleep(3000);
				Message msg = MessageWareHouse.getById(0l);
				Assert.assertNotNull(msg);
				Context.deregister(senderId, msg);
			} catch (Exception e) {
				Assert.fail();
			}
	}

	@After
	public void tearDown() throws Exception {
		//删除缓存里的数据
		/*MemCachedUtil.putCommunicateNum(senderId, receiverId);
		MemCachedUtil.putCommunicateTotal(receiverId);*/
		
	}

}
