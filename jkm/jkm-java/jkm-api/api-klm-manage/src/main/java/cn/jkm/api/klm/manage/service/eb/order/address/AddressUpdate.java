package cn.jkm.api.klm.manage.service.eb.order.address;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.order.UserAddress;
import cn.jkm.core.domain.type.AddressType;
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
 * @description 公司地址更新 .
 * @create 2017-07-27 10:30
 * @update 2017/7/27 10:30
 */
@Component("addressUpdate1.0")
@NotNull(name = {"userId", "userName", "mobile","addDetail","addressId"}, message = "缺少参数")
public class AddressUpdate extends AbstractManageService {

    @Reference(version = "1.0")
    AddressService addressService;

    @Override
    public Map service(Map request) {
        UserAddress address = new UserAddress();
        address.setUserName(formatString(request.get("userName")));
        address.setMobile(formatString(request.get("mobile")));
        address.setId(formatString(request.get("addressId")));
        address.setAddDetail(formatString(request.get("addDetail")));
        address.setAddressType(AddressType.PERSONAL);
        addressService.updatePersonalAddress(address);
        return ApiResponse.success().body(null);
    }

}
