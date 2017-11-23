package cn.jkm.eb.service.mq;

import java.util.Map;

import org.apache.rocketmq.client.QueryResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息发送者
 * @author zhengzb
 *
 */
public class MQProducer {
	
	private final Logger logger = LoggerFactory.getLogger(MQProducer.class);
	private String producerGroup;
	private String namesrvAddr;
	private TransactionMQProducer producer;
	
	public void init() {
		
		this.producer = new TransactionMQProducer(producerGroup);
		this.producer.setNamesrvAddr(namesrvAddr);	//nameserver服务
		this.producer.setCheckThreadPoolMinSize(5);	// 事务回查最小并发数
		this.producer.setCheckThreadPoolMaxSize(20);	// 事务回查最大并发数
		this.producer.setCheckRequestHoldMax(2000);	// 队列数
		//服务器回调Producer，检查本地事务分支成功还是失败(无效)
		this.producer.setTransactionCheckListener(new TransactionCheckListener() {
			public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
				logger.info("state -- "+ new String(msg.getBody()));
				return LocalTransactionState.COMMIT_MESSAGE;
			}
		});
		try {
			this.producer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}	
	}
	
	public QueryResult queryMessage(String topic, String key, int maxNum, long begin, long end) throws Exception {
		return this.producer.queryMessage(topic, key, maxNum, begin, end);
	}
	
	public LocalTransactionState check(MessageExt me){
		LocalTransactionState ls = this.producer.getTransactionCheckListener().checkLocalTransactionState(me);
		return ls;
	}
	
	public void sendTransactionMessage(Message message, LocalTransactionExecuter localTransactionExecuter, Map<String, Object> transactionMapArgs) throws Exception {
		TransactionSendResult tsr = this.producer.sendMessageInTransaction(message, localTransactionExecuter, transactionMapArgs);
		logger.info("send返回内容：" + tsr.toString());
	}
	
	public void shutdown(){
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				producer.shutdown();
			}
		}));
		System.exit(0);
	}

	public String getProducerGroup() {
		return producerGroup;
	}

	public void setProducerGroup(String producerGroup) {
		this.producerGroup = producerGroup;
	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}


}