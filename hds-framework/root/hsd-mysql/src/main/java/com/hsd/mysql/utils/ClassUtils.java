package com.hsd.mysql.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.*;


public class ClassUtils {


    public static <T> T getBean(Map source, Class clazz) {
        T obj = (T) org.springframework.beans.BeanUtils.instantiate(clazz);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        for (Object s : source.keySet()) {
            try {
                bw.setPropertyValue(String.valueOf(s), "null".equals(source.get(s)) ? null : source.get(s));
            } catch (NotWritablePropertyException ex) {
            }
        }
        return obj;
    }


    public static Map getDBMap(Map source, Class clazz) {
        Map<String, String> propertyColumn = getPropertyWithColumn(clazz);
        return new HashMap() {{
            source.forEach((k, v) -> {
                if (null != propertyColumn.get(k)) {
                    put(propertyColumn.get(k), v);
                }
            });
        }};
    }



    public static Map getPropertyWithColumn(Class clazz) {
        //灞炴�с�丂鏁版嵁搴撳垪
        Map<String, String> propertyColumn = new HashMap<>();
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));

        while (clazz != null) {
            clazz = clazz.getSuperclass();
            if (clazz.getName().equals("java.lang.Object")) {
                break;
            }
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                propertyColumn.put(field.getName(), column.name());
            } else {
                propertyColumn.put(field.getName(), field.getName());
            }
        }
        return propertyColumn;
    }
}
