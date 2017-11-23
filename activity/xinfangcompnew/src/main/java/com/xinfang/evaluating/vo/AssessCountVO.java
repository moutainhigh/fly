package com.xinfang.evaluating.vo;

import net.sf.json.JSONArray;

public class AssessCountVO {
	
	//父ID
	private Integer pId;
	//ID
	private Integer id;
	//考核项目名称(小项)
	private String name;
	//实际得分
	private Float relatityScore;
	//子集
	private JSONArray children=new JSONArray();
	public JSONArray getChildren() {
		return children;
	}
	public void setChildren(JSONArray children) {
		this.children = children;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getRelatityScore() {
		return relatityScore;
	}
	public void setRelatityScore(Float relatityScore) {
		this.relatityScore = relatityScore;
	}
	
}
