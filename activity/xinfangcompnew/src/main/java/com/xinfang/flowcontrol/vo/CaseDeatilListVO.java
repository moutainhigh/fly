package com.xinfang.flowcontrol.vo;

public class CaseDeatilListVO {
	//案件ID
	private Integer caseId;
	//信访编号
	private String petitionCode;
	//信访人姓名
	private String petitionNames;
	//信访方式
	private String petitionWays;
	//案件归属地
	private String belongtoAdress;
	//事件描述
	private String desc;
	//处理单位
	private String currentUnit;
	//登记时间
	private String gmtTime;
	//状态
	private String state;
	
	public Integer getCaseId() {
		return caseId;
	}
	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}
	public String getPetitionCode() {
		return petitionCode;
	}
	public void setPetitionCode(String petitionCode) {
		this.petitionCode = petitionCode;
	}
	public String getPetitionNames() {
		return petitionNames;
	}
	public void setPetitionNames(String petitionNames) {
		this.petitionNames = petitionNames;
	}
	public String getPetitionWays() {
		return petitionWays;
	}
	public void setPetitionWays(String petitionWays) {
		this.petitionWays = petitionWays;
	}
	public String getBelongtoAdress() {
		return belongtoAdress;
	}
	public void setBelongtoAdress(String belongtoAdress) {
		this.belongtoAdress = belongtoAdress;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCurrentUnit() {
		return currentUnit;
	}
	public void setCurrentUnit(String currentUnit) {
		this.currentUnit = currentUnit;
	}
	public String getGmtTime() {
		return gmtTime;
	}
	public void setGmtTime(String gmtTime) {
		this.gmtTime = gmtTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
