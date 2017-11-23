package cn.jkm.common.pay.wxpay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 16:29 2017/6/19
 */
@XStreamAlias("xml")
public class WxPayOrderQueryRequest extends WxPayBaseRequest {
    @XStreamAlias("transaction_id")
    private String transactionId;
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    public WxPayOrderQueryRequest() {
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

    protected void checkConstraints() {
        if(StringUtils.isBlank(this.transactionId) && StringUtils.isBlank(this.outTradeNo) || StringUtils.isNotBlank(this.transactionId) && StringUtils.isNotBlank(this.outTradeNo)) {
            throw new IllegalArgumentException("transaction_id 和 out_trade_no 不能同时存在或同时为空，必须二选一");
        }
    }
}
