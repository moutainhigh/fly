package cn.jkm.core.domain.mongo.content;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.Gender;
import cn.jkm.framework.core.annotation.Document;

/**
 * Expert entity. @author zhengzb	2017/7/14
 */
@Document(name = "Expert")
public class Expert extends MongoBasic<Expert> {

	// Fields

	private String name;			//专家姓名
	private String avatar;			//专家头像
	private String position;		//专家职称
	private Gender sex;				//专家性别
	private Integer age;			//专家年龄
	private String prief;			//专家简介
	private String remark;			//专家备注
	private String addUserId;		//添加人
	private Integer postCount;		//专家发帖数
	private Integer point;			//专家点赞数
	private Integer store;        	//专家收藏数
	
	
	public Expert() {
		super();
	}


	public Expert(String name, String avatar, String position, Gender sex,
				  Integer age, String prief, String remark,
				  String addUserId) {
		super();
		this.name = name;
		this.avatar = avatar;
		this.position = position;
		this.sex = sex;
		this.age = age;
		this.prief = prief;
		this.remark = remark;
		this.addUserId = addUserId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public Gender getSex() {
		return sex;
	}


	public void setSex(Gender sex) {
		this.sex = sex;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	public String getPrief() {
		return prief;
	}


	public void setPrief(String prief) {
		this.prief = prief;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getAddUserId() {
		return addUserId;
	}


	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}

	public int getPostCount(int i) {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	@Override
	public String toString() {
		return "Expert{" +
				"name='" + name + '\'' +
				", avatar='" + avatar + '\'' +
				", position='" + position + '\'' +
				", sex=" + sex +
				", age=" + age +
				", prief='" + prief + '\'' +
				", remark='" + remark + '\'' +
				", addUserId='" + addUserId + '\'' +
				", postCount=" + postCount +
				", point=" + point +
				", store=" + store +
				'}';
	}
}