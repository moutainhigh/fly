package com.xinfang.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xinfang.model.FcSqbEntity;

/**
 * 市区
 * @author sunbjx
 * Date: 2017年6月6日下午4:19:25
 */
public interface SqbDao extends CrudRepository<FcSqbEntity, Integer> {

	/**
	 * 通过市区ID获取市区名称
	 * @param cityId
	 * @return
	 */
	@Query("select sqMc from FcSqbEntity T where T.sqId =:cityId")
	String getCityNameById(@Param("cityId") Integer cityId);
}
