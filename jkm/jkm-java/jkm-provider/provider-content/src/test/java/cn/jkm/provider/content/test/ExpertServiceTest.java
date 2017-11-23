package cn.jkm.provider.content.test;

import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.core.domain.mongo.content.Expert;
import cn.jkm.core.domain.type.ContentStatus;
import cn.jkm.core.domain.type.Gender;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.provider.content.BasicTest;
import cn.jkm.provider.content.content.v1_0.ExpertServiceImpl;
import cn.jkm.service.content.ExpertService;
import org.junit.Test;

import java.util.List;

/**
 * Created by zhengzb on 2017/7/18.
 */
public class ExpertServiceTest extends BasicTest {
    ExpertService expertService = new ExpertServiceImpl();
    @Test
    public void testAdd(){
//        for (int i=0;i<50;i++) {
//            Expert expert = new Expert();
//            expert.setName("张三"+i);
//            expert.setAge(50+i);
//            expert.setSex(Gender.MALE);
//            expert.setPoint(0+i);
//            expert.setStore(0);
//            expert.setPostCount(0+i*2);
//            expert.setPosition("高级专家"+i);
//            expert.setAvatar("http://localhost:8080/xxx"+i);
//            expert.setPrief("专家简介"+i);
//            expert.setAddUserId("SDFSDFSF-ERT-DGDGF"+i);
//            expertService.create(expert);
//        }
        Expert expert = new Expert();
        expert.setName("中国");
        expertService.create(expert);
    }

    @Test
    public void testAddContentPost(){
        for (int i=0;i<100;i++) {
            ContentPost contentPost = new ContentPost();
            contentPost.setTitle("帖子"+i);
            contentPost.setChannelId("xxxxxx");
            contentPost.setDetail("帖子详情<pf></pf>"+i);
            contentPost.setPostFormulaInfo("edc");
            contentPost.setPostProductionInfo("rfc");
            contentPost.setContentStatus(ContentStatus.SHOW);
            Mongo.buildMongo().insert(contentPost);
        }
    }

    @Test
    public void testUpdate(){
//        Expert expert = new Expert();
//        expert.setId("596db08d889e4fe55a4487f3");
//        expert.setName("李四5");
//        expert.setPosition("null");
//        expert.setPrief(null);
//        expertService.update(expert);
//        Mongo.buildMongo().eq("_id","596db08d889e4fe55a4487f3").updateFirst(update -> update.set("name","李四").set("position",null).set("prief","null"),Expert.class);
//         Mongo.buildMongo().updateMulti(update -> {update.set("postType","POST");},ContentPost.class);
        List<ContentPost> contents = Mongo.buildMongo().or(new String[]{"title","detail"},new String[]{"帖子","帖子"},new Mongo.OP[]{Mongo.OP.F, Mongo.OP.F}).or(new String[]{"postType","postType","postType"},new String[]{ContentStatus.SHOW.toString(),ContentStatus.TOP.toString(),ContentStatus.HIDDEN.toString()},new Mongo.OP[]{Mongo.OP.EQ,Mongo.OP.EQ,Mongo.OP.EQ}).limit(10,1).find(ContentPost.class);
        for (ContentPost post:contents) {
            System.out.println(post.getTitle());
        }
    }
    @Test
    public void testFindOne(){
        Expert expert = Mongo.buildMongo().ne("name",null).findOne(Expert.class);
        System.out.println("---------------+++++++"+expert);
    }
    @Test
    public void testFind(){
        List<Expert> experts = expertService.findExperts("张",Status.ACTIVE,1,10);
        for (Expert expert:experts) {
            System.out.println(expert);
        }
    }
    @Test
    public void testHandleStore(){
        expertService.handleStore("596db08d889e4fe55a4487f3",-1);
    }

}
