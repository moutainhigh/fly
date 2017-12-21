package com.xinfang.enums;

public enum HandleState {

	ASSIGNED(1, "交办"), TRUN(2, "转办"), ALLOW(3, "受理"), REPLY(4, "直接回复"), REFUSE(5, "不予受理 "), REFUSEL(6, "不再受理 "), SAVE(7,
			"直接存档"), RETURN(8, "退回"), FINISH(9, "申请办结"), DELAY(10, "申请延期"), APPRY(2001, "审批通过"), APPRYN(2002,
					"审批不通过"), FORD(2003, "收文岗派发"),TRANS(2004, "转办"),ADD_SING(2005, "加签"), SHUNT(100, "分流室"), WINDOW(101,
							"窗口"), UPDATE_CASE(102, "案件更新"), LIANXI(1001, "联系上访人"), DIAOCHA(1002, "调查取证"), GOUTONG(1003,
									"沟通协调"), YANQI(1004, "延期申请"), ANJIAN(1005, "案件退回"), BANJIE(1006,
											"案件办结"), TODO_AUDIT(11, "待审"), TODO_HANDLE(12, "待处理"), TODO_FINISH(13,
													"待办结信访件"), TURN_PETITION(14, "转为信访件"), TURN_DISHED(15, "转为指挥调度"),
	TURN_RETUEN(21, "窗口退回分流室"), TURN_RETUEN_NO(22, "窗口退回分流室审核不通过"), URN_RETUEN_OK(23, "窗口退回分流室审核通过");

	// ASSIGNED(1, "交办"), TRUN(2, "转办"), ALLOW(3, "受理"), REPLY(4, "直接回复"),
	// REFUSE(5, "不予受理 "),
	// REFUSEL(6, "不再受理 "), SAVE(7, "直接存档"), RETURN(8, "退回"), FINISH(9, "申请办结"),
	// DELAY(10, "申请延期"),
	// APPRY(11, "审批通过"), APPRYN(12, "审批不通过"), FORD(13, "收文岗派发"), LIANXI(14,
	// "联系上访人"), DIAOCHA(15, "调查取证"),
	// GOUTONG(16, "沟通协调"), TURN_PETITION(17, "转为信访件"), TURN_DISHED(18,
	// "转为指挥调度"),
	//
	// SHUNT(100, "分流室添加"), WINDOW(101, "窗口添加"), UPDATE_CASE(103, "更新案件信息");

	private int value;
	private String name;

	HandleState(int value, String name) {
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
		for (HandleState c : HandleState.values()) {
			if (c.getValue() == index) {
				return c.name;
			}
		}
		return null;
	}
}
