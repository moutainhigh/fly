package cn.jkm.core.domain.mongo.content;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.Gender;
import cn.jkm.framework.core.annotation.Document;

/**
 * ActivityEnrollUser entity. @author zhengzb 2017/7/15
 * 报名用户表
 */
@Document(name = "activity_enroll_user")
public class ActivityEnrollUser extends  MongoBasic<ActivityEnrollUser>{

	// Fields
	private String activityId;			//活动id
//	private String enrollName;			//姓名
//	private String enrollTel;			//电话
//	private Integer enrollAge;			//年龄
//	@Enumerated(EnumType.STRING)
//	private Gender enrollSex;			//性别
//	private String enrollAddress;		//住址
//	private String enrollCompanyName;	//公司
//	private Long enrollTime;			//报名时间
//	private String enrollRemark;		//备注
	private String itemData;			//报名数据{"姓名"："张三"，"地址"："四川成都"}
	private String enrollRemark;		//备注
	public ActivityEnrollUser() {
	}


	@Column(name = "activity_id", length = 64)
	public String getActivityId() {
		return this.activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getItemData() {
		return itemData;
	}

	public void setItemData(String itemData) {
		this.itemData = itemData;
	}

	public String getEnrollRemark() {
		return enrollRemark;
	}
	public void setEnrollRemark(String enrollRemark) {
		this.enrollRemark = enrollRemark;
	}

	@Override
	public String toString() {
		return "ActivityEnrollUser{" +
				"activityId='" + activityId + '\'' +
				", itemData='" + itemData + '\'' +
				'}';
	}


}