package cn.jkm.api.klm.manage.service.eb.order.address;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.address.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

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
@Component("addressDelete1.0")
@NotNull(name = {"addressId"}, message = "缺少参数")
public class AddressDelete extends AbstractManageService {

    @Reference(version = "1.0")
    AddressService addressService;

    @Override
    public Map service(Map request) {
        System.out.println(addressService);
        boolean b = addressService.deletePersonalAddress(formatString(request.get("addressId")));
        return ApiResponse.success().body(ApiResponse.success());
    }

}
