package com.helome.messagecenter.core;

import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.message.base.CacheBinaryMessage;
import com.helome.messagecenter.message.business.MultimediaMessage;
import com.helome.messagecenter.message.business.PictureMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 写文件任务
 *
 * User: Rocs Zhang
 */
public class CacheBinaryWriter implements Callable<Integer> {
    private final static Logger logger = LoggerFactory.getLogger(CacheBinaryWriter.class);

    public static final int N_WRITE_THREADS = 10;       //写文件的并发数
    public static final ExecutorService execWritePool = Executors.newFixedThreadPool(N_WRITE_THREADS);

    private RandomAccessFile dstFile;
    private int seekSize;
    private Message[] datas;

    private CacheBinaryWriter(RandomAccessFile dstFile, int seekSize, Message[] datas) {
        this.dstFile = dstFile;
        this.seekSize = seekSize;
        this.datas = datas;
    }

    @Override
    public Integer call() throws Exception {
        int result = 0;
        if(dstFile != null && datas.length > 0){
            short code = Short.MIN_VALUE;
            int _seekSize = this.seekSize;
            byte[] data = null;
            for(int i = 0; i < datas.length; i++){
                Message message = datas[0];
                if(i == 0){
                    code = message.getCode();
                }
                if(CacheBinaryMessage.CODE == code){
                    data = ((CacheBinaryMessage)message).getData();
                }else if(MultimediaMessage.CODE == code){
                    data = ((MultimediaMessage)message).getData();
                }else if(PictureMessage.CODE == code){
                    data = ((PictureMessage) message).getData();
                }
                if(data != null && data.length > 0){
                    dstFile.seek(_seekSize);
                    dstFile.write(data);
                    _seekSize += data.length;
                }
                ++ result;
            }
        }
        return result;
    }

    public static int writeFile(Message[] messages, int firstMsgLength, String dstFilePath) throws FileNotFoundException, ExecutionException, InterruptedException {
        int blockSize = firstMsgLength;
        int blockCount = messages.length;
        RandomAccessFile raFile = null;
        int blockSum = 0;     //记录数据块量
        try {
            raFile = new RandomAccessFile(dstFilePath, "rws");
            int lenBatch = blockCount / N_WRITE_THREADS;
            int remBatch = blockCount % N_WRITE_THREADS;
            Set<Future<Integer>> fs = new HashSet<Future<Integer>>();

            if(lenBatch == 0){
                fs.add(execWritePool.submit(new CacheBinaryWriter(raFile, 0, messages)));
            }else if(remBatch == 0){
                for(int i = 0 ; i < N_WRITE_THREADS; i++){
                    if(i == 0){
                        fs.add(execWritePool.submit(new CacheBinaryWriter(raFile, 0, Arrays.copyOfRange(messages, 0, lenBatch))));
                    }else{
                        fs.add(execWritePool.submit(new CacheBinaryWriter(raFile, i * lenBatch * blockSize, Arrays.copyOfRange(messages, i * lenBatch, (i + 1) * lenBatch))));
                    }
                }
            }else{
                int remTag = remBatch;
                for(int i = 0 ; i < N_WRITE_THREADS; i++){
                    if(i == 0){
                        fs.add(execWritePool.submit(new CacheBinaryWriter(raFile, 0, Arrays.copyOfRange(messages, 0, lenBatch + (remTag-- > 0 ? 1 : 0)))));
                    }else if(remTag > 0){
                        fs.add(execWritePool.submit(new CacheBinaryWriter(raFile, (i * lenBatch + remBatch - remTag) * blockSize, Arrays.copyOfRange(messages, i * lenBatch + remBatch - remTag, (i + 1) * lenBatch + remBatch - remTag + (remTag-- > 0 ? 1 : 0)))));
                    }else{
                        fs.add(execWritePool.submit(new CacheBinaryWriter(raFile, (i * lenBatch + remBatch) * blockSize, Arrays.copyOfRange(messages, i * lenBatch + remBatch, (i + 1) * lenBatch + remBatch))));
                    }
                }
            }
            for (Future<Integer> future : fs) {
                blockSum += future.get();
            }
        }finally {
            if(raFile != null){
                try {
                    raFile.close();
                } catch (IOException e) {
                    logger.debug("",e);
                }
            }
        }
        return blockSum;
    }
}
