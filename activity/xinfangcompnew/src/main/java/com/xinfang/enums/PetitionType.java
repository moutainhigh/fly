package com.xinfang.enums;

/**
 * 上访人类型
 * Created by sunbjx on 2017/3/22.
 */
public enum PetitionType {

    ALONE(1, "个访"), GROUP(2, "群体访");

    private int value;
    private String name;

    PetitionType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
