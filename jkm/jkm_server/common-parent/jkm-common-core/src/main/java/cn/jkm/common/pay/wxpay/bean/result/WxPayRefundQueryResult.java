package cn.jkm.common.pay.wxpay.bean.result;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 16:44 2017/6/19
 */
@XStreamAlias("xml")
public class WxPayRefundQueryResult extends WxPayBaseResult {
    @XStreamAlias("device_info")
    private String deviceInfo;
    @XStreamAlias("transaction_id")
    private String transactionId;
    @XStreamAlias("out_trade_no")
    private String outTradeNo;
    @XStreamAlias("total_fee")
    private Integer totalFee;
    @XStreamAlias("settlement_total_fee")
    private Integer settlementTotalFee;
    @XStreamAlias("fee_type")
    private String feeType;
    @XStreamAlias("cash_fee")
    private Integer cashFee;
    @XStreamAlias("refund_count")
    private Integer refundCount;
    private List<RefundRecord> refundRecords;

    public WxPayRefundQueryResult() {
    }

    public String getDeviceInfo() {
        return this.deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return this.outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getTotalFee() {
        return this.totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getSettlementTotalFee() {
        return this.settlementTotalFee;
    }

    public void setSettlementTotalFee(Integer settlementTotalFee) {
        this.settlementTotalFee = settlementTotalFee;
    }

    public String getFeeType() {
        return this.feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Integer getCashFee() {
        return this.cashFee;
    }

    public void setCashFee(Integer cashFee) {
        this.cashFee = cashFee;
    }

    public Integer getRefundCount() {
        return this.refundCount;
    }

    public void setRefundCount(Integer refundCount) {
        this.refundCount = refundCount;
    }

    public List<RefundRecord> getRefundRecords() {
        return this.refundRecords;
    }

    public void setRefundRecords(List<RefundRecord> refundRecords) {
        this.refundRecords = refundRecords;
    }

    public void composeRefundRecords() {
        if(this.refundCount != null && this.refundCount.intValue() > 0) {
            this.refundRecords = Lists.newArrayList();

            for(int i = 0; i < this.refundCount.intValue(); ++i) {
                WxPayRefundQueryResult.RefundRecord refundRecord = new WxPayRefundQueryResult.RefundRecord();
                this.refundRecords.add(refundRecord);
                refundRecord.setOutRefundNo(this.getXmlValue(new String[]{"xml/out_refund_no_" + i}));
                refundRecord.setRefundId(this.getXmlValue(new String[]{"xml/refund_id_" + i}));
                refundRecord.setRefundChannel(this.getXmlValue(new String[]{"xml/refund_channel_" + i}));
                refundRecord.setRefundFee(this.getXmlValueAsInt(new String[]{"xml/refund_fee_" + i}));
                refundRecord.setSettlementRefundFee(this.getXmlValueAsInt(new String[]{"xml/settlement_refund_fee_" + i}));
                refundRecord.setCouponType(this.getXmlValue(new String[]{"xml/coupon_type_" + i}));
                refundRecord.setCouponRefundFee(this.getXmlValueAsInt(new String[]{"xml/coupon_refund_fee_" + i}));
                refundRecord.setCouponRefundCount(this.getXmlValueAsInt(new String[]{"xml/coupon_refund_count_" + i}));
                refundRecord.setRefundStatus(this.getXmlValue(new String[]{"xml/refund_status_" + i}));
                refundRecord.setRefundRecvAccout(this.getXmlValue(new String[]{"xml/refund_recv_accout_" + i}));
                if(refundRecord.getCouponRefundCount() != null && refundRecord.getCouponRefundCount().intValue() != 0) {
                    List<RefundRecord.RefundCoupon> coupons = Lists.newArrayList();

                    for(int j = 0; j < refundRecord.getCouponRefundCount().intValue(); ++j) {
                        coupons.add(new WxPayRefundQueryResult.RefundRecord.RefundCoupon(this.getXmlValue(new String[]{"xml/coupon_refund_id_" + i + "_" + j}), this.getXmlValueAsInt(new String[]{"xml/coupon_refund_fee_" + i + "_" + j})));
                    }
                }
            }
        }

    }

    public static class RefundRecord {
        @XStreamAlias("out_refund_no")
        private String outRefundNo;
        @XStreamAlias("refund_id")
        private String refundId;
        @XStreamAlias("refund_channel")
        private String refundChannel;
        @XStreamAlias("refund_fee")
        private Integer refundFee;
        @XStreamAlias("settlement_refund_fee")
        private Integer settlementRefundFee;
        @XStreamAlias("refund_account")
        private String refundAccount;
        @XStreamAlias("coupon_type")
        private String couponType;
        @XStreamAlias("coupon_refund_fee")
        private Integer couponRefundFee;
        @XStreamAlias("coupon_refund_count")
        private Integer couponRefundCount;
        private List<RefundCoupon> refundCoupons;
        @XStreamAlias("refund_status")
        private String refundStatus;
        @XStreamAlias("refund_recv_accout")
        private String refundRecvAccout;

        public RefundRecord() {
        }

        public String getOutRefundNo() {
            return this.outRefundNo;
        }

        public void setOutRefundNo(String outRefundNo) {
            this.outRefundNo = outRefundNo;
        }

        public String getRefundId() {
            return this.refundId;
        }

        public void setRefundId(String refundId) {
            this.refundId = refundId;
        }

        public String getRefundChannel() {
            return this.refundChannel;
        }

        public void setRefundChannel(String refundChannel) {
            this.refundChannel = refundChannel;
        }

        public Integer getRefundFee() {
            return this.refundFee;
        }

        public void setRefundFee(Integer refundFee) {
            this.refundFee = refundFee;
        }

        public Integer getSettlementRefundFee() {
            return this.settlementRefundFee;
        }

        public void setSettlementRefundFee(Integer settlementRefundFee) {
            this.settlementRefundFee = settlementRefundFee;
        }

        public String getRefundAccount() {
            return this.refundAccount;
        }

        public void setRefundAccount(String refundAccount) {
            this.refundAccount = refundAccount;
        }

        public String getCouponType() {
            return this.couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public Integer getCouponRefundFee() {
            return this.couponRefundFee;
        }

        public void setCouponRefundFee(Integer couponRefundFee) {
            this.couponRefundFee = couponRefundFee;
        }

        public Integer getCouponRefundCount() {
            return this.couponRefundCount;
        }

        public void setCouponRefundCount(Integer couponRefundCount) {
            this.couponRefundCount = couponRefundCount;
        }

        public List<RefundCoupon> getRefundCoupons() {
            return this.refundCoupons;
        }

        public void setRefundCoupons(List<RefundCoupon> refundCoupons) {
            this.refundCoupons = refundCoupons;
        }

        public String getRefundStatus() {
            return this.refundStatus;
        }

        public void setRefundStatus(String refundStatus) {
            this.refundStatus = refundStatus;
        }

        public String getRefundRecvAccout() {
            return this.refundRecvAccout;
        }

        public void setRefundRecvAccout(String refundRecvAccout) {
            this.refundRecvAccout = refundRecvAccout;
        }

        public static class RefundCoupon {
            /** @deprecated */
            @XStreamAlias("coupon_refund_batch_id")
            private String couponRefundBatchId;
            @XStreamAlias("coupon_refund_id")
            private String couponRefundId;
            @XStreamAlias("coupon_refund_fee")
            private Integer couponRefundFee;

            public RefundCoupon(String couponRefundId, Integer couponRefundFee) {
                this.couponRefundId = couponRefundId;
                this.couponRefundFee = couponRefundFee;
            }

            /** @deprecated */
            @Deprecated
            public RefundCoupon(String couponRefundBatchId, String couponRefundId, Integer couponRefundFee) {
                this.couponRefundBatchId = couponRefundBatchId;
                this.couponRefundId = couponRefundId;
                this.couponRefundFee = couponRefundFee;
            }
        }
    }
}
