package com.xinfang.taskdistribute.enums;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-10 11:57
 **/
public enum TaskIsFlag {

    undo(0, "未处理"), reveive(1, "接收"), transfer(2, "转发"), sendback(3, "退回"), did(4, "已完结"); // 任务状态
    // 成员变量

    private int index;
    private String name;


    // 构造方法
    private TaskIsFlag(int index, String name) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (TaskIsFlag c : TaskIsFlag.values()) {
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
