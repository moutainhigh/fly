package cn.jkm.service.eb.order;/**
 * Created by admin on 2017/7/20.
 */

import cn.jkm.core.domain.mysql.content.SettingBanner;
import cn.jkm.core.domain.mysql.order.Order;
import cn.jkm.core.domain.mysql.order.RefundOrderApplyment;
import cn.jkm.core.domain.type.OrderState;
import cn.jkm.core.domain.type.PayType;

import java.util.List;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.serevice.eb
 * @description 订单接口
 * @create 2017-07-20 11:30
 */
public interface OrderService {

    /**
     *  新增订单
     */
    public boolean addOrder(Order order);

    /**
     * 根据订单状态查询订单(分页实现)
     * @param orderState
     * @return Order实体.
     */
    public List<Order> findOrdersByState(OrderState orderState,String userId,int limit, int page);

    /**
     * 修改订单状态
     * @param orderState
     * @return Order实体.
     */
    public boolean updateOrderState(OrderState orderState,String userId);

    /**
     * 取消订单
     * @param userId
     * @return Order实体.
     */
    public boolean cancelOrder(String userId,String orderId);

    /**
     * 取消订单
     * @param userId
     * @return Order实体.
     */
    public boolean cancelOrderAccept(String userId,String orderId);

    /**
     * 取消订单
     * @param userId
     * @return Order实体.
     */
    public boolean cancelOrderReject(String userId,String orderId,String reject);

    /**
     * 订单退货
     * @param userId
     * @return Order实体.
     */
    public boolean refundOrder(String userId, String orderId, RefundOrderApplyment refundOrderApplyment);

    /**
     * 订单支付
     * @param userId
     * @return Order实体.
     */
    public boolean orderPay(String userId, String orderId, PayType payType);

    /**
     *  通知发货
     * @param userId
     * @param orderId
     * @return
     */
    public boolean notifySend(String userId,String orderId,String sellerId);

    /**
     *  通知付款
     * @param userId
     * @param orderId
     * @return
     */
    public boolean notifyPay(String userId,String orderId,String sellerId);

    /**
     *  退货订单审核.
     * @param userId
     * @param orderId
     * @return
     */
    public boolean RefundOrderExamine(String userId,String orderId,String sellerId);
}
