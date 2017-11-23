package cn.jkm.uis.facade.entities;
/**
 * 专家文章点赞
 */
public class ExpertPostPoint {
	private String id;
	private String userId;//点赞用户id
	private long createTime;//点赞时间
	private String expertPostId;//专家文章id
	private int status;//1 正常，2 删除
}
