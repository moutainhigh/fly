package com.xinfang.taskdistribute.model;

import javax.persistence.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-01 15:03
 **/
@Entity
@Table(name = "rw_task_person_group")
public class RwTaskPersonGroup {
    private int groupPersonId;
    private int groupId;
    private String groupName;
    private int ryId;
    private String ryName;
    private Integer dwId;
    private String dwMc;
    private Integer qxsId;
    private String qxsMc;
    private Integer ksId;
    private String ksMc;
    private Integer kszwId;
    private String kszwMc;
    private String ryImg;

    private String telephone; // 新增电话号码

    @Id
    @GeneratedValue
    @Column(name = "group_person_id", nullable = false)
    public int getGroupPersonId() {
        return groupPersonId;
    }

    public void setGroupPersonId(int groupPersonId) {
        this.groupPersonId = groupPersonId;
    }

    @Basic
    @Column(name = "group_id", nullable = false)
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "group_name", nullable = true, length = 50)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
    @Column(name = "ry_name", nullable = true, length = 50)
    public String getRyName() {
        return ryName;
    }

    public void setRyName(String ryName) {
        this.ryName = ryName;
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
    @Column(name = "ry_img", nullable = true, length = 1000)
    public String getRyImg() {
        return ryImg;
    }

    public void setRyImg(String ryImg) {
        this.ryImg = ryImg;
    }

    @Basic
    @Column(name = "telephone", nullable = true, length = 1000)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "dw_mc", nullable = true, length = 1000)
    public String getDwMc() {
        return dwMc;
    }

    public void setDwMc(String dwMc) {
        this.dwMc = dwMc;
    }

    @Basic
    @Column(name = "qxs_mc", nullable = true, length = 1000)
    public String getQxsMc() {
        return qxsMc;
    }

    public void setQxsMc(String qxsMc) {
        this.qxsMc = qxsMc;
    }

    @Basic
    @Column(name = "ks_mc", nullable = true, length = 1000)
    public String getKsMc() {
        return ksMc;
    }


    public void setKsMc(String ksMc) {
        this.ksMc = ksMc;
    }

    @Basic
    @Column(name = "kszw_mc", nullable = true, length = 1000)
    public String getKszwMc() {
        return kszwMc;
    }

    public void setKszwMc(String kszwMc) {
        this.kszwMc = kszwMc;
    }
}
