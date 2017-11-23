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
 * @description 直接回复，不在受理，不予受理
 * @author ZhangHuaRong   
 * @update 2017年3月25日 下午10:10:26
 */
@Component("commonTask")
public class CommonTask implements JavaDelegate{
	
	@Autowired
    private CaseService caseService;
	
	@Autowired
	private FdCaseRepository fdCaseRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Map<String,Object> variables= execution.getVariables();
		Object obj = variables.get("fdCase");
//		Integer caseId = (Integer) variables.get("caseid");
//		FdCase  fCase = fdCaseRepository.findById(caseId);
		if(obj!=null){
			FdCase fdCase = (FdCase) obj;
			caseService.updateCaseHandleStateById(null,StateConstants.HANDLE_HAS, fdCase.getId());	
			fdCaseRepository.updateHandleFactEndTime(new Date(), fdCase.getId());
			
		}
		
		
		
	}

}
