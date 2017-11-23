package com.xinfang.foursectionsdata.service;

import java.util.Map;


public interface ExpertCaseService {
	
	/**
	 * 
	 * @param fuzzy 模糊搜索功能
	 * @param startTime 起始时间
	 * @param endTime 结束时间
	 * @param petitionWay 信访方式
	 * @param belongTo 问题归属地
	 * @param PetitionDepartment 信访部门
	 * @param department 责任部门
	 * @return
	 */
	Map<String, Object> getCaseList(String fuzzy,String startTime,String endTime,Integer petitionWay,Integer belongTo,Integer PetitionDepartment,Integer department,Integer startPage,Integer pageCount);
	/**
	 * 
	 * @param fuzzy 模糊搜索
	 * @param record 学历
	 * @param terrytory 13大类
	 * @param ksId 所在科室ID
	 * @param departmentId 信访部门ID
	 * @return
	 */
	Map<String, Object> getExpertList(String fuzzy,String record,Integer terrytory,Integer ksId,Integer departmentId,Integer startPage,Integer pageCount);
	/**
	 * 
	 * @param ID
	 * @return
	 */
	Map<String, Object> getCase(Integer id);

}
