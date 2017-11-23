package com.xinfang.VO;

/**
 * Created by sunbjx on 2017/3/27.
 */
public class HistoryCaseVO {

    // 案件编号
    private String caseCode;
    // 上访人
    private String petitionName;
    // 信访形式
    private String petitionType;
    // 上访时间
    private String petitionTime;
    // 问题归属地
    private String questionBelongTo;
    // 分类
    private String caseType;
    // 当前办理单位
    private String currentHandleUnit;
    // 当前状态
    private String caseState;
    // 限办时限
    private String handlePeriodEnd;
    // 内容简述
    private String caseDesc;
    // 上访人ID
    private Integer guestId;
    // 案件ID
    private Integer caseId;

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getPetitionName() {
        return petitionName;
    }

    public void setPetitionName(String petitionName) {
        this.petitionName = petitionName;
    }

    public String getPetitionType() {
        return petitionType;
    }

    public void setPetitionType(String petitionType) {
        this.petitionType = petitionType;
    }

    public String getPetitionTime() {
        return petitionTime;
    }

    public void setPetitionTime(String petitionTime) {
        this.petitionTime = petitionTime;
    }

    public String getQuestionBelongTo() {
        return questionBelongTo;
    }

    public void setQuestionBelongTo(String questionBelongTo) {
        this.questionBelongTo = questionBelongTo;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCurrentHandleUnit() {
        return currentHandleUnit;
    }

    public void setCurrentHandleUnit(String currentHandleUnit) {
        this.currentHandleUnit = currentHandleUnit;
    }

    public String getCaseState() {
        return caseState;
    }

    public void setCaseState(String caseState) {
        this.caseState = caseState;
    }

    public String getHandlePeriodEnd() {
        return handlePeriodEnd;
    }

    public void setHandlePeriodEnd(String handlePeriodEnd) {
        this.handlePeriodEnd = handlePeriodEnd;
    }

    public String getCaseDesc() {
        return caseDesc;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
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
}
