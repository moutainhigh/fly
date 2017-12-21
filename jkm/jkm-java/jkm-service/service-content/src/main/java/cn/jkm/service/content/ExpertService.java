package cn.jkm.service.content;

import cn.jkm.core.domain.mongo.content.Expert;
import cn.jkm.core.domain.type.Status;

import java.util.List;

/**
 * Created by zhengzb on 2017/7/18.
 */
public interface ExpertService {
    /**
     * 添加专家
     * @param expert
     */
    void create(Expert expert);

    /**
     * 修改、锁定、删除 专家
     * @param expert
     */
    void update(Expert expert);

    /**
     * 专家发帖数加一
     */
    void incPostCount(String id);

    /**
     * 专家内容点赞数加一
     */
    void incPoint(String id);

    /**
     * 修改专家内容收藏数
     * inc 收藏传1，取消收藏传-1
     */
    void handleStore(String id,Number inc);

    /**
     * 根据id查找专家
     * @param id
     * @return
     */
    Expert findById(String id);
    /**
     * 按条件分页查找专家
     * @param keys 查找关键字
     * @param status  专家状态
     * @return  专家列表
     */
    List<Expert> findExperts(String keys, Status status, int page, int limit);

}
