package com.helome.messagecenter.utils;

import com.helome.messagecenter.client.websocket.BlockWebSocketByStateClient;

public class WebSendThread extends Thread
{
  private BlockWebSocketByStateClient client;

  public WebSendThread(BlockWebSocketByStateClient client)
  {
    this.client = client;
  }

  public void run()
  {
    try {
      this.client.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}