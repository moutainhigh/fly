package cn.jkm.eb.service.mq;

import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.jkm.eb.service.service.MsgService;


/**
 * 消息事务
 * @author zhengzb
 *
 */
@Component("transactionExecuterImpl")
public class TransactionExecuterImpl implements LocalTransactionExecuter {
	private final Logger logger = LoggerFactory.getLogger(TransactionExecuterImpl.class);
	@Autowired
	private MsgService msgService;
	public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
		try {
			//TODO 执行db操作
			msgService.insertMsg();
			//成功通知MQ消息变更 该消息变为：<确认发送>
			return LocalTransactionState.COMMIT_MESSAGE;
		} catch (Exception e) {
			logger.error("消息事务执行失败!", e);
			//失败则不通知MQ 该消息一直处于：<暂缓发送>
			return LocalTransactionState.ROLLBACK_MESSAGE;
			
		}
		
	}
}