package cn.jkm.service.content;

import cn.jkm.core.domain.mongo.content.ContentBase;
import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.core.domain.mongo.content.Expert;
import cn.jkm.core.domain.type.ContentStatus;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.core.domain.type.TopType;
import cn.jkm.service.core.domain.ListResult;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/18
 */
public interface ContentPostService {
    /**
     * 添加帖子、新闻、活动
     * @param contentPost
     */
    void create(ContentPost contentPost);

    /**
     * 查询单个帖子、新闻、活动
     * @param id
     */
    ContentPost find(String id);

    /**
     * 查询帖子是否有待处理状态的投诉
     * @param id
     * @return
     */
    boolean isHandle(String id) throws ParseException;

    /**
     * 修改帖子状态 隐藏、显示、删除、恢复、审核、
     * @param id
     */
    void handleStatus(String id, String contentStatus);

    /**
     * 置顶或取消置顶
     * @param id
     * @param isTop
     */
    void upPost(String id, String isTop);

    /**
     * 打回帖子
     * @param id
     */
    void repulsePost(String id, String type);

    /**
     * 逻辑删除帖子或新闻
     * @param id    帖子或新闻id
     * @param type  类型
     */
    void delete(String id, String type, String handleUserId);
    /**
     * 根据条件查询 帖子、新闻
     * @param postType  主题类型
     * @param channelId 频道或栏目id
     * @param contentStatus 帖子状态
     * @param keys  关键字
     * @param startTime 发布开始时间
     * @param endTime   发布结束时间
     * @param orderBy   排序
     * @return
     */
    ListResult<ContentPost> listByCondition(String postType, String channelId, String contentStatus, String keys, Long startTime, Long endTime, String orderBy, int page, int limit);

    /**
     * 查询用户投诉列表
     * @param map
     * @return
     */
    ListResult<ContentPost> listComplaint(Map map);
    /**
     * 根据条件查找活动
     * @param contentStatus 活动状态
     * @param keys  查询关键字
     * @param startTime 活动发表开始时间
     * @param endTime   活动发表结束时间
     * @param stStTime  活动开始时间段开始时间
     * @param stEnTime  活动开始时间段结束时间
     * @param enStTime  活动结束时间段开始时间
     * @param enEnTime  活动结束时间段结束时间
     * @param minNum    活动报名人数最少人数
     * @param maxNum    活动报名人数最多人数
     * @param orderBy   活动排序
     * @param page
     * @param limit
     * @return
     */
    List<ContentPost> listActivityByCondition(ContentStatus contentStatus, String keys, Long startTime, Long endTime, Long stStTime, Long stEnTime, Long enStTime, Long enEnTime, Integer minNum, Integer maxNum, String orderBy, int page, int limit);
}
