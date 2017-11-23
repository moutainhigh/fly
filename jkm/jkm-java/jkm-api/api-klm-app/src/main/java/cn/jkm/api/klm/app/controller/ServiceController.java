package cn.jkm.api.klm.app.controller;

import cn.jkm.api.core.controller.BaseController;
import cn.jkm.api.core.helper.ApiDefinition;
import cn.jkm.api.core.helper.RequestWarper;
import cn.jkm.api.core.helper.ServiceConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by werewolf on 2017/7/16.
 */
@Controller
@RequestMapping("/app")
public class ServiceController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "/{service}")
    public Object service(@PathVariable String service,
                          @RequestParam(value = "version") String version,
                          @RequestParam(value = "data", required = false) String data,
                          @RequestParam(value = "timestamp") Long timestamp,
                          @RequestParam(value = "salt") String salt,
                          @RequestParam(value = "sign") String sign,
                          HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false, value = "filedata") MultipartFile file) {


        return handler(new RequestWarper()
                .setService(service)
                .setVersion(version)
                .setData(data)
                .setTimestamp(timestamp)
                .setSalt(salt)
                .setSign(sign)
                .setRequest(request)
                .setResponse(response)
                .setCheckSign(true)
                .setEncodeResponse(find(service).getResponse())
                .setDecodeRequest(find(service).getResponse())
                .setDataKey(ApiDefinition.Key.APP_KEY_DATA)
                .setSignKey(ApiDefinition.Key.APP_KEY_SIGN)
        );
    }


    private static Map<String, ServiceConfig> serviceTable = new HashMap() {{
        put("login", new ServiceConfig("login", true, true));
        put("test", new ServiceConfig("test", true, true));
    }};


    public static ServiceConfig find(String service) {
        return serviceTable.get(service) == null ? new ServiceConfig(service, false, false) : serviceTable.get(service);
    }

}
