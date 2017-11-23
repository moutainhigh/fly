package com.xinfang.meetingmodule.VO;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-17 19:55
 **/
public class FhMeetingUrgeVO {

    @ApiModelProperty(value = "会议id", required = true)
    private Integer meetingId;

    @ApiModelProperty(value = "参会人员表id，即列表中的id字段", required = true)
    private Integer personId;

    @ApiModelProperty(value = "参会人id")
    @ApiParam(value = "参会人id")
    private Integer meetingPersonId;

    @ApiModelProperty(value = "参会人姓名")
    private String meetingPersonName;

    @ApiModelProperty(value = "部门id")
    @ApiParam(value = "部门id")
    private Integer meetingDepartmentId;

    @ApiModelProperty(value = "部门名称")
    private String meetingDepartmentName;

    @ApiModelProperty(value = "职位id")
    @ApiParam(value = "职位id")
    private Integer meetingUnitId;

    @ApiModelProperty(value = "职位名称")
    private String meetingUnitName;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "催到人id,即当前发送提醒的人员id")
    private Integer urgePersonId;

    @ApiModelProperty(value = "催到人名称,即当前发送提醒的人员名称")
    private String urgePersonName;

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getMeetingPersonId() {
        return meetingPersonId;
    }

    public void setMeetingPersonId(Integer meetingPersonId) {
        this.meetingPersonId = meetingPersonId;
    }

    public String getMeetingPersonName() {
        return meetingPersonName;
    }

    public void setMeetingPersonName(String meetingPersonName) {
        this.meetingPersonName = meetingPersonName;
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

    public Integer getMeetingUnitId() {
        return meetingUnitId;
    }

    public void setMeetingUnitId(Integer meetingUnitId) {
        this.meetingUnitId = meetingUnitId;
    }

    public String getMeetingUnitName() {
        return meetingUnitName;
    }

    public void setMeetingUnitName(String meetingUnitName) {
        this.meetingUnitName = meetingUnitName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUrgePersonId() {
        return urgePersonId;
    }

    public void setUrgePersonId(Integer urgePersonId) {
        this.urgePersonId = urgePersonId;
    }

    public String getUrgePersonName() {
        return urgePersonName;
    }

    public void setUrgePersonName(String urgePersonName) {
        this.urgePersonName = urgePersonName;
    }
}
