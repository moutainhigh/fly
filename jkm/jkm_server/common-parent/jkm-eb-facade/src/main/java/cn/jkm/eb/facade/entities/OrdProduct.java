package cn.jkm.eb.facade.entities;

import java.math.BigDecimal;

public class OrdProduct {

	
	/**
	 * 訂單产品id
	 */
	private String ordProductId;
	
	/**
	 * 产品数量
	 */
	private int OrdProductNum;
	
	/**
	 * 产品单价
	 */
	private BigDecimal OrdProductUnit;
	
	/**
	 * 产品单价
	 */
	private BigDecimal OrdProTotalAmount;
	
	/**
	 * 产品id
	 */
	private String productId;
	
	/**
	 * 订单id
	 */
	private String orderId;
	
	
	public String getOrdProductId() {
		return ordProductId;
	}
	public void setOrdProductId(String ordProductId) {
		this.ordProductId = ordProductId;
	}
	public int getOrdProductNum() {
		return OrdProductNum;
	}
	public void setOrdProductNum(int ordProductNum) {
		OrdProductNum = ordProductNum;
	}
	public BigDecimal getOrdProductUnit() {
		return OrdProductUnit;
	}
	public void setOrdProductUnit(BigDecimal ordProductUnit) {
		OrdProductUnit = ordProductUnit;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getOrdProTotalAmount() {
		return OrdProTotalAmount;
	}
	public void setOrdProTotalAmount(BigDecimal ordProTotalAmount) {
		OrdProTotalAmount = ordProTotalAmount;
	}
}

