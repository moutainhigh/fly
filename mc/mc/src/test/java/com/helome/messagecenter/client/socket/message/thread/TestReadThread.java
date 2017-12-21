package com.helome.messagecenter.client.socket.message.thread;

import java.io.IOException;

import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;

public class TestReadThread extends Thread{

	private SocketClient sendClient;
	
	public TestReadThread(SocketClient sendClient) {
		this.sendClient = sendClient;
		
		
	}



	@Override
	public void run() {
		try {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendClient.readDateBlock(7);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() throws IOException{
		sendClient.disconnect();
	}

}
