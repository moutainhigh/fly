package com.xinfang.meetingmodule.enums;

/**
 * @author zemal-tan
 * @description  参会人员
 * @create 2017-05-11 14:47
 **/
public enum MeetingJionPerson {

    auto(11, "选择机构人员添加"), manaul(12,"手动录入"),  // 添加类型编号及名称
    unconfirmed(20, "未确认"), confirmed(21, "已确认"), absent(22, "无法参加"),
    unsignin(30, "未签到"), singin(31, "已签到"),// 人员参会状态;

    capital(2001, "重大资金"),personnel(2002, "重要的人事任免"), project(2003, "重大项目"); // 会议性质

    // 成员变量

    private int index;
    private String name;


    // 构造方法
    private MeetingJionPerson(int index, String name) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (MeetingJionPerson c : MeetingJionPerson.values()) {
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
