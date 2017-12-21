package cn.jkm.common.pay.wxpay.util;

import cn.jkm.common.pay.wxpay.bean.result.WxError;
import cn.jkm.common.pay.wxpay.exception.WxErrorException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 14:59 2017/6/19
 */
public class BeanUtils {
    private static Logger log = LoggerFactory.getLogger(BeanUtils.class);

    public BeanUtils() {
    }

    public static void checkRequiredFields(Object bean) throws WxErrorException {
        List<String> requiredFields = Lists.newArrayList();
        List<Field> fields = new ArrayList<Field>(Arrays.asList(bean.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(bean.getClass().getSuperclass().getDeclaredFields()));
        Iterator<Field> var3 = fields.iterator();

        while(var3.hasNext()) {
            Field field = (Field)var3.next();

            try {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if(field.isAnnotationPresent(Required.class) && (field.get(bean) == null || field.get(bean) instanceof String && StringUtils.isBlank(field.get(bean).toString()))) {
                    requiredFields.add(field.getName());
                }

                field.setAccessible(isAccessible);
            } catch (IllegalArgumentException | IllegalAccessException | SecurityException var6) {
                var6.printStackTrace();
            }
        }

        if(!requiredFields.isEmpty()) {
            String msg = "必填字段 " + requiredFields + " 必须提供值";
            log.debug(msg);
            throw new WxErrorException(WxError.newBuilder().setErrorMsg(msg).build());
        }
    }

    public static Map<String, String> xmlBean2Map(Object bean) {
        Map<String, String> result = Maps.newHashMap();
        List<Field> fields = new ArrayList<Field>(Arrays.asList(bean.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(bean.getClass().getSuperclass().getDeclaredFields()));
        Iterator<Field> var3 = fields.iterator();

        while(var3.hasNext()) {
            Field field = (Field)var3.next();

            try {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if(field.get(bean) == null) {
                    field.setAccessible(isAccessible);
                } else {
                    if(field.isAnnotationPresent(XStreamAlias.class)) {
                        result.put(((XStreamAlias)field.getAnnotation(XStreamAlias.class)).value(), field.get(bean).toString());
                    }

                    field.setAccessible(isAccessible);
                }
            } catch (IllegalArgumentException | IllegalAccessException | SecurityException var6) {
                var6.printStackTrace();
            }
        }

        return result;
    }
}
