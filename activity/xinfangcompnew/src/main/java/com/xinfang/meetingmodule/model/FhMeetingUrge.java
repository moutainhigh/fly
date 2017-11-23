package com.xinfang.meetingmodule.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-10 10:37
 **/
@Entity
@Table(name = "fh_meeting_urge")
public class FhMeetingUrge {
    private int urgeId;
    private Integer meetingId;
    private Integer personId;
    private Integer meetingPersonId;
    private String meetingPersonName;
    private Integer meetingDepartmentId;
    private String meetingDepartmentName;
    private Integer meetingUnitId;
    private String meetingUnitName;
    private String phone;
    private Date urgeDate;
    private Integer urgePersonId;
    private String urgePersonName;

    @Id
    @GeneratedValue
    @Column(name = "urge_id", nullable = false)
    public int getUrgeId() {
        return urgeId;
    }

    public void setUrgeId(int urgeId) {
        this.urgeId = urgeId;
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
    @Column(name = "person_id", nullable = true)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "meeting_person_id", nullable = true, length = 50)
    public Integer getMeetingPersonId() {
        return meetingPersonId;
    }

    public void setMeetingPersonId(Integer meetingPersonId) {
        this.meetingPersonId = meetingPersonId;
    }

    @Basic
    @Column(name = "meeting_person_name", nullable = true, length = 10)
    public String getMeetingPersonName() {
        return meetingPersonName;
    }

    public void setMeetingPersonName(String meetingPersonName) {
        this.meetingPersonName = meetingPersonName;
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
    @Column(name = "meeting_unit_id", nullable = true, length = 50)
    public Integer getMeetingUnitId() {
        return meetingUnitId;
    }

    public void setMeetingUnitId(Integer meetingUnitId) {
        this.meetingUnitId = meetingUnitId;
    }

    @Basic
    @Column(name = "meeting_unit_name", nullable = true, length = 50)
    public String getMeetingUnitName() {
        return meetingUnitName;
    }

    public void setMeetingUnitName(String meetingUnitName) {
        this.meetingUnitName = meetingUnitName;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "urge_date", nullable = true, length = 50)
    public Date getUrgeDate() {
        return urgeDate;
    }

    public void setUrgeDate(Date urgeDate) {
        this.urgeDate = urgeDate;
    }

    @Basic
    @Column(name = "urge_person_id", nullable = true, length = 50)
    public Integer getUrgePersonId() {
        return urgePersonId;
    }

    public void setUrgePersonId(Integer urgePersonId) {
        this.urgePersonId = urgePersonId;
    }

    @Basic
    @Column(name = "urge_person_name", nullable = true, length = 10)
    public String getUrgePersonName() {
        return urgePersonName;
    }

    public void setUrgePersonName(String urgePersonName) {
        this.urgePersonName = urgePersonName;
    }

}
