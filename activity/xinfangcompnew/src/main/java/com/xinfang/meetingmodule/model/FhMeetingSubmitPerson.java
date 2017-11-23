package com.xinfang.meetingmodule.model;

import javax.persistence.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-15 17:42
 **/
@Entity
@Table(name = "fh_meeting_submit_person")
public class FhMeetingSubmitPerson implements java.io.Serializable{

    private int id;
    private Integer meetingId;
    private Integer meetingPersonId;
    private String meetingPersonName;
    private Integer meetingDepartmentId;  // 召开部门id
    private String meetingDepartmentName;  // 召开部门name

//    private FcRybAllField fcRybAllField;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "meeting_department_name", nullable = true, length = 100)
    public String getMeetingDepartmentName() {
        return meetingDepartmentName;
    }

    public void setMeetingDepartmentName(String meetingDepartmentName) {
        this.meetingDepartmentName = meetingDepartmentName;
    }

//    @ManyToOne
//    @JoinColumn(name="meeting_person_id", referencedColumnName = "ry_id", insertable = false, updatable = false,
//            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
//    public FcRybAllField getFcRybAllField() {
//        return fcRybAllField;
//    }
//
//    public void setFcRybAllField(FcRybAllField fcRybAllField) {
//        this.fcRybAllField = fcRybAllField;
//    }
}
