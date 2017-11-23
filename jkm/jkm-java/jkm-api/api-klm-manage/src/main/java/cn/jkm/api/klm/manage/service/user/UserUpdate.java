package cn.jkm.api.klm.manage.service.user;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.sys.SysUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * Created by Xia Guosong on 2017/7/18.
 * 修改系统用户（角色/账户名）
 */
@Component("userUpdate1.0")
@NotNull(name = {"userId", "roleId", "accountName"}, message = "缺少参数")
public class UserUpdate extends AbstractManageService {

    @Reference(version = "1.0")
    SysUserService sysUserService;

    @Override
    public Map service(Map request) {
        sysUserService.update(formatString(request.get("userId")),
                formatString(request.get("roleId")),
                formatString(request.get("accountName")),
                formatString(request.get("headerUrl"))
        );
        return ApiResponse.success();
    }
}
