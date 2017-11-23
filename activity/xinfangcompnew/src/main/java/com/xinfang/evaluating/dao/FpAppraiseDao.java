package com.xinfang.evaluating.dao;

import com.xinfang.evaluating.model.FpAppraiseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 投诉办理评价Dao接口
 * Created by sunbjx on 2017/5/8.
 */
public interface FpAppraiseDao extends JpaRepository<FpAppraiseEntity, Integer>, JpaSpecificationExecutor<FpAppraiseEntity> {

    /**
     * 通过案件ID查询评价信息
     *
     * @param caseId
     * @return
     */
    @Query("from FpAppraiseEntity T where T.caseId =:caseId")
    List<FpAppraiseEntity> getByCaseId(@Param("caseId") Integer caseId);
    
    @Query("from FpAppraiseEntity T where T.caseId =:caseId and T.userid=:userId")
    List<FpAppraiseEntity> getByCaseIdAndUserId(@Param("caseId") Integer caseId,@Param("userId") Integer userId);

}
