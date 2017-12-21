package cn.jkm.api.klm.manage.service.eb.order.address;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.order.UserAddress;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.address.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.eb.order
 * @description 公司地址创建
 * @create 2017-07-27 10:30
 * @update 2017/7/27 10:30
 */
@Component("personalAddressList1.0")
@NotNull(name = {"userId","type","limit","page"}, message = "缺少参数")
public class PersonalAddressList extends AbstractManageService {

    @Reference(version = "1.0")
    AddressService addressService;

    @Override
    public Map service(Map request) {
        System.out.println(addressService);
        List<UserAddress> userAddressList = addressService.queryPersonalAddress(
                formatString(request.get("userId")),formatString(request.get("type")),formatInteger(request.get("limit")),formatInteger(request.get("page")));
        return ApiResponse.success().body(userAddressList);
    }

}
