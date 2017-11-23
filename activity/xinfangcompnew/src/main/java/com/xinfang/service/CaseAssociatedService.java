package com.xinfang.service;

import com.xinfang.VO.HistoryCaseVO;
import com.xinfang.VO.LawRulesVO;

import java.util.List;
import java.util.Map;

/**
 * Created by sunbjx on 2017/3/27.
 */
public interface CaseAssociatedService {

    /**
     * 通过身份证获取历史关联案件信息
     * @param idcard
     * @return
     */
    List<HistoryCaseVO> listHistoryCaseByIdcard2(String idcard);

    /**
     * 通过用户名获取历史关联案件信息
     * @param username
     * @return
     */
    List<HistoryCaseVO> listHistoryCaseByUsername2(String username);
    
    /**
     * 通过身份证获取历史关联案件信息
     * @param idcard
     * @return
     */
    List<Map<String, Object>> listHistoryCaseByIdcard(String idcard);

    /**
     * 通过用户名获取历史关联案件信息
     * @param username
     * @return
     */
    List<Map<String, Object>> listHistoryCaseByUsername(String username);
    
    /**
     * 根据类别ID查询
     * @param categoryIds
     * @return
     */
    List<LawRulesVO> referenceLawRules(Integer[] categoryIds);
    
    /**
     * 根据法律法规ID获取法律信息
     * @param lawId
     * @return
     */
    String getByLawId(Integer lawId);
    
    /**
     * 案件关联
     * @param caseId
     */
    void relationCase(Integer caseId, String relationId);
    
    /**
     * 是否有关联案件
     * @param caseId
     */
    boolean isRelation(Integer caseId);
    
}
