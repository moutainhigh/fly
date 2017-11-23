package com.xinfang.listener.flow;

import java.util.Date;

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
 * @description 市级审批
 * @author ZhangHuaRong   
 * @update 2017年4月20日 下午3:32:06
 */
@Component("cityAppryListerer")
public class CityAppryListerer implements TaskListener {
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
		//	Integer approval = (Integer) task.getVariable("approval");
			Integer type = (Integer) task.getVariable("type");
			Object processid =  task.getVariable("processid");
			 ApiLog.chargeLog1("==============流程控制===============cityAppryListerer==");
             System.out.println("==============流程控制===============cityAppryListerer==");
			if(type==8){//退回
				
			}else if(type==1){//交办
				
				
			}else if(type==4||type==5||type==6||type==7){//直接回复，不再受理 ，不予受理
				//update FdDepCase 
				
			}else{
				//String xbdepinfo = (String) task.getVariable("xbdepinfo");
				Date nowtime = new Date();
				FdDepCase maindep = new FdDepCase();
				FcDwb fcDwb = fcDwbRepository.findOne(depid);
				if(fcDwb.getDwType().intValue()==4 || fcDwb.getDwType().intValue()==8){//职能部门
					maindep.setStartTime(nowtime);
					maindep.setEndTime(DateUtils.add(nowtime, handleday*RuleUtil.getRule().getReply()*0.01f));
					maindep.setLimitTime(handleday*RuleUtil.getRule().getReply()*0.01f);
					maindep.setType(HandleLimitState.reply.getValue());
					maindep.setNote(HandleLimitState.reply.getName());
				}else if(fcDwb.getDwType().intValue()==3 || fcDwb.getDwType().intValue()==7){//区县
					maindep.setStartTime(nowtime);
					maindep.setEndTime(DateUtils.add(nowtime, handleday*RuleUtil.getRule().getForword()*0.01f));
					maindep.setLimitTime(handleday*RuleUtil.getRule().getRegister()*0.01f);
					maindep.setType(HandleLimitState.forword.getValue());
					maindep.setNote(HandleLimitState.forword.getName()+"(1)");
				}else{
					maindep.setNote("转交单位id"+maindep.getDepId()+" 类型："+fcDwb.getDwType());
				}
				maindep.setCaseId((long) caseid);
				//maindep.setCreaterId(userid);
				maindep.setCreateTime(nowtime);
				//maindep.setNeedReceipt(needReceipt);
				maindep.setState(1);
				maindep.setUpdateTime(nowtime);
				if(processid!=null){
					maindep.setProcessid(processid.toString());
				}
				maindep.setDepId(depid);
				fdDepCaseRepository.saveAndFlush(maindep);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ApiLog.log("交办领导审批后计算被交办单位限办时间出错："+e.getMessage());
		}
		
		

	}

}