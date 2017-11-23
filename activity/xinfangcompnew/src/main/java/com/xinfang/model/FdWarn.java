package com.xinfang.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "fd_warn")
public class FdWarn {
	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer caseId;
	
	private Date createTime;
	
	private String sender;
	
	private Integer receiver;
	
	private String msg ;
	
	private Integer level;
	
	private Integer createDep;
	
	private Integer receiveDep;
	
	

	public Integer getCreateDep() {
		return createDep;
	}

	public void setCreateDep(Integer createDep) {
		this.createDep = createDep;
	}

	public Integer getReceiveDep() {
		return receiveDep;
	}

	public void setReceiveDep(Integer receiveDep) {
		this.receiveDep = receiveDep;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Integer getReceiver() {
		return receiver;
	}

	public void setReceiver(Integer receiver) {
		this.receiver = receiver;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
}
