package com.helome.messagecenter.client.socket.message;

import static org.junit.Assert.*;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;
import com.helome.messagecenter.client.socket.message.thread.SendThread;
import com.helome.messagecenter.client.utils.CommonsUtils;
import com.helome.messagecenter.client.utils.UUIDUtil;

public class TestRejectChatMessage {
	private SocketClient sendClient ;
	private SocketClient receiveClient ;
	private Long senderId;
	private Long receiveId;
	private MessageMetaData sendMessageMetaData;
    private MessageMetaData receiveMessageMetaData;
    private MessageMetaData rejectChatmessageMetaData;
	@Before
	public void setUp() throws Exception {
		sendClient = new SocketClient("127.0.0.1",CommonsUtils.getSocketPort());
		sendClient.connect();
		receiveClient = new SocketClient("127.0.0.1",CommonsUtils.getSocketPort());
		receiveClient.connect();
		
		senderId = UUIDUtil.longUUID();
		receiveId = UUIDUtil.longUUID();
		sendMessageMetaData = CommonsUtils.getReportMetaData(senderId);
		receiveMessageMetaData = CommonsUtils.getReportMetaData(receiveId);
	    rejectChatmessageMetaData = CommonsUtils.getRejectChatMetaData(receiveId);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			receiveClient.writeDate(receiveMessageMetaData);
			Thread.sleep(1000);
			SendThread sendThread= new SendThread(sendClient,sendMessageMetaData,rejectChatmessageMetaData);
			sendThread.start();
			Thread.sleep(1000);
			receiveClient.readDateBlock(107);
			List<Map<String, Object>>  lists=receiveClient.getList();
			boolean result = false;
			for(Map<String, Object> map :lists){
                  if(map.containsKey("receiverId")&&map.containsValue(receiveId)){
                	 short code = (short) map.get("code");
                	 if(code==107){
                		 result = true; 
                	 }
                  }

			}
			Assert.assertTrue(result);
			
		} catch (SocketException e) {
		//	e.printStackTrace();
		}catch(EOFException e){
			//
		}catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
