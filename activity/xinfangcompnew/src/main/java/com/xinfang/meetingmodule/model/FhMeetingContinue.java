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
@Table(name = "fh_meeting_continue")
public class FhMeetingContinue {
    private int continueId;
    private Integer meetingId;
    private Integer meetingDepartmentId;
    private String meetingDepartmentName;
    private Date confirmDate;

    @Id
    @GeneratedValue
    @Column(name = "continue_id", nullable = false)
    public int getContinueId() {
        return continueId;
    }

    public void setContinueId(int continueId) {
        this.continueId = continueId;
    }

    @Basic
    @Column(name = "meeting_id", nullable = true)
    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
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
    @Column(name = "meeting_department_name", nullable = true)
    public String getMeetingDepartmentName() {
        return meetingDepartmentName;
    }

    public void setMeetingDepartmentName(String meetingDepartmentName) {
        this.meetingDepartmentName = meetingDepartmentName;
    }

    @Basic
    @Column(name = "confirm_date", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

}
