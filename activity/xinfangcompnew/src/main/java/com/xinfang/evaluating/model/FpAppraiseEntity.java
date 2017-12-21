package com.xinfang.evaluating.model;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * 投诉办理评价实体类
 * Created by sunbjx on 2017/5/8.
 */
@Entity
@Table(name = "fp_appraise")
@ApiModel(value="")
public class FpAppraiseEntity {
	 
	  @Id
	  @Column(name="ID")
	  @GeneratedValue(strategy = GenerationType.AUTO)  
	  @ApiModelProperty(value = "主键id ")
    private Integer id;
	  @ApiModelProperty(value = "案件id  *必填")
    private Integer caseId;
    // 评价人姓名
	  @ApiModelProperty(value = "评价人姓名  *必填")
    private String appraiseName;
    // 是否满意信访部门
	  @ApiModelProperty(value = "是否满意信访部门  ")
    private String department;
    // 是否满意信访部门评价时间
	  @ApiModelProperty(value = "是否满意信访部门评价时间   ")
    private String departmentDate;
    // 是否满意责任单位
	  @ApiModelProperty(value = "是否满意责任单位    ")
    private String unit;
    // 是否满意责任单位评价时间
	  @ApiModelProperty(value = "是否满意责任单位评价时间   ")
    private String unitDate;
    // 是否满意办理案件
	  @ApiModelProperty(value = "是否满意办理案件   ")
    private String caseSatisfied;
    // 是否满意办理案件评价时间
	  @ApiModelProperty(value = "是否满意办理案件评价时间   ")
    private String caseDate;
    // 评价方式
	  @ApiModelProperty(value = "评价方式   *必填")
    private String appraiseWay;
    // 问题属地
	  @ApiModelProperty(value = "问题属地  *必填")
    private String appraiseArea;
    // 信访目的
	  @ApiModelProperty(value = "信访目的   *必填")
    private String appraisePurpose;
    // 案件类别
	  @ApiModelProperty(value = "案件类别   *必填")
    private String appraiseGenre;
    // 办理案件评价内容
	  @ApiModelProperty(value = "办理案件评价内容   ")
    private String appraiseContent;
    // 案件办理信访单位
	  @ApiModelProperty(value = "案件办理信访单位   ")
    private String caseDepartment;
    // 案件办理责任单位
	  @ApiModelProperty(value = "案件办理责任单位   ")
    private String caseUnit;
    // 评价人头像
	  @ApiModelProperty(value = "评价人头像   *必填")
    private String appraisePicture;
    // 案件编号
	  @ApiModelProperty(value = "案件编号   *必填")
    private String caseBh;
    // 信访方式
	  @ApiModelProperty(value = "信访方式    *必填")
    private String caseWay;
    // 信访时间
	  @ApiModelProperty(value = "信访时间     *必填")
    private String caseXfDate;
    // 是否满意信访单位ID
	  @ApiModelProperty(value = "是否满意信访单位ID  ")
    private Integer departmentId;
    // 是否满意责任单位编号
	  @ApiModelProperty(value = "是否满意责任单位编号   ")
    private Integer unitId;
    // 是否满意案件编号
	  @ApiModelProperty(value = "是否满意案件编号   ")
    private Integer caseSatisfiedId;
    // 问题属地编号
	  @ApiModelProperty(value = "问题属地编号   *必填")
    private String appraiseAreaId;
    // 信访目的编号
	  @ApiModelProperty(value = "信访目的编号  *必填")
    private String appraisePurposeId;
    // 案件类别编号
	  @ApiModelProperty(value = "案件类别编号   *必填")
    private String appraiseGenreId;
    // 案件办理信访单位编号
	  @ApiModelProperty(value = "案件办理信访单位编号   *必填")
    private String caseDepartmentId;
    // 案件办理责任单位编号
	  @ApiModelProperty(value = "案件办理责任单位编号   *必填")
    private String caseUnitId;
    // 对信访单位的评价内容
	  @ApiModelProperty(value = "对信访单位的评价内容   ")
    private String departmentContent;
    // 对责任单位的评价内容
	  @ApiModelProperty(value = "对责任单位的评价内容   ")
    private String unitContent;
    // 对信访单位是否满意
	  @ApiModelProperty(value = "对信访单位是否满意   ")
    private Byte isDepartment;
    // 对责任单位是否满意
	  @ApiModelProperty(value = "对责任单位是否满意   ")
    private Byte isUnit;
    // 对案件处理结果是否满意
	  @ApiModelProperty(value = "对案件处理结果是否满意   ")
    private Byte isCase;
	  @ApiModelProperty(value = "创建时间 不填 （取系统时间） ")
    private Timestamp gmtCreate;
	  @ApiModelProperty(value = "更新时间  不填（不填取系统时间）")
    private Timestamp gmtModified;
	  @ApiModelProperty(value = "评价人id *必填   ")
    private Integer userid;//评价人id
    
	  
   
    public Integer getId() {
        return id;
    }

    
    
    
    
    
    public Integer getUserid() {
		return userid;
	}






	public void setUserid(Integer userid) {
		this.userid = userid;
	}






	public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "case_id")
    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    @Basic
    @Column(name = "appraise_name")
    public String getAppraiseName() {
        return appraiseName;
    }

    public void setAppraiseName(String appraiseName) {
        this.appraiseName = appraiseName;
    }

    @Basic
    @Column(name = "department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Basic
    @Column(name = "department_date")
    public String getDepartmentDate() {
        return departmentDate;
    }

    public void setDepartmentDate(String departmentDate) {
        this.departmentDate = departmentDate;
    }

    @Basic
    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "unit_date")
    public String getUnitDate() {
        return unitDate;
    }

    public void setUnitDate(String unitDate) {
        this.unitDate = unitDate;
    }

    @Basic
    @Column(name = "case_satisfied")
    public String getCaseSatisfied() {
        return caseSatisfied;
    }

    public void setCaseSatisfied(String caseSatisfied) {
        this.caseSatisfied = caseSatisfied;
    }

    @Basic
    @Column(name = "case_date")
    public String getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(String caseDate) {
        this.caseDate = caseDate;
    }

    @Basic
    @Column(name = "appraise_way")
    public String getAppraiseWay() {
        return appraiseWay;
    }

    public void setAppraiseWay(String appraiseWay) {
        this.appraiseWay = appraiseWay;
    }

    @Basic
    @Column(name = "appraise_area")
    public String getAppraiseArea() {
        return appraiseArea;
    }

    public void setAppraiseArea(String appraiseArea) {
        this.appraiseArea = appraiseArea;
    }

    @Basic
    @Column(name = "appraise_purpose")
    public String getAppraisePurpose() {
        return appraisePurpose;
    }

    public void setAppraisePurpose(String appraisePurpose) {
        this.appraisePurpose = appraisePurpose;
    }

    @Basic
    @Column(name = "appraise_genre")
    public String getAppraiseGenre() {
        return appraiseGenre;
    }

    public void setAppraiseGenre(String appraiseGenre) {
        this.appraiseGenre = appraiseGenre;
    }

    @Basic
    @Column(name = "appraise_content")
    public String getAppraiseContent() {
        return appraiseContent;
    }

    public void setAppraiseContent(String appraiseContent) {
        this.appraiseContent = appraiseContent;
    }

    @Basic
    @Column(name = "case_department")
    public String getCaseDepartment() {
        return caseDepartment;
    }

    public void setCaseDepartment(String caseDepartment) {
        this.caseDepartment = caseDepartment;
    }

    @Basic
    @Column(name = "case_unit")
    public String getCaseUnit() {
        return caseUnit;
    }

    public void setCaseUnit(String caseUnit) {
        this.caseUnit = caseUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FpAppraiseEntity that = (FpAppraiseEntity) o;

        if (id != that.id) return false;
        if (caseId != null ? !caseId.equals(that.caseId) : that.caseId != null) return false;
        if (appraiseName != null ? !appraiseName.equals(that.appraiseName) : that.appraiseName != null) return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;
        if (departmentDate != null ? !departmentDate.equals(that.departmentDate) : that.departmentDate != null)
            return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (unitDate != null ? !unitDate.equals(that.unitDate) : that.unitDate != null) return false;
        if (caseSatisfied != null ? !caseSatisfied.equals(that.caseSatisfied) : that.caseSatisfied != null)
            return false;
        if (caseDate != null ? !caseDate.equals(that.caseDate) : that.caseDate != null) return false;
        if (appraiseWay != null ? !appraiseWay.equals(that.appraiseWay) : that.appraiseWay != null) return false;
        if (appraiseArea != null ? !appraiseArea.equals(that.appraiseArea) : that.appraiseArea != null) return false;
        if (appraisePurpose != null ? !appraisePurpose.equals(that.appraisePurpose) : that.appraisePurpose != null)
            return false;
        if (appraiseGenre != null ? !appraiseGenre.equals(that.appraiseGenre) : that.appraiseGenre != null)
            return false;
        if (appraiseContent != null ? !appraiseContent.equals(that.appraiseContent) : that.appraiseContent != null)
            return false;
        if (caseDepartment != null ? !caseDepartment.equals(that.caseDepartment) : that.caseDepartment != null)
            return false;
        if (caseUnit != null ? !caseUnit.equals(that.caseUnit) : that.caseUnit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (caseId != null ? caseId.hashCode() : 0);
        result = 31 * result + (appraiseName != null ? appraiseName.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (departmentDate != null ? departmentDate.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (unitDate != null ? unitDate.hashCode() : 0);
        result = 31 * result + (caseSatisfied != null ? caseSatisfied.hashCode() : 0);
        result = 31 * result + (caseDate != null ? caseDate.hashCode() : 0);
        result = 31 * result + (appraiseWay != null ? appraiseWay.hashCode() : 0);
        result = 31 * result + (appraiseArea != null ? appraiseArea.hashCode() : 0);
        result = 31 * result + (appraisePurpose != null ? appraisePurpose.hashCode() : 0);
        result = 31 * result + (appraiseGenre != null ? appraiseGenre.hashCode() : 0);
        result = 31 * result + (appraiseContent != null ? appraiseContent.hashCode() : 0);
        result = 31 * result + (caseDepartment != null ? caseDepartment.hashCode() : 0);
        result = 31 * result + (caseUnit != null ? caseUnit.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "appraise_picture")
    public String getAppraisePicture() {
        return appraisePicture;
    }

    public void setAppraisePicture(String appraisePicture) {
        this.appraisePicture = appraisePicture;
    }

    @Basic
    @Column(name = "case_bh")
    public String getCaseBh() {
        return caseBh;
    }

    public void setCaseBh(String caseBh) {
        this.caseBh = caseBh;
    }

    @Basic
    @Column(name = "case_way")
    public String getCaseWay() {
        return caseWay;
    }

    public void setCaseWay(String caseWay) {
        this.caseWay = caseWay;
    }

    @Basic
    @Column(name = "case_xf_date")
    public String getCaseXfDate() {
        return caseXfDate;
    }

    public void setCaseXfDate(String caseXfDate) {
        this.caseXfDate = caseXfDate;
    }

    @Basic
    @Column(name = "department_id")
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "unit_id")
    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    @Basic
    @Column(name = "case_satisfied_id")
    public Integer getCaseSatisfiedId() {
        return caseSatisfiedId;
    }

    public void setCaseSatisfiedId(Integer caseSatisfiedId) {
        this.caseSatisfiedId = caseSatisfiedId;
    }

    @Basic
    @Column(name = "appraise_area_id")
    public String getAppraiseAreaId() {
        return appraiseAreaId;
    }

    public void setAppraiseAreaId(String appraiseAreaId) {
        this.appraiseAreaId = appraiseAreaId;
    }

    @Basic
    @Column(name = "appraise_purpose_id")
    public String getAppraisePurposeId() {
        return appraisePurposeId;
    }

    public void setAppraisePurposeId(String appraisePurposeId) {
        this.appraisePurposeId = appraisePurposeId;
    }

    @Basic
    @Column(name = "appraise_genre_id")
    public String getAppraiseGenreId() {
        return appraiseGenreId;
    }

    public void setAppraiseGenreId(String appraiseGenreId) {
        this.appraiseGenreId = appraiseGenreId;
    }

    @Basic
    @Column(name = "case_department_id")
    public String getCaseDepartmentId() {
        return caseDepartmentId;
    }

    public void setCaseDepartmentId(String caseDepartmentId) {
        this.caseDepartmentId = caseDepartmentId;
    }

    @Basic
    @Column(name = "case_unit_id")
    public String getCaseUnitId() {
        return caseUnitId;
    }

    public void setCaseUnitId(String caseUnitId) {
        this.caseUnitId = caseUnitId;
    }

    @Basic
    @Column(name = "department_content")
    public String getDepartmentContent() {
        return departmentContent;
    }

    public void setDepartmentContent(String departmentContent) {
        this.departmentContent = departmentContent;
    }

    @Basic
    @Column(name = "unit_content")
    public String getUnitContent() {
        return unitContent;
    }

    public void setUnitContent(String unitContent) {
        this.unitContent = unitContent;
    }

    @Basic
    @Column(name = "is_department")
    public Byte getIsDepartment() {
        return isDepartment;
    }

    public void setIsDepartment(Byte isDepartment) {
        this.isDepartment = isDepartment;
    }

    @Basic
    @Column(name = "is_unit")
    public Byte getIsUnit() {
        return isUnit;
    }

    public void setIsUnit(Byte isUnit) {
        this.isUnit = isUnit;
    }

    @Basic
    @Column(name = "is_case")
    public Byte getIsCase() {
        return isCase;
    }

    public void setIsCase(Byte isCase) {
        this.isCase = isCase;
    }

    @Basic
    @Column(name = "gmt_create")
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_modified")
    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }
}
