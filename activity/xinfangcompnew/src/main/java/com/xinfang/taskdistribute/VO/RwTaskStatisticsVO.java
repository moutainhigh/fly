package com.xinfang.taskdistribute.VO;

import io.swagger.annotations.ApiModelProperty;
import java.util.Map;

/**
 * @author zemal-tan
 * @description 任务情况统计页面
 * @create 2017-06-07 9:26
 **/
public class RwTaskStatisticsVO {

    @ApiModelProperty(value = "发送任务总数目", required = false)
    private Integer taskSendCount;
//    @ApiModelProperty(value = "收到回复数目", required = false)
//    private Integer taskReplyCount;
    @ApiModelProperty(value = "收到任务总数目", required = false) //
    private Integer taskReceiveCount;
    @ApiModelProperty(value = "收到的并且未查看的任务数目,即未处理的任务", required = false)
    private Integer taskUncheckedCount;
    @ApiModelProperty(value = "进行中任务数目", required = false)
    private Integer taskDoingCount;
    @ApiModelProperty(value = "已完成任务数目", required = false)
    private Integer taskCompleteCount;

    //============== 任务类型和任务状况 =======================
    @ApiModelProperty(value = "'1任务'类型数目", required = false)
    private Map<String, Integer> taskCountMap;
    @ApiModelProperty(value ="'2通知'类型数目", required = false)
    private Map<String, Integer> notificationCountMap;
    @ApiModelProperty(value = "'3公告'类型数目", required = false)
    private Map<String, Integer> announcementCountMap;
    @ApiModelProperty(value = "'4共享'类型数目", required = false)
    private Map<String, Integer> shareCountMap;
    @ApiModelProperty(value = "'5其他'类型数目", required = false)
    private Map<String, Integer> othersCountMap;

    @ApiModelProperty(value = "接收任务完成度，精确到了小数点后4位", required = false)
    private float taskComplteDegree;

    @ApiModelProperty(value = "剩余任务数", required = false)
    private Integer taskRemainCount;

    public RwTaskStatisticsVO() {
    }

    public RwTaskStatisticsVO(Integer taskSendCount, Integer taskReceiveCount, Integer taskUncheckedCount, Integer taskDoingCount, Integer taskCompleteCount) {
        this.taskSendCount = taskSendCount;
//        this.taskReplyCount = taskReplyCount;     , Integer taskReplyCount
        this.taskReceiveCount = taskReceiveCount;
        this.taskUncheckedCount = taskUncheckedCount;
        this.taskDoingCount = taskDoingCount;
        this.taskCompleteCount = taskCompleteCount;
    }

    public Integer getTaskSendCount() {
        return taskSendCount;
    }

    public void setTaskSendCount(Integer taskSendCount) {
        this.taskSendCount = taskSendCount;
    }

//    public Integer getTaskReplyCount() {
//        return taskReplyCount;
//    }
//
//    public void setTaskReplyCount(Integer taskReplyCount) {
//        this.taskReplyCount = taskReplyCount;
//    }

    public Integer getTaskReceiveCount() {
        return taskReceiveCount;
    }

    public void setTaskReceiveCount(Integer taskReceiveCount) {
        this.taskReceiveCount = taskReceiveCount;
    }

    public Integer getTaskUncheckedCount() {
        return taskUncheckedCount;
    }

    public void setTaskUncheckedCount(Integer taskUncheckedCount) {
        this.taskUncheckedCount = taskUncheckedCount;
    }

    public Integer getTaskDoingCount() {
        return taskDoingCount;
    }

    public void setTaskDoingCount(Integer taskDoingCount) {
        this.taskDoingCount = taskDoingCount;
    }

    public Map<String, Integer> getTaskCountMap() {
        return taskCountMap;
    }

    public void setTaskCountMap(Map<String, Integer> taskCountMap) {
        this.taskCountMap = taskCountMap;
    }

    public Map<String, Integer> getNotificationCountMap() {
        return notificationCountMap;
    }

    public void setNotificationCountMap(Map<String, Integer> notificationCountMap) {
        this.notificationCountMap = notificationCountMap;
    }

    public Map<String, Integer> getAnnouncementCountMap() {
        return announcementCountMap;
    }

    public void setAnnouncementCountMap(Map<String, Integer> announcementCountMap) {
        this.announcementCountMap = announcementCountMap;
    }

    public Map<String, Integer> getShareCountMap() {
        return shareCountMap;
    }

    public void setShareCountMap(Map<String, Integer> shareCountMap) {
        this.shareCountMap = shareCountMap;
    }

    public Map<String, Integer> getOthersCountMap() {
        return othersCountMap;
    }

    public void setOthersCountMap(Map<String, Integer> othersCountMap) {
        this.othersCountMap = othersCountMap;
    }

    public Integer getTaskCompleteCount() {
        return taskCompleteCount;
    }

    public void setTaskCompleteCount(Integer taskCompleteCount) {
        this.taskCompleteCount = taskCompleteCount;
    }

    public float getTaskComplteDegree() {
        return taskComplteDegree;
    }

    public void setTaskComplteDegree(float taskComplteDegree) {
        this.taskComplteDegree = taskComplteDegree;
    }

    public Integer getTaskRemainCount() {
        return taskRemainCount;
    }

    public void setTaskRemainCount(Integer taskRemainCount) {
        this.taskRemainCount = taskRemainCount;
    }
}
