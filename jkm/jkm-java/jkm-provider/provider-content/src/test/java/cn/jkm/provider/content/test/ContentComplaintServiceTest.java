package cn.jkm.provider.content.test;

import cn.jkm.core.domain.mongo.content.ContentComplaint;
import cn.jkm.core.domain.type.ComplaintType;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.provider.content.BasicTest;
import cn.jkm.provider.content.content.v1_0.ContentComplaintServiceImpl;
import cn.jkm.service.content.ContentComplaintService;
import org.junit.Test;

/**
 * Created by zhengzb on 2017/7/25.
 */
public class ContentComplaintServiceTest extends BasicTest {
    ContentComplaintService contentComplaintService = new ContentComplaintServiceImpl();
    @Test
    public void testAddConten(){
        for (int i=0;i<3;i++) {
            ContentComplaint contentComplaint = new ContentComplaint();
            contentComplaint.setContentBaseId("5975c2112d2436ba64b7e6a9");
            contentComplaint.setContent("这是帖子0的投诉内容");
            contentComplaint.setUserId("asdsdf");
            contentComplaint.setHandleUserId("dgdfgdfg");
            contentComplaint.setCommentId(null);
            contentComplaint.setDealTime(14345346456l);
            contentComplaint.setComplaintStatus(ComplaintType.IGNORE);
            contentComplaint.setType(ContentType.POST);
            contentComplaintService.create(contentComplaint);
        }
    }
    @Test
    public void testAddContenComment(){
        for (int i=0;i<1;i++) {
            ContentComplaint contentComplaint = new ContentComplaint();
            contentComplaint.setContentBaseId("5975c2112d2436ba64b7e6a9");
            contentComplaint.setContent("这是帖子0的评论的投诉内容");
            contentComplaint.setUserId("asdsdf");
            contentComplaint.setHandleUserId("dgdfgdfg");
            contentComplaint.setCommentId("5976f8f02d24e104b7677f7d");
            contentComplaint.setDealTime(14345346456l);
            contentComplaint.setComplaintStatus(ComplaintType.IGNORE);
            contentComplaint.setType(ContentType.POST);
            contentComplaintService.create(contentComplaint);
        }
    }
    @Test
    public void testUpdateComplaint(){
//        Mongo.buildMongo().updateMulti(update -> {update.set("contentBaseId","5975c2112d2436ba64b7e6a9");},ContentComplaint.class);
        Mongo.buildMongo().eq("contentBaseId","5975c2112d2436ba64b7e6a9").updateMulti(update -> {
                    update.set("complaintStatus",ComplaintType.NOHANDLE);
                },ContentComplaint.class);
    }

}
