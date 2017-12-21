package com.xinfang.listener;

import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinfang.service.TzmPetitionService;

@Component("depTwoTaskListener")
public class DepTwoTaskListener implements TaskListener {
	private static final long serialVersionUID = 1L;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	TzmPetitionService tzmPetitionService;

	/**
	 * （一个监听如果目标单位操作人没有或者有多个这个节点正常执行并指派给收文岗工作人员） 否则监听里面自动执行
	 */
	@Override
	public void notify(DelegateTask task) {
		Object obj =  task.getVariable("dep2");
		if(obj==null){
			taskService.complete(task.getId());//自动完成
		}else {
			//获取处理人员人员 
			List<Map> tsCaseHandles =tzmPetitionService.getRyIdAndRyLeaderIdByDwid((Integer)obj);
			task.setAssignee(tsCaseHandles.get(0).get("ryId").toString());
		}
		

		System.out.println("taskService3==DepOneTaskListener==" + taskService);

	}

}