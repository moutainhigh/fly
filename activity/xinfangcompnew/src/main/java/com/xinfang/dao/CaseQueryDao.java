package com.xinfang.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author sunbjx
 * Date: 2017年6月5日上午10:26:42
 */
public interface CaseQueryDao {
	
	/**
	 * 自定义条件查询
	 * @param conditions
	 * @return
	 */
	List<Map<String, Object>> customConditionQuery(List<Map<String, Object>> conditions, int caseTimeTag);
}
