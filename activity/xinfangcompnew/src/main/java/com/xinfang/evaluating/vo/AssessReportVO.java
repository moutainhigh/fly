package com.xinfang.evaluating.vo;

/**
 * 考核报表VO Created by sunbjx on 2017/5/16.
 */
public class AssessReportVO {
	// 单位
	private String assessUnit;
	// 最终得分
	private Float realityScore;
	// 领导重视
	private Float leader;
	// 基层基础
	private Float base;
	// 非访治理
	private Float manager;
	// 信访事项办理
	private Float deal;
	// 加分
	private Float bonus;
	// 减分
	private Float deduction;
	//种类
	private Integer temlateType;
	//小项名
	private String atandard;
	

	public String getAtandard() {
		return atandard;
	}

	public void setAtandard(String atandard) {
		this.atandard = atandard;
	}

	public Integer getTemlateType() {
		return temlateType;
	}

	public void setTemlateType(Integer temlateType) {
		this.temlateType = temlateType;
	}



	public String getAssessUnit() {
		return assessUnit;
	}

	public void setAssessUnit(String assessUnit) {
		this.assessUnit = assessUnit;
	}

	public Float getRealityScore() {
		return realityScore;
	}

	public void setRealityScore(Float realityScore) {
		this.realityScore = realityScore;
	}

	public Float getLeader() {
		return leader;
	}

	public void setLeader(Float leader) {
		this.leader = leader;
	}

	public Float getBase() {
		return base;
	}

	public void setBase(Float base) {
		this.base = base;
	}

	public Float getManager() {
		return manager;
	}

	public void setManager(Float manager) {
		this.manager = manager;
	}

	public Float getDeal() {
		return deal;
	}

	public void setDeal(Float deal) {
		this.deal = deal;
	}

	public Float getBonus() {
		return bonus;
	}

	public void setBonus(Float bonus) {
		this.bonus = bonus;
	}

	public Float getDeduction() {
		return deduction;
	}

	public void setDeduction(Float deduction) {
		this.deduction = deduction;
	}

}
