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

public class TestReplyMessage {
	private SocketClient client ;
    private Long senderId;
    private MessageMetaData messageMetaData;
    private MessageMetaData replymessageMetaData;
	@Before
	public void setUp() throws Exception {
		client = new SocketClient("127.0.0.1",CommonsUtils.getSocketPort());
		client.connect();
		senderId = UUIDUtil.longUUID();
	    messageMetaData = CommonsUtils.getReportMetaData(senderId);
	    replymessageMetaData = CommonsUtils.getCommonTxtMetaData(senderId,senderId,"test",101);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			client.writeDate(messageMetaData);
			client.writeDate(replymessageMetaData);
			client.readDateBlock(101);
			List<Map<String, Object>>  lists=client.getList();
			boolean result = false;
			for(Map<String, Object> map :lists){
                  if(map.containsKey("senderId")&&map.containsValue(senderId)){
                	 short code = (short) map.get("code");
                	 if(code==101){
                		 result = true; 
                	 }
                  }

			}
			Assert.assertTrue(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
