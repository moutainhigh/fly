package com.xinfang.dao;

import com.xinfang.model.DisputeCaseEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by sunbjx on 2017/5/17.
 */
public interface DisputeCaseDao extends CrudRepository<DisputeCaseEntity, Integer> {

	/**
	 * 通过矛盾纠纷编码获取矛盾纠纷诉求
	 * 
	 * @param petitionCode
	 * @return
	 */
	@Query("from DisputeCaseEntity T where T.petitionCode =:petitionCode")
	DisputeCaseEntity getByPetitionCode(@Param("petitionCode") String petitionCode);

	/**
	 * 通过矛盾纠纷案件ID更新案件处理状态
	 * 
	 * @param caseHandleState
	 * @param disputeCaseId
	 */
	@Modifying
	@Query("update DisputeCaseEntity T set T.caseHandleState =:caseHandleState where T.disputeCaseId =:disputeCaseId")
	void updateCaseHandleStateById(@Param("caseHandleState") Integer caseHandleState,
			@Param("disputeCaseId") Integer disputeCaseId);

	/**
	 * 统计今日记录
	 * @param prefixPetitionCode
	 * @return
	 */
	@Query("select count(*) from DisputeCaseEntity f where f.petitionCode like :prefixPetitionCode")
	int countCurrentRecord(@Param("prefixPetitionCode") String prefixPetitionCode);
}
