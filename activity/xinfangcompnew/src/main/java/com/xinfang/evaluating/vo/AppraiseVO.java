package com.xinfang.evaluating.vo;

/**
 * 投诉办理评价列表展现
 * Created by sunbjx on 2017/5/8.
 */
public class AppraiseVO {
    // 评价人姓名
    private String username;
    // 评价人头像
    private String userHeadSrc;
    // 是否满意信访部门
    private String petitionAppraiseOk;
    // 对信访部门的评价时间
    private String petitionAppraiseTime;
    // 是否满意责任单位
    private String dutyAppraiseOk;
    // 对责任单位的评价时间
    private String dutyAppraiseTime;
    // 是否满意案件办理
    private String caseAppraiseOk;
    // 对案件办理结果评价时间
    private String caseAppraiseTime;
    // 评价方式
    private String appraiseWay;
    // 问题属地
    private String caseBelongTo;
    // 信访目的
    private String petitionPurpose;
    // 案件类别
    private String caseType;
    // 评价内容（显示当前）
    private String caseAppraiseContent;
    // 案件办理信访部门
    private String petitionUnit;
    // 案件办理责任单位
    private String dutyUnit;
    // 案件ID
    private Integer caseId;

    @Override
    public String toString() {
        return "AppraiseVO{" +
                "username='" + username + '\'' +
                ", userHeadSrc='" + userHeadSrc + '\'' +
                ", petitionAppraiseOk='" + petitionAppraiseOk + '\'' +
                ", petitionAppraiseTime='" + petitionAppraiseTime + '\'' +
                ", dutyAppraiseOk='" + dutyAppraiseOk + '\'' +
                ", dutyAppraiseTime='" + dutyAppraiseTime + '\'' +
                ", caseAppraiseOk='" + caseAppraiseOk + '\'' +
                ", caseAppraiseTime='" + caseAppraiseTime + '\'' +
                ", appraiseWay='" + appraiseWay + '\'' +
                ", caseBelongTo='" + caseBelongTo + '\'' +
                ", petitionPurpose='" + petitionPurpose + '\'' +
                ", caseType='" + caseType + '\'' +
                ", caseAppraiseContent='" + caseAppraiseContent + '\'' +
                ", petitionUnit='" + petitionUnit + '\'' +
                ", dutyUnit='" + dutyUnit + '\'' +
                ", caseId=" + caseId +
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

    public String getPetitionAppraiseOk() {
        return petitionAppraiseOk;
    }

    public void setPetitionAppraiseOk(String petitionAppraiseOk) {
        this.petitionAppraiseOk = petitionAppraiseOk;
    }

    public String getPetitionAppraiseTime() {
        return petitionAppraiseTime;
    }

    public void setPetitionAppraiseTime(String petitionAppraiseTime) {
        this.petitionAppraiseTime = petitionAppraiseTime;
    }

    public String getDutyAppraiseOk() {
        return dutyAppraiseOk;
    }

    public void setDutyAppraiseOk(String dutyAppraiseOk) {
        this.dutyAppraiseOk = dutyAppraiseOk;
    }

    public String getDutyAppraiseTime() {
        return dutyAppraiseTime;
    }

    public void setDutyAppraiseTime(String dutyAppraiseTime) {
        this.dutyAppraiseTime = dutyAppraiseTime;
    }

    public String getCaseAppraiseOk() {
        return caseAppraiseOk;
    }

    public void setCaseAppraiseOk(String caseAppraiseOk) {
        this.caseAppraiseOk = caseAppraiseOk;
    }

    public String getCaseAppraiseTime() {
        return caseAppraiseTime;
    }

    public void setCaseAppraiseTime(String caseAppraiseTime) {
        this.caseAppraiseTime = caseAppraiseTime;
    }

    public String getAppraiseWay() {
        return appraiseWay;
    }

    public void setAppraiseWay(String appraiseWay) {
        this.appraiseWay = appraiseWay;
    }

    public String getCaseBelongTo() {
        return caseBelongTo;
    }

    public void setCaseBelongTo(String caseBelongTo) {
        this.caseBelongTo = caseBelongTo;
    }

    public String getPetitionPurpose() {
        return petitionPurpose;
    }

    public void setPetitionPurpose(String petitionPurpose) {
        this.petitionPurpose = petitionPurpose;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseAppraiseContent() {
        return caseAppraiseContent;
    }

    public void setCaseAppraiseContent(String caseAppraiseContent) {
        this.caseAppraiseContent = caseAppraiseContent;
    }

    public String getPetitionUnit() {
        return petitionUnit;
    }

    public void setPetitionUnit(String petitionUnit) {
        this.petitionUnit = petitionUnit;
    }

    public String getDutyUnit() {
        return dutyUnit;
    }

    public void setDutyUnit(String dutyUnit) {
        this.dutyUnit = dutyUnit;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }
}
