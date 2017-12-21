package cn.jkm.common.pay.wxpay.bean.request;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cn.jkm.common.pay.wxpay.config.WxPayConfig;
import cn.jkm.common.pay.wxpay.exception.WxErrorException;

/**
 * Created by admin on 2017/6/19.
 */
@XStreamAlias("xml")
public class WxPayUnifiedOrderRequest extends WxPayBaseRequest {

    private static final String[] TRADE_TYPES = new String[]{"JSAPI", "NATIVE", "APP"};
    @XStreamAlias("device_info")
    private String deviceInfo;
    @XStreamAlias("body")
    private String body;
    @XStreamAlias("detail")
    private String detail;
    @XStreamAlias("attach")
    private String attach;
    @XStreamAlias("out_trade_no")
    private String outTradeNo;
    @XStreamAlias("fee_type")
    private String feeType;
    @XStreamAlias("total_fee")
    private Integer totalFee;
    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;
    @XStreamAlias("time_start")
    private String timeStart;
    @XStreamAlias("time_expire")
    private String timeExpire;
    @XStreamAlias("goods_tag")
    private String goodsTag;
    @XStreamAlias("notify_url")
    private String notifyURL;
    @XStreamAlias("trade_type")
    private String tradeType;
    @XStreamAlias("product_id")
    private String productId;
    @XStreamAlias("limit_pay")
    private String limitPay;
    @XStreamAlias("openid")
    private String openid;

    public WxPayUnifiedOrderRequest() {
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    protected void checkConstraints() {
        if(!ArrayUtils.contains(TRADE_TYPES, this.getTradeType())) {
            throw new IllegalArgumentException(String.format("trade_type目前必须为%s其中之一,实际值：%s", new Object[]{Arrays.toString(TRADE_TYPES), this.getTradeType()}));
        } else if("JSAPI".equals(this.getTradeType()) && this.getOpenid() == null) {
            throw new IllegalArgumentException("当 trade_type是'JSAPI'时未指定openid");
        } else if("NATIVE".equals(this.getTradeType()) && this.getProductId() == null) {
            throw new IllegalArgumentException("当 trade_type是'NATIVE'时未指定product_id");
        }
    }

    public void checkAndSign(WxPayConfig config) throws WxErrorException {
        if(StringUtils.isBlank(this.getNotifyURL())) {
            this.setNotifyURL(config.getNotifyUrl());
        }

        if(StringUtils.isBlank(this.getTradeType())) {
            this.setTradeType(config.getTradeType());
        }

        super.checkAndSign(config);
    }
}
