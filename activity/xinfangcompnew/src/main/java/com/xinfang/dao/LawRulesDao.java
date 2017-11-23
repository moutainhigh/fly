package com.xinfang.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xinfang.model.LawRulesEntity;

/**
 * 
 * @author sunbjx
 * Date: 2017年5月25日上午10:13:21
 */
public interface LawRulesDao extends CrudRepository<LawRulesEntity, Integer>, JpaSpecificationExecutor<LawRulesEntity> {
	
	/**
	 * 根据法律法规ID获取信息
	 * @param id
	 * @return
	 */
	@Query("select content from LawRulesEntity T where T.id =:id")
	String getByLawId(@Param("id") Integer id);
}
