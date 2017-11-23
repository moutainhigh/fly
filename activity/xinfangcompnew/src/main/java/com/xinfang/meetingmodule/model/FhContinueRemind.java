package com.xinfang.meetingmodule.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-10 10:37
 **/
@Entity
@Table(name = "fh_continue_remind")
public class FhContinueRemind {
    private int remindId;
    private Integer continueId;
    private Integer meetingDepartmentId;
    private String meetingDepartmentName;
    private Date remindDate;
    private Integer remindPersonId;
    private String remindPersonName;

    @Id
    @GeneratedValue
    @Column(name = "remind_id", nullable = false)
    public int getRemindId() {
        return remindId;
    }

    public void setRemindId(int remindId) {
        this.remindId = remindId;
    }

    @Basic
    @Column(name = "continue_id", nullable = true)
    public Integer getContinueId() {
        return continueId;
    }

    public void setContinueId(Integer continueId) {
        this.continueId = continueId;
    }

    @Basic
    @Column(name = "meeting_department_id", nullable = true, length = 50)
    public Integer getMeetingDepartmentId() {
        return meetingDepartmentId;
    }

    public void setMeetingDepartmentId(Integer meetingDepartmentId) {
        this.meetingDepartmentId = meetingDepartmentId;
    }

    @Basic
    @Column(name = "meeting_department_name", nullable = true, length = 50)
    public String getMeetingDepartmentName() {
        return meetingDepartmentName;
    }

    public void setMeetingDepartmentName(String meetingDepartmentName) {
        this.meetingDepartmentName = meetingDepartmentName;
    }

    @Basic
    @Column(name = "remind_date", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    public Date getRemindDate() {
        return remindDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }

    @Basic
    @Column(name = "remind_person_id", nullable = true, length = 50)
    public Integer getRemindPersonId() {
        return remindPersonId;
    }

    public void setRemindPersonId(Integer remindPersonId) {
        this.remindPersonId = remindPersonId;
    }

    @Basic
    @Column(name = "remind_person_name", nullable = true, length = 10)
    public String getRemindPersonName() {
        return remindPersonName;
    }

    public void setRemindPersonName(String remindPersonName) {
        this.remindPersonName = remindPersonName;
    }

}
