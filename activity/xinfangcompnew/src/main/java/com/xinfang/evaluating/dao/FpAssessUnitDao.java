package com.xinfang.evaluating.dao;

import com.xinfang.evaluating.model.FpAssessUnitEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by sunbjx on 2017/5/16.
 */
public interface FpAssessUnitDao extends CrudRepository<FpAssessUnitEntity, Integer> {

    /**
     * 通过考核ID和考核单位类型删除考核单位信息
     * @param assessId
     * @return
     */
    @Modifying
    @Query("delete FpAssessUnitEntity T where T.assessId =:assessId and T.assessUnitType =:assessUnitType")
    void removeByAssessIdAndAssessUnitType(@Param("assessId") Integer assessId, @Param("assessUnitType") Integer assessUnitType);

    /**
     * 通过考核ID删除考核单位信息
     * @param assessId
     */
    @Modifying
    @Query("delete FpAssessUnitEntity T where T.assessId =:assessId")
    void removeByAssessId(@Param("assessId") Integer assessId);

    /**
     * 通过考核ID获取考核单位信息
     * @param assessId
     * @return
     */
    @Query("from FpAssessUnitEntity T where T.assessId =:assessId")
    List<FpAssessUnitEntity> getByAssessId(@Param("assessId") Integer assessId);

    /**
     * 通过考核ID和考核单位类型查询考核单位信息
     * @param assessId
     * @param assessUnitType
     * @return
     */
    @Query("from FpAssessUnitEntity T where T.assessId =:assessId and T.assessUnitType =:assessUnitType")
    List<FpAssessUnitEntity> getByAssessIdAndAssessUnitType(@Param("assessId") Integer assessId, @Param("assessUnitType") Integer assessUnitType);
    /**
     * 通关过考核ID获取单位ID
     * @param assessId
     * @return
     */
    @Query("select T.unitId from FpAssessUnitEntity T where T.assessId =:assessId")
    List<Integer> getUnitIdByAssessId(@Param("assessId") Integer assessId);
    /**
     * 通过考核id获取单位总数
     * @param assessId
     * @return
     */
    @Query(value="SELECT (SELECT COUNT(*) FROM fp_assess_unit where assess_unit_type =1 AND assess_id =:assessId)as btotal,"
    		+ " (SELECT COUNT(*) FROM fp_assess_unit where assess_unit_type =0 AND assess_id =:assessId )as atotal,count(*) AS total "
    		+ " FROM fp_assess_unit WHERE  assess_id =:assessId",nativeQuery=true)
     Map<String, Object> getUnitCountByAssessId(@Param("assessId") Integer assessId);
    /**
     * 通过考核ID获取总分数
     * @param assessId
     * @return
     */
    @Query(value="SELECT (SELECT sum(reality_score) FROM fp_assess_score where templet_type =1 AND fid is null AND assess_id = :assessId)as btotal,"
    		+ "(SELECT sum(reality_score) FROM fp_assess_score where templet_type =0 AND  fid is null AND assess_id=:assessId)as atotal,"
    		+ "sum(reality_score) AS total FROM fp_assess_score WHERE fid is null  AND assess_id=:assessId",nativeQuery=true)
    Map<String, Object> getScoreCountByAssessId(@Param("assessId") Integer assessId);
    

}
