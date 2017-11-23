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
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 监视文件数据,如若则开启处理操作
 *
 * User: Rocs Zhang
 */
public class CacheBinaryMonitor extends Thread{
    private final static Logger logger = LoggerFactory.getLogger(CacheBinaryMonitor.class);

  //  public static final int N_MONITOR_THREADS = 5;       //监视文件数据的并发数
  //  public static final ExecutorService execMonitorPool = Executors.newFixedThreadPool(N_MONITOR_THREADS);

    private static String contextDir = PropertiesUtils.readValue("upload.path");
    private static String cdnDir = PropertiesUtils.readValue("download.path.cdn");

    /** 内部处理码定义 **/
    private static final long RETURN_FILE_NOT_FOUND =  -1L;    //表示提取不到消息中的文件名

    private int _nThread;
    public CacheBinaryMonitor(int nThread){
        this._nThread = nThread;
    }

    public void run(){
        logger.info("处理文件消息的后台任务[{}*~{}]start>>>>>>", this._nThread, CacheBinaryWriter.N_WRITE_THREADS);
        while (true) {
            try {
                Long key = Context.fileMessageQueue.take();
                if (key != null) {
                    logger.info("新key {}", key);
                    long begTotal = System.currentTimeMillis(), endTotal = begTotal;
                    ConcurrentMap<Integer, Message> entity = Context.fileMessageData.get(key);
                    if (entity != null && entity.size() >0 ) {
                        entity = new ConcurrentSkipListMap<>(entity);
                        short code = entity.get(0).getCode();
                        if(CacheBinaryMessage.CODE == code){
                               endTotal = processCacheBinary(key, entity);
                        }else if(MultimediaMessage.CODE == code){
                               endTotal = processMultimediaMessage(key, entity);
                        }else if(PictureMessage.CODE == code){
                               endTotal = processPictureMessage(key, entity);
                        }else{
                            logger.error("key:{}对应为无效数据{}", key);
                            continue;
                        }
                    }
                    if(endTotal == RETURN_FILE_NOT_FOUND){
                        logger.error("无法提取key:{}对应的文件", key);
                    }else if(endTotal > 0L){
                        logger.info("单次写key:{}对应文件耗时 {} MS", key, endTotal - begTotal);
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
    //写入数据文件 For CacheBinaryMessage
    private long processCacheBinary(Long key, ConcurrentMap<Integer, Message> entity){
        long beg = System.currentTimeMillis();
        CacheBinaryMessage[] messages = (CacheBinaryMessage[])entity.values().toArray(new CacheBinaryMessage[0]);
        // 从第一个消息中提取相关信息
        CacheBinaryMessage message0 = messages[0];
        /* 以消息的senderId创建文件目录 */
        File dstDir = new File(contextDir + message0.getSenderId());
        if (!dstDir.exists()) {
            dstDir.mkdir();
        }
        String fileName = message0.getFileName();
        if (fileName == null || fileName.isEmpty()) {
            return RETURN_FILE_NOT_FOUND;
        }
        /* 以 key_{消息中的文件名} 作为下载文件的文件名片断 */
        String downloadFileName = key + "_"	+ fileName;

        /* 以 {消息的receiverId}_key_{消息中的文件名} 作为保存文件的文件名 */
        String saveFileName =  message0.getReceiverId() + "_" + downloadFileName;

        int blockSize = message0.getData().length;
        int blockCount = messages.length;
        int fileSize =  (blockCount == 1) ? blockSize : blockSize * (blockCount - 1) + messages[blockCount - 1].getData().length;
        String dstFilePath = dstDir + File.separator + saveFileName;
        Boolean isDirtyFile = false;
        try{
            logger.info("开始写一个普通二进制数据文件，大小 {} Byte.", fileSize);
            long begWrite = System.currentTimeMillis();
            int blockSum = CacheBinaryWriter.writeFile( messages, blockSize, dstFilePath);
            if(blockSum == blockCount){
                long endWrite = System.currentTimeMillis();
                logger.info("写普通二进制数据文件完成,耗时 {} MS,开始进行消息处理", endWrite - begWrite);
                return endWrite + processDownloadMessage(key, fileSize, messages[blockCount - 1], downloadFileName);
            }else{
                logger.error("未完整写入普通二进制数据文件,将会删除该脏数据文件.");
                isDirtyFile = true;
            }
        } catch (Exception e) {
            logger.error("写普通二进制数据文件发生错误 /",e);
            isDirtyFile = true;
        } finally {
            File dstFile = new File(dstFilePath);
            if(dstFile.exists() && isDirtyFile){
                dstFile.delete();
            }
        }
        return 0L;
    }
    //写入数据文件 For MultimediaMessage
    private long processMultimediaMessage(Long key, ConcurrentMap<Integer, Message> entity){
        long beg = System.currentTimeMillis();
        MultimediaMessage[] messages = (MultimediaMessage[])entity.values().toArray(new MultimediaMessage[0]);
        // 从第一个消息中提取相关信息
        MultimediaMessage message0 = messages[0];
        /* 以消息的senderId创建文件目录 */
        File dstDir = new File(contextDir + message0.getSenderId());
        if (!dstDir.exists()) {
            dstDir.mkdir();
        }
        String fileName = message0.getFileName();
        if (fileName == null || fileName.isEmpty() || !message0.getFileName().contains(".")) {
            return RETURN_FILE_NOT_FOUND;
        }
        /* 以 key_{消息中的文件名} 作为下载文件的文件名片断 */
        String downloadFileName = key + "_"	+ fileName;

        /* 以 {消息的receiverId}_key_{消息中的文件名} 作为保存文件的文件名 */
        String saveFileName =  message0.getReceiverId() + "_" + downloadFileName;

        int blockSize = message0.getData().length;
        int blockCount = messages.length;
        int fileSize =  (blockCount == 1) ? blockSize : blockSize * (blockCount - 1) + messages[blockCount - 1].getData().length;
        String dstFilePath = dstDir + File.separator + saveFileName;
        Boolean isDirtyFile = false;
        try{
             logger.info("开始写一个多媒体数据文件，大小 {} Byte.", fileSize);
             long begWrite = System.currentTimeMillis();
             int blockSum = CacheBinaryWriter.writeFile( messages, blockSize, dstFilePath);
             if(blockSum == blockCount){
                long endWrite = System.currentTimeMillis();
                logger.info("写多媒体数据文件完成,耗时 {} MS,开始进行消息处理", endWrite - begWrite);
                return endWrite + processDownloadMessage(key, fileSize, messages[blockCount - 1], downloadFileName);
            }else{
                logger.error("未完整写入多媒体数据文件,将会删除该脏数据文件.");
                isDirtyFile = true;
            }
        } catch (Exception e) {
            logger.error("写多媒体数据文件发生错误 /",e);
            isDirtyFile = true;
        } finally {
            File dstFile = new File(dstFilePath);
            if(dstFile.exists() && isDirtyFile){
                dstFile.delete();
            }
        }
        return 0L;
    }
    //写入数据文件 For PictureMessage
    private long processPictureMessage(Long key, ConcurrentMap<Integer, Message> entity){
        long beg = System.currentTimeMillis();
        PictureMessage[] messages = (PictureMessage[])entity.values().toArray(new PictureMessage[0]);
        // 从第一个消息中提取相关信息
        PictureMessage message0 = messages[0];
        /* 以消息的senderId创建文件目录 */
        File dstDir = new File(contextDir + message0.getSenderId());
        if (!dstDir.exists()) {
            dstDir.mkdir();
        }
        String fileName = message0.getFileName();
        if (fileName == null || fileName.isEmpty() || !message0.getFileName().contains(".")) {
            return RETURN_FILE_NOT_FOUND;
        }
        /* 以 key_{消息中的文件名} 作为下载文件的文件名片断 */
        String downloadFileName = key + "_"	+ fileName;

        /* 以 {消息的receiverId}_key_{消息中的文件名} 作为保存文件的文件名 */
        String saveFileName =  message0.getReceiverId() + "_" + downloadFileName;

        int blockSize = message0.getData().length;
        int blockCount = messages.length;
        int fileSize =  (blockCount == 1) ? blockSize : blockSize * (blockCount - 1) + messages[blockCount - 1].getData().length;
        String dstFilePath = dstDir + File.separator + saveFileName;
        Boolean isDirtyFile = false;
        try{
            logger.info("开始写一个图片数据文件，大小 {} Byte.", fileSize);
            long begWrite = System.currentTimeMillis();
            int blockSum = CacheBinaryWriter.writeFile( messages, blockSize, dstFilePath);
            if(blockSum == blockCount){
                long endWrite = System.currentTimeMillis();
                logger.info("写图片数据文件完成,耗时 {} MS,开始进行消息处理", endWrite - begWrite);
                return endWrite + processDownloadMessage(key, fileSize, messages[blockCount - 1], downloadFileName);
            }else{
                logger.error("未完整写入图片数据文件,将会删除该脏数据文件.");
                isDirtyFile = true;
            }
        } catch (Exception e) {
            logger.error("写图片数据文件发生错误 /",e);
            isDirtyFile = true;
        } finally {
            File dstFile = new File(dstFilePath);
            if(dstFile.exists() && isDirtyFile){
                dstFile.delete();
            }
        }
        return 0L;
    }
    //发送通知消息
    private long processDownloadMessage(Long key, long fileSize, Message message, String downloadFileName) throws URISyntaxException, JsonProcessingException {
        long begMsg = System.currentTimeMillis();
        long senderId = 0;
        long receiverId = 0;
        String senderName = null;
        String receiverName = null;
        String fileName = null;
        String urlStr = null;
        List<Endpoint> toEndpoints = null;
        short code = message.getCode();
        if(CacheBinaryMessage.CODE == code){
            CacheBinaryMessage msg = (CacheBinaryMessage)message;
            senderId = msg.getSenderId();
            senderName = msg.getSenderName();
            receiverId = msg.getReceiverId();
            receiverName = msg.getReceiverName();
            fileName = msg.getFileName();
            /* CDN下载路径: {消息的senderId}/{消息的receiverId}_key_{消息中的文件名}*/
            String downloadFilePath = new StringBuffer().append(cdnDir).append(senderId).append(File.separator).append(receiverId).append("_").append(downloadFileName).toString();
            DownLoadMessage dmessage = createDownLoadMessage(fileSize, senderId, senderName, receiverId, receiverName, msg.getFileName(), urlStr = downloadFilePath);
            toEndpoints = Context.getEndpoints(receiverId);
            boolean isInChat = false;
            if (toEndpoints != null) {
                sendsDownLoadMes(dmessage, senderId, senderName, receiverId, receiverName, toEndpoints, isInChat);
            } else {
                setSystemOrChatMegNum(senderId, receiverId);
            }
        }else if(MultimediaMessage.CODE == code){
            MultimediaMessage msg = (MultimediaMessage)message;
            senderId = msg.getSenderId();
            senderName = msg.getSenderName();
            receiverId = msg.getReceiverId();
            receiverName = msg.getReceiverName();
            fileName = msg.getFileName();
            /* CDN下载路径: {消息的senderId}/{消息的receiverId}_key_{消息中的文件名}*/
            String downloadFilePath = new StringBuffer().append(cdnDir).append(senderId).append(File.separator).append(receiverId).append("_").append(downloadFileName).toString();
            MultimediaDownLoadMessage dmessage = createMultimediaDownLoadMessage(fileSize, senderId, senderName, receiverId, receiverName, msg.getFileName(), urlStr = downloadFilePath);
            toEndpoints = Context.getEndpoints(receiverId);
            boolean isInChat = false;
            if (toEndpoints != null) {
                sendsDownLoadMes(dmessage, senderId, senderName, receiverId, receiverName, toEndpoints, isInChat);
            } else {
                setSystemOrChatMegNum(senderId, receiverId);
            }
        }else if(PictureMessage.CODE == code){
            PictureDownLoadMessage msg = (PictureDownLoadMessage)message;
            senderId = msg.getSenderId();
            senderName = msg.getSenderName();
            receiverId = msg.getReceiverId();
            receiverName = msg.getReceiverName();
            fileName = msg.getFileName();
            /* CDN下载路径: {消息的senderId}/{消息的receiverId}_key_{消息中的文件名}*/
            String downloadFilePath = new StringBuffer().append(cdnDir).append(senderId).append(File.separator).append(receiverId).append("_").append(downloadFileName).toString();
            PictureDownLoadMessage dmessage = createPictureDownLoadMessage(fileSize, senderId, senderName, receiverId, receiverName, msg.getFileName(), urlStr = downloadFilePath);
            toEndpoints = Context.getEndpoints(receiverId);
            boolean isInChat = false;
            if (toEndpoints != null) {
                sendsDownLoadMes(dmessage, senderId, senderName, receiverId, receiverName, toEndpoints, isInChat);
            } else {
                setSystemOrChatMegNum(senderId, receiverId);
            }
        }
        Context.fileMessageData.remove(key);

        String jsonStr = createChatRecode(urlStr, fileSize, fileName, senderId, receiverId);
        logger.info("保存下载文件提醒消息聊天记录{}.",jsonStr);
        long begChat = System.currentTimeMillis();
        String response = HttpUtils.sendChatRequest(jsonStr);
        logger.info("保存下载文件提醒消息聊天记录完成,耗时 {} MS, 结果{}",System.currentTimeMillis() - begChat, response);
        return System.currentTimeMillis() - begMsg;
    }
    //构建文件下载提醒消息
    private DownLoadMessage createDownLoadMessage(Long fileSize, long senderId, String senderName, long receiverId, String receiverName, String fileName, String downloadFilePath) {
        DownLoadMessage dmessage = new DownLoadMessage();
        dmessage.setSenderId(senderId);
        dmessage.setSenderName(senderName);
        dmessage.setReceiverId(receiverId);
        dmessage.setReceiverName(receiverName);
        dmessage.setUrl(downloadFilePath);
        dmessage.setFileName(fileName);
        dmessage.setFileSize(fileSize);
        dmessage.setSendTime(new Date().getTime());
        dmessage.setLength(2+8+1+ 8	+ 3	+ 8	+ 8 + Utils.getUTF8StringLength(dmessage.getSenderName())
                + Utils.getUTF8StringLength(dmessage.getReceiverName())
                + Utils.getUTF8StringLength(dmessage.getFileName())
                + Utils.getUTF8StringLength(dmessage.getUrl()));
        return dmessage;
    }
    private MultimediaDownLoadMessage createMultimediaDownLoadMessage(Long fileSize, long senderId, String senderName, long receiverId, String receiverName, String fileName, String downloadFilePath) {
        MultimediaDownLoadMessage dmessage = new MultimediaDownLoadMessage();
        dmessage.setSenderId(senderId);
        dmessage.setSenderName(senderName);
        dmessage.setReceiverId(receiverId);
        dmessage.setReceiverName(receiverName);
        dmessage.setUrl(downloadFilePath);
        dmessage.setFileName(fileName);
        dmessage.setFileSize(fileSize);
        dmessage.setLength(2+8+1+ 8	+ 3	+ 8	+ Utils.getUTF8StringLength(dmessage.getSenderName())
                + Utils.getUTF8StringLength(dmessage.getReceiverName())
                + Utils.getUTF8StringLength(dmessage.getFileName())
                + Utils.getUTF8StringLength(dmessage.getUrl()));
        return dmessage;
    }
    private PictureDownLoadMessage createPictureDownLoadMessage(Long fileSize, long senderId, String senderName, long receiverId, String receiverName, String fileName, String downloadFilePath) {
        PictureDownLoadMessage dmessage = new PictureDownLoadMessage();
        dmessage.setSenderId(senderId);
        dmessage.setSenderName(senderName);
        dmessage.setReceiverId(receiverId);
        dmessage.setReceiverName(receiverName);
        dmessage.setUrl(downloadFilePath);
        dmessage.setFileName(fileName);
        dmessage.setFileSize(fileSize);
        dmessage.setLength(2+8+1+ 8	+ 3	+ 8	+ Utils.getUTF8StringLength(dmessage.getSenderName())
                + Utils.getUTF8StringLength(dmessage.getReceiverName())
                + Utils.getUTF8StringLength(dmessage.getFileName())
                + Utils.getUTF8StringLength(dmessage.getUrl()));
        return dmessage;
    }
    //构建并发送文件下载提醒消息
    private void sendsDownLoadMes(Message message, long senderId, String senderName, long receiverId, String receiverName, List<Endpoint> toEndpoints, boolean isInChat) {
        synchronized (toEndpoints) {
            for (Endpoint toEndpoint : toEndpoints) {
                if (toEndpoint instanceof WebRTCEndpoint) {
                    Long id = Context.ENDPOINTS_TO_PEER.get(toEndpoint);
                    logger.info("将通知消息用户{}下载", receiverName);
                    if (senderId == id) {
                        isInChat = true;
                    }
                }
            }
            if (!isInChat) {
                setSystemOrChatMegNum(senderId, receiverId);
            }
            for (Endpoint toEndpoint : toEndpoints) {
                message.setEndpoint(toEndpoint);
                if (toEndpoint instanceof SocketEndpoint) {
                    toEndpoint.getContext().channel().writeAndFlush(message.toBinary());
                } else {
                    toEndpoint.getContext().channel().writeAndFlush(new TextWebSocketFrame(message.toJson()));
                }
            }
        }
    }
    //不在线 消息数目缓存
    private void setSystemOrChatMegNum(long senderId, long receiverId) {
        MemCachedUtil.putCommunicateNum(senderId, receiverId);
        MemCachedUtil.putCommunicateTotal(receiverId);
    }
    //构建下载文件消息的聊天记录格式
    private String createChatRecode(String urlStr, long fileSize, String fileName, long senderId, long receiverId) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> subMap = new HashMap<String, Object>();
        subMap.put("subType", "file");
        subMap.put("url", urlStr);
        subMap.put("fileSize", fileSize);
        subMap.put("fileName",fileName);
        map.put("product", "helome");
        map.put("type", 1);
        map.put("contentType", 1);
        map.put("from", senderId + "");
        map.put("to", receiverId + "");
        map.put("sendTime", DateUtil.getDate());
        map.put("content", JsonUtils.stringify(subMap));
        String jsonStr = JsonUtils.stringify(map);
        return jsonStr;
    }
}
