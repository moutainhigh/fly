package cn.jkm.eb.service.mq;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息消费者
 * @author zhengzb
 *
 */
public class MQConsumer {
	
	private final Logger logger = LoggerFactory.getLogger(MQConsumer.class);
	private String consumerGroup;
	private String namesrvAddr;
	private DefaultMQPushConsumer consumer;
	
	public void init() {
		try {
			this.consumer = new DefaultMQPushConsumer(consumerGroup);
			this.consumer.setNamesrvAddr(namesrvAddr);
			this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
			this.consumer.subscribe("jkm", "payResultMsg");//订阅支付成功发送的消息
			this.consumer.registerMessageListener(new Listener());
			this.consumer.start();
			logger.info("...........consumer start success........");
		} catch (Exception e) {
			logger.error("消息消费者启动失败!", e);
		}
	}

	class Listener implements MessageListenerConcurrently {
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			MessageExt msg = msgs.get(0);
			try {
				//TODO 收到消息做业务处理
				System.out.println("收到消息msg : body : " + new String(msg.getBody(), "utf-8"));
				
//				int a = 1/0;
			} catch (Exception e) {	
				//收到消息后处理本地业务失败则重新拉取消息 重试次数为3情况 
				if(msg.getReconsumeTimes() == 3){
					logger.info("接收消息后处理失败,请手动处理!"+msg);
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}			
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public String getConsumerGroup() {
		return consumerGroup;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}

}
