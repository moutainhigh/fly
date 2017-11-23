package cn.jkm.api.klm.app.service;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.framework.core.validator.NotNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/27.
 */
@Component("test1.0")
@NotNull(name = {"accountName", "password"})
public class Test extends AbstractAppService {
    @Override
    public Map service(Map request) {
        return ApiResponse.success().body(new HashMap(){{
           put("accountName",request.get("accountName"));
            put("password",request.get("password"));
        }});
    }
}
