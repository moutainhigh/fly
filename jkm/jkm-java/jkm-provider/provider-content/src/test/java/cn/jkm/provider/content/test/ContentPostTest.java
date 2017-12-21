package cn.jkm.provider.content.test;

import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.provider.content.BasicTest;
import org.junit.Test;

/**
 * Created by zhengzb on 2017/7/26.
 */
public class ContentPostTest extends BasicTest {
    @Test
    public void ContentPostStatusTest(){
        Mongo mongo = Mongo.buildMongo();
        mongo.eq("_id","5975c2112d2436ba64b7e6a9").updateFirst(update -> {
            update.set("commentNum",10);
            update.set("browseNum",5);
            update.set("pointNum",5);
            update.set("collectionNum",5);
            update.set("complaintCount",5);
            update.set("complaintHandleCount",5);
        }, ContentPost.class);
    }
    @Test
    public void ContentPostUpdate(){
        Mongo.buildMongo().updateMulti(update -> {
            update.set("complaintNoHandleCount",5);
        },ContentPost.class);
    }
}
