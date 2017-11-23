package com.hsd.mysql;


import com.alibaba.druid.pool.DruidDataSource;
import com.hsd.mysql.operation.Delete;
import com.hsd.mysql.operation.Insert;
import com.hsd.mysql.operation.Query;
import com.hsd.mysql.operation.Update;

import java.util.List;


public class JdbcOperation {


    private DruidDataSource defaultDataSource;

    private String tableName;


    public JdbcOperation(DruidDataSource dataSource) {
        this.defaultDataSource = dataSource;
    }

    public JdbcOperation(DruidDataSource dataSource, String tableName) {
        this.defaultDataSource = dataSource;
        this.tableName = tableName;
    }


    public Jdbc2Template SQL() {
        return new Jdbc2Template(this.defaultDataSource);
    }

    //==========================
    public int insert(Object object) {
        return Insert.insert(this.defaultDataSource, object, this.tableName);
    }

//    public Long insert(Object object) {
//        return Insert.insert2(this.defaultDataSource, object, this.tableName);
//    }

    public int[] insertBatch(List list) {
        return Insert.insertBatch(this.defaultDataSource, list, this.tableName);
    }

    public Query query(Class clazz, String... columns) {
        return Query.query(clazz, this.tableName, this.defaultDataSource, columns);
    }

    public Delete delete(Class clazz) {
        return Delete.delete(clazz, this.tableName, this.defaultDataSource);
    }

    public Update update(Class clazz) {
        return Update.update(clazz, this.tableName, this.defaultDataSource);
    }


}
