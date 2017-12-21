package com.xinfang.listener;

import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("caseHandleListener")
public class CaseHandleListener implements TaskListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TaskService taskService;

	@Override
	public void notify(DelegateTask paramDelegateTask) {
		Map<String, Object> taskVariables = taskService.getVariables(paramDelegateTask.getId());
		if (taskVariables.containsKey("staff")) {
			paramDelegateTask.setAssignee(taskVariables.get("staff").toString());
		}

	}

}
