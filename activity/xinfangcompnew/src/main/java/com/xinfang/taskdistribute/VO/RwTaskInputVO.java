package com.xinfang.taskdistribute.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-01 15:30
 **/
public class RwTaskInputVO {

    @ApiModelProperty(value = "任务id。发起任务时无需填写任务id，自增长。", required = false)
    private Integer taskId;

    @ApiModelProperty(value = "任务类型id，（新增和修改时必填）")
    private Integer taskTypeId;

    @ApiModelProperty(value = "任务类型名称，（新增和修改时必填）")
    private String taskTypeName;

    @ApiModelProperty(value = "任务级别id，（新增和修改时必填）")
    private Integer taskLevelId;

    @ApiModelProperty(value = "任务级别名称，（新增和修改时必填）")
    private String taskLevelName;

    @ApiModelProperty(value = "提醒间隔名称(码表21中的name)，（新增和修改时必填）")
    private String taskIntervalName;

    @ApiModelProperty(value = "提醒间隔分钟数(码表21中的code)，（新增和修改时必填）")
    private Integer taskInterval;

    @ApiModelProperty(value = "任务标题，（新增和修改时必填）")
    private String taskTitle;

    @ApiModelProperty(value = "任务内容，（新增和修改时必填）")
    private String taskContent;

    @ApiModelProperty(value = "文件附件，（新增和修改时必填）")
    private String taskFile;

    @ApiModelProperty(value = "是否短信提醒，默认为否0，（新增和修改时必填）")
    @ApiParam(value = "是否短信提醒，默认为否,0 ，（新增和修改时必填）", defaultValue = "0")
    private Byte isWarn;

    @ApiModelProperty(value = "短信内容，如果短信提醒是1，则短信内容有效，（非必填）", required = false)
    private String smsContent;

    @ApiModelProperty(value = "反馈截止时间 yyyy-MM-dd HH:mm:ss，（新增和修改时必填）")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    private Date feedbackDate;

    @ApiModelProperty(value = "是否置顶，默认为否0，（新增和修改时必填）")
    @ApiParam(value = "是否置顶，默认为否,0", defaultValue = "0")
    private Byte isTop;

    @ApiModelProperty(value = "创建人员的id", required = true)
    private Integer createPersonId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(Integer taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    public Integer getTaskLevelId() {
        return taskLevelId;
    }

    public void setTaskLevelId(Integer taskLevelId) {
        this.taskLevelId = taskLevelId;
    }

    public String getTaskLevelName() {
        return taskLevelName;
    }

    public void setTaskLevelName(String taskLevelName) {
        this.taskLevelName = taskLevelName;
    }

    public String getTaskIntervalName() {
        return taskIntervalName;
    }

    public void setTaskIntervalName(String taskIntervalName) {
        this.taskIntervalName = taskIntervalName;
    }

    public Integer getTaskInterval() {
        return taskInterval;
    }

    public void setTaskInterval(Integer taskInterval) {
        this.taskInterval = taskInterval;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskFile() {
        return taskFile;
    }

    public void setTaskFile(String taskFile) {
        this.taskFile = taskFile;
    }

    public Byte getIsWarn() {
        return isWarn;
    }

    public void setIsWarn(Byte isWarn) {
        this.isWarn = isWarn;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public Byte getIsTop() {
        return isTop;
    }

    public void setIsTop(Byte isTop) {
        this.isTop = isTop;
    }

    public Integer getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(Integer createPersonId) {
        this.createPersonId = createPersonId;
    }

}
