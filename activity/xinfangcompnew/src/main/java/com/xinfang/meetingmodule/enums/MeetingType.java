package com.xinfang.meetingmodule.enums;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-10 11:57
 **/
public enum MeetingType {

    DZH(1, "党组会"), BGH(2, "办公会"), DDH(3, "调度会"), XTH(4, "协调会"),
    GBZGDH(5, "干部职工大会"), SPH(6, "视频会"), PXH(7, "培训会"), QT(8, "其他"); // 会议类型
    // 成员变量

    private int index;
    private String name;


    // 构造方法
    private MeetingType(int index, String name) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (MeetingType c : MeetingType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
