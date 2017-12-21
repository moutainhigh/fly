package com.xinfang.VO;

import java.util.Date;

/**
 * Created by sunbjx on 2017/3/30.
 */
public class HomeListVO {

	// 信访编号
	private String petitionCode;
	// 信访人
	private String petitionName;
	// 信访人头像
	private String petitionHeadSrc;
	// 信访方式
	private String petitionType;
	// 案件归属地
	private String caseBelongToAddress;
	// 事件描述
	private String caseDesc;
	// 当前处理单位
	private String currentHandleUnit;
	// 当前案件处理人
	private String currentHandleName;
	// 处理人头像
	private String handleUserHeadSrc;
	// 登记时间
	private Date petitionTime;
	// 限办时间
	private String handlePeriodEnd;
	// 渠道
	private String channel;
	// 当前状态
	private String currentState;
	// 是否满意度件
	private String isSatisfied;
	// 状态
	private String state;
	// 流程ID
	private String flowId;
	// 流程任务ID
	private String taskId;
	// 信访ID
	private Integer guestId;
	// 案件ID
	private Integer caseId;
	// 创建时间
	private String gmtCreate;
	// 窗口退回分流室案件审核状态
	private String turnAuditState;
	// =====================四库数据新增显示字段===============
	private String createUnit;
	private Integer createUnitId;
	// 民族
	private String ethnic;
	// 户籍地
	private String Hjd;
	// 性别
	private String sex;
	// 热点问题
	private String QuestionHot;
	// 现居住地
	private String jzd;
	// 人员电话
	private String ryPhone;
	// 身份证
	private String petitionIdCard;
	// 随访人姓名
	private String sfName;
	// 随访人ID
	private String sfidCard;
	// 信访层级
	private String xfcj;
	// 三跨三离
	private Integer sksl;
	// 是否越级
	private Integer yj;
	// 历史上访时间
	private String historyTime;
	// 历史上访情况
	private String historyDesc;
	// 职业
	private String work;
	// 家庭情况·
	private String family;
	// 是否重访
	private Integer isRepation;
	// 信访人数
	private String petitionnumbers;
	// 所属系统·
	private String belongTosys;
	// 信访人电话
	private String petitionPhone;
	// 信访目的
	private String petitionpurpose;
	// 信访人头像
	private String petitionImg;
	// 初访时间
	private String firstTime;
	// 信访积案
	private Integer backlog;
	// 信访性质
	private String petitionNature;
	// 主要诉求
	private String desc;
	// 包案领导
	private String leader;
	// 化解情况
	private String hjqk;
	// 稳控情况
	private String wkqk;

	public String getHjqk() {
		return hjqk;
	}

	public void setHjqk(String hjqk) {
		this.hjqk = hjqk;
	}

	public String getWkqk() {
		return wkqk;
	}

	public void setWkqk(String wkqk) {
		this.wkqk = wkqk;
	}

	public Integer getSksl() {
		return sksl;
	}

	public void setSksl(Integer sksl) {
		this.sksl = sksl;
	}

	public Integer getYj() {
		return yj;
	}

	public void setYj(Integer yj) {
		this.yj = yj;
	}

	public Integer getIsRepation() {
		return isRepation;
	}

	public void setIsRepation(Integer isRepation) {
		this.isRepation = isRepation;
	}

	public Integer getBacklog() {
		return backlog;
	}

	public void setBacklog(Integer backlog) {
		this.backlog = backlog;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getPetitionNature() {
		return petitionNature;
	}

	public void setPetitionNature(String petitionNature) {
		this.petitionNature = petitionNature;
	}

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	public String getPetitionImg() {
		return petitionImg;
	}

	public void setPetitionImg(String petitionImg) {
		this.petitionImg = petitionImg;
	}

	public String getPetitionpurpose() {
		return petitionpurpose;
	}

	public void setPetitionpurpose(String petitionpurpose) {
		this.petitionpurpose = petitionpurpose;
	}

	public String getPetitionPhone() {
		return petitionPhone;
	}

	public void setPetitionPhone(String petitionPhone) {
		this.petitionPhone = petitionPhone;
	}

	public String getPetitionnumbers() {
		return petitionnumbers;
	}

	public String getBelongTosys() {
		return belongTosys;
	}

	public void setBelongTosys(String belongTosys) {
		this.belongTosys = belongTosys;
	}

	public void setPetitionnumbers(String petitionnumbers) {
		this.petitionnumbers = petitionnumbers;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getQuestionHot() {
		return QuestionHot;
	}

	public void setQuestionHot(String questionHot) {
		QuestionHot = questionHot;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSfName() {
		return sfName;
	}

	public void setSfName(String sfName) {
		this.sfName = sfName;
	}

	public String getSfidCard() {
		return sfidCard;
	}

	public void setSfidCard(String sfidCard) {
		this.sfidCard = sfidCard;
	}

	public String getXfcj() {
		return xfcj;
	}

	public void setXfcj(String xfcj) {
		this.xfcj = xfcj;
	}

	public String getHistoryTime() {
		return historyTime;
	}

	public void setHistoryTime(String historyTime) {
		this.historyTime = historyTime;
	}

	public String getHistoryDesc() {
		return historyDesc;
	}

	public void setHistoryDesc(String historyDesc) {
		this.historyDesc = historyDesc;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getHjd() {
		return Hjd;
	}

	public void setHjd(String hjd) {
		Hjd = hjd;
	}

	public String getJzd() {
		return jzd;
	}

	public void setJzd(String jzd) {
		this.jzd = jzd;
	}

	public String getRyPhone() {
		return ryPhone;
	}

	public void setRyPhone(String ryPhone) {
		this.ryPhone = ryPhone;
	}

	public String getPetitionIdCard() {
		return petitionIdCard;
	}

	public void setPetitionIdCard(String petitionIdCard) {
		this.petitionIdCard = petitionIdCard;
	}

	public String getCreateUnit() {
		return createUnit;
	}

	public void setCreateUnit(String createUnit) {
		this.createUnit = createUnit;
	}

	public Integer getCreateUnitId() {
		return createUnitId;
	}

	public void setCreateUnitId(Integer createUnitId) {
		this.createUnitId = createUnitId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getPetitionCode() {
		return petitionCode;
	}

	public void setPetitionCode(String petitionCode) {
		this.petitionCode = petitionCode;
	}

	public String getPetitionName() {
		return petitionName;
	}

	public void setPetitionName(String petitionName) {
		this.petitionName = petitionName;
	}

	public String getPetitionHeadSrc() {
		return petitionHeadSrc;
	}

	public void setPetitionHeadSrc(String petitionHeadSrc) {
		this.petitionHeadSrc = petitionHeadSrc;
	}

	public String getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(String petitionType) {
		this.petitionType = petitionType;
	}

	public String getCaseBelongToAddress() {
		return caseBelongToAddress;
	}

	public void setCaseBelongToAddress(String caseBelongToAddress) {
		this.caseBelongToAddress = caseBelongToAddress;
	}

	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}

	public String getCurrentHandleUnit() {
		return currentHandleUnit;
	}

	public void setCurrentHandleUnit(String currentHandleUnit) {
		this.currentHandleUnit = currentHandleUnit;
	}

	public String getCurrentHandleName() {
		return currentHandleName;
	}

	public void setCurrentHandleName(String currentHandleName) {
		this.currentHandleName = currentHandleName;
	}

	public String getHandleUserHeadSrc() {
		return handleUserHeadSrc;
	}

	public void setHandleUserHeadSrc(String handleUserHeadSrc) {
		this.handleUserHeadSrc = handleUserHeadSrc;
	}

	public Date getPetitionTime() {
		return petitionTime;
	}

	public void setPetitionTime(Date petitionTime) {
		this.petitionTime = petitionTime;
	}

	public String getHandlePeriodEnd() {
		return handlePeriodEnd;
	}

	public void setHandlePeriodEnd(String handlePeriodEnd) {
		this.handlePeriodEnd = handlePeriodEnd;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getIsSatisfied() {
		return isSatisfied;
	}

	public void setIsSatisfied(String isSatisfied) {
		this.isSatisfied = isSatisfied;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getTurnAuditState() {
		return turnAuditState;
	}

	public void setTurnAuditState(String turnAuditState) {
		this.turnAuditState = turnAuditState;
	}

}
