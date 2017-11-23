package com.xinfang.VO;

import java.io.Serializable;
import java.util.List;
import org.activiti.engine.task.Comment;

import com.xinfang.model.FdCase;
import com.xinfang.model.FdGuest;

public class TaskVO  implements Serializable {
	
	
	 /**serialVersionUID TODO*/ 
	
	private static final long serialVersionUID = 1L;

	
	private FdCase fdCase;
	
	
	private String taskId;
	
	private String taskName;
	
	private String taskDefinitionKey;
	
	private String procDefId;
	
	private String proInsId;
	
	private List<Comment> comments;
	
	private 	Object formdate;
	
	LogInInfo userinfo;
	
	private FdGuest guest;
	
	private Integer needReceipt;
	private List<LogInInfo> swgUserList;
	
	private Integer caseHandleState;
	
	
	
	

	public Integer getCaseHandleState() {
		return caseHandleState;
	}




	public void setCaseHandleState(Integer caseHandleState) {
		this.caseHandleState = caseHandleState;
	}




	public FdGuest getGuest() {
		return guest;
	}




	public void setGuest(FdGuest guest) {
		this.guest = guest;
	}




	public LogInInfo getUserinfo() {
		return userinfo;
	}




	public void setUserinfo(LogInInfo userinfo) {
		this.userinfo = userinfo;
	}




	public Object getFormdate() {
		return formdate;
	}




	public void setFormdate(Object formdate) {
		this.formdate = formdate;
	}




	public List<Comment> getComments() {
		return comments;
	}




	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}




	public String getProInsId() {
		return proInsId;
	}




	public void setProInsId(String proInsId) {
		this.proInsId = proInsId;
	}




	public String getProcDefId() {
		return procDefId;
	}




	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}




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




	public FdCase getFdCase() {
		return fdCase;
	}




	public void setFdCase(FdCase fdCase) {
		this.fdCase = fdCase;
	}




	public Integer getNeedReceipt() {
		return needReceipt;
	}




	public void setNeedReceipt(Integer needReceipt) {
		this.needReceipt = needReceipt;
	}

	public List<LogInInfo> getSwgUserList() {
		return swgUserList;
	}




	public void setSwgUserList(List<LogInInfo> swgUserList) {
		this.swgUserList = swgUserList;
	}







	  
	
	
	





	
	

}
