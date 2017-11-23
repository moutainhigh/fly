package cn.jkm.uis.facade.entities;

/**
 * 闪屏
 */
public class SplashScreen {

	private String id;
	private String userId;//发布人
	private long createTime;//创建时间
	private long beginTime;//开始时间（生效时间）
	private long endTime;//结束时间（失效时间）
	private String images;//图片组（使用“,”分隔符）
	private long showTime;//显示时长
	private long effectTime;//特效时长
	private String effectType;//特效,0：无；1：左滑；2：右滑；3：渐变
	private int status;

}
