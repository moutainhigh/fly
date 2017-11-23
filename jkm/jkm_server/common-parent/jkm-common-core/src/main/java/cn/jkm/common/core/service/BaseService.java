package cn.jkm.common.core.service;

import java.io.Serializable;

public interface BaseService<T,K extends Serializable> {
	void save(T t);
	void update (T t);
	void delete (T t);
	T get(K k);
}
