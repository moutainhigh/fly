package cn.jkm.provider.content.content.v1_0;

import cn.jkm.core.domain.mongo.content.Expert;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.content.ExpertService;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by zhengzb on 2017/7/18.
 */
@Service(version = "1.0")
public class ExpertServiceImpl implements ExpertService {
    @Override
    public void create(Expert expert) {
        Mongo.buildMongo().insert(expert);
    }

    @Override
    public void update(Expert expert) {
        Mongo.buildMongo().eq("_id",expert.getId()).updateFirst((Update update) -> {

            if(StringUtils.isNotEmpty(expert.getName())){
                update.set("name",expert.getName());
            }
            if(StringUtils.isNotEmpty(expert.getAvatar())){
                update.set("avatar",expert.getAvatar());
            }
            if(StringUtils.isNotEmpty(expert.getPosition())){
                update.set("position",expert.getPosition());
            }
            if(StringUtils.isNotEmpty(expert.getPrief())){
                update.set("prief",expert.getPrief());
            }
            if(expert.getStatus() != null){
                update.set("status",expert.getStatus());
            }

        },Expert.class);
    }

    @Override
    public void incPostCount(String id) {
        Mongo.buildMongo().eq("_id",id).updateFirst(update -> update.inc("postCount",1),Expert.class);
    }

    @Override
    public void incPoint(String id) {
        Mongo.buildMongo().eq("_id",id).updateFirst(update -> update.inc("point",1),Expert.class);
    }

    @Override
    public void handleStore(String id,Number inc) {
        Mongo.buildMongo().eq("_id",id).updateFirst(update -> update.inc("postCount",inc),Expert.class);
    }


    @Override
    public Expert findById(String id) {
        return Mongo.buildMongo().eq("_id",id).findOne(Expert.class);
    }

    @Override
    public List<Expert> findExperts(String keys, Status status, int page, int limit) {
        Mongo mongo = Mongo.buildMongo();
        if(status != null){
            mongo.eq("status",status);
        }
        if(StringUtils.isNotEmpty(keys)){
            mongo.or(new String[]{"name","position","prief"},new String[]{keys,keys,keys},new Mongo.OP[]{Mongo.OP.F, Mongo.OP.F, Mongo.OP.F});
        }
        return mongo.limit(limit,page).find(Expert.class);
    }
}
