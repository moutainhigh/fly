package com.xinfang.VO;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinfang.model.FdCase;

public class FdCaseFeedbackVO {
	
	private String state;
	private Date startTime;
	private String nodeUnitCostTime;
	private String nodeUserUnit;
	private String nodeUser;
	private String nodeUserId;
	private String nodeUserHeadSrc;
	private String nodeProceedTime;
	private List<FlowVO> flow;
	private FdCase fdCse;
	private String nodeUserPosition;
	
	private Integer depId;
	private Date createTime;
	
	
	
	
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public String getNodeUserPosition() {
		return nodeUserPosition;
	}
	public void setNodeUserPosition(String nodeUserPosition) {
		this.nodeUserPosition = nodeUserPosition;
	}
	public FdCase getFdCse() {
		return fdCse;
	}
	public void setFdCse(FdCase fdCse) {
		this.fdCse = fdCse;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getNodeUnitCostTime() {
		return nodeUnitCostTime;
	}
	public void setNodeUnitCostTime(String nodeUnitCostTime) {
		this.nodeUnitCostTime = nodeUnitCostTime;
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
	public String getNodeProceedTime() {
		return nodeProceedTime;
	}
	public void setNodeProceedTime(String nodeProceedTime) {
		this.nodeProceedTime = nodeProceedTime;
	}
	public List<FlowVO> getFlow() {
		return flow;
	}
	public void setFlow(List<FlowVO> flow) {
		this.flow = flow;
	}
	
	
	

}
