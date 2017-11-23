package com.xinfang.evaluating.service;

import com.xinfang.context.PageFinder;
import com.xinfang.evaluating.vo.AppraiseDetailVO;
import com.xinfang.evaluating.vo.AppraiseVO;

import java.util.Map;

/**
 * Created by sunbjx on 2017/5/8.
 */
public interface AppraiseService {

    /**
     * 通过案件ID获取评价详情
     *
     * @param caseId
     * @return
     */
    AppraiseDetailVO getByCaseId(Integer caseId);

    /**
     * 当前登录人处理过的相关案件评价列表过滤
     *
     * @param signInUserUnitId
     * @param flag               1 信访部门评价 2 责任单位评价
     * @param petitionPurpose    信访目的
     * @param caseType           案件类别
     * @param caseBelongTo       问题属地
     * @param unitId             单位ID
     * @param petitionAppraiseOk 是否满意信访单位
     * @param dutyAppraiseOk     是否满意责任单位
     * @param caseAppraiseOk     是否满意案件处理
     * @param startTime          评价开始时间
     * @param endTime            评价结束时间
     * @return
     */
    PageFinder<AppraiseVO> listBySignInUser(Integer signInUserUnitId,
                                            int flag,
                                            Integer petitionPurpose,
                                            Integer caseType,
                                            Integer caseBelongTo,
                                            Integer unitId,
                                            Byte petitionAppraiseOk,
                                            Byte dutyAppraiseOk,
                                            Byte caseAppraiseOk,
                                            String startTime,
                                            String endTime,
                                            int startPage,
                                            int pageSize);

    /**
     * 群众满意度统计
     *
     * @param petitionPurpose 信访目的
     * @param caseType        案件类型
     * @param caseBelongTo    问题属地
     * @param unitId          单位ID
     * @param startTime       评价开始时间
     * @param endTime         评价结束时间
     * @return
     */
    Map<String, Object> okRateStatistical(Integer petitionPurpose,
                                          Integer caseType,
                                          Integer caseBelongTo,
                                          Integer unitId,
                                          String startTime,
                                          String endTime);

}
