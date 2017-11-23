package cn.jkm.api.core.controller;

import cn.jkm.api.core.helper.*;
import cn.jkm.framework.core.spring.SpringApplicationContext;
import cn.jkm.framework.core.utils.DateUtils;
import cn.jkm.framework.core.utils.Des3Utils;
import cn.jkm.framework.core.utils.DigestUtils;
import cn.jkm.framework.core.utils.UUIDUtils;
import cn.jkm.framework.core.validator.Validator;
import cn.jkm.framework.core.validator.ValidatorException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by werewolf on 2017/7/16.
 */
public class BaseController {

    private static final String SYSTEM_TIME_SERVICE = "systemTime";

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);


    protected Object handler(RequestWarper requestWarper) {
        try {

            if (SYSTEM_TIME_SERVICE.equals(requestWarper.getService())) {
                return doSystemTime();
            }

            logger.info("request info :" + JSON.toJSONString(requestWarper));
            ApiService apiService = SpringApplicationContext.getBean(requestWarper.getService() + requestWarper.getVersion());
            if (apiService == null) {
                return ApiResponse.fail(HttpStatus.NOT_SERVICE.getCode(), HttpStatus.NOT_SERVICE.getMessage());
            }

            //验证签名
            if (requestWarper.getCheckSign()) {
                //验证签名失败
                String _sign = DigestUtils.MD5(requestWarper.getService() + requestWarper.getTimestamp() + requestWarper.getData() + requestWarper.getVersion() + requestWarper.getSalt() + requestWarper.getSignKey());
                if (!_sign.equals(requestWarper.getSign())) {
                    return ApiResponse.fail(HttpStatus.SERVICE_FORBIDDEN.getCode(), HttpStatus.SERVICE_FORBIDDEN.getMessage());
                }

                //验证时间戳 60秒有效
                if (Math.abs(DateUtils.currentTimeSecs() - (requestWarper.getTimestamp() / 1000)) > 60) {
                    return ApiResponse.fail(HttpStatus.TIME_OUT.getCode(), HttpStatus.TIME_OUT.getMessage());
                }

            }
            if (requestWarper.getDecodeRequest()) {
                //解密 data
                if (requestWarper.getData() != null) {
                    requestWarper.setData(new String(Des3Utils.decode(DigestUtils.MD5(requestWarper.getSalt() + requestWarper.getDataKey()),
                            DigestUtils.decodeBase64(requestWarper.getData().getBytes("utf-8"))), "utf-8"));
                }
            }

            //接口请求参数验证
            final Map requestParams = StringUtils.isEmpty(requestWarper.getData()) ? new HashMap() : JSON.parseObject(requestWarper.getData(), HashMap.class);
            requestParams.put("ip", WebToolsUtils.ip(requestWarper.getRequest()));
            //接口参数验证
            Validator.validate(apiService.getClass(), requestParams);

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Map result = apiService.service(requestParams);
            stopWatch.stop();

            logger.info(requestWarper.getService() + requestWarper.getVersion() + "--execute time millis---" + stopWatch.getTotalTimeMillis());

            if (requestWarper.getEncodeResponse() && result.get("body") != null) {
                final String key = UUIDUtils.sn();//加密随机KEY
                result.put("key", key);
                result.put("body", DigestUtils.encodeBase64(
                        Des3Utils.encode(
                                DigestUtils.MD5(key + requestWarper.getDataKey()), JSON.toJSONString(result.get("body"), ApiResponse.filter, SerializerFeature.WriteMapNullValue)
                        )
                ));
            }
            return result;
        } catch (ValidatorException e) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("--Exception---", e);
            return ApiResponse.fail("系统错误");
        }
    }

    protected Object doSystemTime() {
        return ApiResponse.success().body(new HashMap() {{
            put("timestamp", System.currentTimeMillis());
        }});

    }
}
