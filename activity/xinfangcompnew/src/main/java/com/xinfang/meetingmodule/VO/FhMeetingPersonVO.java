package com.xinfang.meetingmodule.VO;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-15 1:15
 **/
public class FhMeetingPersonVO {

    @ApiModelProperty(value = "会议id", required = true)
    private Integer meetingId;

    @ApiModelProperty(value = "参会人id")
    @ApiParam(value = "参会人id",defaultValue = "-1")
    private Integer meetingPersonId;

    @ApiModelProperty(value = "参会人姓名", required = true)
    private String meetingPersonName;

    @ApiModelProperty(value = "部门id")
    @ApiParam(value = "部门id", defaultValue = "-1")
    private Integer meetingDepartmentId;

    @ApiModelProperty(value = "部门名称", required = true)
    private String meetingDepartmentName;

    @ApiModelProperty(value = "职位id")
    @ApiParam(value = "职位id", defaultValue = "-1")
    private Integer meetingUnitId;

    @ApiModelProperty(value = "职位名称", required = true)
    private String meetingUnitName;

    @ApiModelProperty(value = "电话号码", required = true)
    private String phone;

    @ApiModelProperty(value = "添加类型id（11为选择，12为手动）", required = true)
    private Byte personType;

    @ApiModelProperty(value = "提交人id", required = true)
    private Integer submitId;

    @ApiModelProperty(value = "提交人姓名", required = true)
    private String submitName;

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
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

    public Byte getPersonType() {
        return personType;
    }

    public void setPersonType(Byte personType) {
        this.personType = personType;
    }

    public Integer getSubmitId() {
        return submitId;
    }

    public void setSubmitId(Integer submitId) {
        this.submitId = submitId;
    }

    public String getSubmitName() {
        return submitName;
    }

    public void setSubmitName(String submitName) {
        this.submitName = submitName;
    }
}
