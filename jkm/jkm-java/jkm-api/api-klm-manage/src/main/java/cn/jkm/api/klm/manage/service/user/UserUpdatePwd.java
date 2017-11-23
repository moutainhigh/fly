package cn.jkm.api.klm.manage.service.user;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.user.SysUser;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.sys.SysUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Xia Guosong on 2017/7/18.
 * 修改系统用户密码
 */
@Component("userUpdatePwd1.0")
@NotNull(name = {"userId", "oldPwd", "newPwd"}, message = "缺少参数")
public class UserUpdatePwd extends AbstractManageService {

    @Reference(version = "1.0")
    SysUserService sysUserService;

    @Override
    public Map service(Map request) {
        String userId = formatString(request.get("userId"));
        String oldPwd = formatString(request.get("oldPwd"));
        SysUser sysUser = sysUserService.find(userId);
        if (oldPwd.equals(sysUser.getPassword())) {
            return ApiResponse.fail("旧密码错误");
        }
        sysUserService.updatePwd(userId, formatString(request.get("newPwd")));
        return ApiResponse.success();
    }
}
