package cn.jkm.api.klm.manage.service.eb.order;/**
 * Created by admin on 2017/7/27.
 */

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.order.Order;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.eb.order.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.eb.order
 * @description 订单详情查询
 * @create 2017-07-27 9:41
 * @update 2017/7/27 9:41
 */
@Component("orderDetail1.0")
@NotNull(name = {"limit", "page", "orderId"}, message = "缺少参数")
public class OrderDetail extends AbstractManageService {

        @Reference(version = "1.0")
        OrderService orderService;

        @Override
        public Map service(Map request) {
            System.out.println(orderService);
            Map<String,Object> orderMap = orderService.queryOrder(formatString(request.get("orderId")),limit(request),page(request));
            return ApiResponse.success().body(orderMap);
        }
}
