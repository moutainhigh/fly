package cn.jkm.common.pay.wxpay.service;

import cn.jkm.common.pay.wxpay.bean.request.*;
import cn.jkm.common.pay.wxpay.bean.result.*;
import cn.jkm.common.pay.wxpay.config.WxPayConfig;
import cn.jkm.common.pay.wxpay.exception.WxErrorException;
import cn.jkm.common.pay.wxpay.util.SignUtils;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.net.SSLSocketHttpConnectionProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/6/19.
 */
public class WxPayService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private WxPayConfig config;

    public WxPayService() {
    }

    public WxPayConfig getConfig() {
        return this.config;
    }

    public void setConfig(WxPayConfig config) {
        this.config = config;
    }

    private String getPayBaseUrl() {
        return this.getConfig().useSandbox()?"https://api.mch.weixin.qq.com/sandboxnew":"https://api.mch.weixin.qq.com";
    }

    public WxPayUnifiedOrderResult unifiedOrder(WxPayUnifiedOrderRequest request) throws WxErrorException {
        request.checkAndSign(this.getConfig());
        String url = this.getPayBaseUrl() + "/pay/unifiedorder";
        String responseContent = this.post(url, request.toXML());
        WxPayUnifiedOrderResult result = (WxPayUnifiedOrderResult) WxPayBaseResult.fromXML(responseContent, WxPayUnifiedOrderResult.class);
        result.checkResult(this);
        return result;
    }

    public Map<String, String> getPayInfo(WxPayUnifiedOrderRequest request) throws WxErrorException {
        WxPayUnifiedOrderResult unifiedOrderResult = this.unifiedOrder(request);
        String prepayId = unifiedOrderResult.getPrepayId();
        if(StringUtils.isBlank(prepayId)) {
            throw new RuntimeException(String.format("无法获取prepay id，错误代码： '%s'，信息：%s。", new Object[]{unifiedOrderResult.getErrCode(), unifiedOrderResult.getErrCodeDes()}));
        } else {
            Map<String, String> payInfo = new HashMap<String, String>();
            if("NATIVE".equals(request.getTradeType())) {
                payInfo.put("codeUrl", unifiedOrderResult.getCodeURL());
            } else if("APP".equals(request.getTradeType())) {
                String appId = this.getConfig().getAppId();
                Map<String, String> configMap = new HashMap<String, String>();
                String partnerid = this.getConfig().getMchId();
                configMap.put("prepayid", prepayId);
                configMap.put("partnerid", partnerid);
                configMap.put("package", "Sign=WXPay");
                configMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
                configMap.put("noncestr", String.valueOf(System.currentTimeMillis()));
                configMap.put("appid", appId);
                payInfo.put("sign", SignUtils.createSign(configMap, this.getConfig().getMchKey()));
                payInfo.put("prepayId", prepayId);
                payInfo.put("partnerId", partnerid);
                payInfo.put("appId", appId);
                payInfo.put("packageValue", "Sign=WXPay");
                payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000L));
                payInfo.put("nonceStr", String.valueOf(System.currentTimeMillis()));
            } else if("JSAPI".equals(request.getTradeType())) {
                payInfo.put("appId", unifiedOrderResult.getAppid());
                payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000L));
                payInfo.put("nonceStr", String.valueOf(System.currentTimeMillis()));
                payInfo.put("package", "prepay_id=" + prepayId);
                payInfo.put("signType", "MD5");
                payInfo.put("paySign", SignUtils.createSign(payInfo, this.getConfig().getMchKey()));
            }

            return payInfo;
        }
    }

    public WxPayOrderNotifyResult getOrderNotifyResult(String xmlData) throws WxErrorException {
        try {
            this.log.debug("微信支付回调参数详细：{}", xmlData);
            WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            this.log.debug("微信支付回调结果对象：{}", result);
            result.checkResult(this);
            return result;
        } catch (WxErrorException var3) {
            this.log.error(var3.getMessage(), var3);
            throw var3;
        } catch (Exception var4) {
            this.log.error(var4.getMessage(), var4);
            throw new WxErrorException(WxError.newBuilder().setErrorMsg("发生异常" + var4.getMessage()).build());
        }
    }

    public WxPayOrderQueryResult queryOrder(String transactionId, String outTradeNo) throws WxErrorException {
        WxPayOrderQueryRequest request = new WxPayOrderQueryRequest();
        request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
        request.setTransactionId(StringUtils.trimToNull(transactionId));
        request.checkAndSign(this.getConfig());
        String url = this.getPayBaseUrl() + "/pay/orderquery";
        String responseContent = this.post(url, request.toXML());
        if(StringUtils.isBlank(responseContent)) {
            throw new WxErrorException(WxError.newBuilder().setErrorMsg("无响应结果").build());
        } else {
            WxPayOrderQueryResult result = (WxPayOrderQueryResult)WxPayBaseResult.fromXML(responseContent, WxPayOrderQueryResult.class);
            result.checkResult(this);
            return result;
        }
    }

    public WxPayOrderCloseResult closeOrder(String outTradeNo) throws WxErrorException {
        if(StringUtils.isBlank(outTradeNo)) {
            throw new IllegalArgumentException("out_trade_no不能为空");
        } else {
            WxPayOrderCloseRequest request = new WxPayOrderCloseRequest();
            request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
            request.checkAndSign(this.getConfig());
            String url = this.getPayBaseUrl() + "/pay/closeorder";
            String responseContent = this.post(url, request.toXML());
            WxPayOrderCloseResult result = (WxPayOrderCloseResult)WxPayBaseResult.fromXML(responseContent, WxPayOrderCloseResult.class);
            result.checkResult(this);
            return result;
        }
    }

    public WxPayRefundResult refund(WxPayRefundRequest request) throws WxErrorException {
        request.checkAndSign(this.getConfig());
        String url = this.getPayBaseUrl() + "/secapi/pay/refund";
        String responseContent = this.postWithKey(url, request.toXML());
        WxPayRefundResult result = (WxPayRefundResult)WxPayBaseResult.fromXML(responseContent, WxPayRefundResult.class);
        result.checkResult(this);
        return result;
    }

    public WxPayRefundQueryResult refundQuery(String transactionId, String outTradeNo, String outRefundNo, String refundId) throws WxErrorException {
        WxPayRefundQueryRequest request = new WxPayRefundQueryRequest();
        request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
        request.setTransactionId(StringUtils.trimToNull(transactionId));
        request.setOutRefundNo(StringUtils.trimToNull(outRefundNo));
        request.setRefundId(StringUtils.trimToNull(refundId));
        request.checkAndSign(this.getConfig());
        String url = this.getPayBaseUrl() + "/pay/refundquery";
        String responseContent = this.post(url, request.toXML());
        WxPayRefundQueryResult result = (WxPayRefundQueryResult)WxPayBaseResult.fromXML(responseContent, WxPayRefundQueryResult.class);
        result.composeRefundRecords();
        result.checkResult(this);
        return result;
    }

    private String post(String url, String xmlParam) {
        String requestString = xmlParam;

        try {
            requestString = new String(xmlParam.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException var9) {
            var9.printStackTrace();
        }

        HttpRequest request = (HttpRequest) HttpRequest.post(url).body(requestString);
        HttpResponse response = request.send();
        String responseString = null;

        try {
            responseString = new String(response.bodyText().getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException var8) {
            var8.printStackTrace();
        }

        this.log.debug("\n[URL]: {}\n[PARAMS]: {}\n[RESPONSE]: {}", new Object[]{url, xmlParam, responseString});
        return responseString;
    }

    private String postWithKey(String url, String requestStr) throws WxErrorException {
        try {
            SSLContext sslContext = this.getConfig().getSslContext();
            if(null == sslContext) {
                sslContext = this.getConfig().initSSLContext();
            }

            HttpRequest request = HttpRequest.post(url).withConnectionProvider(new SSLSocketHttpConnectionProvider(sslContext));
            request.bodyText(requestStr);
            HttpResponse response = request.send();
            String result = response.bodyText();
            this.log.debug("\n[URL]:  {}\n[PARAMS]: {}\n[RESPONSE]: {}", new Object[]{url, requestStr, result});
            return result;
        } catch (Exception var7) {
            this.log.error("\n[URL]:  {}\n[PARAMS]: {}\n[EXCEPTION]: {}", new Object[]{url, requestStr, var7.getMessage()});
            throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg(var7.getMessage()).build(), var7);
        }
    }
}
