package com.xinfang.VO;


import net.sf.json.JSONArray;



public class TemplateMaintenanceVO {
	//考核积分编号
	private Integer scoreId;
	//模板编号	
	private Integer templateId;
	
	//模板序号
	private String templateNumber;
	
	//评分标准
	private String standard;
	
	//设定分数
	private Float setvalue;
	
	//数目
	private Integer number;
	
	//系数
	private Float cofficient;
	
	//分值控制
	private Integer control;
	
	//录入类型
	private Integer inType;
	
	//描述
	private String desc;
	
	//模板层级编号
	private String level;
	
	//排序
	private Integer sort;
	
	//类型
	private Integer templateType;
	//父ID
	private Integer pid;
	//考核单位
	private String assessUnit;
	//考核项目名称
	private String assessName;
	//发起单位
	private String sponsorUnitName;
	//发起人姓名
	private String nitiatorName;
	//发起人头像
	private String initiatorHeadsrc;
	//创建时间
	private String gmtTime;	
	//实际得分
	private Float realityScore; 
    //子集
	private JSONArray children =new JSONArray();
	//父级评分标准
	private String parentStandard;
	
	
	public String getParentStandard() {
		return parentStandard;
	}

	public void setParentStandard(String parentStandard) {
		this.parentStandard = parentStandard;
	}

	public Integer getScoreId() {
		return scoreId;
	}

	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
	}

	public Float getRealityScore() {
		return realityScore;
	}

	public void setRealityScore(Float realityScore) {
		this.realityScore = realityScore;
	}

	public String getAssessUnit() {
		return assessUnit;
	}

	public void setAssessUnit(String assessUnit) {
		this.assessUnit = assessUnit;
	}

	public String getAssessName() {
		return assessName;
	}

	public void setAssessName(String assessName) {
		this.assessName = assessName;
	}

	public String getSponsorUnitName() {
		return sponsorUnitName;
	}

	public void setSponsorUnitName(String sponsorUnitName) {
		this.sponsorUnitName = sponsorUnitName;
	}

	public String getNitiatorName() {
		return nitiatorName;
	}

	public void setNitiatorName(String nitiatorName) {
		this.nitiatorName = nitiatorName;
	}

	public String getInitiatorHeadsrc() {
		return initiatorHeadsrc;
	}

	public void setInitiatorHeadsrc(String initiatorHeadsrc) {
		this.initiatorHeadsrc = initiatorHeadsrc;
	}

	public String getGmtTime() {
		return gmtTime;
	}

	public void setGmtTime(String gmtTime) {
		this.gmtTime = gmtTime;
	}

	public JSONArray getChildren() {
		return children;
	}

	public void setChildren(JSONArray children) {
		this.children = children;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getTemplateNumber() {
		return templateNumber;
	}

	public void setTemplateNumber(String templateNumber) {
		this.templateNumber = templateNumber;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Float getSetvalue() {
		return setvalue;
	}

	public void setSetvalue(Float setvalue) {
		this.setvalue = setvalue;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Float getCofficient() {
		return cofficient;
	}

	public void setCofficient(Float cofficient) {
		this.cofficient = cofficient;
	}

	public Integer getControl() {
		return control;
	}

	public void setControl(Integer control) {
		this.control = control;
	}

	public Integer getInType() {
		return inType;
	}

	public void setInType(Integer inType) {
		this.inType = inType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}



	
}
