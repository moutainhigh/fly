package com.xinfang.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-27 11:40
 **/
@Entity
@Table(name = "fd_case_feedback", schema = "xf1", catalog = "")
public class FdCaseFeedbackAll {
    private int id;
    private Integer caseId;
    private Integer state;
    private Integer type;
    private String creater;
    private Date creatTime;
    private String note;
    private String enclosure1;
    private String enclosure2;
    private String enclosure3;
    private Integer createrId;
    private String createrCompany;
    private String createrDep;
    private String createrRole;
    private Date createTime;
    private Integer depId;
    private Date endTime;
    private Integer limitTime;
    private Date startTime;
    private Date updateTime;
    private String noteOth;
    private Integer needReceipt;
    private String userurl;
    private Integer unitlimit;
    private String taketime;
    private String caseState;

    private String intervalTime; // 从创建开始间隔时间字符串

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CASE_ID", nullable = true)
    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    @Basic
    @Column(name = "STATE", nullable = true)
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "TYPE", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "CREATER", nullable = true, length = 20)
    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Basic
    @Column(name = "CREAT_TIME", nullable = true)
    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Basic
    @Column(name = "NOTE", nullable = true, length = 500)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "ENCLOSURE1", nullable = true, length = 500)
    public String getEnclosure1() {
        return enclosure1;
    }

    public void setEnclosure1(String enclosure1) {
        this.enclosure1 = enclosure1;
    }

    @Basic
    @Column(name = "ENCLOSURE2", nullable = true, length = 500)
    public String getEnclosure2() {
        return enclosure2;
    }

    public void setEnclosure2(String enclosure2) {
        this.enclosure2 = enclosure2;
    }

    @Basic
    @Column(name = "ENCLOSURE3", nullable = true, length = 500)
    public String getEnclosure3() {
        return enclosure3;
    }

    public void setEnclosure3(String enclosure3) {
        this.enclosure3 = enclosure3;
    }

    @Basic
    @Column(name = "CREATER_ID", nullable = true)
    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    @Basic
    @Column(name = "CREATER_COMPANY", nullable = true, length = 20)
    public String getCreaterCompany() {
        return createrCompany;
    }

    public void setCreaterCompany(String createrCompany) {
        this.createrCompany = createrCompany;
    }

    @Basic
    @Column(name = "CREATER_DEP", nullable = true, length = 20)
    public String getCreaterDep() {
        return createrDep;
    }

    public void setCreaterDep(String createrDep) {
        this.createrDep = createrDep;
    }

    @Basic
    @Column(name = "CREATER_ROLE", nullable = true, length = 20)
    public String getCreaterRole() {
        return createrRole;
    }

    public void setCreaterRole(String createrRole) {
        this.createrRole = createrRole;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "dep_id", nullable = true)
    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    @Basic
    @Column(name = "end_time", nullable = true)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "limit_time", nullable = true)
    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    @Basic
    @Column(name = "start_time", nullable = true)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "note_oth", nullable = true, length = 50)
    public String getNoteOth() {
        return noteOth;
    }

    public void setNoteOth(String noteOth) {
        this.noteOth = noteOth;
    }

    @Basic
    @Column(name = "need_receipt", nullable = true)
    public Integer getNeedReceipt() {
        return needReceipt;
    }

    public void setNeedReceipt(Integer needReceipt) {
        this.needReceipt = needReceipt;
    }

    @Basic
    @Column(name = "userurl", nullable = true, length = 255)
    public String getUserurl() {
        return userurl;
    }

    public void setUserurl(String userurl) {
        this.userurl = userurl;
    }

    @Basic
    @Column(name = "unitlimit", nullable = true)
    public Integer getUnitlimit() {
        return unitlimit;
    }

    public void setUnitlimit(Integer unitlimit) {
        this.unitlimit = unitlimit;
    }

    @Basic
    @Column(name = "taketime", nullable = true, length = 100)
    public String getTaketime() {
        return taketime;
    }

    public void setTaketime(String taketime) {
        this.taketime = taketime;
    }

    @Basic
    @Column(name = "case_state", nullable = true, length = 24)
    public String getCaseState() {
        return caseState;
    }

    public void setCaseState(String caseState) {
        this.caseState = caseState;
    }

    @Basic
    @Column(name = "interval_time", columnDefinition = "varchar(128) COMMENT '间隔时间字符串，当前生成减案件创建时间，格式为x天y小时z分钟'")
    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

	@Override
	public String toString() {
		return "FdCaseFeedbackAll [id=" + id + ", type=" + type + ", creater=" + creater + ", note=" + note + ", depId="
				+ depId + "]";
	}
    
    
}