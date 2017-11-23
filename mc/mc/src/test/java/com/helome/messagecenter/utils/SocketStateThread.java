package com.helome.messagecenter.utils;

import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;
import java.io.IOException;

public class SocketStateThread extends Thread
{
  private SocketClient sendClient;
  private MessageMetaData reportMetaData;
  private int stopCode;


  public SocketStateThread(SocketClient sendClient,
		MessageMetaData reportMetaData, int stopCode) {
	this.sendClient = sendClient;
	this.reportMetaData = reportMetaData;
	this.stopCode = stopCode;
}


public void run()
  {
    try {
    	if(reportMetaData!=null){
    		 this.sendClient.writeDate(reportMetaData);
    	}
      this.sendClient.readDateBlock(stopCode);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}