package cn.jkm.api.klm.manage.service.user;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.user.SysRole;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.sys.SysRoleService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xia Guosong on 2017/7/21.
 * 角色列表
 */
@Component("roleList1.0")
@NotNull(name = {"limit", "page"}, message = "缺少参数")
public class RoleList extends AbstractManageService {

    @Reference(version = "1.0")
    SysRoleService sysRoleService;

    @Override
    public Map service(Map request) {
        final ListResult<SysRole> listResult = sysRoleService.list(limit(request), page(request));
        return ApiResponse.success().body(new HashMap() {{
            put("sysRoles", new ArrayList() {{
                listResult.getList().forEach(v -> {
                    add(new HashMap() {{
                        put("roleId", v.getId());
                        put("roleName", v.getName());
                    }});
                });
            }});
            put("count", listResult.getCount());
        }});

    }
}
