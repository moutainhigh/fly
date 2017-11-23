package com.xinfang.listener.servicetask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinfang.service.TzmPetitionService;

@Component("approvalEndListener")
public class ApprovalEndListener implements JavaDelegate {
	@Autowired
	private TzmPetitionService tzmPetitionService;

	@Override
	public void execute(DelegateExecution task) throws Exception {
		// TODO Auto-generated method stub
		String taskId = task.getId();
		Map<String, Object> taskVariables = task.getVariables();

		int type = (int) task.getVariable("type");
		if (type == 4 || type == 5 || type == 6 || type == 7 || type == 9 || type == 10) {
			List<String> approvalList = null;
			if (taskVariables.containsKey("approvalList") && taskVariables.get("approvalList") != null) {
				approvalList = (List<String>) taskVariables.get("approvalList");
			} else {
				approvalList = new ArrayList<String>();
			}

			if ((int) taskVariables.get("approval") < 1) {
				if (taskVariables.containsKey("approvalList")) {
					task.removeVariable("approvalList");
				}
			} else {
				if (taskVariables.containsKey("tempTask")) {
					approvalList.add(task.getVariable("tempTask").toString());
				}
				task.setVariable("approvalList", approvalList);
			}
		} else if (type == 8) {
			List<String> completedTransfer = null;
			if (taskVariables.containsKey("completedTransfer") && taskVariables.get("completedTransfer") != null) {
				completedTransfer = (List<String>) taskVariables.get("completedTransfer");
			} else {
				completedTransfer = new ArrayList<String>();
			}
			if ((int) taskVariables.get("approval") == 1) {
				if (taskVariables.containsKey("tempTask")) {

					ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
					HistoryService historyService = processEngine.getHistoryService();
					HistoricTaskInstance h = historyService.createHistoricTaskInstanceQuery()
							.taskId(task.getVariable("tempTask").toString()).singleResult();
					completedTransfer.add(task.getVariable("tempTask").toString());
					List<Integer> leaders = tzmPetitionService.getLeaderIdByRyid(Integer.parseInt(h.getAssignee()));
					task.setVariable("staff", h.getAssignee());
					task.setVariable("staffleader", leaders.get(0).toString());
				} else {

					task.setVariable("staff", task.getVariable("createrid"));
					task.setVariable("staffleader", task.getVariable("createrLeader"));
				}
				task.setVariable("completedTransfer", completedTransfer);
			}

		}

	}

}
