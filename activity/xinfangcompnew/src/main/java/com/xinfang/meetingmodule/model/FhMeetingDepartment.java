package com.xinfang.meetingmodule.model;

import javax.persistence.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-11 16:01
 **/
@Entity
@Table(name = "fh_meeting_department")
public class FhMeetingDepartment implements java.io.Serializable {
    private int id;
    private Integer meetingId;
    private Integer departmentId;
    private String departmentName;
    private Byte isAttend;
    private String isCause;

//    private Set<FhMeetingInitiate> fhMeetingInitiateSet = new HashSet<>();

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
    @Column(name = "department_id", nullable = true, length = 20)
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "department_name", nullable = true, length = 50)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "is_attend", nullable = true)
    public Byte getIsAttend() {
        return isAttend;
    }

    public void setIsAttend(Byte isAttend) {
        this.isAttend = isAttend;
    }

    @Basic
    @Column(name = "is_cause", nullable = true, length = 1000)
    public String getIsCause() {
        return isCause;
    }

    public void setIsCause(String isCause) {
        this.isCause = isCause;
    }

}
