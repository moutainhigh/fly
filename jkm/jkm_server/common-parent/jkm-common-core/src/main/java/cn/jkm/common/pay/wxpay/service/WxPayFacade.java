package cn.jkm.common.pay.wxpay.service;

import cn.jkm.common.pay.wxpay.bean.request.WxPayRefundRequest;
import cn.jkm.common.pay.wxpay.bean.request.WxPayUnifiedOrderRequest;
import cn.jkm.common.pay.wxpay.bean.result.*;
import cn.jkm.common.pay.wxpay.exception.WxErrorException;

import java.util.Map;

/**
 * Created by admin on 2017/6/19.
 */
public interface WxPayFacade {

    /**
     * 统一下单，在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
     * @param request
     * @return
     * @throws WxErrorException
     */
    WxPayUnifiedOrderResult unifiedOrder(WxPayUnifiedOrderRequest request) throws WxErrorException;

    /**
     * 该接口调用“统一下单”接口，并拼装发起支付请求需要的参数
     * @param request
     * @return
     * @throws WxErrorException
     */
    Map<String, String> getPayInfo(WxPayUnifiedOrderRequest request) throws WxErrorException;

    /**
     * 读取支付结果通知
     * @param xmlData
     * @return
     * @throws WxErrorException
     */
    WxPayOrderNotifyResult getOrderNotifyResult(String xmlData) throws WxErrorException;

    /**
     * 查询订单
     * @param transactionId
     * @param outTradeNo
     * @return
     * @throws WxErrorException
     */
    WxPayOrderQueryResult queryOrder(String transactionId, String outTradeNo) throws WxErrorException;

    /**
     * 关闭订单
     * @param outTradeNo
     * @return
     * @throws WxErrorException
     */
    WxPayOrderCloseResult closeOrder(String outTradeNo) throws WxErrorException;

    /**
     * 微信支付-申请退款
     * @param request
     * @return
     * @throws WxErrorException
     */
    WxPayRefundResult refund(WxPayRefundRequest request) throws WxErrorException;

    /**
     * 微信支付-查询退款
     * @param transactionId
     * @param outTradeNo
     * @param outRefundNo
     * @param refundId
     * @return
     * @throws WxErrorException
     */
    WxPayRefundQueryResult refundQuery(String transactionId, String outTradeNo, String outRefundNo, String refundId) throws WxErrorException;
}
