package cn.jkm.uis.facade.entities;

/**
 * 热点
 */
public class Hotspot {

	private String id;
	private int type;//类型：1.贴子资讯  2.活动
	private String contentId;//关联对应类型id
	private long createTime;//创建时间
	private int orderNum;//排序
}
