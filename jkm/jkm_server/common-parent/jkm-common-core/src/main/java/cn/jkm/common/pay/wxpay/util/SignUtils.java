package cn.jkm.common.pay.wxpay.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 14:59 2017/6/19
 */
public class SignUtils {

    public SignUtils() {
    }

    public static String createSign(Object xmlBean, String signKey) {
        return createSign(BeanUtils.xmlBean2Map(xmlBean), signKey);
    }

    public static String createSign(Map<String, String> params, String signKey) {
        SortedMap<String, String> sortedMap = new TreeMap<String, String>(params);
        StringBuilder toSign = new StringBuilder();
        Iterator<String> var4 = sortedMap.keySet().iterator();

        while (var4.hasNext()) {
            String key = (String) var4.next();
            String value = (String) params.get(key);
            if (StringUtils.isNotEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }

        toSign.append("key=").append(signKey);
        return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
    }

    public static boolean checkSign(Object xmlBean, String signKey) {
        return checkSign(BeanUtils.xmlBean2Map(xmlBean), signKey);
    }

    public static boolean checkSign(Map<String, String> params, String signKey) {
        String sign = createSign(params, signKey);
        return sign.equals(params.get("sign"));
    }
}
