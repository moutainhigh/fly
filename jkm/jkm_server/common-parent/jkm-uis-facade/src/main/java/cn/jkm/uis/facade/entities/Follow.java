package cn.jkm.uis.facade.entities;

/**
 * 关注
 */
public class Follow {

	private String id;
	private String userId;//关注人
	private String consernUserId;//被关注人
	private long createTime;//关注时间
	private int status;//状态，1：正常，2：取消
}
