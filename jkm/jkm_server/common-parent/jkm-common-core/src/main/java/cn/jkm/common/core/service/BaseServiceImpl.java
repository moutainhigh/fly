package cn.jkm.common.core.service;

import java.io.Serializable;
import cn.jkm.common.core.dao.BaseDao;

public abstract class BaseServiceImpl<T, K extends Serializable> implements BaseService<T, K> {
	public abstract BaseDao<T, K> getDao();

	public void save(T t) {
		this.getDao().save(t);
	}

	public void update(T t) {
		this.getDao().update(t);
	}

	public void delete(T t) {
		this.getDao().delete(t);
	}

	public T get(K k) {
		return this.getDao().get(k);
	}
}
