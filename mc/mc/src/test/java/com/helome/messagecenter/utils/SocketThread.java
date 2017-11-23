package com.helome.messagecenter.utils;

import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;
import java.io.IOException;

public class SocketThread extends Thread
{
  private SocketClient sendClient;
  private MessageMetaData reportMetaData;
  private MessageMetaData sendMetaData;

  public SocketThread(SocketClient sendClient, MessageMetaData reportMetaData, MessageMetaData sendMetaData)
  {
    this.sendClient = sendClient;
    this.reportMetaData = reportMetaData;
    this.sendMetaData = sendMetaData;
  }

  public void run()
  {
    try {
    	if(reportMetaData!=null){
    		 this.sendClient.writeDate(reportMetaData);
    	}
      Thread.sleep(3000L);
      this.sendClient.writeDate(sendMetaData);
      this.sendClient.readDateBlock();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}