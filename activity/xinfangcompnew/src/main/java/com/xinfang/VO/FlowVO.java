package com.xinfang.VO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FlowVO {
	private String nodeUserUnit;
	private String nodeUser;
	private String nodeUserId;
	private String nodeUserHeadSrc;
	private Date nodeHandleTime;
	private String nodeUnitCostTime;
	private String nodeCostTime;
	private String currentCostTime;
	private String nodeHandleState;
	private String files;
	private String nodeUserPosition;
	
	private Date createTime;
	private Integer id;
	
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getNodeUserPosition() {
		return nodeUserPosition;
	}
	public void setNodeUserPosition(String nodeUserPosition) {
		this.nodeUserPosition = nodeUserPosition;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getNodeUserUnit() {
		return nodeUserUnit;
	}
	public void setNodeUserUnit(String nodeUserUnit) {
		this.nodeUserUnit = nodeUserUnit;
	}
	public String getNodeUser() {
		return nodeUser;
	}
	public void setNodeUser(String nodeUser) {
		this.nodeUser = nodeUser;
	}
	public String getNodeUserId() {
		return nodeUserId;
	}
	public void setNodeUserId(String nodeUserId) {
		this.nodeUserId = nodeUserId;
	}
	public String getNodeUserHeadSrc() {
		return nodeUserHeadSrc;
	}
	public void setNodeUserHeadSrc(String nodeUserHeadSrc) {
		this.nodeUserHeadSrc = nodeUserHeadSrc;
	}
	
	public Date getNodeHandleTime() {
		return nodeHandleTime;
	}
	public void setNodeHandleTime(Date nodeHandleTime) {
		this.nodeHandleTime = nodeHandleTime;
	}
	public String getNodeUnitCostTime() {
		return nodeUnitCostTime;
	}
	public void setNodeUnitCostTime(String nodeUnitCostTime) {
		this.nodeUnitCostTime = nodeUnitCostTime;
	}
	public String getNodeCostTime() {
		return nodeCostTime;
	}
	public void setNodeCostTime(String nodeCostTime) {
		this.nodeCostTime = nodeCostTime;
	}
	public String getCurrentCostTime() {
		return currentCostTime;
	}
	public void setCurrentCostTime(String currentCostTime) {
		this.currentCostTime = currentCostTime;
	}
	public String getNodeHandleState() {
		return nodeHandleState;
	}
	public void setNodeHandleState(String nodeHandleState) {
		this.nodeHandleState = nodeHandleState;
	}
	
	

}
