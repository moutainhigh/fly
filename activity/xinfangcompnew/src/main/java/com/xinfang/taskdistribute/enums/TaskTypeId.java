package com.xinfang.taskdistribute.enums;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-06 15:16
 **/
public enum TaskTypeId {

    task(1, "任务"), notification(2, "通知"), announcement(3, "公告"), share(4, "共享"), others(5, "其它"); // 任务类型

    private int index;
    private String name;


    // 构造方法
    private TaskTypeId(int index, String name) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (TaskTypeId c : TaskTypeId.values()) {
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
