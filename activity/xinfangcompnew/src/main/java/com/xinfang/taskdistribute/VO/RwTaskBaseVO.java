package com.xinfang.taskdistribute.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinfang.VO.LogInInfo;
import com.xinfang.taskdistribute.model.RwTaskPerson;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-06 10:40
 **/
public class RwTaskBaseVO {

    private int taskId;
    private Integer taskTypeId;
    private String taskTypeName;
    private Integer taskLevelId;
    private String taskLevelName;
    private String taskTitle;
    private String taskContent;
    private String smsContent;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    private Date createDate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    private Date feedbackDate;  // 反馈截止时间

    private Integer timeLimit;   // 时限  截止时间-创建时间 天数

    private Integer isFlag;
    private String taskFile;
    private Byte isTop;
    private Byte isWarn;
    private Integer taskInterval;

    private Integer createPersonId; // 创建人
    private LogInInfo createPerson;

    private Set<RwTaskPerson> receiverPersonSet = new HashSet<>();
//    private Integer receiverPersonId; // 接收人
//    private LogInInfo receiverPerson;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public Integer getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(Integer isFlag) {
        this.isFlag = isFlag;
    }

    public Integer getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(Integer createPersonId) {
        this.createPersonId = createPersonId;
    }

    public LogInInfo getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(LogInInfo createPerson) {
        this.createPerson = createPerson;
    }

    public Set<RwTaskPerson> getReceiverPersonSet() {
        return receiverPersonSet;
    }

    public void setReceiverPersonSet(Set<RwTaskPerson> receiverPersonSet) {
        this.receiverPersonSet = receiverPersonSet;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getTaskFile() {
        return taskFile;
    }

    public void setTaskFile(String taskFile) {
        this.taskFile = taskFile;
    }

    public Byte getIsTop() {
        return isTop;
    }

    public void setIsTop(Byte isTop) {
        this.isTop = isTop;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

	public Byte getIsWarn() {
		return isWarn;
	}

	public void setIsWarn(Byte isWarn) {
		this.isWarn = isWarn;
	}

	public Integer getTaskInterval() {
		return taskInterval;
	}

	public void setTaskInterval(Integer taskInterval) {
		this.taskInterval = taskInterval;
	}
    
    
}
