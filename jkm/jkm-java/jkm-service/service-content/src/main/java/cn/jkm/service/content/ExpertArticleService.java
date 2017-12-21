package cn.jkm.service.content;

import java.util.List;

import cn.jkm.core.domain.mongo.content.ExpertArticle;

/**
 * Created by zhengzb on 2017/7/22
 */
public interface ExpertArticleService {
	/**
	 * 新增专家文章
	 * @param expertArticle
	 */
	void create(ExpertArticle expertArticle);
	/**
	 * 修改专家文章
	 * @param expertArticle
	 */
	void update(ExpertArticle expertArticle);
	/**
	 * 修改专家文章状态（包括删除）
	 * @param id
	 * @param status
	 */
	void updateStatus(String id, String status);
	/**
	 * 根据专家文章id查询专家文章详情
	 * @param id 专家文章id
	 * @return
	 */
	ExpertArticle find(String id);
	/**
	 * 分页查找专家文章列表
	 * @param keys	查询关键字
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @param status	状态
	 * @param limit		
	 * @param page
	 */
	List<ExpertArticle> list(String keys, long startTime,long endTime,String status,String orderBy,int limit,int page);
}
