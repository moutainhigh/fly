package com.helome.messagecenter.client.socket.message.thread;

import java.io.IOException;

import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;

public class SendThread extends Thread{

	private SocketClient sendClient;
	private MessageMetaData reportMetaData;
	private MessageMetaData sendMetaData;
	
	public SendThread(SocketClient sendClient, MessageMetaData reportMetaData,
			MessageMetaData sendMetaData) {
		this.sendClient = sendClient;
		this.reportMetaData = reportMetaData;
		this.sendMetaData = sendMetaData;
	}



	@Override
	public void run() {
		try {
			sendClient.writeDate(reportMetaData);
			//sendClient.writeDate(sendMetaData);
			sendClient.readDateBlock();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() throws IOException{
		sendClient.disconnect();
	}

}
