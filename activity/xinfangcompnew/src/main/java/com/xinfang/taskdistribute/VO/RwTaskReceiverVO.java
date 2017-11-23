package com.xinfang.taskdistribute.VO;

import com.xinfang.VO.LogInInfo;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-05 15:30
 **/
public class RwTaskReceiverVO {
    private Integer taskId;
    private Integer ryId;
    private String ryMc;
    private Integer taskFlag; // 任务状态，‘任务’包括’ 0未操作（未处理）、1接收、2转发、3退回、4完成（已处理），而‘非任务类型只有’0和4
    private String ryImg;

    private String taskTitle;
    private String taskContent;
    private String taskFile;
    private Integer createPersonId; // 派发人
    private LogInInfo createPersonMessage;  // 派发信息

    private LogInInfo receiverPersonMessage;  // 接收人信息

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getRyId() {
        return ryId;
    }

    public void setRyId(Integer ryId) {
        this.ryId = ryId;
    }

    public String getRyMc() {
        return ryMc;
    }

    public void setRyMc(String ryMc) {
        this.ryMc = ryMc;
    }

    public Integer getTaskFlag() {
        return taskFlag;
    }

    public void setTaskFlag(Integer taskFlag) {
        this.taskFlag = taskFlag;
    }

    public String getRyImg() {
        return ryImg;
    }

    public void setRyImg(String ryImg) {
        this.ryImg = ryImg;
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

    public Integer getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(Integer createPersonId) {
        this.createPersonId = createPersonId;
    }

    public LogInInfo getCreatePersonMessage() {
        return createPersonMessage;
    }

    public void setCreatePersonMessage(LogInInfo createPersonMessage) {
        this.createPersonMessage = createPersonMessage;
    }

    public LogInInfo getReceiverPersonMessage() {
        return receiverPersonMessage;
    }

    public void setReceiverPersonMessage(LogInInfo receiverPersonMessage) {
        this.receiverPersonMessage = receiverPersonMessage;
    }
}
