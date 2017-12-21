package com.xinfang.listener.servicetask;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * 
 * @description 案件退回
 * @author ZhangHuaRong   
 * @update 2017年3月25日 下午10:06:18
 */
@Component("taskReturn")
public class TaskReturn implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		 execution.setVariable("istaskReturn", 1);//help sunbx
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
