package com.xinfang.enums;

/**
 * 
 * @description 流程控制
 * @author ZhangHuaRong   
 * @update 2017年4月19日 上午9:46:32
 */
public enum HandleLimitState {

	register(1, "信访登记到转出阶段 "), forword(2, "区信访或职能部门转交阶段"),reply(3,"职能部门出具受理告知书或者其他答复阶段"),replyLetter(4,"责任单位出具意见答复书阶段  "),areaApproval(5,"区县审批 "),cityApproval(6,"市级审批  ")
    ;
    
	
    private int value;
    private String name;

    HandleLimitState(int value, String name) {
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
        for (HandleLimitState c : HandleLimitState.values()) {  
            if (c.getValue() == index) {  
                return c.name;  
            }  
        }  
        return null;  
    } 
}
