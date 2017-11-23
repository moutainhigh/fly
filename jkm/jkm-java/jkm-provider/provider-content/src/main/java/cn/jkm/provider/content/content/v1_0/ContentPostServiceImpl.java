package cn.jkm.provider.content.content.v1_0;

import cn.jkm.core.domain.mongo.content.ContentComment;
import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.core.domain.type.ComplaintType;
import cn.jkm.core.domain.type.ContentStatus;
import cn.jkm.core.domain.type.TopType;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.provider.content.action.ContentCommentAction;
import cn.jkm.provider.content.action.ContentComplaintAction;
import cn.jkm.provider.content.action.StoreAction;
import cn.jkm.service.content.ContentCommentService;
import cn.jkm.service.content.ContentComplaintService;
import cn.jkm.service.content.ContentPostService;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.core.exception.ProviderException;
import cn.jkm.service.core.exception.ProviderStatus;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.String;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/17.
 */
@Service(version = "1.0")
public class ContentPostServiceImpl implements ContentPostService{

    @Autowired
    ContentCommentService contentCommentService;
    @Autowired
    ContentComplaintService contentComplaintService;
    @Override
    public void create(ContentPost contentPost) {
        Mongo.buildMongo().insert(contentPost);
    }

    @Override
    public ContentPost find(String id) {
        return Mongo.buildMongo().eq("_id",id).findOne(ContentPost.class);
    }

    @Override
    public boolean isHandle(String id) throws ProviderException{
        ContentPost contentPost = find(id);
        if(contentPost != null){
            return contentPost.getComplaintNoHandleCount()>0 ? true : false;
        }else
            throw new ProviderException(ProviderStatus.PROVIDER_REEOR,"ContentPost is null.");
    }

    @Override
    public void handleStatus(String id,String contentStatus) {
        Mongo.buildMongo().eq("_id",id).updateFirst(update -> {
                update.set("contentStatus",contentStatus);
                update.set("updateAt",System.currentTimeMillis()/1000);
            },ContentPost.class);
    }

    @Override
    public void upPost(String id, String isTop) {
        Mongo.buildMongo().eq("_id",id).updateFirst(update -> {
            update.set("contentStatus",isTop);
            if(isTop.equals(ContentStatus.TOP.toString())) {
                update.set("topTime", System.currentTimeMillis() / 1000);
            }else{
                update.set("topTime", null);
            }
            update.set("updateAt",System.currentTimeMillis() / 1000);
        },ContentPost.class);
    }


    @Override
    public void repulsePost(String id,String type) {
        List<ContentComment> comments = ContentCommentAction.listComments(id,type,null,1);
        comments.forEach(v->{
            ContentComplaintAction.deleteComplaintComment(id,type,v.getId());
        });
        ContentCommentAction.deletes(id,type);
        ContentComplaintAction.deleteComplaintBase(id,type);
        StoreAction.deleteStore(id);
        Mongo.buildMongo().eq("_id",id).updateFirst(update -> {
            update.set("commentNum",0)
                    .set("browseNum",0)
                    .set("pointNum",0)
                    .set("collectionNum",0)
                    .set("complaintCount",0)
                    .set("complaintHandleCount",0)
                    .set("contentStatus",ContentStatus.PRE_EXAMINE)
                    .set("updateAt",System.currentTimeMillis()/1000);
        },ContentPost.class);
    }

    @Override
    public void delete(String id, String type,String handleUserId) {
        //修改帖子的状态为删除
        Mongo.buildMongo().eq("_id",id).eq("postType",type).updateFirst(update -> {
                update.set("contentStatus",ContentStatus.DEL.toString());
                update.set("updateAt",System.currentTimeMillis()/1000);
            },ContentPost.class);
        //修改主题帖的待处理投诉状态更改为“删除主题”。
        ContentComplaintAction.handleComplaintBase(id,type, ComplaintType.DELMASTER.toString(),handleUserId);
    }

    @Override
    public ListResult<ContentPost> listByCondition(String postType , String channelId, String contentStatus, String keys, Long startTime, Long endTime, String orderBy, int page, int limit) {
        ContentMongoUtil contentMongo = new ContentMongoUtil();
        List<ContentPost> contentPosts = contentMongo.eq("postType",postType)
                                                .eq("channelId",channelId)
                                                .eqPostStatus("contentStatus",contentStatus)
                                                .orKeys(new String[]{"title","detail"},new String[]{keys,keys},new Mongo.OP[]{Mongo.OP.F, Mongo.OP.F})
                                                .timeQuantum("createAt",startTime,endTime)
                                                .orderBy(orderBy)
                                                .buildMongo().limit(limit,page).find(ContentPost.class);
        long count = contentMongo.buildMongo().count("content_post");
        return  new ListResult<ContentPost>(count,contentPosts);
    }

    @Override
    public ListResult<ContentPost> listComplaint(Map map) {
        ContentMongoUtil contentMongo = new ContentMongoUtil();
        List<ContentPost> contentPosts = contentMongo.eq("channelId",map.get("channelId"))
                                                     .eq("postType",map.get("type"))
                                                     .gt("complaintNoHandleCount",0)
                                                     .timeQuantum("createAt",map.get("publicStTime"),map.get("publicEnTime"))
                                                     .timeQuantum("handleTime",map.get("handleStTime"),map.get("handleEnTime"))
                                                     .timeQuantum("complaintTime",map.get("complaintStTime"),map.get("complaintEnTime"))
                                                     .orderBy(formatString(map.get("orderBy")))
                                                    .orKeys(new String[]{"title","detail"},new String[]{formatString(map.get("keys")),formatString(map.get("keys"))},new Mongo.OP[]{Mongo.OP.F, Mongo.OP.F})
                                                    .buildMongo()
                                                    .in("contentStatus",new String[]{ContentStatus.SHOW.toString(),ContentStatus.TOP.toString()})
                                                    .limit(formatInteger(map.get("limit")),formatInteger(map.get("page"))).find(ContentPost.class);
        long count = contentMongo.buildMongo().count("content_post");
        return new ListResult<>(count,contentPosts);
    }

    @Override
    public List<ContentPost> listActivityByCondition(ContentStatus contentStatus, String keys, Long startTime, Long endTime, Long stStTime, Long stEnTime, Long enStTime, Long enEnTime, Integer minNum, Integer maxNum, String orderBy, int page, int limit) {
        return null;
    }
    public String formatString(Object obj) {
        if (null == obj) {
            return null;
        }
        return String.valueOf(obj);
    }
    public Integer formatInteger(Object obj) {
        if (null == obj) {
            return null;
        }
        return Integer.valueOf(obj.toString());
    }


}
