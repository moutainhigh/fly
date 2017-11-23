package com.xinfang.taskdistribute.model;

import javax.persistence.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-01 15:03
 **/
@Entity
@Table(name = "rw_task_zf_person")
public class RwTaskZfPerson {
    private int id;
    private int taskId;
    private int ryId;
    private String ryMc;
    private Integer dwId;
    private Integer qxsId;
    private Integer ksId;
    private Integer kszwId;
    private String ryImg;
    private Integer taskZfPersonId;

    @Id
    @GeneratedValue
    @Column(name = "task_person_id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Basic
    @Column(name = "task_id", nullable = false)
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "ry_id", nullable = false)
    public int getRyId() {
        return ryId;
    }

    public void setRyId(int ryId) {
        this.ryId = ryId;
    }

    @Basic
    @Column(name = "ry_mc", nullable = true, length = 20)
    public String getRyMc() {
        return ryMc;
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
    @Column(name = "ry_img", nullable = true, length = 2000)
    public String getRyImg() {
        return ryImg;
    }

    public void setRyImg(String ryImg) {
        this.ryImg = ryImg;
    }

    @Basic
    @Column(name = "task_zf_person_id", nullable = true)
    public Integer getTaskZfPersonId() {
        return taskZfPersonId;
    }

    public void setTaskZfPersonId(Integer taskZfPersonId) {
        this.taskZfPersonId = taskZfPersonId;
    }

}
