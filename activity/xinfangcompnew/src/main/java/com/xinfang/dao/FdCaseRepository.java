package com.xinfang.dao;

import com.xinfang.model.FdCase;
import com.xinfang.model.FdCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface FdCaseRepository extends JpaRepository<FdCase, Integer>, JpaSpecificationExecutor<FdCase> {

	/**
	 * 统计当日案件数
	 * 
	 * @param prefixPetitionCode
	 * @return
	 */
	@Query("select count(*) from FdCase f where f.petitionCode like :prefixPetitionCode")
	int countCurrentRecord(
			@Param("prefixPetitionCode") String prefixPetitionCode);

	/**
	 * 通过案件编码查询案件信息
	 * 
	 * @param petitionCode
	 * @return
	 */
	@Query("from FdCase f where f.petitionCode =:petitionCode")
	FdCase getCaseByPetitionCode(@Param("petitionCode") String petitionCode);

	/**
	 * 通过案件ID更新案件状态
	 * 
	 * @param caseHandleState
	 * @param caseId
	 * @return
	 */
	@Modifying(clearAutomatically = true)
	@Query("update FdCase f set f.caseHandleState =:caseHandleState where f.id =:caseId")
	int updateCaseHandleStateById(
			@Param("caseHandleState") Integer caseHandleState,
			@Param("caseId") Integer caseId);

	/**
	 * 根据案件id获取案件信息
	 */
	@Query("from FdCase f where f.id=:id")
	FdCase findById(@Param("id") Integer caseId);
	
	List<FdCase> findByIdIn(Integer[] ids);

	@Query("from FdCode f where f.code=:code")
	FdCode findNameByCode(@Param("code") Integer code);
	

	/**
	 * 按期办结统计
	 * 
	 * @param handleState
	 * @return
	 */
	@Query("select count(*) from FdCase f where f.handleDuration = 0 and f.caseHandleState =:handleState")
	int countPeriodFinish(@Param("handleState") Integer handleState);

	/**
	 * 期限过半统计
	 * 
	 * @param handleState
	 * @return
	 */
	@Query(value = "SELECT count(*) FROM fd_case f WHERE f.handle_duration = 0 "
			+ "AND f.case_handle_state !=:handleState "
			+ "AND timestampdiff(DAY, f.handle_period_end, f.handle_period_start) / 2 "
			+ " < timestampdiff(DAY, f.handle_period_end, current_timestamp)", nativeQuery = true)
	int timeLimitMoreThanHalf(@Param("handleState") Integer handleState);


	@Query(value = "FROM FdCase f WHERE f.handleDuration = 0 "
			+ "AND f.caseHandleState !=:handleState "
			+ "AND timestampdiff(DAY, f.handle_period_end, f.handle_period_start) / 2 "
			+ " < timestampdiff(DAY, f.handle_period_end, current_timestamp)", nativeQuery = true)
	int timeLimitMoreThanHalfContent(@Param("handleState") Integer handleState);

	/**
	 * 逾期办结统计
	 * 
	 * @param handleState
	 * @return
	 */
	@Query("select count(*) from FdCase f where f.handleDuration > 0 and f.caseHandleState =:handleState")
	int countOverdueHas(@Param("handleState") Integer handleState);

	/**
	 * 逾期未结统计
	 * 
	 * @param handleState
	 * @return
	 */
	@Query("select count(*) from FdCase f where f.handleDuration > 0 and f.caseHandleState !=:handleState")
	int countOverdue(@Param("handleState") Integer handleState);

	/**
	 * 总的办理情况统计
	 * 
	 * @param handleState
	 * @return
	 */
	@Query("select count(*) from FdCase f where f.caseHandleState =:handleState")
	int countHandState(@Param("handleState") Integer handleState);

	/**
	 * 通过处理人ID 获取案件信息
	 * @param signInUserId
	 * @return
	 */
	@Query("from FdCase T where T.handleUserid =:signInUserId")
	List<FdCase> getByHandleUserid(@Param("signInUserId") Integer signInUserId);

	@Query("SELECT COUNT(*) FROM FdCase T WHERE T.isHandle IN (0,1) AND T.handleUserid =:signInUserId")
	int countTodoHandle(@Param("signInUserId") Integer signInUserId);

	/**
	 * 通过窗口编号查询案件
	 * @param windowNumber
	 * @return
	 */
	@Query("from FdCase T where T.windowNumber =:windowNumber")
	List<FdCase> getCaseByWindowNumber(@Param("windowNumber") Integer windowNumber);


	/**
	 * 根据案件处理人和案件处理状态查询(非流程的)
	 * @param signInUserId
	 * @param caseHandleState
	 * @return
	 */
	@Query("from FdCase T where T.handleUserid =:signInUserId and T.caseHandleState =:caseHandleState and T.isHandle =2 and T.isFlow != 1")
	List<FdCase> getByHandleUseridAndState(@Param("signInUserId") Integer signInUserId, @Param("caseHandleState") Integer caseHandleState);
	
	//@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update FdCase f set f.handleFactEndTime=:handleFactEndTime where f.id = :caseId")
	int updateHandleFactEndTime(@Param("handleFactEndTime") Date handleFactEndTime,
			@Param("caseId")Integer caseId);

	@Query("select f.gmtCreate from FdCase f where f.id=:id")
	Date findCreateTimeById(@Param("id") Integer caseId);
	
	@Query("from FdCase T where T.turnAuditState = 0 and T.creatorId =:creatorId")
	List<FdCase> getCaseByCreatorId(@Param("creatorId") Integer creatorId);
}
