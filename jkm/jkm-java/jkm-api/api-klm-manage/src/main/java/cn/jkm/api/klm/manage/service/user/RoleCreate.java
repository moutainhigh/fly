package cn.jkm.api.klm.manage.service.user;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.user.SysRole;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.sys.SysMenuService;
import cn.jkm.service.sys.SysRoleService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Xia Guosong on 2017/7/21.
 * 新增角色
 */
@Component("roleCreate1.0")
@NotNull(name = {"json"}, message = "缺少参数")
public class RoleCreate extends AbstractManageService {

    @Reference(version = "1.0")
    SysRoleService sysRoleService;

    @Reference(version = "1.0")
    SysMenuService sysMenuService;

    @Override
    public Map service(Map request) {
        String json = formatString(request.get("json"));
        SysRole sysRole = JSON.parseObject(json, SysRole.class);
        sysRoleService.create(sysRole);
        return ApiResponse.success();
    }
}


