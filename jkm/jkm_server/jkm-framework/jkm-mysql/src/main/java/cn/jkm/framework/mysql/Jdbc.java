package cn.jkm.framework.mysql;

import cn.jkm.framework.core.spring.SpringApplicationContext;
import com.alibaba.druid.pool.DruidDataSource;


/**
 * Created by werewolf on 2016/11/23.
 */
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
