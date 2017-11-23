package com.xinfang.enums;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-01 10:23
 **/
public enum TzmEnum {

    A(1, "市委"),
    B(2, "市人民政府"),
    C(3, "市信访局"),
    D(4, "市级职能部门"),
    E(5, "区委"),
    F(6, "区政府"),
    G(7, "区信访局"),
    H(8, "区级职能部门"),
    I(9, "乡镇社区");

    /** 机构类型
     (1, '市委'),
     (2, '市人民政府'),
     (3, '市信访局'),
     (4, '市级职能部门'),
     (5, '区委'),
     (6, '区政府'),
     (7, '区信访局'),
     (8, '区级职能部门'),
     (9, '乡镇社区'),
     **/
    private int value;
    private String name;

    TzmEnum(int value, String name) {
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
        for (TzmEnum c : TzmEnum.values()) {
            if (c.getValue() == index) {
                return c.name;
            }
        }
        return null;
    }
}
