package cn.jkm.api.klm.manage.service.user;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.user.SysMenu;
import cn.jkm.core.domain.mongo.user.SysRole;
import cn.jkm.service.sys.SysMenuService;
import cn.jkm.service.sys.SysRoleService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xia Guosong on 2017/7/25.
 */
@Component("roleInfo1.0")
public class RoleInfo extends AbstractManageService {

    @Reference(version = "1.0")
    SysRoleService sysRoleService;

    @Reference(version = "1.0")
    SysMenuService sysMenuService;

    @Override
    public Map service(Map request) {
        List<SysMenu> sysMenus = sysMenuService.list();
        String roleId = formatString(request.get("roleId"));
        if (StringUtils.isEmpty(roleId)) {
            return ApiResponse.success().body(sysMenus);  //新增，只返回菜单列表
        }
        SysRole sysRole = sysRoleService.find(roleId);
        return ApiResponse.success().body(new HashMap() {{ //修改，返回菜单列表和当前角色信息
            put("sysRole", sysRole);
            put("sysMenus", sysMenus);
        }});
    }
}
