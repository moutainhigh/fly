package cn.jkm.core.domain.mysql.useraccount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.jkm.core.domain.mysql.MySqlBasic;

/**
 * 
 * @title UserAccount.java
 * @package cn.jkm.core.domain.mysql.useraccount
 * @description 用户账户（我的钱包）
 * @author ZhangHuaRong   
 * @update 2017年7月18日 下午3:19:16
 */
@Entity
@Table(name = "tb_user_account")
public class UserAccount extends MySqlBasic<UserAccount> {
	
	
	@Column(name = "user_id",columnDefinition = " varchar(64) COMMENT '用户id'")
	private String userId;//用户id
	
	@Column(name = "case_amount", columnDefinition = "BIGINT(20) COMMENT'现金余额'")
	private long caseAmount;//现金余额
	
	@Column(name = "vip_card_amount", columnDefinition = "BIGINT(20) COMMENT'会员卡余额'")
	private long vipCardAmount;//会员卡余额
	
	@Column(name = "reward_amount", columnDefinition = "BIGINT(20) COMMENT'奖励佣金'")
	private long rewardAmount;//奖励佣金
	
	@Column(name = "reward_integral", columnDefinition = "INT(11) COMMENT'奖励积分'")
	private int rewardIntegral;//奖励积分
	
	@Column(name = "consume_integral", columnDefinition = "INT(11) COMMENT'消费积分'")
	private int consumeIntegral;//消费积分
	
	@Column(name = "coupon", columnDefinition = "BIGINT(20) COMMENT'优惠券'")
	private long coupon;//优惠券
	
	

	public long getCoupon() {
		return coupon;
	}

	public void setCoupon(long coupon) {
		this.coupon = coupon;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getCaseAmount() {
		return caseAmount;
	}

	public void setCaseAmount(long caseAmount) {
		this.caseAmount = caseAmount;
	}

	public long getVipCardAmount() {
		return vipCardAmount;
	}

	public void setVipCardAmount(long vipCardAmount) {
		this.vipCardAmount = vipCardAmount;
	}

	public long getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(long rewardAmount) {
		this.rewardAmount = rewardAmount;
	}

	public int getRewardIntegral() {
		return rewardIntegral;
	}

	public void setRewardIntegral(int rewardIntegral) {
		this.rewardIntegral = rewardIntegral;
	}

	public int getConsumeIntegral() {
		return consumeIntegral;
	}

	public void setConsumeIntegral(int consumeIntegral) {
		this.consumeIntegral = consumeIntegral;
	}
	
	
	
	
}
