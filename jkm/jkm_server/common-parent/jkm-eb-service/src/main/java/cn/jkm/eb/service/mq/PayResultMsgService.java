package cn.jkm.eb.service.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jkm.eb.facade.mq.PayResultMsgFacade;
/**
 * 支付成功后发送消息
 * @author zhengzb
 *
 */
@Service("payResultMsgService")
public class PayResultMsgService implements PayResultMsgFacade {
	private final Logger logger = LoggerFactory.getLogger(PayResultMsgService.class);
	@Autowired
	private MQProducer mQProducer;
	
	@Autowired
	private LocalTransactionExecuter transactionExecuterImpl;
	@Override
	public void mqProduce(String orderId, String payStatus) throws Exception {
        try {
        	//构造消息数据
			Message message = new Message();
			//主题
			message.setTopic("jkm");
			//子标签
			message.setTags("payResultMsg");
			//key
			message.setKeys(orderId);
			
			//TODO 内容可封装成json
			
			message.setBody(payStatus.getBytes());
			this.mQProducer.sendTransactionMessage(message, this.transactionExecuterImpl, null);
        } catch (MQClientException e) {
            logger.error("支付消息发送异常！",e);
        }
	}

	@Override
	public void mqRefund(String string, String jsonString) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void mqWithdraw(String string, String jsonString) throws Exception {
		// TODO Auto-generated method stub

	}

}
