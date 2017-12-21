package com.xinfang.service;

import java.util.Map;

import com.xinfang.context.PageFinder;
import com.xinfang.model.DisputeCaseEntity;

/**
 * Created by sunbjx on 2017/5/17.
 */
public interface DisputeCaseService {

	/**
	 * 案件信息保存 必须带入案件ID
	 *
	 * @param fdCase
	 * @param strCaseTime
	 * @return
	 */
	int save(DisputeCaseEntity fdCase, String strCaseTime);

	/**
	 * 根据ID获取案件信息
	 *
	 * @param disputeCaseId
	 * @return
	 */
	DisputeCaseEntity getById(Integer disputeCaseId);

	/**
	 * 根据案件编号获取
	 *
	 * @param petitionCode
	 * @return
	 */
	DisputeCaseEntity getByPetitionCode(String petitionCode);

	/**
	 * 通过案件ID更新状态
	 *
	 * @param disputeCaseId
	 * @return
	 */
	int updateCaseHandleStateById(Integer userId, Integer caseHandleState, Integer disputeCaseId);

	/**
	 * 通过案件ID修改信访人数
	 * 
	 * @param disputeCaseId
	 * @param petitionNumbers
	 * @return
	 */
	int updatePetitionNumbersById(Integer disputeCaseId, Integer petitionNumbers);

	/**
	 * 案件处理反馈表
	 */
	int handleDisputeCase(Integer disputeCaseId, int type, String note, Integer userId, String replyFileSrc,
			String otherFileSrc, int flagPetitionUnit);

	/**
	 * 矛盾纠纷预警查询
	 * 
	 * @param handleType
	 * @param handState
	 * @param time_progress
	 * @param startTime
	 * @param endTime
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	PageFinder<Map<String, Object>> warningQuery(byte handleType, int handState, Integer countyCityId,
			String fuzzy, String time_progress, String startTime, String endTime, int startPage, int pageSize);

	/**
	 * 矛盾纠纷统计查询
	 * 
	 * @param authId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Map<String, Object> statisticalQuery(Integer authId, String startTime, String endTime);

}
