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
@Table(name = "rw_task_person")
public class RwTaskPerson {
    private int taskPersonId;
    private Integer taskId;
    private Integer ryId;
    private String ryMc;
    private String telephone;
    private Integer dwId;
    private String dwMc;
    private Integer qxsId;
    private String qxsMc;
    private Integer ksId;
    private String ksMc;
    private Integer kszwId;
    private String kszwMc;
    private Integer taskFlag;
    private String ryImg;
    private String taskReturnContent;
    private Date taskReturnDate;
    private String taskContent;
    private Date taskDate;
    private String taskFile;

    private Date taskReceiveDate;  // 任务接受时间，任务类taskFlag=1，非任务类taskFlag=4

    @Id
    @GeneratedValue
    @Column(name = "task_person_id", nullable = false)
    public int getTaskPersonId() {
        return taskPersonId;
    }

    public void setTaskPersonId(int taskPersonId) {
        this.taskPersonId = taskPersonId;
    }

    @Basic
    @Column(name = "task_id", nullable = true)
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "ry_id", nullable = true)
    public Integer getRyId() {
        return ryId;
    }

    public void setRyId(Integer ryId) {
        this.ryId = ryId;
    }

    @Basic
    @Column(name = "ry_mc", nullable = true, length = 20)
    public String getRyMc() {
        return ryMc;
    }

    @Basic
    @Column(name = "telephone", nullable = true, length = 20)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setRyMc(String ryMc) {
        this.ryMc = ryMc;
    }

    @Basic
    @Column(name = "dw_id", nullable = true)
    public Integer getDwId() {
        return dwId;
    }

    public void setDwId(Integer dwId) {
        this.dwId = dwId;
    }

    @Basic
    @Column(name = "qxs_id", nullable = true)
    public Integer getQxsId() {
        return qxsId;
    }

    public void setQxsId(Integer qxsId) {
        this.qxsId = qxsId;
    }

    @Basic
    @Column(name = "ks_id", nullable = true)
    public Integer getKsId() {
        return ksId;
    }

    public void setKsId(Integer ksId) {
        this.ksId = ksId;
    }

    @Basic
    @Column(name = "kszw_id", nullable = true)
    public Integer getKszwId() {
        return kszwId;
    }

    public void setKszwId(Integer kszwId) {
        this.kszwId = kszwId;
    }

    @Basic
    @Column(name = "task_flag", nullable = true)
    public Integer getTaskFlag() {
        return taskFlag;
    }

    public void setTaskFlag(Integer taskFlag) {
        this.taskFlag = taskFlag;
    }

    @Basic
    @Column(name = "ry_img", nullable = true, length = 1000)
    public String getRyImg() {
        return ryImg;
    }

    public void setRyImg(String ryImg) {
        this.ryImg = ryImg;
    }

    @Basic
    @Column(name = "task_return_content", nullable = true, length = 1000)
    public String getTaskReturnContent() {
        return taskReturnContent;
    }

    public void setTaskReturnContent(String taskReturnContent) {
        this.taskReturnContent = taskReturnContent;
    }

    @Basic
    @Column(name = "task_return_date", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getTaskReturnDate() {
        return taskReturnDate;
    }

    public void setTaskReturnDate(Date taskReturnDate) {
        this.taskReturnDate = taskReturnDate;
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
    @Column(name = "task_date", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
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
    @Column(name = "dw_mc", nullable = true, length = 100)
    public String getDwMc() {
        return dwMc;
    }

    public void setDwMc(String dwMc) {
        this.dwMc = dwMc;
    }

    @Basic
    @Column(name = "qxs_mc", nullable = true, length = 100)
    public String getQxsMc() {
        return qxsMc;
    }

    public void setQxsMc(String qxsMc) {
        this.qxsMc = qxsMc;
    }

    @Basic
    @Column(name = "ks_mc", nullable = true, length = 100)
    public String getKsMc() {
        return ksMc;
    }

    public void setKsMc(String ksMc) {
        this.ksMc = ksMc;
    }

    @Basic
    @Column(name = "kszw_mc", nullable = true, length = 100)
    public String getKszwMc() {
        return kszwMc;
    }

    public void setKszwMc(String kszwMc) {
        this.kszwMc = kszwMc;
    }

    @Basic
    @Column(name = "task_receive_date", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getTaskReceiveDate() {
        return taskReceiveDate;
    }

    public void setTaskReceiveDate(Date taskReceiveDate) {
        this.taskReceiveDate = taskReceiveDate;
    }
}
