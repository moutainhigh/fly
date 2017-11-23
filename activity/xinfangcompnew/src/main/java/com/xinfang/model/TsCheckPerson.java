package com.xinfang.model;

import javax.persistence.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-25 10:34
 **/
@Entity
@Table(name = "ts_check_person")
public class TsCheckPerson {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "ry_id")
    private Integer ryId;  // 人员id

    @Column(name = "check_person_id")
    private Integer checkPersonId;  //审核人id

    @Column(name = "xh")
    private Integer xh; // 排序

    @Column(name = "status")
    private Integer status;  // 状态

    @Column(name = "create_time")
    private Integer createTime;  // 创建时间

    @Column(name = "update_time")
    private Integer updateTime;  // 更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRyId() {
        return ryId;
    }

    public void setRyId(Integer ryId) {
        this.ryId = ryId;
    }

    public Integer getCheckPersonId() {
        return checkPersonId;
    }

    public void setCheckPersonId(Integer checkPersonId) {
        this.checkPersonId = checkPersonId;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }
}
