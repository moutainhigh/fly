package com.xinfang.foursectionsdata.service;

import java.util.Map;





public interface FourSectionsDataService {
	
	 /**@param isRepeatPetition 0初访事项  1重访事项
	  * @param startPage  起始页数
	  * @param pageCount 每页条数
	  * @param unitId 单位ID
	  * @param fuzzy 模糊搜索
	  * @param belongTo 问题归属地
	  * @param petitionWay 到访方式
	  * 初访重访事项列表
	  */
		public Map<String, Object> getListByisRepeatPetition(Integer isRepeatPetition,Integer startpage,Integer pageCount,Integer unitId,Integer belongTo,String fuzzy,Integer petitionWay,String startTime,String endTime);
	/**
	 * @param startPage 起始页数
	 * @param pageCount 每页条数
	 * @param fuzzy 模糊搜索
	 * @param unitId 单位ID
	 * @param belongTo 问题归属地
	 * @param petitionWay 到访方式
	 * @param type 1个访    2群访
	 * 个访群体访事项列表
	 */
		public Map<String, Object> getListByType(Integer startPage,Integer pageCount,String fuzzy,Integer unitId,Integer belongTo,Integer type,Integer petitionWay,String startTime,String endTime);
	/**
	 *  @param startPage 起始页数
	 * @param pageCount 每页条数
	 * @param fuzzy 模糊搜索
	 * @param unitId 单位ID
	 * @param belongTo 问题归属地
	 * @param petitionWay 到访方式
	 * @param PetitionType 1正常访    2非正常访
	 * 正常非正常访事项列表
	 */
		 public Map<String, Object> getListByPetitionType(Integer startPage,Integer pageCount,String fuzzy,Integer unitId,Integer belongTo,Integer petitionWay,Integer PetitionType,String startTime,String endTime);
	
	/**
	 * @param startPage 起始页数
	 * @param pageCount 每页条数
	 * @param fuzzy 模糊搜索
	 * @param unitId 单位ID
	 * @param belongTo 问题归属地
	 * @param questionHot 热点问题
	 * @param petitionWay 到访方式
	 * 热点问题列表
	 */
		 public Map<String, Object> getListByQuestionHot(Integer startPage,Integer pageCount,final String fuzzy,final Integer unitId,final Integer belongTo,final Integer questionType ,final Integer petitionWay,String startTime,String endTime);
	/**
	 * 热点问题列表接口
	 */
		 public Map<String, Object> getQuestionHotList();
		 
}
