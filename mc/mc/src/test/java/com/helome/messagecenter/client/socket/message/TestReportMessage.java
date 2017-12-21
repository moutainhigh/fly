package com.helome.messagecenter.client.socket.message;


import java.io.IOException;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.helome.messagecenter.client.socket.MessageDateInfo;
import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;
import com.helome.messagecenter.client.utils.CommonsUtils;
import com.helome.messagecenter.client.utils.UUIDUtil;

public class TestReportMessage {
    private SocketClient client ;
    private Long senderId;
    private MessageMetaData messageMetaData;
	@Before
	public void setUp() throws Exception {
		client = new SocketClient("127.0.0.1",CommonsUtils.getSocketPort());
		client.connect();
		senderId = UUIDUtil.longUUID();
	    messageMetaData = CommonsUtils.getReportMetaData(senderId);
		
	}
    @Test
	public void test(){
    	try {
			client.writeDate(messageMetaData);
			Map<String,Object> receiveDate=client.readDate();
			Object state = receiveDate.get("state");
			Assert.assertNotNull(state);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		//context 缺少根据id注销连接的接口
	}

}
