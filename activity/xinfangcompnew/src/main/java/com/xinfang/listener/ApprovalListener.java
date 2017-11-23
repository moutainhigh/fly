package com.xinfang.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("approvalListener")
public class ApprovalListener implements TaskListener {
	private static final long serialVersionUID = 1L;
	@Autowired
	private TaskService taskService;

	@Override
	public void notify(DelegateTask task) {
		String intanceId = task.getProcessInstanceId();
		String taskId = task.getId();
		String taskDefinitionKey = task.getTaskDefinitionKey().toString();

		boolean flag = false;
		Map<String, Object> taskVariables = taskService.getVariables(taskId);
		// 获取历史任务
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		HistoryService historyService = processEngine.getHistoryService();
		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(intanceId).list();

		// 对历史任务列表进行排序，按任务ID排序
		Collections.sort(historicTaskInstances, new Comparator<HistoricTaskInstance>() {

			/*
			 * int compare(HistoricTaskInstance o1, HistoricTaskInstance o2)
			 */
			@Override
			public int compare(HistoricTaskInstance o1, HistoricTaskInstance o2) {
				return Integer.parseInt(o1.getId().toString()) - Integer.parseInt(o2.getId());
			}

		});
		List<String> approvalList = null;
		List<String> completedTransfer = null;

		// 用taskId作为审核人列表key
		if (taskVariables.containsKey("approvalList") && taskVariables.get("approvalList") != null) {
			approvalList = (List<String>) taskVariables.get("approvalList");
		} else {
			approvalList = new ArrayList<String>();
		}

		if (taskVariables.containsKey("completedTransfer") && taskVariables.get("completedTransfer") != null) {
			completedTransfer = (List<String>) taskVariables.get("completedTransfer");
		} else {
			completedTransfer = new ArrayList<String>();
		}

		// 审核发起者，放入completedTransfer列表
		// HistoricTaskInstance x =
		// historicTaskInstances.get(historicTaskInstances.size() - 1);
		// if (historicTaskInstances.size() > 0) {
		// if (historicTaskInstances.get(historicTaskInstances.size() -
		// 1).getTaskDefinitionKey().toString()
		// .equals("usertask3")
		// && !completedTransfer
		// .contains(historicTaskInstances.get(historicTaskInstances.size() -
		// 1).getId().toString())) {
		// completedTransfer.add(historicTaskInstances.get(historicTaskInstances.size()
		// - 1).getId().toString());
		// task.setVariable("completedTransfer", completedTransfer);
		//
		// }
		// }
		int tempNum = 0;
		// 倒序检索历史任务列表，将检索到第一个审核人不能进行审核
		for (int i = historicTaskInstances.size() - 2; i >= 0; i--) {
			HistoricTaskInstance historicTask = historicTaskInstances.get(i);
			String historicTaskId = historicTask.getId().toString();
			String historicTaskDefinitionKey = historicTask.getTaskDefinitionKey().toString();
			if (!approvalList.contains(historicTaskId) && !completedTransfer.contains(historicTaskId)) {
				if (historicTaskDefinitionKey.equals("usertask3")) {
					tempNum++;
					if (tempNum == 1) {
						completedTransfer.add(historicTaskId);

					} else {
						flag = true;
						// 转交任务的办案人员审核
						task.setVariable("tempTask", historicTask.getId());
						task.setVariable("approvalType", 111);
						if (!taskDefinitionKey.equals("usertask7")) {
							task.setAssignee(historicTask.getAssignee().toString());
						}
						break;
					}

				}
			}

		}
		task.setVariable("completedTransfer", completedTransfer);
		if (flag == false) {
			task.removeVariable("tempTask");
			// task.setVariable("staff", taskVariables.get("createrid"));
			task.setVariable("approvalType", 222);
			if (!taskDefinitionKey.equals("usertask7")) {
				task.setAssignee(taskVariables.get("createrid").toString());
			}
		}

	}

}
