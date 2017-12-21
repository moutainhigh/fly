package com.xinfang.meetingmodule.VO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-17 11:53
 **/
public class FhContinueRemindVO {

    @ApiModelProperty(value = "落实情况id", required = true)
    private Integer continueId;

    @ApiModelProperty(value = "部门id")
    private Integer meetingDepartmentId;

    @ApiModelProperty(value = "部门名称")
    private String meetingDepartmentName;

    @ApiModelProperty(value = "提醒操作人id", required = true)
    private Integer remindPersonId;

    @ApiModelProperty(value = "提醒操作人名称")
    private String remindPersonName;

    public Integer getContinueId() {
        return continueId;
    }

    public void setContinueId(Integer continueId) {
        this.continueId = continueId;
    }

    public Integer getMeetingDepartmentId() {
        return meetingDepartmentId;
    }

    public void setMeetingDepartmentId(Integer meetingDepartmentId) {
        this.meetingDepartmentId = meetingDepartmentId;
    }

    public String getMeetingDepartmentName() {
        return meetingDepartmentName;
    }

    public void setMeetingDepartmentName(String meetingDepartmentName) {
        this.meetingDepartmentName = meetingDepartmentName;
    }

    public Integer getRemindPersonId() {
        return remindPersonId;
    }

    public void setRemindPersonId(Integer remindPersonId) {
        this.remindPersonId = remindPersonId;
    }

    public String getRemindPersonName() {
        return remindPersonName;
    }

    public void setRemindPersonName(String remindPersonName) {
        this.remindPersonName = remindPersonName;
    }
}
