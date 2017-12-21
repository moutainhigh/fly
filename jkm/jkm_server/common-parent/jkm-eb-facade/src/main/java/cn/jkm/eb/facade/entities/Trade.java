package cn.jkm.eb.facade.entities;


public class Trade {

	/**
	 * 交易主键.
	 */
	private String tradeId;
	
	/**
	 * 交易状态  1. 交易成功 2.交易失败 3.交易关闭.
	 */
	private String tradeStatus;
	
	/**
	 * 交易时间
	 */
	private long createTime;
	
	/**
	 * 订单id
	 */
	private String orderId;
	
	
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
