package com.xinfang.taskdistribute.enums;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-07 10:01
 **/
public enum TaskLevelId {

    common(1, "普通"), important(2, "重要"), veryimportant(3, "非常重要"); // 任务类型

    private int index;
    private String name;

    // 构造方法
    private TaskLevelId(int index, String name) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (TaskLevelId c : TaskLevelId.values()) {
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
