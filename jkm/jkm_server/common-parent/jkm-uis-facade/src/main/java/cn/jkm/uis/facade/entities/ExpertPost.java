package cn.jkm.uis.facade.entities;

/**
 * 专家文章
 */
public class ExpertPost {

	private String id;
	private String title;//标题
	private String content;//内容
	private int status;//状态，1:正常 2.删除
	private long createTime;//发布时间
	//private long commentNum;//评论数
	//private long browseNum;//浏览数
	//private long pointNum;//点赞数
	//private long collectionNum;//收藏数
	private String userId;//用户id（后台用户）
	private String expertId;//专家id
}
