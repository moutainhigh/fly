package com.xinfang.dao;

import com.xinfang.model.FdCaseFeedbackAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-27 11:19
 **/
public interface FdCaseFeedbackAllRepository extends JpaRepository<FdCaseFeedbackAll, Integer>, JpaSpecificationExecutor<FdCaseFeedbackAll> {
	
	@Query("From FdCaseFeedbackAll f where f.caseId=:caseId order by f.creatTime asc")
	ArrayList<FdCaseFeedbackAll> findCaseFeedbackAllById(@Param("caseId") Integer caseId);
	
	@Query(value ="select f.create_time From fd_case_feedback f where f.CASE_ID=:caseId order by f.create_time desc limit 1",nativeQuery = true)
	Date getlastUpdateTime(@Param("caseId") Integer caseId);
	
	/**
	 * 
	 * @param caseId
	 * @description 获取自己退回的案件
	 * @author ZhangHuaRong
	 * @update 2017年4月7日 下午4:18:09
	 */
	@Query(value ="select f.creatTime From fd_case_feedback f where f.caseId=:caseId order by f.creatTime desc limit 1",nativeQuery = true)
	List<Integer> getreturncase(@Param("caseId") Integer caseId);
	
	@Query(value ="SELECT a.CASE_ID from fd_case_feedback a where a.CREATER_ID =:userId and a.TYPE =:value GROUP BY a.CASE_ID",nativeQuery = true)
	List<Integer> getreturncase(@Param("userId") Integer userId,@Param("value")  int value);

	@Query(value ="SELECT count(*) from fd_case_feedback a where a.CREATER_ID =:userId and a.TYPE =:value",nativeQuery = true)
	Integer getCaseCount(@Param("userId") Integer userId,@Param("value")  int value);

	List<FdCaseFeedbackAll> findByCaseIdAndDepId(int caseid, Integer depId);
	
	@Query("From FdCaseFeedbackAll f where f.caseId=:caseid and f.type=:type")
	List<FdCaseFeedbackAll> findbycaseidandtype(@Param("caseid")int caseid,@Param("type")int type);

	@Query("select max(createTime) from FdCaseFeedbackAll where caseId = :caseId")
	Date findLatestCreateTimeByCaseId(@Param("caseId") int caseId);

	@Query("select caseId from FdCaseFeedbackAll where createrId = :createrId group by caseId")
	List<Integer> findCaseFeedbackAllByCreaterId(@Param("createrId") int createrId);
	
	/**
	 * 更新反馈去重
	 * @param type
	 * @param caseId
	 * @return
	 */
	@Query("from FdCaseFeedbackAll T where T.caseId =:caseId and T.type =:type")
	List<FdCaseFeedbackAll> getByCaseIdAndType(@Param("caseId") Integer caseId, @Param("type") Integer type);
	
	public void deleteByCaseId(Integer caseId);
}
