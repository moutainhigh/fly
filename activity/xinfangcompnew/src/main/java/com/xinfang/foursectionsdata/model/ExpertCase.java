package com.xinfang.foursectionsdata.model;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "fh_case_database")
public class ExpertCase {

	    @Id
	    @Column(name = "ID")
	    @GeneratedValue
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
	    @Transient
	    private String strPetitionCountry;
	    
	    @ApiModelProperty(value = "信访单位")
	    private String petitionUnit;
	    
	    @ApiModelProperty(value = "信访方式")
	    private Integer petitionWay;
	    @Transient
	    private String strPetitionWay;
	    
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
	    @Transient
	    private String strQuestionBelongTo;
	    
	    @ApiModelProperty(value = "内容分类")
	    private Integer questionType;
	    @Transient
	    private String strQusetionType;
	    
	    @ApiModelProperty(value = "案件分类")
	    private Integer caseType;
	    @Transient
	    private String strCaseType;
	    
	    @ApiModelProperty(value = "热点问题")
	    private Integer questionHot;
	    @Transient
	    private String strQuestionHot;
	    
	    @ApiModelProperty(value = "所属系统")
	    private Integer belongToSys;
	    @Transient
	    private String strBelongToSys;
	    
		@ApiModelProperty(value = "13大类")
	    private Integer thirteenCategories;
		@Transient
		private String strThirteenCategories;
		
	    @ApiModelProperty(value = "信访原因")
	    private Integer petitionWhy;
	    @Transient
	    private String strPetitionWhy;
	    
	    @ApiModelProperty(value = "信访目的")
	    private Integer petitionPurpose;
	    @Transient
	    private String strPetitionPurpose;
	    
	    @ApiModelProperty(value = "领导限办")
	    private Byte isLeaderLimited;
	    
	    @ApiModelProperty(value = "揭发控告对象")
	    private Integer exposeObject;
	    @Transient
	    private String strExposeObject;
	    
	    @ApiModelProperty(value = "信访性质")
	    private Integer petitionNature;
	    @Transient
	    private String strPetitionNature;
	    
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
	    // 1 个访  2 群访
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
	    @ApiModelProperty(value = "-1 其他  0 分流室  1 窗口")
	    private Byte form;
	    @ApiModelProperty(value = "0 未录入  1 已经录入 2 已经进行案件处理")
	    private Byte isHandle;
	    @ApiModelProperty(value = "0 非流程 1 流程")
	    @Column(columnDefinition="INT default 0")
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

	   

		public Byte getIsFlow() {
	        return isFlow;
	    }

	    public void setIsFlow(Byte isFlow) {
	        this.isFlow = isFlow;
	    }

	   
	    public Byte getIsHandle() {
	        return isHandle;
	    }

	    public void setIsHandle(Byte isHandle) {
	        this.isHandle = isHandle;
	    }

	    public Byte getHandleFlowType() {
	        return handleFlowType;
	    }

	    public void setHandleFlowType(Byte handleFlowType) {
	        this.handleFlowType = handleFlowType;
	    }

	    public String getStrBelongToSys() {
			return strBelongToSys;
		}

		public void setStrBelongToSys(String strBelongToSys) {
			this.strBelongToSys = strBelongToSys;
		}
		
		
		
	    public int getAssociatedNumbers() {
	        return associatedNumbers;
	    }

	    public void setAssociatedNumbers(int associatedNumbers) {
	        this.associatedNumbers = associatedNumbers;
	    }

	    public Integer getHandleUserid() {
	        return handleUserid;
	    }

	    public void setHandleUserid(Integer handleUserid) {
	        this.handleUserid = handleUserid;
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
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
	    @Column(name = "is_leader_approve")
	    public Byte getIsLeaderApprove() {
	        return isLeaderApprove;
	    }

	    public void setIsLeaderApprove(Byte isLeaderApprove) {
	        this.isLeaderApprove = isLeaderApprove;
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
	    @Column(name = "is_acrossFrom")
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

//	    @Basic
//	    @Column(name = "is_urgent")
//	    public Byte getIsUrgent() {
//	        return isUrgent;
//	    }
	//
//	    public void setIsUrgent(Byte isUrgent) {
//	        this.isUrgent = isUrgent;
//	    }

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
	    @Column(name = "type")
	    public Integer getType() {
	        return type;
	    }

	    public void setType(Integer type) {
	        this.type = type;
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
	    @Column(name = "guest_id")
	    public Integer getGuestId() {
	        return guestId;
	    }

	    public void setGuestId(Integer guestId) {
	        this.guestId = guestId;
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
	    @Column(name = "attach_case_ids")
	    public String getAttachCaseIds() {
	        return attachCaseIds;
	    }

	    public void setAttachCaseIds(String attachCaseIds) {
	        this.attachCaseIds = attachCaseIds;
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
	    @Column(name = "petition_code")
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


	    public Integer getApproveId() {
	        return approveId;
	    }

	    public void setApproveId(Integer approveId) {
	        this.approveId = approveId;
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

/*	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        FdCase fdCase = (FdCase) o;

	        if (id != fdCase.id) return false;
	        if (state != fdCase.state) return false;
	        if (petitionNames != null ? !petitionNames.equals(fdCase.petitionNames) : fdCase.petitionNames != null)
	            return false;
	        if (petitionNumbers != null ? !petitionNumbers.equals(fdCase.petitionNumbers) : fdCase.petitionNumbers != null)
	            return false;
	        if (petitionTime != null ? !petitionTime.equals(fdCase.petitionTime) : fdCase.petitionTime != null)
	            return false;
	        if (petitionCounty != null ? !petitionCounty.equals(fdCase.petitionCounty) : fdCase.petitionCounty != null)
	            return false;
	        if (petitionUnit != null ? !petitionUnit.equals(fdCase.petitionUnit) : fdCase.petitionUnit != null)
	            return false;
	        if (petitionWay != null ? !petitionWay.equals(fdCase.petitionWay) : fdCase.petitionWay != null) return false;
	        if (isRepeatPetition != null ? !isRepeatPetition.equals(fdCase.isRepeatPetition) : fdCase.isRepeatPetition != null)
	            return false;
	        if (caseTime != null ? !caseTime.equals(fdCase.caseTime) : fdCase.caseTime != null) return false;
	        if (caseAddress != null ? !caseAddress.equals(fdCase.caseAddress) : fdCase.caseAddress != null) return false;
	        if (caseDesc != null ? !caseDesc.equals(fdCase.caseDesc) : fdCase.caseDesc != null) return false;
	        if (caseDemand != null ? !caseDemand.equals(fdCase.caseDemand) : fdCase.caseDemand != null) return false;
	        if (fileSrc != null ? !fileSrc.equals(fdCase.fileSrc) : fdCase.fileSrc != null) return false;
	        if (handleDuration != null ? !handleDuration.equals(fdCase.handleDuration) : fdCase.handleDuration != null)
	            return false;
	        if (handlePeriodStart != null ? !handlePeriodStart.equals(fdCase.handlePeriodStart) : fdCase.handlePeriodStart != null)
	            return false;
	        if (handlePeriodEnd != null ? !handlePeriodEnd.equals(fdCase.handlePeriodEnd) : fdCase.handlePeriodEnd != null)
	            return false;
	        if (questionBelongingTo != null ? !questionBelongingTo.equals(fdCase.questionBelongingTo) : fdCase.questionBelongingTo != null)
	            return false;
	        if (questionType != null ? !questionType.equals(fdCase.questionType) : fdCase.questionType != null)
	            return false;
	        if (caseType != null ? !caseType.equals(fdCase.caseType) : fdCase.caseType != null) return false;
	        if (questionHot != null ? !questionHot.equals(fdCase.questionHot) : fdCase.questionHot != null) return false;
	        if (belongToSys != null ? !belongToSys.equals(fdCase.belongToSys) : fdCase.belongToSys != null) return false;
	        if (thirteenCategories != null ? !thirteenCategories.equals(fdCase.thirteenCategories) : fdCase.thirteenCategories != null)
	            return false;
	        if (petitionWhy != null ? !petitionWhy.equals(fdCase.petitionWhy) : fdCase.petitionWhy != null) return false;
	        if (petitionPurpose != null ? !petitionPurpose.equals(fdCase.petitionPurpose) : fdCase.petitionPurpose != null)
	            return false;
	        if (isLeaderLimited != null ? !isLeaderLimited.equals(fdCase.isLeaderLimited) : fdCase.isLeaderLimited != null)
	            return false;
	        if (exposeObject != null ? !exposeObject.equals(fdCase.exposeObject) : fdCase.exposeObject != null)
	            return false;
	        if (petitionNature != null ? !petitionNature.equals(fdCase.petitionNature) : fdCase.petitionNature != null)
	            return false;
	        if (trusteName != null ? !trusteName.equals(fdCase.trusteName) : fdCase.trusteName != null) return false;
	        if (isAbove != null ? !isAbove.equals(fdCase.isAbove) : fdCase.isAbove != null) return false;
	        if (isBacklog != null ? !isBacklog.equals(fdCase.isBacklog) : fdCase.isBacklog != null) return false;
	        if (isReview != null ? !isReview.equals(fdCase.isReview) : fdCase.isReview != null) return false;
	        if (isSatisfied != null ? !isSatisfied.equals(fdCase.isSatisfied) : fdCase.isSatisfied != null) return false;
	        //if (isAnonymity != null ? !isAnonymity.equals(fdCase.isAnonymity) : fdCase.isAnonymity != null) return false;
	        if (isProminent != null ? !isProminent.equals(fdCase.isProminent) : fdCase.isProminent != null) return false;
	        if (isAcrossFrom != null ? !isAcrossFrom.equals(fdCase.isAcrossFrom) : fdCase.isAcrossFrom != null)
	            return false;
	        if (isLeaderPackageCase != null ? !isLeaderPackageCase.equals(fdCase.isLeaderPackageCase) : fdCase.isLeaderPackageCase != null)
	            return false;
	        //if (isUrgent != null ? !isUrgent.equals(fdCase.isUrgent) : fdCase.isUrgent != null) return false;
	        if (isTypeRight != null ? !isTypeRight.equals(fdCase.isTypeRight) : fdCase.isTypeRight != null) return false;
	        if (isScaleRight != null ? !isScaleRight.equals(fdCase.isScaleRight) : fdCase.isScaleRight != null)
	            return false;
	        if (isTierRight != null ? !isTierRight.equals(fdCase.isTierRight) : fdCase.isTierRight != null) return false;
	        if (gmtCreate != null ? !gmtCreate.equals(fdCase.gmtCreate) : fdCase.gmtCreate != null) return false;
	        if (gmtModified != null ? !gmtModified.equals(fdCase.gmtModified) : fdCase.gmtModified != null) return false;
	        if (type != null ? !type.equals(fdCase.type) : fdCase.type != null) return false;
	        if (creatorId != null ? !creatorId.equals(fdCase.creatorId) : fdCase.creatorId != null) return false;
	        if (caseHandleState != null ? !caseHandleState.equals(fdCase.caseHandleState) : fdCase.caseHandleState != null)
	            return false;
	        if (windowNumber != null ? !windowNumber.equals(fdCase.windowNumber) : fdCase.windowNumber != null)
	            return false;
	        if (createTime != null ? !createTime.equals(fdCase.createTime) : fdCase.createTime != null) return false;
	        if (guestId != null ? !guestId.equals(fdCase.guestId) : fdCase.guestId != null) return false;
	        if (followGuestIds != null ? !followGuestIds.equals(fdCase.followGuestIds) : fdCase.followGuestIds != null)
	            return false;
	        if (attachCaseIds != null ? !attachCaseIds.equals(fdCase.attachCaseIds) : fdCase.attachCaseIds != null)
	            return false;
	        if (attachCaseDesc != null ? !attachCaseDesc.equals(fdCase.attachCaseDesc) : fdCase.attachCaseDesc != null)
	            return false;
	        if (form != null ? !form.equals(fdCase.form) : fdCase.form != null) return false;
	        if (title != null ? !title.equals(fdCase.title) : fdCase.title != null) return false;
	        if (petitionCode != null ? !petitionCode.equals(fdCase.petitionCode) : fdCase.petitionCode != null)
	            return false;

	        return true;
	    }

	    @Override
	    public int hashCode() {
	        int result = id;
	        result = 31 * result + (petitionNames != null ? petitionNames.hashCode() : 0);
	        result = 31 * result + (petitionNumbers != null ? petitionNumbers.hashCode() : 0);
	        result = 31 * result + (petitionTime != null ? petitionTime.hashCode() : 0);
	        result = 31 * result + (petitionCounty != null ? petitionCounty.hashCode() : 0);
	        result = 31 * result + (petitionUnit != null ? petitionUnit.hashCode() : 0);
	        result = 31 * result + (petitionWay != null ? petitionWay.hashCode() : 0);
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
//	        result = 31 * result + (isAnonymity != null ? isAnonymity.hashCode() : 0);
	        result = 31 * result + (isProminent != null ? isProminent.hashCode() : 0);
	        result = 31 * result + (isAcrossFrom != null ? isAcrossFrom.hashCode() : 0);
	        result = 31 * result + (isLeaderPackageCase != null ? isLeaderPackageCase.hashCode() : 0);
//	        result = 31 * result + (isUrgent != null ? isUrgent.hashCode() : 0);
	        result = 31 * result + (isTypeRight != null ? isTypeRight.hashCode() : 0);
	        result = 31 * result + (isScaleRight != null ? isScaleRight.hashCode() : 0);
	        result = 31 * result + (isTierRight != null ? isTierRight.hashCode() : 0);
	        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
	        result = 31 * result + (gmtModified != null ? gmtModified.hashCode() : 0);
	        result = 31 * result + (type != null ? type.hashCode() : 0);
	        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
	        result = 31 * result + (caseHandleState != null ? caseHandleState.hashCode() : 0);
	        result = 31 * result + (windowNumber != null ? windowNumber.hashCode() : 0);
	        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
	        result = 31 * result + (guestId != null ? guestId.hashCode() : 0);
	        result = 31 * result + (followGuestIds != null ? followGuestIds.hashCode() : 0);
	        result = 31 * result + (attachCaseIds != null ? attachCaseIds.hashCode() : 0);
	        result = 31 * result + (attachCaseDesc != null ? attachCaseDesc.hashCode() : 0);
	        result = 31 * result + (form != null ? form.hashCode() : 0);
	        result = 31 * result + state;
	        result = 31 * result + (title != null ? title.hashCode() : 0);
	        result = 31 * result + (petitionCode != null ? petitionCode.hashCode() : 0);
	        return result;
	    }*/
	


}
