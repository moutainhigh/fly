package cn.jkm.provider.content.content.v1_0;

import java.util.List;

import cn.jkm.core.domain.mongo.content.ActivityEnrollUser;
import cn.jkm.core.domain.mongo.content.ActivityItems;
import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.content.ActivityService;

public class ActivityServiceImpl implements ActivityService{

	@Override
	public void createOrUpdate(ContentPost contentPost, List<ActivityItems> activityItems) {
		if(contentPost.getId() != null && activityItems == null){
			//修改  修改的时候只能修改活动基本信息，不能修改定制表
			Mongo.buildMongo().eq("_id", contentPost.getId()).updateFirst(update->{
				if(contentPost.getTitle() != null) {
					update.set("title", contentPost.getTitle());
				}
				if(contentPost.getActivityName() != null) {
					update.set("activityName", contentPost.getActivityName());
				}
				if(contentPost.getAddress() != null) {
					update.set("address", contentPost.getAddress());
				}
				if(contentPost.getSponsorName() != null) {
					update.set("sponsorName", contentPost.getSponsorName());
				}
				if(contentPost.getBeginTime() != null) {
					update.set("beginTime", contentPost.getBeginTime());
				}
				if(contentPost.getEndTime() != null) {
					update.set("endTime", contentPost.getEndTime());
				}
				if(contentPost.getMaxNum() != null) {
					update.set("maxNum", contentPost.getMaxNum());
				}
				if(contentPost.getDetail() != null) {
					update.set("detail", contentPost.getDetail());
				}
				if(contentPost.getPublishUserId() != null) {
					update.set("publishUserId", contentPost.getPublishUserId());
				}
				update.set("updateAt", System.currentTimeMillis()/1000);
			}, ContentPost.class);
		}else {
			//添加
			Mongo.buildMongo().insert(contentPost);
			activityItems.forEach(v->{
				v.setActivityId(contentPost.getId());
				Mongo.buildMongo().insert(v);
			});
		}
	}
	
	@Override
	public void updateStatus(String id, String status) {
		Mongo.buildMongo().eq("_id", id).updateFirst(update->{update.set("contentStatus",status);}, ContentPost.class);
	}
	
	@Override
	public ContentPost find(String id) {
		return Mongo.buildMongo().eq("_id", id).findOne(ContentPost.class);
	}
	
	@Override
	public List<ActivityEnrollUser> findEnrollUsers(String id) {
		return Mongo.buildMongo().eq("activityId", id).find(ActivityEnrollUser.class);
	}

	@Override
	public List<ContentPost> list(long publicStTime, long publicEnTime, String status, long beStTime, long beEnTime,
			long enStTime, long enEnTime, int stNum, int enNum, String address, String keys, String orderBy,int limit, int page) {
		ContentMongoUtil activityMongo = new ContentMongoUtil();
        List<ContentPost> contentPosts = activityMongo.eq("postType", ContentType.ACTIVITY)
        										.eqStatus("contentStatus", status)
        										.orKeys(new String[]{"title","sponsorName"},new String[]{keys,keys},new Mongo.OP[]{Mongo.OP.F, Mongo.OP.F})
                                                .timeQuantum("createAt",publicStTime,publicEnTime)
                                                .timeQuantum("beginTime",beStTime,beEnTime)
                                                .timeQuantum("endTime",enStTime,enEnTime)
                                                .timeQuantum("maxNum",stNum,enNum)
                                                .orderBy(orderBy)
                                                .buildMongo().limit(limit,page).find(ContentPost.class);
        return  contentPosts;
	}

	

	

	

}
