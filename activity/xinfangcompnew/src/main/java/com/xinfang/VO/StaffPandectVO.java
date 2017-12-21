package com.xinfang.VO;

import net.sf.json.JSONArray;




public class StaffPandectVO {
	
	//人员头像
	private String img;
	//人员名称
	private String username;
	//科室
	private String department;
	//职务
	private String post;
	//listVO集合
	private JSONArray children =new JSONArray();
	//职务ID
	private Integer zId;
	//父ID
	private Integer pId;
	public Integer getzId() {
		return zId;
	}
	public void setzId(Integer zId) {
		this.zId = zId;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public JSONArray getChildren() {
		return children;
	}
	public void setChildren(JSONArray children) {
		this.children = children;
	}
	

	

	
}
