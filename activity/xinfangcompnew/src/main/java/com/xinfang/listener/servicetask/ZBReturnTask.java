package com.xinfang.listener.servicetask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.stereotype.Component;

@Component("zBReturnTask")
public class ZBReturnTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution task) throws Exception {
		Map<String, Object> taskVariables = task.getVariables();
		int type = (int) task.getVariable("type");
		if (type == 8) {
			boolean flag = false;
			String intanceId = task.getProcessInstanceId();
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			HistoryService historyService = processEngine.getHistoryService();
			List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
					.processInstanceId(intanceId).list();
			List<String> completedTransfer = null;
			Collections.sort(historicTaskInstances, new Comparator<HistoricTaskInstance>() {

				/*
				 * int compare(HistoricTaskInstance o1, HistoricTaskInstance o2)
				 */
				@Override
				public int compare(HistoricTaskInstance o1, HistoricTaskInstance o2) {
					return Integer.parseInt(o1.getId().toString()) - Integer.parseInt(o2.getId());
				}

			});
			if (taskVariables.containsKey("completedTransfer") && taskVariables.get("completedTransfer") != null) {
				completedTransfer = (List<String>) taskVariables.get("completedTransfer");
			} else {
				completedTransfer = new ArrayList<String>();
			}
			int tempNum = 0;

			for (int i = historicTaskInstances.size() - 2; i >= 0; i--) {
				HistoricTaskInstance historicTask = historicTaskInstances.get(i);
				String historicTaskId = historicTask.getId().toString();
				String historicTaskDefinitionKey = historicTask.getTaskDefinitionKey().toString();
				if (completedTransfer.contains(historicTaskId)) {
					if (historicTaskDefinitionKey.equals("usertask3")) {
						tempNum++;
						if (tempNum == 1) {
							completedTransfer.add(historicTaskId);

						} else {
							flag = true;
							// 转交任务的办案人员审核
							task.setVariable("tempTask", historicTask.getId());
							task.setVariable("approvalType", 111);

							break;
						}

					}
				}

			}
			task.setVariable("completedTransfer", completedTransfer);
			if (flag == false) {
				task.removeVariable("tempTask");
				task.setVariable("approvalType", 222);

			}
		}

	}

}
