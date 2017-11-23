package com.xinfang.app.VO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComplaintVO {
	
	private String case_id;//编号
	
	private String complaint_location;//地点
	
	private Date complaint_time;//案件发生时间
	
	//@JsonFormat
	private Date created_time;//案件提交时间
	
	
	private String title;
	
	private String complaint_reason;
	
	private String[] complaint_condition;
	
	private String status;
	
	private String is_order;//是否预约
	
	private String[] evaluationB;//部门的评价
	
	private String[] evaluationG;//个人评价
	
	private String case_method; 
	
	private String files;
	
	private String caseCode;
	
	
	private String[] dfyjs;//答复意见书           type=9 file2
	
	private String[] bjbg;//办结报告 type=9 file1
	
	private String[] slgzs;//受理告知书 type=3
	
	private String[] dismiss;//不予受理告知书 type=5
	
	
	
	
	
	

	public String[] getDfyjs() {
		if(dfyjs==null){
			dfyjs = new String[]{};
		}
		return dfyjs;
	}

	public String[] getBjbg() {
		if(bjbg==null){
			bjbg = new String[]{};
		}
		return bjbg;
	}

	public String[] getSlgzs() {
		if(slgzs==null){
			slgzs = new String[]{};
		}
		return slgzs;
	}

	public String[] getDismiss() {
		if(dismiss==null){
			dismiss = new String[]{};
		}
		return dismiss;
	}
	
	

	public void setDfyjs(String[] dfyjs) {
		this.dfyjs = dfyjs;
	}

	public void setBjbg(String[] bjbg) {
		this.bjbg = bjbg;
	}

	public void setSlgzs(String[] slgzs) {
		this.slgzs = slgzs;
	}

	public void setDismiss(String[] dismiss) {
		this.dismiss = dismiss;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCase_method() {
		return case_method;
	}

	public void setCase_method(String case_method) {
		this.case_method = case_method;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getIs_order() {
		//return is_order;
		return "否";
	}

	public void setIs_order(String is_order) {
		this.is_order = is_order;
	}

	public String[] getEvaluationB() {
		if(evaluationB==null){
			evaluationB = new String[]{};
		}
		return evaluationB;
	}

	public void setEvaluationB(String[] evaluationB) {
		this.evaluationB = evaluationB;
	}

	

	public String[] getEvaluationG() {
		if(evaluationG==null){
			evaluationG = new String[]{};
		}
		return evaluationG;
	}

	public void setEvaluationG(String[] evaluationG) {
		this.evaluationG = evaluationG;
	}

	public String[] getComplaint_condition() {
		if(complaint_condition==null){
			complaint_condition = new String[]{};
		}
		return complaint_condition;
	}

	public void setComplaint_condition(String[] complaint_condition) {
		this.complaint_condition = complaint_condition;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCase_id() {
		return case_id;
	}

	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}
  
	public String getComplaint_location() {
		return complaint_location;
	}

	public void setComplaint_location(String complaint_location) {
		this.complaint_location = complaint_location;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8") 
	public Date getComplaint_time() {
		return complaint_time;
	}

	public void setComplaint_time(Date complaint_time) {
		this.complaint_time = complaint_time;
	}
	

	public String getTitle() {
		return title;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8") 
	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComplaint_reason() {
		return complaint_reason;
	}

	public void setComplaint_reason(String complaint_reason) {
		this.complaint_reason = complaint_reason;
	}
	
	
	

}
