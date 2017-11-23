package cn.jkm.provider.content.test;

import cn.jkm.core.domain.mongo.content.ContentComment;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.provider.content.BasicTest;
import cn.jkm.provider.content.content.v1_0.ContentCommentServiceImpl;
import cn.jkm.service.content.ContentCommentService;
import org.junit.Test;

/**
 * Created by admin on 2017/7/25.
 */
public class ContentCommentTest extends BasicTest {
    ContentCommentService contentCommentService = new ContentCommentServiceImpl();
    @Test
    public void testAddContenComment(){
        for (int i=0;i<10;i++) {
            ContentComment contentComment = new ContentComment();
            contentComment.setContentBaseId("5975c2112d2436ba64b7e6a9");
            contentComment.setContent("这个帖子不错哦!"+i);
            contentComment.setRefId(null);
            contentComment.setUserId("5975c2112d2436ba64b7e6a9");
            contentComment.setLevel(1);
            contentComment.setType(ContentType.POST);
            contentCommentService.create(contentComment);
        }
    }
    @Test
    public void contentCommentUpdateTest(){
        Mongo.buildMongo().eq("_id","5979ae844071e280d04a5e7f").updateMulti(update -> {
            update.set("complaintNoHandleCount",0);
        },ContentComment.class);
    }
}
