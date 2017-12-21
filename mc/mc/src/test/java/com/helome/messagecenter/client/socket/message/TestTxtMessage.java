package com.helome.messagecenter.client.socket.message;

import java.io.IOException;
import java.net.SocketException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;
import com.helome.messagecenter.client.socket.message.thread.SendThread;
import com.helome.messagecenter.client.socket.message.thread.TestSendThread;
import com.helome.messagecenter.client.utils.CommonsUtils;
import com.helome.messagecenter.client.utils.UUIDUtil;

public class TestTxtMessage {
	private SocketClient client ;
    private Long senderId;
    private Long receiverId;
    private SocketClient receiveClient ;
    private MessageMetaData receivemessageMetaData;
    
    private MessageMetaData messageMetaData;
    private MessageMetaData txtmessageMetaData;
	@Before
	public void setUp() throws Exception {
		client = new SocketClient("172.16.4.222",8000);
		client.connect();
		
		receiveClient = new SocketClient("172.16.4.222",8000);
		receiveClient.connect();
		
		senderId = UUIDUtil.absLongUUID();
		receiverId = UUIDUtil.absLongUUID();
	    messageMetaData = CommonsUtils.getReportMetaData(senderId);
	    txtmessageMetaData = CommonsUtils.getTxtMetaData(senderId,receiverId,"test");
	    receivemessageMetaData= CommonsUtils.getReportMetaData(receiverId);
	}
    @Test
	public void test(){
    	try {
    		SendThread readThread = new SendThread(receiveClient,receivemessageMetaData,null);
    		readThread.start();
    		client.writeDate(messageMetaData);
			TestSendThread thread = new TestSendThread(client,txtmessageMetaData,senderId,receiverId);
			thread.start();
			
			System.out.println("---here2---");
			while(true){
				try {
					thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
		//	e.printStackTrace();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		//context 缺少根据id注销连接的接口
	}

}
