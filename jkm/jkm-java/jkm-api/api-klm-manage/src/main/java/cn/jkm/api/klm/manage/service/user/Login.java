package cn.jkm.api.klm.manage.service.user;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.user.SysUser;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.sys.SysRoleService;
import cn.jkm.service.sys.SysUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by werewolf on 2017/7/16.
 * 后台用户登录
 */
@Component("login1.0")
@NotNull(name = {"accountName", "password"}, message = "缺少参数")
public class Login extends AbstractManageService {

    @Reference(version = "1.0")
    SysUserService sysUserService;

    @Reference(version = "1.0")
    SysRoleService sysRoleService;

    @Override
    public Map service(Map request) {
        final SysUser sysUser = sysUserService.login(
                formatString(request.get("accountName")),
                formatString(request.get("password"))
        );
        if (null == sysUser) {
            return ApiResponse.fail("用户名或者密码错误");
        }
        return ApiResponse.success().body(new HashMap(){{
            put("accountName",sysUser.getAccountName());
            put("headerUrl",sysUser.getHeadUrl());
            put("roleId", sysUser.getRoleId());
        }});
    }
}
