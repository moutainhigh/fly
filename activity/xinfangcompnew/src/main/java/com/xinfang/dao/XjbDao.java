package com.xinfang.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xinfang.model.FcXjbEntity;

/**
 * 县级
 * @author sunbjx
 * Date: 2017年6月6日下午4:19:35
 */
public interface XjbDao extends CrudRepository<FcXjbEntity, Integer> {

	/**
	 * 通过县级ID获取县级名称
	 * @param countyId
	 * @return
	 */
	@Query("select xjMc from FcXjbEntity T where T.xjId =:countyId")
	String getCountyNameById(@Param("countyId") Integer countyId);
}
