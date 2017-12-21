package com.xinfang.listener.flow;

import java.util.Date;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinfang.dao.FcDwbRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.enums.HandleLimitState;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FcDwb;
import com.xinfang.model.FdDepCase;
import com.xinfang.utils.DateUtils;
import com.xinfang.utils.RuleUtil;
import com.xinfang.log.SuperLog;
/**
 * 
 * @title FinishRegListerer.java
 * @package com.xinfang.listener.flow
 * @description 案件办结出具答复意见书
 * @author ZhangHuaRong   
 * @update 2017年4月20日 下午3:32:06
 */
@Component("replyListerer")
public class ReplyListerer implements TaskListener {
	private static final long serialVersionUID = 1L;

	@Autowired
	FcDwbRepository fcDwbRepository;
	@Autowired
	FdDepCaseRepository fdDepCaseRepository;
	@Override
	public void notify(DelegateTask task) {
		
		/*if(){ 是否是回退案件判断
			
		}*/ //TODO 是否是回退案件判断
		try {
			SuperLog.chargeLog1("开始");
			Integer depid = (Integer) task.getVariable("depid");
			Integer handleday = (Integer) task.getVariable("handleday");
			Integer caseid = (Integer) task.getVariable("caseid");
		//	Integer approval = (Integer) task.getVariable("approval");
			Integer type = (Integer) task.getVariable("type");
			Object processid =  task.getVariable("processid");
			Integer	createDep = (Integer)  task.getVariable("createDep");
			Integer	secondDep = (Integer)  task.getVariable("SecondDep");
			 ApiLog.chargeLog1("==============流程控制===============replyListerer==");
             System.out.println("==============流程控制===============replyListerer==");

			if(type==8){//退回
				
			}else if(type==2 ){// 转办 交办		
				List<FdDepCase> depcases=fdDepCaseRepository.findByCaseIdAndState(caseid, 1);
				Date nowtime = new Date();
				FdDepCase maindep = new FdDepCase();
				maindep.setStartTime(nowtime);
				maindep.setEndTime(DateUtils.add(nowtime, handleday*RuleUtil.getRule().getReply()*0.01f));
				maindep.setLimitTime(handleday*RuleUtil.getRule().getReply()*0.01f);
				maindep.setType(HandleLimitState.reply.getValue());
				maindep.setNote(HandleLimitState.reply.getName());
				maindep.setCaseId((long) caseid);
				maindep.setCreateTime(nowtime);
				maindep.setState(1);
				maindep.setUpdateTime(nowtime);
				maindep.setDepId(depid);
				if(processid!=null){
					maindep.setProcessid(processid.toString());
				}
				fdDepCaseRepository.saveAndFlush(maindep);
				if(depcases!=null&&!depcases.isEmpty()){
					FdDepCase dep = depcases.get(0);
					dep.setState(0);
					dep.setUpdateTime(nowtime);
					fdDepCaseRepository.saveAndFlush(dep);
				}
				
			}else if(type==4||type==5||type==6||type==7){//直接回复，不再受理 ，不予受理
				if(depid.intValue()==createDep.intValue()){
					record(secondDep, handleday, caseid, type, processid,"areaApproval");
				}else{
					
				}
				
				
			}else if(type==3){//受理
				record(depid, handleday, caseid, type, processid,"replyLetter");
				
			}else if(type==1){ //交办
				record(depid, handleday, caseid, type, processid,"forword");
			}
			SuperLog.chargeLog1("结束");
		} catch (Exception e) {
			e.printStackTrace();
			ApiLog.log("交办领导审批后计算被交办单位限办时间出错："+e.getMessage());
		}
		
		

	}
	private void record(Integer depid, Integer handleday, Integer caseid, Integer type, Object processid,String name) {
		SuperLog.chargeLog1("type: "+ type +"/n" + "添加数据开始");
		List<FdDepCase> depcases=fdDepCaseRepository.findByCaseIdAndState(caseid, 1);
		Date nowtime = new Date();
		FdDepCase maindep = new FdDepCase();
		FcDwb fcDwb = fcDwbRepository.findOne(depid);
		maindep.setStartTime(nowtime);
		if(name.equals("replyLetter")){
			
			maindep.setEndTime(DateUtils.add(nowtime, handleday*RuleUtil.getRule().getReplyLetter()*0.01f));
			maindep.setLimitTime(handleday*RuleUtil.getRule().getReplyLetter()*0.01f);
			maindep.setType(HandleLimitState.replyLetter.getValue());
			maindep.setNote(HandleLimitState.replyLetter.getName());
			
		}else if(name.equals("areaApproval")){
			
			maindep.setEndTime(DateUtils.add(nowtime, handleday*RuleUtil.getRule().getAreaApproval()*0.01f));
			maindep.setLimitTime(handleday*RuleUtil.getRule().getAreaApproval()*0.01f);
			maindep.setType(HandleLimitState.areaApproval.getValue());
			maindep.setNote(HandleLimitState.areaApproval.getName());
			
		}else if(name.equals("forword")){
			maindep.setEndTime(DateUtils.add(nowtime, handleday*RuleUtil.getRule().getForword()*0.01f));
			maindep.setLimitTime(handleday*RuleUtil.getRule().getForword()*0.01f);
			maindep.setType(HandleLimitState.forword.getValue());
			maindep.setNote(HandleLimitState.forword.getName()+"(4)");
		}
		
		maindep.setCaseId((long) caseid);
		maindep.setCreateTime(nowtime);
		maindep.setState(1);
		maindep.setUpdateTime(nowtime);
		maindep.setDepId(depid);
		if(processid!=null){
			maindep.setProcessid(processid.toString());
		}
		fdDepCaseRepository.saveAndFlush(maindep);
		if(depcases!=null&&!depcases.isEmpty()){
			FdDepCase dep = depcases.get(0);
			dep.setState(0);
			dep.setUpdateTime(nowtime);
			fdDepCaseRepository.saveAndFlush(dep);
		}
		SuperLog.chargeLog1("添加数据结束");
	}

}