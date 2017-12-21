package com.xinfang.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xinfang.model.FcSfbEntity;

/**
 * 省份
 * @author sunbjx
 * Date: 2017年6月6日下午4:18:18
 */
public interface SfbDao extends CrudRepository<FcSfbEntity, Integer> {

	/**
	 * 通过省份ID查询省份名称
	 * @param provinceId
	 * @return
	 */
	@Query("select sfMc from FcSfbEntity T where T.sfId =:provinceId")
	String getProvinceNameById(@Param("provinceId") Integer provinceId);
}
