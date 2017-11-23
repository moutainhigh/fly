package cn.jkm.framework.mongo.mongo;


import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by werewolf on 2017/7/13.
 */
public class MongoQuery {

    protected Query query;

    protected Criteria criteria;

    public MongoQuery() {
        criteria = new Criteria();
        query = Query.query(criteria);
    }

    protected Criteria and(String key) {
        return criteria.and(key);
    }
}
