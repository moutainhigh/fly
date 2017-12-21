package com.xinfang.listener.servicetask;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinfang.service.CaseService;

/**
 * 
 * @description 消息发送
 * @author ZhangHuaRong   
 * @update 2017年3月25日 下午10:10:26
 */
@Component("logTask")
public class LogTask implements JavaDelegate{
	
	@Autowired
    private CaseService caseService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Map<String,Object> variables= execution.getVariables();
		Object obj = variables.get("fdCase");
		/*if(obj!=null){
			FdCase fdCase = (FdCase) obj;
			caseService.updateCaseHandleStateById(StateConstants.HANDLE_HAS, fdCase.getId());
			
		}*/
		
		
	}

}
