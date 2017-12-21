package com.xinfang.evaluating.dao;

import java.util.List;

import com.xinfang.evaluating.model.TemplateMaintenanceEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TemplateMaintenanceDao extends JpaRepository<TemplateMaintenanceEntity,Integer>{

	 @Modifying
     @Transactional
     @Query(value="delete from fp_check_templet where templet_id = :id",nativeQuery=true)
	 Integer deleteTemplate(@Param("id")Integer id);
	 
	 @Query("FROM TemplateMaintenanceEntity")
	 List<TemplateMaintenanceEntity> getTemplateMaintenanceEntityList();
	 @Query(value="select templet_id from fp_check_templet where FID is null And templet_type=:type",nativeQuery=true)
	 Integer getTemplateMaintenance(@Param("type") Integer type);
	 @Query(" FROM TemplateMaintenanceEntity where templateType = :type")
	 List<TemplateMaintenanceEntity> getListBytemlateType(@Param("type") Integer type);
	
	
	
	
}
