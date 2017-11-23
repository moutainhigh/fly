package cn.jkm.api.klm.manage.service.eb.order.address;/**
 * Created by admin on 2017/7/27.
 */

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.order.UserAddress;
import cn.jkm.core.domain.type.AddressType;
import cn.jkm.framework.core.utils.UUIDUtils;
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
 */
@Component("companyAddressCreate1.0")
@NotNull(name = {"userName", "mobile","addDetail"}, message = "缺少参数")
public class CompanyAddressCreate extends AbstractManageService {

    @Reference(version = "1.0")
    AddressService addressService;

    @Override
    public Map service(Map request) {
        UserAddress useraddress = new UserAddress();
        useraddress.setId(UUIDUtils.uuid());
        useraddress.setMobile(formatString(request.get("mobile")));
        useraddress.setUserName(formatString(request.get("userName")));
        useraddress.setAddressType(AddressType.COMPANY);
        useraddress.setAddDetail(formatString(request.get("addDetail")));
        addressService.addCompanyAddress(useraddress);
        return ApiResponse.success().body(null);
    }

}
