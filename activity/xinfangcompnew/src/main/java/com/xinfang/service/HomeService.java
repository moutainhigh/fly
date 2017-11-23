package com.xinfang.service;

import java.util.List;
import java.util.Map;

import com.xinfang.VO.HomeListVO;
import com.xinfang.context.PageFinder;

/**
 * Created by sunbjx on 2017/3/28.
 */
public interface HomeService {

	PageFinder<HomeListVO> packages(int flag, Integer userId, int caseState, int handleState, int timeProgress,
			String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept, int startPage, int pageSize);

	/**
	 * 收文岗
	 * @param userId
	 * @param caseState
	 * @param handleState
	 * @param timeProgress
	 * @param fuzzy
	 * @param petitionType
	 * @param caseBelongTo
	 * @param dept
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> dispatches(Integer userId, int caseState, int handleState, int timeProgress,
			String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept, int startPage, int pageSize);
	
	/**
	 * 案件状态
	 * @param userId
	 * @param caseState
	 * @param handleState
	 * @param timeProgress
	 * @param fuzzy
	 * @param petitionType
	 * @param caseBelongTo
	 * @param dept
	 * @return
	 */
	List<Map<String, Object>> caseStateQuery(Integer userId, int caseState, int handleState, int timeProgress,
				String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept);
	
}
