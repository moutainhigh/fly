package com.xinfang.listener;

import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinfang.model.FdCase;
import com.xinfang.personnelmanagement.service.BaseTreeService;
import com.xinfang.service.TzmPetitionService;

@Component("myTaskListener")
public class MyTaskListener implements TaskListener {
	private static final long serialVersionUID = 1L;

	@Autowired
	private TaskService taskService;
	@Autowired
	TzmPetitionService tzmPetitionService;
	
	 @Autowired
	 BaseTreeService baseTreeService;

	/**
	 * （一个监听如果目标单位操作人没有或者有多个这个节点正常执行并指派给收文岗工作人员） 否则监听里面自动执行
	 */
	@Override
	public void notify(DelegateTask task) {
		String[] candidateUsers = { "a", "b", "c" };

		Integer depId = (Integer) task.getVariable("depid");
		FdCase fdcase  = (FdCase) task.getVariable("fdCase");
		Integer casetype = Integer.parseInt(task.getVariable("type")+"") ;//1 交办
		// 获取收文岗工作人员
		//List<Integer> num = tzmPetitionService.getRyIdByDwId(depId);
		
		/*List<Map> num =baseTreeService.getPersonCaseType(depId);*/

		Map<String, Object> taskVariables = task.getVariables();
		/*String staff=null;
		
		String type =fdcase.getPetitionWay()+"";
		int i=0;
		if(num==null){
			
		}else if(num.size()==1){
			  staff=num.get(0).get("ryId")+"";
			  i++;
			
		}else{
			for(Map m:num){
				  String caseIdType=	m.get("caseIdType")+"";
				  if(type.equals(caseIdType)){
					  staff=m.get("ryId")+"";
					  i++;
				  }
			}
		}*/
		
		 Map<String, Object> map = baseTreeService.getPersonCaseType3(depId, fdcase.getPetitionWay());

		 String staff= map.get("FH_RY_ID").toString();
		 String staffleader = map.get("FH_RY_FJID").toString();
		 String isreceive = map.get("FH_RY_FPLX").toString();// 1 直接到人

		 
//			    if(casetype.intValue()==2){//转办直接到人
//			    	task.setAssignee(staff);
//			    	return;
//			    }
			    if(isreceive.equals("1")){
			    	if (task.getProcessDefinitionId().toString().startsWith("jiaoban")) {
						if (!taskVariables.containsKey("staff")) {
							task.setVariable("staff", staff);
							task.setVariable("staffleader", staffleader);
						} else if (!taskVariables.containsKey("staff1")) {
							task.setVariable("staff1", staff);
							task.setVariable("staffleader1", staffleader);
						} else if (!taskVariables.containsKey("staff2")) {
							task.setVariable("staff2", staff);
							task.setVariable("staffleader2", staffleader);
						} else if (!taskVariables.containsKey("staff3")) {
							task.setVariable("staff3", staff);
							task.setVariable("staffleader3", staffleader);
						}
					} else {
						task.setVariable("staff", staff);
						task.setVariable("staffleader", staffleader);
					}
			    	taskService.complete(task.getId());
			    	
			    }else{
			    	task.setAssignee(staff);
			    }
			   
		
		

	}

}