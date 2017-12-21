package com.xinfang.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sunbjx on 2017/5/17.
 */
@Entity
@Table(name = "dispute_case")
public class DisputeCaseEntity {
	private Integer disputeCaseId;
	@ApiModelProperty(value = "信访人员")
	private String petitionNames;
	@ApiModelProperty(value = "信访人数")
	private Integer petitionNumbers;
	@ApiModelProperty(value = "信访时间 yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date petitionTime;
	@ApiModelProperty(value = "信访区县")
	private Integer petitionCounty;
	@ApiModelProperty(value = "信访单位")
	private String petitionUnit;
	@ApiModelProperty(value = "信访方式")
	private Integer petitionWay;
	private String petitionCode;
	@ApiModelProperty(value = "是否重复信访")
	private Byte isRepeatPetition;
	@ApiModelProperty(value = "事件时间 yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date caseTime;
	@ApiModelProperty(value = "事件地点")
	private String caseAddress;
	@ApiModelProperty(value = "事件描述")
	private String caseDesc;
	@ApiModelProperty(value = "事件要求")
	private String caseDemand;
	@ApiModelProperty(value = "附件信息")
	private String fileSrc;
	@ApiModelProperty(value = "处理时长")
	private Integer handleDuration;
	@ApiModelProperty(value = "处理时间段开始 yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date handlePeriodStart;
	@ApiModelProperty(value = "处理时间段结束 yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date handlePeriodEnd;
	@ApiModelProperty(value = "问题归属地")
	private Integer questionBelongingTo;
	@ApiModelProperty(value = "内容分类三级")
	private Integer questionType;
	@ApiModelProperty(value = "内容分类一级")
	private Integer questionType1;
	@ApiModelProperty(value = "内容分类二级")
	private Integer questionType2;
	@ApiModelProperty(value = "案件分类")
	private Integer caseType;
	@ApiModelProperty(value = "热点问题")
	private Integer questionHot;
	@ApiModelProperty(value = "所属系统")
	private Integer belongToSys;
	@ApiModelProperty(value = "13大类")
	private Integer thirteenCategories;
	@ApiModelProperty(value = "信访原因")
	private Integer petitionWhy;
	@ApiModelProperty(value = "信访目的")
	private Integer petitionPurpose;
	@ApiModelProperty(value = "领导限办")
	private Byte isLeaderLimited;
	@ApiModelProperty(value = "揭发控告对象")
	private Integer exposeObject;
	@ApiModelProperty(value = "信访性质")
	private Integer petitionNature;
	@ApiModelProperty(value = "受信人")
	private String trusteName;
	@ApiModelProperty(value = "是否越级信访")
	private Byte isAbove;
	@ApiModelProperty(value = "是否信访积案")
	private Byte isBacklog;
	@ApiModelProperty(value = "是否复查复核")
	private Byte isReview;
	@ApiModelProperty(value = "列入满意评价")
	private Byte isSatisfied;
	@ApiModelProperty(value = "是否突出事项")
	private Byte isProminent;
	@ApiModelProperty(value = "是否三跨三离")
	private Byte isAcrossFrom;
	@ApiModelProperty(value = "是否领导包案")
	private Byte isLeaderPackageCase;
	@ApiModelProperty(value = "是否领导批示")
	private Byte isLeaderApprove;
	@ApiModelProperty(value = "方式是否正确")
	private Byte isTypeRight;
	@ApiModelProperty(value = "规模准确")
	private Byte isScaleRight;
	@ApiModelProperty(value = "层级是否准确")
	private Byte isTierRight;
	// 创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date gmtCreate;
	// 修改时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date gmtModified;
	@ApiModelProperty(value = "创建者ID")
	private Integer creatorId;
	@ApiModelProperty(value = "案件状态")
	private Integer caseHandleState;
	// 1 个访 2 群访
	private Integer type;
	@ApiModelProperty(value = "窗口编号")
	private Integer windowNumber;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date createTime;
	@ApiModelProperty(value = "关联案件说明")
	private String attachCaseDesc;
	private int guestId;
	@ApiModelProperty(value = "关联案件IDs")
	private String attachCaseIds;
	private Byte form;
	private int state;
	private String title;
	@ApiModelProperty(value = "是否涉法涉诉")
	private Byte isSuitWade;
	@ApiModelProperty(value = "是否涉台")
	private Byte isSuitTaiwan;
	@ApiModelProperty(value = "是否涉澳")
	private Byte isSuitAomen;
	@ApiModelProperty(value = "是否涉港")
	private Byte isSuitHongkong;
	@ApiModelProperty(value = "是否涉侨")
	private Byte isSuitAbroad;
	@ApiModelProperty(value = "是否涉外")
	private Byte isSuitOuter;
	@ApiModelProperty(value = "是否扬言")
	private Byte isSuitThreaten;
	@ApiModelProperty(value = "随访人IDs")
	private String followGuestIds;
	private String createName;
	private String createWindow;
	// 评论ID
	private Integer approveId;
	@ApiModelProperty(value = "案件处理人ID", required = true)
	private Integer handleUserid;
	@ApiModelProperty(value = "涉及人数")
	private Integer associatedNumbers;
	// 流程分类
	private Byte handleFlowType;
	// 0 未录入 1 已经录入 2 已经进行案件处理
	private Byte isHandle;
	private Byte isDegree;
	private Byte isTimelyAccept;
	private Byte isOntimeFinish;
	// 0 非流程 1 流程
	private Integer isFlow;
	// 当前处理单位ID
	private Integer currentHandleUnitid;
	// 处理方式
	private Integer handleType;
	// 当前处理人ID
	private Integer currentHandlePersonalid;
	// 当前处理状态
	private String currentHandleState;
	// 处理天数
	private Integer handleDays;
	// 案件实际结束时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date handleFactEndTime;
	// 案件录入单位ID
	private Integer createUnitid;
	// 案件转交单位ID
	private Integer transferUnitid;
	// 案件转件人ID
	private Integer transferPersonalid;
	// 案件接收单位ID
	private Integer receiveUnitid;
	// 案件接收人ID
	private Integer receivePersonalid;
	// 转交给信访单位
	private Byte transferToPetitionUnit;
	// 调度ID
	private Integer dishedId;

	// 非序列化字段
	private String strQuestionBelongTo;
	private String strBelongToSys;
	private String strCaseType;
	private String strExposeObject;
	private String strPetitionCountry;
	private String strPetitionNature;
	private String strPetitionPurpose;
	private String strPetitionWay;
	private String strPetitionWhy;
	private String strQuestionHot;
	private String strQusetionType;
	private String strThirteenCategories;

	@Id
	@Column(name = "dispute_case_id")
	@GeneratedValue
	public Integer getDisputeCaseId() {
		return disputeCaseId;
	}

	public void setDisputeCaseId(Integer disputeCaseId) {
		this.disputeCaseId = disputeCaseId;
	}

	@Basic
	@Column(name = "petition_names")
	public String getPetitionNames() {
		return petitionNames;
	}

	public void setPetitionNames(String petitionNames) {
		this.petitionNames = petitionNames;
	}

	@Basic
	@Column(name = "petition_numbers")
	public Integer getPetitionNumbers() {
		return petitionNumbers;
	}

	public void setPetitionNumbers(Integer petitionNumbers) {
		this.petitionNumbers = petitionNumbers;
	}

	@Basic
	@Column(name = "petition_time")
	public Date getPetitionTime() {
		return petitionTime;
	}

	public void setPetitionTime(Date petitionTime) {
		this.petitionTime = petitionTime;
	}

	@Basic
	@Column(name = "petition_county")
	public Integer getPetitionCounty() {
		return petitionCounty;
	}

	public void setPetitionCounty(Integer petitionCounty) {
		this.petitionCounty = petitionCounty;
	}

	@Basic
	@Column(name = "petition_unit")
	public String getPetitionUnit() {
		return petitionUnit;
	}

	public void setPetitionUnit(String petitionUnit) {
		this.petitionUnit = petitionUnit;
	}

	@Basic
	@Column(name = "petition_way")
	public Integer getPetitionWay() {
		return petitionWay;
	}

	public void setPetitionWay(Integer petitionWay) {
		this.petitionWay = petitionWay;
	}

	@Basic
	@Column(name = "petition_code")
	public String getPetitionCode() {
		return petitionCode;
	}

	public void setPetitionCode(String petitionCode) {
		this.petitionCode = petitionCode;
	}

	@Basic
	@Column(name = "is_repeat_petition")
	public Byte getIsRepeatPetition() {
		return isRepeatPetition;
	}

	public void setIsRepeatPetition(Byte isRepeatPetition) {
		this.isRepeatPetition = isRepeatPetition;
	}

	@Basic
	@Column(name = "case_time")
	public Date getCaseTime() {
		return caseTime;
	}

	public void setCaseTime(Date caseTime) {
		this.caseTime = caseTime;
	}

	@Basic
	@Column(name = "case_address")
	public String getCaseAddress() {
		return caseAddress;
	}

	public void setCaseAddress(String caseAddress) {
		this.caseAddress = caseAddress;
	}

	@Basic
	@Column(name = "case_desc")
	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}

	@Basic
	@Column(name = "case_demand")
	public String getCaseDemand() {
		return caseDemand;
	}

	public void setCaseDemand(String caseDemand) {
		this.caseDemand = caseDemand;
	}

	@Basic
	@Column(name = "file_src")
	public String getFileSrc() {
		return fileSrc;
	}

	public void setFileSrc(String fileSrc) {
		this.fileSrc = fileSrc;
	}

	@Basic
	@Column(name = "handle_duration")
	public Integer getHandleDuration() {
		return handleDuration;
	}

	public void setHandleDuration(Integer handleDuration) {
		this.handleDuration = handleDuration;
	}

	@Basic
	@Column(name = "handle_period_start")
	public Date getHandlePeriodStart() {
		return handlePeriodStart;
	}

	public void setHandlePeriodStart(Date handlePeriodStart) {
		this.handlePeriodStart = handlePeriodStart;
	}

	@Basic
	@Column(name = "handle_period_end")
	public Date getHandlePeriodEnd() {
		return handlePeriodEnd;
	}

	public void setHandlePeriodEnd(Date handlePeriodEnd) {
		this.handlePeriodEnd = handlePeriodEnd;
	}

	@Basic
	@Column(name = "question_belonging_to")
	public Integer getQuestionBelongingTo() {
		return questionBelongingTo;
	}

	public void setQuestionBelongingTo(Integer questionBelongingTo) {
		this.questionBelongingTo = questionBelongingTo;
	}

	@Basic
	@Column(name = "question_type")
	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	@Basic
	@Column(name = "case_type")
	public Integer getCaseType() {
		return caseType;
	}

	public void setCaseType(Integer caseType) {
		this.caseType = caseType;
	}

	@Basic
	@Column(name = "question_hot")
	public Integer getQuestionHot() {
		return questionHot;
	}

	public void setQuestionHot(Integer questionHot) {
		this.questionHot = questionHot;
	}

	@Basic
	@Column(name = "belong_to_sys")
	public Integer getBelongToSys() {
		return belongToSys;
	}

	public void setBelongToSys(Integer belongToSys) {
		this.belongToSys = belongToSys;
	}

	@Basic
	@Column(name = "thirteen_categories")
	public Integer getThirteenCategories() {
		return thirteenCategories;
	}

	public void setThirteenCategories(Integer thirteenCategories) {
		this.thirteenCategories = thirteenCategories;
	}

	@Basic
	@Column(name = "petition_why")
	public Integer getPetitionWhy() {
		return petitionWhy;
	}

	public void setPetitionWhy(Integer petitionWhy) {
		this.petitionWhy = petitionWhy;
	}

	@Basic
	@Column(name = "petition_purpose")
	public Integer getPetitionPurpose() {
		return petitionPurpose;
	}

	public void setPetitionPurpose(Integer petitionPurpose) {
		this.petitionPurpose = petitionPurpose;
	}

	@Basic
	@Column(name = "is_leader_limited")
	public Byte getIsLeaderLimited() {
		return isLeaderLimited;
	}

	public void setIsLeaderLimited(Byte isLeaderLimited) {
		this.isLeaderLimited = isLeaderLimited;
	}

	@Basic
	@Column(name = "expose_object")
	public Integer getExposeObject() {
		return exposeObject;
	}

	public void setExposeObject(Integer exposeObject) {
		this.exposeObject = exposeObject;
	}

	@Basic
	@Column(name = "petition_nature")
	public Integer getPetitionNature() {
		return petitionNature;
	}

	public void setPetitionNature(Integer petitionNature) {
		this.petitionNature = petitionNature;
	}

	@Basic
	@Column(name = "truste_name")
	public String getTrusteName() {
		return trusteName;
	}

	public void setTrusteName(String trusteName) {
		this.trusteName = trusteName;
	}

	@Basic
	@Column(name = "is_above")
	public Byte getIsAbove() {
		return isAbove;
	}

	public void setIsAbove(Byte isAbove) {
		this.isAbove = isAbove;
	}

	@Basic
	@Column(name = "is_backlog")
	public Byte getIsBacklog() {
		return isBacklog;
	}

	public void setIsBacklog(Byte isBacklog) {
		this.isBacklog = isBacklog;
	}

	@Basic
	@Column(name = "is_review")
	public Byte getIsReview() {
		return isReview;
	}

	public void setIsReview(Byte isReview) {
		this.isReview = isReview;
	}

	@Basic
	@Column(name = "is_satisfied")
	public Byte getIsSatisfied() {
		return isSatisfied;
	}

	public void setIsSatisfied(Byte isSatisfied) {
		this.isSatisfied = isSatisfied;
	}

	@Basic
	@Column(name = "is_prominent")
	public Byte getIsProminent() {
		return isProminent;
	}

	public void setIsProminent(Byte isProminent) {
		this.isProminent = isProminent;
	}

	@Basic
	@Column(name = "is_across_from")
	public Byte getIsAcrossFrom() {
		return isAcrossFrom;
	}

	public void setIsAcrossFrom(Byte isAcrossFrom) {
		this.isAcrossFrom = isAcrossFrom;
	}

	@Basic
	@Column(name = "is_leader_package_case")
	public Byte getIsLeaderPackageCase() {
		return isLeaderPackageCase;
	}

	public void setIsLeaderPackageCase(Byte isLeaderPackageCase) {
		this.isLeaderPackageCase = isLeaderPackageCase;
	}

	@Basic
	@Column(name = "is_leader_approve")
	public Byte getIsLeaderApprove() {
		return isLeaderApprove;
	}

	public void setIsLeaderApprove(Byte isLeaderApprove) {
		this.isLeaderApprove = isLeaderApprove;
	}

	@Basic
	@Column(name = "is_type_right")
	public Byte getIsTypeRight() {
		return isTypeRight;
	}

	public void setIsTypeRight(Byte isTypeRight) {
		this.isTypeRight = isTypeRight;
	}

	@Basic
	@Column(name = "is_scale_right")
	public Byte getIsScaleRight() {
		return isScaleRight;
	}

	public void setIsScaleRight(Byte isScaleRight) {
		this.isScaleRight = isScaleRight;
	}

	@Basic
	@Column(name = "is_tier_right")
	public Byte getIsTierRight() {
		return isTierRight;
	}

	public void setIsTierRight(Byte isTierRight) {
		this.isTierRight = isTierRight;
	}

	@Basic
	@Column(name = "gmt_create")
	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Basic
	@Column(name = "gmt_modified")
	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Basic
	@Column(name = "creator_id")
	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	@Basic
	@Column(name = "case_handle_state")
	public Integer getCaseHandleState() {
		return caseHandleState;
	}

	public void setCaseHandleState(Integer caseHandleState) {
		this.caseHandleState = caseHandleState;
	}

	@Basic
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Basic
	@Column(name = "window_number")
	public Integer getWindowNumber() {
		return windowNumber;
	}

	public void setWindowNumber(Integer windowNumber) {
		this.windowNumber = windowNumber;
	}

	@Basic
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Basic
	@Column(name = "attach_case_desc")
	public String getAttachCaseDesc() {
		return attachCaseDesc;
	}

	public void setAttachCaseDesc(String attachCaseDesc) {
		this.attachCaseDesc = attachCaseDesc;
	}

	@Basic
	@Column(name = "guest_id")
	public int getGuestId() {
		return guestId;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	@Basic
	@Column(name = "attach_case_ids")
	public String getAttachCaseIds() {
		return attachCaseIds;
	}

	public void setAttachCaseIds(String attachCaseIds) {
		this.attachCaseIds = attachCaseIds;
	}

	@Basic
	@Column(name = "form")
	public Byte getForm() {
		return form;
	}

	public void setForm(Byte form) {
		this.form = form;
	}

	@Basic
	@Column(name = "state")
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Basic
	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Basic
	@Column(name = "is_suit_wade")
	public Byte getIsSuitWade() {
		return isSuitWade;
	}

	public void setIsSuitWade(Byte isSuitWade) {
		this.isSuitWade = isSuitWade;
	}

	@Basic
	@Column(name = "is_suit_taiwan")
	public Byte getIsSuitTaiwan() {
		return isSuitTaiwan;
	}

	public void setIsSuitTaiwan(Byte isSuitTaiwan) {
		this.isSuitTaiwan = isSuitTaiwan;
	}

	@Basic
	@Column(name = "is_suit_aomen")
	public Byte getIsSuitAomen() {
		return isSuitAomen;
	}

	public void setIsSuitAomen(Byte isSuitAomen) {
		this.isSuitAomen = isSuitAomen;
	}

	@Basic
	@Column(name = "is_suit_hongkong")
	public Byte getIsSuitHongkong() {
		return isSuitHongkong;
	}

	public void setIsSuitHongkong(Byte isSuitHongkong) {
		this.isSuitHongkong = isSuitHongkong;
	}

	@Basic
	@Column(name = "is_suit_abroad")
	public Byte getIsSuitAbroad() {
		return isSuitAbroad;
	}

	public void setIsSuitAbroad(Byte isSuitAbroad) {
		this.isSuitAbroad = isSuitAbroad;
	}

	@Basic
	@Column(name = "is_suit_outer")
	public Byte getIsSuitOuter() {
		return isSuitOuter;
	}

	public void setIsSuitOuter(Byte isSuitOuter) {
		this.isSuitOuter = isSuitOuter;
	}

	@Basic
	@Column(name = "is_suit_threaten")
	public Byte getIsSuitThreaten() {
		return isSuitThreaten;
	}

	public void setIsSuitThreaten(Byte isSuitThreaten) {
		this.isSuitThreaten = isSuitThreaten;
	}

	@Basic
	@Column(name = "follow_guest_ids")
	public String getFollowGuestIds() {
		return followGuestIds;
	}

	public void setFollowGuestIds(String followGuestIds) {
		this.followGuestIds = followGuestIds;
	}

	@Basic
	@Column(name = "create_name")
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Basic
	@Column(name = "create_window")
	public String getCreateWindow() {
		return createWindow;
	}

	public void setCreateWindow(String createWindow) {
		this.createWindow = createWindow;
	}

	@Basic
	@Column(name = "approve_id")
	public Integer getApproveId() {
		return approveId;
	}

	public void setApproveId(Integer approveId) {
		this.approveId = approveId;
	}

	@Basic
	@Column(name = "handle_userid")
	public Integer getHandleUserid() {
		return handleUserid;
	}

	public void setHandleUserid(Integer handleUserid) {
		this.handleUserid = handleUserid;
	}

	@Basic
	@Column(name = "associated_numbers")
	public Integer getAssociatedNumbers() {
		return associatedNumbers;
	}

	public void setAssociatedNumbers(Integer associatedNumbers) {
		this.associatedNumbers = associatedNumbers;
	}

	@Basic
	@Column(name = "handle_flow_type")
	public Byte getHandleFlowType() {
		return handleFlowType;
	}

	public void setHandleFlowType(Byte handleFlowType) {
		this.handleFlowType = handleFlowType;
	}

	@Basic
	@Column(name = "is_handle")
	public Byte getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(Byte isHandle) {
		this.isHandle = isHandle;
	}

	@Basic
	@Column(name = "is_degree")
	public Byte getIsDegree() {
		return isDegree;
	}

	public void setIsDegree(Byte isDegree) {
		this.isDegree = isDegree;
	}

	@Basic
	@Column(name = "is_timely_accept")
	public Byte getIsTimelyAccept() {
		return isTimelyAccept;
	}

	public void setIsTimelyAccept(Byte isTimelyAccept) {
		this.isTimelyAccept = isTimelyAccept;
	}

	@Basic
	@Column(name = "is_ontime_finish")
	public Byte getIsOntimeFinish() {
		return isOntimeFinish;
	}

	public void setIsOntimeFinish(Byte isOntimeFinish) {
		this.isOntimeFinish = isOntimeFinish;
	}

	@Basic
	@Column(name = "is_flow")
	public Integer getIsFlow() {
		return isFlow;
	}

	public void setIsFlow(Integer isFlow) {
		this.isFlow = isFlow;
	}

	@Basic
	@Column(name = "current_handle_unitid")
	public Integer getCurrentHandleUnitid() {
		return currentHandleUnitid;
	}

	public void setCurrentHandleUnitid(Integer currentHandleUnitid) {
		this.currentHandleUnitid = currentHandleUnitid;
	}

	@Basic
	@Column(name = "handle_type")
	public Integer getHandleType() {
		return handleType;
	}

	public void setHandleType(Integer handleType) {
		this.handleType = handleType;
	}

	@Basic
	@Column(name = "current_handle_personalid")
	public Integer getCurrentHandlePersonalid() {
		return currentHandlePersonalid;
	}

	public void setCurrentHandlePersonalid(Integer currentHandlePersonalid) {
		this.currentHandlePersonalid = currentHandlePersonalid;
	}

	@Basic
	@Column(name = "current_handle_state")
	public String getCurrentHandleState() {
		return currentHandleState;
	}

	public void setCurrentHandleState(String currentHandleState) {
		this.currentHandleState = currentHandleState;
	}

	@Basic
	@Column(name = "handle_days")
	public Integer getHandleDays() {
		return handleDays;
	}

	public void setHandleDays(Integer handleDays) {
		this.handleDays = handleDays;
	}

	@Basic
	@Column(name = "handle_fact_end_time")
	public Date getHandleFactEndTime() {
		return handleFactEndTime;
	}

	public void setHandleFactEndTime(Date handleFactEndTime) {
		this.handleFactEndTime = handleFactEndTime;
	}

	@Basic
	@Column(name = "create_unitid")
	public Integer getCreateUnitid() {
		return createUnitid;
	}

	public void setCreateUnitid(Integer createUnitid) {
		this.createUnitid = createUnitid;
	}

	@Basic
	@Column(name = "transfer_unitid")
	public Integer getTransferUnitid() {
		return transferUnitid;
	}

	public void setTransferUnitid(Integer transferUnitid) {
		this.transferUnitid = transferUnitid;
	}

	@Basic
	@Column(name = "transfer_personalid")
	public Integer getTransferPersonalid() {
		return transferPersonalid;
	}

	public void setTransferPersonalid(Integer transferPersonalid) {
		this.transferPersonalid = transferPersonalid;
	}

	@Basic
	@Column(name = "receive_unitid")
	public Integer getReceiveUnitid() {
		return receiveUnitid;
	}

	public void setReceiveUnitid(Integer receiveUnitid) {
		this.receiveUnitid = receiveUnitid;
	}

	@Basic
	@Column(name = "receive_personalid")
	public Integer getReceivePersonalid() {
		return receivePersonalid;
	}

	public void setReceivePersonalid(Integer receivePersonalid) {
		this.receivePersonalid = receivePersonalid;
	}

	@Column(name = "transfer_to_petition_unit")
	public Byte getTransferToPetitionUnit() {
		return transferToPetitionUnit;
	}

	public void setTransferToPetitionUnit(Byte transferToPetitionUnit) {
		this.transferToPetitionUnit = transferToPetitionUnit;
	}

	@Column(name = "dished_id")
	public Integer getDishedId() {
		return dishedId;
	}

	public void setDishedId(Integer dishedId) {
		this.dishedId = dishedId;
	}

	@Transient
	public String getStrQuestionBelongTo() {
		return strQuestionBelongTo;
	}

	public void setStrQuestionBelongTo(String strQuestionBelongTo) {
		this.strQuestionBelongTo = strQuestionBelongTo;
	}

	@Transient
	public String getStrBelongToSys() {
		return strBelongToSys;
	}

	public void setStrBelongToSys(String strBelongToSys) {
		this.strBelongToSys = strBelongToSys;
	}

	@Transient
	public String getStrCaseType() {
		return strCaseType;
	}

	public void setStrCaseType(String strCaseType) {
		this.strCaseType = strCaseType;
	}

	@Transient
	public String getStrExposeObject() {
		return strExposeObject;
	}

	public void setStrExposeObject(String strExposeObject) {
		this.strExposeObject = strExposeObject;
	}

	@Transient
	public String getStrPetitionCountry() {
		return strPetitionCountry;
	}

	public void setStrPetitionCountry(String strPetitionCountry) {
		this.strPetitionCountry = strPetitionCountry;
	}

	@Transient
	public String getStrPetitionNature() {
		return strPetitionNature;
	}

	public void setStrPetitionNature(String strPetitionNature) {
		this.strPetitionNature = strPetitionNature;
	}

	@Transient
	public String getStrPetitionPurpose() {
		return strPetitionPurpose;
	}

	public void setStrPetitionPurpose(String strPetitionPurpose) {
		this.strPetitionPurpose = strPetitionPurpose;
	}

	@Transient
	public String getStrPetitionWay() {
		return strPetitionWay;
	}

	public void setStrPetitionWay(String strPetitionWay) {
		this.strPetitionWay = strPetitionWay;
	}

	@Transient
	public String getStrPetitionWhy() {
		return strPetitionWhy;
	}

	public void setStrPetitionWhy(String strPetitionWhy) {
		this.strPetitionWhy = strPetitionWhy;
	}

	@Transient
	public String getStrQuestionHot() {
		return strQuestionHot;
	}

	public void setStrQuestionHot(String strQuestionHot) {
		this.strQuestionHot = strQuestionHot;
	}

	@Transient
	public String getStrQusetionType() {
		return strQusetionType;
	}

	public void setStrQusetionType(String strQusetionType) {
		this.strQusetionType = strQusetionType;
	}

	@Transient
	public String getStrThirteenCategories() {
		return strThirteenCategories;
	}

	public void setStrThirteenCategories(String strThirteenCategories) {
		this.strThirteenCategories = strThirteenCategories;
	}

	public Integer getQuestionType1() {
		return questionType1;
	}

	public void setQuestionType1(Integer questionType1) {
		this.questionType1 = questionType1;
	}

	public Integer getQuestionType2() {
		return questionType2;
	}

	public void setQuestionType2(Integer questionType2) {
		this.questionType2 = questionType2;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		DisputeCaseEntity that = (DisputeCaseEntity) o;

		if (guestId != that.guestId)
			return false;
		if (state != that.state)
			return false;
		if (disputeCaseId != that.disputeCaseId)
			return false;
		if (petitionNames != null ? !petitionNames.equals(that.petitionNames) : that.petitionNames != null)
			return false;
		if (petitionNumbers != null ? !petitionNumbers.equals(that.petitionNumbers) : that.petitionNumbers != null)
			return false;
		if (petitionTime != null ? !petitionTime.equals(that.petitionTime) : that.petitionTime != null)
			return false;
		if (petitionCounty != null ? !petitionCounty.equals(that.petitionCounty) : that.petitionCounty != null)
			return false;
		if (petitionUnit != null ? !petitionUnit.equals(that.petitionUnit) : that.petitionUnit != null)
			return false;
		if (petitionWay != null ? !petitionWay.equals(that.petitionWay) : that.petitionWay != null)
			return false;
		if (petitionCode != null ? !petitionCode.equals(that.petitionCode) : that.petitionCode != null)
			return false;
		if (isRepeatPetition != null ? !isRepeatPetition.equals(that.isRepeatPetition) : that.isRepeatPetition != null)
			return false;
		if (caseTime != null ? !caseTime.equals(that.caseTime) : that.caseTime != null)
			return false;
		if (caseAddress != null ? !caseAddress.equals(that.caseAddress) : that.caseAddress != null)
			return false;
		if (caseDesc != null ? !caseDesc.equals(that.caseDesc) : that.caseDesc != null)
			return false;
		if (caseDemand != null ? !caseDemand.equals(that.caseDemand) : that.caseDemand != null)
			return false;
		if (fileSrc != null ? !fileSrc.equals(that.fileSrc) : that.fileSrc != null)
			return false;
		if (handleDuration != null ? !handleDuration.equals(that.handleDuration) : that.handleDuration != null)
			return false;
		if (handlePeriodStart != null ? !handlePeriodStart.equals(that.handlePeriodStart)
				: that.handlePeriodStart != null)
			return false;
		if (handlePeriodEnd != null ? !handlePeriodEnd.equals(that.handlePeriodEnd) : that.handlePeriodEnd != null)
			return false;
		if (questionBelongingTo != null ? !questionBelongingTo.equals(that.questionBelongingTo)
				: that.questionBelongingTo != null)
			return false;
		if (questionType != null ? !questionType.equals(that.questionType) : that.questionType != null)
			return false;
		if (caseType != null ? !caseType.equals(that.caseType) : that.caseType != null)
			return false;
		if (questionHot != null ? !questionHot.equals(that.questionHot) : that.questionHot != null)
			return false;
		if (belongToSys != null ? !belongToSys.equals(that.belongToSys) : that.belongToSys != null)
			return false;
		if (thirteenCategories != null ? !thirteenCategories.equals(that.thirteenCategories)
				: that.thirteenCategories != null)
			return false;
		if (petitionWhy != null ? !petitionWhy.equals(that.petitionWhy) : that.petitionWhy != null)
			return false;
		if (petitionPurpose != null ? !petitionPurpose.equals(that.petitionPurpose) : that.petitionPurpose != null)
			return false;
		if (isLeaderLimited != null ? !isLeaderLimited.equals(that.isLeaderLimited) : that.isLeaderLimited != null)
			return false;
		if (exposeObject != null ? !exposeObject.equals(that.exposeObject) : that.exposeObject != null)
			return false;
		if (petitionNature != null ? !petitionNature.equals(that.petitionNature) : that.petitionNature != null)
			return false;
		if (trusteName != null ? !trusteName.equals(that.trusteName) : that.trusteName != null)
			return false;
		if (isAbove != null ? !isAbove.equals(that.isAbove) : that.isAbove != null)
			return false;
		if (isBacklog != null ? !isBacklog.equals(that.isBacklog) : that.isBacklog != null)
			return false;
		if (isReview != null ? !isReview.equals(that.isReview) : that.isReview != null)
			return false;
		if (isSatisfied != null ? !isSatisfied.equals(that.isSatisfied) : that.isSatisfied != null)
			return false;
		if (isProminent != null ? !isProminent.equals(that.isProminent) : that.isProminent != null)
			return false;
		if (isAcrossFrom != null ? !isAcrossFrom.equals(that.isAcrossFrom) : that.isAcrossFrom != null)
			return false;
		if (isLeaderPackageCase != null ? !isLeaderPackageCase.equals(that.isLeaderPackageCase)
				: that.isLeaderPackageCase != null)
			return false;
		if (isLeaderApprove != null ? !isLeaderApprove.equals(that.isLeaderApprove) : that.isLeaderApprove != null)
			return false;
		if (isTypeRight != null ? !isTypeRight.equals(that.isTypeRight) : that.isTypeRight != null)
			return false;
		if (isScaleRight != null ? !isScaleRight.equals(that.isScaleRight) : that.isScaleRight != null)
			return false;
		if (isTierRight != null ? !isTierRight.equals(that.isTierRight) : that.isTierRight != null)
			return false;
		if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null)
			return false;
		if (gmtModified != null ? !gmtModified.equals(that.gmtModified) : that.gmtModified != null)
			return false;
		if (creatorId != null ? !creatorId.equals(that.creatorId) : that.creatorId != null)
			return false;
		if (caseHandleState != null ? !caseHandleState.equals(that.caseHandleState) : that.caseHandleState != null)
			return false;
		if (type != null ? !type.equals(that.type) : that.type != null)
			return false;
		if (windowNumber != null ? !windowNumber.equals(that.windowNumber) : that.windowNumber != null)
			return false;
		if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null)
			return false;
		if (attachCaseDesc != null ? !attachCaseDesc.equals(that.attachCaseDesc) : that.attachCaseDesc != null)
			return false;
		if (attachCaseIds != null ? !attachCaseIds.equals(that.attachCaseIds) : that.attachCaseIds != null)
			return false;
		if (form != null ? !form.equals(that.form) : that.form != null)
			return false;
		if (title != null ? !title.equals(that.title) : that.title != null)
			return false;
		if (isSuitWade != null ? !isSuitWade.equals(that.isSuitWade) : that.isSuitWade != null)
			return false;
		if (isSuitTaiwan != null ? !isSuitTaiwan.equals(that.isSuitTaiwan) : that.isSuitTaiwan != null)
			return false;
		if (isSuitAomen != null ? !isSuitAomen.equals(that.isSuitAomen) : that.isSuitAomen != null)
			return false;
		if (isSuitHongkong != null ? !isSuitHongkong.equals(that.isSuitHongkong) : that.isSuitHongkong != null)
			return false;
		if (isSuitAbroad != null ? !isSuitAbroad.equals(that.isSuitAbroad) : that.isSuitAbroad != null)
			return false;
		if (isSuitOuter != null ? !isSuitOuter.equals(that.isSuitOuter) : that.isSuitOuter != null)
			return false;
		if (isSuitThreaten != null ? !isSuitThreaten.equals(that.isSuitThreaten) : that.isSuitThreaten != null)
			return false;
		if (followGuestIds != null ? !followGuestIds.equals(that.followGuestIds) : that.followGuestIds != null)
			return false;
		if (createName != null ? !createName.equals(that.createName) : that.createName != null)
			return false;
		if (createWindow != null ? !createWindow.equals(that.createWindow) : that.createWindow != null)
			return false;
		if (approveId != null ? !approveId.equals(that.approveId) : that.approveId != null)
			return false;
		if (handleUserid != null ? !handleUserid.equals(that.handleUserid) : that.handleUserid != null)
			return false;
		if (associatedNumbers != null ? !associatedNumbers.equals(that.associatedNumbers)
				: that.associatedNumbers != null)
			return false;
		if (handleFlowType != null ? !handleFlowType.equals(that.handleFlowType) : that.handleFlowType != null)
			return false;
		if (isHandle != null ? !isHandle.equals(that.isHandle) : that.isHandle != null)
			return false;
		if (isDegree != null ? !isDegree.equals(that.isDegree) : that.isDegree != null)
			return false;
		if (isTimelyAccept != null ? !isTimelyAccept.equals(that.isTimelyAccept) : that.isTimelyAccept != null)
			return false;
		if (isOntimeFinish != null ? !isOntimeFinish.equals(that.isOntimeFinish) : that.isOntimeFinish != null)
			return false;
		if (isFlow != null ? !isFlow.equals(that.isFlow) : that.isFlow != null)
			return false;
		if (currentHandleUnitid != null ? !currentHandleUnitid.equals(that.currentHandleUnitid)
				: that.currentHandleUnitid != null)
			return false;
		if (handleType != null ? !handleType.equals(that.handleType) : that.handleType != null)
			return false;
		if (currentHandlePersonalid != null ? !currentHandlePersonalid.equals(that.currentHandlePersonalid)
				: that.currentHandlePersonalid != null)
			return false;
		if (currentHandleState != null ? !currentHandleState.equals(that.currentHandleState)
				: that.currentHandleState != null)
			return false;
		if (handleDays != null ? !handleDays.equals(that.handleDays) : that.handleDays != null)
			return false;
		if (handleFactEndTime != null ? !handleFactEndTime.equals(that.handleFactEndTime)
				: that.handleFactEndTime != null)
			return false;
		if (createUnitid != null ? !createUnitid.equals(that.createUnitid) : that.createUnitid != null)
			return false;
		if (transferUnitid != null ? !transferUnitid.equals(that.transferUnitid) : that.transferUnitid != null)
			return false;
		if (transferPersonalid != null ? !transferPersonalid.equals(that.transferPersonalid)
				: that.transferPersonalid != null)
			return false;
		if (receiveUnitid != null ? !receiveUnitid.equals(that.receiveUnitid) : that.receiveUnitid != null)
			return false;
		if (receivePersonalid != null ? !receivePersonalid.equals(that.receivePersonalid)
				: that.receivePersonalid != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = petitionNames != null ? petitionNames.hashCode() : 0;
		result = 31 * result + (petitionNumbers != null ? petitionNumbers.hashCode() : 0);
		result = 31 * result + (petitionTime != null ? petitionTime.hashCode() : 0);
		result = 31 * result + (petitionCounty != null ? petitionCounty.hashCode() : 0);
		result = 31 * result + (petitionUnit != null ? petitionUnit.hashCode() : 0);
		result = 31 * result + (petitionWay != null ? petitionWay.hashCode() : 0);
		result = 31 * result + (petitionCode != null ? petitionCode.hashCode() : 0);
		result = 31 * result + (isRepeatPetition != null ? isRepeatPetition.hashCode() : 0);
		result = 31 * result + (caseTime != null ? caseTime.hashCode() : 0);
		result = 31 * result + (caseAddress != null ? caseAddress.hashCode() : 0);
		result = 31 * result + (caseDesc != null ? caseDesc.hashCode() : 0);
		result = 31 * result + (caseDemand != null ? caseDemand.hashCode() : 0);
		result = 31 * result + (fileSrc != null ? fileSrc.hashCode() : 0);
		result = 31 * result + (handleDuration != null ? handleDuration.hashCode() : 0);
		result = 31 * result + (handlePeriodStart != null ? handlePeriodStart.hashCode() : 0);
		result = 31 * result + (handlePeriodEnd != null ? handlePeriodEnd.hashCode() : 0);
		result = 31 * result + (questionBelongingTo != null ? questionBelongingTo.hashCode() : 0);
		result = 31 * result + (questionType != null ? questionType.hashCode() : 0);
		result = 31 * result + (caseType != null ? caseType.hashCode() : 0);
		result = 31 * result + (questionHot != null ? questionHot.hashCode() : 0);
		result = 31 * result + (belongToSys != null ? belongToSys.hashCode() : 0);
		result = 31 * result + (thirteenCategories != null ? thirteenCategories.hashCode() : 0);
		result = 31 * result + (petitionWhy != null ? petitionWhy.hashCode() : 0);
		result = 31 * result + (petitionPurpose != null ? petitionPurpose.hashCode() : 0);
		result = 31 * result + (isLeaderLimited != null ? isLeaderLimited.hashCode() : 0);
		result = 31 * result + (exposeObject != null ? exposeObject.hashCode() : 0);
		result = 31 * result + (petitionNature != null ? petitionNature.hashCode() : 0);
		result = 31 * result + (trusteName != null ? trusteName.hashCode() : 0);
		result = 31 * result + (isAbove != null ? isAbove.hashCode() : 0);
		result = 31 * result + (isBacklog != null ? isBacklog.hashCode() : 0);
		result = 31 * result + (isReview != null ? isReview.hashCode() : 0);
		result = 31 * result + (isSatisfied != null ? isSatisfied.hashCode() : 0);
		result = 31 * result + (isProminent != null ? isProminent.hashCode() : 0);
		result = 31 * result + (isAcrossFrom != null ? isAcrossFrom.hashCode() : 0);
		result = 31 * result + (isLeaderPackageCase != null ? isLeaderPackageCase.hashCode() : 0);
		result = 31 * result + (isLeaderApprove != null ? isLeaderApprove.hashCode() : 0);
		result = 31 * result + (isTypeRight != null ? isTypeRight.hashCode() : 0);
		result = 31 * result + (isScaleRight != null ? isScaleRight.hashCode() : 0);
		result = 31 * result + (isTierRight != null ? isTierRight.hashCode() : 0);
		result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
		result = 31 * result + (gmtModified != null ? gmtModified.hashCode() : 0);
		result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
		result = 31 * result + (caseHandleState != null ? caseHandleState.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (windowNumber != null ? windowNumber.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (attachCaseDesc != null ? attachCaseDesc.hashCode() : 0);
		result = 31 * result + guestId;
		result = 31 * result + (attachCaseIds != null ? attachCaseIds.hashCode() : 0);
		result = 31 * result + (form != null ? form.hashCode() : 0);
		result = 31 * result + state;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (isSuitWade != null ? isSuitWade.hashCode() : 0);
		result = 31 * result + (isSuitTaiwan != null ? isSuitTaiwan.hashCode() : 0);
		result = 31 * result + (isSuitAomen != null ? isSuitAomen.hashCode() : 0);
		result = 31 * result + (isSuitHongkong != null ? isSuitHongkong.hashCode() : 0);
		result = 31 * result + (isSuitAbroad != null ? isSuitAbroad.hashCode() : 0);
		result = 31 * result + (isSuitOuter != null ? isSuitOuter.hashCode() : 0);
		result = 31 * result + (isSuitThreaten != null ? isSuitThreaten.hashCode() : 0);
		result = 31 * result + (followGuestIds != null ? followGuestIds.hashCode() : 0);
		result = 31 * result + (createName != null ? createName.hashCode() : 0);
		result = 31 * result + (createWindow != null ? createWindow.hashCode() : 0);
		result = 31 * result + (approveId != null ? approveId.hashCode() : 0);
		result = 31 * result + (handleUserid != null ? handleUserid.hashCode() : 0);
		result = 31 * result + (associatedNumbers != null ? associatedNumbers.hashCode() : 0);
		result = 31 * result + (handleFlowType != null ? handleFlowType.hashCode() : 0);
		result = 31 * result + (isHandle != null ? isHandle.hashCode() : 0);
		result = 31 * result + (isDegree != null ? isDegree.hashCode() : 0);
		result = 31 * result + (isTimelyAccept != null ? isTimelyAccept.hashCode() : 0);
		result = 31 * result + (isOntimeFinish != null ? isOntimeFinish.hashCode() : 0);
		result = 31 * result + (isFlow != null ? isFlow.hashCode() : 0);
		result = 31 * result + (currentHandleUnitid != null ? currentHandleUnitid.hashCode() : 0);
		result = 31 * result + (handleType != null ? handleType.hashCode() : 0);
		result = 31 * result + (currentHandlePersonalid != null ? currentHandlePersonalid.hashCode() : 0);
		result = 31 * result + (currentHandleState != null ? currentHandleState.hashCode() : 0);
		result = 31 * result + (handleDays != null ? handleDays.hashCode() : 0);
		result = 31 * result + (handleFactEndTime != null ? handleFactEndTime.hashCode() : 0);
		result = 31 * result + (createUnitid != null ? createUnitid.hashCode() : 0);
		result = 31 * result + (transferUnitid != null ? transferUnitid.hashCode() : 0);
		result = 31 * result + (transferPersonalid != null ? transferPersonalid.hashCode() : 0);
		result = 31 * result + (receiveUnitid != null ? receiveUnitid.hashCode() : 0);
		result = 31 * result + (receivePersonalid != null ? receivePersonalid.hashCode() : 0);
		result = 31 * result + disputeCaseId;
		return result;
	}
}
