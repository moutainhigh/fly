package cn.jkm.core.domain.mysql.useraccount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.PayChannel;
import cn.jkm.core.domain.type.PaymentOrderState;
import cn.jkm.core.domain.type.PaymentOrderType;

/**
 * 
 * @title PaymentOrder.java
 * @package cn.jkm.core.domain.mysql.useraccount
 * @description 第三方支付订单（充值，提现，交易商品支付）
 * @author ZhangHuaRong   
 * @update 2017年7月18日 下午3:18:08
 */
@Entity
@Table(name = "tb_payment_order")
public class PaymentOrder extends MySqlBasic<PaymentOrder>{
	
	//private String accountId;//适用于充值，提现
	@Column(name = "user_id",columnDefinition = " varchar(64) COMMENT '户id'")
	private String userId;
	
    @Column(name = "payment_order_type",columnDefinition = " varchar(32) COMMENT  '第三方支付订单类型（充值 ，提现，商品直接支付）'")
    @Enumerated(EnumType.STRING)
	private PaymentOrderType paymentOrderType;//充值 ，提现，商品直接支付
	
    @Column(name = "order_id", columnDefinition = " varchar(64) COMMENT '商品订单id'")
	private String orderId;//适用于商品交易直接第三方支付
	
	@Column(name = "order_state",columnDefinition = "varchar(32) COMMENT'支付状态（待支付 ,支付失败,已完成）'")
	@Enumerated(EnumType.STRING)
	private PaymentOrderState orderState;// 待支付 ,支付失败,已完成,待审核（扩展用于提现审核）,审核通过（扩展用于提现）
	
	@Column(name = "out_trade_no", columnDefinition = " varchar(64) COMMENT '第三方交易唯一编号 用于回调后续操作'")
	private String outTradeNo;//第三方交易唯一编号 用于回调后续操作
	
	@Column(name = "subject",columnDefinition = "varchar(255) COMMENT '主题描述'")
	private String subject;//主题描述
	
	@Column(name = "pay_channel",columnDefinition = "varchar(32) COMMENT '支付渠道（支付宝app,支付宝pc,支付宝扫码,微信钱包,微信公众号,银联。。）'")
    @Enumerated(EnumType.STRING)
	private PayChannel payChannel;
	
	@Column(name = "amount",columnDefinition = "BIGINT(20) COMMENT '金额'")
	private long amount;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public PaymentOrderType getPaymentOrderType() {
		return paymentOrderType;
	}

	public void setPaymentOrderType(PaymentOrderType paymentOrderType) {
		this.paymentOrderType = paymentOrderType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public PaymentOrderState getOrderState() {
		return orderState;
	}

	public void setOrderState(PaymentOrderState orderState) {
		this.orderState = orderState;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	
	
	
}
