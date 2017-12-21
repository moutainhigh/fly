package com.hsd.mysql.operation;

import com.alibaba.druid.pool.DruidDataSource;
import com.hsd.mysql.Jdbc2Template;

import java.util.List;

public class Insert extends AbstractOperation {


    public Insert(DruidDataSource dataSource, Object obj, String tableName) {
        this.clazz = obj.getClass();
        this.jdbc2Template = new Jdbc2Template(dataSource);
        this.tableName = tableName;
    }

    public static Integer insert(DruidDataSource dataSource, Object obj, String tableName) {
        return new Insert(dataSource, obj, tableName)._insert2(obj);
    }

    public static Long insert2(DruidDataSource dataSource, Object obj, String tableName) {
        return new Insert(dataSource, obj, tableName)._insert(obj);
    }

    public static int[] insertBatch(DruidDataSource dataSource, List list, String tableName) {
        if (list == null || list.size() == 0) {
            return null;
        }
        return new Insert(dataSource, list.get(0), tableName)._insertBatch(list);
    }

    public Long _insert(Object obj) {
        return this.jdbc2Template.insert(obj, this.tableName());
    }

    public int _insert2(Object obj) {

        return this.jdbc2Template.insert2(obj, this.tableName());
    }

    public int[] _insertBatch(List list) {
        return this.jdbc2Template.insertBatch(list, this.tableName());
    }


    @Override
    protected StringBuilder sqlPrefix() {
        return null;
    }
}
