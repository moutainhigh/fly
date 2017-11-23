package cn.jkm.provider.content.action;

import cn.jkm.core.domain.mongo.content.ContentComplaint;
import cn.jkm.core.domain.type.ComplaintType;
import cn.jkm.framework.mongo.mongo.Mongo;

/**
 * Created by zhengzb on 2017/7/26.
 */
public class ContentComplaintAction {
    /**
     * 逻辑删除主题的投诉（打回帖子是使用）
     * @param id    主题id
     * @param type  主题类型
     */
    public static void deleteComplaintBase(String id, String type) {
        Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).eq("commentId",null).remove("content_complaint");
    }
    /**
     * 逻辑删除评论的投诉（打回帖子时使用）
     * @param id 主题id
     * @param type  主题类型
     * @param commentId 评论id
     */
    public static void deleteComplaintComment(String id, String type, String commentId) {
        Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).eq("commentId",commentId).remove("content_complaint");
    }
    /**
     * 处理主题投诉
     * @param id    主题id
     * @param type  主题类型
     * @param complaintType 要处理的类型
     * @param handleUserId 处理人
     */
    public static void handleComplaintBase(String id, String type, String complaintType, String handleUserId) {
        Mongo mongo = Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).eq("commentId","null");
        if(ComplaintType.DELMASTER.toString().equals(complaintType)){
            mongo.eq("complaintStatus",ComplaintType.NOHANDLE);
        }
        mongo.updateFirst(update -> {
                update.set("complaintStatus",complaintType);
                update.set("handleUserId",handleUserId);
                update.set("dealTime",System.currentTimeMillis()/1000);
                update.set("updateAt",System.currentTimeMillis()/1000);
            },ContentComplaint.class);
    }
}
