package com.helome.messagecenter.message.group;

import java.util.List;

public class Group {
	
	private Long  groupId;
	private String groupName;
	private Long  masterId;
	private List<Long> members;
	public Group(Long groupId,Long masterId,String groupName){
		this.groupId = groupId;
		this.masterId = masterId;
		this.groupName = groupName;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public List<Long> getMembers() {
		return members;
	}
	public void setMembers(List<Long> members) {
		this.members = members;
	}
	
	

}
