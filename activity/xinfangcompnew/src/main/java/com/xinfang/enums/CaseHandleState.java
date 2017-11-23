package com.xinfang.enums;

/**
 * 案件总的处理状态
 * Created by sunbjx on 2017/4/7.
 */
public enum CaseHandleState {

    HANDLE_INIT(2004, "待录入"), HANDLE_TODO(2001, "待处理"), HANDLE_IN(2002,"处理中"), HANDLE_END(2003, "已完结");

    private int value;
    private String name;

    CaseHandleState(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
    public static String getName(int index) {
        for (CaseHandleState c : CaseHandleState.values()) {
            if (c.getValue() == index) {
                return c.name;
            }
        }
        return null;
    }
}
