package cn.jkm.uis.facade.entities;
/*
 * 短信消息
 */
public class SmsMessage {
	private String id;
	private String title;//主题
	private String content;//内容
	private long createTime;//发送时间
	private String userIds;//发送用户
	
	private String tels;//发送电话号码
	
	
	private int type;//主类别
	private int subType;//子类别
}
