package com.hsd.oa.service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsd.oa.service.VO.TaskVO;


/**
 * Created by jery on 2016/11/23.
 */

@Service
// @Transactional
public class ActivitiService {
	// 注入为我们自动配置好的服务
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	

	// 开始流程，传入申请者的id以及公司的id
	public String startProcess(String key, String formjson) {
		Map<String, Object> variables = new HashMap<String, Object>();
		setVariableByKey(key,variables);
		variables.put("formjson", formjson);//表单数据
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(key, variables);
	    return "success";
	}


	private void setVariableByKey(String key, Map<String, Object> variables) {
		if(key.equals("qingjia")) {
			variables.put("leader", "1");//TODO 此处设置leader为用户id为1 用着测试
			variables.put("day", 3);
			variables.put("bmleader", "2");
			
		}else if(key.equals("baoxiao")) {
			variables.put("bmleader", "1");
			variables.put("jingli", "2");
			variables.put("chuna", "3");
			variables.put("chaiwu", "4");
		}
	}

	/**
	 * 
		 * 此方法描述的是：   查看我的任务列表
		 * @param assignee
		 * @return List<Task>
	 */
	public List<TaskVO> getTasks(String assignee) {
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
		List<TaskVO> vos = new ArrayList<TaskVO>();
		for (Task task : tasks) {
			TaskVO vo = new TaskVO();
			Object formdate = taskService.getVariable(task.getId(), "formjson");
			vo.setTaskId(task.getId());
			vo.setTaskName(task.getName());
			vo.setTaskDefinitionKey(task.getTaskDefinitionKey());
			vo.setProcDefId(task.getProcessDefinitionId());
			vo.setProInsId(task.getProcessInstanceId());
			vo.setFormdate(formdate);
			vos.add(vo);
		}
		
		return vos;
	}
	
	/**
	 * 
		 * 此方法描述的是：   审批
		 * @param taskid
		 * @param approval 1通过0不通过
		 * @param files
		 * @param note
		 * @param userId void
	 */
	public void completetask(String taskid, int approval, String files, String note, int userId) {
		Map<String, Object> taskVariables = taskService.getVariables(taskid);
		taskVariables.put("approval", approval);
		taskService.complete(taskid, taskVariables);

	}
	
	
	/**
	 * 
		 * 此方法描述的是：   查看已经完成的流程
		 * @param userid
		 * @param startpage
		 * @param pagesize
		 * @return List<HistoricProcessInstance>
	 */
	public List<HistoricProcessInstance> finishtask(String userid, int startpage, int pagesize) {
		List<HistoricProcessInstance> datas = historyService.createHistoricProcessInstanceQuery().involvedUser(userid)
				.finished().list();

		return null;
	}

	/**
	 * 
		 * 此方法描述的是：   查看正在进行的流程
		 * @param userid
		 * @param startpage
		 * @param pagesize
		 * @return List<HistoricProcessInstance>
	 */
	public List<HistoricProcessInstance> unfinishtask(String userid, int startpage, int pagesize) {
		List<HistoricProcessInstance> datas = historyService.createHistoricProcessInstanceQuery().involvedUser(userid)
				.unfinished().list();

		return null;
	}

}
