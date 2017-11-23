package cn.jkm.eb.facade.entities;

public class Pay {
    /**
     *
     * 支付主键
     */
    private String payId;

    /**
     *
     * 支付类型1.奖励现金,2,奖励积分
     */
    private String payType;

    /**
     *
     * 支付描述.
     */
    private String payDesc;

    /**
     *
     * 支付時間.
     */
    private String payTime;

    /**
     *
     * 支付狀態.
     */
    private String payStatus;

    /**
     *
     * 支付金額. 
     */
    private String payAmount;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
}