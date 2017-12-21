package com.xinfang.meetingmodule.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @author zemal-tan
 * @description  会议列表查询
 * @create 2017-05-11 17:25
 **/
public class SearchRequest {

    @ApiModelProperty(value = "人员id", required = true)
    private Integer ryId;

    @ApiModelProperty(value = "人员id类型", required = true)
    @ApiParam(value = "人员id类型,0表示所有人员，1表示接收部门人员，2表示发起会议人员，3表示部门提交人员， 4表示接收部门人员或发起会议人员", defaultValue = "0")
    private Integer ryIdType;

    @ApiModelProperty(value = "会议类型id")
    private Integer meetingTypeId;

    @ApiModelProperty(value = "关键字搜索")
    private String keyword;

    @ApiModelProperty(value = "当前会议为0、历史会议为1")
    private Integer nowOrHistory;

    @ApiModelProperty(value = "是否关联上访案件，0为否、1为是")
    private Integer isCase;

    @ApiModelProperty(value = "是否关联调度事件，0为否、1为是")
    private String isDispatch;

    @ApiModelProperty(value = "过滤开始时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    private Date startTime;

    @ApiModelProperty(value = "过滤结束时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    private Date endTime;

    @ApiParam(value = "开始页数，默认为0，即第一页", defaultValue = "0")
    @ApiModelProperty(value = "开始页数，默认为0，即第一页")
    private Integer startPage;

    @ApiParam(value = "每条页数，默认为10", defaultValue = "10")
    @ApiModelProperty(value = "每条页数，默认为10")
    private Integer pageSize;

    public Integer getRyId() {
        return ryId;
    }

    public void setRyId(Integer ryId) {
        this.ryId = ryId;
    }

    public Integer getRyIdType() {
        return ryIdType;
    }

    public void setRyIdType(Integer ryIdType) {
        this.ryIdType = ryIdType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getNowOrHistory() {
        return nowOrHistory;
    }

    public void setNowOrHistory(Integer nowOrHistory) {
        this.nowOrHistory = nowOrHistory;
    }

    public Integer getIsCase() {
        return isCase;
    }

    public void setIsCase(Integer isCase) {
        this.isCase = isCase;
    }

    public String getIsDispatch() {
        return isDispatch;
    }

    public void setIsDispatch(String isDispatch) {
        this.isDispatch = isDispatch;
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

    public Integer getMeetingTypeId() {
        return meetingTypeId;
    }

    public void setMeetingTypeId(Integer meetingTypeId) {
        this.meetingTypeId = meetingTypeId;
    }
}
