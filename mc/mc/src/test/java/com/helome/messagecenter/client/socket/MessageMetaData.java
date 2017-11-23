package com.helome.messagecenter.client.socket;

import java.util.LinkedList;

public class MessageMetaData {
	
	LinkedList<MessageDateInfo> dataQueue ;

	public MessageMetaData() {
		dataQueue =  new LinkedList<MessageDateInfo>();
	}

	public void add(MessageDateInfo messageDateInfo){
		dataQueue.add(messageDateInfo);
	}
	
	public void remove(MessageDateInfo messageDateInfo){
		dataQueue.remove(messageDateInfo);
	}
	
	public void clean(){
		dataQueue.clear();
	}

	public LinkedList<MessageDateInfo> getDataQueue() {
		return dataQueue;
	}
	
}
