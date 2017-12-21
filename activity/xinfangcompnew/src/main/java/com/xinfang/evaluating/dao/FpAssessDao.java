package com.xinfang.evaluating.dao;

import com.xinfang.evaluating.model.FpAssessEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by sunbjx on 2017/5/16.
 */
public interface FpAssessDao extends CrudRepository<FpAssessEntity, Integer>, JpaSpecificationExecutor<FpAssessEntity> {

    /**
     * 逻辑删除更新状态
     * @param assessId
     */
    @Modifying
    @Query("update FpAssessEntity T set T.state = -1 where T.assessId =:assessId")
    void updateState(@Param("assessId") Integer assessId);
    
    @Query("from FpAssessEntity where assessId=:assessId")
    FpAssessEntity getFpAssessEntityByAssessId(@Param("assessId") Integer assessId);
}
