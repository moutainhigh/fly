package com.hsd.mysql.rowmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


public class AnnotationJdbcInsert {
    private static Logger log = LoggerFactory.getLogger(AnnotationJdbcInsert.class);

    private String tableName;
    private JdbcTemplate jdbcTemplate;

    public AnnotationJdbcInsert(String tableName, JdbcTemplate jdbcTemplate) {
        this.tableName = tableName;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Number executeAndReturnKey() {
        //nothing todo
        return null;
    }

    public Integer executeAndReturnRows(Object object) {
        Map<String, Object> map = getColumnWithValue(object);
        StringJoiner column = new StringJoiner(",", "(", ")");
        StringJoiner value = new StringJoiner(",", "(", ")");
        map.forEach((k, v) -> {
            column.add(k);
            value.add(v.toString());
        });
        String sql = "insert into " + this.tableName + column.toString() + "values" + value.toString();
        log.info("insert sql :" + sql);
        return jdbcTemplate.update(sql);
    }

    public static String firstCharToUpperCase(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);

    }

    public Map getColumnWithValue(Object obj) {
        Class clazz = (Class) obj.getClass();
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        Map<String, Object> result = new HashMap<>();

        while (clazz != null) {
            clazz = clazz.getSuperclass();
            if (clazz.getName().equals("java.lang.Object")) {
                break;
            }
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        Set<String> getMethods = readableProperty(obj.getClass());
        for (Field field : fields) {
            try {
                String columnName = null;
                field.setAccessible(true);
                if (field.isAnnotationPresent(Transient.class)) {
                    continue;
                }
                if (!getMethods.contains("get" + firstCharToUpperCase(field.getName()))) {
                    continue;
                }
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    columnName = column.name();
                } else {
                    columnName = field.getName();
                }
                if (field.getGenericType().getTypeName().toLowerCase().endsWith("long") ||
                        field.getGenericType().getTypeName().toLowerCase().endsWith("int") ||
                        field.getGenericType().getTypeName().toLowerCase().endsWith("integer") ||
                        field.getGenericType().getTypeName().toLowerCase().endsWith("short")) {
                    result.put(columnName, field.get(obj));
                } else {
                    result.put(columnName, "'" + field.get(obj) + "'");
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    public Set<String> readableProperty(Class clazz) {
        Set<String> set = new HashSet<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                set.add(method.getName());
            }
        }
        return set;
    }

}
