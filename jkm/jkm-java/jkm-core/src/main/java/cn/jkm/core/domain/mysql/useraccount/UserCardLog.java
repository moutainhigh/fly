package cn.jkm.core.domain.mysql.useraccount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.jkm.core.domain.mysql.MySqlBasic;

/**
 * 
 * @title UserCardLog.java
 * @package cn.jkm.core.domain.mysql.useraccount
 * @description 用户卡卷操作日志
 * @author ZhangHuaRong   
 * @update 2017年7月18日 下午3:22:46
 */
@Entity
@Table(name = "tb_user_card_log")
public class UserCardLog  extends MySqlBasic<UserCardLog>{
	
	@Column(name = "user_id", columnDefinition = " varchar(64) COMMENT '用户id'")
	private String userId;
	
	@Column(name = "order_id", columnDefinition = " varchar(64) COMMENT '订单id'")
	private String orderId;
	
	@Column(name = "card_id",columnDefinition = "varchar(64) COMMENT '卡卷id'")
	private String cardId;
	
	@Column(name = "subject", columnDefinition = "varchar(200) COMMENT '主题描述'")
	private String subject;
	
	@Column(name = "amount", columnDefinition = "BIGINT(20) COMMENT '使用金额,数量'")
	private long amount;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	
	
	
	
}
