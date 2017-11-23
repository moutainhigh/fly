package com.xinfang.service;




import java.util.ArrayList;
import java.util.Date;

import com.xinfang.model.FdCaseFeedbackAll;


/**
 * 
 * @author zhangbo
 *
 */

public interface FdCaseFeedbackAllService {
	/**
	 * 案件反馈详情
	 */
	ArrayList<FdCaseFeedbackAll> findCaseFeedbackAllById(Integer caseId);
	
	/**
	 * 
	 * @param caseId 案件id
	 * @description 根据案件获取最新处理时间
	 * @author ZhangHuaRong
	 * @update 2017年4月7日 上午11:11:57
	 */
	public Date getlastUpdateTime(Integer caseId);
}
