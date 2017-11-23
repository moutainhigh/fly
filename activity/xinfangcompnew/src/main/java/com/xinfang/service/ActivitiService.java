package com.xinfang.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Sorts;
import com.github.wenhao.jpa.Specifications;
import com.google.gson.JsonObject;
import com.xinfang.Exception.BizException;
import com.xinfang.VO.HistoryTaskVO;
import com.xinfang.VO.LogInInfo;
import com.xinfang.VO.NodeVO;
import com.xinfang.VO.TaskVO;
import com.xinfang.context.PageFinder;
import com.xinfang.context.StateConstants;
import com.xinfang.dao.FcDwbRepository;
import com.xinfang.dao.FdCaseFeedbackAllRepository;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.FdCodeRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.dao.FdWarnRepository;
import com.xinfang.enums.HandleLimitState;
import com.xinfang.enums.HandleState;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FcDwb;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdCaseFeedbackAll;
import com.xinfang.model.FdCode;
import com.xinfang.model.FdDepCase;
import com.xinfang.model.FdGuest;
import com.xinfang.model.FdWarn;
import com.xinfang.model.LawRulesEntity;
import com.xinfang.personnelmanagement.service.BaseTreeService;
import com.xinfang.utils.DateUtils;
import com.xinfang.utils.RuleUtil;

/**
 * Created by jery on 2016/11/23.
 */

@Service
// @Transactional
public class ActivitiService {
	// 注入为我们自动配置好的服务
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	FdDepCaseRepository fdDepCaseRepository;
	@Autowired
	ManagementService managementService;

	/*
	 * @Autowired private ProcessEngine processEngine;
	 */
	/*
	 * @Autowired private ProcessEngineConfiguration processEngineConfiguration;
	 */

	@Autowired
	private CaseService caseService;

	@Autowired
	TzmPetitionService tzmPetitionService;

	@Autowired
	FdCaseFeedbackAllRepository fdCaseFeedbackAllRepository;// 案件反馈表（日志表）

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private FdCaseRepository fdCaseRepository;

	@Autowired
	private IdentityService identityService;

	@Autowired
	FcDwbRepository fcDwbRepository;
	
	@Autowired
	FdWarnRepository fdWarnRepository;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	 @Autowired
	 BaseTreeService baseTreeService;
	 
	 @Autowired
	 FdCodeRepository fdCodeRepository;
	 
	 /** 
	     * 中止流程(特权人直接审批通过等) 
	     *  
	     * @param taskId 
	     */  
	   @Transactional
	    public void endProcess(String processInstanceId,String reason	,Integer caseid) throws Exception { 
	    	backCase(caseid);//还原案件以便能重新发起
	    	deletemark(caseid);//删除 fd_case_feedback 和 fd_dep_case数据
	    	runtimeService.deleteProcessInstance(processInstanceId,reason);
	        historyService.deleteHistoricProcessInstance(processInstanceId);//(顺序不能换)
	    }  
	

	private void deletemark(Integer caseid) {
		fdDepCaseRepository.deleteByCaseId(Long.parseLong(caseid.toString()));
		fdCaseFeedbackAllRepository.deleteByCaseId(caseid);
	}

    /*
     * handleDuration	// 处理时长
handlePeriodStart // 处理时间段开始
handlePeriodEnd // 处理时间段结束
caseHandleState // 案件状态
isHandle
isFlow

currentHandleUnitid	 // 当前处理单位ID
currentHandlePersonalid	// 当前处理人ID
handleType	// 处理方式
handleDays	// 处理天数
handleFactEndTime	// 案件实际结束时间
receiveUnitid	// 案件接收单位ID
receivePersonalid	 // 案件接收人ID
     */
	private void backCase(Integer caseid) {
		FdCase fdCase = caseService.getFdCaseById(caseid);
		fdCase.setHandleDays(null);
		fdCase.setHandlePeriodStart(null);
		fdCase.setHandlePeriodEnd(null);
		fdCase.setIsHandle(null);
		fdCase.setIsFlow(null);
		fdCase.setCurrentHandlePersonalid(null);
		fdCase.setCaseHandleState(StateConstants.HANDLE_TODO);
		fdCase.setHandleType(null);
		fdCase.setHandleDays(null);
		fdCase.setHandleFactEndTime(null);
		fdCase.setReceivePersonalid(null);
		fdCase.setReceiveUnitid(null);
		fdCaseRepository.save(fdCase);
	}


	// 开始流程，传入申请者的id以及公司的id
	public void startProcess(Long personId, Long caseId) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("personId", personId);
		variables.put("caseId", caseId);

		ProcessInstance instance = runtimeService.startProcessInstanceByKey("test", variables);
		// usertask1
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getProcessDefinitionId()).list();
		for (Task task : tasks) {

		}

		System.out.println("=========" + instance);
	}

	// 开始交办流程
	/**
	 * 
	 * @param userid
	 *            用户id
	 * @param caseid
	 *            案件id
	 * @param leaderid
	 *            发起者领导id
	 * @param type
	 *            类型1交办 2转办
	 * @param state
	 *            案件状态 流程状态2000 正办 1000代办 3000已办
	 * @param depid
	 *            单位id
	 * @param files
	 *            附件信息
	 * @param note
	 *            备注
	 * @param xbdepinfo
	 *            协办单位信息
	 * @param handleday
	 *            办理期限
	 * @param needReceipt
	 *            是否需要回执
	 * @author ZhangHuaRong
	 * @param taskid 
	 * @throws Exception
	 * @update 2017年3月30日 上午10:49:09
	 */
	public String startProcess(long userid, int caseid, int leaderid, int type, int state, int depid, String files,
			String note, String xbdepinfo, int handleday, int needReceipt, String taskid) throws Exception {
		Map<String, Object> variables  =null;
		FdCase fdCase = caseService.getFdCaseById(caseid);
		FcDwb fcDwb = fcDwbRepository.findOne(depid);
		 Map<String, Object> map = baseTreeService.getPersonCaseType3(depid, fdCase.getPetitionWay());
         if(map==null){
        	 throw new BizException(10015,"案件转办失败！"+fcDwb.getDwMc()+"没有设置收文岗。");
         }else{
        	 if(map.get("FH_RY_FJID").toString().equals("-1")){
        		 StringBuffer sb = new StringBuffer();
        		 sb.append(map.get("DW_MC").toString()).append(",").append(map.get("RY_MC").toString()).append("没有分管领导");
         		throw	 new BizException(10003, sb.toString());
         	}
        	if( map.get("FH_RY_ID").toString().equals("0")){
           	 throw new BizException(10015,"案件转办失败！"+fcDwb.getDwMc()+"没有设置收文岗。");

        	}
        	
        	 
         }
		
		if(taskid!=null && !taskid.equals("")){
			variables  = taskService.getVariables(taskid);
			variables.put("createrid", userid);
			variables.put("createrLeader", leaderid);
			variables.put("depid", depid);
			variables.put("handleday", handleday);
			variables.put("caseid", caseid);
			variables.put("type", type + "");
			variables.put("xbdepinfo", xbdepinfo);
			variables.put("needReceipt", String.valueOf(needReceipt));
			addcomment(taskid, files, note, (int) userid, type);
			taskService.complete(taskid, variables);
			fdCase.setCurrentHandleState("领导审批");
			fdCase.setIsFlow((byte)0);
			fdCaseRepository.save(fdCase);
			return variables.get("processid")+"";
			
		}
		
		
		 variables = new HashMap<String, Object>();
		variables.put("createrid", userid);
		variables.put("createrLeader", leaderid);
		variables.put("depid", depid);
		variables.put("handleday", handleday);
		variables.put("caseid", caseid);
		variables.put("type", type + "");
		variables.put("xbdepinfo", xbdepinfo);
		variables.put("needReceipt", String.valueOf(needReceipt));
		
		// FdCase fdCase = caseService.getCaseById(caseid);
		fdCase.setCaseHandleState(state);
		variables.put("fdCase", fdCase);
		ProcessInstance instance = null;
		if (xbdepinfo != null) {
			variables.put("hasco", 1);// 是否有协办单位的标志
		}

		// 1交办 2转办
		if (1 == type) {
			instance = runtimeService.startProcessInstanceByKey("jiaoban3", variables);
			fdCase.setHandleFlowType((byte) 1);
		} else if (2 == type) {
			if (0 == needReceipt) {
				// 转办流程，不需要回执
				instance = runtimeService.startProcessInstanceByKey("zhuanban2", variables);
				fdCase.setHandleFlowType((byte) 3);
			} else {
				// 转办流程，需要回执
				instance = runtimeService.startProcessInstanceByKey("zhuanban1", variables);
				fdCase.setHandleFlowType((byte) 2);
			}

		}

		List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getId()).list();
		Integer num = handleday;
		LogInInfo info = null;
		info = getUserInfo(userid);
		addformdatestart(type, null, (int) userid, null, files, variables, info, note, depid, num);
		
		for (Task task : tasks) {
			addcomment(task.getId(), files, note, (int) userid, type);
			variables.put("processid", instance.getId());
			taskService.complete(task.getId(), variables);
			break;
		}
		
		LogInInfo user=addFeedBack(userid, caseid, type, note, files, null, null,instance.getId());// 添加反馈信息
	
		
		
		fdCase.setHandlePeriodEnd(DateUtils.addDays(new Date(), handleday));
		fdCase.setIsHandle((byte) 2);
		fdCase.setIsFlow((byte) 1);
		fdCase.setHandleDays(handleday);
		fdCase.setGmtModified(new Date());
		fdCaseRepository.save(fdCase);
		
		  /**** ******************zhanghr 2017-04-19 ************************ ***/
        int count = fdDepCaseRepository.countByCaseIdAndDepId(fdCase.getId().intValue(), depid);
        
        if (count < 1) {
            if (num == null || num.intValue() == 0) {
            	num = 90;
            }
           // Date now = new Date();
            Date now = fdCase.getGmtCreate();
            FdDepCase depcase = new FdDepCase();
            depcase.setCaseId(fdCase.getId().longValue());
            depcase.setCreaterId(fdCase.getCreatorId().longValue());
            depcase.setCreateTime(now);
            depcase.setStartTime(now);
            depcase.setDepId(info.getDwId());
            depcase.setEndTime(DateUtils.add(now, num * RuleUtil.getRule().getRegister() * 0.01f));
            depcase.setState(1);
            depcase.setNeedReceipt(1);
            depcase.setProcessid(instance.getId());
            depcase.setLimitTime(num * RuleUtil.getRule().getRegister() * 0.01f);
            depcase.setType(HandleLimitState.register.getValue());
            depcase.setNote(HandleLimitState.register.getName());
            fdDepCaseRepository.save(depcase);
        }
        if(type == 2){
        	zBAddDepCase(depid, handleday,caseid,instance.getId());
        }
        // bug #304 需要
        updateFdCase(caseid,depid,user);

		// 获取协办单位信息
		/*if (xbdepinfo != null) {
			List<FdDepCase> list = getfromxbdepinfo(userid, xbdepinfo, caseid, depid, handleday, needReceipt);

			for (FdDepCase depcase : list) {// TODO 赶时间协办暂缓 //
				// startProcess(userid,caseid,leaderid,type,state,depcase.getDepId(),files,note,null,depcase.getLimitTime(),depcase.getNeedReceipt())
				// ;
				FdDepCase dcase = fdDepCaseRepository.saveAndFlush(depcase);

			}

		}*/
		return instance.getId();
	}
	
	// bug#304 需要
	/**
	 * 
	 * @param caseId
	 * @param depid
	 * @param info
	 * @description 流程开始 设置转办单位 转办人 责任单位 责任人
	 * @version 1.0
	 * @author ZhangHuaRong
	 * @update 2017年6月9日 下午3:27:48
	 */
	private void updateFdCase(Integer caseId, int depid,LogInInfo info) {
		try {
			FcDwb fcDwb = fcDwbRepository.findOne(depid);
			FdCase fdCase = caseService.getFdCaseById(caseId);
			Integer targetUser = null;//目标单位用户
			List<Map> num =baseTreeService.getPersonCaseType(depid);
			if(num.isEmpty()||num.size()>1){
				List<Integer> shouwgs = tzmPetitionService.selectSwgInfoByDwId(depid);
				if(!shouwgs.isEmpty()){
					targetUser=shouwgs.get(0);
				}
			}else{
				targetUser = (Integer) num.get(0).get("ryId");
			}
			
			if(fcDwb.getDwType().intValue()==3||fcDwb.getDwType().intValue()==7){//如果是信访局
			  fdCase.setTransferUnitid(depid);
			  fdCase.setTransferPersonalid(targetUser);
			}else{
				fdCase.setReceiveUnitid(depid);
				fdCase.setReceivePersonalid(targetUser);
				//转办单位
				fdCase.setTransferUnitid(info.getDwId());
				fdCase.setTransferPersonalid(info.getRyId());
			}
			fdCaseRepository.save(fdCase);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param caseId
	 * @param depid
	 * @description 区县信访局转发设置责任单位 ,责任人
	 * @version 1.0
	 * @author ZhangHuaRong
	 * @update 2017年6月9日 下午3:27:03
	 */
	private void updateFdCaseOth(Integer caseId, Integer depid) {
		try {
			FcDwb fcDwb = fcDwbRepository.findOne(depid);
			FdCase fdCase = caseService.getFdCaseById(caseId);
			Integer targetUser = null;//目标单位用户
			List<Map> num =baseTreeService.getPersonCaseType(depid);
			if(num.isEmpty()||num.size()>1){
				List<Integer> shouwgs = tzmPetitionService.selectSwgInfoByDwId(depid);
				if(!shouwgs.isEmpty()){
					targetUser=shouwgs.get(0);
				}
			}else{
				targetUser = (Integer) num.get(0).get("ryId");
			}
			fdCase.setReceiveUnitid(depid);
			fdCase.setReceivePersonalid(targetUser);
			fdCaseRepository.save(fdCase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    /**
     * 
     * @param caseId
     * @param dwId
     * @param userid
     * @description 收文岗派件后设置责任单位和责任人
     * @version 1.0
     * @author ZhangHuaRong
     * @update 2017年6月9日 下午3:26:29
     */
	private void updateFdCaseFord(Integer caseId,Integer dwId, int userid) {
		FdCase fdCase = caseService.getFdCaseById(caseId);
		fdCase.setReceiveUnitid(dwId);
		fdCase.setReceivePersonalid(userid);
		fdCaseRepository.save(fdCase);
		
	}
	
	private void zBAddDepCase(Integer depid, Integer handleday,Integer caseid, String processid){
		try {
//			Integer depid = (Integer) task.getVariable("depid");
//			Integer handleday = (Integer) task.getVariable("handleday");
//			Integer caseid = (Integer) task.getVariable("caseid");
//			Object processid = task.getVariable("processid");

			Date nowtime = new Date();
			FdDepCase maindep = new FdDepCase();
			FcDwb fcDwb = fcDwbRepository.findOne(depid);
			List<FdDepCase> depcases = fdDepCaseRepository.findByCaseIdAndState(caseid, 1);

			if (fcDwb.getDwType().intValue() == 4||fcDwb.getDwType().intValue() == 8) {// 职能部门
				maindep.setStartTime(nowtime);
				maindep.setEndTime(DateUtils.add(nowtime, handleday * RuleUtil.getRule().getReply() * 0.01f));
				maindep.setLimitTime(handleday * RuleUtil.getRule().getReply() * 0.01f);
				maindep.setType(HandleLimitState.reply.getValue());
				maindep.setNote(HandleLimitState.reply.getName());
			} else if (fcDwb.getDwType().intValue() == 7||fcDwb.getDwType().intValue() == 3) {// 区县
				maindep.setStartTime(nowtime);
				maindep.setEndTime(DateUtils.add(nowtime, handleday * RuleUtil.getRule().getForword() * 0.01f));
				maindep.setLimitTime(handleday * RuleUtil.getRule().getRegister() * 0.01f);
				maindep.setType(HandleLimitState.forword.getValue());
				maindep.setNote(HandleLimitState.forword.getName());
			} else {
				maindep.setNote("转交单位id" + maindep.getDepId() + " 类型：" + fcDwb.getDwType());
			}
			maindep.setCaseId((long) caseid);
			// maindep.setCreaterId(userid);
			maindep.setCreateTime(nowtime);
			// maindep.setNeedReceipt(needReceipt);
			maindep.setState(1);
			maindep.setUpdateTime(nowtime);
			maindep.setDepId(depid);
			if (processid != null) {
				maindep.setProcessid(processid.toString());
			}
			fdDepCaseRepository.saveAndFlush(maindep);
			if (depcases != null && !depcases.isEmpty()) {
				FdDepCase dep = depcases.get(0);
				dep.setState(0);
				dep.setUpdateTime(nowtime);
				fdDepCaseRepository.saveAndFlush(dep);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ApiLog.log("交办领导审批后计算被交办单位限办时间出错：" + e.getMessage());
		}

	}
	

	private LogInInfo addFeedBack(long userid, int caseid, int type, String note, String file1, String file2,
			String file3,String processid) {
		Date now = new Date();
		LogInInfo info = null;
		info = getUserInfo(userid);

		Date lastupdate = fdCaseFeedbackAllRepository.getlastUpdateTime(caseid);

		FdCaseFeedbackAll fdCaseFeedback = new FdCaseFeedbackAll();
		fdCaseFeedback.setType(type);
		fdCaseFeedback.setUserurl(info.getUserImg());
		fdCaseFeedback.setCaseId(caseid);
		fdCaseFeedback.setCreaterId((int) userid);
		fdCaseFeedback.setCreateTime(now);
		fdCaseFeedback.setUpdateTime(now);
		fdCaseFeedback.setNote(note);
		fdCaseFeedback.setEnclosure3(file3);
		fdCaseFeedback.setCreaterCompany(info.getKsMc());
		fdCaseFeedback.setCreaterRole(info.getZwMc());
		fdCaseFeedback.setCreaterDep(info.getDwMc());
		fdCaseFeedback.setDepId(info.getDwId());
		fdCaseFeedback.setCreater(info.getUserName());
		fdCaseFeedback.setEnclosure2(file2);
		fdCaseFeedback.setEnclosure1(file1);
		if (lastupdate != null) {
			fdCaseFeedback.setTaketime(DateUtils.getDatePoor(lastupdate, now));
		}
		// fdCaseFeedback.set

		fdCaseFeedbackAllRepository.saveAndFlush(fdCaseFeedback);
		
		FdCase fdCase = caseService.getFdCaseById(caseid);
		try {
			NodeVO nextnode =getcurrentnode(processid) ;
			if(nextnode!=null){
				if(nextnode.getCurrentUserInfo() != null){
					fdCase.setCurrentHandleUnitid(nextnode.getCurrentUserInfo().getDwId());//当前处理单位
					fdCase.setCurrentHandlePersonalid(nextnode.getCurrentUserInfo().getRyId());//当前处理人
				}
				else{
					// 流程已结束了
					fdCase.setCurrentHandleUnitid(nextnode.getEndUserInfo().getDwId());//当前处理单位
					fdCase.setCurrentHandlePersonalid(nextnode.getEndUserInfo().getRyId());//当前处理人
				}
				
				String taskname = nextnode.getTaskname();
//			if(taskname.contains("审")){
//				fdCase.setCurrentHandleState(HandleState.TODO_AUDIT.getName());
//			}else if(taskname.contains("操作")){
//				fdCase.setCurrentHandleState(HandleState.TODO_HANDLE.getName());
//			}
				fdCase.setCurrentHandleState(taskname);

			}
			fdCase.setHandleType(type);
			fdCase.setGmtModified(new Date());
			fdCaseRepository.save(fdCase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	
	private LogInInfo addFeedBackoth(long userid, int caseid, int type, String note, String file1, String file2,
			String file3,String processid,String taskstr, int approval) {
		Date now = new Date();
		LogInInfo info = null;
		info = getUserInfo(userid);

		Date lastupdate = fdCaseFeedbackAllRepository.getlastUpdateTime(caseid);

		FdCaseFeedbackAll fdCaseFeedback = new FdCaseFeedbackAll();
		fdCaseFeedback.setType(type);
		fdCaseFeedback.setUserurl(info.getUserImg());
		fdCaseFeedback.setCaseId(caseid);
		fdCaseFeedback.setCreaterId((int) userid);
		fdCaseFeedback.setCreateTime(now);
		fdCaseFeedback.setUpdateTime(now);
		fdCaseFeedback.setNote(note);
		fdCaseFeedback.setEnclosure3(file3);
		fdCaseFeedback.setCreaterCompany(info.getKsMc());
		fdCaseFeedback.setCreaterRole(info.getZwMc());
		fdCaseFeedback.setCreaterDep(info.getDwMc());
		fdCaseFeedback.setDepId(info.getDwId());
		fdCaseFeedback.setCreater(info.getUserName());
		fdCaseFeedback.setEnclosure2(file2);
		fdCaseFeedback.setEnclosure1(file1);
		if (lastupdate != null) {
			fdCaseFeedback.setTaketime(DateUtils.getDatePoor(lastupdate, now));
		}
		// fdCaseFeedback.set

		fdCaseFeedbackAllRepository.saveAndFlush(fdCaseFeedback);
		
		FdCase fdCase = caseService.getFdCaseById(caseid);
		try {
			NodeVO nextnode =getcurrentnode(processid) ;
			if(nextnode!=null){
				if(nextnode.getCurrentUserInfo() != null){
					fdCase.setCurrentHandleUnitid(nextnode.getCurrentUserInfo().getDwId());//当前处理单位
					fdCase.setCurrentHandlePersonalid(nextnode.getCurrentUserInfo().getRyId());//当前处理人
				}
				else{
					// 流程已结束了
					fdCase.setCurrentHandleUnitid(nextnode.getEndUserInfo().getDwId());//当前处理单位
					fdCase.setCurrentHandlePersonalid(nextnode.getEndUserInfo().getRyId());//当前处理人
					fdCase.setCaseHandleState(StateConstants.HANDLE_HAS);
				}
				
				String taskname = nextnode.getTaskname();
//			if(taskname.contains("审")){
//				fdCase.setCurrentHandleState(HandleState.TODO_AUDIT.getName());
//			}else if(taskname.contains("操作")){
//				fdCase.setCurrentHandleState(HandleState.TODO_HANDLE.getName());
//			}
				fdCase.setCurrentHandleState(taskname);

			}
			if(taskstr.equals("usertask2")){
				if(approval==0){
					fdCase.setIsFlow((byte)0);
				}
			}
			fdCase.setHandleType(type);
			fdCase.setGmtModified(new Date());
			fdCaseRepository.save(fdCase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return info;
	}

	private LogInInfo getUserInfo(long userid) {
		LogInInfo info;
		Object obj = redisTemplate.opsForValue().get("user:" + userid);
		if (obj != null) {
			info = (LogInInfo) obj;
		} else {
			info = tzmPetitionService.selectLogInInfoByRyId((int) userid);
		}
		return info;
	}

	/*
	 * private FdCase getFdCase(int caseid) { Date now = new Date(); String
	 * title = "测试测试"+now.toLocaleString(); FdCase fdcase = new FdCase();
	 * fdcase.setId(1); fdcase.setPetitionNames("测试人员"+now.toLocaleString());
	 * fdcase.setCaseAddress("成都"+now.toLocaleString());
	 * fdcase.setCaseHandleState(1000);
	 * 
	 * return fdcase; }
	 */

	// 获得某个人的任务别表
	public List<Task> getTasks(String assignee) {
		List<Task> result = taskService.createTaskQuery().taskAssignee(assignee).list();
		for (Task task : result) {

		}
		return result;
	}

	// 完成任务
	public void completeTasks(Boolean joinApproved, String taskId, String personid) {
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("joinApproved", joinApproved);
		taskVariables.put("personId", personid);
		taskService.complete(taskId, taskVariables);
	}

	// 驳回
	/*
	 * public void completeTasks(Boolean joinApproved, String taskId) {
	 * Map<String, Object> taskVariables = new HashMap<String, Object>();
	 * taskVariables.put("joinApproved", joinApproved);
	 * taskService.complete(taskId, taskVariables); }
	 */
	public PageFinder<TaskVO> getTasks(String userid, int startpage, int pagesize, int state) {

		List<Task> result = null;
		long total = 0;
		
		if(state==0){
			result = taskService.createTaskQuery().taskCandidateOrAssigned(userid).listPage(startpage, pagesize);
			total = taskService.createTaskQuery().taskCandidateOrAssigned(userid).count();
			
			/*List<Task> test0 = taskService.createTaskQuery().processInstanceBusinessKey("jiaoban3").list();
			List<Task> test = taskService.createTaskQuery().processInstanceBusinessKeyLike("jiaoban3").list();*/
			/*List<Task> test1 = taskService.createTaskQuery().processDefinitionKeyLike("jiaoban3").list();
			List<Task> test2 = taskService.createTaskQuery().processDefinitionNameLike("jiaoban3").list();*/

		}else if(state==1){//交办
			result = taskService.createTaskQuery().taskCandidateOrAssigned(userid).processDefinitionKeyLike("jiaoban3").listPage(startpage, pagesize);
			total = taskService.createTaskQuery().taskCandidateOrAssigned(userid).processDefinitionKeyLike("jiaoban3").count();
			
		}else if(state==2){
			
			result = taskService.createTaskQuery().taskCandidateOrAssigned(userid).processDefinitionKeyLike("zhuanban2").listPage(startpage, pagesize);
			total = taskService.createTaskQuery().taskCandidateOrAssigned(userid).processDefinitionKeyLike("zhuanban").count();
		}else if(state==3){
			result = taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskNameLikeIgnoreCase("审").listPage(startpage, pagesize);
			total = taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskNameLikeIgnoreCase("审").count();
		}else if(state==4){
			result = taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskNameLikeIgnoreCase("操作").listPage(startpage, pagesize);
			total = taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskNameLikeIgnoreCase("操作").count();
			
		}else if(state==5){
			result = taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskNameLikeIgnoreCase("操作").listPage(startpage, pagesize);
			total = taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskNameLikeIgnoreCase("操作").count();
			
		}else{
			throw new BizException("请传入指定的状态");
		}

		List<TaskVO> vos = new ArrayList<TaskVO>();
		long startTime=System.currentTimeMillis();
		List<FdCode> petitionWay = fdCodeRepository.findByTypeAndState(12, 1); //来访方式
		List<FdCode> questBelongTo = fdCodeRepository.findByTypeAndState(15, 1); //来访方式
		for (Task task : result) {
			try {
				TaskVO vo = new TaskVO();
				Integer needReceipt = 1;
				Integer caseid = (Integer) taskService.getVariable(task.getId(), "caseid");
				FdCase fdcase = caseService.getFdCaseById(caseid);
			//	FdCase fdcase = (FdCase) taskService.getVariable(task.getId(), "fdCase");
				
				fdcase.setStrPetitionWayandStrQuestionBelongTo(petitionWay,questBelongTo);
				Object formdate = taskService.getVariable(task.getId(), "formdate");
				try {
					needReceipt = Integer.valueOf((String) taskService.getVariable(task.getId(), "needReceipt"));
				} catch (Exception e) {
					needReceipt = 1;
				}
				vo.setFdCase(fdcase);
				vo.setTaskId(task.getId());
				vo.setNeedReceipt(needReceipt);
				vo.setTaskName(task.getName());
				vo.setTaskDefinitionKey(task.getTaskDefinitionKey());
				vo.setProcDefId(task.getProcessDefinitionId());
				vo.setProInsId(task.getProcessInstanceId());

				//List<Comment> comments = getProcessComments(task.getId());
				vo.setComments(null);
				if (fdcase.getGuestId() != null) {
					FdGuest fdGuest = identityService.getGuestByIdForUrl(fdcase.getGuestId());
					fdGuest.setFdCaseList(null);
					vo.setGuest(fdGuest);
				}

				vo.setFormdate(formdate);

				vos.add(vo);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		long endTime=System.currentTimeMillis();
		
		System.out.println("程序运行时间： "+(endTime-startTime)/1000+" 秒");
		PageFinder<TaskVO> page = new PageFinder<TaskVO>(startpage, pagesize, (int) total, vos);
		return page;
	}
	
	public PageFinder<TaskVO> getreceivecase(String userid, int startpage, int pagesize, int state) {

		List<Task> result = null;
		long   total = taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskNameLikeIgnoreCase("%收文%").count();
		//查询 app录入的  
		 List<FdCase>  cases = getCaseFromApp(userid, startpage, pagesize);
		
		
		/*long all=*/
		result = taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskNameLikeIgnoreCase("%收文%").listPage(startpage, pagesize);
			

		List<TaskVO> vos = new ArrayList<TaskVO>();
		long startTime=System.currentTimeMillis();
		List<FdCode> petitionWay = fdCodeRepository.findByTypeAndState(12, 1); //来访方式
		List<FdCode> questBelongTo = fdCodeRepository.findByTypeAndState(15, 1); //来访方式
		for(FdCase fdcase:cases){
			TaskVO vo = new TaskVO();
			vo.setFdCase(fdcase);
			vos.add(vo);
		}
		for (Task task : result) {
			try {
				TaskVO vo = new TaskVO();
				Integer needReceipt = 1;
				Integer caseid = (Integer) taskService.getVariable(task.getId(), "caseid");
				FdCase fdcase = caseService.getFdCaseById(caseid);
			//	FdCase fdcase = (FdCase) taskService.getVariable(task.getId(), "fdCase");
				
				fdcase.setStrPetitionWayandStrQuestionBelongTo(petitionWay,questBelongTo);
				Object formdate = taskService.getVariable(task.getId(), "formdate");
				try {
					needReceipt = Integer.valueOf((String) taskService.getVariable(task.getId(), "needReceipt"));
				} catch (Exception e) {
					needReceipt = 1;
				}
				vo.setFdCase(fdcase);
				vo.setTaskId(task.getId());
				vo.setNeedReceipt(needReceipt);
				vo.setTaskName(task.getName());
				vo.setTaskDefinitionKey(task.getTaskDefinitionKey());
				vo.setProcDefId(task.getProcessDefinitionId());
				vo.setProInsId(task.getProcessInstanceId());

				//List<Comment> comments = getProcessComments(task.getId());
				vo.setComments(null);
				if (fdcase.getGuestId() != null) {
					FdGuest fdGuest = identityService.getGuestByIdForUrl(fdcase.getGuestId());
					fdGuest.setFdCaseList(null);
					vo.setGuest(fdGuest);
				}

				vo.setFormdate(formdate);

				vos.add(vo);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		long endTime=System.currentTimeMillis();
		
		
		
		System.out.println("程序运行时间： "+(endTime-startTime)/1000+" 秒");
		PageFinder<TaskVO> page = new PageFinder<TaskVO>(startpage, pagesize, (int) total, vos);
		return page;
	}

	private List<FdCase> getCaseFromApp(String userid, int startpage, int pagesize) {
		long total;
		LogInInfo user = getUserInfo(Long.parseLong(userid));
		PredicateBuilder<FdCase> build =Specifications.<FdCase>and();
		build.eq("createUnitid", user.getDwId()).eq("isDispatcherReceive", 0).eq("isHandle", 0);
		Specification<FdCase> spec=	build.build();
		
		 Sort sort = Sorts.builder()
			        .desc("gmtCreate")
			        /*.asc("birthday")*/
			        .build();
		
		PageRequest pagerequest =		 new PageRequest(startpage-1, pagesize, sort);
		
 

     Page<FdCase> cases=fdCaseRepository.findAll(spec,pagerequest);
		return cases.getContent();
	}

	  

	public void completetask(String taskid, int approval, String files, String note, int userId,String ryid) {

		
		Map<String, Object> taskVariables = taskService.getVariables(taskid);
		Task tt=taskService.createTaskQuery().taskId(taskid).singleResult();
		taskVariables.put("approval", approval);
		if (ryid != "" && ryid != null)
			taskVariables.put("createrLeader", ryid);
		if (approval == 0) {// 审批不通过
			FdCase fdcase = (FdCase) taskVariables.get("fdCase");
			fdcase.setCaseHandleState(StateConstants.FLOW_TODO);
			// fdcase.update(); usertask2
			
		}
		String processid=null;
		Object processidobj=taskVariables.get("processid");
		if (approval == 1) {//要取下一步的人员
			addcomment(taskid, files, note, userId, 2001);
		} else {
			addcomment(taskid, files, note, userId, 2002);
		}
		LogInInfo userinfo =getUserInfo(userId);
		if (approval == 1) {//要取下一步的人
			addformdate(2001, files, userId, null, null, taskVariables, userinfo, note, null, null);
		}else{
			addformdate(2002, files, userId, null, null, taskVariables, userinfo, note, null, null);
		}
		taskService.complete(taskid, taskVariables);
		
		if(processidobj!=null){
			processid=processidobj.toString();
		}
		if (approval == 1) {//要取下一步的人员
			addFeedBackoth(userId, (Integer) taskVariables.get("caseid"), 2001, note, files, null, null,processid,tt.getTaskDefinitionKey(),approval);// 添加反馈信息
		} else {
			 addFeedBackoth(userId, (Integer) taskVariables.get("caseid"), 2002, note, files, null, null,processid,tt.getTaskDefinitionKey(),approval);
		}
           

	}

	private void addcomment(String taskid, String files, String note, int userid, int type) {
		Date now = new Date();
		LogInInfo info = null;
		Object obj = redisTemplate.opsForValue().get("user:" + userid);
		if (obj != null) {
			info = (LogInInfo) obj;
		} else {
			info = tzmPetitionService.selectLogInInfoByRyId(userid);
		}
		JsonObject result = new JsonObject();
		result.addProperty("usname", info.getUserName());
		result.addProperty("userid", userid);
		result.addProperty("createtime", now.toLocaleString());
		result.addProperty("files", files);
		result.addProperty("note", note);
		result.addProperty("type", type);
		result.addProperty("pic", info.getUserImg());
		result.addProperty("typename", HandleState.getName(type));
		Authentication.setAuthenticatedUserId(info.getUserName());
		taskService.addComment(taskid, null, result.toString());
	}

	/**
	 * 
	 * @param userid
	 * @description 查看已经完成的流程
	 * @author ZhangHuaRong
	 * @update 2017年3月30日 上午9:47:44
	 */
	public List<HistoryTaskVO> finishtask(String userid, int startpage, int pagesize) {
		List<HistoricProcessInstance> datas = historyService.createHistoricProcessInstanceQuery().involvedUser(userid)
				.finished().list();

		return null;
	}

	/**
	 * 
	 * @param userid
	 * @description 查看正在进行的流程
	 * @author ZhangHuaRong
	 * @update 2017年3月30日 上午9:47:44
	 */
	public List<HistoryTaskVO> unfinishtask(String userid, int startpage, int pagesize) {
		List<HistoricProcessInstance> datas = historyService.createHistoricProcessInstanceQuery().involvedUser(userid)
				.unfinished().list();

		return null;
	}

	// http://www.tuicool.com/articles/niYzeu
	public PageFinder<HistoryTaskVO> historytask(String userid, int startpage, int pagesize) {
		/*
		 * List<HistoricVariableInstance> list =
		 * processEngine.getHistoryService()
		 * .createHistoricVariableInstanceQuery()//创建一个历史的流程变量查询对象
		 * .variableName("请假原因") .list();
		 */
		/*
		 * List<HistoricTaskInstance> results =
		 * historyService.createHistoricTaskInstanceQuery().finished()
		 * .taskInvolvedUser(userid).listPage(startpage, pagesize);
		 */

		List<HistoricProcessInstance> lists = historyService.createHistoricProcessInstanceQuery().finished()
				.involvedUser(userid).listPage(startpage, pagesize);
		long total = historyService.createHistoricProcessInstanceQuery().unfinished().involvedUser(userid).count();

		List<HistoryTaskVO> vos = new ArrayList<HistoryTaskVO>();

		// .involvedUser(userid)
		for (HistoricProcessInstance task : lists) {

			List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()// 创建一个历史的流程变量查询对象
					.processInstanceId(task.getId())//
					.list();

			for (HistoricVariableInstance hvi : list) {

				if (hvi.getVariableName().equals("fdCase")) {

					List<HistoricActivityInstance> list1 = historyService// 历史任务Service
							.createHistoricActivityInstanceQuery() // 创建历史活动实例查询
							.processInstanceId(task.getId()) // 指定流程实例id
							.finished().orderByHistoricActivityInstanceStartTime().desc().list();

					HistoryTaskVO vo = new HistoryTaskVO(null, (FdCase) hvi.getValue());
					TaskVO currenttask = getCurrentTask(task.getId());
					FdCase fdcase = (FdCase) hvi.getValue();
					FdGuest fdGuest = identityService.getGuestById(fdcase.getGuestId());
					currenttask.setGuest(fdGuest);
					vo.setTaskVO(currenttask);

					for (HistoricActivityInstance hai : list1) {
						if (hai.getAssignee() != null) {
							LogInInfo endinfo = getUserInfo(Long.parseLong(hai.getAssignee()));
							vo.setEndpeople(endinfo);
							break;
						}
					}

					vos.add(vo);
				}
			}
		}

		PageFinder<HistoryTaskVO> page = new PageFinder<HistoryTaskVO>(startpage, pagesize, (int) total, vos);

		return page;
	}

	// http://www.tuicool.com/articles/bM7Rre 查看流程图
	// http://www.massapi.com/class/org/activiti/image/ProcessDiagramGenerator.html
	public InputStream getDiagram(String processinstanceId) {
		/*
		 * ProcessDefinition processDefinition =
		 * repositoryService.createProcessDefinitionQuery()
		 * .processDefinitionId(deploymentId).singleResult();
		 */

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processinstanceId).singleResult();

		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processinstanceId).singleResult();

		if (processInstance == null && historicProcessInstance == null) {
			return null;
		}

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processInstance == null ? historicProcessInstance.getProcessDefinitionId()
						: processInstance.getProcessDefinitionId());

		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

		List<String> activeActivityIds = new ArrayList<String>();

		if (processInstance != null) {
			activeActivityIds = runtimeService.getActiveActivityIds(processInstance.getProcessInstanceId());
		} else {
			activeActivityIds.add(historyService.createHistoricActivityInstanceQuery()
					.processInstanceId(processinstanceId).activityType("endEvent").singleResult().getActivityId());
		}

		List<String> highLightedFlows = getHighLightedFlows(processDefinition, processinstanceId);

		ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
		InputStream iss = processDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds, highLightedFlows,
				"宋体", "宋体", "宋体", null, 1.0);

		return iss;
	}

	private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinition, String processinstanceId) {
		List<String> highLightedFlows = new ArrayList<String>();

		List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processinstanceId).orderByHistoricActivityInstanceStartTime()/**
																								 * 按创建时间排序，
																								 * 不能按结束时间排序，否则会有连线丢失<按结束时间排序的话，如果流程没有结束，则流程的当前的活动节点永远是排在第一个或最后一个>
																								 */
				.asc().list();

		LinkedList<HistoricActivityInstance> hisActInstList = new LinkedList<HistoricActivityInstance>();

		hisActInstList.addAll(historicActivityInstances);
		getHighlightedFlows(processDefinition.getActivities(), hisActInstList, highLightedFlows);

		return highLightedFlows;
	}

	private void getHighlightedFlows(List<ActivityImpl> activityList,
			LinkedList<HistoricActivityInstance> hisActInstList, List<String> highLightedFlows) {

		List<ActivityImpl> startEventActList = new ArrayList<ActivityImpl>();
		Map<String, ActivityImpl> activityMap = new HashMap<String, ActivityImpl>(activityList.size());

		for (ActivityImpl activity : activityList) {

			activityMap.put(activity.getId(), activity);

			String actType = (String) activity.getProperty("type");
			if (actType != null && actType.toLowerCase().indexOf("startevent") >= 0) {
				startEventActList.add(activity);
			}
		}

		HistoricActivityInstance firstHistActInst = hisActInstList.getFirst();
		String firstActType = firstHistActInst.getActivityType();
		if (firstActType != null && firstActType.toLowerCase().indexOf("startevent") < 0) {
			PvmTransition startTrans = getStartTransaction(startEventActList, firstHistActInst);
			if (startTrans != null) {
				highLightedFlows.add(startTrans.getId());
			}
		}

		while (!hisActInstList.isEmpty()) {
			HistoricActivityInstance histActInst = hisActInstList.removeFirst();
			ActivityImpl activity = activityMap.get(histActInst.getActivityId());
			if (activity != null) {
				boolean isParallel = false;
				String type = histActInst.getActivityType();
				if ("parallelGateway".equals(type) || "inclusiveGateway".equals(type)) {
					isParallel = true;
				} else if ("subProcess".equals(histActInst.getActivityType())) {
					getHighlightedFlows(activity.getActivities(), hisActInstList, highLightedFlows);
				}

				List<PvmTransition> allOutgoingTrans = new ArrayList<PvmTransition>();
				allOutgoingTrans.addAll(activity.getOutgoingTransitions());
				allOutgoingTrans.addAll(getBoundaryEventOutgoingTransitions(activity));
				List<String> activityHighLightedFlowIds = getHighlightedFlows(allOutgoingTrans, hisActInstList,
						isParallel);
				highLightedFlows.addAll(activityHighLightedFlowIds);
			}
		}

	}

	private List<String> getHighlightedFlows(List<PvmTransition> pvmTransitionList,
			LinkedList<HistoricActivityInstance> hisActInstList, boolean isParallel) {

		List<String> highLightedFlowIds = new ArrayList<String>();

		PvmTransition earliestTrans = null;
		HistoricActivityInstance earliestHisActInst = null;

		for (PvmTransition pvmTransition : pvmTransitionList) {

			String destActId = pvmTransition.getDestination().getId();
			HistoricActivityInstance destHisActInst = findHisActInst(hisActInstList, destActId);

			if (destHisActInst != null) {
				if (isParallel) {
					highLightedFlowIds.add(pvmTransition.getId());
				} else /*
						 * if (earliestHisActInst == null ||
						 * (earliestHisActInst.getId().compareTo(
						 * destHisActInst.getId()) > 0))
						 */

				if (destHisActInst.getActivityId().equals(hisActInstList.get(0).getActivityId())) {
					/** 解决互斥网关默认只显示一条流程连线问题，此处用互斥网关流出连线的目标活动节点与历史活动节点的第一个节点判断，取出它们之间的连线 */
					earliestTrans = pvmTransition;
					earliestHisActInst = destHisActInst;
				}
			}
		}

		if ((!isParallel) && earliestTrans != null) {
			highLightedFlowIds.add(earliestTrans.getId());
		}

		return highLightedFlowIds;
	}

	private HistoricActivityInstance findHisActInst(LinkedList<HistoricActivityInstance> hisActInstList, String actId) {
		for (HistoricActivityInstance hisActInst : hisActInstList) {
			if (hisActInst.getActivityId().equals(actId)) {
				return hisActInst;
			}
		}
		return null;
	}

	private List<PvmTransition> getBoundaryEventOutgoingTransitions(ActivityImpl activity) {
		List<PvmTransition> boundaryTrans = new ArrayList<PvmTransition>();
		for (ActivityImpl subActivity : activity.getActivities()) {
			String type = (String) subActivity.getProperty("type");
			if (type != null && type.toLowerCase().indexOf("boundary") >= 0) {
				boundaryTrans.addAll(subActivity.getOutgoingTransitions());
			}
		}
		return boundaryTrans;
	}

	private PvmTransition getStartTransaction(List<ActivityImpl> startEventActList,
			HistoricActivityInstance firstActInst) {
		for (ActivityImpl startEventAct : startEventActList) {
			for (PvmTransition trans : startEventAct.getOutgoingTransitions()) {
				if (trans.getDestination().getId().equals(firstActInst.getActivityId())) {
					return trans;
				}
			}
		}
		return null;
	}

	public String handletask(String processid, String taskid, int type, String files, String note, Integer depid,
			Integer delayDay, int userId, String files1, String files2,Integer shrid) {
		StringBuffer sb = new StringBuffer();
		Map<String, String> format = null;
		Map<String, Object> taskVariables = taskService.getVariables(taskid);
		taskVariables.put("type", type);
		Integer caseId = (Integer) taskVariables.get("caseid");
		LogInInfo userinfo = null;
		userinfo = getUserInfo(userId);
		

		if (type == 1 || type == 2) {// 1 交办 2转办 3受理 4直接回复 5 不予受理 6 不在受理 7直接存档 8退回 9 申请办结 10
			FcDwb fcDwb = fcDwbRepository.findOne(depid);
			FdCase fdCase = caseService.getFdCaseById(caseId);
			 Map<String, Object> map = baseTreeService.getPersonCaseType3(depid, fdCase.getPetitionWay());
	         if(map==null){
	        	 throw new BizException(10015,"案件转办失败！"+fcDwb.getDwMc()+"没有设置收文岗。");
	         }else{
	        	 if(map.get("FH_RY_FJID").toString().equals("-1")){
	        		 StringBuffer sb1 = new StringBuffer();
	        		 sb1.append(map.get("DW_MC").toString()).append(",").append(map.get("RY_MC").toString()).append("没有分管领导");
	         		throw	 new BizException(10003, sb1.toString());
		        }
	        	if( map.get("FH_RY_ID").toString().equals("0")){
	        		 throw new BizException(10015,"案件转办失败！"+fcDwb.getDwMc()+"没有设置收文岗。");
	        	}
	        	
	        	 
	         }
			
			taskVariables.put("depid", depid);
			if (taskVariables.get("dep1") == null) {
				taskVariables.put("dep1", depid);
			} else if (taskVariables.get("dep2") == null) {
				taskVariables.put("dep2", depid);
			} else if (taskVariables.get("dep3") == null) {
				taskVariables.put("dep3", depid);
			}

//		} else if (type == 2) {
//			taskVariables.put("depid", depid);

		} else if (type == 10) {
			taskVariables.put("delayDay", delayDay);
			taskVariables.put("mydepid", userinfo.getDwId());
		}
		taskVariables.put("istaskReturn", 0);// help sunbx
		taskVariables.put("staffleader", shrid);
		
		  // bug #304 需要
		if(type==1||type==2){
			  updateFdCaseOth(caseId,depid);
		}
      

		try {
			format = addformdate(type, files, userId, files1, files2, taskVariables, userinfo, note, depid, delayDay);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		addcomment(taskid, files, note, userId, type);
		
		
		taskService.complete(taskid, taskVariables);
		 
		addFeedBack(userId, caseId, type, note, files, files1, files2,processid);// 添加反馈信息
		try {
			if (type == 1 || type == 2) {
				sb.append("提交成功！已提交").append(format.get("receivedep")).append("处理");
			} else {
				TaskVO vo = getCurrentTask(processid);
				LogInInfo info = vo.getUserinfo();
			 //   updateFdCase(caseId,depid,"",userId,info);
				if (info != null) {
					sb.append("提交成功！已提交").append("[").append(info.getDwMc()).append("]").append(info.getUserName())
							.append("处理");
				} else {
					sb.append("提交成功！已提交");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	

	private Map<String, String> addformdate(int type, String files, int userId, String files1, String files2,
			Map<String, Object> taskVariables, LogInInfo userinfo, String note, Integer depid, Integer delayDay) {
		Map<String, String> formdate = new HashMap<String, String>();// 流程表单数据
         if(files!=null)
		formdate.put("files", files);
         if(files1!=null)
		formdate.put("files1", files1);
         if(files2!=null)
		formdate.put("files2", files2);
         if(note!=null)
		formdate.put("note", note);
		if (type == 10) {
			formdate.put("applydep", userinfo.getDwId().toString());
			if (delayDay != null) {
				formdate.put("delayDay", delayDay.toString());
			}
		}
		if(depid!=null){
			String todepname = fcDwbRepository.findDwMcAndDwIdByDwId(depid);
			formdate.put("receivedep", todepname);
		}
		
		
		formdate.put("createtime", new Date().toLocaleString());
		formdate.put("type", type + "");
		if(type!=2001||type!=2002){
			formdate.put("typename", HandleState.getName(type));
	
		}

		formdate.put("createtime", new Date().toLocaleString());
		formdate.put("username", userinfo.getUserName());
		formdate.put("depname", userinfo.getDwMc());
		formdate.put("ksname", userinfo.getKsMc());
		formdate.put("qxsname", userinfo.getQxsMc());
		
		
		formdate.put("userid", userId + "");
		formdate.put("pic", userinfo.getUserImg());
		taskVariables.put("formdate", formdate);
		return formdate;
	}
	
	private Map<String, String> addformdatestart(int type, String files, int userId, String files1, String files2,
			Map<String, Object> taskVariables, LogInInfo userinfo, String note, Integer depid, Integer delayDay) {
		Map<String, String> formdate = new HashMap<String, String>();// 流程表单数据

		formdate.put("files", files);
		formdate.put("files1", files1);
		formdate.put("files2", files2);
		formdate.put("note", note);
		if (type == 10) {
			formdate.put("applydep", userinfo.getDwId().toString());
			if (delayDay != null) {
				formdate.put("delayDay", delayDay.toString());
			}
		}
		String todepname = fcDwbRepository.findDwMcAndDwIdByDwId(depid);
		formdate.put("createtime", new Date().toLocaleString());
		formdate.put("type", type + "");
		formdate.put("typename", HandleState.getName(type));

		formdate.put("createtime", new Date().toLocaleString());
		formdate.put("username", userinfo.getUserName());
		formdate.put("depname", userinfo.getDwMc());
		formdate.put("ksname", userinfo.getKsMc());
		formdate.put("qxsname", userinfo.getQxsMc());
		formdate.put("receivedep", todepname);
		
		formdate.put("userid", userId + "");
		formdate.put("pic", userinfo.getUserImg());
		taskVariables.put("createDep", userinfo.getDwId());
		taskVariables.put("SecondDep", depid);
		taskVariables.put("formdate", formdate);
		return formdate;
	}

	public void forwardtask(int myid, String taskid, int userid, String files, String note) {
		Map<String, Object> taskVariables = taskService.getVariables(taskid);
		// Integer depid = (Integer)
		// taskVariables.get("depid");getLeaderIdByRyid
		List<Integer> leaders = tzmPetitionService.getLeaderIdByRyid(userid);
		if (leaders.isEmpty()) {
			throw BizException.NO_LEADER;
		}
		try {
			// taskService
			Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
			if (task.getProcessDefinitionId().toString().startsWith("jiaoban")) {
				if (!taskVariables.containsKey("staff")) {
					taskVariables.put("staff", userid);
					taskVariables.put("staffleader", leaders.get(0).toString());
				} else if (!taskVariables.containsKey("staff1")) {
					taskVariables.put("staff1", userid);
					taskVariables.put("staffleader1", leaders.get(0).toString());
				} else if (!taskVariables.containsKey("staff2")) {
					taskVariables.put("staff2", userid);
					taskVariables.put("staffleader2", leaders.get(0).toString());
				} else if (!taskVariables.containsKey("staff3")) {
					taskVariables.put("staff3", userid);
					taskVariables.put("staffleader3", leaders.get(0).toString());
				}
			} else {
				taskVariables.put("staff", userid);
				taskVariables.put("staffleader", leaders.get(0).toString());
			}
		} catch (Exception e) {
			ApiLog.log(e.getMessage());
			e.printStackTrace();
		}
		String processid=null;
		Object processidobj=taskVariables.get("processid");
		if(processidobj!=null){
			processid=processidobj.toString();
		}

		addcomment(taskid, files, note, myid, 2003);
		Integer total = null;// 以后计算出来
		taskService.complete(taskid, taskVariables);
		LogInInfo userinfo = addFeedBack(myid, (Integer) taskVariables.get("caseid"), 2003, note, files, null, null,processid);// 添加反馈信息
		addformdate(2003, files, myid, null, null, taskVariables, userinfo, note, 0, total);
		 updateFdCaseFord((Integer) taskVariables.get("caseid"),userinfo.getDwId(),userid);

	}

	

	public List<Comment> getProcessComments(String taskId) {
		List<Comment> historyCommnets = new ArrayList<>();
		// 1) 获取流程实例的ID
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId())
				.singleResult();
		// 2）通过流程实例查询所有的(用户任务类型)历史活动
		List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(pi.getId()).activityType("userTask").list();
		// 3）查询每个历史任务的批注
		for (HistoricActivityInstance hai : hais) {
			String historytaskId = hai.getTaskId();
			List<Comment> comments = taskService.getTaskComments(historytaskId);
			// 4）如果当前任务有批注信息，添加到集合中
			if (comments != null && comments.size() > 0) {
				historyCommnets.addAll(comments);
			}
		}
		// 5）返回
		return historyCommnets;
	}

	public TaskVO getTask(String taskid) {
		TaskVO vo = null;
		try {
			Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
			vo = new TaskVO();
			Object obj = taskService.getVariable(taskid, "formdate");
			FdCase fdcase = (FdCase) taskService.getVariable(task.getId(), "fdCase");
			vo.setFdCase(fdcase);
			vo.setTaskId(task.getId());
			vo.setTaskName(task.getName());
			vo.setTaskDefinitionKey(task.getTaskDefinitionKey());
			vo.setProcDefId(task.getProcessDefinitionId());
			vo.setProInsId(task.getProcessInstanceId());
			List<Comment> comments = getProcessComments(task.getId());
			vo.setComments(comments);
			vo.setFormdate(obj);
			FdGuest fdGuest = identityService.getGuestById(fdcase.getGuestId());
			vo.setGuest(fdGuest);
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
		return vo;
	}

	public PageFinder<HistoryTaskVO> historytaskin(String userid, int startpage, int pagesize) {
		/*
		 * List<HistoricTaskInstance> results =
		 * historyService.createHistoricTaskInstanceQuery().unfinished()
		 * .taskInvolvedUser(userid).listPage(startpage, pagesize);
		 */
		/*
		 * long total=
		 * historyService.createHistoricTaskInstanceQuery().unfinished()
		 * .taskInvolvedUser(userid).count();
		 */

		List<HistoricProcessInstance> lists = historyService.createHistoricProcessInstanceQuery().unfinished()
				.involvedUser(userid).listPage(startpage, pagesize);
		long total = historyService.createHistoricProcessInstanceQuery().unfinished().involvedUser(userid).count();

		List<HistoryTaskVO> vos = new ArrayList<HistoryTaskVO>();

		// .involvedUser(userid)
		for (HistoricProcessInstance task : lists) {
			// String lastActivitiHandleUserName=task.getStartUserId();
			List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(task.getId()).list();

			for (HistoricVariableInstance hvi : list) {
				System.out.println(hvi.getId() + "   " + hvi.getProcessInstanceId() + "   " + hvi.getVariableName()
						+ "   " + hvi.getVariableTypeName() + "    " + hvi.getValue());
				System.out.println("###############################################");
				if (hvi.getVariableName().equals("fdCase")) {
					HistoryTaskVO vo = new HistoryTaskVO(null, (FdCase) hvi.getValue());
					TaskVO currenttask = getCurrentTask(task.getId());
					vo.setTaskVO(currenttask);
					FdGuest fdGuest = identityService.getGuestById(currenttask.getFdCase().getGuestId());
					currenttask.setGuest(fdGuest);
					vos.add(vo);

				}
			}
		}
		PageFinder<HistoryTaskVO> page = new PageFinder<HistoryTaskVO>(startpage, pagesize, (int) total, vos);

		return page;
	}

	public TaskVO getCurrentTask(String processInstanceId) {
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		// taskService

		TaskVO vo = new TaskVO();
		if (task == null) {
			List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
			if(!list.isEmpty()){
				for(HistoricVariableInstance var:list){
					if(var.getVariableName().equals("caseid")){
						FdCase fdCase = caseService.getFdCaseById(Integer.parseInt(var.getValue().toString()));
						vo.setFdCase(fdCase);
						vo.setCaseHandleState(fdCase.getCaseHandleState());
						break;
					}
				}
			}
			
			return vo;
		}
		LogInInfo info = null;
		List<LogInInfo> infos = new ArrayList<LogInInfo>();

		if (task.getAssignee() != null) {
			Object userinfo = redisTemplate.opsForValue().get("user:" + task.getAssignee());
			if (userinfo != null) {
				info = (LogInInfo) userinfo;
			} else {
				info = tzmPetitionService.selectLogInInfoByRyId(Integer.parseInt(task.getAssignee()));
			}
		} else {
			// 获取收文岗人员
			List<IdentityLink> lt = taskService.getIdentityLinksForTask(task.getId());
			for (IdentityLink identityLink : lt) {
				String tempUserId = identityLink.getUserId();
				System.out.println("**** " + identityLink.getUserId() + " ****");
				if (tempUserId != null) {
					infos.add(tzmPetitionService.selectLogInInfoByRyId(Integer.parseInt(tempUserId)));
				}

			}
		}

		Object obj = taskService.getVariable(task.getId(), "formdate");
		Object needReceipt = taskService.getVariable(task.getId(), "needReceipt");
		FdCase fdcase = (FdCase) taskService.getVariable(task.getId(), "fdCase");
		vo.setFdCase(fdcase);
		vo.setTaskId(task.getId());
		vo.setTaskName(task.getName());
		vo.setTaskDefinitionKey(task.getTaskDefinitionKey());
		vo.setProcDefId(task.getProcessDefinitionId());
		vo.setProInsId(task.getProcessInstanceId());
		List<Comment> comments = getProcessComments(task.getId());
		vo.setComments(comments);
		vo.setFormdate(obj);
		vo.setUserinfo(info);
		vo.setSwgUserList(infos);
		vo.setCaseHandleState(fdcase.getCaseHandleState());
		vo.setNeedReceipt(Integer.parseInt(needReceipt.toString()));
		return vo;

	}
    /**
     * 
     * @param caseid
     * @return
     * @description 根据案件id获取当前处理人处理单位等信息
     * @version 1.0
     * @author ZhangHuaRong
     * @update 2017年5月4日 下午5:04:22
     */
	public NodeVO getcurrentnode(String processInstanceId) {
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		LogInInfo info = null;
		Long caseid=null;
		
		
			if(task==null){
				 List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
						    .processInstanceId(processInstanceId )
						    .orderByHistoricActivityInstanceEndTime().desc()
						    .list();
				 if(hais.isEmpty()){
					 return null;
				 }
				 for (HistoricActivityInstance hai : hais) {
					   if(hai.getAssignee()!=null){
						   info = getUserInfo(Long.parseLong(hai.getAssignee()));
						   break;
					   }
					  }
				 
				   NodeVO result = new NodeVO();
					result.setState(0);
					result.setEndUserInfo(info);
					result.setWarn("已完结");
				   /*List<HistoricDetail> hisvar=	historyService.createHistoricDetailQuery().processInstanceId(processInstanceId).list();;
				   for(HistoricDetail var:hisvar){
					   HistoricVariableUpdate variable = (HistoricVariableUpdate) var;
					   if(variable.getVariableName().equals("caseid")){
						   caseid = Long.parseLong(variable.getValue().toString());
						   break;
					   }
				   }*/
				   //List<FdDepCase> findByCaseId(long id);
				   
				return result;
			}
			caseid=Long.parseLong(taskService.getVariable(task.getId(), "caseid").toString());
			if(task.getAssignee()!=null){
				System.out.println("流程当前处理人："+task.getAssignee());
				info = getUserInfo(Long.parseLong(task.getAssignee()));
			}else{
			  List<IdentityLink> links = 	taskService.getIdentityLinksForTask(task.getId());
			  for(IdentityLink link:links){
				  System.out.println("当前流程处理人1："+link.getUserId());
				  info = getUserInfo(Long.parseLong(link.getUserId()));
				  break;
			  }
			}
			NodeVO result = new NodeVO();
			result.setState(1);
			result.setCurrentUserInfo(info);
			result.setTaskname(task.getName());
			
			List<FdDepCase> depcases=fdDepCaseRepository.findByCaseIdAndState(caseid, 1);
			if(!depcases.isEmpty()){
				FdDepCase current = depcases.get(0);
				result.setStartTime(DateUtils.formatDateTime(current.getCreateTime()));
				result.setLimitTime(DateUtils.formatDateTime(current.getEndTime()));
				result.setPastDay(DateUtils.getDatePoor(current.getCreateTime(),new Date()));
				result.setLimitdaynum(current.getLimitTime());
				if(current.getFlag3()!=null){
					result.setWarn("超期100%");
				}else if(current.getFlag2()!=null){
					result.setWarn("已过75%");
				}else if(current.getFlag1()!=null){
					result.setWarn("已过50%");
				}else {
					result.setWarn("正常");
				}
			}
			
			
		return result;
	}

	public FdWarn save(FdWarn log) {
		jdbcTemplate.update("INSERT INTO fd_warn(create_time, sender, receiver, state,msg,case_id,level,create_dep,receive_dep) VALUES(?, ?, ?, ?,?,?,?,?,?)", new Object[] {log.getCreateTime(), log.getSender(), log.getReceiver(), 1,log.getMsg(),log.getCaseId(),log.getLevel(),log.getCreateDep(),log.getReceiveDep()}); 
		return log;
	}
	
	public FdDepCase saveFdDepCase(FdDepCase log) {
		int result=jdbcTemplate.update("update fd_dep_case set flag1=?,flag2=?,flag3=? where id=?", new Object[] { log.getFlag1(),log.getFlag2(),log.getFlag3(),log.getId()}); 
		return log;
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public FdCase saveCase(FdCase log,int i) {
		try {
			int result=jdbcTemplate.update("update fd_case set is_flow=? where id=?", new Object[] { log.getIsFlow(),log.getId()});
		} catch (Exception e) {
			e.printStackTrace();
			if(i<4){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				saveCase(log,i++);
			}
		} 
		return log;
	}

	public String completessigen(long userid, int caseid, int leaderid, int type, int i, int depid, String files,
			String note, String xbdepinfo, int handleday, int needReceipt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void transferAssignee(String myid,String taskid, String userid,Integer caseid,String note,String file,String processid) {
		taskService.setAssignee(taskid, userid);  
		addFeedBack(Long.parseLong(myid), caseid, HandleState.TRANS.getValue(), note, file, null, null,processid);// 添加反馈信息
	
	}

	public void addsign(String myid, String taskid, String userid, Integer caseid, String note, String file,
			String processid) {
		taskService.setAssignee(taskid, userid);  
		addFeedBack(Long.parseLong(myid), caseid, HandleState.ADD_SING.getValue(), note, file, null, null,processid);// 添加反馈信息

		
	}
	
	
	/**
	 * 根据当前任务ID，查询可以驳回的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 */
	public List<ActivityImpl> findBackAvtivity(String taskId) throws Exception {
		List<ActivityImpl> rtnList = null;
		/*if (processOtherService.isJointTask(taskId)) {// 会签任务节点，不允许驳回
			rtnList = new ArrayList<ActivityImpl>();
		} else {*/
			rtnList = iteratorBackActivity(taskId, findActivitiImpl(taskId,
					null), new ArrayList<ActivityImpl>(),
					new ArrayList<ActivityImpl>());
		//}
		return reverList(rtnList);
	}
	
	
	/**
	 * 驳回流程
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            驳回节点ID
	 * @param variables
	 *            流程存储参数
	 * @throws Exception
	 */
	public void backProcess(String taskId, String activityId,
			Map<String, Object> variables) throws Exception {
		if (StringUtils.isBlank(activityId)) {
			throw new Exception("驳回目标节点ID为空！");
		}

		// 查找所有并行任务节点，同时驳回
		List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(
				taskId).getId(), findTaskById(taskId).getTaskDefinitionKey());
		for (Task task : taskList) {
			commitProcess(task.getId(), variables, activityId);
		}
	}
	
	
	
	
	private void commitProcess(String taskId, Map<String, Object> variables,
			String activityId) throws Exception {
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}
		// 跳转节点为空，默认提交操作
		if (StringUtils.isBlank(activityId)) {
			taskService.complete(taskId, variables);
		} else {// 流程转向操作
			turnTransition(taskId, activityId, variables);
		}
	}
	
	
	/**
	 * 流程转向操作
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            目标节点任务ID
	 * @param variables
	 *            流程变量
	 * @throws Exception
	 */
	private void turnTransition(String taskId, String activityId,
			Map<String, Object> variables) throws Exception {
		// 当前节点
		ActivityImpl currActivity = findActivitiImpl(taskId, null);
		// 清空当前流向
		List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

		// 创建新流向
		TransitionImpl newTransition = currActivity.createOutgoingTransition();
		// 目标节点
		ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
		// 设置新流向的目标节点
		newTransition.setDestination(pointActivity);

		// 执行转向任务
		taskService.complete(taskId, variables);
		// 删除目标节点新流入
		pointActivity.getIncomingTransitions().remove(newTransition);

		// 还原以前流向
		restoreTransition(currActivity, oriPvmTransitionList);
	}
	
	
	/**
	 * 根据任务ID和节点ID获取活动节点 <br>
	 * 
	 * @param taskId
	 *            任务ID
	 * @param activityId
	 *            活动节点ID <br>
	 *            如果为null或""，则默认查询当前活动节点 <br>
	 *            如果为"end"，则查询结束节点 <br>
	 * 
	 * @return
	 * @throws Exception
	 */
	private ActivityImpl findActivitiImpl(String taskId, String activityId)
			throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 获取当前活动节点ID
		if (StringUtils.isBlank(activityId)) {
			activityId = findTaskById(taskId).getTaskDefinitionKey();
		}

		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl
						.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}

		// 根据节点ID，获取对应的活动节点
		ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
				.findActivity(activityId);

		return activityImpl;
	}
	
	
	/**
	 * 清空指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @return 节点流向集合
	 */
	private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
		// 存储当前节点所有流向临时变量
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		// 获取当前节点所有流向，存储到临时变量，然后清空
		List<PvmTransition> pvmTransitionList = activityImpl
				.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		return oriPvmTransitionList;
	}
	/**
	 * 还原指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @param oriPvmTransitionList
	 *            原有节点流向集合
	 */
	private void restoreTransition(ActivityImpl activityImpl,
			List<PvmTransition> oriPvmTransitionList) {
		// 清空现有流向
		List<PvmTransition> pvmTransitionList = activityImpl
				.getOutgoingTransitions();
		pvmTransitionList.clear();
		// 还原以前流向
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}
	
	
	/**
	 * 根据任务ID获取流程定义
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
			String taskId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(findTaskById(taskId)
						.getProcessDefinitionId());

		if (processDefinition == null) {
			throw new Exception("流程定义未找到!");
		}

		return processDefinition;
	}
	
	/**
	 * 根据任务ID获得任务实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private TaskEntity findTaskById(String taskId) throws Exception {
		TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(
				taskId).singleResult();
		if (task == null) {
			throw new Exception("任务实例未找到!");
		}
		return task;
	}
	
	/**
	 * 迭代循环流程树结构，查询当前节点可驳回的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param currActivity
	 *            当前活动节点
	 * @param rtnList
	 *            存储回退节点集合
	 * @param tempList
	 *            临时存储节点集合（存储一次迭代过程中的同级userTask节点）
	 * @return 回退节点集合
	 */
	private List<ActivityImpl> iteratorBackActivity(String taskId,
			ActivityImpl currActivity, List<ActivityImpl> rtnList,
			List<ActivityImpl> tempList) throws Exception {
		// 查询流程定义，生成流程树结构
		ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);

		// 当前节点的流入来源
		List<PvmTransition> incomingTransitions = currActivity
				.getIncomingTransitions();
		// 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
		List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();
		// 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
		List<ActivityImpl> parallelGateways = new ArrayList<ActivityImpl>();
		// 遍历当前节点所有流入路径
		for (PvmTransition pvmTransition : incomingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			ActivityImpl activityImpl = transitionImpl.getSource();
			String type = (String) activityImpl.getProperty("type");
			/**
			 * 并行节点配置要求：<br>
			 * 必须成对出现，且要求分别配置节点ID为:XXX_start(开始)，XXX_end(结束)
			 */
			if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId
						.lastIndexOf("_") + 1);
				if ("START".equals(gatewayType.toUpperCase())) {// 并行起点，停止递归
					return rtnList;
				} else {// 并行终点，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
					parallelGateways.add(activityImpl);
				}
			} else if ("startEvent".equals(type)) {// 开始节点，停止递归
				return rtnList;
			} else if ("userTask".equals(type)) {// 用户任务
				tempList.add(activityImpl);
			} else if ("exclusiveGateway".equals(type)) {// 分支路线，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
				currActivity = transitionImpl.getSource();
				exclusiveGateways.add(currActivity);
			}
		}

		/**
		 * 迭代条件分支集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : exclusiveGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}

		/**
		 * 迭代并行集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : parallelGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}

		/**
		 * 根据同级userTask集合，过滤最近发生的节点
		 */
		currActivity = filterNewestActivity(processInstance, tempList);
		if (currActivity != null) {
			// 查询当前节点的流向是否为并行终点，并获取并行起点ID
			String id = findParallelGatewayId(currActivity);
			if (StringUtils.isBlank(id)) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点
				rtnList.add(currActivity);
			} else {// 根据并行起点ID查询当前节点，然后迭代查询其对应的userTask任务节点
				currActivity = findActivitiImpl(taskId, id);
			}

			// 清空本次迭代临时集合
			tempList.clear();
			// 执行下次迭代
			iteratorBackActivity(taskId, currActivity, rtnList, tempList);
		}
		return rtnList;
	}
	
	/**
	 * 根据任务ID获取对应的流程实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessInstance findProcessInstanceByTaskId(String taskId)
			throws Exception {
		// 找到流程实例
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery().processInstanceId(
						findTaskById(taskId).getProcessInstanceId())
				.singleResult();
		if (processInstance == null) {
			throw new Exception("流程实例未找到!");
		}
		return processInstance;
	}
	
	/**
	 * 根据流入任务集合，查询最近一次的流入任务节点
	 * 
	 * @param processInstance
	 *            流程实例
	 * @param tempList
	 *            流入任务集合
	 * @return
	 */
	private ActivityImpl filterNewestActivity(ProcessInstance processInstance,
			List<ActivityImpl> tempList) {
		while (tempList.size() > 0) {
			ActivityImpl activity_1 = tempList.get(0);
			HistoricActivityInstance activityInstance_1 = findHistoricUserTask(
					processInstance, activity_1.getId());
			if (activityInstance_1 == null) {
				tempList.remove(activity_1);
				continue;
			}

			if (tempList.size() > 1) {
				ActivityImpl activity_2 = tempList.get(1);
				HistoricActivityInstance activityInstance_2 = findHistoricUserTask(
						processInstance, activity_2.getId());
				if (activityInstance_2 == null) {
					tempList.remove(activity_2);
					continue;
				}

				if (activityInstance_1.getEndTime().before(
						activityInstance_2.getEndTime())) {
					tempList.remove(activity_1);
				} else {
					tempList.remove(activity_2);
				}
			} else {
				break;
			}
		}
		if (tempList.size() > 0) {
			return tempList.get(0);
		}
		return null;
	}
	
	/**
	 * 查询指定任务节点的最新记录
	 * 
	 * @param processInstance
	 *            流程实例
	 * @param activityId
	 * @return
	 */
	private HistoricActivityInstance findHistoricUserTask(
			ProcessInstance processInstance, String activityId) {
		HistoricActivityInstance rtnVal = null;
		// 查询当前流程实例审批结束的历史节点
		List<HistoricActivityInstance> historicActivityInstances = historyService
				.createHistoricActivityInstanceQuery().activityType("userTask")
				.processInstanceId(processInstance.getId()).activityId(
						activityId).finished()
				.orderByHistoricActivityInstanceEndTime().desc().list();
		if (historicActivityInstances.size() > 0) {
			rtnVal = historicActivityInstances.get(0);
		}

		return rtnVal;
	}
	
	
	/**
	 * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行起点ID
	 * 
	 * @param activityImpl
	 *            当前节点
	 * @return
	 */
	private String findParallelGatewayId(ActivityImpl activityImpl) {
		List<PvmTransition> incomingTransitions = activityImpl
				.getOutgoingTransitions();
		for (PvmTransition pvmTransition : incomingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			activityImpl = transitionImpl.getDestination();
			String type = (String) activityImpl.getProperty("type");
			if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId
						.lastIndexOf("_") + 1);
				if ("END".equals(gatewayType.toUpperCase())) {
					return gatewayId.substring(0, gatewayId.lastIndexOf("_"))
							+ "_start";
				}
			}
		}
		return null;
	}
	
	/**
	 * 反向排序list集合，便于驳回节点按顺序显示
	 * 
	 * @param list
	 * @return
	 */
	private List<ActivityImpl> reverList(List<ActivityImpl> list) {
		List<ActivityImpl> rtnList = new ArrayList<ActivityImpl>();
		// 由于迭代出现重复数据，排除重复
		for (int i = list.size(); i > 0; i--) {
			if (!rtnList.contains(list.get(i - 1)))
				rtnList.add(list.get(i - 1));
		}
		return rtnList;
	}
	
	/**
	 * 根据流程实例ID和任务key值查询所有同级任务集合
	 * 
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	private List<Task> findTaskListByKey(String processInstanceId, String key) {
		return taskService.createTaskQuery().processInstanceId(
				processInstanceId).taskDefinitionKey(key).list();
	}
	
}
