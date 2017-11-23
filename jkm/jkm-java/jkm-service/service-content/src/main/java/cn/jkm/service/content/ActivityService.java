package cn.jkm.service.content;

import java.util.List;

import cn.jkm.core.domain.mongo.content.ActivityEnrollUser;
import cn.jkm.core.domain.mongo.content.ActivityItems;
import cn.jkm.core.domain.mongo.content.ContentPost;

/**
 * Created by zhengzb on 2017/7/22.
 */
public interface ActivityService {
	/**
	 * 新增或修改活动信息
	 * @param contentPost 活动详细信息
	 * @param items	活动定制表信息
	 */
	void createOrUpdate(ContentPost contentPost,List<ActivityItems> activityItems);
	/**
	 * 修改活动状态
	 * @param id
	 * @param status
	 */
	void updateStatus(String id,String status);
	/**
	 * 查询活动详情
	 * @param id
	 */
	ContentPost find(String id);
	/**
	 * 查询活动报名用户信息
	 * @param id	活动id
	 * @return
	 */
	List<ActivityEnrollUser> findEnrollUsers(String id);
	/**
	 * 按条件分页查找活动详情
	 * @param publicStTime
	 * @param publicEnTime
	 * @param status
	 * @param beStTime
	 * @param beEnTime
	 * @param enStTime
	 * @param enEnTime
	 * @param stNum
	 * @param enNum
	 * @param address
	 * @param keys
	 * @param limit
	 * @param page
	 * @return
	 */
	List<ContentPost> list(long publicStTime,long publicEnTime,String status,long beStTime,long beEnTime,long enStTime,long enEnTime,int stNum,int enNum,String address,String keys,String orderBy,int limit,int page);
}
