package com.xinfang.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 法律法规
 * 
 * @author sunbjx Date: 2017年5月25日上午10:02:58
 */
@Entity
@Table(name = "stork_xf_siku_law_info")
public class LawRulesEntity {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "law_id")
	private Integer law_id;

	private String category;

	private String name;

	private String content;

	private String problem;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date time;
	@Column(name = "send_apartment")
	private String send_apartment;
	@Column(name = "receive_apartment")
	private String receive_apartment;

	private Integer counttoview;

	private String attechment;

	private Integer xh;

	@Column(name = "category_id")
	private Integer category_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLaw_id() {
		return law_id;
	}

	public void setLaw_id(Integer law_id) {
		this.law_id = law_id;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getSend_apartment() {
		return send_apartment;
	}

	public void setSend_apartment(String send_apartment) {
		this.send_apartment = send_apartment;
	}

	public String getReceive_apartment() {
		return receive_apartment;
	}

	public void setReceive_apartment(String receive_apartment) {
		this.receive_apartment = receive_apartment;
	}

	public Integer getCounttoview() {
		return counttoview;
	}

	public void setCounttoview(Integer counttoview) {
		this.counttoview = counttoview;
	}

	public String getAttechment() {
		return attechment;
	}

	public void setAttechment(String attechment) {
		this.attechment = attechment;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

}
