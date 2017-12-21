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
 * 新增后台用户
 */
@Component("userCreate1.0")
@NotNull(name = {"accountName", "password", "roleId"}, message = "缺少参数")
public class UserCreate extends AbstractManageService {

    @Reference(version = "1.0")
    SysUserService sysUserService;

    @Override
    public Map service(Map request) {
        String accountName = formatString(request.get("accountName"));
        String password = formatString(request.get("password"));
        String headerUrl = formatString(request.get("headerUrl"));
        String roleId = formatString(request.get("roleId"));
        sysUserService.create(accountName, password, headerUrl, roleId);
        return ApiResponse.success();
    }
}
