package cn.jkm.api.klm.manage.service.eb.order;/**
 * Created by admin on 2017/7/26.
 */

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.content.SettingBanner;
import cn.jkm.core.domain.mysql.order.Order;
import cn.jkm.core.domain.type.OrderState;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.BannerService;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.eb.address.AddressService;
import cn.jkm.service.eb.order.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.jndi.cosnaming.IiopUrl;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.api.klm.manage.service.eb.order
 * @description 订单查询
 * @create 2017-07-26 15:50
 */
@Component("orderList1.0")
@NotNull(name = {"limit", "page", "state"}, message = "缺少参数")
public class OrderList extends AbstractManageService {

    @Reference(version = "1.0")
    OrderService orderService;

    @Reference(version = "1.0")
    AddressService addressService;

    @Override
    public Map service(Map request) {
        System.out.println(orderService);
        List<Order> orderList = orderService.findOrdersByState(formatString(request.get("state")),limit(request),page(request));
        long count = orderService.findOrderCount( formatString(request.get("state")));
        ListResult<Order> result = new ListResult<Order>(count, orderList);
        return ApiResponse.success().body(result);
    }


}
