package cn.jkm.uis.facade.entities;

/**
 * 配方
 */
public class Formula {
	
	private String id;
	private String title;//标题
	private String content;//内容
	private String symptom;//适应症状
	private String userId;//发布人id
	private String expertId;//专家id
	private long createTime;//创建时间
	private String productInfo;//商品信息
	private int status;//状态，1:正常；2:删除
}
