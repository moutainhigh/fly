package com.xinfang.foursectionsdata.service;

import java.util.Map;

public interface LedgeService {
	
	/**
	 * 
	 * @param populationType
	 * @param orgLevel
	 * @param fuzzy
	 * @param petitionWay
	 * @param belongToId
	 * @param departmentId
	 * @param startTime
	 * @param endTime
	 * @param startPage
	 * @param pageCount
	 * @return
	 */
  Map<String, Object> getLedgeList(Integer populationType,Integer orgLevel,String fuzzy,Integer petitionWay,Integer belongToId,Integer departmentId,String startTime,String endTime,Integer startPage,Integer pageCount);
  /**
  * 
  * @param caseId
  * @return
  */
  Map<String, Object> getLedgeImportant(Integer caseId);
  Map<String, Object> getCaseDetailById(Integer caseId);

}
