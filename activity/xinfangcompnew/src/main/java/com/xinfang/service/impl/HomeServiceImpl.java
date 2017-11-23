package com.xinfang.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.VO.HomeListVO;
import com.xinfang.context.PageFinder;
import com.xinfang.dao.ComplainHomeDao;
import com.xinfang.log.ApiLog;
import com.xinfang.service.HomeService;
import com.xinfang.utils.DateUtils;

/**
 * Created by sunbjx on 2017/3/28.
 */
@Service("homeService")
public class HomeServiceImpl implements HomeService {

	@Autowired
	private ComplainHomeDao complainHomeDao;

	@Override
	public PageFinder<HomeListVO> packages(int flag, Integer userId, int caseState, int handleState, int timeProgress,
			String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept, int startPage, int pageSize) {

		List<HomeListVO> homeListVOS = new ArrayList<HomeListVO>();
		PageFinder<HomeListVO> page = null;
		try {
			List<Map<String, Object>> list = complainHomeDao.packages(flag, userId, caseState, handleState,
					timeProgress, fuzzy, petitionType, caseBelongTo, dept, startPage, pageSize);
			// List<Map<String, Object>> listCount =
			// complainHomeDao.packages(flag, userId, caseState, handleState,
			// timeProgress, fuzzy, petitionType, caseBelongTo, dept, -1, -1);

			for (int i = 0; i < list.size(); i++) {
				HomeListVO homeListVO = new HomeListVO();
				homeListVO.setTaskId(
						StringUtils.isEmpty(list.get(i).get("task_id")) ? "" : list.get(i).get("task_id").toString());
				homeListVO.setPetitionCode(StringUtils.isEmpty(list.get(i).get("petition_code")) ? ""
						: list.get(i).get("petition_code").toString());
				homeListVO.setPetitionName(
						StringUtils.isEmpty(list.get(i).get("username")) ? "" : list.get(i).get("username").toString()); // 信访人员
				homeListVO.setPetitionHeadSrc(
						StringUtils.isEmpty(list.get(i).get("head_pic")) ? "" : list.get(i).get("head_pic").toString());
				homeListVO.setPetitionType(StringUtils.isEmpty(list.get(i).get("petition_way")) ? ""
						: list.get(i).get("petition_way").toString());
				homeListVO.setCaseBelongToAddress(StringUtils.isEmpty(list.get(i).get("question_belong_to")) ? ""
						: list.get(i).get("question_belong_to").toString());
				homeListVO.setCaseDesc(StringUtils.isEmpty(list.get(i).get("case_desc")) ? ""
						: list.get(i).get("case_desc").toString());
				homeListVO.setCurrentHandleUnit(StringUtils.isEmpty(list.get(i).get("current_unit")) ? ""
						: list.get(i).get("current_unit").toString());
				homeListVO.setPetitionTime(StringUtils.isEmpty(list.get(i).get("petition_time")) ? null
						: DateUtils.parseDate(list.get(i).get("petition_time").toString()));
				homeListVO.setHandlePeriodEnd(StringUtils.isEmpty(list.get(i).get("handle_period_end")) ? ""
						: list.get(i).get("handle_period_end").toString());
				homeListVO.setIsSatisfied(StringUtils.isEmpty(list.get(i).get("is_degree"))
						|| list.get(i).get("is_degree").toString().equals(0) ? "否" : "是");
				homeListVO.setCurrentHandleName(StringUtils.isEmpty(list.get(i).get("current_username")) ? ""
						: list.get(i).get("current_username").toString());
				homeListVO.setHandleUserHeadSrc(StringUtils.isEmpty(list.get(i).get("current_userpic")) ? ""
						: list.get(i).get("current_userpic").toString());
				homeListVO.setCurrentState(StringUtils.isEmpty(list.get(i).get("current_state")) ? ""
						: list.get(i).get("current_state").toString());

				homeListVO.setGuestId(StringUtils.isEmpty(list.get(i).get("guest_id")) ? 0
						: Integer.valueOf(list.get(i).get("guest_id").toString()));
				homeListVO.setCaseId(StringUtils.isEmpty(list.get(i).get("case_id")) ? 0
						: Integer.valueOf(list.get(i).get("case_id").toString()));
				homeListVO.setGmtCreate(StringUtils.isEmpty(list.get(i).get("gmt_create")) ? ""
						: list.get(i).get("gmt_create").toString());
				homeListVO.setTurnAuditState(StringUtils.isEmpty(list.get(i).get("turn_audit_state")) ? ""
						: list.get(i).get("turn_audit_state").toString());
				// 通过案件ID 查询流程变量的流程实例ID
				// List<HistoricVariableInstance> hiVarInst =
				// historyService.createHistoricVariableInstanceQuery()
				// .variableValueEquals("caseid",
				// StringUtils.isEmpty(list.get(i).get("case_id")) ? 0 :
				// Integer.valueOf(list.get(i).get("case_id").toString()))
				// .list();

				// homeListVO.setFlowId(hiVarInst.isEmpty() ? null :
				// hiVarInst.get(0).getProcessInstanceId());
				homeListVO.setFlowId(StringUtils.isEmpty(list.get(i).get("proc_inst_id")) ? ""
						: list.get(i).get("proc_inst_id").toString());

				homeListVOS.add(homeListVO);

				page = new PageFinder<HomeListVO>(startPage, pageSize, homeListVOS.size(), homeListVOS);
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
		return page;

	}

	@Override
	public Map<String, Object> dispatches(Integer userId, int caseState, int handleState, int timeProgress,

			String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept, int startPage, int pageSize) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = complainHomeDao.dispatcher(userId, caseState, handleState, timeProgress, fuzzy, petitionType,
					caseBelongTo, dept, startPage, pageSize);
			if (result != null && result.size() > 0)
				return result;

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return result;

	}

	@Override
	public List<Map<String, Object>> caseStateQuery(Integer userId, int caseState, int handleState, int timeProgress,
			String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept) {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		try {
			maps = complainHomeDao.caseStateQuery(userId, caseState, handleState, timeProgress, fuzzy, petitionType,
					caseBelongTo, dept);
			if (maps != null && maps.size() > 0)
				return maps;

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return maps;
	}

}
