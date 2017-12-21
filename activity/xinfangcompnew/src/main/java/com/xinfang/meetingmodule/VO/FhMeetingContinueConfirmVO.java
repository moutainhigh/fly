package com.xinfang.meetingmodule.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-17 14:44
 **/
public class FhMeetingContinueConfirmVO {

    @ApiModelProperty(value = "落实情况编号id", required = true)
    private Integer continueId;

    @ApiModelProperty(value = "会议id", required = true)
    private Integer meetingId;

    @ApiModelProperty(value = "部门id")
    private Integer meetingDepartmentId;

    @ApiModelProperty(value = "部门名称")
    private String meetingDepartmentName;

    @ApiModelProperty(value = "反馈时间，录入不填，自动生成", required = false, hidden = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    private Date continueDate;

    @ApiModelProperty(value = "反馈人id")
    private Integer continuePersonId;

    @ApiModelProperty(value = "反馈人名称")
    private String continuePersonName;

    @ApiModelProperty(value = "反馈内容")
    private String continueContent;

    @ApiModelProperty(value = "反馈人文件")
    private String continueFile;

    @ApiModelProperty(value = "反馈人影像")
    private String continueImage;

    public Integer getContinueId() {
        return continueId;
    }

    public void setContinueId(Integer continueId) {
        this.continueId = continueId;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getMeetingDepartmentId() {
        return meetingDepartmentId;
    }

    public void setMeetingDepartmentId(Integer meetingDepartmentId) {
        this.meetingDepartmentId = meetingDepartmentId;
    }

    public String getMeetingDepartmentName() {
        return meetingDepartmentName;
    }

    public void setMeetingDepartmentName(String meetingDepartmentName) {
        this.meetingDepartmentName = meetingDepartmentName;
    }

    public Date getContinueDate() {
        return continueDate;
    }

    public void setContinueDate(Date continueDate) {
        this.continueDate = continueDate;
    }

    public Integer getContinuePersonId() {
        return continuePersonId;
    }

    public void setContinuePersonId(Integer continuePersonId) {
        this.continuePersonId = continuePersonId;
    }

    public String getContinuePersonName() {
        return continuePersonName;
    }

    public void setContinuePersonName(String continuePersonName) {
        this.continuePersonName = continuePersonName;
    }

    public String getContinueContent() {
        return continueContent;
    }

    public void setContinueContent(String continueContent) {
        this.continueContent = continueContent;
    }

    public String getContinueFile() {
        return continueFile;
    }

    public void setContinueFile(String continueFile) {
        this.continueFile = continueFile;
    }

    public String getContinueImage() {
        return continueImage;
    }

    public void setContinueImage(String continueImage) {
        this.continueImage = continueImage;
    }
}
