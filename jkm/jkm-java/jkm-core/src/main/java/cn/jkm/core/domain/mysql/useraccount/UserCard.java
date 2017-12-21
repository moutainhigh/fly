package cn.jkm.core.domain.mysql.useraccount;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.SwitchStatus;

import javax.persistence.*;

/**
 * 
 * @title UserCard.java
 * @package cn.jkm.core.domain.mysql.useraccount
 * @description 用户卡卷（会员卡，美容卡，或者其他）
 * @author ZhangHuaRong   
 * @update 2017年7月18日 下午3:21:21
 */
@Entity
@Table(name = "tb_user_card")
public class UserCard extends MySqlBasic<UserCard> {
	

	@Column(name = "user_id", columnDefinition = " varchar(64) COMMENT '用户id'")
    private String userId; //用户ID
    
	@Column(name = "card_id", columnDefinition = " varchar(64) COMMENT '订单id'")
    private String cardId;
    
    @Column(name = "convert_vip", columnDefinition = "varchar(32) COMMENT '是不是会员卡的标识'")
	@Enumerated(EnumType.STRING)
    private SwitchStatus convertVip;//关联card SwitchStatus 用于判断是不是会员卡 
    
    @Column(name = "name", columnDefinition = "varchar(64) COMMENT '名称'")
    private String name;
    
    @Column(name = "card_no", columnDefinition = "varchar(64) COMMENT '卡编号'")
    private String cardNo;
    
    @Column(name = "effect_time", columnDefinition = "BIGINT(20) COMMENT '开始时间'")
    private Long effectTime;
    
    @Column(name = "end_time", columnDefinition = "BIGINT(20) COMMENT '失效时间'")
    private Long endTime;
    
    @Column(name = "total", columnDefinition = "BIGINT(20) COMMENT '总金额'")
    private long total;//总金额

    @Column(name = "balance", columnDefinition = "BIGINT(20) COMMENT '剩余金额'")
    private long balance;//余额

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public SwitchStatus getConvertVip() {
		return convertVip;
	}

	public void setConvertVip(SwitchStatus convertVip) {
		this.convertVip = convertVip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Long getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Long effectTime) {
		this.effectTime = effectTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}
    
    

    
}
