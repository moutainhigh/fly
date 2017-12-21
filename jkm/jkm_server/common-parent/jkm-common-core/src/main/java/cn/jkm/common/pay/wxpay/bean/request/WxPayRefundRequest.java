package cn.jkm.common.pay.wxpay.bean.request;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cn.jkm.common.pay.wxpay.config.WxPayConfig;
import cn.jkm.common.pay.wxpay.exception.WxErrorException;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 16:40 2017/6/19
 */
@XStreamAlias("xml")
public class WxPayRefundRequest extends WxPayBaseRequest {
    private static final String[] REFUND_ACCOUNT = new String[]{"REFUND_SOURCE_RECHARGE_FUNDS", "REFUND_SOURCE_UNSETTLED_FUNDS"};
    @XStreamAlias("device_info")
    private String deviceInfo;
    @XStreamAlias("transaction_id")
    private String transactionId;
    @XStreamAlias("out_trade_no")
    private String outTradeNo;
    @XStreamAlias("out_refund_no")
    private String outRefundNo;
    @XStreamAlias("total_fee")
    private Integer totalFee;
    @XStreamAlias("refund_fee")
    private Integer refundFee;
    @XStreamAlias("refund_fee_type")
    private String refundFeeType;
    @XStreamAlias("op_user_id")
    private String opUserId;
    @XStreamAlias("refund_account")
    private String refundAccount;

    public WxPayRefundRequest() {
    }

    private WxPayRefundRequest(WxPayRefundRequest.Builder builder) {
        this.setDeviceInfo(builder.deviceInfo);
        this.setAppid(builder.appid);
        this.setTransactionId(builder.transactionId);
        this.setMchId(builder.mchId);
        this.setOutTradeNo(builder.outTradeNo);
        this.setSubAppId(builder.subAppId);
        this.setSubMchId(builder.subMchId);
        this.setOutRefundNo(builder.outRefundNo);
        this.setNonceStr(builder.nonceStr);
        this.setTotalFee(builder.totalFee);
        this.setSign(builder.sign);
        this.setRefundFee(builder.refundFee);
        this.setRefundFeeType(builder.refundFeeType);
        this.setOpUserId(builder.opUserId);
        this.setRefundAccount(builder.refundAccount);
    }

    public static WxPayRefundRequest.Builder newBuilder() {
        return new WxPayRefundRequest.Builder();
    }

    public void checkAndSign(WxPayConfig config) throws WxErrorException {
        if(StringUtils.isBlank(this.getOpUserId())) {
            this.setOpUserId(config.getMchId());
        }

        super.checkAndSign(config);
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

    public String getOutRefundNo() {
        return this.outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public Integer getTotalFee() {
        return this.totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getRefundFee() {
        return this.refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public String getRefundFeeType() {
        return this.refundFeeType;
    }

    public void setRefundFeeType(String refundFeeType) {
        this.refundFeeType = refundFeeType;
    }

    public String getOpUserId() {
        return this.opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public String getRefundAccount() {
        return this.refundAccount;
    }

    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount;
    }

    protected void checkConstraints() {
        if(StringUtils.isNotBlank(this.getRefundAccount()) && !ArrayUtils.contains(REFUND_ACCOUNT, this.getRefundAccount())) {
            throw new IllegalArgumentException(String.format("refund_account目前必须为%s其中之一,实际值：%s", new Object[]{Arrays.toString(REFUND_ACCOUNT), this.getRefundAccount()}));
        } else if(StringUtils.isBlank(this.getOutTradeNo()) && StringUtils.isBlank(this.getTransactionId())) {
            throw new IllegalArgumentException("transaction_id 和 out_trade_no 不能同时为空，必须提供一个");
        }
    }

    public static final class Builder {
        private String deviceInfo;
        private String appid;
        private String transactionId;
        private String mchId;
        private String outTradeNo;
        private String subAppId;
        private String subMchId;
        private String outRefundNo;
        private String nonceStr;
        private Integer totalFee;
        private String sign;
        private Integer refundFee;
        private String refundFeeType;
        private String opUserId;
        private String refundAccount;

        private Builder() {
        }

        public WxPayRefundRequest.Builder deviceInfo(String deviceInfo) {
            this.deviceInfo = deviceInfo;
            return this;
        }

        public WxPayRefundRequest.Builder appid(String appid) {
            this.appid = appid;
            return this;
        }

        public WxPayRefundRequest.Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public WxPayRefundRequest.Builder mchId(String mchId) {
            this.mchId = mchId;
            return this;
        }

        public WxPayRefundRequest.Builder outTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
            return this;
        }

        public WxPayRefundRequest.Builder subAppId(String subAppId) {
            this.subAppId = subAppId;
            return this;
        }

        public WxPayRefundRequest.Builder subMchId(String subMchId) {
            this.subMchId = subMchId;
            return this;
        }

        public WxPayRefundRequest.Builder outRefundNo(String outRefundNo) {
            this.outRefundNo = outRefundNo;
            return this;
        }

        public WxPayRefundRequest.Builder nonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
            return this;
        }

        public WxPayRefundRequest.Builder totalFee(Integer totalFee) {
            this.totalFee = totalFee;
            return this;
        }

        public WxPayRefundRequest.Builder sign(String sign) {
            this.sign = sign;
            return this;
        }

        public WxPayRefundRequest.Builder refundFee(Integer refundFee) {
            this.refundFee = refundFee;
            return this;
        }

        public WxPayRefundRequest.Builder refundFeeType(String refundFeeType) {
            this.refundFeeType = refundFeeType;
            return this;
        }

        public WxPayRefundRequest.Builder opUserId(String opUserId) {
            this.opUserId = opUserId;
            return this;
        }

        public WxPayRefundRequest.Builder refundAccount(String refundAccount) {
            this.refundAccount = refundAccount;
            return this;
        }

        public WxPayRefundRequest build() {
            return new WxPayRefundRequest(this);
        }
    }
}
