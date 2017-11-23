package com.helome.messagecenter.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.message.base.CacheBinaryMessage;
import com.helome.messagecenter.message.base.DownLoadMessage;
import com.helome.messagecenter.message.business.MultimediaDownLoadMessage;
import com.helome.messagecenter.message.business.MultimediaMessage;
import com.helome.messagecenter.message.business.PictureDownLoadMessage;
import com.helome.messagecenter.message.business.PictureMessage;
import com.helome.messagecenter.utils.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
/**
 * @title CacheBinaryWriter1.java
 * @description 文件数据块写入线程
 */
public class CacheBinaryWriter1 extends Thread {
	private final static Logger logger = LoggerFactory
			.getLogger(CacheBinaryWriter1.class);

	public void run() {
		String filePath = PropertiesUtils.readValue("upload.path");
		while (true) {
			try {
				Long key = Context.fileMessageQueue.take();
				if (key != null) {
					logger.info("新的可用key {}",key);
					long all = new Date().getTime();
					ConcurrentMap<Integer, Message> entity = Context.fileMessageData.get(key);
					if (entity != null&&entity.size()>0) {
						 writeDataBlock(filePath,  key, entity);
					}
					logger.info("单次写key:{}对应文件耗时{}",key,new Date().getTime()-all);
				}
			} catch (Exception e) {
				logger.error("",e);
			}
		}
	}
	//写入数据块
	private void writeDataBlock(String filePath, Long key,
			ConcurrentMap<Integer, Message> entity)
			throws FileNotFoundException, IOException, JsonProcessingException,
			URISyntaxException {
			Message msg = entity.get(0);
			if(msg.getCode()==CacheBinaryMessage.CODE){
				boolean resule = writeCacheBinary(filePath,entity,key);
				if(!resule){
				}
			}else if(msg.getCode()==MultimediaMessage.CODE){
				boolean resule = writeMultimedia(filePath,entity,key);
				if(!resule){
				}
			}else if(msg.getCode()==PictureMessage.CODE){
				boolean resule = writePicture(filePath,entity,key);
				if(!resule){
				}
		}
	}
	
	
	private String createChatRecode(Long fileSize, PictureMessage message,
			String saveFileName) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("subType", "file");
		subMap.put("url",PropertiesUtils.readValue("download.path.cdn")
						+ message.getSenderId()
						+ File.separator
						+ message.getReceiverId()
						+ "_" + saveFileName);
		subMap.put("fileSize", fileSize);
		subMap.put("fileName",
				message.getFileName());
		map.put("product", "helome");
		map.put("type", 1);
		map.put("contentType", 1); 
		map.put("from", message.getSenderId() + "");
		map.put("to", message.getReceiverId() + "");
		map.put("sendTime", DateUtil.getDate());
		map.put("content", JsonUtils.stringify(subMap));
		String jsonStr = JsonUtils.stringify(map);
		return jsonStr;
	}
	private void setSystemOrChatMegNum(PictureMessage message) {
		MemCachedUtil.putCommunicateNum(message.getSenderId(),message.getReceiverId());
		MemCachedUtil.putCommunicateTotal(message.getReceiverId());
	}
	private void sendsDownLoadMes(PictureMessage message,
			PictureDownLoadMessage dmessage, List<Endpoint> toEndpoints,
			boolean isInChat) {
		synchronized (toEndpoints) {
			for (Endpoint toEndpoint : toEndpoints) {
				if (toEndpoint instanceof WebRTCEndpoint) {
					Long id = Context.ENDPOINTS_TO_PEER
							.get(toEndpoint);
					logger.info("通知消息用户{}下载");
					if (message.getSenderId() == id) {
						isInChat = true;
					}
				}
			}
			if (!isInChat) {
				setSystemOrChatMegNum(message);
			}
			for (Endpoint toEndpoint : toEndpoints) {
				dmessage.setEndpoint(toEndpoint);
				if (toEndpoint instanceof SocketEndpoint) {
					toEndpoint.getContext().channel().writeAndFlush(dmessage.toBinary());
				} else {
					toEndpoint.getContext().channel().writeAndFlush(new TextWebSocketFrame(dmessage.toJson()));
				}
			}
		}
		
	}
	
	//发送文件下载提醒消息
	private void sendsDownLoadMes(CacheBinaryMessage message,
			DownLoadMessage dmessage, List<Endpoint> toEndpoints,
			boolean isInChat) {
		synchronized (toEndpoints) {
			for (Endpoint toEndpoint : toEndpoints) {
				if (toEndpoint instanceof WebRTCEndpoint) {
					Long id = Context.ENDPOINTS_TO_PEER
							.get(toEndpoint);
					logger.info("通知消息用户{}下载");
					if (message.getSenderId() == id) {
						isInChat = true;
					}
				}
			}
			if (!isInChat) {
				setSystemOrChatMegNum(message);
			}
			for (Endpoint toEndpoint : toEndpoints) {
				dmessage.setEndpoint(toEndpoint);
				if (toEndpoint instanceof SocketEndpoint) {
					toEndpoint.getContext().channel().writeAndFlush(dmessage.toBinary());
				} else {
					toEndpoint.getContext().channel().writeAndFlush(new TextWebSocketFrame(dmessage.toJson()));
				}
			}
		}
	}
	//不在线 消息数目存缓存
	private void setSystemOrChatMegNum(CacheBinaryMessage message) {
		MemCachedUtil.putCommunicateNum(message.getSenderId(),message.getReceiverId());
		MemCachedUtil.putCommunicateTotal(message.getReceiverId());
	}
	//创建文件消息的聊天记录格式
	private String createChatRecode(Long fileSize, CacheBinaryMessage message,
			String saveFileName) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("subType", "file");
		subMap.put("url",PropertiesUtils.readValue("download.path.cdn")
						+ message.getSenderId()
						+ File.separator
						+ message.getReceiverId()
						+ "_" + saveFileName);
		subMap.put("fileSize", fileSize);
		subMap.put("fileName",
				message.getFileName());
		map.put("product", "helome");
		map.put("type", 1);
		map.put("contentType", 1); 
		map.put("from", message.getSenderId() + "");
		map.put("to", message.getReceiverId() + "");
		map.put("sendTime", DateUtil.getDate());
		map.put("content", JsonUtils.stringify(subMap));
		String jsonStr = JsonUtils.stringify(map);
		return jsonStr;
	}
  
	
	private boolean writeCacheBinary(String filePath,ConcurrentMap<Integer, Message> entity,Long key) throws IOException, URISyntaxException{
         boolean flag = true;
         CacheBinaryMessage message0 = ((CacheBinaryMessage)entity.get(0));
         if(message0!=null){
         File file = new File(filePath+ message0.getSenderId());
			if (!file.exists()) {
				file.mkdir();
			}
			if (message0.getFileName() == null|| "".equals(message0.getFileName())|| !message0.getFileName().contains(".")) {
				flag =false;
				return flag;
			}
			String saveFileName = key+ "_"	+ message0.getFileName();
			long start = new Date().getTime();
			Long fileSize = 0L;
			FileOutputStream fos = new FileOutputStream(filePath	+ message0.getSenderId()	+ File.separator+ message0.getReceiverId() + "_"+ saveFileName, true);
			for(int i=0;i<entity.size();i++){
        	 CacheBinaryMessage message = (CacheBinaryMessage)entity.get(i);
		fileSize = fileSize + Long.valueOf(message.getData().length);
		if (message != null) {
		
			logger.debug("开始写数据块，大小{}.",message.getData().length);
			fos.write(message.getData());
			logger.debug("写数据块耗时{}ms.",new Date().getTime()-start);
			if (message.getFinish() == 1) {//文件读取完成
				DownLoadMessage dmessage = createDownLoadMessage(fileSize, message, saveFileName);
				List<Endpoint> toEndpoints = Context.getEndpoints(message.getReceiverId());
				boolean isInChat = false;
				if (toEndpoints != null) {
					sendsDownLoadMes(message, dmessage,	toEndpoints, isInChat);
				} else {
					setSystemOrChatMegNum(message);
				}
				Context.fileMessageData.remove(key);
				String jsonStr = createChatRecode(fileSize,	message, saveFileName);
				String response = HttpUtils.sendChatRequest(jsonStr);
				logger.info("保存文件记录{},结果{}",jsonStr,response);
			}
		}
		
		}

			try{
			fos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
         }
		return flag;
	}
	
	private boolean writeMultimedia(String filePath,ConcurrentMap<Integer, Message> entity, Long key) throws IOException, URISyntaxException {
		  boolean flag = true;
		  MultimediaMessage message0 = ((MultimediaMessage)entity.get(0));
	         if(message0!=null){
	         File file = new File(filePath+ message0.getSenderId());
				if (!file.exists()) {
					file.mkdir();
				}
				if (message0.getFileName() == null|| "".equals(message0.getFileName())|| !message0.getFileName().contains(".")) {
					flag =false;
					return flag;
				}
				String saveFileName = key+ "_"	+ message0.getFileName();
				long start = new Date().getTime();
				FileOutputStream fos = new FileOutputStream(filePath	+ message0.getSenderId()	+ File.separator+ message0.getReceiverId() + "_"+ saveFileName, true);
				 long size = 0L;
				for(int i=0;i<entity.size();i++){
	        	 MultimediaMessage message = (MultimediaMessage)entity.get(i);
			Long fileSize = Long.valueOf(message.getData().length);
			size+=fileSize;
			if (message != null) {
			
				logger.debug("开始写数据块，大小{}.",message.getData().length);
				fos.write(message.getData());
				logger.debug("写数据块耗时{}ms.",new Date().getTime()-start);
			if (message.getFinish() == 1) {//文件读取完成
				MultimediaDownLoadMessage dmessage = createMultimediaDownLoadMessage(size, message, saveFileName);
				List<Endpoint> toEndpoints = Context.getEndpoints(message.getReceiverId());
				boolean isInChat = false;
				if (toEndpoints != null) {
					sendsDownLoadMes(message, dmessage,	toEndpoints, isInChat);
				} else {
					setSystemOrChatMegNum(message);
				}
				Context.fileMessageData.remove(key);
				String jsonStr = createChatRecode(size,	message, saveFileName);
				String response = HttpUtils.sendChatRequest(jsonStr);
				logger.info("保存音视频文件记录{},结果{}",jsonStr,response);
			}
				}
			}

				try{
				fos.close();
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		return flag;
	}
	private boolean writePicture(String filePath,ConcurrentMap<Integer, Message> entity, Long key) throws URISyntaxException, IOException {
		  boolean flag = true;
		  PictureMessage message0 = ((PictureMessage)entity.get(0));
	         if(message0!=null){
	         File file = new File(filePath+ message0.getSenderId());
				if (!file.exists()) {
					file.mkdir();
				}
				if (message0.getFileName() == null|| "".equals(message0.getFileName())|| !message0.getFileName().contains(".")) {
					flag =false;
					return flag;
				}
				String saveFileName = key+ "_"	+ message0.getFileName();
				long start = new Date().getTime();
				FileOutputStream fos = new FileOutputStream(filePath	+ message0.getSenderId()	+ File.separator+ message0.getReceiverId() + "_"+ saveFileName, true);
				long size = 0L;
				for(int i=0;i<entity.size();i++){
	        	 PictureMessage message = (PictureMessage)entity.get(i);
			Long fileSize = Long.valueOf(message.getData().length);
			size+=fileSize;
			if (message != null) {
			
				logger.debug("开始写数据块，大小{}.",message.getData().length);
				fos.write(message.getData());
				logger.debug("写数据块耗时{}ms.",new Date().getTime()-start);
				if (message.getFinish() == 1) {//文件读取完成
					PictureDownLoadMessage dmessage = createMultimediaDownLoadMessage(size, message, saveFileName);
					List<Endpoint> toEndpoints = Context.getEndpoints(message.getReceiverId());
					boolean isInChat = false;
					if (toEndpoints != null) {
						sendsDownLoadMes(message, dmessage,	toEndpoints, isInChat);
					} else {
						setSystemOrChatMegNum(message);
					}
					Context.fileMessageData.remove(key);
					String jsonStr = createChatRecode(size,	message, saveFileName);
					String response = HttpUtils.sendChatRequest(jsonStr);
					logger.info("保存文件记录{},结果{}",jsonStr,response);
				}
				
				}
			}

				try{
				fos.close();
				}catch(Exception e){
					e.printStackTrace();
				}
		}
			return flag;
	}
	private String createChatRecode(Long fileSize, MultimediaMessage message,
			String saveFileName) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("subType", "file");
		subMap.put("url",PropertiesUtils.readValue("download.path.cdn")
						+ message.getSenderId()
						+ File.separator
						+ message.getReceiverId()
						+ "_" + saveFileName);
		subMap.put("fileSize", fileSize);
		subMap.put("fileName",
				message.getFileName());
		map.put("product", "helome");
		map.put("type", 1);
		map.put("contentType", 1); 
		map.put("from", message.getSenderId() + "");
		map.put("to", message.getReceiverId() + "");
		map.put("sendTime", DateUtil.getDate());
		map.put("content", JsonUtils.stringify(subMap));
		String jsonStr = JsonUtils.stringify(map);
		return jsonStr;
	}
	private void setSystemOrChatMegNum(MultimediaMessage message) {
		MemCachedUtil.putCommunicateNum(message.getSenderId(),message.getReceiverId());
		MemCachedUtil.putCommunicateTotal(message.getReceiverId());
	}
	private void sendsDownLoadMes(MultimediaMessage message,
			MultimediaDownLoadMessage dmessage, List<Endpoint> toEndpoints,
			boolean isInChat) {
		synchronized (toEndpoints) {
			for (Endpoint toEndpoint : toEndpoints) {
				if (toEndpoint instanceof WebRTCEndpoint) {
					Long id = Context.ENDPOINTS_TO_PEER
							.get(toEndpoint);
					logger.info("通知消息用户{}下载");
					if (message.getSenderId() == id) {
						isInChat = true;
					}
				}
			}
			if (!isInChat) {
				setSystemOrChatMegNum(message);
			}
			for (Endpoint toEndpoint : toEndpoints) {
				dmessage.setEndpoint(toEndpoint);
				if (toEndpoint instanceof SocketEndpoint) {
					toEndpoint.getContext().channel().writeAndFlush(dmessage.toBinary());
				} else {
					toEndpoint.getContext().channel().writeAndFlush(new TextWebSocketFrame(dmessage.toJson()));
				}
			}
		}
		
	}
	private MultimediaDownLoadMessage createMultimediaDownLoadMessage(Long fileSize,
			MultimediaMessage message, String saveFileName) {
		MultimediaDownLoadMessage dmessage = new MultimediaDownLoadMessage();
		dmessage.setSenderId(message.getSenderId());
		dmessage.setSenderName(message.getSenderName());
		dmessage.setReceiverId(message.getReceiverId());
		dmessage.setReceiverName(message.getReceiverName());
		dmessage.setUrl(PropertiesUtils.readValue("download.path.cdn")+ message.getSenderId()+ File.separator+ message.getReceiverId()
				+ "_"+ saveFileName);
		dmessage.setFileName(message.getFileName());
		dmessage.setFileSize(fileSize);
		dmessage.setLength(2+8+1+ 8	+ 3	+ 8	+ Utils.getUTF8StringLength(dmessage.getSenderName())
				+ Utils.getUTF8StringLength(dmessage.getReceiverName())
				+ Utils.getUTF8StringLength(message.getFileName())
				+ Utils.getUTF8StringLength(dmessage.getUrl()));
		return dmessage;
	}
	private PictureDownLoadMessage createMultimediaDownLoadMessage(
			Long fileSize, PictureMessage message, String saveFileName) {
		PictureDownLoadMessage dmessage = new PictureDownLoadMessage();
		dmessage.setSenderId(message.getSenderId());
		dmessage.setSenderName(message.getSenderName());
		dmessage.setReceiverId(message.getReceiverId());
		dmessage.setReceiverName(message.getReceiverName());
		dmessage.setUrl(PropertiesUtils.readValue("download.path.cdn")+ message.getSenderId()+ File.separator+ message.getReceiverId()
				+ "_"+ saveFileName);
		dmessage.setFileName(message.getFileName());
		dmessage.setFileSize(fileSize);
		dmessage.setLength(2+8+1+ 8	+ 3	+ 8	+ Utils.getUTF8StringLength(dmessage.getSenderName())
				+ Utils.getUTF8StringLength(dmessage.getReceiverName())
				+ Utils.getUTF8StringLength(message.getFileName())
				+ Utils.getUTF8StringLength(dmessage.getUrl()));
		return dmessage;
	}
	
	  //创建文件下载提醒消息
		private DownLoadMessage createDownLoadMessage(Long fileSize,
				CacheBinaryMessage message, String saveFileName) {
			DownLoadMessage dmessage = new DownLoadMessage();
			dmessage.setSenderId(message.getSenderId());
			dmessage.setSenderName(message.getSenderName());
			dmessage.setReceiverId(message.getReceiverId());
			dmessage.setReceiverName(message.getReceiverName());
			dmessage.setUrl(PropertiesUtils.readValue("download.path.cdn")+ message.getSenderId()+ File.separator+ message.getReceiverId()
					+ "_"+ saveFileName);
			dmessage.setFileName(message.getFileName());
			dmessage.setFileSize(fileSize);
			dmessage.setSendTime(new Date().getTime());
			dmessage.setLength(2+8+1+ 8	+ 3	+ 8	+ 8 + Utils.getUTF8StringLength(dmessage.getSenderName())
					+ Utils.getUTF8StringLength(dmessage.getReceiverName())
					+ Utils.getUTF8StringLength(message.getFileName())
					+ Utils.getUTF8StringLength(dmessage.getUrl()));
			return dmessage;
		}
	
}
