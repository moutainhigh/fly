package cn.jkm.provider.content.content.v1_0;

import cn.jkm.core.domain.mongo.content.ContentComment;
import cn.jkm.core.domain.type.ComplaintType;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.content.ContentCommentService;
import cn.jkm.service.content.ContentComplaintService;
import cn.jkm.service.core.domain.ListResult;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by admin on 2017/7/19.
 */
@Service(version = "1.0")
public class ContentCommentServiceImpl implements ContentCommentService{
    @Autowired
    ContentComplaintService contentComplaintService;

    @Override
    public void create(ContentComment contentComment) {
        Mongo mongo = Mongo.buildMongo();
        mongo.insert(contentComment);
    }

    @Override
    public ContentComment find(String id) {
        return Mongo.buildMongo().eq("_id",id).findOne(ContentComment.class);
    }

    @Override
    public ListResult<ContentComment> listCommentsPage(String id, String type, String refId, int level, int page, int limit) {
        Mongo mongo = Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).eq("status", Status.ACTIVE);
        long count = mongo.count("content_comment");
        List<ContentComment> comments = mongo.limit(limit,page).find(ContentComment.class);
        return new ListResult<ContentComment>(count,comments);
    }

    @Override
    public ListResult<ContentComment> listNoComplaintCom(String id, String type, int page, int limit) {
        Mongo mongo = Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).gt("complaintNoHandleCount",0).eq("status", Status.ACTIVE);
        long count = mongo.count("content_comment");
        List<ContentComment> comments = mongo.limit(limit,page).find(ContentComment.class);
        return new ListResult<ContentComment>(count,comments);
    }

    @Override
    public List<ContentComment> listComments(String id, String type, String refId, int level) {
        return Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).eq("status", Status.ACTIVE).find(ContentComment.class);
    }

    @Override
    public void delete(String id, String handleUserId) {
        Mongo.buildMongo().eq("_id",id).updateFirst(update -> update.set("status",Status.DELETE),ContentComment.class);
        contentComplaintService.handleComplaintComment(id, ComplaintType.DELSALVE,handleUserId);
    }

    @Override
    public void deletes(String id, String type) {
        Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).remove("content_comment");
    }
}
