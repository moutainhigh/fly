package cn.jkm.core.domain.mysql.order;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.*;

import javax.persistence.*;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.core.domain.mysql.order
 * @description 订单实体类
 * @create 2017-07-20 9:29
 */
@Entity
@Table(name = "tb_order")
public class Order extends MySqlBasic<Order> {

    @Column(name = "user_id")
    private String userId; //用户ID

    @Column(name = "order_no")
    private long orderNo; // 订单编号

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderState orderStatus;// 订单状态

    @Column(name = "total_amount")
    private long totalAmount; // 订单总金额

    @Column(name = "reject")
    private String reject; // 拒绝的理由

    @Column(name = "real_amount")
    private long realAmount; // 应付金额

    @Column(name = "tradeTime")
    private long tradeTime;// 交易时间（支付时间）

    @Column(name = "delivery_time")
    private long deliveryTime; // 发货时间

    @Column(name = "receipt_time")
    private long receiptTime;// 收货时间

    @Column(name = "remark")
    private String remark;//订单备注

    @Column(name = "bill_id")
    private String  billId;// 发票id

    @Column(name = "award_cash_amount")
    private long awardCashAmount; // 奖励现金抵扣金额

    @Column(name = "deductible_score")
    private long deductibleScore;// 抵扣积分

    @Column(name = "score_amount")
    private long scoreAmount;// 积分抵扣金额

    @Column(name = "deductible_voucher")
    private long deductibleVoucher;// 抵扣券

    @Column(name = "address_id")
    private String  addressId;// 收货地址id

    @Column(name = "areaCode")
    private String areaCode;// 省市区编码.

    @Column(name = "receipt_address")
    private String receiptAddress;// 详细收货地址.

    @Column(name = "pay_type")
    @Enumerated(EnumType.STRING)
    private PayType payType;// 支付类型  1.现金余额 2.会员卡支付 3.其他卡片支付

    @Column(name = "shopping_channel")
    private String shoppingChannel;// 购买渠道./APP / PC / 微信公众号

    @Column(name = "is_pay")
    @Enumerated(EnumType.STRING)
    private Whether isPay = Whether.N;// 商品订单是否支付

    @Column(name = "trade_state")
    @Enumerated(EnumType.STRING)
    private TradeState tradeState;// 交易状态.

    @Column(name = "send_status")
    @Enumerated(EnumType.STRING)
    private SendStatus sendStatus;//  提醒发货状态

    @Column(name = "notify_pay_time")
    private long notifyPayTime;// 提醒付款时间

    @Column(name = "notify_pay")
    @Enumerated(EnumType.STRING)
    private NotifyPay notifyPay;// 提醒支付状态.

    @Column(name = "notify_delivery_time")
    private long notifyDeliveryTime;// 提醒发货时间.

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public OrderState getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderState orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(long realAmount) {
        this.realAmount = realAmount;
    }

    public long getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(long tradeTime) {
        this.tradeTime = tradeTime;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public long getReceiptTime() {
        return receiptTime;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

    public void setReceiptTime(long receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public long getAwardCashAmount() {
        return awardCashAmount;
    }

    public void setAwardCashAmount(long awardCashAmount) {
        this.awardCashAmount = awardCashAmount;
    }

    public long getDeductibleScore() {
        return deductibleScore;
    }

    public void setDeductibleScore(long deductibleScore) {
        this.deductibleScore = deductibleScore;
    }

    public long getScoreAmount() {
        return scoreAmount;
    }

    public void setScoreAmount(long scoreAmount) {
        this.scoreAmount = scoreAmount;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getReceiptAddress() {
        return receiptAddress;
    }

    public void setReceiptAddress(String receiptAddress) {
        this.receiptAddress = receiptAddress;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public String getShoppingChannel() {
        return shoppingChannel;
    }

    public void setShoppingChannel(String shoppingChannel) {
        this.shoppingChannel = shoppingChannel;
    }

    public Whether getIsPay() {
        return isPay;
    }

    public void setIsPay(Whether isPay) {
        this.isPay = isPay;
    }

    public TradeState getTradeState() {
        return tradeState;
    }

    public void setTradeState(TradeState tradeState) {
        this.tradeState = tradeState;
    }

    public SendStatus getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(SendStatus sendStatus) {
        this.sendStatus = sendStatus;
    }

    public long getNotifyPayTime() {
        return notifyPayTime;
    }

    public void setNotifyPayTime(long notifyPayTime) {
        this.notifyPayTime = notifyPayTime;
    }

    public NotifyPay getNotifyPay() {
        return notifyPay;
    }

    public void setNotifyPay(NotifyPay notifyPay) {
        this.notifyPay = notifyPay;
    }

    public long getNotifyDeliveryTime() {
        return notifyDeliveryTime;
    }

    public void setNotifyDeliveryTime(long notifyDeliveryTime) {
        this.notifyDeliveryTime = notifyDeliveryTime;
    }
}
