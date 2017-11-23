package com.xinfang.service;

import java.util.List;

import com.xinfang.model.FdCase;

/**
 * Created by sunbjx on 2017/3/23.
 */
public interface CaseService {

	/**
	 * 案件信息保存 必须带入案件ID
	 * 
	 * @param fdCase
	 * @param strCaseTime
	 * @return
	 */
	int save(FdCase fdCase, String strCaseTime);

	/**
	 * 根据ID获取案件信息
	 * 
	 * @param caseId
	 * @return
	 */
	FdCase getFdCaseById(Integer caseId);

	FdCase getCaseById(Integer caseId);

	/**
	 * 根据案件编号获取
	 * 
	 * @param petitionCode
	 * @return
	 */
	FdCase getCaseByPetitionCode(String petitionCode);

	/**
	 * 通过案件ID更新状态
	 * 
	 * @param caseId
	 * @return
	 */
	int updateCaseHandleStateById(Integer userId, Integer caseHandleState, Integer caseId);

	/**
	 * 通过案件ID 更新时长
	 * 
	 * @param caseId
	 * @param handleDuration
	 * @return
	 */
	int updateCaseHandleDurationById(Integer userId, Integer caseId, Integer handleDuration);

	/**
	 * 更新窗口编号
	 * 
	 * @param windowNumber
	 * @return
	 */
	int updateWindowNumber(Integer userId, Integer caseId, Integer windowNumber, String turnCaseDesc);

	/**
	 * 通过案件ID修改信访人数
	 * 
	 * @param caseId
	 * @param petitionNumbers
	 * @return
	 */
	int updatePetitionNumbersByCaseId(Integer caseId, Integer petitionNumbers);

	/**
	 * 通过案件ID收文刚派送app案件
	 * @param caseId	案件ID
	 * @param handleUserId 处理人ID
	 * @param dispatcherToUserid 派送人ID
	 * @return
	 */
	void dispatcherAppCase(Integer caseId, Integer handleUserId);
	
	/**
	 * 通过案件ID退回到收文岗
	 * @param caseId
	 */
	int returnToTurn(Integer caseId, Integer doUserId);
	
	/**
	 * 通过创建者ID查询窗口退回分流室待审核案件
	 * @param createrId
	 * @return
	 */
	List<FdCase> listTurnAudit(Integer creatorId);
	
	/**
	 * 通过案件ID分流室进行创酷案件退回待审核
	 * @param caseId
	 * @return
	 */
	boolean turnAudit(Integer caseId, int isOk, Integer doUserId);
	
	

}
