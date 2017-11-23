package cn.jkm.common.pay.wxpay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 16:36 2017/6/19
 */
@XStreamAlias("xml")
public class WxPayOrderCloseResult extends WxPayBaseResult {
    @XStreamAlias("result_msg")
    private String resultMsg;

    public WxPayOrderCloseResult() {
    }

    public String getResultMsg() {
        return this.resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
