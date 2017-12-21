package com.hsd.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import com.hsd.core.spring.SpringApplicationContext;



public class Jdbc {


    public static JdbcOperation build() {
        return new JdbcOperation(SpringApplicationContext.getBean("dataSource"));
    }


    public static JdbcOperation build(DruidDataSource dataSource) {
        return new JdbcOperation(dataSource);
    }


    public static JdbcOperation build(String tableName) {
        return new JdbcOperation(SpringApplicationContext.getBean("dataSource"), tableName);
    }

    public static JdbcOperation build(DruidDataSource dataSource, String tableName) {
        return new JdbcOperation(dataSource, tableName);
    }


}
