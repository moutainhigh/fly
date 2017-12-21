package cn.jkm.provider.sys.test;

import cn.jkm.core.domain.mongo.user.SysMenu;
import cn.jkm.core.domain.mysql.user.User;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.provider.sys.v1_0.user.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Xia Guosong on 2017/7/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class SysMenuServiceTest {

    @Test
    public void testCreate() {
        Mongo mongo = Mongo.buildMongo();
        SysMenu sysMenu1 = new SysMenu("内容管理");
        SysMenu sysMenu2 = new SysMenu("账号管理");
        mongo.insert(sysMenu1);
        mongo.insert(sysMenu2);
        SysMenu sysMenu1_1 = new SysMenu("帖子", sysMenu1.getId());
        SysMenu sysMenu1_2 = new SysMenu("新闻", sysMenu1.getId());
        SysMenu sysMenu2_1 = new SysMenu("用户管理","www.baidu.com", sysMenu2.getId());
        SysMenu sysMenu2_2 = new SysMenu("专家管理","www.baidu.com", sysMenu2.getId());
        SysMenu sysMenu2_3 = new SysMenu("账号权限管理","www.baidu.com", sysMenu2.getId());
        SysMenu sysMenu2_4 = new SysMenu("用户行为设置","www.baidu.com", sysMenu2.getId());
        SysMenu sysMenu2_5 = new SysMenu("密码修改","www.baidu.com", sysMenu2.getId());
        mongo.insert(sysMenu1_1);
        mongo.insert(sysMenu1_2);
        mongo.insert(sysMenu2_1);
        mongo.insert(sysMenu2_2);
        mongo.insert(sysMenu2_3);
        mongo.insert(sysMenu2_4);
        mongo.insert(sysMenu2_5);
        SysMenu sysMenu1_1_1 = new SysMenu("帖子列表","www.baidu.com", sysMenu1_1.getId());
        SysMenu sysMenu1_1_2 = new SysMenu("审核列表","www.baidu.com", sysMenu1_1.getId());
        SysMenu sysMenu1_1_3 = new SysMenu("投诉列表","www.baidu.com", sysMenu1_1.getId());
        SysMenu sysMenu1_1_4 = new SysMenu("回收站","www.baidu.com", sysMenu1_1.getId());
        mongo.insert(sysMenu1_1_1);
        mongo.insert(sysMenu1_1_2);
        mongo.insert(sysMenu1_1_3);
        mongo.insert(sysMenu1_1_4);
        SysMenu sysMenu1_2_1 = new SysMenu("新闻列表", "www.baidu.com", sysMenu1_2.getId());
        SysMenu sysMenu1_2_2 = new SysMenu("投书列表", "www.baidu.com", sysMenu1_2.getId());
        SysMenu sysMenu1_2_3 = new SysMenu("回收站", "www.baidu.com", sysMenu1_2.getId());
        mongo.insert(sysMenu1_2_1);
        mongo.insert(sysMenu1_2_2);
        mongo.insert(sysMenu1_2_3);
    }

    @Test
    public void testFindUser() {
        User user = new UserServiceImpl().find("597846b51ae734b5d41cd21c");
        System.out.println(user.getAge());
    }
}
