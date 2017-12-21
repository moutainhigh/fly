package com.xinfang.service;

import com.xinfang.Exception.BizException;
import com.xinfang.VO.AuthUserInfo;
import com.xinfang.VO.LogInInfo;
import com.xinfang.dao.*;
import com.xinfang.enums.HandleLimitState;
import com.xinfang.enums.HandleState;
import com.xinfang.enums.TzmEnum;
import com.xinfang.log.ApiLog;
import com.xinfang.model.*;
import com.xinfang.utils.DateUtils;
import com.xinfang.utils.RuleUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author tanzhouming
 * @description 投诉办理服务
 * @create 2017-03-21 18:15
 **/
@Service
@Transactional
public class TzmPetitionService {

	@Autowired
	FdCodeRepository fdCodeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TsTransferPersonRepository tsTransferPersonRepository;

	@Autowired
	FdMsgRepository fdMsgRepository;

	@Autowired
	TsCaseHandleRepository tsCaseHandleRepository;

	@Autowired
	FdCaseRepository fdCaseRepository;

	@Autowired
	FdCaseFeedbackAllRepository fdCaseFeedbackAllRepository;

	@Autowired
	TsPersonCaseTypeRepository tsPersonCaseTypeRepository;

	@Autowired
	TsWindowPersonRepository tsWindowPersonRepository;

	@Autowired
	AuthUserGroupRepository authUserGroupRepository;

	@Autowired
	TsForwardOrgRepository tsForwardOrgRepository;

	@Autowired
	FcDwbRepository fcDwbRepository;

	@Autowired
	FcQxsbRepository fcQxsbRepository;

	@Autowired
	FcRybAllFieldRepository fcRybAllFieldRepository;

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	FdDepCaseRepository fdDepCaseRepository;

	@Autowired
	FcXlbRepository fcXlbRepository;

	public List<Map> selectByCodeType(int codeType) {
		List<Map> resultList = new ArrayList<>();
		List<FdCode> fdCodeList = fdCodeRepository.findByTypeAndState(codeType, 1); // 1表示启用的字段
		for (FdCode fdCode : fdCodeList) {
			if (fdCode.getParentCode() == -1) {
				Map map = new HashMap();
				map.put("id", fdCode.getCode());
				map.put("name", fdCode.getName());
				List<Map> mapList = new ArrayList<>();
				map.put("children", mapList);
				resultList.add(map);
			}
		}
		for (FdCode fdCode : fdCodeList) {
			for (Map resultMap : resultList) {
				if (fdCode.getParentCode() == (int) resultMap.get("id")) { // 第二层
					Map map = new HashMap();
					map.put("id", fdCode.getCode());
					map.put("name", fdCode.getName());
					List<Map> mapList = new ArrayList<>();
					map.put("children", mapList);
					(((ArrayList<Map>) resultMap.get("children"))).add(map);
				}
			}
		}
		// 第二次循环录入第三层
		for (FdCode fdCode : fdCodeList) {
			for (Map map : resultList) { // 前2层树，第一层树
				for (Map childrenMap : (List<Map>) map.get("children")) { // 第二层树map
					if (fdCode.getParentCode() == (int) childrenMap.get("id")) {
						Map grandSonMap = new HashMap();
						grandSonMap.put("id", fdCode.getCode());
						grandSonMap.put("name", fdCode.getName());
						((ArrayList<Map>) childrenMap.get("children")).add(grandSonMap);
					}
				}
			}
		}
		return resultList;
	}

	public List<Map> selectByCodeTypeSingle(Integer codeType, Integer parentCode) {
		List<Map> resultList = new ArrayList<>();
		if (parentCode == null) {
			parentCode = -1;
		}
		List<FdCode> fdCodeList = fdCodeRepository.findByTypeAndParentCodeAndState(codeType, parentCode, 1);
		for (FdCode fdCode : fdCodeList) {
			Map map = new HashMap();
			map.put("id", fdCode.getCode());
			map.put("name", fdCode.getName());
			resultList.add(map);
		}
		return resultList;
	}

	// @Transactional 不能这样开启事务。因为需要删除后再添加。
	public List<FdCode> inputFdCode(List<String> codeContent, Integer codeType, Integer parentCode) {
		// List<FdCode> oldFdCodeList =
		// fdCodeRepository.findByType((int)codeType); // 如果 录入多层需屏蔽，不适用
		// if (oldFdCodeList != null && oldFdCodeList.size()>0)
		// fdCodeRepository.delete(oldFdCodeList);
		List<FdCode> fdCodeList = new ArrayList<>();
		for (int i = 0; i < codeContent.size(); i++) {
			FdCode fdCode = new FdCode();
			// 四位数是一层，type为1位或2位数
			// 5位数和6位数是第二层，7位数和8位数为第三层(暂时只能录入第一二层！！！)
			if (parentCode == null)
				parentCode = -1;
			if (parentCode == -1)
				fdCode.setCode(codeType * 100 + i);
			else
				fdCode.setCode(parentCode * 100 + i); // 可能统一类型code数目达到2位数
			fdCode.setName(codeContent.get(i));
			fdCode.setState(1);
			fdCode.setType(codeType);
			fdCode.setParentCode(parentCode);
			fdCodeList.add(fdCode);
		}
		return fdCodeRepository.save(fdCodeList);
	}

	/**
	 * 根据身份证号和密码获取信息
	 * 
	 * @return
	 */
	public LogInInfo selectLogInInfo(String logInName, String password) {
		LogInInfo logInInfo = userRepository.findLogInInfo(logInName, password);
		List<FcXlb> fcXlbList = fcXlbRepository.findAll();
		for (FcXlb fcXlb: fcXlbList){
			if (logInInfo.getXlId()==fcXlb.getXlId()){
				logInInfo.setXlMc(fcXlb.getXlMc());break;
			}
		}
		if (logInInfo == null) {
			if (userRepository.findByLogInName(logInName) == null || userRepository.findByLogInName(logInName).size() == 0){
				throw new BizException("用户名不存在！");
			}else {
				throw new BizException("密码错误！");
			}
//			throw new BizException("请检查用户表中区县id、单位id、科室id、或科室职位id为空");
		}
		String userImg = "http://112.74.94.53:8080/team/" + logInInfo.getIdcard() + ".jpg";
		logInInfo.setRyImg(userImg);
		logInInfo.setUserImg(userImg);
		return logInInfo;
	}

	public LogInInfo selectLogInInfoByRyId(Integer ryId) {
		LogInInfo logInInfo = userRepository.findLogInInfoByRyId(ryId);
		List<FcXlb> fcXlbList = fcXlbRepository.findAll();
		for (FcXlb fcXlb: fcXlbList){
			if (logInInfo.getXlId()==fcXlb.getXlId()){
				logInInfo.setXlMc(fcXlb.getXlMc());break;
			}
		}
		System.out.println("userid  --- "+ryId);
		if (logInInfo == null) {
			logInInfo = userRepository.findLogInInfoNotContainForeignKeyByRyId(ryId);
//			throw new BizException("请检查用户表中区县id、单位id、科室id、或科室职位id为空");
			if (logInInfo == null)
				throw new BizException("根据人员id获取用户信息失败");
		}
		LogInInfo resultRyInfo = new LogInInfo();
		BeanUtils.copyProperties(logInInfo, resultRyInfo);
		String userImg = "http://112.74.94.53:8080/team/" + logInInfo.getIdcard() + ".jpg";
		resultRyInfo.setRyImg(userImg);
		resultRyInfo.setUserImg(userImg);
		return resultRyInfo;
	}

	public LogInInfo selectLogInInfoByLogInName(String logInName) {
		LogInInfo logInInfo = userRepository.findLogInInfoByLogInName(logInName);
		if (logInInfo == null) {
			throw new BizException("请检查用户表中区县id、单位id、科室id、或科室职位id为空");
		}
		List<FcXlb> fcXlbList = fcXlbRepository.findAll();
		for (FcXlb fcXlb: fcXlbList){
			if (logInInfo.getXlId()==fcXlb.getXlId()){
				logInInfo.setXlMc(fcXlb.getXlMc());break;
			}
		}
		LogInInfo resultRyInfo = new LogInInfo();
		BeanUtils.copyProperties(logInInfo, resultRyInfo);
		String userImg = "http://112.74.94.53:8080/team/" + logInInfo.getIdcard() + ".jpg";
		resultRyInfo.setRyImg(userImg);
		resultRyInfo.setUserImg(userImg);
		return resultRyInfo;
	}

	public List<Integer> selectSwgInfoByDwId(int dwId) {
		List<Integer> ryIds = null;
		List<Integer> userIds = tsTransferPersonRepository.finSwgInfoByDwId(dwId);
		if (userIds.size() > 0) {
			ryIds = userIds;
		}
		return ryIds;
	}

	public FdMsg sendMessage(Integer senderId, Integer receiverId, String msgContent,
                             Integer msgType, Integer caseId) {
		FdMsg fdMsg = new FdMsg();
		fdMsg.setSenderId(senderId);
		fdMsg.setReceiverId(receiverId);
		fdMsg.setMsg(msgContent);
		fdMsg.setType(msgType);
		fdMsg.setState(0); // 未读 new Timestamp(System.currentTimeMillis())
		Date now = new Date();
		fdMsg.setCreateTime(new Timestamp(now.getTime()));
		String senderName = null;
//		senderName = userRepository.findRyInfoByRyid(senderId).getUserName();
		senderName = selectLogInInfoByRyId(senderId).getUserName();
		fdMsg.setSenderName(senderName);
		fdMsg.setCaseId(caseId);
		fdMsg = fdMsgRepository.save(fdMsg);
		return fdMsg;
	}

	public List<FdMsg> listMessageByReceiverIdAndState(Integer receiverId, Integer state) {
		List<FdMsg> result = null;
		if (state == null) {
			state = 0;
		}
		String key = "msg:" + receiverId + ":" + state;
		ListOperations<String, List<FdMsg>> ops = redisTemplate.opsForList();
		if (redisTemplate.hasKey(key)) {
			return ops.leftPop(key);
		} else {
			result = fdMsgRepository.findByReceiverIdAndState(receiverId, state);
			ops.leftPush(key, result);
			redisTemplate.expire(key, 30, TimeUnit.MINUTES);
		}

		// redisTemplate
		return result;
	}

	public void changeStateByMessageId(List<Integer> messageIds, Integer state) {
		fdMsgRepository.changeStateByMessageId(messageIds, state);
	}

    public void changeStateByCaseId(Set<Integer> caseIdSet, Integer state) {
        Set<Integer> caseIdSetDatabase = fdMsgRepository.findCaseIdByCaseId(caseIdSet); // 数据库中caseIdSet
        if (!caseIdSet.equals(caseIdSetDatabase)){
            throw new BizException("录入的caseIdSet为"+caseIdSet+",而数据库存在的caseIdSet为"+caseIdSetDatabase);
        }
        fdMsgRepository.changeStateByCaseId(caseIdSet, state);
    }

	public TsCaseHandle inputInfoWhenHandleCase(Integer caseId, Integer handlePersonId) {
//		LogInInfo ryInfo = userRepository.findRyInfoByRyid(handlePersonId);
		LogInInfo ryInfo = selectLogInInfoByRyId(handlePersonId);
		TsCaseHandle tsCaseHandle = new TsCaseHandle();
		tsCaseHandle.setCaseId(caseId);
		tsCaseHandle.setHandlePersonId(handlePersonId);
		tsCaseHandle.setDwId(ryInfo.getDwId());
		tsCaseHandle.setCreateTime(new Timestamp(System.currentTimeMillis()));
		tsCaseHandle = tsCaseHandleRepository.save(tsCaseHandle);
		return tsCaseHandle;
	}

	public List<TsCaseHandle> getInfoWhenHandleCaseByDwid(Integer dwId) {
		return tsCaseHandleRepository.findByDwId(dwId);
	}

	public List<Map> getRyIdAndRyLeaderIdByDwid(Integer dwId) {
		List<Map> result = new ArrayList<>();
		List<TsCaseHandle> tsCaseHandleList = getInfoWhenHandleCaseByDwid(dwId);
		List<Integer> ryIds = new ArrayList<>();
		for (TsCaseHandle tsCaseHandle : tsCaseHandleList) {
			ryIds.add(tsCaseHandle.getHandlePersonId());
		}
		if (ryIds.isEmpty()) {
			throw BizException.DB_SELECTONE_IS_NULL;
		}
		List<LogInInfo> rysInfo = findRyInfoByRyids(ryIds);
		for (LogInInfo logInInfo : rysInfo) {
			Map map = new HashMap();
			map.put("ryId", logInInfo.getRyId());
			List<Integer> integerList = new ArrayList<>();
			map.put("ryLeaderId", getLeaderIdByRyid(logInInfo.getRyId()));
			result.add(map);
		}
		return result;
	}

	public List<TsCaseHandle> getInfoWhenHandleCaseByCaseid(Integer caseId) {
		return tsCaseHandleRepository.findByCaseId(caseId);
	}

	public List<TsCaseHandle> getInfoWhenHandleCaseByHandlePersonId(Integer handlePersonId) {
		return tsCaseHandleRepository.findByHandlePersonId(handlePersonId);
	}

	public List<LogInInfo> findRyInfoByRyids(List<Integer> ryIds) {
		return userRepository.findRyInfoByRyids(ryIds);
	};

	public List<Integer> getLeaderIdByRyid(Integer ryId) {
//		LogInInfo logInInfo = userRepository.findRyInfoByRyid(ryId); // 获取人员信息
//		Integer zwParentId = logInInfo.getZwParentId(); // 该人员上级职位id
		// List<FcRybAllField> fcRybs = userRepository.findByZwId(zwParentId);
		// List ryIdList = new ArrayList();
		// for (FcRybAllField fcRyb : fcRybs) {
		// ryIdList.add(fcRyb.getRyId());
		// }

		List ryIdList = new ArrayList();
		Integer checkPersonId = fcRybAllFieldRepository.getCheckPersonIdByRyId(ryId);
		ryIdList.add(checkPersonId);
		return ryIdList;
	};

	@Transactional
	public Map updateCaseStatusAndFeedbackMessage(Integer ryId, Integer caseId, Integer caseHandleState, Integer caseType,
			Integer limitTime, String note, String enclosure1, String enclosure2, String enclosure3,
			String currentHandleState, Integer delayDay) {
		// fdCaseRepository.updateCaseHandleStateById(caseId, caseHandleState);
		FdCase fdCase = fdCaseRepository.findById(caseId); // 因为事务状态还没更新
		if (fdCase == null) {
			throw new BizException(500500, "需要更新的caseId不存在");
		}

		FdCaseFeedbackAll fdCaseFeedbackAll = new FdCaseFeedbackAll();
		fdCaseFeedbackAll.setCaseId(caseId);
		fdCaseFeedbackAll.setState(caseHandleState);
		fdCaseFeedbackAll.setType(caseType); // type还是caseType
		fdCaseFeedbackAll.setCreateTime(new Date());
		fdCaseFeedbackAll.setCreaterId(ryId);
		fdCaseFeedbackAll.setLimitTime(limitTime);

		Date latestCreateTime = fdCaseFeedbackAllRepository.findLatestCreateTimeByCaseId(caseId);
		String takeTime = DateUtils.getDatePoor(latestCreateTime, new Date());
		fdCaseFeedbackAll.setTaketime(takeTime);
		// 2017-06-10 当前时间 - 案件创建时间
		Date caseCreateTime = fdCaseRepository.findCreateTimeById(caseId);  // 案件的创建时间
		if (caseCreateTime != null){
			String intervalTime = DateUtils.getDatePoor(caseCreateTime, new Date());
			fdCaseFeedbackAll.setIntervalTime(intervalTime);
		}else {
            String errorMessage = "updateCaseStatusAndFeedbackMessage 案件没有创建时间，无法设置案件反馈表的interval_time字段";
            System.err.println(errorMessage);
            ApiLog.log(errorMessage);
        }

		LogInInfo logInInfo = null;
		try {
			logInInfo = selectLogInInfoByRyId(ryId);;
			fdCaseFeedbackAll.setCreater(logInInfo.getUserName());
			fdCaseFeedbackAll.setCreaterCompany(logInInfo.getDwMc()); // 单位
			fdCaseFeedbackAll.setCreaterDep(logInInfo.getKsMc()); // 科室
			fdCaseFeedbackAll.setCreaterRole(logInInfo.getZwMc());
			fdCaseFeedbackAll.setDepId(logInInfo.getDwId());
			fdCaseFeedbackAll.setUserurl(logInInfo.getRyImg());

			fdCaseFeedbackAll.setState(caseHandleState);

			if (caseType == 3) {
				fdCase.setIsHandle((byte) 1);
			} else {
				fdCase.setIsHandle((byte) 2);
			}
			fdCase.setCaseHandleState(caseHandleState);
			fdCase.setCurrentHandleUnitid(logInInfo.getDwId()); // 当前案件处理单位ID
			fdCase.setCurrentHandlePersonalid(logInInfo.getRyId()); // 更新当前案件处理人ID

			// bug #307  2017-06-09
			if (HandleState.ASSIGNED.getValue() != caseHandleState && HandleState.TRUN.getValue() != caseHandleState){
				fdCase.setTransferUnitid(logInInfo.getDwId());
				fdCase.setTransferPersonalid(logInInfo.getRyId());
			}

			fdCase.setHandleType(caseType); // 更新当前案件处理类型
			fdCase.setHandleDays(delayDay);
			fdCase.setCurrentHandleState(currentHandleState); // 更新当前案件处理状态
			fdCase.setHandleFactEndTime(new Date());
			fdCase.setHandlePeriodStart(fdCase.getGmtCreate());
			fdCase.setHandlePeriodEnd(DateUtils.addDays(fdCase.getGmtCreate(), delayDay));
			fdCase.setGmtCreate(new Date());
			fdCaseRepository.save(fdCase);
			// fdCaseRepository.saveAndFlush(fdCase);

		} catch (Exception e) {
			throw new BizException(500, "获取创建者信息异常，无法根据人员id获取相关人员信息数据，请查看数据库人员表");
		}

		fdCaseFeedbackAll.setStartTime(fdCase.getHandlePeriodStart());
		fdCaseFeedbackAll.setEndTime(fdCase.getHandlePeriodEnd());
		fdCaseFeedbackAll.setUpdateTime(new Date());
		if (caseType == HandleState.RETURN.getValue()) {
			fdCaseFeedbackAll.setNote(HandleState.RETURN.getName()); // 直接退回
		} else {
			fdCaseFeedbackAll.setNote(note); // 备注
		}
		fdCaseFeedbackAll.setEnclosure1(enclosure1); // 附件1
		fdCaseFeedbackAll.setEnclosure2(enclosure2);
		fdCaseFeedbackAll.setEnclosure3(enclosure3);

		fdCaseFeedbackAllRepository.save(fdCaseFeedbackAll);
		Map map = new HashMap();
		map.put("caseId", caseId);

		/****
		 * ******************zhanghr 2017-05-11 start ************************
		 ***/
		int count = fdDepCaseRepository.countByCaseIdAndDepId(fdCase.getId().intValue(), logInInfo.getDwId());

		if (count < 1) {
			if (limitTime == null || limitTime.intValue() == 0) {
				limitTime = 90;
			}
			Date now = new Date();
			FdDepCase depcase = new FdDepCase();
			depcase.setCaseId(fdCase.getId().longValue());
			depcase.setCreaterId(fdCase.getCreatorId().longValue());
			depcase.setCreateTime(now);
			depcase.setStartTime(now);
			depcase.setDepId(logInInfo.getDwId());
			depcase.setEndTime(DateUtils.add(now, limitTime * RuleUtil.getRule().getRegister() * 0.01f));
			depcase.setState(1);
			depcase.setNeedReceipt(1);
			depcase.setProcessid(null);
			depcase.setLimitTime(limitTime * RuleUtil.getRule().getRegister() * 0.01f);
			depcase.setType(HandleLimitState.register.getValue());
			depcase.setNote(HandleLimitState.register.getName());
			depcase.setUpdateTime(new Date());  //
			fdDepCaseRepository.save(depcase);
		}
		/****
		 * ******************zhanghr 2017-05-11 end ************************
		 ***/
		return map;
	}

	public List<Integer> getRyIdByTypeId(List<Integer> typeIds, Integer dwId) {
		List<TsPersonCaseTypeNew> tsPersonCaseTypeList = tsPersonCaseTypeRepository.findByTypeId(typeIds, dwId);
		List<Integer> ryIdList = new ArrayList();
		for (TsPersonCaseTypeNew tsPersonCaseType : tsPersonCaseTypeList) {
			ryIdList.add(tsPersonCaseType.getUserId());
		}
		return ryIdList;
	};

	/**
	 * 根据单位id 查询可以接收案件的人员id
	 * 
	 * @param dwId
	 * @return
	 */
	public List<Integer> getRyIdByDwId(Integer dwId) {
		return tsPersonCaseTypeRepository.findRyIdByDwId(dwId);
	};

	/**
	 * 
	 * @param caseId
	 *            案件id
	 * @param type
	 *            1001联系上访人 ；1002调查取证；1003沟通协调; 1004延期申请; 1005案件退回 ; 1006案件办结
	 * @param note
	 *            联系情况录入|取证内容|诉求信息补充
	 * @param enclosure1
	 *            附件1
	 * @param userId
	 *            用户id
	 * @param handleId
	 *            处理人id
	 * @param noteoth
	 *            信访人需求
	 * @description 案件处理（联系上访人,调查取证,沟通协调）
	 * @author ZhangHuaRong
	 * @param updateTime
	 * @throws ParseException
	 * @update 2017年3月27日 下午6:07:38
	 */
	public FdCaseFeedbackAll handleCse(Integer caseId, int type, String note, String enclosure1, String witnessName,
			Integer userId, Integer handleId, String noteoth, String updateTime, String currentHandleState,
			Integer caseHandleType, Integer state) throws ParseException {
		FdCase fdCase = fdCaseRepository.findById(caseId);
		if (fdCase == null) {
			throw new BizException(500500, "需要更新的caseId不存在");
		}
		LogInInfo info = null;
		Object obj = redisTemplate.opsForValue().get("user:" + userId.toString());
		FdCaseFeedbackAll fdCaseFeedbackAll = new FdCaseFeedbackAll();

		if (obj != null) {
			info = (LogInInfo) obj;
		} else {
			info = selectLogInInfoByRyId(userId);
		}
		Date lastupdate = fdCaseFeedbackAllRepository.getlastUpdateTime(caseId);

		fdCaseFeedbackAll.setCaseId(caseId);
		fdCaseFeedbackAll.setCreateTime(new Date());
		fdCaseFeedbackAll.setUpdateTime(DateUtils.strToDate2(updateTime));
		fdCaseFeedbackAll.setDepId(info.getDwId());
		fdCaseFeedbackAll.setCreater(info.getUserName());
		fdCaseFeedbackAll.setCreaterRole(info.getZwMc());
		fdCaseFeedbackAll.setCreaterDep(info.getDwMc());
		fdCaseFeedbackAll.setDepId(info.getDwId());
		fdCaseFeedbackAll.setCreaterCompany(info.getKsMc());
		fdCaseFeedbackAll.setUserurl(info.getUserImg());

		Date latestCreateTime = fdCaseFeedbackAllRepository.findLatestCreateTimeByCaseId(caseId);
		String takeTime = DateUtils.getDatePoor(latestCreateTime, new Date());
		fdCaseFeedbackAll.setTaketime(takeTime);
		// 2017-06-10 当前时间 - 案件创建时间
		Date caseCreateTime = fdCaseRepository.findCreateTimeById(caseId);  // 案件的创建时间
		if (caseCreateTime != null){
			String intervalTime = DateUtils.getDatePoor(caseCreateTime, new Date());
			fdCaseFeedbackAll.setIntervalTime(intervalTime);
		}else {
		    String errorMessage = "handleCse案件没有创建时间，无法设置案件反馈表的interval_time字段";
		    System.err.println(errorMessage);
            ApiLog.log(errorMessage);
        }

		if (lastupdate != null) {
			fdCaseFeedbackAll.setTaketime(DateUtils.getDatePoor(lastupdate, new Date()));
		}
		if (handleId != null) {
			fdCaseFeedbackAll.setCreaterId(handleId);
		} else {
			fdCaseFeedbackAll.setCreaterId(userId);
		}
		try {
			// if (enclosure1 != null && enclosure1.length !=0){
			// String enclosure1Str = "";
			// for (String item: enclosure1){
			// enclosure1Str += item.trim();
			// }
			// fdCaseFeedbackAll.setEnclosure1(enclosure1Str); // 附件1
			// }
			fdCaseFeedbackAll.setEnclosure1(enclosure1); // 附件1
		} catch (Exception e) {

		}

		fdCaseFeedbackAll.setEnclosure2(witnessName); // 去证人姓名
		Integer[] typeList = new Integer[] { 1001, 1002, 1003, 1004, 1005, 1006 };
		Boolean isType = false;
		for (int i = 0; i < typeList.length; i++) {
			if (type == typeList[i]) {
				isType = true;
				break;
			}
		}
		if (isType == false)
			throw BizException.RECEIVE_DATE_EXCEPTION;

		fdCaseFeedbackAll.setType(type);
		fdCaseFeedbackAll.setNote(note);
		fdCaseFeedbackAll.setNoteOth(noteoth);

		fdCaseFeedbackAll.setState(state);

		fdCase.setCurrentHandleUnitid(info.getDwId()); // 当前案件处理单位ID
		fdCase.setCurrentHandlePersonalid(info.getRyId()); // 更新当前案件处理人ID
		fdCase.setHandleType(caseHandleType); // 更新当前案件处理类型
		fdCase.setCurrentHandleState(currentHandleState); // 更新当前案件处理状态

		// bug #307  2017-06-09
//		if (HandleState.ASSIGNED.getValue() != caseHandleState && HandleState.TRUN.getValue() != caseHandleState){
//			fdCase.setTransferUnitid(info.getDwId());
//			fdCase.setTransferPersonalid(info.getRyId());
//		}

		fdCaseRepository.save(fdCase);

		FdCaseFeedbackAll result = fdCaseFeedbackAllRepository.save(fdCaseFeedbackAll);
		return result;
	};

	/**
	 * 信访局工作人员根据人员id获取对应窗口
	 * 
	 * @param ryId
	 * @return
	 */
	public Map getWindowByRyId(Integer ryId) {
		TsWindowPerson tsWindowPerson = tsWindowPersonRepository.findByUserId(ryId);
		Map map = new HashMap();
		map.put("window_id", tsWindowPerson.getWindowId());
		map.put("window_name", tsWindowPerson.getTsWindow().getWindowName());
		return map;
	}

	public List<Map> getAllSwg() {
		List<Integer> orgIds = tsTransferPersonRepository.findAllSwg();
		if (orgIds.size() == 0)
			return null;
		List<Map> mapList = fcDwbRepository.findDwMcAndDwIdByDwIds(orgIds);
		return mapList;
	}

	/**
	 * 根据区县市id获取职能部门
	 * 
	 * @param qxsId
	 * @return
	 */
	public List<Map> getDwMcAndDwIdByQxsId(Integer qxsId) {
		return fcDwbRepository.findDwMcAndDwIdByQxsId(qxsId);
	}

	public List<AuthUserInfo> getAuthUserInfoByGroupId(Integer groupId) {
		List<AuthUserInfo> authUserInfoList = authUserGroupRepository.findAuthUserInfoByGroupId(groupId);
		return authUserInfoList;
	}

	public AuthUserInfo isInGroup(Integer ryId, Integer groupId) {
		AuthUserInfo authUserInfo = authUserGroupRepository.findAuthUserInfoByRyIdAndGroupId(ryId, groupId);
		return authUserInfo;
	}

	/**
	 * 根据人员id获取可以转发的单位（机构）
	 * 
	 * @param ryId
	 * @param dwTypes
	 * @return
	 */
	public List<Map> getTransferDwByRyId(Integer ryId, List<Integer> dwTypes) {
		// List<Integer> dwIdList = tsForwardOrgRepository.findDwIdByRyId(ryId);
		List<Map> resultList = null;
		if (dwTypes == null || dwTypes.size() == 0) {
			resultList = tsForwardOrgRepository.findDwInfoByRyId(ryId);
		} else {
			resultList = tsForwardOrgRepository.findDwInfoByRyIdAndDwType(ryId, dwTypes);
		}
		// 将结果转成树结构
		List<Map> treeList = new ArrayList<>();
		for (Map map : resultList) { // 第一层树
			Map mapItem = new HashMap();
			mapItem.put("id", map.get("dwType"));
			String name = TzmEnum.getName((Integer) map.get("dwType"));
			mapItem.put("name", name == null ? "其他" : name);
			if (resultList.size() == 0 || !treeList.contains(mapItem)) {
				treeList.add(mapItem);
			}
		}
		for (Map mapK : treeList) { // 第二层树，建立在第一层树上
			List<Map> childrenMapList = new ArrayList<>();
			for (Map mapJ : resultList) {
				if (mapJ.get("dwType").equals(mapK.get("id"))) {
					Map childrenMap = new HashMap();
					childrenMap.put("id", mapJ.get("dwId"));
					childrenMap.put("name", mapJ.get("dwMc"));
					childrenMapList.add(childrenMap);
				}
			}
			mapK.put("children", childrenMapList);
		}
		return treeList;
	}

	public Map getDwInfoRyId(Integer ryId) {
		FcRyb fcRyb = userRepository.findById(ryId);
		FcDwb fcDwb = fcDwbRepository.findOne(fcRyb.getDwId());
		Map map = new HashMap();
		map.put("ryId", fcRyb.getRyId());
		map.put("ryMc", fcRyb.getRyMc());
		map.put("dwId", fcDwb.getDwId());
		map.put("dwMc", fcDwb.getDwMc());
		map.put("dwType", fcDwb.getDwType());
		map.put("dwTypeName", TzmEnum.getName(fcDwb.getDwType()));
		return map;
	}

	/**
	 * 根据码表id获取码表name
	 * 
	 * @param areaId
	 * @return
	 */
	public String getFdCodeNameByCode(Integer areaId) {
		String codeName = fdCodeRepository.getNameByCode(areaId);
		// Map map = new HashMap();
		// map.put("id", areaId);
		// map.put("name", codeName);
		return codeName;
	}

	/**
	 * 根据问题属地id获取职能部门id
	 * 
	 * @param areaId
	 * @return
	 */
	public List<Map> getDwIdAndNameByProblemArea(Integer areaId) {
		String codeName = getFdCodeNameByCode(areaId);
		Integer qxsId = fcQxsbRepository.findQxsIdByQxsMc(codeName);
		List<Map> dwInfoList = getDwMcAndDwIdByQxsId(qxsId);
		return dwInfoList;
	}

	public List<Map> getDwMcByDwId(List<Integer> dwIds) {
		return fcDwbRepository.findDwMcAndDwIdByDwIds(dwIds);
	}
}
