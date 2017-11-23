package cn.jkm.provider.content.action;

import cn.jkm.core.domain.mongo.content.ContentPoint;
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
 * @description //点赞操作
 * @update 2017/7/25 14:07
 */
public class PointAction {

    /**
     * 点赞操作
     * @param subjectId    点赞的主题id
     * @param userId        点赞的用户id
     * @param type           点赞的内容类别
     */
    public static boolean point(String subjectId, String userId, ContentType type){
        if(!StringUtils.isEmpty(subjectId)&&!StringUtils.isEmpty(userId)&&type!=null){
            ContentPoint point = Mongo.buildMongo()
                    .eq("contentBaseId",subjectId)
                    .eq("userId",userId)
                    .eq("type",type.name())
                    .findOne(ContentPoint.class);
            if(point!=null){
                if(!point.getStatus().equals(Status.ACTIVE)){
                    Mongo.buildMongo().eq("_id",point.getId()).updateFirst(update->{
                        update.set("status", Status.ACTIVE.toString());
                    },ContentPoint.class);
                }
                return false;
            }else{
                point = new ContentPoint();
                point.setStatus(Status.ACTIVE);
                point.setContentBaseId(subjectId);
                point.setType(type);
                point.setUserId(userId);
                point.setTime(System.currentTimeMillis()/1000);
                Mongo.buildMongo().insert(point);
                return true;
            }
        }else{
            throw new ProviderException("参数异常");
        }
    }
}
