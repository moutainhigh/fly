package com.xinfang.service;

import com.xinfang.VO.FdCaseCountVO;

/**
 * 
 * @author zhangbo
 * 
 */
public interface FdCaseCountService {
	/**
	 * 案件办理期限监控
	 */
	FdCaseCountVO countCase(String stratTime, String endTime);

	/**
	 * 案件处理类型统计
	 */
	FdCaseCountVO countCaseBack(String startTime, String endTime);

	/**
	 * 案件相关情况统计
	 */
	FdCaseCountVO countCaseSituation(String startTime, String endTime);

	/**
	 * 案件处理状态统计
	 */
	FdCaseCountVO countCaseState(String startTime, String endTime);

	/**
	 * 案件时间监控统计
	 */
	FdCaseCountVO countCaseTime(String endTime);

	/**
	 * 案件归属地统计
	 */
	FdCaseCountVO countCaseBelongTo(String startTime, String endTime);

	/**
	 * 案件处理相关统计+案件登记相关统计
	 */
	FdCaseCountVO countCase(String startTime, String endTime,
			Integer caseHandling, Integer caseregistration, Integer way);
}
