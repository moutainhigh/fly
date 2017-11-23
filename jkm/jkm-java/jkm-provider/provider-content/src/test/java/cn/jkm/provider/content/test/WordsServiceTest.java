package cn.jkm.provider.content.test;

import cn.jkm.core.domain.mysql.content.SensitiveWords;
import cn.jkm.provider.content.v1_0.setting.WordsSettingServiceImpl;
import cn.jkm.service.content.WordsSettingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhong on 2017/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class WordsServiceTest {
    private WordsSettingService service = new WordsSettingServiceImpl();


    @Test
    public void testAddWord(){
        service.add("哎呀");
    }
    @Test
    public void testBatchAdd(){
        List<String> words = new ArrayList<>();
        words.add("31");
        words.add("122312331");
        words.add("432");
        words.add("1231");
        service.batchAdd(words);
    }
    @Test
    public void testDel(){
        service.del("0cbc8cb77fa141feb598e495440b7d97");
    }
    @Test
    public void testBatchDel(){
        List<String> batchDel = new ArrayList<>();
        batchDel.add("3664884fc6ab4f179a3f31b3cee49ff0");
        batchDel.add("d76db3f3bfab47ef891b2a54017cc797");
        service.batchDel(batchDel);
    }
    @Test
    public void find(){
        List<SensitiveWords> sensitiveWordsList = service.find(10,1,"3");
        Assert.assertTrue(sensitiveWordsList.size()==4);
    }
    @Test
    public void findCount(){
        long count = service.findCount(null);
        Assert.assertTrue(count==4l);
    }

    @Test
    public void delAll(){
        this.service.delAllWords();
    }
}
