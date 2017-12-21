package cn.jkm.api.klm.manage.service.user;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.user.User;
import cn.jkm.core.domain.type.Gender;
import cn.jkm.core.domain.type.UserStatus;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.sys.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xia Guosong on 2017/7/21.
 * APP用户列表
 */
@Component("userList1.0")
public class UserList extends AbstractManageService  {

    @Reference(version = "1.0")
    UserService userService;

    @Override
    public Map service(Map request) {
        String userStatus = formatString(request.get("userStatus"));
        String type = formatString(request.get("type"));
        String gender = formatString(request.get("gender"));
        String keyword = formatString(request.get("keyword"));
        final ListResult<User> listResult = userService.list(StringUtils.isEmpty(userStatus) ? null : UserStatus.valueOf(userStatus),
                StringUtils.isEmpty(type) ? null : User.UserType.valueOf(type),
                StringUtils.isEmpty(gender) ? null : Gender.valueOf(gender),
                keyword, limit(request), page(request));
        return ApiResponse.success().body(new HashMap(){{
            put("count", listResult.getCount());
            put("list", listResult.getList());
        }});
    }
}
