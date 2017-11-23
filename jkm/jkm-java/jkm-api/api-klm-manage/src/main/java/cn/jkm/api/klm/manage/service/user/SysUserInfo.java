package cn.jkm.api.klm.manage.service.user;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.user.SysUser;
import cn.jkm.service.sys.SysRoleService;
import cn.jkm.service.sys.SysUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xia Guosong on 2017/7/21.
 */
@Component("sysUserInfo1.0")
public class SysUserInfo extends AbstractManageService {

    @Reference(version = "1.0")
    SysUserService sysUserService;

    @Reference(version = "1.0")
    SysRoleService sysRoleService;

    @Override
    public Map service(Map request) {
        List sysRoles = new ArrayList() {{
            sysRoleService.list().forEach(v -> {
                add(new HashMap() {{
                    put("roleId", v.getId());
                    put("roleName", v.getName());
                }});
            });
        }};
        String userId = formatString(request.get("userId"));
        if (StringUtils.isEmpty(userId)) {
            return ApiResponse.success().body(sysRoles);// 新增，只返回角色列表
        }
        SysUser sysUser = sysUserService.find(userId);
        return ApiResponse.success().body(new HashMap(){{ //修改，返回角色列表与当前用户信息
            put("sysUser", sysUser);
            put("sysRoles", sysRoles);
        }});
    }
}
