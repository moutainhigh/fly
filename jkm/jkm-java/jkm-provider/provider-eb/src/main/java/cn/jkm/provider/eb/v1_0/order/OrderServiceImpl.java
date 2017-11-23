package cn.jkm.provider.eb.v1_0.order;

import cn.jkm.core.domain.mysql.order.Order;
import cn.jkm.core.domain.mysql.order.RefundOrderApplyment;
import cn.jkm.core.domain.type.OrderState;
import cn.jkm.core.domain.type.PayType;
import cn.jkm.core.domain.type.SendStatus;
import cn.jkm.framework.mysql.Jdbc;
import cn.jkm.service.eb.order.OrderService;
import cn.jkm.service.core.exception.ProviderException;

import java.util.HashMap;
import java.util.List;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.provider.eb.v1_0.order
 * @description 订单接口实现类
 * @create 2017-07-20 15:27
 */
public class OrderServiceImpl implements OrderService {


    @Override
    public boolean addOrder(Order order) {
        int insert = Jdbc.build().insert(order);
        if(insert==1)
            return true;
        return false;
    }

    @Override
    public List<Order> findOrdersByState(OrderState orderState, String userId, int limit, int page) {
        List<Order> list = Jdbc.build().query(Order.class).where("user_id=? and order_state=？", userId,orderState).page(limit,page);
        if(!list.isEmpty()){
            return list;
        }
        return null;
    }

    @Override
    public boolean updateOrderState(OrderState orderState, String userId) {
        return false;
    }

    @Override
    public boolean cancelOrder(String userId,String orderId) {
        // 判断用户是否存在.
        // 用戶訂單
        //User mu = userService.find.findManagerUserByUsername(manager);
        //if (mu == null)
         //   return "管理员不存在或者不具有此操作权限";
        Order order =	Jdbc.build().query(Order.class).find(orderId);
        if(order == null){
            throw  new ProviderException("订单不存在");
        }
        if(order.getOrderStatus()==null||order.getOrderStatus()!=OrderState.WAIT_PAY){
            throw  new ProviderException("订单当前状态不能被取消");
        }else if(order.getOrderStatus()==OrderState.WAIT_PAY){
                order.setOrderStatus(OrderState.CANCEL);
                order.setUpdateAt(System.currentTimeMillis()/1000);
        }else if(order.getOrderStatus()==OrderState.WAIT_DEVILEY){
            order.setOrderStatus(OrderState.EXAMINE);
            order.setUpdateAt(System.currentTimeMillis()/1000);
        }
        int set = Jdbc.build().update(Order.class).where("order_id=? ", orderId).set(new HashMap(){ {
            put("user_id",order.getUserId());
            put("order_status",order.getOrderStatus());
            put("update_at",order.getUpdateAt());
        }});
        if(set==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelOrderAccept(String userId, String orderId) {
        // 判断用户是否存在.
        // 用戶訂單
        //User mu = userService.find.findManagerUserByUsername(manager);
        //if (mu == null)
        //   return "管理员不存在或者不具有此操作权限";
        Order order =	Jdbc.build().query(Order.class).find(orderId);
        if(order == null){
            throw  new ProviderException("订单不存在");
        }
        if(order.getOrderStatus()==null||order.getOrderStatus()!=OrderState.WAIT_PAY){
            throw  new ProviderException("订单当前状态不能被取消");
        }else if(order.getOrderStatus()==OrderState.WAIT_PAY){
            order.setOrderStatus(OrderState.CANCEL);
            order.setUpdateAt(System.currentTimeMillis()/1000);
        }else if(order.getOrderStatus()==OrderState.WAIT_DEVILEY){
            order.setOrderStatus(OrderState.EXAMINE);
            order.setUpdateAt(System.currentTimeMillis()/1000);
        }
        int set = Jdbc.build().update(Order.class).where("order_id=? ", orderId).set(new HashMap(){ {
            put("user_id",order.getUserId());
            put("order_status",order.getOrderStatus());
            put("update_at",order.getUpdateAt());
        }});
        if(set==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelOrderReject(String userId, String orderId,String reject) {
        // 判断用户是否存在.
        // 用戶訂單
        //User mu = userService.find.findManagerUserByUsername(manager);
        //if (mu == null)
        //   return "管理员不存在或者不具有此操作权限";
        Order order =	Jdbc.build().query(Order.class).find(orderId);
        if(order == null){
            throw  new ProviderException("订单不存在");
        }
        if(order.getOrderStatus()==null){
            throw  new ProviderException("订单当前状态不能被取消");
        }else if(order.getOrderStatus()==OrderState.WAIT_DEVILEY){
            //order.setOrderStatus(OrderState);
            order.setReject(reject);
            order.setUpdateAt(System.currentTimeMillis()/1000);
        }
        int set = Jdbc.build().update(Order.class).where("order_id=? ", orderId).set(new HashMap(){ {
            put("reject",order);
            put("update_at",order.getUpdateAt());
        }});
        if(set==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean refundOrder(String userId, String orderId, RefundOrderApplyment refundOrderApplyment) {

        return false;
    }

    @Override
    public boolean orderPay(String userId, String orderId, PayType payType) {

        return false;
    }

    @Override
    public boolean notifySend(String userId, String orderId, String sellerId) {
        // 判断用户是否存在.
        // 用戶訂單
        //User mu = userService.find.findManagerUserByUsername(manager);
        //if (mu == null)
        //   return "管理员不存在或者不具有此操作权限";
        Order order =	Jdbc.build().query(Order.class).find(orderId);
        if(order == null){
            throw  new ProviderException("订单不存在");
        }
        if(order.getOrderStatus()==OrderState.WAIT_DEVILEY){
            order.setSendStatus(SendStatus.NOTIFY_PRODUCT);
            order.setNotifyDeliveryTime(System.currentTimeMillis()/1000);
            order.setUpdateAt(System.currentTimeMillis()/1000);
        }
        int set = Jdbc.build().update(Order.class).where("order_id=? ", orderId).set(new HashMap(){ {
            put("send_status",order.getSendStatus());
            put("notify_delivery_time",order.getNotifyDeliveryTime());
            put("update_at",order.getUpdateAt());
        }});
        if(set==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean notifyPay(String userId, String orderId, String sellerId) {

        return false;
    }

    @Override
    public boolean RefundOrderExamine(String userId, String orderId, String sellerId) {


        return false;
    }
}
