package com.xinfang.VO;

import java.util.Date;



/**
 * 
 * @author zhangbo
 * 
 */
public class FdCaseShowVO {
	
	private Integer id;

	private String petitionNames;

	private Integer petitionNumbers;

	private int associatedNumbers;

	private Date petitionTime;
	
	//信访区县
	private String petitionCounty;

	private String petitionUnit;
	
	//信访方式
	private String petitionWay;

	private Byte isRepeatPetition;

	private Date caseTime;

	private String caseAddress;

	private String caseDesc;

	private String caseDemand;

	private String fileSrc;

	private Integer handleDuration;

	private Date handlePeriodStart;

	private Date handlePeriodEnd;
	
	//问题归属地
	
	private String questionBelongingTo;
	
	//内容分类
	private String questionType;
	
	//案件分类
	private String caseType;
	
	//热点问题
	private String questionHot;
	
	//所属系统
	
	private String belongToSys;
	
	//13大类
	private String thirteenCategories;
	
	//信访原因
	private String petitionWhy;
	
	//信访目的
	private String petitionPurpose;

	private Byte isLeaderLimited;
	
	//揭发控告对象
	private String exposeObject;
	
	//信访性质
	private String petitionNature;

	private String trusteName;

	private Byte isAbove;

	private Byte isBacklog;

	private Byte isReview;

	private Byte isSatisfied;

	private Byte isProminent;

	private Byte isAcrossFrom;

	private Byte isLeaderPackageCase;

	private Byte isLeaderApprove;

	private Integer approveId;

	private Byte isTypeRight;

	private Byte isScaleRight;

	private Byte isTierRight;

	private Date gmtCreate;

	private Date gmtModified;
	// 1 个访 2 群访

	private Integer type;

	private Integer creatorId;

	private Integer caseHandleState;

	private Integer windowNumber;

	private Date createTime;

	private Integer guestId;

	private String followGuestIds;

	private String attachCaseIds;

	private String attachCaseDesc;

	private Byte form;

	private int state;

	private String title;

	private String petitionCode;

	private Byte isSuitWade;

	private Byte isSuitTaiwan;

	private Byte isSuitAomen;

	private Byte isSuitHongkong;

	private Byte isSuitAbroad;

	private Byte isSuitOuter;

	private Byte isSuitThreaten;

	private Integer handleUserid;
	public FdCaseShowVO(Integer id, String petitionNames,
			Integer petitionNumbers, int associatedNumbers, Date petitionTime,
			String petitionCounty, String petitionUnit, String petitionWay,
			Byte isRepeatPetition, Date caseTime, String caseAddress,
			String caseDesc, String caseDemand, String fileSrc,
			Integer handleDuration, Date handlePeriodStart,
			Date handlePeriodEnd, String questionBelongingTo,
			String questionType, String caseType, String questionHot,
			String belongToSys, String thirteenCategories, String petitionWhy,
			String petitionPurpose, Byte isLeaderLimited, String exposeObject,
			String petitionNature, String trusteName, Byte isAbove,
			Byte isBacklog, Byte isReview, Byte isSatisfied, Byte isProminent,
			Byte isAcrossFrom, Byte isLeaderPackageCase, Byte isLeaderApprove,
			Integer approveId, Byte isTypeRight, Byte isScaleRight,
			Byte isTierRight, Date gmtCreate, Date gmtModified, Integer type,
			Integer creatorId, Integer caseHandleState, Integer windowNumber,
			Date createTime, Integer guestId, String followGuestIds,
			String attachCaseIds, String attachCaseDesc, Byte form, int state,
			String title, String petitionCode, Byte isSuitWade,
			Byte isSuitTaiwan, Byte isSuitAomen, Byte isSuitHongkong,
			Byte isSuitAbroad, Byte isSuitOuter, Byte isSuitThreaten,
			Integer handleUserid) {
		this.id = id;
		this.petitionNames = petitionNames;
		this.petitionNumbers = petitionNumbers;
		this.associatedNumbers = associatedNumbers;
		this.petitionTime = petitionTime;
		this.petitionCounty = petitionCounty;
		this.petitionUnit = petitionUnit;
		this.petitionWay = petitionWay;
		this.isRepeatPetition = isRepeatPetition;
		this.caseTime = caseTime;
		this.caseAddress = caseAddress;
		this.caseDesc = caseDesc;
		this.caseDemand = caseDemand;
		this.fileSrc = fileSrc;
		this.handleDuration = handleDuration;
		this.handlePeriodStart = handlePeriodStart;
		this.handlePeriodEnd = handlePeriodEnd;
		this.questionBelongingTo = questionBelongingTo;
		this.questionType = questionType;
		this.caseType = caseType;
		this.questionHot = questionHot;
		this.belongToSys = belongToSys;
		this.thirteenCategories = thirteenCategories;
		this.petitionWhy = petitionWhy;
		this.petitionPurpose = petitionPurpose;
		this.isLeaderLimited = isLeaderLimited;
		this.exposeObject = exposeObject;
		this.petitionNature = petitionNature;
		this.trusteName = trusteName;
		this.isAbove = isAbove;
		this.isBacklog = isBacklog;
		this.isReview = isReview;
		this.isSatisfied = isSatisfied;
		this.isProminent = isProminent;
		this.isAcrossFrom = isAcrossFrom;
		this.isLeaderPackageCase = isLeaderPackageCase;
		this.isLeaderApprove = isLeaderApprove;
		this.approveId = approveId;
		this.isTypeRight = isTypeRight;
		this.isScaleRight = isScaleRight;
		this.isTierRight = isTierRight;
		this.gmtCreate = gmtCreate;
		this.gmtModified = gmtModified;
		this.type = type;
		this.creatorId = creatorId;
		this.caseHandleState = caseHandleState;
		this.windowNumber = windowNumber;
		this.createTime = createTime;
		this.guestId = guestId;
		this.followGuestIds = followGuestIds;
		this.attachCaseIds = attachCaseIds;
		this.attachCaseDesc = attachCaseDesc;
		this.form = form;
		this.state = state;
		this.title = title;
		this.petitionCode = petitionCode;
		this.isSuitWade = isSuitWade;
		this.isSuitTaiwan = isSuitTaiwan;
		this.isSuitAomen = isSuitAomen;
		this.isSuitHongkong = isSuitHongkong;
		this.isSuitAbroad = isSuitAbroad;
		this.isSuitOuter = isSuitOuter;
		this.isSuitThreaten = isSuitThreaten;
		this.handleUserid = handleUserid;
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

	public String getPetitionCounty() {
		return petitionCounty;
	}

	public void setPetitionCounty(String petitionCounty) {
		this.petitionCounty = petitionCounty;
	}

	public String getPetitionUnit() {
		return petitionUnit;
	}

	public void setPetitionUnit(String petitionUnit) {
		this.petitionUnit = petitionUnit;
	}

	public String getPetitionWay() {
		return petitionWay;
	}

	public void setPetitionWay(String petitionWay) {
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

	public String getQuestionBelongingTo() {
		return questionBelongingTo;
	}

	public void setQuestionBelongingTo(String questionBelongingTo) {
		this.questionBelongingTo = questionBelongingTo;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getQuestionHot() {
		return questionHot;
	}

	public void setQuestionHot(String questionHot) {
		this.questionHot = questionHot;
	}

	public String getBelongToSys() {
		return belongToSys;
	}

	public void setBelongToSys(String belongToSys) {
		this.belongToSys = belongToSys;
	}

	public String getThirteenCategories() {
		return thirteenCategories;
	}

	public void setThirteenCategories(String thirteenCategories) {
		this.thirteenCategories = thirteenCategories;
	}

	public String getPetitionWhy() {
		return petitionWhy;
	}

	public void setPetitionWhy(String petitionWhy) {
		this.petitionWhy = petitionWhy;
	}

	public String getPetitionPurpose() {
		return petitionPurpose;
	}

	public void setPetitionPurpose(String petitionPurpose) {
		this.petitionPurpose = petitionPurpose;
	}

	public Byte getIsLeaderLimited() {
		return isLeaderLimited;
	}

	public void setIsLeaderLimited(Byte isLeaderLimited) {
		this.isLeaderLimited = isLeaderLimited;
	}

	public String getExposeObject() {
		return exposeObject;
	}

	public void setExposeObject(String exposeObject) {
		this.exposeObject = exposeObject;
	}

	public String getPetitionNature() {
		return petitionNature;
	}

	public void setPetitionNature(String petitionNature) {
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

	public Byte getForm() {
		return form;
	}

	public void setForm(Byte form) {
		this.form = form;
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
	

	
	  
}
