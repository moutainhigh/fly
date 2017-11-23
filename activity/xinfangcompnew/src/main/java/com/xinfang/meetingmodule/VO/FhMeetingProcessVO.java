package com.xinfang.meetingmodule.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-16 20:42
 **/
public class FhMeetingProcessVO {

    @ApiModelProperty(value = "会议id", required = true)
    private int meetingId;

//    @ApiModelProperty(value = "会议过程")
    private String meetingProcess;

//    @ApiModelProperty(value = "会议过程文件", required = true)
    private String meetingProcessFile;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    private Date meetingProcessDate;
    private String meetingProcessPersonId;
    private String meetingProcessPersonName;

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingProcess() {
        return meetingProcess;
    }

    public void setMeetingProcess(String meetingProcess) {
        this.meetingProcess = meetingProcess;
    }

    public String getMeetingProcessFile() {
        return meetingProcessFile;
    }

    public void setMeetingProcessFile(String meetingProcessFile) {
        this.meetingProcessFile = meetingProcessFile;
    }

    public Date getMeetingProcessDate() {
        return meetingProcessDate;
    }

    public void setMeetingProcessDate(Date meetingProcessDate) {
        this.meetingProcessDate = meetingProcessDate;
    }

    public String getMeetingProcessPersonId() {
        return meetingProcessPersonId;
    }

    public void setMeetingProcessPersonId(String meetingProcessPersonId) {
        this.meetingProcessPersonId = meetingProcessPersonId;
    }

    public String getMeetingProcessPersonName() {
        return meetingProcessPersonName;
    }

    public void setMeetingProcessPersonName(String meetingProcessPersonName) {
        this.meetingProcessPersonName = meetingProcessPersonName;
    }
}
