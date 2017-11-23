package com.xinfang.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinfang.VO.LogInInfo;
import com.xinfang.context.PageFinder;
import com.xinfang.context.StateConstants;
import com.xinfang.dao.DisputeCaseDao;
import com.xinfang.dao.FdCaseFeedbackAllRepository;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.FdCodeRepository;
import com.xinfang.enums.HandleState;
import com.xinfang.log.ApiLog;
import com.xinfang.model.DisputeCaseEntity;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdCaseFeedbackAll;
import com.xinfang.service.DisputeCaseService;
import com.xinfang.service.TzmPetitionService;
import com.xinfang.utils.DateUtils;

/**
 * Created by sunbjx on 2017/5/17.
 */
@Service
@Transactional
public class DisputeCaseServiceImpl implements DisputeCaseService {
	@Autowired
	private DisputeCaseDao disputeCaseDao;
	@Autowired
	private FdCodeRepository fdCodeRepository;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private TzmPetitionService tzmPetitionService;
	@Autowired
	private FdCaseFeedbackAllRepository fdCaseFeedbackAllRepository;// 案件反馈表（日志表）
	@Autowired
	private FdCaseRepository caseRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int save(DisputeCaseEntity disputeCase, String strCaseTime) {
		try {
			DisputeCaseEntity result = disputeCaseDao.findOne(disputeCase.getDisputeCaseId());

			result.setCaseTime(StringUtils.isEmpty(strCaseTime) ? null : DateUtils.parseDateTime(strCaseTime));
			result.setPetitionCounty(disputeCase.getPetitionCounty());
			result.setPetitionUnit(disputeCase.getPetitionUnit());
			result.setIsRepeatPetition(disputeCase.getIsRepeatPetition());
			result.setCaseAddress(disputeCase.getCaseAddress());
			result.setCaseDesc(disputeCase.getCaseDesc());
			result.setCaseDemand(disputeCase.getCaseDemand());
			result.setFileSrc(disputeCase.getFileSrc());
			result.setHandlePeriodStart(disputeCase.getHandlePeriodStart());
			result.setHandlePeriodEnd(disputeCase.getHandlePeriodEnd());
			result.setQuestionBelongingTo(disputeCase.getQuestionBelongingTo());
			result.setQuestionType(disputeCase.getQuestionType());
			result.setCaseType(disputeCase.getCaseType());
			result.setQuestionHot(disputeCase.getQuestionHot());
			result.setBelongToSys(disputeCase.getBelongToSys());
			result.setThirteenCategories(disputeCase.getThirteenCategories());
			result.setPetitionWhy(disputeCase.getPetitionWhy());
			result.setPetitionPurpose(disputeCase.getPetitionPurpose());
			result.setIsLeaderLimited(disputeCase.getIsLeaderLimited());
			result.setExposeObject(disputeCase.getExposeObject());
			result.setPetitionNature(disputeCase.getPetitionNature());
			result.setTrusteName(disputeCase.getTrusteName());
			result.setIsAbove(disputeCase.getIsAbove());
			result.setIsBacklog(disputeCase.getIsBacklog());
			result.setIsReview(disputeCase.getIsReview());
			result.setIsSatisfied(disputeCase.getIsSatisfied());
			result.setIsProminent(disputeCase.getIsProminent());
			result.setIsAcrossFrom(disputeCase.getIsAcrossFrom());
			result.setIsLeaderPackageCase(disputeCase.getIsLeaderPackageCase());
			result.setIsTypeRight(disputeCase.getIsTypeRight());
			result.setIsScaleRight(disputeCase.getIsScaleRight());
			result.setIsTierRight(disputeCase.getIsTierRight());
			result.setIsLeaderApprove(disputeCase.getIsLeaderApprove());
			result.setApproveId(disputeCase.getApproveId());
			result.setCaseHandleState(StateConstants.HANDLE_TODO); // 待处理
			result.setAttachCaseDesc(disputeCase.getAttachCaseDesc());
			result.setIsSuitWade(disputeCase.getIsSuitWade());
			result.setIsSuitTaiwan(disputeCase.getIsSuitTaiwan());
			result.setIsSuitAomen(disputeCase.getIsSuitAomen());
			result.setIsSuitHongkong(disputeCase.getIsSuitHongkong());
			result.setIsSuitAbroad(disputeCase.getIsSuitAbroad());
			result.setIsSuitOuter(disputeCase.getIsSuitOuter());
			result.setIsSuitThreaten(disputeCase.getIsSuitThreaten());
			result.setState(StateConstants.STATE_UPDATE); // 更新
			result.setHandleUserid(disputeCase.getHandleUserid());
			result.setIsHandle((byte) 1);

			disputeCaseDao.save(result);
			addFeedBack(result.getCreatorId().longValue(), result.getDisputeCaseId().intValue(),
					HandleState.UPDATE_CASE.getValue(), "更新案件信息", result.getFileSrc(), "");
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return 0;
		}
		return 1;
	}

	@Override
	public DisputeCaseEntity getById(Integer disputeCaseId) {
		DisputeCaseEntity disputeCase = null;
		try {

			disputeCase = disputeCaseDao.findOne(disputeCaseId);
			Integer questionBlong = disputeCase.getQuestionBelongingTo();
			Integer questionType = disputeCase.getQuestionType();
			Integer questionHot = disputeCase.getQuestionHot();
			Integer caseType = disputeCase.getCaseType();
			Integer blongToSys = disputeCase.getBelongToSys();
			Integer thirteenCategories = disputeCase.getThirteenCategories();
			Integer petitionWhy = disputeCase.getPetitionWhy();
			Integer petitionPurpose = disputeCase.getPetitionPurpose();
			Integer petitionNature = disputeCase.getPetitionNature();
			Integer exposeObject = disputeCase.getExposeObject();
			Integer petitionCounty = disputeCase.getPetitionCounty();
			Integer petitionWay = disputeCase.getPetitionWay();

			disputeCase
					.setStrQuestionBelongTo(questionBlong != null ? fdCodeRepository.getNameByCode(questionBlong) : "");
			disputeCase.setStrBelongToSys(
					blongToSys != null ? fdCodeRepository.getNameByCode(disputeCase.getBelongToSys()) : "");
			disputeCase
					.setStrCaseType(caseType != null ? fdCodeRepository.getNameByCode(disputeCase.getCaseType()) : "");
			disputeCase.setStrExposeObject(
					exposeObject != null ? fdCodeRepository.getNameByCode(disputeCase.getExposeObject()) : "");
			disputeCase.setStrPetitionCountry(
					petitionCounty != null ? fdCodeRepository.getNameByCode(disputeCase.getPetitionCounty()) : "");
			disputeCase.setStrPetitionNature(
					petitionNature != null ? fdCodeRepository.getNameByCode(disputeCase.getPetitionNature()) : "");
			disputeCase.setStrPetitionPurpose(
					petitionPurpose != null ? fdCodeRepository.getNameByCode(disputeCase.getPetitionPurpose()) : "");
			disputeCase.setStrPetitionWay(
					petitionWay != null ? fdCodeRepository.getNameByCode(disputeCase.getPetitionWay()) : "");
			disputeCase.setStrPetitionWhy(
					petitionWhy != null ? fdCodeRepository.getNameByCode(disputeCase.getPetitionWhy()) : "");
			disputeCase.setStrQuestionHot(
					questionHot != null ? fdCodeRepository.getNameByCode(disputeCase.getQuestionHot()) : "");
			disputeCase.setStrQusetionType(
					questionType != null ? fdCodeRepository.getNameByCode(disputeCase.getQuestionType()) : "");
			disputeCase.setStrThirteenCategories(thirteenCategories != null
					? fdCodeRepository.getNameByCode(disputeCase.getThirteenCategories()) : "");

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return disputeCase;
		}

		return disputeCase;
	}

	@Override
	public DisputeCaseEntity getByPetitionCode(String petitionCode) {

		DisputeCaseEntity disputeCase = null;
		try {
			disputeCase = disputeCaseDao.getByPetitionCode(petitionCode);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return disputeCase;
		}
		return disputeCase;
	}

	@Override
	public int updateCaseHandleStateById(Integer userId, Integer caseHandleState, Integer disputeCaseId) {
		try {

			disputeCaseDao.updateCaseHandleStateById(caseHandleState, disputeCaseId);
			if (userId != null) {
				addFeedBack(userId.longValue(), disputeCaseId.intValue(), HandleState.UPDATE_CASE.getValue(),
						"更新案件处理状态", "", "");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return 0;
		}
		return 1;
	}

	@Override
	public int updatePetitionNumbersById(Integer disputeCaseId, Integer petitionNumbers) {
		try {
			DisputeCaseEntity disputeCase = disputeCaseDao.findOne(disputeCaseId);
			disputeCase.setPetitionNumbers(petitionNumbers);
			disputeCaseDao.save(disputeCase);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return 0;
		}
		return 1;
	}

	@Override
	public int handleDisputeCase(Integer disputeCaseId, int type, String note, Integer userId, String replyFileSrc,
			String otherFileSrc, int flagPetitionUnit) {
		try {
			DisputeCaseEntity disputeCase = disputeCaseDao.findOne(disputeCaseId);
			// 矛盾纠纷案件 DML 插入反馈数据
			LogInInfo info = addFeedBack(userId, disputeCaseId, type, note, replyFileSrc, otherFileSrc);

			// 允许的处理类型值
			Integer[] typeList = new Integer[] { HandleState.REPLY.getValue(), HandleState.SAVE.getValue(),
					HandleState.TURN_PETITION.getValue(), HandleState.TURN_DISHED.getValue() };
			boolean isContainsType = false;
			for (int i = 0; i < typeList.length; i++) {
				if (type == typeList[i]) {
					isContainsType = true;
					break;
				}
			}
			// 处理类型不存在
			if (!isContainsType)
				return -1;

			// 更新矛盾纠纷案件相关数据
			disputeCase.setCurrentHandleUnitid(info.getDwId()); // 当前案件处理单位ID
			disputeCase.setCurrentHandlePersonalid(info.getRyId()); // 更新当前案件处理人ID
			disputeCase.setHandleType(type); // 更新当前案件处理类型
			disputeCase.setCurrentHandleState("2003"); // 更新当前案件处理状态
			disputeCase.setHandleFactEndTime(new Date()); // 案件实际结束时间
			disputeCase.setIsHandle((byte) 2); // 已完结

			// 转交到区信访局
			if (flagPetitionUnit == 1) {
				transferToPetitionUnit(disputeCaseId, info.getDwId());
				disputeCase.setTransferToPetitionUnit((byte) 1);
			}
			// 转交到市信访局
			if (flagPetitionUnit == 2) {
				transferToPetitionUnit(disputeCaseId, Integer.valueOf(761));
				disputeCase.setTransferToPetitionUnit((byte) 2);
			}
			// 转到调度指挥
			if (flagPetitionUnit == 3) {
				// transferToPetitionUnit(disputeCaseId, flagPetitionUnit);
			}

			disputeCaseDao.save(disputeCase);

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return 0;
		}
		return 1;
	}

	@Override
	public PageFinder<Map<String, Object>> warningQuery(byte handleType, int handState, Integer countyCityId,
			String fuzzy, String time_progress, String startTime, String endTime, int startPage, int pageSize) {

		String sql = "SELECT \n" + "    gmt_create,\n" + "    petition_code,\n" + "    (SELECT \n"
				+ "            g.username\n" + "        FROM\n" + "            fd_guest g\n" + "        WHERE\n"
				+ "            g.ID = guest_id) AS petition_name,\n" + "    (SELECT \n" + "            g.head_pic\n"
				+ "        FROM\n" + "            fd_guest g\n" + "        WHERE\n"
				+ "            g.ID = guest_id) AS petition_headsrc,\n" + "    (SELECT \n" + "            c.name\n"
				+ "        FROM\n" + "            fd_code c\n" + "        WHERE\n"
				+ "            c.code = petition_way) AS petition_way,\n" + "    case_desc,\n" + "    CASE\n"
				+ "        WHEN IFNULL(transfer_to_petition_unit, 0) = 1 THEN '转到区信访'\n"
				+ "        WHEN IFNULL(transfer_to_petition_unit, 0) = 2 THEN '转到市信访'\n"
				+ "   	   WHEN IFNULL(handle_type, 0) = 15 THEN '转到指挥调度'\n"
				+ "        WHEN IFNULL(handle_type, 0) = 4 THEN '直接回复'\n"
				+ "		   WHEN IFNULL(handle_type, 0) = 7 THEN '存件'\n" + "        ELSE ''\n"
				+ "    END AS handle_way,\n" + "    (SELECT \n" + "            r.RY_MC\n" + "        FROM\n"
				+ "            fc_ryb r\n" + "        WHERE\n" + "            r.RY_ID = handle_userid) AS entry_name,\n"
				+ "    (SELECT \n" + "            r.RY_IMG\n" + "        FROM\n" + "            fc_ryb r\n"
				+ "        WHERE\n" + "            r.RY_ID = handle_userid) AS entry_headsrc,\n"
				+ "  (SELECT DW_MC FROM fc_dwb WHERE DW_ID = create_unitid) AS create_unit,\n"
				+ "    IF(case_handle_state = 2003,\n" + "        '已转为信访件',\n" + "        '未处理') AS current_state,\n"
				+ "    dispute_case_id,\n" + "    if(ifnull(is_handle, 0) <> 2, 'true', 'false') as modified_tag\n"
				+ "FROM\n" + "    dispute_case\n" + "WHERE 1 = 1\n";

		String transferUnit = " AND IFNULL(transfer_to_petition_unit, 0) = " + handleType;
		String other = " AND IFNULL(handle_type, 0) = ";

		// 1 转到区信访局 2 转到市信访局 3 转到调度指挥 4 直接回复 5 存件
		sql += (handleType == 1 || handleType == 2) ? transferUnit : "";
		sql += (handleType == 3) ? other + 15 : "";
		sql += (handleType == 4) ? other + 4 : "";
		sql += (handleType == 5) ? other + 7 : "";

		if (handState != 0) {
			sql += " AND IFNULL(is_handle, 0) =  " + handState;
		}
		// TODO auth 市信访局能看所有单位，转交单位只能看转交单位，接收单位只能看接收单位
		if (countyCityId != 0) {
			// sql += " AND (IFNULL(create_unitid, 0) = " + unitId;
			// sql += " OR IFNULL(transfer_unitid, 0) = " + unitId;
			// sql += " OR IFNULL(receive_unitid, 0) = " + unitId + ")";

			sql += " AND IFNULL(petition_county, 0) = " + countyCityId;
		}
		if (!StringUtils.isEmpty(fuzzy)) {
			sql += " and (guest_id in (select id from fd_guest where  username like '%" + fuzzy
					+ "%') or petition_code like '%" + fuzzy + "%') ";
		}
		if (!StringUtils.isEmpty(time_progress)) {
			sql += " AND NOW() >= DATE_ADD(gmt_create, INTERVAL FLOOR((SELECT handle_days FROM fd_case c WHERE c.dispute_case_id = dispute_case_id) * "
					+ time_progress + ") DAY) ";
		}
		if (!StringUtils.isEmpty(startTime)) {
			sql += " AND DATE_FORMAT(gmt_create, '%Y-%m-%d') > '" + startTime + "'";
		}
		if (!StringUtils.isEmpty(endTime)) {
			sql += " AND DATE_FORMAT(gmt_create, '%Y-%m-%d') < '" + endTime + "'";
		}
		if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
			sql += " AND DATE_FORMAT(gmt_create, '%Y-%m-%d') BETWEEN '" + startTime + "' AND '" + endTime + "'";
		}
		String noPageSQL = sql;
		sql += "  LIMIT " + (startPage - 1) * pageSize + " , " + startPage * pageSize;
		String pageSQL = sql;

		try {
			List<Map<String, Object>> mapList = jdbcTemplate.queryForList(pageSQL);
			List<Map<String, Object>> recordCount = jdbcTemplate.queryForList(noPageSQL);
			if (mapList != null && mapList.size() > 0) {
				return new PageFinder<Map<String, Object>>(startPage, pageSize, recordCount.size(), mapList);
			}
		} catch (DataAccessException e) {
			ApiLog.chargeLog1(e.getMessage());
			return null;
		}
		return null;
	}

	@Override
	public Map<String, Object> statisticalQuery(Integer authId, String startTime, String endTime) {

		return null;
	}

	/**
	 * 矛盾纠纷案件 DML 插入反馈数据
	 * 
	 * @param userid
	 * @param caseid
	 * @param type
	 * @param note
	 * @return
	 */
	private LogInInfo addFeedBack(long userid, int caseid, int type, String note, String files1, String files2) {
		Date now = new Date();
		LogInInfo info = null;
		Object obj = redisTemplate.opsForValue().get("user:" + userid);
		if (obj != null) {
			info = (LogInInfo) obj;
		} else {
			info = tzmPetitionService.selectLogInInfoByRyId((int) userid);
		}
		FdCaseFeedbackAll fdCaseFeedback = new FdCaseFeedbackAll();
		fdCaseFeedback.setType(type);
		fdCaseFeedback.setUserurl(info.getRyImg());
		fdCaseFeedback.setCaseId(caseid);
		fdCaseFeedback.setCreaterId((int) userid);
		fdCaseFeedback.setCreateTime(now);
		fdCaseFeedback.setUpdateTime(now);
		fdCaseFeedback.setNote(note);
		fdCaseFeedback.setEnclosure1(files1);
		fdCaseFeedback.setEnclosure2(files2);
		fdCaseFeedback.setCreaterCompany(info.getKsMc());
		fdCaseFeedback.setCreaterRole(info.getZwMc());
		fdCaseFeedback.setCreaterDep(info.getDwMc());
		fdCaseFeedback.setDepId(info.getDwId());
		fdCaseFeedback.setCreater(info.getUserName());

		fdCaseFeedbackAllRepository.saveAndFlush(fdCaseFeedback);
		return info;
	}

	/**
	 * 矛盾纠纷案件转交信访案件关联
	 * 
	 * @param disputeCaseId
	 * @param flagPetitionUnit
	 */
	private void transferToPetitionUnit(Integer disputeCaseId, Integer unitId) {
		FdCase fdCase = new FdCase();
		fdCase.setDisputeCaseId(disputeCaseId);
		fdCase.setPetitionCounty(unitId);
		fdCase.setPetitionUnit("信访局");
		caseRepository.save(fdCase);
	}

	/**
	 * 矛盾纠纷案件转交到指挥调度
	 * 
	 * @param disputeCaseId
	 */
	private void transferToDished(Integer disputeCaseId) {

	}

	interface handleTypeSQL {
		String subSQL(String sql, byte handleType);
	}
}
