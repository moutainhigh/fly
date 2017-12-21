package cn.jkm.eb.facade.mq;

/**
 * 支付之后发送消息
 * @author zhengzb
 *
 */
public interface PayResultMsgFacade {
	/**
	 * 支付成功发送消息
	 * @param orderId 订单号
	 * @param payStatus	支付状态
	 * @throws Exception
	 */
	void mqProduce(String orderId, String payStatus)throws Exception;
	/**
	 * 退款成功后发送消息
	 * @param string
	 * @param jsonString
	 * @throws Exception
	 */
	void mqRefund(String string, String jsonString) throws Exception;
	/**
	 * 结算成功后发送消息
	 * @param string
	 * @param jsonString
	 * @throws Exception
	 */
	void mqWithdraw(String string, String jsonString) throws Exception;
}
