package com.helome.messagecenter;

import com.helome.messagecenter.core.CacheBinaryWriter1;
import com.helome.messagecenter.core.SendChatRecordes;
import com.helome.messagecenter.core.SocketServer;
import com.helome.messagecenter.core.WebSocketServer;
import com.helome.messagecenter.http.HttpHelloWorldServer;
import com.helome.messagecenter.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    private static int socketPort = Integer.parseInt(PropertiesUtils.readValue("mc.socket.port"));
    private static int webSocketPort = Integer.parseInt(PropertiesUtils.readValue("mc.websocket.port"));

    public static void main(String[] args) throws Exception {

        try {
           // logger.info("socket端口：" + socketPort + " start---------------");
            logger.info("webSocketPort端口：" + webSocketPort + " start---------------");
          //  new SocketServer(socketPort).start();
            new WebSocketServer(webSocketPort).start();

        /*	new MCMasterHolder(9010).start();
            Thread.sleep(1000);
            MCReportMessage mcReport = new MCReportMessage();
            mcReport.setGroupId(Short.valueOf("1"));
            mcReport.setMachineWeight(Short.valueOf("1"));
            mcReport.setRealIp("172.16.2.227");
            mcReport.setProxyIp("172.16.2.227");
            mcReport.setProxySocket(Short.valueOf("9010"));*/

           int nThreads = 5;
            ExecutorService pool = Executors.newFixedThreadPool(nThreads);
            for (int i = 0; i < nThreads; i++) {
                pool.execute(new CacheBinaryWriter1());
            }
            pool.execute(new SendChatRecordes());
            pool.shutdown();

/*           int nThreads = 1;    //监视文件数据的并发数
            ExecutorService pool = Executors.newFixedThreadPool(nThreads);
            for (int i = 0; i < nThreads; i++) {
                pool.execute(new CacheBinaryMonitor(i));
            }
            pool.shutdown();*/
            new HttpHelloWorldServer(9092).run();

        } catch (Exception ex) {
             logger.error("[MC] an error occured:", ex);
             throw ex;
        }
    }
}
