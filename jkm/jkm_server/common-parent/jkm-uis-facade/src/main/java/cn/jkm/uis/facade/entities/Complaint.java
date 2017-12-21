package cn.jkm.uis.facade.entities;

/**
 * 投诉
 */
public class Complaint {

	private String id;
	private String content;//投诉内容
	private long createTime;//投诉时间
	private String userId;//投诉人
	private String handleUserId;//处理人
	private int type;//1:帖子或资讯 2.专家文章
	private String articleId;//关联帖子或资讯 ，专家讲堂id
	private String commentId;//对应的评论id
	private String replyPostUser;//回复发帖人内容（打回、删除）
	private String replyComplaintUser;//回复投诉人内容（打回、删除）
	private int status;//1:正常；2:忽略；3：打回；4：处理；5：删除

}
