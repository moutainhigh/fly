package cn.jkm.uis.facade.entities;

import java.math.BigDecimal;

/**
 * 活动
 */
public class Activity {
	
	private String id;
	private String title;//标题
	private String content;//内容
	private long beginTime;//活动开始时间
    private long endTime;//活动结束时间
    private long createTime;//发表时间
    private long lastUpdateTime;//最后修改时间
    private String sponsor;//主办方名称
    private String address;//报名地点
    private long lat;//经度
    private long lon;//纬度
    private int minNum;//最少报名人数
    private int maxNum;//最多报名人数
    private BigDecimal money;//报名费
    private int sex;//性别,1:男；2：女；
    private int age;//年龄
    private int status;//状态，1:显示；2:隐藏；
    private long topTime;//置顶时间
    private String remark;//备注
}
