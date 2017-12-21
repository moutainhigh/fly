package com.xinfang.evaluating.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * 
 * @author zhangbo
 *   模板维护MODEL
 */
@Entity
@Table(name = "fp_check_templet")
public class TemplateMaintenanceEntity {
	@ApiModelProperty(value="模板ID",required=true)
	//模板编号	
	private Integer templateId;
	@ApiModelProperty(value="模板序号",required=false)
	//模板序号
	private String templateNumber;
	@ApiModelProperty(value="评分标准",required=false)
	//评分标准
	private String standard;
	@ApiModelProperty(value="设定分数",required=false)
	//设定分数
	private Float setvalue;
	@ApiModelProperty(value="数目",required=false)
	//数目
	private Integer number;
	@ApiModelProperty(value="系数",required=false)
	//系数
	private Float cofficient;
	@ApiModelProperty(value="分值控制",required=false)
	//分值控制
	private Integer control;
	@ApiModelProperty(value="录入类型",required=false)
	//录入类型
	private Integer inType;
	@ApiModelProperty(value="描述",required=false)
	//描述
	private String desc;
	@ApiModelProperty(value="模板层级编号",required=false)
	//模板层级编号
	private String level;
	@ApiModelProperty(value="排序",required=false)
	//排序
	private Integer sort;
	@ApiModelProperty(value="类型(0 A类，1B类)",required=false)
	//类型
	private Integer templateType;
	@ApiModelProperty(value="父ID（上级ID）",required=false)
	//父ID
	private Integer pId;
	//实际分数
	private Float realityScore;
	@Basic
	@Column(name="reality_score")
	public Float getRealityScore() {
		return realityScore;
	}
	public void setRealityScore(Float realityScore) {
		this.realityScore = realityScore;
	}
	@Basic
	@Column(name="FID")
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	@Id
	@Column(name="templet_id")
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	@Basic
	@Column(name="templet_number")
	public String getTemplateNumber() {
		return templateNumber;
	}
	public void setTemplateNumber(String templateNumber) {
		this.templateNumber = templateNumber;
	}
	@Basic
	@Column(name="standard")
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	@Basic
	@Column(name="setvalue")
	public Float getSetvalue() {
		return setvalue;
	}
	public void setSetvalue(Float setvalue) {
		this.setvalue = setvalue;
	}
	@Basic
	@Column(name="number")
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	@Basic
	@Column(name="coefficient")
	public Float getCofficient() {
		return cofficient;
	}
	public void setCofficient(Float cofficient) {
		this.cofficient = cofficient;
	}
	@Basic
	@Column(name="control")
	public Integer getControl() {
		return control;
	}
	public void setControl(Integer control) {
		this.control = control;
	}
	@Basic
	@Column(name="entering_type")
	public Integer getInType() {
		return inType;
	}
	public void setInType(Integer inType) {
		this.inType = inType;
	}
	@Basic
	@Column(name="descs")
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Basic
	@Column(name="levels")
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	@Basic
	@Column(name="sort")
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Basic
	@Column(name="templet_type")
	public Integer getTemplateType() {
		return templateType;
	}
	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}
	

}
