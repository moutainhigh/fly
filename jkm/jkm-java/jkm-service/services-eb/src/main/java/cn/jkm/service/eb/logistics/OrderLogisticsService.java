package cn.jkm.service.eb.logistics;/**
 * Created by admin on 2017/7/20.
 */

import cn.jkm.core.domain.mysql.order.Order;
import cn.jkm.core.domain.mysql.order.OrderLogistics;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.serevice.eb.logistics
 * @description 订单物流接口
 * @create 2017-07-20 14:17
 * @update 2017/7/20 14:17
 */
public interface OrderLogisticsService {

    /**
     *  新增订单物流
     * @param orderLogistics
     * @return
     */
    public boolean addOrderLogistics(OrderLogistics orderLogistics);

}
