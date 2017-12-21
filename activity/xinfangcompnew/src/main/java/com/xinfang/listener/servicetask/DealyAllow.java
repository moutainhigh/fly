package com.xinfang.listener.servicetask;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinfang.dao.FdCaseRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdDepCase;
import com.xinfang.service.CaseService;
import com.xinfang.utils.DateUtils;

/**
 * 
 * @title DealyAllow.java
 * @package com.xinfang.listener.servicetask
 * @description 延期申请通过
 * @author ZhangHuaRong   
 * @update 2017年3月25日 下午10:03:56
 * @version V1.0  
 * Copyright (c)2012 chantsoft-版权所有
 */
@Component("dealyAllow")
public class DealyAllow implements JavaDelegate{
	@Autowired
	FdDepCaseRepository fdDepCaseRepository;
	
	@Autowired
	CaseService caseService;
	@Autowired
	FdCaseRepository fdCaseRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Map<String,Object> variables= execution.getVariables();
		Integer delayDay = (Integer) variables.get("delayDay");
		Integer mydepid = (Integer) variables.get("mydepid");
		Integer caseId = (Integer) variables.get("caseid");
		
		List<FdDepCase> list = fdDepCaseRepository.findByCaseIdAndDepId(caseId, mydepid);
		if(!list.isEmpty()){
			FdDepCase detail = list.get(0);
			detail.setLimitTime(detail.getLimitTime()+delayDay);
			if(detail.getEndTime()!=null){
				detail.setEndTime(DateUtils.addDays(detail.getEndTime(), delayDay));
			}
			fdDepCaseRepository.save(detail);
			FdCase fdcase=caseService.getCaseById(caseId);
			fdcase.setHandleDays(fdcase.getHandleDays()+delayDay);
			if(fdcase.getHandleFactEndTime()!=null){
				fdcase.setHandleFactEndTime(DateUtils.addDays(fdcase.getHandleFactEndTime(), delayDay));
				fdcase.setGmtCreate(new Date());
			}
			fdCaseRepository.save(fdcase);
		}
		
		
		
		
		/*if(obj!=null){
			
			FdCase fdCase = (FdCase) obj;
			caseService.updateCaseHandleDurationById(null,fdCase.getId(), fdCase.getHandleDuration().intValue()+delayDay.intValue());
			
			
		}*/
		
	}

}
