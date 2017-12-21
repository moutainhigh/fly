package com.xinfang.listener.servicetask;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinfang.dao.FdCaseRepository;
import com.xinfang.service.ActivitiService;

/**
 * 
 * @description 案件退回
 * @author ZhangHuaRong   
 * @update 2017年3月25日 下午10:06:18
 */
@Component("taskReturnFirst")
public class TaskReturnFirst implements JavaDelegate{
	
	
	
	@Autowired
	private FdCaseRepository fdCaseRepository;
	@Autowired
	ActivitiService activitiService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		 execution.setVariable("istaskReturn", 1);//help sunbx
		 Map<String,Object> variables= execution.getVariables();
		// Integer caseid = (Integer) variables.get("caseid");
		 
		/* FdCase fdcase = fdCaseRepository.findById(caseid);
		 fdcase.setIsFlow((byte)0);
		 activitiService.saveCase(fdcase,1);*/
		 
		 Object object = execution.getVariable("formdate");
		 if(object!=null){
			 Map<String,String> format= (Map<String, String>) object;
			 String istaskReturn= format.get("istaskReturn");
			 if(istaskReturn!=null){
				 Integer result = Integer.parseInt(istaskReturn);
				 result++;
				 format.put("istaskReturn", result.toString());
			 }
			
			 
			 execution.setVariable("formdate", format);
		 }
		
	}

}
