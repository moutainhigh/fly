package com.xinfang.foursectionsdata.service;

import java.util.Map;

public interface PolicyLawService {
	/**
	 * 政策法规列表
	 * @param startPage 起始页数
	 * @param pageCount 每页条数
	 * @param fuzzy 模糊搜索 
	 * @param lawType 类型
	 * @Param startTime 起始时间
	 * @param endTime 结束时间
	 * 
	 */
		 public Map<String, Object> getLawList(Integer startPage,Integer pageCount,Integer lawType,String fuzzy,String startTime,String endTime);
	/**
	 * 获取政策法规详情
	 * @param lawId 法规ID
	 * 
	 */
		 public Map<String, Object> getLawDetailsById(Integer lawId);
	/**
	 * 获取政策法规分类列表
	 * 
	 */
		 public Map<String, Object> getTypeList();

}
