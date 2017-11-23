package com.xinfang.taskdistribute.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-01 15:03
 **/
@Entity
@Table(name = "rw_task")
public class RwTask {
    private Integer taskId;
    private Integer taskTypeId;
    private String taskTypeName;
    private Integer taskLevelId;
    private String taskLevelName;
    private Integer taskIntervalId;
    private String taskIntervalName;
    private Integer taskInterval;
    private String taskTitle;
    private String taskContent;
    private String taskFile;
    private Byte isWarn;
    private String smsContent;
    private Date feedbackDate;
    private Byte isTop;
    private Date createDate;
    private Integer createPersonId;
    private Integer isFlag;

    @Id
    @GeneratedValue
    @Column(name = "task_id", nullable = false)
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "task_type_id", nullable = true)
    public Integer getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(Integer taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    @Basic
    @Column(name = "task_type_name", nullable = true, length = 50)
    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    @Basic
    @Column(name = "task_level_id", nullable = true)
    public Integer getTaskLevelId() {
        return taskLevelId;
    }

    public void setTaskLevelId(Integer taskLevelId) {
        this.taskLevelId = taskLevelId;
    }

    @Basic
    @Column(name = "task_level_name", nullable = true, length = 50)
    public String getTaskLevelName() {
        return taskLevelName;
    }

    public void setTaskLevelName(String taskLevelName) {
        this.taskLevelName = taskLevelName;
    }

    @Basic
    @Column(name = "task_interval_id", nullable = true)
    public Integer getTaskIntervalId() {
        return taskIntervalId;
    }

    public void setTaskIntervalId(Integer taskIntervalId) {
        this.taskIntervalId = taskIntervalId;
    }

    @Basic
    @Column(name = "task_interval_name", nullable = true, length = 50)
    public String getTaskIntervalName() {
        return taskIntervalName;
    }

    public void setTaskIntervalName(String taskIntervalName) {
        this.taskIntervalName = taskIntervalName;
    }

    @Basic
    @Column(name = "task_interval", nullable = true)
    public Integer getTaskInterval() {
        return taskInterval;
    }

    public void setTaskInterval(Integer taskInterval) {
        this.taskInterval = taskInterval;
    }

    @Basic
    @Column(name = "task_title", nullable = true, length = 100)
    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    @Basic
    @Column(name = "task_content", nullable = true, length = 1000)
    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    @Basic
    @Column(name = "task_file", nullable = true, length = 1000)
    public String getTaskFile() {
        return taskFile;
    }

    public void setTaskFile(String taskFile) {
        this.taskFile = taskFile;
    }

    @Basic
    @Column(name = "is_warn", nullable = true)
    public Byte getIsWarn() {
        return isWarn;
    }

    public void setIsWarn(Byte isWarn) {
        this.isWarn = isWarn;
    }

    @Basic
    @Column(name = "sms_content", nullable = true, length = 1000)
    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    @Basic
    @Column(name = "feedback_date", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    public Date getFeedbackDate() {
        return feedbackDate;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Basic
    @Column(name = "is_top", nullable = true)
    public Byte getIsTop() {
        return isTop;
    }

    public void setIsTop(Byte isTop) {
        this.isTop = isTop;
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "create_person_id", nullable = true)
    public Integer getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(Integer createPersonId) {
        this.createPersonId = createPersonId;
    }

    @Basic
    @Column(name = "is_flag", nullable = true)
    public Integer getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(Integer isFlag) {
        this.isFlag = isFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RwTask rwTask = (RwTask) o;

        if (taskId != rwTask.taskId) return false;
        if (taskTypeId != null ? !taskTypeId.equals(rwTask.taskTypeId) : rwTask.taskTypeId != null) return false;
        if (taskTypeName != null ? !taskTypeName.equals(rwTask.taskTypeName) : rwTask.taskTypeName != null)
            return false;
        if (taskLevelId != null ? !taskLevelId.equals(rwTask.taskLevelId) : rwTask.taskLevelId != null) return false;
        if (taskLevelName != null ? !taskLevelName.equals(rwTask.taskLevelName) : rwTask.taskLevelName != null)
            return false;
        if (taskIntervalId != null ? !taskIntervalId.equals(rwTask.taskIntervalId) : rwTask.taskIntervalId != null)
            return false;
        if (taskIntervalName != null ? !taskIntervalName.equals(rwTask.taskIntervalName) : rwTask.taskIntervalName != null)
            return false;
        if (taskInterval != null ? !taskInterval.equals(rwTask.taskInterval) : rwTask.taskInterval != null)
            return false;
        if (taskTitle != null ? !taskTitle.equals(rwTask.taskTitle) : rwTask.taskTitle != null) return false;
        if (taskContent != null ? !taskContent.equals(rwTask.taskContent) : rwTask.taskContent != null) return false;
        if (taskFile != null ? !taskFile.equals(rwTask.taskFile) : rwTask.taskFile != null) return false;
        if (isWarn != null ? !isWarn.equals(rwTask.isWarn) : rwTask.isWarn != null) return false;
        if (smsContent != null ? !smsContent.equals(rwTask.smsContent) : rwTask.smsContent != null) return false;
        if (feedbackDate != null ? !feedbackDate.equals(rwTask.feedbackDate) : rwTask.feedbackDate != null)
            return false;
        if (isTop != null ? !isTop.equals(rwTask.isTop) : rwTask.isTop != null) return false;
        if (createDate != null ? !createDate.equals(rwTask.createDate) : rwTask.createDate != null) return false;
        if (createPersonId != null ? !createPersonId.equals(rwTask.createPersonId) : rwTask.createPersonId != null)
            return false;
        if (isFlag != null ? !isFlag.equals(rwTask.isFlag) : rwTask.isFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskId;
        result = 31 * result + (taskTypeId != null ? taskTypeId.hashCode() : 0);
        result = 31 * result + (taskTypeName != null ? taskTypeName.hashCode() : 0);
        result = 31 * result + (taskLevelId != null ? taskLevelId.hashCode() : 0);
        result = 31 * result + (taskLevelName != null ? taskLevelName.hashCode() : 0);
        result = 31 * result + (taskIntervalId != null ? taskIntervalId.hashCode() : 0);
        result = 31 * result + (taskIntervalName != null ? taskIntervalName.hashCode() : 0);
        result = 31 * result + (taskInterval != null ? taskInterval.hashCode() : 0);
        result = 31 * result + (taskTitle != null ? taskTitle.hashCode() : 0);
        result = 31 * result + (taskContent != null ? taskContent.hashCode() : 0);
        result = 31 * result + (taskFile != null ? taskFile.hashCode() : 0);
        result = 31 * result + (isWarn != null ? isWarn.hashCode() : 0);
        result = 31 * result + (smsContent != null ? smsContent.hashCode() : 0);
        result = 31 * result + (feedbackDate != null ? feedbackDate.hashCode() : 0);
        result = 31 * result + (isTop != null ? isTop.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (createPersonId != null ? createPersonId.hashCode() : 0);
        result = 31 * result + (isFlag != null ? isFlag.hashCode() : 0);
        return result;
    }
}
