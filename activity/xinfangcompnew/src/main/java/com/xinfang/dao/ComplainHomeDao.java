package com.xinfang.dao;

import java.util.List;
import java.util.Map;

/**
 * 投诉办理首页
 * Created by sunbjx on 2017/5/4.
 */
public interface ComplainHomeDao {

    /**
     *
     * @param flag  1 待办  2 正办  3 已办  4 收文岗  5 已退回  6 汇总
     * @param userId    登录人ID
     * @param caseState     案件状态
     * @param handleState   处理状态
     * @param timeProgress  时间监控
     * @param fuzzy         模糊搜索
     * @param petitionType  信访方式
     * @param caseBelongTo  案件归属地
     * @param dept          参与部门
     * @param startPage     起始页
     * @param pageSize      每页显示数量
     * @return
     */
    List<Map<String, Object>> packages(int flag, Integer userId, int caseState, int handleState, int timeProgress, String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept, int startPage, int pageSize);

    /**
     * 收文岗列表query
     * @param userId
     * @param caseState
     * @param handleState
     * @param timeProgress
     * @param fuzzy
     * @param petitionType
     * @param caseBelongTo
     * @param dept
     * @param startPage
     * @param pageSize
     * @return
     */
    Map<String, Object> dispatcher(Integer userId, int caseState, int handleState, int timeProgress, String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept, int startPage, int pageSize);
    
    /**
     * 首页案件状态查询
     * @param userId
     * @param caseState
     * @param handleState
     * @param timeProgress
     * @param fuzzy
     * @param petitionType
     * @param caseBelongTo
     * @param dept
     * @return
     */
    List<Map<String, Object>> caseStateQuery(Integer userId, int caseState, int handleState, int timeProgress,
			String fuzzy, Integer petitionType, Integer caseBelongTo, Integer dept);
}


