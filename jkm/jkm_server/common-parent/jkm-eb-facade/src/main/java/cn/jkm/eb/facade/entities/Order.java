package cn.jkm.eb.facade.entities;

import java.math.BigDecimal;

public class Order {

	/**
	 * 订单主键.
	 */
	private String orderId;
	/**
	 * 产品id
	 */
	private OrdProduct product;
	
	/**
	 * 订单类型 1.支付订单2.充值订单
	 */
	private int orderType;

	
	/**
	 * 订单状态 1.待付款 2.代发货 3.待收货4.已完成 5.售后
	 */
	private String orderStatus;
	
	/**
	 * 订单总金额
	 */
	private BigDecimal orderAmount;
	
	/**
	 * 下单时间
	 */
	private long createTime;

	/**
	 * 收货时间
	 */
	private long receiptTime;
	
	/**
	 * 发货时间
	 */
	private long deliveryTime;
	
	/**
	 * 交易时间
	 */
	private long tradeTime;
	
	/**
	 * 订单编号
	 */
	private long orderNo;
	
	/**
	 * 交易编号
	 */
	private long tradeNo;
	
	/**
	 * 交易状态 1.交易成功 2.交易失败 3.交易关闭.
	 */
	private int  tradeStatus;
	
	/**
	 * 订单注释
	 */
	private String orderRemark;

	/**
	 * 订单退货原因
	 */
	private String orderReason;
	
	/**
	 * 订单退货凭证
	 */
	private String orderUrl;
	
	/**
	 * 订单退货说明.
	 */
	private String orderDesc;
	
	/**
	 * 用户id
	 */
	private String orderUserId;
	
	/**
	 * 产品id
	 */
	private String  producerId;
	
	/**
	 * 支付id
	 */
	private String  payId;
	
	/**
	 * 库存id
	 */
	private String  storeId;
	
	/**
	 * 更新时间.
	 */
	private long  updateTime;
	
	/**
	 * 收货地址id
	 */
	private String  addressId;
	
	/**
	 * 物流协议id
	 *
	 */
	private String  agreeId;
	
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
/*	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}*/
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	public String getOrderReason() {
		return orderReason;
	}
	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
	}
	public String getOrderUserId() {
		return orderUserId;
	}
	public void setOrderUserId(String orderUserId) {
		this.orderUserId = orderUserId;
	}
	public String getProducerId() {
		return producerId;
	}
	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getAgreeId() {
		return agreeId;
	}
	public void setAgreeId(String agreeId) {
		this.agreeId = agreeId;
	}
	public String getOrderUrl() {
		return orderUrl;
	}
	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public OrdProduct getProduct() {
		return product;
	}
	public void setProduct(OrdProduct product) {
		this.product = product;
	}
	public long getReceiptTime() {
		return receiptTime;
	}
	public void setReceiptTime(long receiptTime) {
		this.receiptTime = receiptTime;
	}
	public long getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public long getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(long tradeTime) {
		this.tradeTime = tradeTime;
	}
	public long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}
	public long getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(long tradeNo) {
		this.tradeNo = tradeNo;
	}
	public int getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(int tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
}
