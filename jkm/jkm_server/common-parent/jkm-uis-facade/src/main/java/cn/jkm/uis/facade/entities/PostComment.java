package cn.jkm.uis.facade.entities;

/**
 * 帖子和资讯评论
 */
public class PostComment {
	
	private String id;
	private String createTime;//评论时间
	private String content;//内容
	private String postId;//帖子或者资讯id
	private String parentId;//父id
	private String userId;//用户id，评论人
	private int status;//1:正常；2:删除
}
