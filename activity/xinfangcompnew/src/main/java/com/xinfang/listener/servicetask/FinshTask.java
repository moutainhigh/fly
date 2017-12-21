package com.xinfang.listener.servicetask;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinfang.context.StateConstants;
import com.xinfang.dao.FdCaseRepository;
import com.xinfang.model.FdCase;
import com.xinfang.service.CaseService;

/**
 * 
 * @description 案件办结
 * @author ZhangHuaRong   
 * @update 2017年3月25日 下午10:11:14
 */
@Component("finshTask")
public class FinshTask implements JavaDelegate{

	@Autowired
    private CaseService caseService;
	
	@Autowired
	private FdCaseRepository fdCaseRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Map<String,Object> variables= execution.getVariables();
		Object obj = variables.get("fdCase");
		Integer delayDay = (Integer) variables.get("delayDay");
		if(obj!=null){
			
			FdCase fdCase = (FdCase) obj;
			caseService.updateCaseHandleStateById(null,StateConstants.HANDLE_HAS, fdCase.getId());
			fdCaseRepository.updateHandleFactEndTime(new Date(), fdCase.getId());
			
			
		}
		
	}

}
