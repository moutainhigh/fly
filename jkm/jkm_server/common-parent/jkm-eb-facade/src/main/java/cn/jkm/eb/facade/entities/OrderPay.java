package cn.jkm.eb.facade.entities;

public class OrderPay {
  
	/**
	 * 支付金额
	 */
    private String orderAmount;

    /**
     * 支付渠道 1 支付宝  2 微信.
     */
    private String orderChannel;

	/**
     * 订单创建时间.
     */
    private String createTime;
    
    /**
     * 订单类型1 充值订单.2
     */
    private String orderType;

    /**
     * 是否支付.
     */
    private String isPay;

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}