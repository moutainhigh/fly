package com.xinfang.evaluating.vo;

import java.util.List;
import java.util.Map;

/**
 * 考核项目详情VO
 * Created by sunbjx on 2017/5/16.
 */
public class AssessVO {

    // 考核项目名称
    private String itemName;
    // 发起人姓名
    private String initiatorName;
    // 发起人头像
    private String initiatorHeadsrc;
    // 开始日期
    private String startTime;
    // 结束日期
    private String endTime;
    // 发起单位
    private String sponsorUnitName;
    // 考核目标
    private List<String> assessUnitName;
    // 考核单位总数量
    private Map<String, Object> assessUnitCount;
    // 考核分数
    private Map<String, Object> assessUnitScore;
    //统计
    private List<Map<String, Object>> assessCount;
    // 考核项目ID
    private Integer assessId;
    // 评分ID
    private Integer scoreId;
    //创建时间
    private String gmtCreat;
    //

    public List<Map<String, Object>> getAssessCount() {
		return assessCount;
	}

	public void setAssessCount(List<Map<String, Object>> assessCount) {
		this.assessCount = assessCount;
	}

	public String getGmtCreat() {
		return gmtCreat;
	}

	public void setGmtCreat(String gmtCreat) {
		this.gmtCreat = gmtCreat;
	}

	@Override
    public String toString() {
        return "AssessVO{" +
                "itemName='" + itemName + '\'' +
                ", initiatorName='" + initiatorName + '\'' +
                ", initiatorHeadsrc='" + initiatorHeadsrc + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", sponsorUnitName='" + sponsorUnitName + '\'' +
                ", assessUnitName='" + assessUnitName + '\'' +
                ", assessUnitCount=" + assessUnitCount +
                ", assessUnitScore=" + assessUnitScore +
                ", assessId=" + assessId +
                ", scoreId=" + scoreId +
                '}';
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    public String getInitiatorHeadsrc() {
        return initiatorHeadsrc;
    }

    public void setInitiatorHeadsrc(String initiatorHeadsrc) {
        this.initiatorHeadsrc = initiatorHeadsrc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSponsorUnitName() {
        return sponsorUnitName;
    }

    public void setSponsorUnitName(String sponsorUnitName) {
        this.sponsorUnitName = sponsorUnitName;
    }

    public List<String> getAssessUnitName() {
        return assessUnitName;
    }

    public void setAssessUnitName(List<String> assessUnitName) {
        this.assessUnitName = assessUnitName;
    }

    public Map<String, Object> getAssessUnitCount() {
        return assessUnitCount;
    }

    public void setAssessUnitCount(Map<String, Object> assessUnitCount) {
        this.assessUnitCount = assessUnitCount;
    }

    public Map<String, Object> getAssessUnitScore() {
        return assessUnitScore;
    }

    public void setAssessUnitScore(Map<String, Object> assessUnitScore) {
        this.assessUnitScore = assessUnitScore;
    }

    public Integer getAssessId() {
        return assessId;
    }

    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
    }

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }
}
