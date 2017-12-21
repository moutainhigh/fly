package cn.jkm.provider.content.content.v1_0;

import cn.jkm.core.domain.mongo.content.ContentComplaint;
import cn.jkm.core.domain.type.ComplaintType;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.content.ContentComplaintService;
import cn.jkm.service.core.domain.ListResult;
import com.alibaba.dubbo.config.annotation.Service;

import java.lang.String;

import java.util.List;

/**
 * Created by zhengzb on 2017/7/19.
 */
@Service(version = "1.0")
public class ContentComplaintServiceImpl implements ContentComplaintService{
    @Override
    public void create(ContentComplaint contentComplaint) {
        Mongo.buildMongo().insert(contentComplaint);
    }

    @Override
    public ListResult<ContentComplaint> listComplaintLog(String id, String type, String[] complaintStatus, int page, int limit) {
        Mongo mongo = Mongo.buildMongo();
        mongo.eq("contentBaseId",id).eq("type",type);
        if(complaintStatus != null){
            if(complaintStatus.length == 1)
                mongo.eq("complaintStatus",complaintStatus);
            else{
                mongo.in("complaintStatus",complaintStatus);
            }
        }
        long count = mongo.count("content_complaint");
        return new ListResult<ContentComplaint>(count,mongo.limit(limit,page).find(ContentComplaint.class));
    }

    @Override
    public ListResult<ContentComplaint> listComplaintBase(String id, String type, int page, int limit) {
        Mongo mongo = Mongo.buildMongo();
        mongo.eq("contentBaseId",id)
                .eq("type",type)
                .eq("commentId","null")
                .eq("complaintStatus",ComplaintType.NOHANDLE.toString());
        long count = mongo.count("content_complaint");
        return new ListResult<ContentComplaint>(count,mongo.limit(limit,page).find(ContentComplaint.class));
    }

    @Override
    public ListResult<ContentComplaint> listComplaintComment(String id, String commentId, String type, int page, int limit) {
        Mongo mongo = Mongo.buildMongo();
        mongo.eq("contentBaseId",id)
                .eq("type",type)
                .eq("commentId",commentId)
                .eq("complaintStatus",ComplaintType.NOHANDLE.toString());
        long count = mongo.count("content_complaint");
        return new ListResult<ContentComplaint>(count,mongo.limit(limit,page).find(ContentComplaint.class));
    }

    @Override
    public void handleComplaintBase(String id, String type, String complaintType, String handleUserId) {
        Mongo mongo = Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).eq("commentId",null);
        if(ComplaintType.DELMASTER.toString().equals(complaintType)){
            mongo.eq("complaintStatus",ComplaintType.NOHANDLE);
        }
        mongo.updateFirst(update -> update.set("complaintStatus",complaintType).set("handleUserId",handleUserId).set("dealTime",System.currentTimeMillis()/1000),ContentComplaint.class);
    }

    @Override
    public void handleComplaintComment(String id, ComplaintType complaintType, String handleUserId) {
        Mongo.buildMongo().eq("commentId",id).updateFirst(update -> update.set("complaintStatus",complaintType).set("handleUserId",handleUserId).set("dealTime",System.currentTimeMillis()/1000),ContentComplaint.class);
    }

    @Override
    public void deleteComplaintBase(String id, String type) {
        Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).eq("commentId",null).remove("content_complaint");
    }

    @Override
    public void deleteComplaintComment(String id, String type, String commentId) {
        Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).eq("commentId",commentId).remove("content_complaint");
    }

    @Override
    public List<ContentComplaint> listComplaints(String channelId, String startTime, String endTime, String handleStTime, String handleEnTime, String complaintStTime, String complaintEnTime, String keys) {
        return null;
    }
}
