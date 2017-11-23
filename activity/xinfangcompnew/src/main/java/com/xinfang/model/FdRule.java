package com.xinfang.model;

public class FdRule {
	//主键id
	private Integer id;
	//扩展 比如说根据案件类型有不同的规则  
	private Integer type;
	//0不可用 1可用
	private Integer state;
	//信访登记阶段  7.2%
	private Float register; 
	//区信访 或职能部门转交阶段 16.7%
	private Float forword;
	//职能部门出具受理告知书或者其他答复阶段 33.3%
	private Float reply;
	//责任单位出具意见答复书阶段  83.3%
	private Float replyLetter;
	//区县审批   9.4%
	private Float areaApproval;
	//市级审批    7.2%
	private Float cityApproval;
	//阶段预警1 50%
	private Float warn1;
	//阶段预警2 75%
	private Float warn2;
	//阶段预警3  100%
	private Float warn3;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Float getRegister() {
		return register;
	}

	public void setRegister(Float register) {
		this.register = register;
	}

	public Float getForword() {
		return forword;
	}

	public void setForword(Float forword) {
		this.forword = forword;
	}

	public Float getReply() {
		return reply;
	}

	public void setReply(Float reply) {
		this.reply = reply;
	}

	public Float getReplyLetter() {
		return replyLetter;
	}

	public void setReplyLetter(Float replyLetter) {
		this.replyLetter = replyLetter;
	}

	public Float getAreaApproval() {
		return areaApproval;
	}

	public void setAreaApproval(Float areaApproval) {
		this.areaApproval = areaApproval;
	}

	public Float getCityApproval() {
		return cityApproval;
	}

	public void setCityApproval(Float cityApproval) {
		this.cityApproval = cityApproval;
	}

	public Float getWarn1() {
		return warn1;
	}

	public void setWarn1(Float warn1) {
		this.warn1 = warn1;
	}

	public Float getWarn2() {
		return warn2;
	}

	public void setWarn2(Float warn2) {
		this.warn2 = warn2;
	}

	public Float getWarn3() {
		return warn3;
	}

	public void setWarn3(Float warn3) {
		this.warn3 = warn3;
	}
	
	

}
