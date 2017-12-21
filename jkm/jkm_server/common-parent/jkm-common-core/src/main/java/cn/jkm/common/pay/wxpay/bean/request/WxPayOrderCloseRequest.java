package cn.jkm.common.pay.wxpay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 16:37 2017/6/19
 */
@XStreamAlias("xml")
public class WxPayOrderCloseRequest extends WxPayBaseRequest {
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    public WxPayOrderCloseRequest() {
    }

    public String getOutTradeNo() {
        return this.outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    protected void checkConstraints() {
    }
}
