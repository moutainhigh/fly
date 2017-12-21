package com.xinfang.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xinfang.model.FcDwb;

/**
 * 
 * @author zhangbo
 *机构列表
 */
public interface OrganizationRepository extends JpaRepository<FcDwb, Integer>{
	/**
	  * 通过区县市ID获取单位列表
	  */
	

}
