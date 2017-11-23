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
 */
@Component("sysUserUnlock1.0")
@NotNull(name = {"userId"}, message = "缺少参数")
public class SysUserUnlock extends AbstractManageService {

    @Reference(version = "1.0")
    SysUserService sysUserService;

    @Override
    public Map service(Map request) {
        sysUserService.unlock(formatString(request.get("userId")));
        return ApiResponse.success();
    }
}
