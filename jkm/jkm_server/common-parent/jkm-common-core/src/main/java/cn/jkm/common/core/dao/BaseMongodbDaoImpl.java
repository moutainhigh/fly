package cn.jkm.common.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class BaseMongodbDaoImpl<T, K extends Serializable> implements BaseDao<T, K> {

	public BaseMongodbDaoImpl() {

	}

	protected MongoTemplate mongoTemplate;

	@Autowired
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void save(T entity) {
		this.mongoTemplate.insert(entity);
	}

	public void delete(T entity) {
		this.mongoTemplate.remove(entity);
	}

	@SuppressWarnings("unchecked")
	public T get(K k) {
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return this.mongoTemplate.findById(k, clazz);
	}
}
