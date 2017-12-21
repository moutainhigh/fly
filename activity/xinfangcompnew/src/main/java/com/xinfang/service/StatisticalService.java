package com.xinfang.service;

import java.util.List;
import java.util.Map;

/**
 * 统计查询接口 Created by sunbjx on 2017/4/17.
 */
public interface StatisticalService {

	/**
	 * 首页列表统计查询
	 * 
	 * @param authFlag
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param handleType
	 * @param registerType
	 * @return
	 */
	List<Map<String, Object>> packaging(int authFlag, Integer userId, String startDate, String endDate, int handleType,
			int registerType);

	/**
	 * 首页角标统计
	 * 
	 * @param userId
	 * @return
	 */
	Map<String, Object> typeNums(Integer userId);

	/**
	 * 日报
	 * 
	 * @param startTime
	 * @param endTime
	 * @param registerUnitId
	 * @param registerUserId
	 * @param transferUnitId
	 * @param transferUserId
	 * @param dutyUnitId
	 * @param dutyUserId
	 * @param keywords
	 * @return
	 */
	List<Map<String, Object>> dailyReport(String startTime, String endTime, String keywords);

	/**
	 * 日报明细
	 * 
	 * @param createUnitId
	 * @param creatorId
	 * @param startTime
	 * @param endTime
	 * @param keywords
	 * @return
	 */
	List<Map<String, Object>> dailyReportDetail(Integer createUnitId, Integer creatorId, String startTime,
			String endTime, String keywords);

	List<Map<String, Object>> zhtj(String fl, String msg, String ct1, String ct2, String ct3, String ct4, String ct5, String ct6,
			String ct7, String ct8, String ct9);

}
