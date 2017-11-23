package com.xinfang.evaluating.service;

import java.util.List;
import java.util.Map;

/**
 * Created by sunbjx on 2017/5/16.
 */
public interface ScoreService {
	/**
	 * 获取评分详情（区分层级）
	 * @param assessUnitId
	 * @return
	 */
	Map<String, Object> getScoreList(Integer assessUnitId,Integer assessId);
	/**
	 * 获取考核详情
	 * @param assessId
	 * @return
	 */
	Map<String, Object> getAssessDetail(Integer assessId);
	/**
	 * 获取评分列表
	 * @param assessId
	 * @param unitId
	 * @param unitType
	 * @return
	 */
	Map<String, Object> scoreList(Integer assessId,Integer unitId,Integer unitType);
	/**
	 * 获取评分列表（不区分层级）
	 * @param assessId
	 * @param unitId
	 * @return
	 */
	Map<String, Object> scoreList2(Integer assessId,Integer unitId);
	/**
	 * 修改评分参数
	 * @return
	 */
	Map<String, Object> updateScore(List<Map> listMap);
	/**
	 * 通过考核积分ID获取详情
	 */
	Map<String, Object> getScoreDetail(Integer scoreId);
	/**
	 * 通过ID获取说明评价编辑详情
	 */
	Map<String, Object> getScoreBetailById(Integer scoreId);
	/**
	 * 通过考核ID修改文件名及描述
	 * @param fileName
	 * @param desc
	 * @param scoreId
	 * @return
	 */
	Map<String,Object> updateFileNameAndDesc(String  fileName,String desc,Integer scoreId);
	
}
