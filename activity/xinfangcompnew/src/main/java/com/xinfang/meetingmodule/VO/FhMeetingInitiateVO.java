package com.xinfang.meetingmodule.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-10 11:34
 **/
@ApiModel("发起会议调度实体类，发起会议页面所需要用到的字段")
public class FhMeetingInitiateVO implements java.io.Serializable {

    @ApiModelProperty(value = "会议id", required = false)
    private Integer meetingId;

    @ApiModelProperty(value = "会议时间 yyyy-MM-dd HH:mm:ss", required = true)
    private Date meetingDate;

    @ApiModelProperty(value = "会议性质id  capital(2001, \"重大资金\"),personnel(2002, \"重要的人事任免\"), project(2003, \"重大项目\")", required = true)
    private Integer meetingNatureId; // 会议性质id

    @ApiModelProperty(value = "会议地点", required = true)
    private String meetingAddress;

    @ApiModelProperty(value = "会议主持", required = true)
    private String meetingEmcee;

    @ApiModelProperty(value = "会议类型编号", required = true)
    private Integer meetingTypeId;

    @ApiModelProperty(value = "会议类型名称", required = true)
    private String meetingTypeName;

    @ApiModelProperty(value = "召开部门编号", required = true)
    private Integer departmentId;

    @ApiModelProperty(value = "召开部门名称", required = true)
    private String departmentName;

    @ApiModelProperty(value = "参与部门编号列表字符串,eg:[4,5]", required = true)
    private String departmentIdList;

    @ApiModelProperty(value = "参与部门名称列表字符串,eg:[\"部门1\",\"部门2\"]", required = true)
    private String departmentNameList;

    @ApiModelProperty(value = "会议标题", required = true)
    private String meetingTitle;

    @ApiModelProperty(value = "召开事由", required = true)
    private String meetingContent;

    @ApiModelProperty(value = "反馈截止时间 yyyy-MM-dd HH:mm:ss", required = true)
    private Date feedbackDate;

    @ApiModelProperty(value = "上传文件列表字符串", required = false)
    private String updateFile;

    @ApiModelProperty(value = "关联案件id列表字符串", required = false)
    private String caseIdList;

    @ApiModelProperty(value = "关联案件编号列表字符串", required = false)
    private String caseBhList;

    @ApiModelProperty(value = "关联调度事件id列表字符串", required = false)
    private String dispatchIdList;

    @ApiModelProperty(value = "关联调度事件编号列表字符串", required = false)
    private String dispatchBhList;

    @ApiModelProperty(value = "会议发起人id", required = true)
    private Integer meetingOperateId;

    @ApiModelProperty(value = "会议发起人名称", required = true)
    private String meetingOperateName;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress;
    }

    public String getMeetingEmcee() {
        return meetingEmcee;
    }

    public void setMeetingEmcee(String meetingEmcee) {
        this.meetingEmcee = meetingEmcee;
    }

    public Integer getMeetingTypeId() {
        return meetingTypeId;
    }

    public void setMeetingTypeId(Integer meetingTypeId) {
        this.meetingTypeId = meetingTypeId;
    }

    public String getMeetingTypeName() {
        return meetingTypeName;
    }

    public void setMeetingTypeName(String meetingTypeName) {
        this.meetingTypeName = meetingTypeName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(String departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public String getDepartmentNameList() {
        return departmentNameList;
    }

    public void setDepartmentNameList(String departmentNameList) {
        this.departmentNameList = departmentNameList;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public String getMeetingContent() {
        return meetingContent;
    }

    public void setMeetingContent(String meetingContent) {
        this.meetingContent = meetingContent;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getUpdateFile() {
        return updateFile;
    }

    public void setUpdateFile(String updateFile) {
        this.updateFile = updateFile;
    }

    public String getCaseIdList() {
        return caseIdList;
    }

    public void setCaseIdList(String caseIdList) {
        this.caseIdList = caseIdList;
    }

    public String getCaseBhList() {
        return caseBhList;
    }

    public void setCaseBhList(String caseBhList) {
        this.caseBhList = caseBhList;
    }

    public String getDispatchIdList() {
        return dispatchIdList;
    }

    public void setDispatchIdList(String dispatchIdList) {
        this.dispatchIdList = dispatchIdList;
    }

    public String getDispatchBhList() {
        return dispatchBhList;
    }

    public void setDispatchBhList(String dispatchBhList) {
        this.dispatchBhList = dispatchBhList;
    }

    public Integer getMeetingOperateId() {
        return meetingOperateId;
    }

    public void setMeetingOperateId(Integer meetingOperateId) {
        this.meetingOperateId = meetingOperateId;
    }

    public String getMeetingOperateName() {
        return meetingOperateName;
    }

    public void setMeetingOperateName(String meetingOperateName) {
        this.meetingOperateName = meetingOperateName;
    }

    public Integer getMeetingNatureId() {
        return meetingNatureId;
    }

    public void setMeetingNatureId(Integer meetingNatureId) {
        this.meetingNatureId = meetingNatureId;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }
}
