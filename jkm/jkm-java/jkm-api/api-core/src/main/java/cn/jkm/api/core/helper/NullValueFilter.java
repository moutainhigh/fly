package cn.jkm.api.core.helper;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * Created by werewolf on 2017/7/15.
 */
public class NullValueFilter implements ValueFilter {

    @Override
    public Object process(Object object, String name, Object value) {
        if (value == null || "null".equals(value)) {
            return "";
        }
        return value ;
    }
}
