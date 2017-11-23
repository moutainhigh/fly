package cn.jkm.eb.facade.entities;

public class Account {
    /**
     *
     * 账户主键
     */
    private String accountId;

    /**
     *
     * 账户余额
     */
    private String accountFee;

    /**
     *
     * 用户主键
     */
    private String userid;

    /**
     *
  	 * 消费积分
     */
    private String consumerScore;

    /**
     *
     * 奖励积分.
     */
    private String awindScore;

    /**
     *
     * 支付宝付款金额.
     */
    private String alipayFee;

    /**
     * 
     *  微信支付金额.
     */
    private String wxFee;

    /**
     *
     * 奖励现金金额.
     */
    private String cashFee;

    
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountFee() {
        return accountFee;
    }

   
    public void setAccountFee(String accountFee) {
        this.accountFee = accountFee;
    }

  
    public String getUserid() {
        return userid;
    }

   
    public void setUserid(String userid) {
        this.userid = userid;
    }

    
    public String getConsumerScore() {
        return consumerScore;
    }

    public void setConsumerScore(String consumerScore) {
        this.consumerScore = consumerScore;
    }

    
    public String getAwindScore() {
        return awindScore;
    }

    
    public void setAwindScore(String awindScore) {
        this.awindScore = awindScore;
    }

   
    public String getAlipayFee() {
        return alipayFee;
    }

    
    public void setAlipayFee(String alipayFee) {
        this.alipayFee = alipayFee;
    }

    
    public String getWxFee() {
        return wxFee;
    }

    
    public void setWxFee(String wxFee) {
        this.wxFee = wxFee;
    }

    
    public String getCashFee() {
        return cashFee;
    }

   
    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }
}