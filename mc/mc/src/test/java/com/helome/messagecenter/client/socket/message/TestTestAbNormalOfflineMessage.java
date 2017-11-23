package com.helome.messagecenter.client.socket.message;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;
import com.helome.messagecenter.client.utils.CommonsUtils;
import com.helome.messagecenter.client.utils.UUIDUtil;

public class TestTestAbNormalOfflineMessage {
	private SocketClient client ;
    private Long senderId;
    private MessageMetaData messageMetaData;
    private MessageMetaData offmessageMetaData;
	@Before
	public void setUp() throws Exception {
		client = new SocketClient("127.0.0.1",8000);
		client.connect();
		senderId = UUIDUtil.longUUID();
	    messageMetaData = CommonsUtils.getReportMetaData(senderId);
	    offmessageMetaData = CommonsUtils.getOffLineMetaData(senderId,"testuser");
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		
		try {
			client.writeDate(messageMetaData);
			client.writeDate(offmessageMetaData);
			try {
				client.readDateBlock();
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean result = false;
			List<Map<String, Object>> list = client.getList();
			for(Map<String, Object> map:list){
				if(null!=map.get("senderId")){
					Long senderIds=(Long) map.get("senderId");
					if(senderIds.longValue()==senderId.longValue()){
						result = true;
					}
				}
			}
			Assert.assertTrue(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
