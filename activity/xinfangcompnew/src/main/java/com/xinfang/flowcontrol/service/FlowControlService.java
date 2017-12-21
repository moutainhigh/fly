package com.xinfang.flowcontrol.service;

import java.util.Map;

public interface FlowControlService {

	/**
	 * 流程控制统计
	 * @param signUserId
	 * @param startTime
	 * @param endTime
	 * @param sponsorUnitId
	 * @param handleUnitId
	 * @return
	 */
	Map<String, Object> flowControlCount(Integer signUserId ,String startTime,String endTime,Integer sponsorUnitId,Integer handleUnitId );
	/**
	 * 案件详情列表（已办结，办理中）
	 * @param signUserId
	 * @param fuzzy
	 * @param startTime
	 * @param endTime
	 * @param state
	 * @param petitionWay
	 * @param type
	 * @param startPage
	 * @param pageCount
	 * @return
	 */
	Map<String, Object> caseDeatilList(Integer signUserId,String fuzzy,String startTime,String endTime,Integer state,Integer petitionWay,Integer type,Integer startPage,Integer pageCount);
	/**
	 * 异常列表统计
	 * @param type
	 * @param startPage
	 * @param pageCount
	 * @return
	 */
	Map<String, Object> abnormalList(Integer signUserId,Integer type,Integer startPage,Integer pageCount);
	/**
	 * 获取单位树形图
	 * @param signUserId
	 * @return
	 */
	Map<String, Object> getDeptListByUserId(Integer signUserId);

}
