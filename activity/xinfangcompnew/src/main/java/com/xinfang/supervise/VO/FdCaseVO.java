package com.xinfang.supervise.VO;

import java.util.Date;

public class FdCaseVO {
	
	private Date createTime;
	
	private Integer caseId;
	
	private String guestName;
	
	private String guestIdCard;
	
	private String compreason;//上访原因
	
	private String accinentDepName;
	
	private String accindetReason;//督责原因
	
	private String area;
	
	private String note;
	
	

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestIdCard() {
		return guestIdCard;
	}

	public void setGuestIdCard(String guestIdCard) {
		this.guestIdCard = guestIdCard;
	}

	public String getCompreason() {
		return compreason;
	}

	public void setCompreason(String compreason) {
		this.compreason = compreason;
	}

	public String getAccinentDepName() {
		return accinentDepName;
	}

	public void setAccinentDepName(String accinentDepName) {
		this.accinentDepName = accinentDepName;
	}

	public String getAccindetReason() {
		return accindetReason;
	}

	public void setAccindetReason(String accindetReason) {
		this.accindetReason = accindetReason;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	

}
