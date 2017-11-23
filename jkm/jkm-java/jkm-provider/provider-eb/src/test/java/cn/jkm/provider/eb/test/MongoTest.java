package cn.jkm.provider.eb.test;

import cn.jkm.core.domain.mongo.content.Expert;
import cn.jkm.framework.mysql.Jdbc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhong on 2017/7/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
class MongoTest {
    @Test
    public void testMongoAdd(){
        Expert expert = new Expert();
        //expert.setAddTime(System.currentTimeMillis());
        expert.setAge(43);
        expert.setAvatar("");
        expert.setName("madaifu");
        expert.setId("1111");
        //Mongo.buildMongo("expert").insert(expert);
        Jdbc.build().insert(expert);
    }
}
