package com.xinfang.meetingmodule.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-16 20:27
 **/
public class FhMeetingRecordVO {

    @ApiModelProperty(value = "会议id", required = true)
    private int meetingId;

    @ApiModelProperty(value = "会议开始时间 yyyy-MM-dd HH:mm:ss")
    private Date meetingBeginDate;

    @ApiModelProperty(value = "会议记录时间 yyyy-MM-dd HH:mm:ss")
    private Date meetingEndDate;

    @ApiModelProperty(value = "会议记录内容")
    private String meetingRecord;

    @ApiModelProperty(value = "是否后续跟踪（0为否，1为是）")
    @ApiParam(value = "是否后续跟踪（0为否，1为是）,默认为0", defaultValue = "0")
    private Byte isContinue;

    @ApiModelProperty(value = "会议影像")
    private String meetingRecordImage;

    @ApiModelProperty(value = "会议过程文件")
    private String meetingRecordFile;

    @ApiModelProperty(value = "会议记录人提交人id")
    private String meetingRecordPersonId;

    @ApiModelProperty(value = "会议记录人提交人名称")
    private String meetingRecordPersonName;

    @ApiModelProperty(value = "部门id和name集合，选择该会议下的部门，eg: [{},{}].格式同G_meetingRecordDepartment接口下的result的value值")
    private String departmentString;  // 用于生成落实情况


    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getMeetingBeginDate() {
        return meetingBeginDate;
    }

    public void setMeetingBeginDate(Date meetingBeginDate) {
        this.meetingBeginDate = meetingBeginDate;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    public Date getMeetingEndDate() {
        return meetingEndDate;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public void setMeetingEndDate(Date meetingEndDate) {
        this.meetingEndDate = meetingEndDate;
    }

    public String getMeetingRecord() {
        return meetingRecord;
    }

    public void setMeetingRecord(String meetingRecord) {
        this.meetingRecord = meetingRecord;
    }

    public String getMeetingRecordImage() {
        return meetingRecordImage;
    }

    public void setMeetingRecordImage(String meetingRecordImage) {
        this.meetingRecordImage = meetingRecordImage;
    }

    public String getMeetingRecordFile() {
        return meetingRecordFile;
    }

    public void setMeetingRecordFile(String meetingRecordFile) {
        this.meetingRecordFile = meetingRecordFile;
    }

    public String getMeetingRecordPersonId() {
        return meetingRecordPersonId;
    }

    public void setMeetingRecordPersonId(String meetingRecordPersonId) {
        this.meetingRecordPersonId = meetingRecordPersonId;
    }

    public String getMeetingRecordPersonName() {
        return meetingRecordPersonName;
    }

    public void setMeetingRecordPersonName(String meetingRecordPersonName) {
        this.meetingRecordPersonName = meetingRecordPersonName;
    }

    public Byte getIsContinue() {
        return isContinue;
    }

    public void setIsContinue(Byte isContinue) {
        this.isContinue = isContinue;
    }

    public String getDepartmentString() {
        return departmentString;
    }

    public void setDepartmentString(String departmentString) {
        this.departmentString = departmentString;
    }
}
