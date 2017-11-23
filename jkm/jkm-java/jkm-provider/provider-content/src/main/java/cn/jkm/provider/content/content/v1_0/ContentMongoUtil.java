package cn.jkm.provider.content.content.v1_0;

import cn.jkm.core.domain.type.ContentStatus;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.core.domain.type.HandleType;
import cn.jkm.framework.mongo.mongo.Mongo;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * Created by zhengzb on 2017/7/19.
 * mongo 操作工具
 */
public class ContentMongoUtil {
    private Mongo mongo;
    public ContentMongoUtil(){
        mongo = Mongo.buildMongo();
    }
    public ContentMongoUtil eq(String key, Object value){
        if(value != null){
            mongo.eq(key,value);
        }
        return this;
    }
    public ContentMongoUtil gt(String key, Object value){
        if(value != null){
            mongo.gt(key,value);
        }
        return this;
    }

    /**
     * 匹配活动、专家文章、的状态
     * @param key
     * @param value
     * @return
     */
    public ContentMongoUtil eqStatus(String key, Object value){
        if(value != null){
            mongo.eq(key,value);
        }else {
        	mongo.ne(key, ContentStatus.DEL);
        }
        return this;
    }

    /**
     * 匹配帖子或新闻的状态
     * @param key
     * @param value
     * @return
     */
    public ContentMongoUtil eqPostStatus(String key, Object value){
        if(value != null){
            mongo.eq(key,value);
        }else {
//            mongo.eq(key, ContentStatus.SHOW).eq(key,ContentStatus.TOP).eq(key,ContentStatus.HIDDEN);
            mongo.in(key,ContentStatus.SHOW.toString(),ContentStatus.TOP.toString(),ContentStatus.HIDDEN.toString());
        }
        return this;
    }


    /**
     * 匹配搜索关键字
     * @param key 要匹配的字段
     * @param value 关键字
     * @param operators 匹配方式
     * @return
     */
    public ContentMongoUtil orKeys(String[] key, String[] value, Mongo.OP[] operators){
        if(key != null && key.length != 0){
            mongo.or(key,value,operators);
        }
        return this;
    }

    /**
     * 匹配时间段
     * @param key 要匹配的字段
     * @param startTime
     * @param endTime
     * @return
     */
    public ContentMongoUtil timeQuantum(String key, Object startTime, Object endTime){
        if(startTime != null && endTime != null){
            mongo.between(key,startTime,endTime, Mongo.Between.EQ);
        }
        if(startTime != null) {
            mongo.gte(key,startTime);
        }
        if(endTime != null){
            mongo.lte(key,endTime);
        }
        return this;
    }

    /**
     * 排序 如果排序字段为空 则按添加时间进行排序
     * @param orderBy   排序字段
     * @return
     */
    public ContentMongoUtil orderBy(String orderBy){
        if(orderBy != null){
            mongo.asc(orderBy);
        }else{
            mongo.asc("createAt");
        }
        return this;
    }
    public Mongo buildMongo(){
        return mongo;
    }

}
