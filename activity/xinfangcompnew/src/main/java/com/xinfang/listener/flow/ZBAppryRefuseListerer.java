package com.xinfang.listener.flow;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinfang.dao.FcDwbRepository;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.enums.HandleLimitState;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdDepCase;
import com.xinfang.utils.DateUtils;
import com.xinfang.utils.RuleUtil;
/**
 * 
 * @title FinishRegListerer.java
 * @package com.xinfang.listener.flow
 * @description 拒绝审批
 * @author ZhangHuaRong   
 * @update 2017年4月20日 下午3:32:06
 */
@Component("zBAppryRefuseListerer")
public class ZBAppryRefuseListerer implements JavaDelegate {
	private static final long serialVersionUID = 1L;

	@Autowired
	FcDwbRepository fcDwbRepository;
	@Autowired
	FdDepCaseRepository fdDepCaseRepository;
	
	@Autowired
	FdCaseRepository fdCaseRepository;
	
	   private  Float getlast(Date createTime, Date nowtime, Integer handleday) {
	 	   float result = (nowtime.getTime()-createTime.getTime())/(1000*3600f)/24f;
	 	   BigDecimal   b   =   new   BigDecimal(result);
	 	   float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue();
	 	   return handleday-f1;
	 	}
	   
	   
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		try {
			Map<String,Object> variables= execution.getVariables();
			Integer depid = (Integer) variables.get("depid");
			Object dep1 =   variables.get("dep1");
			Integer handleday = (Integer)  variables.get("handleday");
			Integer caseid = (Integer)  variables.get("caseid");
			FdCase fdCase =  fdCaseRepository.findById(caseid);
			Date nowtime = new Date();
			Float lasttime =getlast(fdCase.getGmtCreate(),nowtime,handleday);
			Object processid =   variables.get("processid");
			int approval = (int)variables.get("approval");
			if (approval < 1){
				if(dep1!=null){//多次交办
					
					List<FdDepCase> depcases=fdDepCaseRepository.findByCaseIdAndState(caseid, 1);
					FdDepCase maindep = new FdDepCase();
					if(processid!=null){
						maindep.setProcessid(processid.toString());
					}
					maindep.setStartTime(nowtime);
					maindep.setEndTime(DateUtils.add(nowtime, lasttime*RuleUtil.getRule().getReply()*0.01f));
					maindep.setLimitTime(lasttime*RuleUtil.getRule().getReply()*0.01f);
					maindep.setType(HandleLimitState.reply.getValue());
					maindep.setNote(HandleLimitState.reply.getName());
					maindep.setCaseId((long) caseid);
					maindep.setCreateTime(nowtime);
					maindep.setState(1);
					maindep.setUpdateTime(nowtime);
					maindep.setDepId(depid);
					fdDepCaseRepository.saveAndFlush(maindep);
					if(depcases!=null&&!depcases.isEmpty()){
						FdDepCase dep = depcases.get(0);
						dep.setState(0);
						dep.setUpdateTime(nowtime);
						fdDepCaseRepository.saveAndFlush(dep);
					}
					
				}else{
					List<FdDepCase> depcases=fdDepCaseRepository.findByCaseIdAndState(caseid, 1);
					FdDepCase maindep = new FdDepCase();
					maindep.setStartTime(nowtime);
					if(processid!=null){
						maindep.setProcessid(processid.toString());
					}
					maindep.setEndTime(DateUtils.add(nowtime, lasttime*RuleUtil.getRule().getReply()*0.01f));
					maindep.setLimitTime(lasttime*RuleUtil.getRule().getReply()*0.01f);
					maindep.setType(HandleLimitState.reply.getValue());
					maindep.setNote(HandleLimitState.reply.getName());
					maindep.setCaseId((long) caseid);
					maindep.setCreateTime(nowtime);
					maindep.setState(1);
					maindep.setUpdateTime(nowtime);
					maindep.setDepId(depid);
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
		}
		
		
	}

}