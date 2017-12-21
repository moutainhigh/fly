package cn.jkm.provider.content.test;

import cn.jkm.core.domain.mysql.content.ZhongTest;
import cn.jkm.core.domain.type.ContentStatus;
import cn.jkm.framework.mysql.Jdbc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * Created by zhong on 2017/7/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class EnumTest {
    @Test
    public void testAddWord(){
        ZhongTest zhongTest = new ZhongTest();
        zhongTest.setId(UUID.randomUUID().toString());
        zhongTest.setName("zhong");
        zhongTest.setContentStatus(ContentStatus.SHOW);
        Jdbc.build().insert(zhongTest);
    }
}
