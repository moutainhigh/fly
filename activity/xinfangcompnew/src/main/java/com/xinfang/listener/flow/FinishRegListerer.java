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
/**
 * 
 * @title FinishRegListerer.java
 * @package com.xinfang.listener.flow
 * @description 信访局转出后设置下一个单位的限办时间
 * @author ZhangHuaRong   
 * @update 2017年4月20日 下午3:32:06
 */
@Component("finishreg")
public class FinishRegListerer implements TaskListener {
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
			Object processid =  task.getVariable("processid");
             ApiLog.chargeLog1("==============流程控制===============finishreg==");
             System.out.println("==============流程控制===============finishreg==");
			if(approval<1){
				
			}else{
				//String xbdepinfo = (String) task.getVariable("xbdepinfo");
				Date nowtime = new Date();
				FdDepCase maindep = new FdDepCase();
				FcDwb fcDwb = fcDwbRepository.findOne(depid);
				List<FdDepCase> depcases=fdDepCaseRepository.findByCaseIdAndState(caseid, 1);
				
				
				if(fcDwb.getDwType().intValue()==4 || fcDwb.getDwType().intValue()==8){//职能部门
					maindep.setStartTime(nowtime);
					maindep.setEndTime(DateUtils.add(nowtime, handleday*RuleUtil.getRule().getReply()*0.01f));
					maindep.setLimitTime(handleday*RuleUtil.getRule().getReply()*0.01f);
					maindep.setType(HandleLimitState.reply.getValue());
					maindep.setNote(HandleLimitState.reply.getName());
				}else if(fcDwb.getDwType().intValue()==7 || fcDwb.getDwType().intValue()==3){//区县
					maindep.setStartTime(nowtime);
					maindep.setEndTime(DateUtils.add(nowtime, handleday*RuleUtil.getRule().getForword()*0.01f));
					maindep.setLimitTime(handleday*RuleUtil.getRule().getRegister()*0.01f);
					maindep.setType(HandleLimitState.forword.getValue());
					maindep.setNote(HandleLimitState.forword.getName()+"(2)");
				}else{
					maindep.setNote("转交单位id"+maindep.getDepId()+" 类型："+fcDwb.getDwType());
				}
				maindep.setCaseId((long) caseid);
				//maindep.setCreaterId(userid);
				maindep.setCreateTime(nowtime);
				//maindep.setNeedReceipt(needReceipt);
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
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ApiLog.log("交办领导审批后计算被交办单位限办时间出错："+e.getMessage());
		}
		
		

	}

}