package com.xinfang.dao;


import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xinfang.model.FdCase;

public interface FdCaseCountRepository extends JpaRepository<FdCase, Integer>{
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * 案件办理期限监控统计
	 */
	@Query(value="select sum(case when case_handle_state =2001 then 1 end) as 未办理  from (select * FROm fd_case where handle_period_end between (:starttime) and (:endtime)) as aa",nativeQuery = true)
	Integer CountCasenotHandled(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	@Query(value="select sum(case when case_handle_state =2001 then 1 end) as 逾期未办理 from (select * FROm fd_case where handle_period_end > (:endtime)) as aa",nativeQuery=true)
	Integer CountCaseoverdue(@Param("endtime") Date endtime);
	@Query(value="select sum(case when case_handle_state =2002 then 1 end) as 办理中  from (select * FROm fd_case where handle_period_end between (:starttime) and (:endtime)) as aa",nativeQuery = true)
	Integer CountCasehandled(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	@Query(value="select sum(case when case_handle_state =2003 then 1 end) as 逾期办结  from (select * FROm fd_case where handle_period_end > (:endtime)) as aa",nativeQuery = true)
	Integer CountCaseoverdueHandled(@Param("endtime") Date endtime);
	@Query(value="select sum(case when case_handle_state =2003 then 1 end) as 按期办结  from (select * FROm fd_case where handle_period_end < (:endtime)) as aa",nativeQuery = true)
	Integer CountCaselimitHandled(@Param("endtime") Date endTime);
	@Query(value="select sum(case when case_handle_state =2002 then 1 end) as 限期过半  from (select * FROm fd_case where (select datediff(handle_period_end, (:endtime))*2) > handle_duration ) as aa",nativeQuery = true)
	Integer CountCaselimitMore(@Param("endtime") Date endtime);
	
	/**
	 * 案件处理类型统计
	 */
	@Query(value="select sum(case when type =1 then 1 end) as 交办  from (select * FROm fd_case_feedback where creat_time between (:starttime) and (:endtime)) as aa",nativeQuery = true)
	Integer countCaseBackAssign(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	
	 @Query(value="select sum(case when type =2 then 1 end) as 转办  from (select * FROm fd_case_feedback where creat_time between (:starttime) and (:endtime)) as aa",nativeQuery = true)
	 Integer countCaseBackTurn(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 
	 @Query(value="select sum(case when type =3 then 1 end) as 受理  from (select * FROm fd_case_feedback where creat_time between (:starttime) and (:endtime)) as aa",nativeQuery = true)
	 Integer countCaseBackAllow(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 
	 @Query(value="select sum(case when type =4 then 1 end) as 直接回复  from (select * FROm fd_case_feedback where creat_time between (:starttime) and (:endtime)) as aa",nativeQuery = true)
	 Integer countCaseBackReply(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 
	 @Query(value="select sum(case when type =5 then 1 end) as 不予受理  from (select * FROm fd_case_feedback where creat_time between (:starttime) and (:endtime)) as aa",nativeQuery = true)
	 Integer countCaseBackRefuse(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 
	 @Query(value="select sum(case when type =6 then 1 end) as 不再受理  from (select * FROm fd_case_feedback where creat_time between (:starttime) and (:endtime)) as aa",nativeQuery = true)
	 Integer countCaseBackRefusel(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 
	 @Query(value="select sum(case when type =7 then 1 end) as 存件 from (select * FROm fd_case_feedback where creat_time between (:starttime) and (:endtime)) as aa",nativeQuery = true)
	 Integer countCaseBackSave(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 
	 @Query(value="select sum(case when type in (9,10) then 1 end) as 待审件  from (select * FROm fd_case_feedback where creat_time between (:starttime) and (:endtime)) as aa",nativeQuery = true)
	 Integer countCaseBackPending(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 
	 /**
	  * 案件相关情况统计
	  */
	
	 
	 /**
	  * 案件处理状态统计
	  */

	 
	 /**
	  * 案件时间监控统计
	  */
	 @Query(value="select sum(case when case_handle_state in (2001,2002) then 1 end) as 正常  from (select * FROm fd_case where (select datediff(handle_period_end, (:endtime))) > handle_duration*0.31) as aa",nativeQuery=true)
	 Integer countCaseNormal(@Param("endtime") Date endtime);
	 @Query(value="select sum(case when case_handle_state in (2001,2002) then 1 end) as 预警  from (select * FROm fd_case where (select datediff(handle_period_end, (:endtime))) between handle_duration*0.3 and handle_duration*0.5) as aa",nativeQuery=true)
	 Integer countCasePre(@Param("endtime") Date endtime);
	 @Query(value="select sum(case when case_handle_state in (2001,2002) then 1 end) as 警告  from (select * FROm fd_case where (select datediff(handle_period_end, (:endtime))) between handle_duration*0.51 and handle_duration*0.75) as aa",nativeQuery=true)
	 Integer countCaseWarning(@Param("endtime") Date endtime);
	 @Query(value="select sum(case when case_handle_state in (2001,2002) then 1 end) as 严重警告  from (select * FROm fd_case where (select datediff(handle_period_end, (:endtime))) between handle_duration*0.76 and handle_duration*1) as aa",nativeQuery=true)
	 Integer countCaseSerious(@Param("endtime") Date endtime);
	 @Query(value="select sum(case when case_handle_state in (2001,2002) then 1 end) as 超期   from (select * FROm fd_case where (select datediff(handle_period_end, (:endtime))) > handle_duration) as aa",nativeQuery=true)
	 Integer countCaseSuperDate(@Param("endtime") Date endtime);
	 /**
	  * 案件归属地统计
	  */
	 @Query(value="select sum(case when question_belonging_to = 1500 then 1 end) as guanShanHu from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	   Integer countCaseBelongToGuanShanHu(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1501 then 1 end) as baiYun from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToBaiYun(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1502 then 1 end) as economic from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToEconomic(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1503 then 1 end) as shuangLong from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToShuangLong(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1504 then 1 end) as bond from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToBond(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1505 then 1 end) as highTech from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToHighTech(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1506 then 1 end) as guiZhou from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToGuiZhou(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1507 then 1 end) as guiYang from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToGuiYang(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1508 then 1 end) as nanMing from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToNanMing(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1509 then 1 end) as yunYan from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToyunYan(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1510 then 1 end) as xiuWen from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToXiuWen(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1511 then 1 end) as xiFeng from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToXiFeng(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1512 then 1 end) as wuDang from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToWuDang(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1513 then 1 end) as qingZhen from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToQingZhen(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1514 then 1 end) as kaiYang from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToKaiYang(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
	 @Query(value="select sum(case when question_belonging_to = 1515 then 1 end) as huaXi from (select * FROm fd_case where case_time between (:starttime) and (:endtime)) as FdCaseCountVO",nativeQuery=true)
	 Integer countCaseBelongToHuaXi(@Param("starttime") Date startTime,@Param("endtime") Date endTime);
}
