package cn.jkm.test.api.app;

import cn.jkm.framework.core.utils.Des3Utils;
import cn.jkm.framework.core.utils.DigestUtils;
import cn.jkm.framework.core.utils.UUIDUtils;
import cn.jkm.test.api.utils.HttpClientUtil;
import cn.jkm.test.api.utils.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/19.
 */
public abstract class AbstractAppTest {
    final static Logger log = LoggerFactory.getLogger("--[test api]--");

    public static final String APP_DATA_KEY = "8HkocpYLeG1LNi5m";

    public static final String APP_SIGN_KEY = "8HkocpYLeG1LNi5mNN00";

//    public static final String URL = "http://118.89.201.196:9092/api-klm-app/";
public static final String URL = "http://127.0.0.1:8080/app/";

    @Test
    public void testService() {
        Map<String, Object> businessData = params();
        final Long timestamp = System.currentTimeMillis();
        final String salt = UUIDUtils.sn(6);
        Map<String, Object> request = new HashMap() {{
            put("service", service());
            put("version", "1.0");
            put("timestamp", timestamp);
            if (encode()) {
                put("data", DigestUtils.encodeBase64(Des3Utils.encode(DigestUtils.MD5(salt + APP_DATA_KEY), JSON.toJSONString(businessData))));
            } else {
                put("data", JSON.toJSONString(businessData));
            }
            put("salt", salt);
        }};
        String _sign = DigestUtils.MD5(
                request.get("service") +
                        String.valueOf(timestamp) +
                        request.get("data") +
                        request.get("version") +
                        request.get("salt") + APP_SIGN_KEY);
        request.put("sign", _sign);
        try {

            HttpResponse response = HttpClientUtil.getInstance().get(URL + service(), request);
            log.info("----接口返回参数---" + response.getBody());
            JSONObject json = JSON.parseObject(response.getBody());
            Assert.assertEquals(json.getString("status"), "200");
            if (decode()) {

                String key = json.getString("key");
                String _body = new String(Des3Utils.decode(DigestUtils.MD5(key + APP_DATA_KEY),
                        DigestUtils.decodeBase64(json.getString("body").getBytes("utf-8"))), "utf-8");
                log.info("--body---解密后结果---:" + _body);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String service();

    protected abstract Map params();

    protected abstract Boolean decode();

    protected abstract Boolean encode();

}
