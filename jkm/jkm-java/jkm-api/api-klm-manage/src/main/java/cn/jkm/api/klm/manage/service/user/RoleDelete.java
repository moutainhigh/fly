package cn.jkm.api.klm.manage.service.user;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.sys.SysRoleService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Xia Guosong on 2017/7/19.
 * 删除角色
 */
@Component("roleDelete1.0")
@NotNull(name = {"roleId"}, message = "缺少参数")
public class RoleDelete extends AbstractManageService {

    @Reference(version = "1.0")
    SysRoleService sysRoleService;

    @Override
    public Map service(Map request) {
        sysRoleService.delete(formatString(request.get("roleId")));
        return ApiResponse.success();
    }
}
