package com.xinfang.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xinfang.utils.DateUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by sunbjx on 2017/3/24.
 */
@Entity
@Table(name = "fd_case")
public class FdCase {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ApiModelProperty(value = "信访人员")
	private String petitionNames;
	@ApiModelProperty(value = "信访人数")
	private Integer petitionNumbers;
	@ApiModelProperty(value = "涉及人数")
	private int associatedNumbers;
	@ApiModelProperty(value = "信访时间")
	private Date petitionTime;
	@ApiModelProperty(value = "信访区县")
	private Integer petitionCounty;
	@ApiModelProperty(value = "信访单位")
	private String petitionUnit;
	@ApiModelProperty(value = "信访方式")
	private Integer petitionWay;
	@ApiModelProperty(value = "是否重复信访")
	private Byte isRepeatPetition;
	@ApiModelProperty(value = "事件时间")
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
	@ApiModelProperty(value = "处理时间段开始")
	private Date handlePeriodStart;
	@ApiModelProperty(value = "处理时间段结束")
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
	@ApiModelProperty(value = "领导批示ID")
	private Integer approveId;
	@ApiModelProperty(value = "方式是否正确")
	private Byte isTypeRight;
	@ApiModelProperty(value = "规模准确")
	private Byte isScaleRight;
	@ApiModelProperty(value = "层级是否准确")
	private Byte isTierRight;
	@ApiModelProperty(value = "案件录入时间")
	private Date gmtCreate;
	@ApiModelProperty(value = "案件更新时间")
	private Date gmtModified;
	// 1 个访 2 群访
	@ApiModelProperty(readOnly = true)
	private Integer type;
	@ApiModelProperty(value = "创建者")
	private Integer creatorId;
	@ApiModelProperty(value = "案件状态")
	private Integer caseHandleState;
	@ApiModelProperty(value = "窗口编号")
	private Integer windowNumber;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "上诉人ID")
	// @Column(nullable = false)
	private Integer guestId;
	@ApiModelProperty(value = "随访人ID")
	private String followGuestIds;
	@ApiModelProperty(value = "关联案件IDs")
	private String attachCaseIds;
	@ApiModelProperty(value = "关联案件说明")
	private String attachCaseDesc;
	@ApiModelProperty(value = "数据状态")
	private int state;
	@ApiModelProperty(value = "")
	private String title;
	@ApiModelProperty(value = "信访编号")
	private String petitionCode;
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
	@ApiModelProperty(value = "案件处理人ID", required = true)
	private Integer handleUserid;
	@ApiModelProperty(value = "处理流程分类")
	private Byte handleFlowType;
	@ApiModelProperty(value = "-1 其他  6 分流室  5 窗口")
	private Byte form;
	@ApiModelProperty(value = "0 未录入  1 已经录入 2 已经进行案件处理")
	private Byte isHandle;
	@ApiModelProperty(value = "0 非流程 1 流程")
	@Column(columnDefinition = "INT default 0")
	private Byte isFlow;
	@ApiModelProperty(value = "当前处理单位ID")
	private Integer currentHandleUnitid;
	@ApiModelProperty(value = "当前处理人ID")
	private Integer currentHandlePersonalid;
	@ApiModelProperty(value = "处理方式")
	private Integer handleType;
	@ApiModelProperty(value = "当前处理状态")
	private String currentHandleState;
	@ApiModelProperty(value = "处理天数")
	private Integer handleDays;
	@ApiModelProperty(value = "案件实际结束时间")
	private Date handleFactEndTime;
	@ApiModelProperty(value = "案件录入单位ID")
	private Integer createUnitid;
	@ApiModelProperty(value = "案件转交单位ID")
	private Integer transferUnitid;
	@ApiModelProperty(value = "案件转件人ID")
	private Integer transferPersonalid;
	@ApiModelProperty(value = "案件接收单位ID")
	private Integer receiveUnitid;
	@ApiModelProperty(value = "案件接收人ID")
	private Integer receivePersonalid;
	// 矛盾纠纷案件ID
	private Integer disputeCaseId;
	@ApiModelProperty(value = "网络案件收文岗派送人ID")
	private Integer dispatcherToUserid;
	@ApiModelProperty(value = "0 不是收文岗接收  1 是收文岗接收")
	private Byte isDispatcherReceive;
	@ApiModelProperty(value = "分流室案件描述")
	private String turnCaseDesc;
	@ApiModelProperty(value = "关联案件ID")
	private String relationCaseid;
	@ApiModelProperty(value = "分流室审核状态 1 确认退回 0 未审核 -1 未通过")
	private Byte turnAuditState;

	// 非序列化字段
	@Transient
	private String strPetitionCountry;
	@Transient
	private String strPetitionWay;
	@Transient
	private String strQuestionBelongTo;
	@Transient
	private String strQusetionType;
	@Transient
	private String strCaseType;
	@Transient
	private String strQuestionHot;
	@Transient
	private String strBelongToSys;
	@Transient
	private String strThirteenCategories;
	@Transient
	private String strPetitionWhy;
	@Transient
	private String strPetitionPurpose;
	@Transient
	private String strExposeObject;
	@Transient
	private String strPetitionNature;
	// 首页列表过滤
	@Transient
	private String taskId;
	// 预警级别
	@Transient
	private String warn;
	@Transient
	private String comefrom;// "web" 表示来自网上

	public String getComefrom() {
		return comefrom;
	}

	public void setComefrom(String comefrom) {
		this.comefrom = comefrom;
	}

	public String getWarn() {
		String result = null;
		Date now = new Date();
		if (handleDays != null) {
			Float normal = handleDays * 0.5f;// 正常
			Float warn = handleDays * 0.75f;// 预警
			Float warn1 = handleDays * 0.85f;// 警告
			Float warn2 = handleDays * 0.99f;// 严重警告
			// Float warn3 = limitdaynum;//超期
			Date showdTime = DateUtils.add(gmtCreate, normal);
			Date showdTime1 = DateUtils.add(gmtCreate, warn);
			Date showdTime2 = DateUtils.add(gmtCreate, warn1);
			Date showdTime3 = DateUtils.add(gmtCreate, warn2);
			// Date showdTime4 = DateUtils.add(DateUtils.strToDate2(startTime),
			// warn3);
			if (caseHandleState == 2003) {
				if (handleFactEndTime != null) {
					if (handleFactEndTime.getTime() < showdTime.getTime()) {
						result = "正常";
					} else if (handleFactEndTime.getTime() < showdTime1.getTime()) {
						result = "预警";
					} else if (handleFactEndTime.getTime() < showdTime2.getTime()) {
						result = "警告";
					} else if (handleFactEndTime.getTime() < showdTime3.getTime()) {
						result = "严重警告";
					} else {
						result = "超期";
					}
				} else {
					result = "正常";
				}

			} else {
				if (now.getTime() < showdTime.getTime()) {
					result = "正常";
				} else if (now.getTime() < showdTime1.getTime()) {
					result = "预警";
				} else if (now.getTime() < showdTime2.getTime()) {
					result = "警告";
				} else if (now.getTime() < showdTime3.getTime()) {
					result = "严重警告";
				} else {
					result = "超期";
				}
			}

		}
		return result;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPetitionNames() {
		return petitionNames;
	}

	public void setPetitionNames(String petitionNames) {
		this.petitionNames = petitionNames;
	}

	public Integer getPetitionNumbers() {
		return petitionNumbers;
	}

	public void setPetitionNumbers(Integer petitionNumbers) {
		this.petitionNumbers = petitionNumbers;
	}

	public int getAssociatedNumbers() {
		return associatedNumbers;
	}

	public void setAssociatedNumbers(int associatedNumbers) {
		this.associatedNumbers = associatedNumbers;
	}

	public Date getPetitionTime() {
		return petitionTime;
	}

	public void setPetitionTime(Date petitionTime) {
		this.petitionTime = petitionTime;
	}

	public Integer getPetitionCounty() {
		return petitionCounty;
	}

	public void setPetitionCounty(Integer petitionCounty) {
		this.petitionCounty = petitionCounty;
	}

	public String getPetitionUnit() {
		return petitionUnit;
	}

	public void setPetitionUnit(String petitionUnit) {
		this.petitionUnit = petitionUnit;
	}

	public Integer getPetitionWay() {
		return petitionWay;
	}

	public void setPetitionWay(Integer petitionWay) {
		this.petitionWay = petitionWay;
	}

	public Byte getIsRepeatPetition() {
		return isRepeatPetition;
	}

	public void setIsRepeatPetition(Byte isRepeatPetition) {
		this.isRepeatPetition = isRepeatPetition;
	}

	public Date getCaseTime() {
		return caseTime;
	}

	public void setCaseTime(Date caseTime) {
		this.caseTime = caseTime;
	}

	public String getCaseAddress() {
		return caseAddress;
	}

	public void setCaseAddress(String caseAddress) {
		this.caseAddress = caseAddress;
	}

	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}

	public String getCaseDemand() {
		return caseDemand;
	}

	public void setCaseDemand(String caseDemand) {
		this.caseDemand = caseDemand;
	}

	public String getFileSrc() {
		return fileSrc;
	}

	public void setFileSrc(String fileSrc) {
		this.fileSrc = fileSrc;
	}

	public Integer getHandleDuration() {
		return handleDuration;
	}

	public void setHandleDuration(Integer handleDuration) {
		this.handleDuration = handleDuration;
	}

	public Date getHandlePeriodStart() {
		return handlePeriodStart;
	}

	public void setHandlePeriodStart(Date handlePeriodStart) {
		this.handlePeriodStart = handlePeriodStart;
	}

	public Date getHandlePeriodEnd() {
		return handlePeriodEnd;
	}

	public void setHandlePeriodEnd(Date handlePeriodEnd) {
		this.handlePeriodEnd = handlePeriodEnd;
	}

	public Integer getQuestionBelongingTo() {
		return questionBelongingTo;
	}

	public void setQuestionBelongingTo(Integer questionBelongingTo) {
		this.questionBelongingTo = questionBelongingTo;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
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

	public Integer getCaseType() {
		return caseType;
	}

	public void setCaseType(Integer caseType) {
		this.caseType = caseType;
	}

	public Integer getQuestionHot() {
		return questionHot;
	}

	public void setQuestionHot(Integer questionHot) {
		this.questionHot = questionHot;
	}

	public Integer getBelongToSys() {
		return belongToSys;
	}

	public void setBelongToSys(Integer belongToSys) {
		this.belongToSys = belongToSys;
	}

	public Integer getThirteenCategories() {
		return thirteenCategories;
	}

	public void setThirteenCategories(Integer thirteenCategories) {
		this.thirteenCategories = thirteenCategories;
	}

	public Integer getPetitionWhy() {
		return petitionWhy;
	}

	public void setPetitionWhy(Integer petitionWhy) {
		this.petitionWhy = petitionWhy;
	}

	public Integer getPetitionPurpose() {
		return petitionPurpose;
	}

	public void setPetitionPurpose(Integer petitionPurpose) {
		this.petitionPurpose = petitionPurpose;
	}

	public Byte getIsLeaderLimited() {
		return isLeaderLimited;
	}

	public void setIsLeaderLimited(Byte isLeaderLimited) {
		this.isLeaderLimited = isLeaderLimited;
	}

	public Integer getExposeObject() {
		return exposeObject;
	}

	public void setExposeObject(Integer exposeObject) {
		this.exposeObject = exposeObject;
	}

	public Integer getPetitionNature() {
		return petitionNature;
	}

	public void setPetitionNature(Integer petitionNature) {
		this.petitionNature = petitionNature;
	}

	public String getTrusteName() {
		return trusteName;
	}

	public void setTrusteName(String trusteName) {
		this.trusteName = trusteName;
	}

	public Byte getIsAbove() {
		return isAbove;
	}

	public void setIsAbove(Byte isAbove) {
		this.isAbove = isAbove;
	}

	public Byte getIsBacklog() {
		return isBacklog;
	}

	public void setIsBacklog(Byte isBacklog) {
		this.isBacklog = isBacklog;
	}

	public Byte getIsReview() {
		return isReview;
	}

	public void setIsReview(Byte isReview) {
		this.isReview = isReview;
	}

	public Byte getIsSatisfied() {
		return isSatisfied;
	}

	public void setIsSatisfied(Byte isSatisfied) {
		this.isSatisfied = isSatisfied;
	}

	public Byte getIsProminent() {
		return isProminent;
	}

	public void setIsProminent(Byte isProminent) {
		this.isProminent = isProminent;
	}

	public Byte getIsAcrossFrom() {
		return isAcrossFrom;
	}

	public void setIsAcrossFrom(Byte isAcrossFrom) {
		this.isAcrossFrom = isAcrossFrom;
	}

	public Byte getIsLeaderPackageCase() {
		return isLeaderPackageCase;
	}

	public void setIsLeaderPackageCase(Byte isLeaderPackageCase) {
		this.isLeaderPackageCase = isLeaderPackageCase;
	}

	public Byte getIsLeaderApprove() {
		return isLeaderApprove;
	}

	public void setIsLeaderApprove(Byte isLeaderApprove) {
		this.isLeaderApprove = isLeaderApprove;
	}

	public Integer getApproveId() {
		return approveId;
	}

	public void setApproveId(Integer approveId) {
		this.approveId = approveId;
	}

	public Byte getIsTypeRight() {
		return isTypeRight;
	}

	public void setIsTypeRight(Byte isTypeRight) {
		this.isTypeRight = isTypeRight;
	}

	public Byte getIsScaleRight() {
		return isScaleRight;
	}

	public void setIsScaleRight(Byte isScaleRight) {
		this.isScaleRight = isScaleRight;
	}

	public Byte getIsTierRight() {
		return isTierRight;
	}

	public void setIsTierRight(Byte isTierRight) {
		this.isTierRight = isTierRight;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getCaseHandleState() {
		return caseHandleState;
	}

	public void setCaseHandleState(Integer caseHandleState) {
		this.caseHandleState = caseHandleState;
	}

	public Integer getWindowNumber() {
		return windowNumber;
	}

	public void setWindowNumber(Integer windowNumber) {
		this.windowNumber = windowNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getGuestId() {
		return guestId;
	}

	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}

	public String getFollowGuestIds() {
		return followGuestIds;
	}

	public void setFollowGuestIds(String followGuestIds) {
		this.followGuestIds = followGuestIds;
	}

	public String getAttachCaseIds() {
		return attachCaseIds;
	}

	public void setAttachCaseIds(String attachCaseIds) {
		this.attachCaseIds = attachCaseIds;
	}

	public String getAttachCaseDesc() {
		return attachCaseDesc;
	}

	public void setAttachCaseDesc(String attachCaseDesc) {
		this.attachCaseDesc = attachCaseDesc;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPetitionCode() {
		return petitionCode;
	}

	public void setPetitionCode(String petitionCode) {
		this.petitionCode = petitionCode;
	}

	public Byte getIsSuitWade() {
		return isSuitWade;
	}

	public void setIsSuitWade(Byte isSuitWade) {
		this.isSuitWade = isSuitWade;
	}

	public Byte getIsSuitTaiwan() {
		return isSuitTaiwan;
	}

	public void setIsSuitTaiwan(Byte isSuitTaiwan) {
		this.isSuitTaiwan = isSuitTaiwan;
	}

	public Byte getIsSuitAomen() {
		return isSuitAomen;
	}

	public void setIsSuitAomen(Byte isSuitAomen) {
		this.isSuitAomen = isSuitAomen;
	}

	public Byte getIsSuitHongkong() {
		return isSuitHongkong;
	}

	public void setIsSuitHongkong(Byte isSuitHongkong) {
		this.isSuitHongkong = isSuitHongkong;
	}

	public Byte getIsSuitAbroad() {
		return isSuitAbroad;
	}

	public void setIsSuitAbroad(Byte isSuitAbroad) {
		this.isSuitAbroad = isSuitAbroad;
	}

	public Byte getIsSuitOuter() {
		return isSuitOuter;
	}

	public void setIsSuitOuter(Byte isSuitOuter) {
		this.isSuitOuter = isSuitOuter;
	}

	public Byte getIsSuitThreaten() {
		return isSuitThreaten;
	}

	public void setIsSuitThreaten(Byte isSuitThreaten) {
		this.isSuitThreaten = isSuitThreaten;
	}

	public Integer getHandleUserid() {
		return handleUserid;
	}

	public void setHandleUserid(Integer handleUserid) {
		this.handleUserid = handleUserid;
	}

	public Byte getHandleFlowType() {
		return handleFlowType;
	}

	public void setHandleFlowType(Byte handleFlowType) {
		this.handleFlowType = handleFlowType;
	}

	public Byte getForm() {
		return form;
	}

	public void setForm(Byte form) {
		this.form = form;
	}

	public Byte getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(Byte isHandle) {
		this.isHandle = isHandle;
	}

	public Byte getIsFlow() {
		return isFlow;
	}

	public void setIsFlow(Byte isFlow) {
		this.isFlow = isFlow;
	}

	public Integer getCurrentHandleUnitid() {
		return currentHandleUnitid;
	}

	public void setCurrentHandleUnitid(Integer currentHandleUnitid) {
		this.currentHandleUnitid = currentHandleUnitid;
	}

	public Integer getCurrentHandlePersonalid() {
		return currentHandlePersonalid;
	}

	public void setCurrentHandlePersonalid(Integer currentHandlePersonalid) {
		this.currentHandlePersonalid = currentHandlePersonalid;
	}

	public Integer getHandleType() {
		return handleType;
	}

	public void setHandleType(Integer handleType) {
		this.handleType = handleType;
	}

	public String getCurrentHandleState() {
		return currentHandleState;
	}

	public void setCurrentHandleState(String currentHandleState) {
		this.currentHandleState = currentHandleState;
	}

	public Integer getHandleDays() {
		return handleDays;
	}

	public void setHandleDays(Integer handleDays) {
		this.handleDays = handleDays;
	}

	public Date getHandleFactEndTime() {
		return handleFactEndTime;
	}

	public void setHandleFactEndTime(Date handleFactEndTime) {
		this.handleFactEndTime = handleFactEndTime;
	}

	public Integer getCreateUnitid() {
		return createUnitid;
	}

	public void setCreateUnitid(Integer createUnitid) {
		this.createUnitid = createUnitid;
	}

	public Integer getTransferUnitid() {
		return transferUnitid;
	}

	public void setTransferUnitid(Integer transferUnitid) {
		this.transferUnitid = transferUnitid;
	}

	public Integer getTransferPersonalid() {
		return transferPersonalid;
	}

	public void setTransferPersonalid(Integer transferPersonalid) {
		this.transferPersonalid = transferPersonalid;
	}

	public Integer getReceiveUnitid() {
		return receiveUnitid;
	}

	public void setReceiveUnitid(Integer receiveUnitid) {
		this.receiveUnitid = receiveUnitid;
	}

	public Integer getReceivePersonalid() {
		return receivePersonalid;
	}

	public void setReceivePersonalid(Integer receivePersonalid) {
		this.receivePersonalid = receivePersonalid;
	}

	public Integer getDisputeCaseId() {
		return disputeCaseId;
	}

	public void setDisputeCaseId(Integer disputeCaseId) {
		this.disputeCaseId = disputeCaseId;
	}

	public Integer getDispatcherToUserid() {
		return dispatcherToUserid;
	}

	public void setDispatcherToUserid(Integer dispatcherToUserid) {
		this.dispatcherToUserid = dispatcherToUserid;
	}

	public Byte getIsDispatcherReceive() {
		return isDispatcherReceive;
	}

	public void setIsDispatcherReceive(Byte isDispatcherReceive) {
		this.isDispatcherReceive = isDispatcherReceive;
	}

	public String getTurnCaseDesc() {
		return turnCaseDesc;
	}

	public void setTurnCaseDesc(String turnCaseDesc) {
		this.turnCaseDesc = turnCaseDesc;
	}

	public String getRelationCaseid() {
		return relationCaseid;
	}

	public void setRelationCaseid(String relationCaseid) {
		this.relationCaseid = relationCaseid;
	}

	public Byte getTurnAuditState() {
		return turnAuditState;
	}

	public void setTurnAuditState(Byte turnAuditState) {
		this.turnAuditState = turnAuditState;
	}

	public String getStrPetitionCountry() {
		return strPetitionCountry;
	}

	public void setStrPetitionCountry(String strPetitionCountry) {
		this.strPetitionCountry = strPetitionCountry;
	}

	public String getStrPetitionWay() {
		return strPetitionWay;
	}

	public void setStrPetitionWay(String strPetitionWay) {
		this.strPetitionWay = strPetitionWay;
	}

	public String getStrQuestionBelongTo() {
		return strQuestionBelongTo;
	}

	public void setStrQuestionBelongTo(String strQuestionBelongTo) {
		this.strQuestionBelongTo = strQuestionBelongTo;
	}

	public String getStrQusetionType() {
		return strQusetionType;
	}

	public void setStrQusetionType(String strQusetionType) {
		this.strQusetionType = strQusetionType;
	}

	public String getStrCaseType() {
		return strCaseType;
	}

	public void setStrCaseType(String strCaseType) {
		this.strCaseType = strCaseType;
	}

	public String getStrQuestionHot() {
		return strQuestionHot;
	}

	public void setStrQuestionHot(String strQuestionHot) {
		this.strQuestionHot = strQuestionHot;
	}

	public String getStrBelongToSys() {
		return strBelongToSys;
	}

	public void setStrBelongToSys(String strBelongToSys) {
		this.strBelongToSys = strBelongToSys;
	}

	public String getStrThirteenCategories() {
		return strThirteenCategories;
	}

	public void setStrThirteenCategories(String strThirteenCategories) {
		this.strThirteenCategories = strThirteenCategories;
	}

	public String getStrPetitionWhy() {
		return strPetitionWhy;
	}

	public void setStrPetitionWhy(String strPetitionWhy) {
		this.strPetitionWhy = strPetitionWhy;
	}

	public String getStrPetitionPurpose() {
		return strPetitionPurpose;
	}

	public void setStrPetitionPurpose(String strPetitionPurpose) {
		this.strPetitionPurpose = strPetitionPurpose;
	}

	public String getStrExposeObject() {
		return strExposeObject;
	}

	public void setStrExposeObject(String strExposeObject) {
		this.strExposeObject = strExposeObject;
	}

	public String getStrPetitionNature() {
		return strPetitionNature;
	}

	public void setStrPetitionNature(String strPetitionNature) {
		this.strPetitionNature = strPetitionNature;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setStrPetitionWayandStrQuestionBelongTo(List<FdCode> petitionWay2, List<FdCode> questBelongTo1) {
		for (FdCode code : petitionWay2) {
			if (petitionWay != null) {
				if (petitionWay.intValue() == code.getCode().intValue()) {
					this.strPetitionWay = code.getName();
					break;
				}
			}

		}
		for (FdCode code : questBelongTo1) {
			if (questionBelongingTo != null) {
				if (questionBelongingTo.intValue() == code.getCode().intValue()) {
					this.strQuestionBelongTo = code.getName();
					break;
				}
			}

		}
	}

}
