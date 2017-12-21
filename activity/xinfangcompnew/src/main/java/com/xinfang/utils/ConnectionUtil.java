package com.xinfang.utils;

import java.sql.Connection;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class ConnectionUtil {
	
	@Autowired    
    private static SqlSession sqlSession;
	
    public static Connection getConnection(){    
        Connection conn = null;    
        try {    
            conn =  sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();    
           System.out.println("===This Connection isClosed ? "+conn.isClosed());    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return conn;    
    }    

}
