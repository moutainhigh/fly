package com.xinfang.service;

import java.util.List;
import java.util.Map;

import com.xinfang.VO.TaskVO;
import com.xinfang.context.PageFinder;

public interface CaseQueryService {
	public PageFinder<Map<String, Object>> caseQueryByCondition(String str, Integer startPage, Integer pageSize);

	public PageFinder<TaskVO> queryDbCase(Integer type, String vagueConditon, Integer petitionWay,
			Integer questionBelongingTo, Integer userId, Integer startPage, Integer pageSize);

	/**
	 * 通过自定义查询条件进行查询
	 * 
	 * @param conditions
	 * @return
	 */
	PageFinder<Map<String, Object>> caseQueryByCustomCondition(List<Map<String, Object>> conditions, int caseTimeTag,
			Integer userId, int startPage, int pageSize);
}
