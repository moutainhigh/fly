package com.xinfang.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "fd_case_feedback")
@ApiModel(value = "案件反馈表", description = "FdCaseFeedbackAll")
public class FdCaseFeedback {
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键id")
    private Long Id;
    @ApiModelProperty(value = "关联案件IDs")
    private Long caseId;
    @ApiModelProperty(value = "1可用 0不可用")
    private Integer state;
    @ApiModelProperty(value = "类型1 发起  2收文岗派件  3职能部门处理  4审批")
    private Integer type;
    @ApiModelProperty(value = "用户名称")
    private String creater;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "备注,批注")
    private String note;
    @ApiModelProperty(value = "附件1")
    private String enclosure1;
    @ApiModelProperty(value = "附件2")
    private String enclosure2;
    @ApiModelProperty(value = "附件3")
    private String enclosure3;
    @ApiModelProperty(value = "用户id")
    private Long createrId;
    @ApiModelProperty(value = "用户单位名称")
    private String createrCompany;
    @ApiModelProperty(value = "用户部门名称")
    private String createrDep;
    @ApiModelProperty(value = "用户角色名称")
    private String createrRole;
    @ApiModelProperty(value = "案件状态")
    private String caseState;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEnclosure1() {
        return enclosure1;
    }

    public void setEnclosure1(String enclosure1) {
        this.enclosure1 = enclosure1;
    }

    public String getEnclosure2() {
        return enclosure2;
    }

    public void setEnclosure2(String enclosure2) {
        this.enclosure2 = enclosure2;
    }

    public String getEnclosure3() {
        return enclosure3;
    }

    public void setEnclosure3(String enclosure3) {
        this.enclosure3 = enclosure3;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public String getCreaterCompany() {
        return createrCompany;
    }

    public void setCreaterCompany(String createrCompany) {
        this.createrCompany = createrCompany;
    }

    public String getCreaterDep() {
        return createrDep;
    }

    public void setCreaterDep(String createrDep) {
        this.createrDep = createrDep;
    }

    public String getCreaterRole() {
        return createrRole;
    }

    public void setCreaterRole(String createrRole) {
        this.createrRole = createrRole;
    }


    public String getCaseState() {
        return caseState;
    }

    public void setCaseState(String caseState) {
        this.caseState = caseState;
    }
}
