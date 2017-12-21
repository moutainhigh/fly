package com.xinfang.meetingmodule.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-11 16:28
 **/
@Entity
@Table(name = "fh_meeting_initiate")
public class FhMeetingInitiate implements java.io.Serializable {
    private int meetingId;
    private Date meetingDate;
    private Integer meetingNatureId; // 会议性质id
    private String meetingAddress;
    private String meetingEmcee;
    private Integer meetingTypeId;
    private String meetingTypeName;
    private Integer departmentId;
    private String departmentName;
    private String meetingTitle;
    private String meetingContent;
    private Date feedbackDate;
    private String updateFile;
    private String caseIdList;
    private String caseBhList;
    private String dispatchIdList;
    private String dispatchBhList;
    private String meetingOperateDate;
    private Integer meetingOperateId;
    private String meetingOperateName;
    private Byte isCase;
    private Byte isDispatch;
    private Byte isContinue;
    private Byte isLead;
    private Byte isAccomplish;
    private Date meetingBeginDate;
    private Date meetingEndDate;
    private String meetingRecord;
    private String meetingRecordImage;
    private String meetingRecordFile;
    private Date meetingRecordDate;
    private String meetingRecordPersonId;
    private String meetingRecordPersonName;
    private String meetingProcess;
    private String meetingProcessFile;
    private Date meetingProcessDate;
    private String meetingProcessPersonId;
    private String meetingProcessPersonName;

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
    @Column(name = "department_id", nullable = true, length = 50)
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
    @Column(name = "meeting_title", nullable = true, length = 200)
    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    @Basic
    @Column(name = "meeting_content", nullable = true, length = 2000)
    public String getMeetingContent() {
        return meetingContent;
    }

    public void setMeetingContent(String meetingContent) {
        this.meetingContent = meetingContent;
    }

    @Basic
    @Column(name = "feedback_date", nullable = true)
    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Basic
    @Column(name = "update_file", nullable = true, length = 1000)
    public String getUpdateFile() {
        return updateFile;
    }

    public void setUpdateFile(String updateFile) {
        this.updateFile = updateFile;
    }

    @Basic
    @Column(name = "case_id_list", nullable = true, length = 500)
    public String getCaseIdList() {
        return caseIdList;
    }

    public void setCaseIdList(String caseIdList) {
        this.caseIdList = caseIdList;
    }

    @Basic
    @Column(name = "case_bh_list", nullable = true, length = 500)
    public String getCaseBhList() {
        return caseBhList;
    }

    public void setCaseBhList(String caseBhList) {
        this.caseBhList = caseBhList;
    }

    @Basic
    @Column(name = "dispatch_id_list", nullable = true, length = 500)
    public String getDispatchIdList() {
        return dispatchIdList;
    }

    public void setDispatchIdList(String dispatchIdList) {
        this.dispatchIdList = dispatchIdList;
    }

    @Basic
    @Column(name = "dispatch_bh_list", nullable = true, length = 500)
    public String getDispatchBhList() {
        return dispatchBhList;
    }

    public void setDispatchBhList(String dispatchBhList) {
        this.dispatchBhList = dispatchBhList;
    }

    @Basic
    @Column(name = "meeting_operate_date", nullable = true, length = 50)
    public String getMeetingOperateDate() {
        return meetingOperateDate;
    }

    public void setMeetingOperateDate(String meetingOperateDate) {
        this.meetingOperateDate = meetingOperateDate;
    }

    @Basic
    @Column(name = "meeting_operate_id", nullable = true, length = 50)
    public Integer getMeetingOperateId() {
        return meetingOperateId;
    }

    public void setMeetingOperateId(Integer meetingOperateId) {
        this.meetingOperateId = meetingOperateId;
    }

    @Basic
    @Column(name = "meeting_operate_name", nullable = true, length = 50)
    public String getMeetingOperateName() {
        return meetingOperateName;
    }

    public void setMeetingOperateName(String meetingOperateName) {
        this.meetingOperateName = meetingOperateName;
    }

    @Basic
    @Column(name = "is_case", nullable = true)
    public Byte getIsCase() {
        return isCase;
    }

    public void setIsCase(Byte isCase) {
        this.isCase = isCase;
    }

    @Basic
    @Column(name = "is_dispatch", nullable = true)
    public Byte getIsDispatch() {
        return isDispatch;
    }

    public void setIsDispatch(Byte isDispatch) {
        this.isDispatch = isDispatch;
    }

    @Basic
    @Column(name = "is_continue", nullable = true)
    public Byte getIsContinue() {
        return isContinue;
    }

    public void setIsContinue(Byte isContinue) {
        this.isContinue = isContinue;
    }

    @Basic
    @Column(name = "is_lead", nullable = true)
    public Byte getIsLead() {
        return isLead;
    }

    public void setIsLead(Byte isLead) {
        this.isLead = isLead;
    }

    @Basic
    @Column(name = "is_accomplish", nullable = true)
    public Byte getIsAccomplish() {
        return isAccomplish;
    }

    public void setIsAccomplish(Byte isAccomplish) {
        this.isAccomplish = isAccomplish;
    }

    @Basic
    @Column(name = "meeting_begin_date", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getMeetingBeginDate() {
        return meetingBeginDate;
    }

    public void setMeetingBeginDate(Date meetingBeginDate) {
        this.meetingBeginDate = meetingBeginDate;
    }

    @Basic
    @Column(name = "meeting_end_date", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getMeetingEndDate() {
        return meetingEndDate;
    }

    public void setMeetingEndDate(Date meetingEndDate) {
        this.meetingEndDate = meetingEndDate;
    }

    @Basic
    @Column(name = "meeting_record", nullable = true, length = 1000)
    public String getMeetingRecord() {
        return meetingRecord;
    }

    public void setMeetingRecord(String meetingRecord) {
        this.meetingRecord = meetingRecord;
    }

    @Basic
    @Column(name = "meeting_record_image", nullable = true, length = 1000)
    public String getMeetingRecordImage() {
        return meetingRecordImage;
    }

    public void setMeetingRecordImage(String meetingRecordImage) {
        this.meetingRecordImage = meetingRecordImage;
    }

    @Basic
    @Column(name = "meeting_record_file", nullable = true, length = 1000)
    public String getMeetingRecordFile() {
        return meetingRecordFile;
    }

    public void setMeetingRecordFile(String meetingRecordFile) {
        this.meetingRecordFile = meetingRecordFile;
    }

    @Basic
    @Column(name = "meeting_record_date", nullable = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getMeetingRecordDate() {
        return meetingRecordDate;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    public void setMeetingRecordDate(Date meetingRecordDate) {
        this.meetingRecordDate = meetingRecordDate;
    }

    @Basic
    @Column(name = "meeting_record_person_id", nullable = true, length = 50)
    public String getMeetingRecordPersonId() {
        return meetingRecordPersonId;
    }

    public void setMeetingRecordPersonId(String meetingRecordPersonId) {
        this.meetingRecordPersonId = meetingRecordPersonId;
    }

    @Basic
    @Column(name = "meeting_record_person_name", nullable = true, length = 200)
    public String getMeetingRecordPersonName() {
        return meetingRecordPersonName;
    }

    public void setMeetingRecordPersonName(String meetingRecordPersonName) {
        this.meetingRecordPersonName = meetingRecordPersonName;
    }

    @Basic
    @Column(name = "meeting_process", nullable = true, length = 1000)
    public String getMeetingProcess() {
        return meetingProcess;
    }

    public void setMeetingProcess(String meetingProcess) {
        this.meetingProcess = meetingProcess;
    }

    @Basic
    @Column(name = "meeting_process_file", nullable = true, length = 1000)
    public String getMeetingProcessFile() {
        return meetingProcessFile;
    }

    public void setMeetingProcessFile(String meetingProcessFile) {
        this.meetingProcessFile = meetingProcessFile;
    }

    @Basic
    @Column(name = "meeting_process_date", nullable = true, length = 50)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getMeetingProcessDate() {
        return meetingProcessDate;
    }

    public void setMeetingProcessDate(Date meetingProcessDate) {
        this.meetingProcessDate = meetingProcessDate;
    }

    @Basic
    @Column(name = "meeting_process_person_id", nullable = true, length = 50)
    public String getMeetingProcessPersonId() {
        return meetingProcessPersonId;
    }

    public void setMeetingProcessPersonId(String meetingProcessPersonId) {
        this.meetingProcessPersonId = meetingProcessPersonId;
    }

    @Basic
    @Column(name = "meeting_process_person_name", nullable = true, length = 50)
    public String getMeetingProcessPersonName() {
        return meetingProcessPersonName;
    }

    public void setMeetingProcessPersonName(String meetingProcessPersonName) {
        this.meetingProcessPersonName = meetingProcessPersonName;
    }

    @Basic
    @Column(name = "meeting_nature_id", nullable = true)
    public Integer getMeetingNatureId() {
        return meetingNatureId;
    }

    public void setMeetingNatureId(Integer meetingNatureId) {
        this.meetingNatureId = meetingNatureId;
    }
}
