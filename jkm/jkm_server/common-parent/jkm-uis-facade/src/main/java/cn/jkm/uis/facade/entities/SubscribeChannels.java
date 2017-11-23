package cn.jkm.uis.facade.entities;

/**
 * 订阅频道和栏目
 */
public class SubscribeChannels {
    private String userId;//订阅人
    private String channels;//订阅频道集合
    private String columns;//订阅栏目集合
    private long lastUpdateTime;//最近修改时间
    private int status;//1.正常  2.删除
}
