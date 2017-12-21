package com.xinfang.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xinfang.VO.FdCaseShowVO;
import com.xinfang.VO.LogInInfo;
import com.xinfang.context.StateConstants;
import com.xinfang.dao.FdCaseFeedbackAllRepository;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.enums.HandleLimitState;
import com.xinfang.enums.HandleState;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdCaseFeedbackAll;
import com.xinfang.model.FdDepCase;
import com.xinfang.service.CaseService;
import com.xinfang.service.TzmPetitionService;
import com.xinfang.utils.DateUtils;
import com.xinfang.utils.RuleUtil;

/**
 * Created by sunbjx on 2017/3/23.
 */
@Repository("caseService")
@Transactional
public class CaseServiceImpl implements CaseService {

	@Autowired
	private FdCaseRepository fdCaseRepository;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	TzmPetitionService tzmPetitionService;
	@Autowired
	FdCaseFeedbackAllRepository fdCaseFeedbackAllRepository;// 案件反馈表（日志表）

	@Autowired
	FdDepCaseRepository fdDepCaseRepository;

	@Override
	public int save(FdCase fdCase, String strCaseTime) {
		try {
			FdCase result = fdCaseRepository.findOne(fdCase.getId());
			if (result == null) {
				throw new RuntimeException();
			}

			result.setCaseTime(StringUtils.isEmpty(strCaseTime) ? null : DateUtils.parseDateTime(strCaseTime));
			result.setPetitionCounty(fdCase.getPetitionCounty());
			result.setPetitionUnit(fdCase.getPetitionUnit());
			result.setIsRepeatPetition(fdCase.getIsRepeatPetition());
			result.setCaseAddress(fdCase.getCaseAddress());
			result.setCaseDesc(fdCase.getCaseDesc());
			result.setCaseDemand(fdCase.getCaseDemand());
			result.setFileSrc(fdCase.getFileSrc());
			// result.setHandleDuration(fdCase.getHandleDuration());
			result.setHandlePeriodStart(fdCase.getHandlePeriodStart());
			result.setHandlePeriodEnd(fdCase.getHandlePeriodEnd());
			result.setQuestionBelongingTo(fdCase.getQuestionBelongingTo());
			result.setQuestionType(fdCase.getQuestionType());
			result.setCaseType(fdCase.getCaseType());
			result.setQuestionHot(fdCase.getQuestionHot());
			result.setBelongToSys(fdCase.getBelongToSys());
			result.setThirteenCategories(fdCase.getThirteenCategories());
			result.setPetitionWhy(fdCase.getPetitionWhy());
			result.setPetitionPurpose(fdCase.getPetitionPurpose());
			result.setIsLeaderLimited(fdCase.getIsLeaderLimited());
			result.setExposeObject(fdCase.getExposeObject());
			result.setPetitionNature(fdCase.getPetitionNature());
			result.setTrusteName(fdCase.getTrusteName());
			result.setIsAbove(fdCase.getIsAbove());
			result.setIsBacklog(fdCase.getIsBacklog());
			result.setIsReview(fdCase.getIsReview());
			result.setIsSatisfied(fdCase.getIsSatisfied());
			result.setIsProminent(fdCase.getIsProminent());
			result.setIsAcrossFrom(fdCase.getIsAcrossFrom());
			result.setIsLeaderPackageCase(fdCase.getIsLeaderPackageCase());
			result.setIsTypeRight(fdCase.getIsTypeRight());
			result.setIsScaleRight(fdCase.getIsScaleRight());
			result.setIsTierRight(fdCase.getIsTierRight());
			// result.setGmtCreate(new Date());
			result.setIsLeaderApprove(fdCase.getIsLeaderApprove());
			result.setApproveId(fdCase.getApproveId());
			result.setCaseHandleState(StateConstants.HANDLE_TODO); // 待处理
			result.setAttachCaseDesc(fdCase.getAttachCaseDesc());
			result.setIsSuitWade(fdCase.getIsSuitWade());
			result.setIsSuitTaiwan(fdCase.getIsSuitTaiwan());
			result.setIsSuitAomen(fdCase.getIsSuitAomen());
			result.setIsSuitHongkong(fdCase.getIsSuitHongkong());
			result.setIsSuitAbroad(fdCase.getIsSuitAbroad());
			result.setIsSuitOuter(fdCase.getIsSuitOuter());
			result.setIsSuitThreaten(fdCase.getIsSuitThreaten());
			result.setState(StateConstants.STATE_UPDATE); // 更新
			result.setHandleUserid(fdCase.getHandleUserid());

			result.setIsHandle((byte) 1);
			if ("web".equals(fdCase.getComefrom())) {// 判断是不是来自网上
				result.setIsHandle((byte) 0);
			}

			fdCaseRepository.save(result);
			// LogInInfo info = addFeedBack(result.getCreatorId().longValue(),
			// result.getId().intValue(), StateConstants.STATE_UPDATE, "更新案件信息",
			// "");
			if ("web".equals(fdCase.getComefrom())) {

			} else {
				List<FdCaseFeedbackAll> rList = fdCaseFeedbackAllRepository.getByCaseIdAndType(result.getId(),
						Integer.valueOf(HandleState.UPDATE_CASE.getValue()));

				LogInInfo info = tzmPetitionService.selectLogInInfoByRyId(result.getHandleUserid());
				if (rList == null || rList.size() == 0) {
					info = addFeedBack(result.getHandleUserid().longValue(), result.getId().intValue(),
							HandleState.UPDATE_CASE.getValue(), "更新案件信息", result.getFileSrc());
				}

				int count = fdDepCaseRepository.countByCaseIdAndDepId(result.getId().intValue(), info.getDwId());
				if (count < 1) {
					Integer total = fdCase.getHandleDuration();
					if (total == null || total.intValue() == 0) {
						total = 90;
					}
					Date now = new Date();
					FdDepCase depcase = new FdDepCase();
					depcase.setCaseId(result.getId().longValue());
					depcase.setCreaterId(result.getCreatorId().longValue());
					depcase.setCreateTime(now);
					depcase.setStartTime(now);
					depcase.setDepId(info.getDwId());
					depcase.setEndTime(DateUtils.add(now, total * RuleUtil.getRule().getRegister() * 0.01f));
					depcase.setState(1);
					depcase.setNeedReceipt(1);
					depcase.setLimitTime(total * RuleUtil.getRule().getRegister() * 0.01f);
					depcase.setType(HandleLimitState.register.getValue());
					depcase.setNote(HandleLimitState.register.getName());
					// fdDepCaseRepository.save(depcase);
				}
			}

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return 0;
		}
		return 1;
	}

	@Override
	public FdCase getFdCaseById(Integer caseId) {
		FdCase fdCase = null;
		FdCaseShowVO fdCaseShow = null;
		try {

			fdCase = fdCaseRepository.findById(caseId);
			Integer questionBlong = fdCase.getQuestionBelongingTo();
			Integer questionType = fdCase.getQuestionType();
			Integer questionHot = fdCase.getQuestionHot();
			Integer caseType = fdCase.getCaseType();
			Integer blongToSys = fdCase.getBelongToSys();
			Integer thirteenCategories = fdCase.getThirteenCategories();
			Integer petitionWhy = fdCase.getPetitionWhy();
			Integer petitionPurpose = fdCase.getPetitionPurpose();
			Integer petitionNature = fdCase.getPetitionNature();
			Integer exposeObject = fdCase.getExposeObject();
			Integer petitionCounty = fdCase.getPetitionCounty();
			Integer petitionWay = fdCase.getPetitionWay();

			fdCase.setStrQuestionBelongTo(
					questionBlong != null ? fdCaseRepository.findNameByCode(questionBlong).getName() : null);
			fdCase.setStrBelongToSys(
					blongToSys != null ? fdCaseRepository.findNameByCode(fdCase.getBelongToSys()).getName() : null);
			fdCase.setStrCaseType(
					caseType != null ? fdCaseRepository.findNameByCode(fdCase.getCaseType()).getName() : null);
			fdCase.setStrExposeObject(
					exposeObject != null ? fdCaseRepository.findNameByCode(fdCase.getExposeObject()).getName() : null);
			fdCase.setStrPetitionCountry(petitionCounty != null
					? fdCaseRepository.findNameByCode(fdCase.getPetitionCounty()).getName() : null);
			fdCase.setStrPetitionNature(petitionNature != null
					? fdCaseRepository.findNameByCode(fdCase.getPetitionNature()).getName() : null);
			fdCase.setStrPetitionPurpose(petitionPurpose != null
					? fdCaseRepository.findNameByCode(fdCase.getPetitionPurpose()).getName() : null);
			fdCase.setStrPetitionWay(
					petitionWay != null ? fdCaseRepository.findNameByCode(fdCase.getPetitionWay()).getName() : null);
			fdCase.setStrPetitionWhy(
					petitionWhy != null ? fdCaseRepository.findNameByCode(fdCase.getPetitionWhy()).getName() : null);
			fdCase.setStrQuestionHot(
					questionHot != null ? fdCaseRepository.findNameByCode(fdCase.getQuestionHot()).getName() : null);
			fdCase.setStrQusetionType(
					questionType != null ? fdCaseRepository.findNameByCode(fdCase.getQuestionType()).getName() : null);
			fdCase.setStrThirteenCategories(thirteenCategories != null
					? fdCaseRepository.findNameByCode(fdCase.getThirteenCategories()).getName() : null);

			/*
			 * if (questionBlong != null) {
			 * fdCase.setStrQuestionBelongTo(fdCaseRepository.findNameByCode(
			 * questionBlong).getName()); } if (caseType != null) {
			 * fdCase.setStrCaseType(fdCaseRepository.findNameByCode(caseType)
			 * .getName()); } if (questionHot != null) {
			 * fdCase.setStrQuestionHot(fdCaseRepository.findNameByCode(
			 * questionHot).getName()); } if (questionType != null) {
			 * fdCase.setStrQusetionType(fdCaseRepository.findNameByCode(
			 * questionType).getName()); } if (blongToSys != null) {
			 * fdCase.setStrBelongToSys(fdCaseRepository.findNameByCode(
			 * blongToSys).getName()); } if (thirteenCategories != null) {
			 * fdCase.setStrThirteenCategories(fdCaseRepository
			 * .findNameByCode(thirteenCategories).getName()); } if (petitionWhy
			 * != null) {
			 * fdCase.setStrPetitionWhy(fdCaseRepository.findNameByCode(
			 * petitionWhy).getName()); } if (petitionPurpose != null) {
			 * fdCase.setStrPetitionPurpose(fdCaseRepository.findNameByCode(
			 * petitionPurpose).getName()); } if (petitionNature != null) {
			 * fdCase.setStrPetitionNature(fdCaseRepository.findNameByCode(
			 * petitionNature).getName()); } if (exposeObject != null) {
			 * fdCase.setStrExposeObject(fdCaseRepository.findNameByCode(
			 * exposeObject).getName()); } if (petitionCounty != null) {
			 * fdCase.setStrPetitionCountry(fdCaseRepository.findNameByCode(
			 * petitionCounty).getName()); } if (petitionWay != null) {
			 * fdCase.setStrPetitionWay(fdCaseRepository.findNameByCode(
			 * petitionWay).getName()); } fdCaseShow = new
			 * FdCaseShowVO(fdCase.getId(), fdCase.getPetitionNames(),
			 * fdCase.getPetitionNumbers(), fdCase.getAssociatedNumbers(),
			 * fdCase.getPetitionTime(), fdCase.getStrPetitionCountry(),
			 * fdCase.getPetitionUnit(), fdCase.getStrPetitionWay(),
			 * fdCase.getIsRepeatPetition(), fdCase.getCaseTime(),
			 * fdCase.getCaseAddress(), fdCase.getCaseDesc(),
			 * fdCase.getCaseDemand(), fdCase.getFileSrc(),
			 * fdCase.getHandleDuration(), fdCase.getHandlePeriodStart(),
			 * fdCase.getHandlePeriodEnd(), fdCase.getStrQuestionBelongTo(),
			 * fdCase.getStrQusetionType(), fdCase.getStrCaseType(),
			 * fdCase.getStrQuestionHot(), fdCase.getStrBelongToSys(),
			 * fdCase.getStrThirteenCategories(), fdCase.getStrPetitionWhy(),
			 * fdCase.getStrPetitionPurpose(), fdCase.getIsLeaderLimited(),
			 * fdCase.getStrExposeObject(), fdCase.getStrPetitionNature(),
			 * fdCase.getTrusteName(), fdCase.getIsAbove(),
			 * fdCase.getIsBacklog(), fdCase.getIsReview(),
			 * fdCase.getIsSatisfied(), fdCase.getIsProminent(),
			 * fdCase.getIsAcrossFrom(), fdCase.getIsLeaderPackageCase(),
			 * fdCase.getIsLeaderApprove(), fdCase.getApproveId(),
			 * fdCase.getIsTypeRight(), fdCase.getIsScaleRight(),
			 * fdCase.getIsTierRight(), fdCase.getGmtCreate(),
			 * fdCase.getGmtModified(), fdCase.getType(), fdCase.getCreatorId(),
			 * fdCase.getCaseHandleState(), fdCase.getWindowNumber(),
			 * fdCase.getCreateTime(), fdCase.getGuestId(),
			 * fdCase.getFollowGuestIds(), fdCase.getAttachCaseIds(),
			 * fdCase.getAttachCaseDesc(), fdCase.getForm(), fdCase.getState(),
			 * fdCase.getTitle(), fdCase.getPetitionCode(),
			 * fdCase.getIsSuitWade(), fdCase.getIsSuitTaiwan(),
			 * fdCase.getIsSuitAomen(), fdCase.getIsSuitHongkong(),
			 * fdCase.getIsSuitAbroad(), fdCase.getIsSuitOuter(),
			 * fdCase.getIsSuitThreaten(), fdCase.getHandleUserid());
			 */
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return fdCase;
		}

		return fdCase;
	}

	@Override
	public FdCase getCaseById(Integer caseId) {
		FdCase fdCase = null;
		try {
			fdCase = fdCaseRepository.findOne(caseId);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return fdCase;
		}
		return fdCase;
	}

	@Override
	public FdCase getCaseByPetitionCode(String petitionCode) {

		FdCase fdCase = null;
		try {
			fdCase = fdCaseRepository.getCaseByPetitionCode(petitionCode);

		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return fdCase;
		}
		return fdCase;
	}

	@Override
	public int updateCaseHandleStateById(Integer userId, Integer caseHandleState, Integer caseId) {
		try {

			fdCaseRepository.updateCaseHandleStateById(caseHandleState, caseId);
			// if (userId != null) {
			// addFeedBack(userId.longValue(), caseId.intValue(),
			// HandleState.UPDATE_CASE.getValue(), "更新案件处理状态", "");
			// }
			List<FdCaseFeedbackAll> rList = fdCaseFeedbackAllRepository.getByCaseIdAndType(caseId,
					Integer.valueOf(HandleState.UPDATE_CASE.getValue()));

			if (rList == null || rList.size() == 0) {
				addFeedBack(userId.longValue(), caseId.intValue(), HandleState.UPDATE_CASE.getValue(), "更新案件信息", "");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateCaseHandleDurationById(Integer userId, Integer caseId, Integer handleDuration) {
		FdCase fdCase = fdCaseRepository.findOne(caseId);
		fdCase.setHandleDuration(handleDuration);

		try {
			fdCaseRepository.save(fdCase);
			// if (userId != null) {
			// addFeedBack(userId.longValue(), caseId.intValue(),
			// HandleState.UPDATE_CASE.getValue(), "更新案件处理时长", "");
			// }
			List<FdCaseFeedbackAll> rList = fdCaseFeedbackAllRepository.getByCaseIdAndType(caseId,
					Integer.valueOf(HandleState.UPDATE_CASE.getValue()));

			if (rList == null || rList.size() == 0) {
				addFeedBack(userId.longValue(), caseId.intValue(), HandleState.UPDATE_CASE.getValue(), "更新案件信息", "");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateWindowNumber(Integer userId, Integer caseId, Integer windowNumber, String turnCaseDesc) {
		try {
			FdCase fdCase = fdCaseRepository.findOne(caseId);
			fdCase.setWindowNumber(windowNumber);
			fdCase.setTurnCaseDesc(turnCaseDesc);
			fdCase.setTurnAuditState(null);
			fdCaseRepository.save(fdCase);
			// if (userId != null) {
			// addFeedBack(userId.longValue(), caseId.intValue(),
			// HandleState.UPDATE_CASE.getValue(), "更新窗口编号", "");
			// }
			List<FdCaseFeedbackAll> rList = fdCaseFeedbackAllRepository.getByCaseIdAndType(caseId,
					Integer.valueOf(HandleState.UPDATE_CASE.getValue()));

			if (rList == null || rList.size() == 0) {
				addFeedBack(userId.longValue(), caseId.intValue(), HandleState.UPDATE_CASE.getValue(), "更新案件信息", "");
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return 0;
		}
		return 1;
	}

	@Override
	public int updatePetitionNumbersByCaseId(Integer caseId, Integer petitionNumbers) {
		try {
			FdCase fdCase = fdCaseRepository.findOne(caseId);
			fdCase.setPetitionNumbers(petitionNumbers);
			fdCaseRepository.saveAndFlush(fdCase);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return 0;
		}
		return 1;
	}

	@Override
	public void dispatcherAppCase(Integer caseId, Integer handleUserId) {
		try {
			FdCase fdCase = fdCaseRepository.findOne(caseId);
			fdCase.setHandleUserid(handleUserId);
			fdCase.setIsDispatcherReceive((byte) 1);
			// fdCase.setDispatcherToUserid(dispatcherToUserid);
			fdCase.setIsHandle((byte) 0);
			fdCase.setCreatorId(handleUserId);
			fdCaseRepository.save(fdCase);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
	}

	/**
	 * @param userid
	 * @param caseid
	 * @param type
	 * @param note
	 * @param files
	 * @return
	 */
	private LogInInfo addFeedBack(long userid, int caseid, int type, String note, String files) {
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
		fdCaseFeedback.setEnclosure3(files);
		fdCaseFeedback.setCreaterCompany(info.getKsMc());
		fdCaseFeedback.setCreaterRole(info.getZwMc());
		fdCaseFeedback.setCreaterDep(info.getDwMc());
		fdCaseFeedback.setDepId(info.getDwId());
		fdCaseFeedback.setCreater(info.getUserName());

		fdCaseFeedbackAllRepository.saveAndFlush(fdCaseFeedback);
		return info;
	}

	@Override
	public int returnToTurn(Integer caseId, Integer doUserId) {
		try {
			FdCase fdCase = fdCaseRepository.findOne(caseId);
			if (fdCase.getIsHandle() != 2) {
				// fdCase.setForm(null);
				// fdCase.setHandleUserid(null);
				// fdCase.setWindowNumber(null);
				fdCase.setTurnAuditState((byte) 0);
				fdCaseRepository.save(fdCase);
				addFeedBack(doUserId.longValue(), caseId.intValue(), HandleState.TURN_RETUEN.getValue(),
						HandleState.TURN_RETUEN.getName(), "");
				return 1;
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return 0;
	}

	@Override
	public List<FdCase> listTurnAudit(Integer creatorId) {
		try {
			List<FdCase> cases = fdCaseRepository.getCaseByCreatorId(creatorId);
			if (cases != null && cases.size() > 0) {
				return cases;
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}

		return null;
	}

	@Override
	public boolean turnAudit(Integer caseId, int isOk, Integer doUserId) {
		try {
			FdCase fdCase = fdCaseRepository.findOne(caseId);
			if (isOk == 1) {
				fdCase.setHandleUserid(null);
				fdCase.setWindowNumber(null);
				fdCase.setTurnAuditState((byte) isOk);
				fdCaseRepository.save(fdCase);
				addFeedBack(doUserId.longValue(), caseId.intValue(), HandleState.URN_RETUEN_OK.getValue(),
						HandleState.URN_RETUEN_OK.getName(), "");
			} else {
				fdCase.setTurnAuditState((byte) -1);
				addFeedBack(doUserId.longValue(), caseId.intValue(), HandleState.TURN_RETUEN_NO.getValue(),
						HandleState.TURN_RETUEN_NO.getName(), "");
			}
			return true;
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		}
		return false;
	}

}
