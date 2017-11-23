package cn.jkm.common.pay.wxpay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 16:45 2017/6/19
 */
@XStreamAlias("xml")
public class WxPayRefundQueryRequest extends WxPayBaseRequest {
    @XStreamAlias("device_info")
    private String deviceInfo;
    @XStreamAlias("sign_type")
    private String signType;
    @XStreamAlias("transaction_id")
    private String transactionId;
    @XStreamAlias("out_trade_no")
    private String outTradeNo;
    @XStreamAlias("out_refund_no")
    private String outRefundNo;
    @XStreamAlias("refund_id")
    private String refundId;

    public WxPayRefundQueryRequest() {
    }

    public String getDeviceInfo() {
        return this.deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getSignType() {
        return this.signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
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

    public String getRefundId() {
        return this.refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    protected void checkConstraints() {
        if(StringUtils.isBlank(this.transactionId) && StringUtils.isBlank(this.outTradeNo) && StringUtils.isBlank(this.outRefundNo) && StringUtils.isBlank(this.refundId) || StringUtils.isNotBlank(this.transactionId) && StringUtils.isNotBlank(this.outTradeNo) && StringUtils.isNotBlank(this.outRefundNo) && StringUtils.isNotBlank(this.refundId)) {
            throw new IllegalArgumentException("transaction_id，out_trade_no，out_refund_no，refund_id 必须四选一");
        }
    }
}
