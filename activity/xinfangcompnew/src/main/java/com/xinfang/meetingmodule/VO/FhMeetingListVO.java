package com.xinfang.meetingmodule.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xinfang.meetingmodule.model.FhMeetingDepartment;
import com.xinfang.meetingmodule.model.FhMeetingPerson;
import com.xinfang.meetingmodule.model.FhMeetingSubmitPerson;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-11 14:20
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
        "\ti.department_id AS initiate_department_id,\n" +
        "\ti.department_name AS initiate_department_name,\n" +
        "\ti.meeting_title,\n" +
        "\ti.meeting_content,\n" +
        "\ti.meeting_operate_id,\n" +
        "\ti.meeting_operate_name,\n" +
        "\ti.is_case,\n" +
        "\ti.is_dispatch,\n" +
        "\ti.is_accomplish,\n" +
        "\tCOUNT(p.meeting_person_id) AS meeting_person_amount\n" +
        "FROM\n" +
        "\tfh_meeting_initiate i\n" +
        "LEFT JOIN fh_meeting_person p ON i.meeting_id = p.meeting_id\n" +
        "GROUP BY\n" +
        "\ti.meeting_id")
public class FhMeetingListVO {
    private int meetingId;
    private Date meetingDate;
    private String meetingAddress;
    private String meetingEmcee; // 会议主持
    private Integer meetingTypeId;
    private String meetingTypeName;

    private Integer initiateDepartmentId;  // 发起会议部门id
    private String initiateDepartmentName;  // 发起会议部门名称

    private String meetingTitle;
    private String meetingContent;

    private Integer meetingOperateId;  // 会议发起人id
    private String meetingOperateName;  // 会议发起人id名称

    private Byte isCase;   // 是否关联案件
    private Byte isDispatch;   // 是否关联调度
    private Byte isAccomplish;  // 会议是否完成
    private Integer meetingPersonAmount; // 该会议总人数，包含没签到的

    private Set<FhMeetingPerson> meetingPersons = new HashSet<>();  // 参会人员 与会议一对多
    private Set<FhMeetingDepartment> meetingDepartments = new HashSet<>();  // 参会部门 与会议一对多
    private Set<FhMeetingSubmitPerson> meetingSubmitPersons = new HashSet<>();  // 参与部门的提交人

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
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
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

    public Integer getMeetingTypeId() {
        return meetingTypeId;
    }

    @Basic
    @Column(name = "meeting_type_id", nullable = true, length = 50)
    public void setMeetingTypeId(Integer meetingTypeId) {
        this.meetingTypeId = meetingTypeId;
    }

    public String getMeetingTypeName() {
        return meetingTypeName;
    }

    @Basic
    @Column(name = "meeting_type_name", nullable = true, length = 50)
    public void setMeetingTypeName(String meetingTypeName) {
        this.meetingTypeName = meetingTypeName;
    }

    @Basic
    @Column(name = "initiate_department_id", nullable = true, length = 50)
    public Integer getInitiateDepartmentId() {
        return initiateDepartmentId;
    }

    public void setInitiateDepartmentId(Integer initiateDepartmentId) {
        this.initiateDepartmentId = initiateDepartmentId;
    }

    @Basic
    @Column(name = "initiate_department_name", nullable = true, length = 50)
    public String getInitiateDepartmentName() {
        return initiateDepartmentName;
    }

    public void setInitiateDepartmentName(String initiateDepartmentName) {
        this.initiateDepartmentName = initiateDepartmentName;
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
    @Column(name = "is_accomplish", nullable = true)
    public Byte getIsAccomplish() {
        return isAccomplish;
    }

    public void setIsAccomplish(Byte isAccomplish) {
        this.isAccomplish = isAccomplish;
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

    @OneToMany(cascade=CascadeType.DETACH,fetch=FetchType.LAZY)//级联保存、更新、删除、刷新;延迟加载
    @JoinColumn(name = "meeting_id", referencedColumnName = "meeting_id", updatable = false, insertable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    public Set<FhMeetingPerson> getMeetingPersons() {
        return meetingPersons;
    }

    public void setMeetingPersons(Set<FhMeetingPerson> meetingPersons) {
        this.meetingPersons = meetingPersons;
    }

    @OneToMany(cascade=CascadeType.DETACH,fetch=FetchType.LAZY)//级联保存、更新、删除、刷新;延迟加载
    @JoinColumn(name = "meeting_id", referencedColumnName = "meeting_id", updatable = false, insertable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    public Set<FhMeetingDepartment> getMeetingDepartments() {
        return meetingDepartments;
    }

    public void setMeetingDepartments(Set<FhMeetingDepartment> meetingDepartments) {
        this.meetingDepartments = meetingDepartments;
    }

    @OneToMany(cascade=CascadeType.DETACH,fetch=FetchType.LAZY)//级联保存、更新、删除、刷新;延迟加载
    @JoinColumn(name = "meeting_id", referencedColumnName = "meeting_id", updatable = false, insertable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    public Set<FhMeetingSubmitPerson> getMeetingSubmitPersons() {
        return meetingSubmitPersons;
    }

    public void setMeetingSubmitPersons(Set<FhMeetingSubmitPerson> meetingSubmitPersons) {
        this.meetingSubmitPersons = meetingSubmitPersons;
    }
}
