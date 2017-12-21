package com.helome.messagecenter.sockettoweb;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;
import com.helome.messagecenter.client.utils.CommonsUtils;
import com.helome.messagecenter.client.utils.MessageWareHouse;
import com.helome.messagecenter.client.utils.UUIDUtil;
import com.helome.messagecenter.client.websocket.BlockWebSocketByStateClient;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.SocketThread;
import com.helome.messagecenter.utils.WebSendThread;

public class TestTransferMessage {
	private SocketClient scoketClient;
	  private Long senderId;
	  private MessageMetaData messageMetaData;
	  private MessageMetaData transferMessageMetaData;
	  private URI uri;
	  private List<String> values;
	  private Long receiverId;
	@Before
	public void setUp() throws Exception {
		this.scoketClient = new SocketClient("127.0.0.1", CommonsUtils.getSocketPort());
	    this.scoketClient.connect();
	    this.senderId = Long.valueOf(UUIDUtil.longUUID());
	    this.receiverId = Long.valueOf(UUIDUtil.longUUID());
	    this.messageMetaData = CommonsUtils.getReportMetaData(this.senderId.longValue());
	    this.transferMessageMetaData = CommonsUtils.getTransferMetaData(this.senderId, this.receiverId, "test",1);

	    this.uri = URI.create(CommonsUtils.getAddress());
	    this.values = new ArrayList();
	    Map map = new HashMap();

	    map.put("code", Integer.valueOf(1));
	    map.put("id", this.receiverId);
	    map.put("token", "junit:test");
	    map.put("type", Integer.valueOf(1));
	    String jsonStr = JsonUtils.stringify(map);
	    this.values.add(jsonStr);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
		      SocketThread socketSender = new SocketThread(this.scoketClient, this.messageMetaData, this.transferMessageMetaData);
		      BlockWebSocketByStateClient receiveClient = new BlockWebSocketByStateClient(this.uri, this.values, this.senderId.longValue());
		      WebSendThread client = new WebSendThread(receiveClient);
		      client.start();
		      Thread.sleep(2000L);
		      socketSender.start();
		      Thread.sleep(5000L);
		      Message msg = MessageWareHouse.getById(this.senderId);
		      Assert.assertNotNull(msg);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}

}
