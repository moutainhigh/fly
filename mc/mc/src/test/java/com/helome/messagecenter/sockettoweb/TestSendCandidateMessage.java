package com.helome.messagecenter.sockettoweb;

import com.helome.messagecenter.client.socket.MessageMetaData;
import com.helome.messagecenter.client.socket.SocketClient;
import com.helome.messagecenter.client.utils.CommonsUtils;
import com.helome.messagecenter.client.utils.MessageWareHouse;
import com.helome.messagecenter.client.utils.UUIDUtil;
import com.helome.messagecenter.client.websocket.BlockWebSocketByStateClient;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.utils.JsonUtils;
import com.helome.messagecenter.utils.SocketThread;
import com.helome.messagecenter.utils.WebSendThread;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestSendCandidateMessage
{
  private SocketClient scoketClient;
  private Long senderId;
  private MessageMetaData messageMetaData;
  private MessageMetaData sendCandidatemessageMetaData;
  private URI uri;
  private List<String> values;
  private Long receiverId;

  @Before
  public void setUp()
    throws Exception
  {
     scoketClient = new SocketClient("127.0.0.1", CommonsUtils.getSocketPort());
     scoketClient.connect();
     senderId = Long.valueOf(UUIDUtil.longUUID());
     receiverId = Long.valueOf(UUIDUtil.longUUID());
     messageMetaData = CommonsUtils.getReportMetaData( senderId.longValue());
     sendCandidatemessageMetaData = CommonsUtils.getSendCandidateMetaData( senderId,  receiverId, 1);

     uri = URI.create(CommonsUtils.getAddress());
     values = new ArrayList<String>();
    Map<String,Object> map = new HashMap<String,Object>();

    map.put("code", Integer.valueOf(1));
    map.put("id",  receiverId);
    map.put("token", "junit:test");
    map.put("type", Integer.valueOf(1));
    String jsonStr = JsonUtils.stringify(map);
     values.add(jsonStr);
  }
  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void test() {
    try {
      SocketThread socketSender = new SocketThread( scoketClient,  messageMetaData,  sendCandidatemessageMetaData);
      BlockWebSocketByStateClient receiveClient = new BlockWebSocketByStateClient( uri,  values,  senderId.longValue());
      WebSendThread client = new WebSendThread(receiveClient);
      client.start();
      Thread.sleep(2000L);
      socketSender.start();
      Thread.sleep(5000L);
      Message msg = MessageWareHouse.getById( senderId);
      System.out.println("msg=" + msg);
      Assert.assertNotNull(msg);
    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }
  }
}