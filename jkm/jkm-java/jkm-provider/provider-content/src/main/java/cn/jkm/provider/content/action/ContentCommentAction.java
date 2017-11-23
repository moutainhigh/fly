package cn.jkm.provider.content.action;

import cn.jkm.core.domain.mongo.content.ContentComment;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;

import java.util.List;

/**
 * Created by zhengzb on 2017/7/26.
 */
public class ContentCommentAction {
    /**
     * 查询帖子或新闻的所有评论
     * @param id    主题id
     * @param type  主题类型
     * @param refId
     * @param level
     * @return
     */
    public static List<ContentComment> listComments(String id, String type, String refId, int level) {
        return Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).eq("status", Status.ACTIVE).find(ContentComment.class);
    }
    /**
     * 物理删除帖子的所有评论（打回时使用）
     * @param id    帖子id
     * @param type  帖子类型
     */
    public static void deletes(String id, String type) {
        Mongo.buildMongo().eq("contentBaseId",id).eq("type",type).remove("content_comment");
    }
}
