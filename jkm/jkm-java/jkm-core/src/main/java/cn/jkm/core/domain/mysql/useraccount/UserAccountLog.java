package cn.jkm.core.domain.mysql.useraccount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.AddOrReduce;
import cn.jkm.core.domain.type.UserAccountLogType;

/**
 * 
 * @title UserAccountLog.java
 * @package cn.jkm.core.domain.mysql.useraccount
 * @description 用户账户操作日志
 * @author ZhangHuaRong   
 * @update 2017年7月18日 下午3:19:51
 */
@Entity
@Table(name = "tb_user_account_log")
public class UserAccountLog extends MySqlBasic<UserAccountLog>{
	
	//private String accountId;//充值，提现，会员卡充值，会员卡提现 涉及到用户账户更改
	
	@Column(name = "user_id", columnDefinition = " varchar(64) COMMENT '用户id'")
	private String userId;
	
	//充值，提现，会员卡充值，会员卡提现，奖励佣金获取，奖励佣金使用，奖励积分获取，奖励积分使用，消费积分获取，消费积分的使用
	@Column(name = "user_account_log_type", columnDefinition = "varchar(32) COMMENT '操作类型:充值，提现，会员卡充值，会员卡提现，奖励佣金获取，奖励佣金使用，奖励积分获取，奖励积分使用，消费积分获取，消费积分的使用'")
	@Enumerated(EnumType.STRING)
	private UserAccountLogType userAccountLogType;
	
	@Column(name = "amount", columnDefinition = "BIGINT(64) COMMENT '金额,数量'")
	private long amount;
	
	@Column(name = "order_id", length = 64,columnDefinition = "varchar(64) COMMENT '订单id'")
	private String orderId;//商品交易涉及的会员账号更改
	
	@Column(name = "invitation_code",columnDefinition = "varchar(64) COMMENT '邀请码(用于邀请佣金计算待完善)'")
	private String invitationCode;// 邀请码  邀请注册时 加奖励佣金 //TODO  需用户接口提供规则
	
	@Column(name = "payment_order_id", columnDefinition = "varchar(64) COMMENT '用户id'")
	private String paymentOrderId;//充值，提现，或者直接调用第三方 支付接口
	
	@Column(name = "content_post_id", columnDefinition = "BIGINT(64) COMMENT '帖子id'")
	private Long contentPostId;//根据帖子赚取消费积分
	
	@Column(name = "add_or_reduce", columnDefinition = "varchar(32) COMMENT '增加或者减少'")
	@Enumerated(EnumType.STRING)
	private AddOrReduce addOrReduce;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserAccountLogType getUserAccountLogType() {
		return userAccountLogType;
	}

	public void setUserAccountLogType(UserAccountLogType userAccountLogType) {
		this.userAccountLogType = userAccountLogType;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getPaymentOrderId() {
		return paymentOrderId;
	}

	public void setPaymentOrderId(String paymentOrderId) {
		this.paymentOrderId = paymentOrderId;
	}

	public Long getContentPostId() {
		return contentPostId;
	}

	public void setContentPostId(Long contentPostId) {
		this.contentPostId = contentPostId;
	}
	
	
	
	
}
