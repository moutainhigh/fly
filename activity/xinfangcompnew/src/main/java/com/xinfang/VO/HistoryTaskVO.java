package com.xinfang.VO;

import org.activiti.engine.history.HistoricTaskInstance;

import com.xinfang.model.FdCase;

public class HistoryTaskVO {
	
	private HistoricTaskInstance task;
	
	private FdCase fdCase;
	
	private TaskVO taskVO;
	
	private LogInInfo endpeople;
	

	public LogInInfo getEndpeople() {
		return endpeople;
	}

	public void setEndpeople(LogInInfo endpeople) {
		this.endpeople = endpeople;
	}

	public TaskVO getTaskVO() {
		return taskVO;
	}

	public void setTaskVO(TaskVO taskVO) {
		this.taskVO = taskVO;
	}

	

	public HistoryTaskVO(HistoricTaskInstance task, FdCase fdCase) {
		super();
		this.task = task;
		this.fdCase = fdCase;
	}

	public HistoricTaskInstance getTask() {
		return task;
	}

	public void setTask(HistoricTaskInstance task) {
		this.task = task;
	}

	public FdCase getFdCase() {
		return fdCase;
	}

	public void setFdCase(FdCase fdCase) {
		this.fdCase = fdCase;
	}
	
	
	

}
