package cn.jkm.provider.content.action;

import cn.jkm.core.domain.mongo.content.ContentStore;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.core.exception.ProviderException;
import com.alibaba.druid.util.StringUtils;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.provider.content.action
 * @description //收藏操作
 * @update 2017/7/25 14:09
 */
public class StoreAction {

    public static boolean store(String subjectId, String userId, ContentType type){
        if(!StringUtils.isEmpty(subjectId)&&!StringUtils.isEmpty(userId)&&type!=null){
            ContentStore store = Mongo.buildMongo()
                    .eq("contentBaseId",subjectId)
                    .eq("userId",userId)
                    .eq("type",type.name())
                    .findOne(ContentStore.class);
            if(store!=null){
                if(!store.getStatus().equals(Status.ACTIVE)){
                    Mongo.buildMongo().eq("_id",store.getId()).updateFirst(update->{
                        update.set("status", Status.ACTIVE.toString());
                    },ContentStore.class);
                }
                return false;
            }else{
                store = new ContentStore();
                store.setStatus(Status.ACTIVE);
                store.setContentBaseId(subjectId);
                store.setType(type);
                store.setUserId(userId);
                store.setTime(System.currentTimeMillis()/1000);
                Mongo.buildMongo().insert(store);
                return true;
            }
        }else{
            throw new ProviderException("参数异常");
        }
    }

    public static boolean unStore(String subjectId, String userId, ContentType type) {

        ContentStore store = Mongo.buildMongo()
                .eq("contentBaseId",subjectId)
                .eq("userId",userId)
                .eq("type",type.name())
                .findOne(ContentStore.class);
        if(store==null){
            return false;
        }else{
            Mongo.buildMongo()
                    .eq("contentBaseId",subjectId)
                    .eq("userId",userId)
                    .eq("type",type.name())
                    .remove("content_store");
            return true;
        }
    }

    /**
     * 物理删除帖子的所有收藏（打回帖子时使用）
     * @param subjectId
     */
    public static void deleteStore(String subjectId){
        Mongo.buildMongo().eq("contentBaseId",subjectId).remove("content_store");
    }
}
