package com.helome.messagecenter.business;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.helome.messagecenter.client.utils.CommonsUtils;
import com.helome.messagecenter.client.utils.UUIDUtil;
import com.helome.messagecenter.utils.JsonUtils;

public class TestNotAvailable {
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
	     
	     Map<String,Object> commentmap = new HashMap<String,Object>();
	     commentmap.put("code", 271);
	     commentmap.put("userId", senderId);
	     commentmap.put("inviteeId", senderId);
	     commentmap.put("userName", "NotAvailable:"+senderId);
	     commentmap.put("inviteeName", "NotAvailable:"+senderId);
	     commentmap.put("sessionId",senderId);
	     commentmap.put("date", new Date().getTime());
	     String commentjsonStr = JsonUtils.stringify(commentmap);
	     values.add(commentjsonStr);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test() {
		/*try {
			new WebSocketClient(uri,values).run();
			Thread.sleep(3000);
			Message msg = MessageWareHouse.getById(senderId);
			Assert.assertNotNull(msg);
		} catch (Exception e) {
			Assert.fail();
		}*/
		Assert.fail();// 服务器暂时未做任何处理
	}

}
