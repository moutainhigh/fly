package cn.jkm.service.eb.order;/**
 * Created by admin on 2017/7/20.
 */

import cn.jkm.core.domain.mysql.order.RefundOrderApplyment;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.serevice.eb.order
 * @description 退货订单接口
 * @create 2017-07-20 14:25
 * @update 2017/7/20 14:25
 */
public interface RefundOrderService {

    /**
     *
     * @param refundOrderApplyment
     * @return
     */
    public boolean addRefundOrderApplyment(RefundOrderApplyment refundOrderApplyment);


    /**
     * 订单退货通过
     * @param userId
     * @return Order实体.
     */
    public boolean refundOrderAccept(String userId,String orderId,String acceptReason);


    /**
     * 订单退货拒绝
     * @param userId
     * @return Order实体.
     */
    public boolean refundOrderReject(String userId,String orderId,String rejectReason);

    /**
     * 订单退货退款
     * @param userId
     * @return Order实体.
     */
    public boolean refundCash(String userId,String orderId);
}
