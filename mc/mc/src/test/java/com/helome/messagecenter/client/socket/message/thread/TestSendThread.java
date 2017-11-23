package com.helome.messagecenter.client.socket.message.thread;

import java.io.IOException;

import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;
import com.helome.messagecenter.client.utils.CommonsUtils;

public class TestSendThread extends Thread{

	private SocketClient sendClient;
	private MessageMetaData sendMetaData;
	private Long senderId;
    private Long receiverId;



	public TestSendThread(SocketClient sendClient,
			MessageMetaData sendMetaData, Long senderId, Long receiverId) {
		super();
		this.sendClient = sendClient;
		this.sendMetaData = sendMetaData;
		this.senderId = senderId;
		this.receiverId = receiverId;
	}

	@Override
	public void run() {
		try {
			for(int i=0;i<1000000;i++){
				try {
					System.out.println("-----num "+i);
					sendMetaData =CommonsUtils.getTxtMetaData(senderId,receiverId,"test:"+i);
					sendClient.writeDate(sendMetaData);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() throws IOException{
		sendClient.disconnect();
	}

}
