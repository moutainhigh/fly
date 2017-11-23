package com.xinfang.evaluating.dao;

import com.xinfang.evaluating.model.FpAssessScoreEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by sunbjx on 2017/5/17.
 */
public interface FpAssessScoreDao extends CrudRepository<FpAssessScoreEntity, Integer> {
	
	@Query(value="select templet_id from fp_assess_score where fid is null AND assess_unit_id =:unitId AND assess_id=:assessId",nativeQuery=true)
	Integer getParentIdByUnitId(@Param("unitId") Integer UnitId,@Param("assessId")Integer assessId);
	/*
	@Query("select standard from fp_assess_score where fid is null AND assess_unit_id =:unitId AND assess_id=:assessId")
	 String getScoreIdByunitId(@Param("unitId") Integer UnitId,@Param("assessId") Integer assessId);*/
	@Query("FROM FpAssessScoreEntity WHERE fid is null AND  assessId=:assessId AND assessUnitId=:assessUnitId")
	FpAssessScoreEntity getParentObjectById(@Param("assessId")Integer assessId,@Param("assessUnitId")Integer assessUnitId);
	@Modifying
	@Query("delete FpAssessScoreEntity T where T.assessId =:assessId")
	void deleteScoreByAssessId(@Param("assessId") Integer assessId);
	@Query(value="SELECT templet_id FROM fp_assess_score WHERE assess_unit_id=:unitId and assess_id =:assessId AND  standard =:itemType ANd  fid = (SELECT templet_id FROM fp_assess_score WHERE fid IS NULL And assess_id=:assessId And assess_unit_id=:unitId)",nativeQuery=true)
	Integer getParent(@Param("itemType") String itemType, @Param("assessId") Integer assessId,@Param("unitId") Integer unitId);


	
}
