package cn.jkm.api.klm.manage.service.user;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.user.SysUser;
import cn.jkm.core.domain.type.Status;
import cn.jkm.core.domain.type.UserStatus;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.sys.SysUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xia Guosong on 2017/7/17.
 * 后台用户列表
 */
@Component("sysUserList1.0")
public class SysUserList extends AbstractManageService {

    @Reference(version = "1.0")
    SysUserService sysUserService;

    @Override
    public Map service(Map request) {
        String userStatus = formatString(request.get("userStatus"));
        String roleId = formatString(request.get("roleId"));
        String accountName = formatString(request.get("accountName"));
        final ListResult<SysUser> listResult = sysUserService.list(StringUtils.isEmpty(userStatus) ? null : UserStatus.valueOf(userStatus), roleId, accountName, limit(request), page(request));
        return ApiResponse.success().body(new HashMap() {{
            put("count", listResult.getCount());
            put("list", listResult.getList());
        }});
    }
}
