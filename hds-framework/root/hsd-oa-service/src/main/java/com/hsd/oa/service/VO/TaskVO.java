package com.hsd.oa.service.VO;

import java.io.Serializable;
import java.util.List;

import org.activiti.engine.task.Comment;

public class TaskVO  implements Serializable {
	
	
	 /**serialVersionUID TODO*/ 
	
	private static final long serialVersionUID = 1L;

	
	
	
	private String taskId;
	
	private String taskName;
	
	private String taskDefinitionKey;
	
	private String procDefId;
	
	private String proInsId;
	
	
	private 	Object formdate;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getProInsId() {
		return proInsId;
	}

	public void setProInsId(String proInsId) {
		this.proInsId = proInsId;
	}

	

	public Object getFormdate() {
		return formdate;
	}

	public void setFormdate(Object formdate) {
		this.formdate = formdate;
	}
	
	




	  
	
	
	





	
	

}
