package cn.jkm.common.core.dao;

import java.io.Serializable;

public interface BaseDao<T,K extends Serializable>{
	void save(T entity);
	void delete(T entity);
	void update(T entity);
	T get(K k);
}
