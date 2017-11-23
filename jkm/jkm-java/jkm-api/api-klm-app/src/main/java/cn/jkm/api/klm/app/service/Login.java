package cn.jkm.api.klm.app.service;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.framework.core.validator.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by werewolf on 2017/7/16.
 */
@Component("login1.0")
@NotNull(name = {"phone", "password"})
public class Login extends AbstractAppService {
    @Override
    public Map service(Map request) {
        return ApiResponse.success().body("用户信息");
    }
}
