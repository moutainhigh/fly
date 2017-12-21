package com.xinfang.meetingmodule.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-10 10:37
 **/
@Entity
@Table(name = "fh_meeting_continue_confirm")
public class FhMeetingContinueConfirm {
    private int confirmId;
    private Integer continueId;
    private Integer meetingId;
    private Integer meetingDepartmentId;
    private String meetingDepartmentName;
    private Date confirmDate;
    private Date continueDate;
    private Integer continuePersonId;
    private String continuePersonName;
    private String continueContent;
    private String continueFile;
    private String continueImage;

    @Id
    @GeneratedValue
    @Column(name = "confirm_id", nullable = false)
    public int getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(int confirmId) {
        this.confirmId = confirmId;
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
    @Column(name = "meeting_department_name", nullable = true, length = 50)
    public String getMeetingDepartmentName() {
        return meetingDepartmentName;
    }

    public void setMeetingDepartmentName(String meetingDepartmentName) {
        this.meetingDepartmentName = meetingDepartmentName;
    }

    @Basic
    @Column(name = "confirm_date", nullable = true, length = 50)
    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    @Basic
    @Column(name = "continue_date", nullable = true, length = 50)
    public Date getContinueDate() {
        return continueDate;
    }

    public void setContinueDate(Date continueDate) {
        this.continueDate = continueDate;
    }

    @Basic
    @Column(name = "continue_person_id", nullable = true, length = 50)
    public Integer getContinuePersonId() {
        return continuePersonId;
    }

    public void setContinuePersonId(Integer continuePersonId) {
        this.continuePersonId = continuePersonId;
    }

    @Basic
    @Column(name = "continue_person_name", nullable = true, length = 50)
    public String getContinuePersonName() {
        return continuePersonName;
    }

    public void setContinuePersonName(String continuePersonName) {
        this.continuePersonName = continuePersonName;
    }

    @Basic
    @Column(name = "continue_content", nullable = true, length = 10000)
    public String getContinueContent() {
        return continueContent;
    }

    public void setContinueContent(String continueContent) {
        this.continueContent = continueContent;
    }

    @Basic
    @Column(name = "continue_file", nullable = true, length = 1000)
    public String getContinueFile() {
        return continueFile;
    }

    public void setContinueFile(String continueFile) {
        this.continueFile = continueFile;
    }

    @Basic
    @Column(name = "continue_image", nullable = true, length = 1000)
    public String getContinueImage() {
        return continueImage;
    }

    public void setContinueImage(String continueImage) {
        this.continueImage = continueImage;
    }
}
