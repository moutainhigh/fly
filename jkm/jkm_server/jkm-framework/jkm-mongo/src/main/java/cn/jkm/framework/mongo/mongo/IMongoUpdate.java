package cn.jkm.framework.mongo.mongo;

import org.springframework.data.mongodb.core.query.Update;

/**
 * Created by werewolf on 2017/7/13.
 */

public interface IMongoUpdate {
    void update(Update update);
}
