package com.helome.messagecenter.base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.helome.messagecenter.client.utils.CommonsUtils;
import com.helome.messagecenter.client.utils.MessageWareHouse;
import com.helome.messagecenter.client.utils.UUIDUtil;
import com.helome.messagecenter.client.websocket.BinaryWebSocketClient;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.message.base.TxtMessage;
import com.helome.messagecenter.utils.JsonUtils;

public class TestCacheBinaryMessage {

	private URI uri;
	private List<String> values ;
	private Long senderId;

	@Before
	public void setUp() throws Exception {
		 uri = URI.create(CommonsUtils.getAddress());
		 values = new ArrayList<String>();
		 Map<String,Object> map = new HashMap<String,Object>();
		 senderId = UUIDUtil.longUUID();
		 map.put("code", 1);
		 map.put("id", senderId);
		 map.put("token", "junit:test");
		 map.put("type", 1);
		 String jsonStr = JsonUtils.stringify(map);
	     values.add(jsonStr);
	     
	    String path = TestCacheBinaryMessage.class.getResource("").getPath();
	     System.out.println(path);
	     File file = new File(path+"文档.txt");
	    // File file = new File(path+"beyond.flv");
	     System.out.println(file.exists());
	     String fileName= file.getName();
	     for(int k=0;k<1;k++){
	    	     FileInputStream fis = null;  
		         ByteArrayOutputStream ops = null;
		         byte[] temp = new byte[32768];  
		         Long time = new Date().getTime();
		         try {
					fis = new FileInputStream(file);
					 int n;
					 int i = 0;
					 long totalPackage = file.length()/32768;
						 while ((n = fis.read(temp,0,temp.length)) != -1) { 
							 Thread.sleep(2);
							 int finish = 0;
							 if(totalPackage-1 == i || totalPackage==0){
								 finish=1;
							 }
							 String str = new String(Arrays.copyOfRange(temp, 0, n),"iso8859-1");
							 JsonNode node = new TextNode(str);
							 Map<String, Object> filemap = new HashMap<String, Object>();
							 filemap.put("code", 10);
							 filemap.put("senderId", senderId);
							 filemap.put("receiverId", senderId);
							 filemap.put("data", node);
							 filemap.put("senderName", "test");
							 filemap.put("receiverName", "test");
							 filemap.put("fileName", fileName);   
							 filemap.put("dateTime", time);   
							 filemap.put("finish", finish);
							 filemap.put("packageNo", i);
							 
						 		String filejsonStr = JsonUtils.stringify(filemap);
						 		values.add(filejsonStr);
						 		i++;
						 		System.out.println("i="+i+"  ; finish="+finish);
						 }	 
					 
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if (ops != null) {  
						 ops.close();
					}
					if (fis != null) {
						 fis.close();
					}
				}
	    	 
	     }
	     
	     
	     
		
	}

	@After
	public void tearDown() throws Exception {
		//删除缓存里的数据
		/*MemCachedUtil.putCommunicateNum(senderId, receiverId);
		MemCachedUtil.putCommunicateTotal(receiverId);*/
		//Context.removeIdToToken(senderId);
	}

	@Test
	public void test() {
		 try {
				new BinaryWebSocketClient(uri,values).run();
				Thread.sleep(3000);
				Message msg = MessageWareHouse.getById(senderId);
				Assert.assertNotNull(msg);
			} catch (Exception e) {
				Assert.fail();
			}
	}

}
