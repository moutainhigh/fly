package com.xinfang.evaluating.service;

import com.xinfang.context.PageFinder;
import com.xinfang.evaluating.model.FpAssessEntity;
import com.xinfang.evaluating.vo.AssessVO;

import java.util.List;
import java.util.Map;

/**
 * 投诉办理-考核业务层接口
 * Created by sunbjx on 2017/5/16.
 */
public interface AssessService {

    /**
     * 新增考核项目接口
     * @param assess
     * @return
     */
    void assessSave(FpAssessEntity assess, List<Map> unitTypeA, List<Map> unitTypeB);

    /**
     * 更新考核项目接口
     * @param assess
     * @return
     */
    Map<String, Object> assessUpdate(Integer assessId, FpAssessEntity assess,List<Map> unitTypeA, List<Map> unitTypeB);

    /**
     * 通过考核ID逻辑删除考核数据
     * @param assessId
     * @return
     */
    void remove(Integer assessId);

    /**
     * 考核项目详情
     * @param assessId
     * @return
     */
    FpAssessEntity assessDetail(Integer assessId);


    /**
     * 考核列表
     * @param startTime
     * @param endTime
     * @param petitionUnitId
     * @return
     */
    PageFinder<AssessVO> assessList(Integer signInUserId, String startTime, String endTime, Integer petitionUnitId, int startPage, int pageSize);

    /**
     * 考核统计
     * @param unitType
     * @param unitId
     * @param itemType
     * @return
     */
    Map<String, Object> assessStatistics(Integer assessId, Integer unitType, Integer unitId, String itemType);

    /**
     * 考核报表
     * @param assessId
     * @return
     */
    Map<String, Object> assessReport(Integer assessId);
    /**
     * 考核报表2（优化）
     * @param aeesssId
     * @return
     */
    Map<String, Object> assessReport2(Integer aeesssId);
   /**
    * 根据考核ID获取考核单位
    * @param assessId
    * @param type
    * @return
    */
    Map<String, Object> getAssessUnit(Integer assessId,Integer type);
    
    /**
     * 获取考核列表（正在使用的）
     * @param signInUserId
     * @param startTime
     * @param endTime
     * @param petitionUnitId
     * @param startPage
     * @param pageSize
     * @param fuzzy
     * @return
     */
    Map<String, Object> getAssessList(Integer signInUserId, String startTime, String endTime, Integer petitionUnitId, Integer startPage, Integer pageSize,String fuzzy);
    /**
     * 获取考核A类单位列表
     * @return
     */
    Map<String, Object> getAunitList();



}
