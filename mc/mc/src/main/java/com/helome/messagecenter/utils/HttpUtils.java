package com.helome.messagecenter.utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.ClientCookieEncoder;
import io.netty.handler.codec.http.DefaultCookie;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.ErrorDataEncoderException;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

public class HttpUtils {
	private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	private static String chatMessageClientSendUrl =PropertiesUtils.readValue("chatMessage.client.sendUrl"); ;
	private static String verifyTokenUrl =PropertiesUtils.readValue("verifyToken.url");
	private static String addMember =PropertiesUtils.readValue("chatMessage.client.groupMemberAdd");
	private static String update =PropertiesUtils.readValue("chatMessage.client.groupMemberUpdate");
	private static String standardQuery =PropertiesUtils.readValue("chatMessage.client.standardQuery");
    private static BlockingQueue<String> msgRecords = new LinkedBlockingQueue<String>();
	
	public static boolean validateToken(String token) {
		boolean flag = true;
		try {
			URI url = new URI(verifyTokenUrl);
			flag = validateToken(url, token);
		} catch (Exception e) {
			logger.error("验证token出错",e);
			flag = false;
		}
		return flag;
	}
	
	public static boolean validateToken(URI uri, String token) {
		boolean flag = false;
		try {
			HttpClientUtil h = new HttpClientUtil();
			h.open(uri.toString(),"post");
			h.addParameter("token", token);
			@SuppressWarnings("unused")
			int status = h.send();
			String result = h.getResponseBodyAsString("utf-8");
			logger.info("验证token{}返回的结果是{}",token,result);
			h.close();
			if(result.contains("200")){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("验证token出错",e);
		}
		return flag;
	}
	
	/**
	 * 发送消息到聊天记录
	 * paramper 是json 格式
	 * {'product': 'helome','type': 1, 'from': '1', 'to': '2', 
	 * 'sendTime': '2014-03-13 13:15:20.223', 
	 * 'content': '今天晚上要加班'}
	 * @throws URISyntaxException 
	 */
	public static String sendChatRequest( String paramper) throws URISyntaxException {
		String result = null;
		try {
		HttpClientUtil h = new HttpClientUtil();
		h.open(chatMessageClientSendUrl,"post");
		h.addParameter("message", paramper);
		@SuppressWarnings("unused")
		int status = h.send();
		result = h.getResponseBodyAsString("utf-8");
		logger.info("保存聊天记录{}到知识库返回的结果是{}",paramper,result);
		h.close();
		} catch (Exception e) {
			logger.error("保存聊天记录到知识库出错：",e);
		}
		return result;
	}
	/**
	 * 
	 * @param product  产品名称（"helome"必填）
	 * @param user     参与聊天的人A（必填）
	 * @param group    群账户（必填）
	 * @param joinTime 当前用户加入群组的时间（必填） 格式：2014-04-01 12:13:14.555
	 * @description    添加群成员
	 * @author beyond.zhang
	 * @throws IOException 
	 * @update 2014-6-3 上午10:23:21
	 */
	@SuppressWarnings("unused")
	public static String addMember(String product,String user,String group,String joinTime) throws Exception{
		 Map<String, Object> map = new HashMap<String, Object>();
		  String relation = null;
		  map.put("product", product);
		  map.put("user", user);
		  map.put("group", group);
		  map.put("joinTime",joinTime);
		  relation = JsonUtils.stringify(map);
		  HttpClientUtil h = new HttpClientUtil();
		  h.open(addMember,"post");
		  h.addParameter("relation", relation);
		  int status = h.send();
		  String result = h.getResponseBodyAsString("utf-8");
		  h.close();
		return result;
	}
	/**
	 * 
	 * @param product  产品名称（"helome"必填）
	 * @param user     参与聊天的人A（必填）
	 * @param group    群账户（必填）
	 * @param quitTime 当前用户加退出群组的时间（必填） 格式：2014-04-01 12:13:14.555
	 * @description    添加群成员
	 * @author beyond.zhang
	 * @throws IOException 
	 * @update 2014-6-3 上午10:23:21
	 */
	public static String removeMember(String product,String user,String group,String quitTime) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		  String relation = null;
		  map.put("product", product);
		  map.put("user", user);
		  map.put("group", group);
		  map.put("quitTime",quitTime);
		  relation = JsonUtils.stringify(map);
		  HttpClientUtil h = new HttpClientUtil();
		  h.open(update,"post");
		  h.addParameter("relation", relation);
		  int status = h.send();
		  String result = h.getResponseBodyAsString("utf-8");
		  h.close();
		return result;
	}

	public static String standardQuery(String parentId, String groupId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		 map.put("product", "helome");
		 map.put("relevantPerson", groupId);
		 map.put("dateType", 2);//单位： 1-天，2-月，3-年
		 map.put("dateNumber", 2);
		 map.put("contentType", 1);
		 map.put("type", 2);
		 
		 map.put("endDate", DateUtil.getSimDate());
		 map.put("keywords", parentId);
		 map.put("isincludeGroup", true);
		 map.put("isOnlyIncludeGroup", true);
		 String jsonStr = JsonUtils.stringify(map);
		 HttpClientUtil h = new HttpClientUtil();
		 h.open(standardQuery,"post");
		 h.addParameter("conditions", jsonStr);
		 int status = h.send();
		 String result = h.getResponseBodyAsString("utf-8");
		 h.close();
		return result;
	}
	
	public static void putMsgRecords(String recordes){
		try {
			msgRecords.put(recordes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static String takeMsgRecords(){
		String msg = null;
		try {
			msg = msgRecords.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return msg;
	}
}
