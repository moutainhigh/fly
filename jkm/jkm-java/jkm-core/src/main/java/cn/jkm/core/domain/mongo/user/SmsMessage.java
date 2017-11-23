package cn.jkm.core.domain.mongo.user;

import cn.jkm.core.domain.mongo.MongoBasic;

/**
 * Created by Machine on 2017/7/17.
 * 短信消息
 */
public class SmsMessage extends MongoBasic<SmsMessage> {

	private String title;//主题
	private String content;//内容
	private String userIds;//发送用户
	private String phones;//发送电话号码
	private int type;//主类别
	private int subType;//子类别

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

}
