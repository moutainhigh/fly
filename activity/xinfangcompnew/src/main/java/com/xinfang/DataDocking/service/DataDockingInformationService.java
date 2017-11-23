package com.xinfang.DataDocking.service;

import java.util.Map;

public interface DataDockingInformationService {
	/**
	 * 获取重点人员信息
	 * 
	 * @param startTime
	 * @param endTime
	 * @param keyPersonId
	 * @param startPage
	 * @param pageCount
	 * @return
	 */
	Map<String, Object> getKeyPersonInformation(String startTime,
			String endTime, Integer keyPersonId, Integer startPage,
			Integer pageCount);
	/**
	 * 获取矛盾问题详细信息
	 * @param startTime
	 * @param endTime
	 * @param keyPersonId
	 * @param startPage
	 * @param pageCount
	 * @return
	 */
	Map<String, Object> getContradictionInformation(String startTime,
			String endTime, Integer keyPersonId, Integer startPage,
			Integer pageCount);
	/**
	 * 获取情报信息列表
	 * @param startTime
	 * @param endTime
	 * @param keyPersonId
	 * @param startPage
	 * @param pageCount
	 * @return
	 */
	Map<String, Object> getIntellgenceInformation(String startTime,
			String endTime, Integer keyPersonId, Integer startPage,
			Integer pageCount);
}
