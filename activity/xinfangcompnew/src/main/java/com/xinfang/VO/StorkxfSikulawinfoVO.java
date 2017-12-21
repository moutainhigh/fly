package com.xinfang.VO;

import java.util.Date;



public class StorkxfSikulawinfoVO {
	//id
	private int id;
	//法规ID
	private  String lawid;
	//法规类别
	private String category;
	//法规名称
	private String name;
	//法规内容
	private String content;
	//针对问题
	private String problem;
	//下发时间
	private Date time;
	//发文机关
	private String sendApartment;
	//接受部门
	private String receiveApartment;
	//
	private int countofview;
	private String attachment;
	private int xh;
	private int categoryid;
	
	//时间
	private String strTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLawid() {
		return lawid;
	}

	public void setLawid(String lawid) {
		this.lawid = lawid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getSendApartment() {
		return sendApartment;
	}

	public void setSendApartment(String sendApartment) {
		this.sendApartment = sendApartment;
	}

	public String getReceiveApartment() {
		return receiveApartment;
	}

	public void setReceiveApartment(String receiveApartment) {
		this.receiveApartment = receiveApartment;
	}

	public int getCountofview() {
		return countofview;
	}

	public void setCountofview(int countofview) {
		this.countofview = countofview;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public int getXh() {
		return xh;
	}

	public void setXh(int xh) {
		this.xh = xh;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getStrTime() {
		return strTime;
	}

	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}

}
