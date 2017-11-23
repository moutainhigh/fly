package cn.jkm.common.pay.wxpay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 13:49 2017/6/19
 */
@XStreamAlias("xml")
public class WxPayUnifiedOrderResult extends WxPayBaseResult {

    @XStreamAlias("prepay_id")
    private String prepayId;
    @XStreamAlias("trade_type")
    private String tradeType;
    @XStreamAlias("code_url")
    private String codeURL;

    public WxPayUnifiedOrderResult() {
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getCodeURL() {
        return codeURL;
    }

    public void setCodeURL(String codeURL) {
        this.codeURL = codeURL;
    }
}
