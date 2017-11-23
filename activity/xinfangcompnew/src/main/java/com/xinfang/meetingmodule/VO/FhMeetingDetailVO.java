package com.xinfang.meetingmodule.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-16 10:34
 **/

@Entity
@Immutable
@Subselect("SELECT\n" +
        "\ti.meeting_id,\n" +
        "\ti.meeting_date,\n" +
        "\ti.meeting_address,\n" +
        "\ti.meeting_emcee,\n" +
        "\ti.meeting_type_id,\n" +
        "\ti.meeting_type_name,\n" +
        "\ti.department_id,\n" +
        "\ti.department_name,\n" +
        "\ti.meeting_title,\n" +
        "\ti.meeting_content,\n" +
        "\ti.update_file,\n" +
        "\ti.case_id_list,\n" +
        "\ti.case_bh_list,\n" +
        "\ti.dispatch_id_list,\n" +
        "\ti.dispatch_bh_list,\n" +
        "\tCOUNT(p.meeting_person_id) AS meeting_person_amount,\n" +
        "\tCOUNT(NULLIF(p.is_register,30) = 31) AS signin_person_amount\n" +
        "FROM\n" +
        "\tfh_meeting_initiate i\n" +
        "LEFT JOIN fh_meeting_person p ON i.meeting_id = p.meeting_id\n" +
        "GROUP BY\n" +
        "\ti.meeting_id")
public class FhMeetingDetailVO {

    private int meetingId;
    private Date meetingDate;
    private String meetingAddress;
    private String meetingEmcee;
    private Integer meetingTypeId;
    private String meetingTypeName;
    private Integer departmentId;
    private String departmentName;
    private String meetingTitle;
    private String meetingContent;
    private String updateFile;
    private String caseIdList;
    private String caseBhList;
    private String dispatchIdList;
    private String dispatchBhList;

    private Integer meetingPersonAmount; // 参会人数
    private Integer signInPersonAmount;  // 签到人数

    @Id
    @GeneratedValue
    @Column(name = "meeting_id", nullable = false)
    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    @Basic
    @Column(name = "meeting_date", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Basic
    @Column(name = "meeting_address", nullable = true, length = 200)
    public String getMeetingAddress() {
        return meetingAddress;
    }

    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress;
    }

    @Basic
    @Column(name = "meeting_emcee", nullable = true, length = 50)
    public String getMeetingEmcee() {
        return meetingEmcee;
    }

    public void setMeetingEmcee(String meetingEmcee) {
        this.meetingEmcee = meetingEmcee;
    }

    @Basic
    @Column(name = "meeting_type_id", nullable = true, length = 50)
    public Integer getMeetingTypeId() {
        return meetingTypeId;
    }

    public void setMeetingTypeId(Integer meetingTypeId) {
        this.meetingTypeId = meetingTypeId;
    }

    @Basic
    @Column(name = "meeting_type_name", nullable = true, length = 50)
    public String getMeetingTypeName() {
        return meetingTypeName;
    }

    public void setMeetingTypeName(String meetingTypeName) {
        this.meetingTypeName = meetingTypeName;
    }

    @Basic
    @Column(name = "department_id", nullable = true)
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "department_name", nullable = true)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "meeting_title", nullable = true)
    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    @Basic
    @Column(name = "meeting_content", nullable = true)
    public String getMeetingContent() {
        return meetingContent;
    }

    public void setMeetingContent(String meetingContent) {
        this.meetingContent = meetingContent;
    }

    @Basic
    @Column(name = "update_file", nullable = true)
    public String getUpdateFile() {
        return updateFile;
    }

    public void setUpdateFile(String updateFile) {
        this.updateFile = updateFile;
    }

    @Basic
    @Column(name = "case_id_list", nullable = true)
    public String getCaseIdList() {
        return caseIdList;
    }

    public void setCaseIdList(String caseIdList) {
        this.caseIdList = caseIdList;
    }

    @Basic
    @Column(name = "case_bh_list", nullable = true)
    public String getCaseBhList() {
        return caseBhList;
    }

    public void setCaseBhList(String caseBhList) {
        this.caseBhList = caseBhList;
    }

    @Basic
    @Column(name = "dispatch_id_list", nullable = true)
    public String getDispatchIdList() {
        return dispatchIdList;
    }

    public void setDispatchIdList(String dispatchIdList) {
        this.dispatchIdList = dispatchIdList;
    }

    @Basic
    @Column(name = "dispatch_bh_list", nullable = true)
    public String getDispatchBhList() {
        return dispatchBhList;
    }

    public void setDispatchBhList(String dispatchBhList) {
        this.dispatchBhList = dispatchBhList;
    }

    @Basic
    @Column(name = "meeting_person_amount", nullable = true)
    public Integer getMeetingPersonAmount() {
        return meetingPersonAmount;
    }

    public void setMeetingPersonAmount(Integer meetingPersonAmount) {
        this.meetingPersonAmount = meetingPersonAmount;
    }

    @Basic
    @Column(name = "signin_person_amount", nullable = true)
    public Integer getSignInPersonAmount() {
        return signInPersonAmount;
    }

    public void setSignInPersonAmount(Integer signInPersonAmount) {
        this.signInPersonAmount = signInPersonAmount;
    }
}
