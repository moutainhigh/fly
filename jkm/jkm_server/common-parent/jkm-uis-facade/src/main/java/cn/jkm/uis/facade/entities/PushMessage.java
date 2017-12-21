package cn.jkm.uis.facade.entities;
/*
 * 推送消息
 */
public class PushMessage {
	private String id;
	private String title;//主题
	private String content;//内容
	private long createTime;//发送时间
	private String userIds;//发送用户
	
	private int type;//主类别
	private int subType;//子类别
}
