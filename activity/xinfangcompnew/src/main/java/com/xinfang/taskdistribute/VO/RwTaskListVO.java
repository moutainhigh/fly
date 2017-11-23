package com.xinfang.taskdistribute.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-02 9:03
 **/
@Entity
@Immutable
@Subselect("SELECT " +
        " t.task_id, " +
        " t.task_type_id, " +
        " t.task_type_name, " +
        " t.task_level_id, " +
        " t.task_level_name, " +
        " t.task_title, " +
        " t.task_content, " +
        " t.create_date, " +
        " t.is_flag, " +
        " t.is_top, "+
        " t.task_interval, "+
        " t.is_warn, "+
        " t.create_person_id, " +
        " ( " +
        "  SELECT " +
        "   RY_MC " +
        "  FROM " +
        "   fc_ryb " +
        "  WHERE " +
        "   RY_ID = t.create_person_id " +
        " ) AS create_person_name, " +
        " ( " +
        "  SELECT " +
        "   GROUP_CONCAT(p.ry_id) " +
        "  FROM " +
        "   rw_task_person p " +
        "  WHERE " +
        "   p.task_id = t.task_id " +
        " ) AS receiver_person_id_str, " +
        " ( " +
        "  SELECT " +
        "   GROUP_CONCAT(p.ry_mc) " +
        "  FROM " +
        "   rw_task_person p " +
        "  WHERE " +
        "   p.task_id = t.task_id " +
        " ) AS receiver_person_name_str, " +
        " ( " +
        "  SELECT " +
        "   GROUP_CONCAT(z.ry_id) " +
        "  FROM " +
        "   rw_task_zf_person z " +
        "  WHERE " +
        "   z.task_id = t.task_id " +
        " ) AS transfer_person_id_str, " +
        " ( " +
        "  SELECT " +
        "   GROUP_CONCAT(z.ry_mc) " +
        "  FROM " +
        "   rw_task_zf_person z " +
        "  WHERE " +
        "   z.task_id = t.task_id " +
        " ) AS transfer_person_name_str, " +
        "( " +
        "  SELECT " +
        "   GROUP_CONCAT(p.task_flag) " +
        "  FROM " +
        "   rw_task_person p " +
        "  WHERE " +
        "   p.task_id = t.task_id " +
        " ) AS task_flag_str " +    // 接收人员的任务状态
        "FROM " +
        " `rw_task` t " +
        " ORDER BY " +
        " t.is_top DESC")
public class RwTaskListVO {

    @Id
    @Column(name = "task_id", nullable = false)
    @ApiModelProperty(value = "任务id")
    private int taskId;

    @Column(name = "task_type_id")
    @ApiModelProperty(value = "任务类型id")
    private Integer taskTypeId;

    @Column(name = "task_type_name")
    @ApiModelProperty(value = "任务类型名称")
    private String taskTypeName;

    @Column(name = "task_level_id")
    @ApiModelProperty(value = "任务级别id")
    private Integer taskLevelId;

    @Column(name = "task_level_name")
    @ApiModelProperty(value = "任务级别名称")
    private String taskLevelName;

    @Column(name = "task_title")
    @ApiModelProperty(value = "任务标题")
    private String taskTitle;

    @Column(name = "task_content")
    @ApiModelProperty(value = "任务内容")
    private String taskContent;

    @Column(name = "create_date")
    @ApiModelProperty(value = "创建时间 yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    private Date createDate;

    @Column(name = "is_flag")
    @ApiModelProperty(value = "整个任务状态，0未操作、1接收、2转发、3退回、4完成。（非‘任务’类型只包括0和4）")
    private Integer isFlag;

    @Column(name = "is_top")
    @ApiModelProperty(value = "是否置顶，0否、1是")
    private Integer isTop;

    @Column(name = "create_person_id")
    @ApiModelProperty(value = "派发人员的id")
    private Integer createPersonId;
    
    @Column(name = "is_warn")
    @ApiModelProperty(value = "是否提醒")
    private Byte isWarn;
    
    @Column(name = "task_interval")
    @ApiModelProperty(value = "时间间隔")
    private Integer taskInterval;

    //=====================================================

    @Column(name = "create_person_name")
    @ApiModelProperty(value = "派发人员的名字")
    private String createPersonName;

    @Column(name = "receiver_person_id_str")
    @ApiModelProperty(value = "接收人id列表")
    private String receiverPersonIdStr;

    @Column(name = "receiver_person_name_str")
    @ApiModelProperty(value = "接收人name列表")
    private String receiverPersonNameStr;

    @Column(name = "transfer_person_id_str")
    @ApiModelProperty(value = "转发人id列表")
    private String transferPersonIdStr;

    @Column(name = "transfer_person_name_str")
    @ApiModelProperty(value = "转发人name列表")
    private String transferPersonNameStr;

    @Column(name = "task_flag_str")
    @ApiModelProperty(value = "被接收该任务的人员处理状态")
    @JsonIgnore
    private String taskFlagStr;

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

    public Integer getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(Integer isFlag) {
        this.isFlag = isFlag;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(Integer createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreatePersonName() {
        return createPersonName;
    }

    public void setCreatePersonName(String createPersonName) {
        this.createPersonName = createPersonName;
    }

    public String getReceiverPersonIdStr() {
        return receiverPersonIdStr;
    }

    public void setReceiverPersonIdStr(String receiverPersonIdStr) {
        this.receiverPersonIdStr = receiverPersonIdStr;
    }

    public String getReceiverPersonNameStr() {
        return receiverPersonNameStr;
    }

    public void setReceiverPersonNameStr(String receiverPersonNameStr) {
        this.receiverPersonNameStr = receiverPersonNameStr;
    }

    public String getTransferPersonIdStr() {
        return transferPersonIdStr;
    }

    public void setTransferPersonIdStr(String transferPersonIdStr) {
        this.transferPersonIdStr = transferPersonIdStr;
    }

    public String getTransferPersonNameStr() {
        return transferPersonNameStr;
    }

    public void setTransferPersonNameStr(String transferPersonNameStr) {
        this.transferPersonNameStr = transferPersonNameStr;
    }

    public String getTaskFlagStr() {
        return taskFlagStr;
    }

    public void setTaskFlagStr(String taskFlagStr) {
        this.taskFlagStr = taskFlagStr;
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
