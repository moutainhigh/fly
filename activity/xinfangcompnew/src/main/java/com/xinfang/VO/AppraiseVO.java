package com.xinfang.VO;

/**
 * Created by sunbjx on 2017/4/14.
 */
public class AppraiseVO {

    // 评价人
    private String username;
    // 评价人头像
    private String userHeadSrc;
    // 是否满意信访部门
    private String isOkPetitionUnit;
    // 案件创建时间
    private String caseCreateTime;
    // 是否满意责任单位
    private String isOkDutyUnit;
    // 评价创建时间
    private String appraiseCreateTime;
    // 案件问题归属地
    private String questionBelongTo;
    // 信访目的
    private String petitionPurpose;
    // 案件内别
    private String caseType;
    // 案件办理信访部门
    private String petitionUnit;
    // 案件办理责任单位
    private String dutyUnit;
    // 信访人数
    private Integer petitionNumbers;
    // 信访时间
    private String petitionTime;
    // 信访地点
    private String petitionAddress;
    // 信访方式
    private String petitionWay;
    // 信访编号
    private String petitionCode;
    // 满意程度(星级)
    private Integer okLevel;
    // 满意程度对应标签
    private Integer okLevelLable;
    // 主要标签
    private String label;
    // 评价内容
    private String content;
    // 上访人ID
    private Integer guestId;
    // 案件ID
    private Integer caseId;

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

    public String getIsOkPetitionUnit() {
        return isOkPetitionUnit;
    }

    public void setIsOkPetitionUnit(String isOkPetitionUnit) {
        this.isOkPetitionUnit = isOkPetitionUnit;
    }

    public String getCaseCreateTime() {
        return caseCreateTime;
    }

    public void setCaseCreateTime(String caseCreateTime) {
        this.caseCreateTime = caseCreateTime;
    }

    public String getIsOkDutyUnit() {
        return isOkDutyUnit;
    }

    public void setIsOkDutyUnit(String isOkDutyUnit) {
        this.isOkDutyUnit = isOkDutyUnit;
    }

    public String getAppraiseCreateTime() {
        return appraiseCreateTime;
    }

    public void setAppraiseCreateTime(String appraiseCreateTime) {
        this.appraiseCreateTime = appraiseCreateTime;
    }

    public String getQuestionBelongTo() {
        return questionBelongTo;
    }

    public void setQuestionBelongTo(String questionBelongTo) {
        this.questionBelongTo = questionBelongTo;
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

    public Integer getPetitionNumbers() {
        return petitionNumbers;
    }

    public void setPetitionNumbers(Integer petitionNumbers) {
        this.petitionNumbers = petitionNumbers;
    }

    public String getPetitionTime() {
        return petitionTime;
    }

    public void setPetitionTime(String petitionTime) {
        this.petitionTime = petitionTime;
    }

    public String getPetitionAddress() {
        return petitionAddress;
    }

    public void setPetitionAddress(String petitionAddress) {
        this.petitionAddress = petitionAddress;
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

    public Integer getOkLevel() {
        return okLevel;
    }

    public void setOkLevel(Integer okLevel) {
        this.okLevel = okLevel;
    }

    public Integer getOkLevelLable() {
        return okLevelLable;
    }

    public void setOkLevelLable(Integer okLevelLable) {
        this.okLevelLable = okLevelLable;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
