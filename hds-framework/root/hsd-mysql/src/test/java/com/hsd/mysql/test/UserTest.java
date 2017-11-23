package com.hsd.mysql.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hsd.mysql.Jdbc;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class UserTest {

    @Test
    public void insert() {
//        User user = Jdbc.build().query(User.class).find("3453asdsa");
//        System.out.println(user.getId());
//        System.out.println(user.getName());
        User user = new User();
        user.setName("!111sdfa");
        user.setId("sdafdsafdsaafdsa");

//        ClassUtils.getColumnWithValue(user);

        Jdbc.build().insert(user);
     //   System.out.println(Jdbc.build().SQL().jdbcTemplate().update("insert into test_user(id,name) values('1112323122','12321')"));


//        Jdbc.build().insert(user);
    }
}
