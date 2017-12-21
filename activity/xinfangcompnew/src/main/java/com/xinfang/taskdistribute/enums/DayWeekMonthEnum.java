package com.xinfang.taskdistribute.enums;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-06 16:34
 **/
public enum DayWeekMonthEnum {

    day(1, "本日"), week(2, "本周"), month(3, "本月"); //

    private int index;
    private String name;


    // 构造方法
    private DayWeekMonthEnum(int index, String name) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (DayWeekMonthEnum c : DayWeekMonthEnum.values()) {
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
