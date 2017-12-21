package cn.jkm.framework.mongo.mongo;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * Created by werewolf on 2017/7/13.
 */

public interface IMyselfQuery {
    void query(Criteria criteria);
}
