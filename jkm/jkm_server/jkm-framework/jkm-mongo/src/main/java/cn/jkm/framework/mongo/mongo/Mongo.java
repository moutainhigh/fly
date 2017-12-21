package cn.jkm.framework.mongo.mongo;


import cn.jkm.framework.core.annotation.Document;
import cn.jkm.framework.core.spring.SpringApplicationContext;
import cn.jkm.framework.mongo.QueryException;
import cn.jkm.framework.mongo.suport.RegexUtils;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by werewolf on 2017/7/13.
 */

@SuppressWarnings("unused")
public class Mongo extends MongoQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(Mongo.class);

    private MongoTemplate mongoOperations;

    public enum OP {
        IN, F, EQ, GTE, NE, LTE
    }


    private Mongo() {
        super();
        mongoOperations = SpringApplicationContext.getBean("mongoTemplate");
    }


    private Mongo(String source) {
        super();
        mongoOperations = SpringApplicationContext.getBean(source);
    }

    public Mongo safe() {
        mongoOperations.setWriteConcern(WriteConcern.SAFE);
        return this;
    }

    public static Mongo buildMongo() {
        return new Mongo();
    }

    public static Mongo buildMongo(String source) {
        return new Mongo(source);
    }

    public List<GeoResult> geoFind(Double lat, Double lng, Class clazz, String collectionName) {
        NearQuery nearQuery = NearQuery.near(lng, lat, Metrics.KILOMETERS);
        nearQuery.maxDistance(10);
        nearQuery.query(query);
        nearQuery.spherical(true);
        nearQuery.skip(query.getSkip());
        nearQuery.num(query.getSkip() + query.getLimit());
        GeoResults geoResults = mongoOperations.geoNear(nearQuery, clazz, collectionName);
        return geoResults.getContent();
    }

    public Mongo multiEq(String property, String... values) {
        Criteria[] criterias = new Criteria[values.length];
        for (int i = 0; i < values.length; i++) {
            criterias[i] = Criteria.where(property).is(values[i]);
        }
        criteria.andOperator(criterias);
        return this;
    }

    public Mongo mySelf(IMyselfQuery myself) {
        myself.query(criteria);
        return this;
    }

    public Mongo eq(String key, Object value) {
        and(key).is(value);
        return this;
    }

    public Mongo fuzzy(String key, Object value) {
        String re = String.valueOf(value);
        re = RegexUtils.compile(re);
        and(key).regex(re, "i");
        return this;
    }

    public Mongo ne(String key, Object value) {
        and(key).ne(value);
        return this;
    }

    public Mongo or(String key, Object... value) {
        criteria.orOperator(genOrCriteria(key, value));
        return this;
    }

    public Mongo or(String[] key, Object... value) {
        criteria.orOperator(genOrCriteria(key, value));
        return this;
    }

    public Mongo or(String[] key, Object[] value, OP[] operators) {
        criteria.orOperator(genOrCriteria(key, value, operators));
        return this;
    }

    public Mongo gte(String key, Object value) {
        and(key).gte(value);
        return this;
    }

    public Mongo gt(String key, Object value) {
        and(key).gt(value);
        return this;
    }

    public Mongo lt(String key, Object value) {
        and(key).lt(value);
        return this;
    }

    public Mongo lte(String key, Object value) {
        and(key).lte(value);
        return this;
    }


    public Mongo in(String key, Object... value) {
        and(key).in(value);
        return this;
    }

    public Mongo nin(String key, Object... value) {
        and(key).nin(value);
        return this;
    }

    public Mongo size(String key, int size) {
        and(key).size(size);
        return this;
    }

    public Mongo exists(String key, boolean flag) {
        and(key).exists(flag);
        return this;
    }

    private Criteria[] genOrCriteria(String key, Object[] value) {
        Criteria[] criterias = new Criteria[value.length];
        for (int i = 0; i < value.length; i++) {
            criterias[i] = Criteria.where(key).is(value[i]);
        }
        return criterias;
    }

    private Criteria[] genOrCriteria(String[] key, Object[] value) {
        Criteria[] criterias = new Criteria[value.length];
        for (int i = 0; i < value.length; i++) {
            criterias[i] = Criteria.where(key[i]).is(value[i]);
        }
        return criterias;
    }

    private Criteria[] genOrCriteria(String[] key, Object[] value, OP[] operators) {
        Criteria[] criterias = new Criteria[value.length];
        for (int i = 0; i < value.length; i++) {
            switch (operators[i]) {
                case IN:
                    String[] tempStr = (String[]) value[i];
                    criterias[i] = Criteria.where(key[i]).in(tempStr);
                    break;
                case F:
                    String re = String.valueOf(value[i]);
                    re = re.replace("(", "\\(").replace(")", "\\)");
                    criterias[i] = Criteria.where(key[i]).regex(re, "i");
                    break;
                case EQ:
                    criterias[i] = Criteria.where(key[i]).is(value[i]);
                    break;
                case GTE:
                    criterias[i] = Criteria.where(key[i]).gte(value[i]);
                    break;
                case LTE:
                    criterias[i] = Criteria.where(key[i]).lte(value[i]);
                    break;
                case NE:
                    criterias[i] = Criteria.where(key[i]).ne(value[i]);
                    break;
            }
        }
        return criterias;
    }


    public Mongo limit(int limit, int page) {
        if (page < 1) {
            throw new RuntimeException("page is invalid ...");
        }
        query.limit(limit);
        query.skip((page - 1) * limit);
        return this;
    }

    public Mongo limitSkip(int limit, int skip) {
        if (skip < 0) {
            throw new RuntimeException("skip is invalid ...");
        }
        query.limit(limit);
        query.skip(skip);
        return this;
    }

    public Mongo desc(String... properties) {
        query.with(new Sort(Sort.Direction.DESC, properties));
        return this;
    }

    public Mongo asc(String... properties) {
        query.with(new Sort(Sort.Direction.ASC, properties));
        return this;
    }

    public Mongo type(String key, int type) {
        and(key).type(type);
        return this;
    }

    public enum MongoType {
        UPDATE, INSERT
    }

    private String collectionName(Object obj) {
        Class clazz = obj.getClass();
        return collectionName(clazz);
    }

    private String collectionName(Class clazz) {
        if (clazz.isAnnotationPresent(Document.class)) {
            Document table = (Document) clazz.getAnnotation(Document.class);
            return table.name();
        } else {
            return clazz.getSimpleName();
        }
    }

    public MongoType insert(IMongoUpdate update, Object obj) {
        return insert(update, obj, collectionName(obj));
    }

    public MongoType insert(IMongoUpdate update, Object obj, String collectionName) {
        if (count(collectionName) > 0) {
            if (update != null) {
                updateFirst(update, collectionName);
            }
            return MongoType.UPDATE;
        } else {
            insert(obj, collectionName);
            return MongoType.INSERT;
        }
    }

    public WriteResult upsert(IMongoUpdate update, String collectionName) {
        Update u = new Update();
        update.update(u);
        return mongoOperations.upsert(query, u, collectionName);
    }

    public WriteResult updateMulti(IMongoUpdate update, String collectionName) {
        Update u = new Update();
        update.update(u);
        return mongoOperations.updateMulti(query, u, collectionName);
    }

    public void updateFirst(IMongoUpdate update, String collectionName) {
        Update u = new Update();
        update.update(u);
        mongoOperations.updateFirst(query, u, collectionName);
    }

    public void updateFirst(IMongoUpdate update, Object obj) {
        Update u = new Update();
        update.update(u);
        mongoOperations.updateFirst(query, u, collectionName(obj));
    }


    public void remove(String collectionName) {
        mongoOperations.remove(query, collectionName);
    }


    public long count(String collectionName) {
        System.out.println("query count:" + query.toString());
        return mongoOperations.count(query, collectionName);
    }

    public List find(Class clazz) {
        System.out.println("query:" + query.toString());
        return mongoOperations.find(query, clazz, collectionName(clazz));
    }

    public <T> T findAndUpdate(Class clazz, String collectionName, IMongoUpdate update) {
        Update u = new Update();
        update.update(u);
        return (T) mongoOperations.findAndModify(query, u, clazz, collectionName);
    }


    public Long sum(String sum, Class clazz, String... group) {
        return new MyAggregation<Long>() {
            @Override
            public GroupOperation.GroupOperationBuilder nextGroupOperation(GroupOperation groupOperation, String key) {
                return groupOperation.sum(key);
            }

            @Override
            public Long valueOf(String value) {
                return Double.valueOf(value).longValue();
            }
        }.handler(sum, collectionName(clazz), group);
    }

    public GroupOperation fillFields(Class clazz, GroupOperation groupOperation) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            groupOperation = groupOperation.first(field.getName()).as(field.getName());
        }
        fields = clazz.getSuperclass().getDeclaredFields();
        for (Field field : fields) {
            groupOperation = groupOperation.first(field.getName()).as(field.getName());

        }
        return groupOperation;
    }

    public Double avg(String avg, Class clazz, String... group) {
        return new MyAggregation<Double>() {
            @Override
            public GroupOperation.GroupOperationBuilder nextGroupOperation(GroupOperation groupOperation, String key) {
                return groupOperation.avg(key);
            }

            @Override
            public Double valueOf(String value) {
                return Double.valueOf(value);
            }
        }.handler(avg, collectionName(clazz), group);

    }

    private abstract class MyAggregation<T extends Number> {
        public T handler(String key, String collectionName, String... group) {
            String valueKey = "_" + key;
            GroupOperation groupOperation = new GroupOperation(Fields.fields(group));
            AggregationResults aggregationResults = mongoOperations.aggregate(Aggregation.newAggregation(new MatchOperation(criteria), nextGroupOperation(groupOperation, key).as(valueKey)), collectionName, HashMap.class);
            List<Map> maps = aggregationResults.getMappedResults();
            Double total = 0d;
            for (Map map : maps) {
                total += Double.valueOf(String.valueOf(map.get(valueKey)));
            }
            return valueOf(String.valueOf(total));
        }

        public abstract T valueOf(String value);

        public abstract GroupOperation.GroupOperationBuilder nextGroupOperation(GroupOperation groupOperation, String key);
    }

    public List find(Class clazz, String collectionName) {
        LOGGER.debug(query.toString());
        return mongoOperations.find(query, clazz, collectionName);
    }

    public <T> T findOne(Class clazz) {
        return (T) mongoOperations.findOne(query, clazz, collectionName(clazz));
    }

    public <T> T findOne(Class clazz, String collectionName) {
        return (T) mongoOperations.findOne(query, clazz, collectionName);
    }

    public CommandResult executeCommand(String json) {
        return mongoOperations.executeCommand(json);
    }

    public void insert(Object obj) {
        insert(obj, collectionName(obj));
    }

    public void insert(Object obj, String collectionName) {
        mongoOperations.insert(obj, collectionName);
    }

    public List findAll(Class clazz, String collectionName) {
        return mongoOperations.findAll(clazz, collectionName);
    }

    public void ensureIndex(String name, Order order, String collectionName) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put(name, order.getOrderValue());
        mongoOperations.getCollection(collectionName).createIndex(dbObject);
    }

    public void ensureIndex2D(String name, String collectionName) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put(name, "2d");
        mongoOperations.getCollection(collectionName).createIndex(dbObject);
    }


    public Mongo between(String key, Object begin, Object end, Between between) {
        between.between(and(key), begin, end);
        return this;
    }

    public enum Order {
        desc(-1), asc(1);

        private int orderValue;

        Order(int orderValue) {
            this.orderValue = orderValue;
        }

        public int getOrderValue() {
            return orderValue;
        }
    }

    public enum Between {
        EQ, NEQ, FEQ, EEQ;

        public void between(Criteria criteria, Object begin, Object end) {
            switch (this) {
                case EQ:
                    criteria.lte(end).gte(begin);
                    break;
                case NEQ:
                    criteria.lt(end).gt(begin);
                    break;
                case FEQ:
                    criteria.lt(end).gte(begin);
                    break;
                case EEQ:
                    criteria.lte(end).gt(begin);
                    break;
                default:
                    throw new QueryException("no Between enum");
            }
        }
    }
}
