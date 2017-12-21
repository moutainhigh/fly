package com.xinfang.context;

/**
 * 数据库状态常量
 * Created by sunbjx on 2017/3/23.
 */
public interface StateConstants {

    /*---- flow 流程----*/
    int FLOW_TODO = 1000;       // 待办
    int FLOW_IS_TODO = 2000;    // 正办
    int FLOW_HAS_TODO = 3000;   // 已办

    int FLOW_TYPE_TURN = 6001;  // 转办
    int FLOW_TYPE_HANDLE = 6002;  // 交办


    /*---- handle 处理----*/
    int HANDLE_TODO = 2001;     // 待处理
    int HANDLE_IS_TODO = 2002;  // 已处理
    int HANDLE_HAS = 2003;      // 已办结
    int HANDLE_BACK = 2004;     // 待传回

    /*---- monitor 监控----*/
    int MONITOR_PRE = 5001;       // 预警
    int MONITOR_WARNING = 5002;   // 警告
    int MONITOR_SERIOUS = 5003;   // 严重警告
    int MONITOR_SUPER = 5004;     // 超期


    /*---- state 数据库常用CRUD ----*/
    int STATE_REMOVW = -1;      // 删除
    int STATE_DEFAULT = 0;      // 默认
    int STATE_UPDATE = 1;       // 更新

    /*---- form 窗口or分流室 ----*/
    Byte FORM_WINDOW = 5;   // 窗口
    Byte FORM_SHUNT = 6;   // 分流室
    Byte FORM_NOT = -1; //两者都不是

    /*---- 首页汇总信访件 状态过滤 ----*/
    int FLAG_OVERDUE_TODO_FINISH = 1;  // 逾期未结
    int FLAG_OVERDUE_HAS_FINISH = 2;    // 逾期办结

    int FLAG_MORE_THAN_HALF = 3;    // 期限过半
    int FLAG_ON_TIME_FINISH = 4;    // 按期办结

    int FLAG_TODO_HANDLE = 5;   // 未办理
    int FLAG_IS_TODO_HANDLE = 6;    // 办理中

    int FLAG_FLOW_COMPLAINT = 7;    // 转办件
    int FLAG_FLOW_ASSIGN = 8;   // 交办件
    int FLAG_FLOW_PENDING = 9;  // 待审核
    int FLAG_FLOW_TODO_HANDLE = 10;     // 待处理
    int FLAG_FLOW_TODO_FINISH = 11;     // 待办结
    int FLAG_FLOW_NO_ACCEPT = 12;   // 不予受理
    int FLAG_FLOW_NO_LONGER = 13;   // 不再受理
    int FLAG_FLOW_DIRECT_REPLY = 14;    // 直接回复
    int FLAG_FLOW_FEEDBACK = 15;    // 退回
    int FLAG_FLOW_SAVE = 16;    // 存件
}
