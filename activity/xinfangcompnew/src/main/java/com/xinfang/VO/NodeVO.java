package com.xinfang.VO;

/**
 * 
 * @title NodeVO.java
 * @description 当前处理节点信息
 * @author ZhangHuaRong   
 * @update 2017年5月4日 下午5:02:53
 * Copyright (c)2012 chantsoft-版权所有
 */
public class NodeVO {
	
	private int state;
	
	private LogInInfo currentUserInfo;
	
	private LogInInfo endUserInfo;
	
	private String taskname;
	
	private String startTime;
	
	private String limitTime;
	
	private String pastDay;
	
	private String warn;
	
	private Float limitdaynum;
	

	public Float getLimitdaynum() {
		return limitdaynum;
	}

	public void setLimitdaynum(Float limitdaynum) {
		this.limitdaynum = limitdaynum;
	}

	

	public String getWarn() {
		return warn;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}

	

	public String getPastDay() {
		return pastDay;
	}

	public void setPastDay(String pastDay) {
		this.pastDay = pastDay;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public LogInInfo getCurrentUserInfo() {
		return currentUserInfo;
	}

	public void setCurrentUserInfo(LogInInfo currentUserInfo) {
		this.currentUserInfo = currentUserInfo;
	}

	public LogInInfo getEndUserInfo() {
		return endUserInfo;
	}

	public void setEndUserInfo(LogInInfo endUserInfo) {
		this.endUserInfo = endUserInfo;
	}
	
	

}
