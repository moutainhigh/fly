package com.xinfang.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @title FdSuggest.java
 * @package com.xinfang.app.model
 * @description 建议
 * @author ZhangHuaRong   
 * @update 2017年5月16日 下午2:37:17
 * @version V1.0  
 * Copyright (c)2012 chantsoft-版权所有
 */
@Entity
public class FdSuggest {
	@Id
	 @GeneratedValue
   private Integer id;
	@Column(name="case_id") 
	private String case_id;
	@Column(name="suggest_text") 
	private String suggest_text;
	@JSONField(format=  "yyyy-MM-dd HH:mm:ss" ) 
	 @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="input_time") 
	private Date input_time;
	@Column(name="guest_id")   
	private Integer sf_person_id;
	@Column(name="state") 
	private Integer suggest_status;
	
	@Column(name="case_xf_des") 
	private Integer case_xf_des;
	
	@Column(name="reply_person_id") 
	private Integer reply_person_id;
	
	@Column(name="rely_text") 
	private String reply_text;
	@JSONField(format=  "yyyy-MM-dd HH:mm:ss" ) 
	  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="rely_time") 
	private Date reply_time;
	
	@Column(name="reply_org_id")
	private Integer reply_org_id;
	@Column(name="case_method")
	private String case_method;
	
	private String title;
	
	@Column(name="type") 
	private Integer type;
	
	@Column(name="is_open")
	private Integer is_open;
	
	@Transient
	private String case_xf_des_name;
	
	
	
	public String getCase_xf_des_name() {
		return case_xf_des_name;
	}
	public void setCase_xf_des_name(String case_xf_des_name) {
		this.case_xf_des_name = case_xf_des_name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCase_id() {
		return case_id;
	}
	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}
	public String getSuggest_text() {
		return suggest_text;
	}
	public void setSuggest_text(String suggest_text) {
		this.suggest_text = suggest_text;
	}
	@JsonFormat(pattern =  "yyyy-MM-dd HH:mm:ss" ) 
	public Date getInput_time() {
		return input_time;
	}
	public void setInput_time(Date input_time) {
		this.input_time = input_time;
	}
	public Integer getSf_person_id() {
		return sf_person_id;
	}
	public void setSf_person_id(Integer sf_person_id) {
		this.sf_person_id = sf_person_id;
	}
	public Integer getSuggest_status() {
		return suggest_status;
	}
	public void setSuggest_status(Integer suggest_status) {
		this.suggest_status = suggest_status;
	}
	public Integer getCase_xf_des() {
		return case_xf_des;
	}
	public void setCase_xf_des(Integer case_xf_des) {
		this.case_xf_des = case_xf_des;
	}
	public Integer getReply_person_id() {
		return reply_person_id;
	}
	public void setReply_person_id(Integer reply_person_id) {
		this.reply_person_id = reply_person_id;
	}
	
	public String getReply_text() {
		return reply_text;
	}
	public void setReply_text(String reply_text) {
		this.reply_text = reply_text;
	}
	@JsonFormat(pattern =  "yyyy-MM-dd HH:mm:ss" ) 
	public Date getReply_time() {
		return reply_time;
	}
	public void setReply_time(Date reply_time) {
		this.reply_time = reply_time;
	}
	public Integer getReply_org_id() {
		return reply_org_id;
	}
	public void setReply_org_id(Integer reply_org_id) {
		this.reply_org_id = reply_org_id;
	}
	public String getCase_method() {
		return case_method;
	}
	public void setCase_method(String case_method) {
		this.case_method = case_method;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIs_open() {
		return is_open;
	}
	public void setIs_open(Integer is_open) {
		this.is_open = is_open;
	}

	
	
	
	

}
