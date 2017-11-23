package com.helome.messagecenter.utils;

import com.helome.messagecenter.client.websocket.BlockWebSocketClient;

public class WebThread extends Thread
{
  private BlockWebSocketClient client;

  public WebThread(BlockWebSocketClient client)
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