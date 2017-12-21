package cn.jkm.uis.facade.entities;

/**
 * 帖子或者资讯
 */
public class Post {

	private String id;
	private int type;//1.帖子 2.资讯
	private String title;//标题
	private String content;//内容
	private String formulaInfo;//内容中包含的配方信息
	private String productInfo;//内容中包含的商品信息
	private int status;//状态，1:待提交；2:待审核；3:显示；4:隐藏；5:删除；
	private long createTime;//发布时间
	private long lastUpdateTime;//最后修改时间
	private long topTime;//置顶时间
	//private long commentNum;//评论数
	//private long browseNum;//浏览数
	//private long pointNum;//点赞数
	//private long storeNum;//收藏数
	private String channelId;//频道id
	private String columnId;//栏目id
	private String userId;//用户id（帖子是app用户，资讯是后台用户）
	private String auditor;//审核人id
}
