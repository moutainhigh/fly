package com.xinfang.evaluating.vo;

/**
 * 投诉办理评价详情数据展现
 * Created by sunbjx on 2017/5/8.
 */
public class AppraiseDetailVO {
    // 评价人姓名
    private String username;
    // 评价人头像
    private String userHeadSrc;
    // 信访时间
    private String petitionTime;
    // 信访方式
    private String petitionWay;
    // 信访编号
    private String petitionCode;
    // 上访人ID
    private Integer guestId;
    // 案件ID
    private Integer caseId;
    // 信访人数
    private Integer petitionNumbers;

    // 信访单位
    private String petitionAppraiseUnit;
    // 满意程度
    private String petitionAppraiseSatisfied;
    // 信访部门评价时间
    private String petitionAppraiseTime;
    // 信访部门评价内容
    private String petitionAppraiseContent;

    // 责任单位(办结单位)
    private String dutyAppraiseUnit;
    // 责任单位满意程度
    private String dutyAppraiseSatisfied;
    // 责任单位评价时间
    private String dutyAppraiseTime;
    // 责任单位评价内容
    private String dutyAppraiseContent;

    // 案件办理满意程度
    private String caseAppraiseSatisfied;
    // 案件办理评价时间
    private String caseAppraiseTime;
    // 案件办理评价内容
    private String caseAppraiseContent;


    @Override
    public String toString() {
        return "AppraiseDetailVO{" +
                "username='" + username + '\'' +
                ", userHeadSrc='" + userHeadSrc + '\'' +
                ", petitionTime='" + petitionTime + '\'' +
                ", petitionWay='" + petitionWay + '\'' +
                ", petitionCode='" + petitionCode + '\'' +
                ", guestId=" + guestId +
                ", caseId=" + caseId +
                ", petitionAppraiseUnit='" + petitionAppraiseUnit + '\'' +
                ", petitionAppraiseSatisfied='" + petitionAppraiseSatisfied + '\'' +
                ", petitionAppraiseTime='" + petitionAppraiseTime + '\'' +
                ", petitionAppraiseContent='" + petitionAppraiseContent + '\'' +
                ", dutyAppraiseUnit='" + dutyAppraiseUnit + '\'' +
                ", dutyAppraiseSatisfied='" + dutyAppraiseSatisfied + '\'' +
                ", dutyAppraiseTime='" + dutyAppraiseTime + '\'' +
                ", dutyAppraiseContent='" + dutyAppraiseContent + '\'' +
                ", caseAppraiseSatisfied='" + caseAppraiseSatisfied + '\'' +
                ", caseAppraiseTime='" + caseAppraiseTime + '\'' +
                ", caseAppraiseContent='" + caseAppraiseContent + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserHeadSrc() {
        return userHeadSrc;
    }

    public void setUserHeadSrc(String userHeadSrc) {
        this.userHeadSrc = userHeadSrc;
    }

    public String getPetitionTime() {
        return petitionTime;
    }

    public void setPetitionTime(String petitionTime) {
        this.petitionTime = petitionTime;
    }

    public String getPetitionWay() {
        return petitionWay;
    }

    public void setPetitionWay(String petitionWay) {
        this.petitionWay = petitionWay;
    }

    public String getPetitionCode() {
        return petitionCode;
    }

    public void setPetitionCode(String petitionCode) {
        this.petitionCode = petitionCode;
    }

    public Integer getGuestId() {
        return guestId;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getPetitionAppraiseUnit() {
        return petitionAppraiseUnit;
    }

    public void setPetitionAppraiseUnit(String petitionAppraiseUnit) {
        this.petitionAppraiseUnit = petitionAppraiseUnit;
    }

    public String getPetitionAppraiseSatisfied() {
        return petitionAppraiseSatisfied;
    }

    public void setPetitionAppraiseSatisfied(String petitionAppraiseSatisfied) {
        this.petitionAppraiseSatisfied = petitionAppraiseSatisfied;
    }

    public String getPetitionAppraiseTime() {
        return petitionAppraiseTime;
    }

    public void setPetitionAppraiseTime(String petitionAppraiseTime) {
        this.petitionAppraiseTime = petitionAppraiseTime;
    }

    public String getPetitionAppraiseContent() {
        return petitionAppraiseContent;
    }

    public void setPetitionAppraiseContent(String petitionAppraiseContent) {
        this.petitionAppraiseContent = petitionAppraiseContent;
    }

    public String getDutyAppraiseUnit() {
        return dutyAppraiseUnit;
    }

    public void setDutyAppraiseUnit(String dutyAppraiseUnit) {
        this.dutyAppraiseUnit = dutyAppraiseUnit;
    }

    public String getDutyAppraiseSatisfied() {
        return dutyAppraiseSatisfied;
    }

    public void setDutyAppraiseSatisfied(String dutyAppraiseSatisfied) {
        this.dutyAppraiseSatisfied = dutyAppraiseSatisfied;
    }

    public String getDutyAppraiseTime() {
        return dutyAppraiseTime;
    }

    public void setDutyAppraiseTime(String dutyAppraiseTime) {
        this.dutyAppraiseTime = dutyAppraiseTime;
    }

    public String getDutyAppraiseContent() {
        return dutyAppraiseContent;
    }

    public void setDutyAppraiseContent(String dutyAppraiseContent) {
        this.dutyAppraiseContent = dutyAppraiseContent;
    }

    public String getCaseAppraiseSatisfied() {
        return caseAppraiseSatisfied;
    }

    public void setCaseAppraiseSatisfied(String caseAppraiseSatisfied) {
        this.caseAppraiseSatisfied = caseAppraiseSatisfied;
    }

    public String getCaseAppraiseTime() {
        return caseAppraiseTime;
    }

    public void setCaseAppraiseTime(String caseAppraiseTime) {
        this.caseAppraiseTime = caseAppraiseTime;
    }

    public String getCaseAppraiseContent() {
        return caseAppraiseContent;
    }

    public void setCaseAppraiseContent(String caseAppraiseContent) {
        this.caseAppraiseContent = caseAppraiseContent;
    }

    public Integer getPetitionNumbers() {
        return petitionNumbers;
    }

    public void setPetitionNumbers(Integer petitionNumbers) {
        this.petitionNumbers = petitionNumbers;
    }
}
