package com.xinfang.meetingmodule.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-10 10:37
 **/
@Entity
@Table(name = "fh_meeting_person")
public class FhMeetingPerson {
    private int id;
    private Integer meetingId;
    private Integer meetingPersonId;
    private String meetingPersonName;
    private String meetingPersonImg;  // 参会人员头像
    private Integer meetingDepartmentId;
    private String meetingDepartmentName;
    private Integer meetingUnitId;
    private String meetingUnitName;
    private String phone;
    private Byte personType;
    private String personTypeName;
    private Byte personState;
    private String personStateName;
    private String absentReason;  // 如果如法参加，缺席原因
    private Byte isRegister;
    private Date registerDate;
    private Integer submitId;
    private String submitName;
    private Date submitDate;

    private FhMeetingInitiate fhMeetingInitiate;  // 会议
//    private FhMeetingSubmitPerson fhMeetingSubmitPerson; // 提交人  已经有submitId，不用加外键

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
    @Column(name = "meeting_person_name", nullable = true, length = 100)
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

    @Basic
    @Column(name = "meeting_unit_id", nullable = true, length = 50)
    public Integer getMeetingUnitId() {
        return meetingUnitId;
    }

    public void setMeetingUnitId(Integer meetingUnitId) {
        this.meetingUnitId = meetingUnitId;
    }

    @Basic
    @Column(name = "meeting_unit_name", nullable = true, length = 10)
    public String getMeetingUnitName() {
        return meetingUnitName;
    }

    public void setMeetingUnitName(String meetingUnitName) {
        this.meetingUnitName = meetingUnitName;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 50)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "person_type", nullable = true)
    public Byte getPersonType() {
        return personType;
    }

    public void setPersonType(Byte personType) {
        this.personType = personType;
    }

    @Basic
    @Column(name = "person_type_name", nullable = true, length = 50)
    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
    }

    @Basic
    @Column(name = "person_state", nullable = true)
    public Byte getPersonState() {
        return personState;
    }

    public void setPersonState(Byte personState) {
        this.personState = personState;
    }

    @Basic
    @Column(name = "person_state_name", nullable = true, length = 50)
    public String getPersonStateName() {
        return personStateName;
    }

    public void setPersonStateName(String personStateName) {
        this.personStateName = personStateName;
    }

    @Basic
    @Column(name = "is_register", nullable = true)
    public Byte getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(Byte isRegister) {
        this.isRegister = isRegister;
    }

    @Basic
    @Column(name = "register_date", nullable = true, length = 50)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }


    @Basic
    @Column(name = "submit_id", nullable = true, length = 10)
    public Integer getSubmitId() {
        return submitId;
    }

    public void setSubmitId(Integer submitId) {
        this.submitId = submitId;
    }

    @Basic
    @Column(name = "submit_name", nullable = true, length = 20)
    public String getSubmitName() {
        return submitName;
    }

    public void setSubmitName(String submitName) {
        this.submitName = submitName;
    }

    @Basic
    @Column(name = "submit_date", nullable = true, length = 50)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    @Basic
    @Column(name = "meeting_person_img", nullable = true, length = 100)
    public String getMeetingPersonImg() {
        return meetingPersonImg;
    }

    public void setMeetingPersonImg(String meetingPersonImg) {
        this.meetingPersonImg = meetingPersonImg;
    }

    @Basic
    @Column(name = "absent_reason", nullable = true, length = 500)
    public String getAbsentReason() {
        return absentReason;
    }

    public void setAbsentReason(String absentReason) {
        this.absentReason = absentReason;
    }

    @ManyToOne
    @JoinColumn(name="meeting_id", referencedColumnName = "meeting_id", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    public FhMeetingInitiate getFhMeetingInitiate() {
        return fhMeetingInitiate;
    }

    public void setFhMeetingInitiate(FhMeetingInitiate fhMeetingInitiate) {
        this.fhMeetingInitiate = fhMeetingInitiate;
    }

//    @ManyToOne
//    @JoinColumn(name="submit_id", referencedColumnName = "meeting_person_id", insertable = false, updatable = false,
//            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
//    @JsonIgnore
//    public FhMeetingSubmitPerson getFhMeetingSubmitPerson() {
//        return fhMeetingSubmitPerson;
//    }
//
//    public void setFhMeetingSubmitPerson(FhMeetingSubmitPerson fhMeetingSubmitPerson) {
//        this.fhMeetingSubmitPerson = fhMeetingSubmitPerson;
//    }

}
