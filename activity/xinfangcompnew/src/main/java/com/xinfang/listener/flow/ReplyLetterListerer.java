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
import com.xinfang.model.FdDepCase;
import com.xinfang.utils.DateUtils;
import com.xinfang.utils.RuleUtil;
/**
 * 
 * @title FinishRegListerer.java
 * @package com.xinfang.listener.flow
 * @description 出办结书的时候
 * @author ZhangHuaRong   
 * @update 2017年4月20日 下午3:32:06
 */
@Component("replyLetterListerer")
public class ReplyLetterListerer implements TaskListener {
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
			Integer depid = (Integer) task.getVariable("depid");
			Integer handleday = (Integer) task.getVariable("handleday");
			Integer caseid = (Integer) task.getVariable("caseid");
			Integer approval = (Integer) task.getVariable("approval");
			Object dep1 =  task.getVariable("dep1");
			Integer createDep = (Integer) task.getVariable("createDep");
			Object processid =  task.getVariable("processid");
			 ApiLog.chargeLog1("==============流程控制===============replyLetterListerer==");
             System.out.println("==============流程控制===============replyLetterListerer==");

			if(approval<1){//审批不通过
				
			}else{
				if(dep1!=null){//多次交办
					
					List<FdDepCase> depcases=fdDepCaseRepository.findByCaseIdAndState(caseid, 1);
					Date nowtime = new Date();
					FdDepCase maindep = new FdDepCase();
					maindep.setStartTime(nowtime);
					maindep.setEndTime(DateUtils.add(nowtime, handleday*RuleUtil.getRule().getAreaApproval()*0.01f));
					maindep.setLimitTime(handleday*RuleUtil.getRule().getAreaApproval()*0.01f);
					maindep.setType(HandleLimitState.areaApproval.getValue());
					maindep.setNote(HandleLimitState.areaApproval.getName());
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
					
				}else{
					List<FdDepCase> depcases=fdDepCaseRepository.findByCaseIdAndState(caseid, 1);
					Date nowtime = new Date();
					FdDepCase maindep = new FdDepCase();
					maindep.setStartTime(nowtime);
					maindep.setEndTime(DateUtils.add(nowtime, handleday*RuleUtil.getRule().getCityApproval()*0.01f));
					maindep.setLimitTime(handleday*RuleUtil.getRule().getCityApproval()*0.01f);
					maindep.setType(HandleLimitState.cityApproval.getValue());
					maindep.setNote(HandleLimitState.cityApproval.getName());
					maindep.setCaseId((long) caseid);
					maindep.setCreateTime(nowtime);
					maindep.setState(1);
					maindep.setUpdateTime(nowtime);
					maindep.setDepId(createDep);
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
				}
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ApiLog.log("交办领导审批后计算被交办单位限办时间出错："+e.getMessage());
		}
		
		

	}

}