package com.xinfang.taskdistribute.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-02 11:01
 **/
public class SearchList {

    @ApiModelProperty(value = "登录人id", required = true)
    private Integer ryId;

    @ApiModelProperty(value = "任务状态，‘任务’状态包括 0未操作（未处理）、1接收、2转发、3退回、4完成（已处理），而‘非任务’只有0和4")
    private Integer isFlag;

    @ApiModelProperty(value = "任务类型id，1任务、2通知、3公告、4共享、5其他")
    private Integer taskTypeId;

    @ApiModelProperty(value = "任务级别id，1普通、2重要、3特别重要")
    private Integer taskLevelId;

    @ApiModelProperty(value = "关键字搜索")
    private String keyword;

    @ApiModelProperty(value = "任务分类，1我派发的任务、2我接受的任务、3我转发的任务、4未处理的任务、5进行中的任务、6历史汇总、7任务汇总")
    private Integer taskCategory;

    @ApiModelProperty(value = "时间筛选，开始时间 yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date startTime;

    @ApiModelProperty(value = "时间筛选，结束时间 yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date endTime;

    @ApiModelProperty(value="开始页数，默认为0，即第一页")
    @ApiParam(value="开始页数，默认为0，即第一页", defaultValue = "0")
    private Integer startPage;

    @ApiModelProperty(value="每页条目数，默认为10")
    @ApiParam(value="每页条目数，默认为10", defaultValue = "10")
    private Integer pageSize;

    public Integer getRyId() {
        return ryId;
    }

    public void setRyId(Integer ryId) {
        this.ryId = ryId;
    }

    public Integer getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(Integer isFlag) {
        this.isFlag = isFlag;
    }

    public Integer getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(Integer taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public Integer getTaskLevelId() {
        return taskLevelId;
    }

    public void setTaskLevelId(Integer taskLevelId) {
        this.taskLevelId = taskLevelId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(Integer taskCategory) {
        this.taskCategory = taskCategory;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
