package cn.jkm.uis.facade.entities;

/**
 * 收藏
 */
public class Store {
	private String id;
	private int type;// 1.帖子资讯 2.专家文章
	private long createTime;// 收藏时间
	private String contentId;// 帖子资讯 ,专家文章id
	private String userId;// 收藏的用户id
	private int status;// 1.正常 2.删除
}
