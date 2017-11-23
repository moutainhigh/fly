package com.xinfang.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xinfang.VO.LogInInfo;
import com.xinfang.VO.TaskVO;
import com.xinfang.context.PageFinder;
import com.xinfang.dao.CaseQueryDao;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdGuest;
import com.xinfang.service.CaseQueryService;
import com.xinfang.service.IdentityService;
import com.xinfang.service.TzmPetitionService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository("caseQueryService")
public class CaseQueryServiceImpl implements CaseQueryService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private TaskService taskservice;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private TzmPetitionService tzmPetitionService;

	@Autowired
	private CaseQueryDao caseQueryDao;

	@Override
	public PageFinder<Map<String, Object>> caseQueryByCondition(String str, Integer startPage, Integer pageSize) {
		String excuteSql = "SELECT *, (select ry_mc  from fc_ryb where ry_id=handlePersonId) handlerPersonName,"
				+ "(select RY_SFZ  from fc_ryb where ry_id=handlePersonId) sfz"
				+ " FROM ( SELECT c.ID petitionId, c.petition_names petitionNames, "
				+ "c.petition_numbers petitionNumbers, "
				+ "DATE_FORMAT(c.petition_time,'%Y-%m-%d %H:%i:%s') pPetitionTime,"
				+ "(SELECT DATE(c.petition_time)) pPetitionTimeDay," + " c.petition_county petitionCounty, "
				+ "(SELECT f.`name` FROM fd_code f WHERE f.code =c.petition_county) petitionCountyName, "
				+ "c.petition_unit petitionUnit, c.petition_way petitionWay, "
				+ "(SELECT f.`name` FROM fd_code f WHERE f.code = c.petition_way) petitionWayName, "
				+ "c.petition_code petitionCode, c.is_repeat_petition isRepeatPetition, "
				+ "(CASE c.is_repeat_petition WHEN 1 THEN '是' else '否' end) isRepeatPetitionName , "
				+ "DATE_FORMAT(c.case_time,'%Y-%m-%d %H:%i:%s') caseTime, (select date(c.case_time)) caseTimeDay, "
				+ "c.case_address caseAddress, c.case_desc caseDesc, c.case_demand caseDemand, "
				+ "c.file_src pFileSrc, c.handle_duration handleDuration, "
				+ "DATE_FORMAT(c.handle_period_start,'%Y-%m-%d') pHandlePeriodStart, "
				+ "DATE_FORMAT(c.handle_period_end,'%Y-%m-%d') pHandlePeriodEnd, "
				+ "c.question_belonging_to questionBelongingTo, "
				+ "(SELECT f.`name` FROM fd_code f WHERE f.code =  c.question_belonging_to) questionBelongingToName, "
				+ "c.question_type questionType, "
				+ "(SELECT f.`name` FROM fd_code f WHERE f.code = c.question_type) questionTypeName, "
				+ "c.case_type caseType, "
				+ "(SELECT f.`name` FROM fd_code f WHERE f.code = c.case_type) caseTypeName, "
				+ "c.question_hot questionHot, "
				+ "(CASE c.question_hot WHEN 1 THEN '是' else '否' end) questionHotName, "
				+ "c.belong_to_sys belongToSys, "
				+ "(SELECT f.`name` FROM fd_code f WHERE f.code = c.belong_to_sys) belongToSysName, "
				+ "c.thirteen_categories thirteenCategories, "
				+ "(SELECT f.`name` FROM fd_code f WHERE f.code = c.thirteen_categories) thirteenCategoriesName, "
				+ "c.petition_why petitionWhy, "
				+ "(SELECT f.`name` FROM fd_code f WHERE f.code =  c.petition_why) petitionWhyName, "
				+ "c.petition_purpose petitionPurpose, "
				+ "(SELECT f.`name` FROM fd_code f WHERE f.code =  c.petition_purpose) petitionPurposeName, "
				+ "c.is_leader_limited isLeaderLimited, "
				+ "(CASE c.is_leader_limited WHEN 1 THEN '是' else '否' end) isLeaderLimitedName, "
				+ "c.expose_object exposeObject, " + "(SELECT f.`name` FROM fd_code f WHERE f.code = c.expose_object) "
				+ "exposeObjectName, c.petition_nature petitionNature, "
				+ "(SELECT f.`name` FROM fd_code f WHERE f.code =c.petition_nature) petitionNatureName, "
				+ "c.truste_name trusteName, c.is_above isAbove, "
				+ "(CASE c.is_above WHEN 1 THEN '是' ELSE '否' END) isAboveName, " + "c.is_backlog isBackLog, "
				+ "(CASE c.is_backlog WHEN 1 THEN '是' ELSE '否' END) isBacklogName, " + "c.is_review isReview, "
				+ "(CASE c.is_review WHEN 1 THEN '是' ELSE '否' END) isReviewName, "
				+ "c.is_satisfied isSatisfied, (case c.is_satisfied when 1 then '是' else '否' end) "
				+ "isStatisfiedName, c.is_prominent isProminent, "
				+ "(CASE c.is_prominent WHEN 1 THEN '是' ELSE '否' END) isProminentName, "
				+ "c.is_across_from isAcrossFrom, "
				+ "(CASE c.is_across_from WHEN 1 THEN '是' ELSE '否' END) isAcrossFromName, "
				+ "c.is_leader_package_case isLeaderPackageCase, "
				+ "(CASE c.is_leader_package_case WHEN 1 THEN '是' ELSE '否' END) isLeaderPackageCaseName, "
				+ "c.is_leader_approve isLeaderApprove, "
				+ "(CASE c.is_leader_approve WHEN 1 THEN '是' ELSE '否' END) isLeaderApproveName, "
				+ "c.is_type_right isTypeRight, "
				+ "(CASE c.is_type_right WHEN 1 THEN '是' ELSE '否' END) isTypeRightName, "
				+ "c.is_scale_right isScaleRight, "
				+ "(CASE c.is_scale_right WHEN 1 THEN '是' ELSE '否' END) isScaleRightName, "
				+ "c.is_tier_right isTierRight, "
				+ "(CASE c.is_tier_right WHEN 1 THEN '是' ELSE '否' END) isTierRightName, "
				+ "c.gmt_create pGmtCreate, (SELECT DATE(c.gmt_create)) pGmtCreateDay, "
				+ "c.gmt_modified pGmtModified, (SELECT DATE(c.gmt_modified)) pGmtModifiedDay, "
				+ "c.creator_id creatorId, " + "(SELECT r.ry_mc FROM fc_ryb r WHERE r.ry_id=c.creator_id) creatorName, "
				+ "c.case_handle_state caseHandleState, c.type pType, "
				+ "(CASE c.type WHEN 1 THEN '个访' WHEN 2 THEN '群访' ELSE '未知' END) pTypeName, "
				+ "c.window_number windowNumber,"
				+ "(SELECT window_name FROM ts_window w WHERE w.id=c.window_number) windowNumberName, "
				+ "c.create_time createTime, " + "(SELECT DATE(c.create_time)) pCreateTime, "
				+ "c.attach_case_desc attachCaseDesc, " + "c.guest_id guestId, "
				+ "(SELECT username FROM fd_guest d WHERE d.id =c.guest_id) guestName, "
				+ "c.attach_case_ids attachCaseIds, c.form form, "
				+ "(CASE c.form WHEN 1 then '窗口' WHEN 2 THEN '分流室' ELSE '未知' END) formName, "
				+ "c.state state, c.title title, c.is_suit_wade isSuitWade, "
				+ "(CASE c.is_suit_wade WHEN 1 THEN '是' ELSE '否' END)  isSuitWadeName, "
				+ "c.is_suit_taiwan isSuitTaiwan, "
				+ "(CASE c.is_suit_taiwan WHEN 1 THEN '是' ELSE '否' END) isSuitTaiwanName, "
				+ "c.is_suit_aomen isSuitAomen, "
				+ "(CASE c.is_suit_aomen  WHEN 1 THEN '是' ELSE '否' END) isSuitAomenName, "
				+ "c.is_suit_hongkong isSuitBongkong, "
				+ "(CASE c.is_suit_hongkong  WHEN 1 THEN '是' ELSE '否' END) isSuitHongKongName, "
				+ "c.is_suit_abroad isSuitAbroad, "
				+ "(CASE c.is_suit_abroad  WHEN 1 THEN '是' ELSE '否' END) isSuitAbroadName, "
				+ "c.is_suit_outer isSuitOuter, "
				+ "(CASE c.is_suit_outer  WHEN 1 THEN '是' ELSE '否' END) isSuitOuterName, "
				+ "c.is_suit_threaten isSuitThreaten, "
				+ "(CASE c.is_suit_threaten  WHEN 1 THEN '是' ELSE '否' END) isThreatenName, "
				+ "c.follow_guest_ids followGuestIds, c.create_window createWindow, " + "c.approve_id approveId, "
				+ "(CASE c.approve_id WHEN 1 THEN '是' ELSE '否' END) approveIdName, " + "c.handle_userid handleUserId, "
				+ "(SELECT r.ry_mc FROM fc_ryb r WHERE r.ry_id = c.handle_userid) handleUserName, "
				+ "c.associated_numbers associatedNumbers, "
				+ "c.handle_flow_type handleFlowType, c.is_handle isHandle, "
				+ "(CASE  c.is_handle WHEN 0 THEN '未录入' WHEN 1 THEN '已经录入' WHEN 2 THEN '已经进行案件处理' ELSE '未知' END ) isHandleName, "
				+ "c.is_degree isDegree, " + "(CASE c.is_degree  WHEN 1 THEN '是' ELSE '否' END) isDegreeName, "
				+ "c.is_timely_accept isTimelyAccept, "
				+ "(CASE c.is_timely_accept  WHEN 1 THEN '是' ELSE '否' END) isTimelyAcceptName, "
				+ "c.is_ontime_finish isOntimeFinish, "
				+ "(CASE c.is_ontime_finish  WHEN 1 THEN '是' ELSE '否' END) isOntimeFinishName, "
				+ "g.petition_way_parent petitionWayParent, g.petition_way_child petitionWayChild, "
				+ "DATE_FORMAT(g.petition_time,'%Y-%m-%d %h:%i:%s') gPetitionTime, g.card_type cardType, "
				+ "g.username userName, g.sex sex, "
				+ "g.ethnic ethnic, g.birthday birthday, g.nationality nationality, "
				+ "g.census_register censusRegister, g.idcard idcard, g.head_pic headPic, "
				+ "g.phone phone, g.contact_address contactAddress, g.final_tel finalTel, "
				+ "g.now_address nowAddress, g.zip_code zipCode, g.is_focus isFocus, "
				+ "g.employed_Info employedInfo, g.ca_province caProvince, "
				+ "g.ca_city caCity, g.ca_county caCounty, g.na_province naProvince, "
				+ "g.na_city naCity, g.na_county naCounty, g.file_src gFileSrc, "
				+ "g.type gType, g.state gState, g.gmt_create gGmtCreate, "
				+ "date_format(g.gmt_modified,'%Y-%m-%d %h:%i:%s') gGmtModified, g.is_anonymity isAnonymity, "
				+ "g.creator_id gCreatorId, "
				+ "(SELECT f.ry_mc FROM fc_ryb f WHERE f.ry_id=g.creator_id) gCreaterName, "
				+ "g.associated_numbers gAssociatedNumbers, g.petition_code gPetitionCode, "
				+ "g.petition_numbers gPetitionNumbers, " + "v.proc_inst_id_ , b.assignee_ assigneeId, "
				+ "( CASE WHEN v.proc_inst_id_ IS NULL THEN '空' ELSE CASE WHEN b.assignee_ IS NULL THEN b.name_ ELSE ( SELECT f.ry_mc FROM fc_ryb f WHERE f.ry_id = b.assignee_ ) END END ) assigneeName,"
				+ "(CASE  WHEN v.PROC_INST_ID_ IS NOT NULL AND b.assignee_ IS NOT NULL THEN b.assignee_ ELSE CASE WHEN c.handle_userid IS NOT NULL THEN c.handle_userid ELSE c.creator_id END END) handlePersonId,"
				+ "b.id_ taskId FROM fd_case c " + "LEFT JOIN fd_guest g ON c.guest_id = g.id "
				+ "LEFT JOIN act_hi_varinst v ON c.id = v.long_ AND v.name_ = 'caseid' "
				+ "LEFT JOIN (SELECT htk.* FROM act_hi_taskinst htk, "
				+ "(SELECT PROC_INST_ID_ maxTimeId, max(START_TIME_) maxTime "
				+ "FROM act_hi_taskinst GROUP BY PROC_INST_ID_ ) maxCount "
				+ "WHERE htk.PROC_INST_ID_ = maxCount.maxTimeId "
				+ "AND htk.START_TIME_ = maxCount.maxTime) b ON b.proc_inst_id_ = v.proc_inst_id_ ) a " + "WHERE 1 = 1";
		if (str != null) {
			JSONArray jsonArrayList = JSONArray.fromObject(str);
			for (int i = 0; i < jsonArrayList.size(); i++) {
				JSONArray jArray = jsonArrayList.getJSONArray(i);
				String temp = null;
				temp = this.queryConditionUnion(jArray);
				if (temp != null && !temp.equals("")) {
					excuteSql += temp;
				}

			}

			// JSONObject jsonObject = JSONObject.fromObject(str);
			// Iterator<String> it = jsonObject.keys();
			// while (it.hasNext()) {
			// String key = it.next();
			// JSONArray jsonArray = jsonObject.getJSONArray(key);
			// String temp = null;
			// if (key.equals("petitionCode")) {
			// temp = this.queryConditionUnion(jsonArray, "petitionCode");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// } else if (key.equals("petitionNumbers")) {
			// temp = this.queryConditionUnion(jsonArray, "petitionNumbers");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// // caseType
			// } else if (key.equals("petitionWay")) {
			// temp = this.queryConditionUnion(jsonArray, "petitionWay");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("caseType")) {
			// temp = this.queryConditionUnion(jsonArray, "caseType");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("questionType")) {
			// temp = this.queryConditionUnion(jsonArray, "questionType");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// } else if (key.equals("questionBelongingTo")) {
			// temp = this.queryConditionUnion(jsonArray,
			// "questionBelongingTo");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// } else if (key.equals("belongToSys")) {
			// temp = this.queryConditionUnion(jsonArray, "belongToSys");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// } else if (key.equals("questionHot")) {
			// temp = this.queryConditionUnion(jsonArray, "questionHot");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// } else if (key.equals("associatedNumbers")) {
			// temp = this.queryConditionUnion(jsonArray, "associatedNumbers");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("petitionWhy")) {
			// temp = this.queryConditionUnion(jsonArray, "petitionWhy");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// } else if (key.equals("petitionPurpose")) {
			// temp = this.queryConditionUnion(jsonArray, "petitionPurpose");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// } else if (key.equals("thirteenCategories")) {
			// temp = this.queryConditionUnion(jsonArray, "thirteenCategories");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("creator")) {
			// temp = this.queryConditionUnion(jsonArray, "creatorName");
			// if (temp != null) {
			// excuteSql += temp;
			//
			// }
			// }
			//
			// else if (key.equals("handleUser")) {
			// temp = this.queryConditionUnion(jsonArray, "handleUserName");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("gmtCreate")) {
			// temp = this.queryConditionUnion(jsonArray, "pGmtCreateDay");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// } else if (key.equals("petitionTime")) {
			// temp = this.queryConditionUnion(jsonArray, "pPetitionTimeDay");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("username")) {
			// temp = this.queryConditionUnion(jsonArray, "userName");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("idcard")) {
			// temp = this.queryConditionUnion(jsonArray, "idcard");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("sex")) {
			// temp = this.queryConditionUnion(jsonArray, "sex");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("phone")) {
			// temp = this.queryConditionUnion(jsonArray, "phone");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("isAnonymity")) {
			// temp = this.queryConditionUnion(jsonArray, "isAnonymity");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			// // ethnic
			// else if (key.equals("ethnic")) {
			// temp = this.queryConditionUnion(jsonArray, "ethnic");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("contactAddress")) {
			// temp = this.queryConditionUnion(jsonArray, "contactAddress");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			//
			// else if (key.equals("nowAddress")) {
			// temp = this.queryConditionUnion(jsonArray, "nowAddress");
			// if (temp != null) {
			// excuteSql += temp;
			// }
			// }
			// }
		}

		System.out.println(excuteSql + " order by pGmtCreate desc");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(excuteSql);
		PageFinder<Map<String, Object>> page = new PageFinder<Map<String, Object>>(startPage, pageSize, list.size(),
				list);
		return page;

	}

	// [{"value":"","type":"","cType":"or"},{"value":"","type":"","cType":"and"}]
	public String queryConditionUnion(JSONArray jsonArray) {
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < jsonArray.size(); i++) {
			String tempStr = null;
			JSONObject json = jsonArray.getJSONObject(i);
			String value = !json.get("type").equals("like") ? "'" + json.get("value") + "'"
					: "'%" + json.get("value") + "%'";

			if (json.containsKey("value") && json.get("value") != null) {
				if (json.get("key").equals("handleUserName")) {

					tempStr = "(EXISTS (SELECT '' FROM act_hi_taskinst h, fc_ryb ry "
							+ "WHERE h.proc_inst_id_ = a.proc_inst_id_ AND ry.ry_id = h.ASSIGNEE_  " + "AND ry.ry_mc "
							+ json.get("type") + " " + value + ") OR " + "a.handleUserName " + json.get("type") + " "
							+ value + ")";

				} else if (json.get("key").toString().endsWith("Day")) {
					tempStr = json.get("key") + " " + json.get("type") + "STR_TO_DATE(" + value + ",'%Y-%m-%d')";

				} else {
					tempStr = json.get("key") + " " + json.get("type") + " " + value + " ";
				}
			}

			if (tempStr != null) {
				if (temp.toString().equals("")) {
					temp.append(" " + json.get("cType") + "(" + tempStr);
				} else {
					temp.append(" " + json.get("cType") + " " + tempStr);
				}

			}

		}
		temp.append(")");

		return temp.toString();
	}

	public String queryConditionUnion(JSONArray jsonArray, String fieldName) {
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			String tempStr = null;
			if (fieldName.equals("pGmtCreateDay") || fieldName.equals("pPetitionTime")) {
				if (json.containsKey("begin") && json.get("begin") != null) {
					tempStr = fieldName + json.get("beginType") + " " + "str_to_date('" + json.get("begin") + "'" + ","
							+ "'%Y-%m-%d')";
				}
				if (json.containsKey("end") && json.get("end") != null) {
					if (tempStr != null) {
						tempStr += " AND " + fieldName + json.get("endType") + " " + "str_to_date('" + json.get("end")
								+ "'" + "," + "'%Y-%m-%d')";
					} else {
						tempStr = fieldName + json.get("endType") + " " + "str_to_date('" + json.get("end") + "'" + ","
								+ "'%Y-%m-%d')";
					}
				}
				if (tempStr != null) {
					if (temp.toString().equals("")) {
						temp.append(" " + json.get("cType") + "(" + "(" + tempStr + ")");
					} else {
						temp.append(" " + json.get("cType") + " " + " (" + tempStr + ")");
					}

				}
			} else {
				String value = !json.get("type").equals("like") ? "'" + json.get("value") + "'"
						: "'%" + json.get("value") + "%'";
				if (fieldName.equals("handleUserName")) {
					if (json.containsKey("value") && json.get("value") != null) {

						tempStr = "(EXISTS (SELECT '' FROM act_hi_taskinst h, fc_ryb ry "
								+ "WHERE h.proc_inst_id_ = a.proc_inst_id_ AND ry.ry_id = h.ASSIGNEE_  "
								+ "AND ry.ry_mc " + json.get("type") + " " + value + ") or " + "a.handleUserName "
								+ json.get("type") + " " + value + ")";
					}

				} else {
					if (json.containsKey("value") && json.get("value") != null) {
						tempStr = fieldName + json.get("type") + " " + value;
					}
				}
				if (tempStr != null) {
					if (temp.toString().equals("")) {
						temp.append(" " + json.get("cType") + "(" + tempStr);
					} else {
						temp.append(" " + json.get("cType") + " " + tempStr);
					}
				}

			}

		}

		temp.append(")");
		return temp.toString();
	}

	/***
	 * type: 交办1， 转办2，审核3，流程外4，办结5
	 */
	@Override
	public PageFinder<TaskVO> queryDbCase(Integer type, String vagueConditon, Integer petitionWay,
			Integer questionBelongingTo, Integer userId, Integer startPage, Integer pageSize) {
		List<TaskVO> list = new ArrayList<TaskVO>();
		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().unfinished()
				.taskInvolvedUser(userId.toString()).list();

		for (HistoricTaskInstance task : historicTaskInstances) {
			Map<String, Object> variables = taskservice.getVariables(task.getId());
			FdCase fdCase = (FdCase) variables.get("fdCase");
			if (1 == type && 1 != Integer.parseInt((String) variables.get("type"))) {
				continue;
			} else if (2 == type && 2 != Integer.parseInt((String) variables.get("type"))) {
				continue;
			} else if (3 == type) {

			}
			FdGuest fdGuest = identityService.getGuestById(fdCase.getGuestId());
			fdGuest.setFdCaseList(null);
			LogInInfo info = null;
			Object userinfo = redisTemplate.opsForValue().get("user:" + task.getAssignee());
			if (userinfo != null) {
				info = (LogInInfo) userinfo;
			} else {
				if (task.getAssignee() != null) {
					info = tzmPetitionService.selectLogInInfoByRyId(Integer.parseInt(task.getAssignee()));
				}
			}
			TaskVO taskVO = new TaskVO();
			taskVO.setFdCase(fdCase);
			taskVO.setFormdate(variables.get("formate"));
			taskVO.setGuest(fdGuest);
			taskVO.setUserinfo(info);
			taskVO.setTaskId(task.getId());
			taskVO.setProcDefId(task.getProcessDefinitionId());
			taskVO.setProInsId(task.getProcessInstanceId());
			taskVO.setTaskDefinitionKey(task.getTaskDefinitionKey());
			taskVO.setTaskName(task.getName());
			list.add(taskVO);
		}

		PageFinder<TaskVO> pageFinder = new PageFinder<TaskVO>(startPage, pageSize, list.size(), list);

		return pageFinder;
	}

	@Override
	public PageFinder<Map<String, Object>> caseQueryByCustomCondition(List<Map<String, Object>> conditions,
			int caseTimeTag, Integer userId, int startPage, int pageSize) {
		// 权限设置
		// 市信访去可以查看所有单位的案件 区信访局可以查看所属区单位的案件 非信访局只能查看本单位的案件
		LogInInfo logInInfo = null;
		try {
			logInInfo = tzmPetitionService.selectLogInInfoByRyId(userId);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			logInInfo = null;
		}

		if (logInInfo != null) {
			// 区信访局可以查看所属区单位的案件
			if (!logInInfo.getDwMc().equals("贵阳市信访局") && logInInfo.getDwMc().indexOf("区信访局") != -1) {
				Map<String, Object> authMap = new HashMap<String, Object>();
				authMap.put("relation", " and ");
				authMap.put("singleColumn", " c.petition_county ");
				authMap.put("singleWhere", " = ");
				authMap.put("singleColumnValue", logInInfo.getQxsId());

				conditions.add(authMap);
			}
			// 非信访局只能查看本单位的案件
			if (!logInInfo.getDwMc().equals("贵阳市信访局") && logInInfo.getDwMc().indexOf("区信访局") == -1) {
				Map<String, Object> authMap = new HashMap<String, Object>();
				authMap.put("relation", " and ");
				authMap.put("singleColumn", " c.create_unitid ");
				authMap.put("singleWhere", " = ");
				authMap.put("singleColumnValue", logInInfo.getDwId());

				conditions.add(authMap);
			}
		}

		List<Map<String, Object>> result = caseQueryDao.customConditionQuery(conditions, caseTimeTag);

		// 分页查询结果集
		Map<String, Object> limitMap = new HashMap<String, Object>();
		limitMap.put("relation", " limit ");
		limitMap.put("singleColumn", startPage);
		limitMap.put("singleWhere", ",");
		limitMap.put("singleColumnValue", pageSize);
		conditions.add(limitMap);

		List<Map<String, Object>> limitResult = caseQueryDao.customConditionQuery(conditions, caseTimeTag);

		return new PageFinder<Map<String, Object>>(startPage, pageSize, result.size(), limitResult);
	}

}
