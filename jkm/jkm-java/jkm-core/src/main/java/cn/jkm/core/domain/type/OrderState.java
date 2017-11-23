package cn.jkm.core.domain.type;

/**
 * 订单状态
 * @author xeh
 *
 */
public enum OrderState {
	WAIT_PAY,
	WAIT_DEVILEY,
	WAIT_RECEIPT,
	DEAL,
	REFUND,
	CANCEL,
	EXAMINE,
	SERVICES;
}
