package cn.jkm.service.content;

import cn.jkm.core.domain.mongo.content.ContentComment;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.service.core.domain.ListResult;

import java.util.List;

/**
 * Created by zhengzb on 2017/7/19.
 */
public interface ContentCommentService {
    /**
     * 添加评论
     * @param contentComment
     */
    void create(ContentComment contentComment);
    /**
     * 查询单条评论
     * @param id    评论id
     * @return
     */
    ContentComment find(String id);
    /**
     * 分页查询查询主题帖的正常评论
     * @param id    主题id
     * @param type  主题类型
     * @param refId 评论的上级id
     * @param level 评论的级别
     * @return
     */
    ListResult<ContentComment> listCommentsPage(String id, String type, String refId, int level, int page, int limit);

    /**
     * 分页查询主题帖含有待处理投诉的评论
     * @param id    主题id
     * @param type  主题类型
     * @param page
     * @param limit
     * @return
     */
    ListResult<ContentComment> listNoComplaintCom(String id, String type, int page, int limit);
    /**
     * 查询主题帖的所有评论
     * @param id
     * @param type
     * @param refId
     * @param level
     * @return
     */
    List<ContentComment> listComments(String id, String type, String refId, int level);

    /**
     * 逻辑删除单条评论
     * @param id    评论id
     */
    void delete(String id, String handleUserId);

    /**
     * 物理删除帖子的所有评论（打回时使用）
     * @param id    帖子id
     * @param type  帖子类型
     */
    void deletes(String id, String type);
}
