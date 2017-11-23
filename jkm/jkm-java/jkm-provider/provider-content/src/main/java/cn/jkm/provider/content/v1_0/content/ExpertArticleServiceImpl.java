package cn.jkm.provider.content.v1_0.content;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;

import cn.jkm.core.domain.mongo.content.ExpertArticle;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.provider.content.content.v1_0.ContentMongoUtil;
import cn.jkm.service.content.ExpertArticleService;
/**
 * Created by zhengzb on 2017/7/17.
 */
@Service(version = "1.0")
public class ExpertArticleServiceImpl implements ExpertArticleService {

	@Override
	public void create(ExpertArticle expertArticle) {
		Mongo.buildMongo().insert(expertArticle);
	}
	@Override
	public void update(ExpertArticle expertArticle) {
	    Mongo.buildMongo().eq("_id", expertArticle.getId()).updateFirst(update->{
	    	if(expertArticle.getTitle() != null) {
	    		update.set("title", expertArticle.getTitle());
	    	}
	    	if(expertArticle.getContent() != null) {
	    		update.set("content", expertArticle.getContent());
	    	}
	    	if(expertArticle.getExpertId() != null) {
	    		update.set("expertId", expertArticle.getExpertId());
	    	}
	    }, ExpertArticle.class);
		
	}
	@Override
	public void updateStatus(String id, String status) {
		Mongo.buildMongo().eq("_id", id).updateFirst(update->{update.set("status", status);}, ExpertArticle.class);
		
	}
	@Override
	public ExpertArticle find(String id) {
		return Mongo.buildMongo().eq("_id", id).findOne(ExpertArticle.class);
	}
	@Override
	public List<ExpertArticle> list(String keys, long startTime, long endTime, String status, String orderBy, int limit, int page) {
		ContentMongoUtil contentMongo = new ContentMongoUtil();
        List<ExpertArticle> expertArticles = contentMongo.eqStatus("status",status)
                                                .orKeys(new String[]{"title","content"},new String[]{keys,keys},new Mongo.OP[]{Mongo.OP.F, Mongo.OP.F})
                                                .timeQuantum("createAt",startTime,endTime)
                                                .orderBy(orderBy)
                                                .buildMongo().limit(limit,page).find(ExpertArticle.class);
        return  expertArticles;
	}
	
	

	

}
