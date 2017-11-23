package cn.jkm.service.content;

import cn.jkm.core.domain.mongo.content.ContentComplaint;
import cn.jkm.core.domain.type.ComplaintType;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.service.core.domain.ListResult;

import java.util.List;

/**
 * Created by zhengzb on 2017/7/19.
 */
public interface ContentComplaintService {
    /**
     * 添加投诉内容
     * @param contentComplaint
     */
    void create(ContentComplaint contentComplaint);
    /**
     * 分页查找投诉历史
     * @param id    投诉主题id
     * @param type  主题类型
     * @param page
     * @param limit
     * @return
     */
    ListResult<ContentComplaint> listComplaintLog(String id, String type, String[] complaintStatus, int page, int limit);
    /**
     * 分页查找主题投诉
     * @param id    主题id
     * @param type  主题类型
     * @param page
     * @param limit
     * @return
     */
    ListResult<ContentComplaint> listComplaintBase(String id, String type, int page, int limit);

    /**
     * 分页查找评论投诉
     * @param id    主题id
     * @param commentId 评论id
     * @param type  主题类型
     * @param page
     * @param limit
     * @return
     */
    ListResult<ContentComplaint> listComplaintComment(String id, String commentId, String type, int page, int limit);

    /**
     * 处理主题投诉
     * @param id    主题id
     * @param type  主题类型
     * @param complaintType 要处理的类型
     * @param handleUserId 处理人
     */
    void handleComplaintBase(String id, String type, String complaintType, String handleUserId);

    /**
     * 处理评论的投诉
     * @param id    评论的id(因为评论在一个实体里 是唯一的，所以可以用评论id做修改依据)
     * @param complaintType 要处理的类型
     * @param handleUserId  处理人
     */
    void handleComplaintComment(String id, ComplaintType complaintType, String handleUserId);

    /**
     * 逻辑删除主题的投诉（打回帖子是使用）
     * @param id    主题id
     * @param type  主题类型
     */
    void deleteComplaintBase(String id, String type);

    /**
     * 逻辑删除评论的投诉 (打回帖子是使用)
     * @param id    主题id
     * @param type  主题类型
     * @param commentId 评论id
     */
    void deleteComplaintComment(String id, String type, String commentId);
    /**
     * 根据条件查找用户投诉列表
     * @param channelId 频道栏目id
     * @param startTime 发表开始时间
     * @param endTime   发表结束时间
     * @param handleStTime  审核开始时间
     * @param handleEnTime  审核结束时间
     * @param complaintStTime   投诉开始时间
     * @param complaintEnTime   投诉结束时间
     * @param keys  查询关键字
     * @return
     */
    List<ContentComplaint> listComplaints(String channelId,String startTime,String endTime,String handleStTime,String handleEnTime,String complaintStTime,String complaintEnTime,String keys);

}
