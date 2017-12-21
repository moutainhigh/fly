package com.xinfang.meetingmodule.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-19 11:40
 **/
public class FhMeetingContinueDetailVO {

    private int continueId;
    private Integer meetingId;
    private Integer meetingDepartmentId;
    private String meetingDepartmentName;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    private Date continueDate;  // 落实时间


    //===================================反馈=======================
    private Integer confirmId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    private String confirmDate;  // 反馈时间

    private Integer continuePersonId;

    private String continuePersonName;

    private String continueContent;

    public int getContinueId() {
        return continueId;
    }

    public void setContinueId(int continueId) {
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

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
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

    public Integer getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(Integer confirmId) {
        this.confirmId = confirmId;
    }
}
