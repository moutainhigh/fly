package cn.jkm.framework.mysql;


import cn.jkm.framework.mysql.operation.Delete;
import cn.jkm.framework.mysql.operation.Insert;
import cn.jkm.framework.mysql.operation.Query;
import cn.jkm.framework.mysql.operation.Update;
import com.alibaba.druid.pool.DruidDataSource;


import java.util.List;

/**
 * Created by werewolf on 2016/11/23.
 */
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
    public Long insert(Object object) {
        return Insert.insert(this.defaultDataSource, object, this.tableName);
    }


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
