package com.xinfang.model;

import javax.persistence.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-30 15:15
 **/
@Entity
@Table(name = "ts_auth_group_new")
public class AuthGroupNew {   // 新系统权限组表

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;  // 主键id

    @Column(name = "type")
    private String type;  // 类型

    @Column(name = "permission")
    private String permission;  // 权限

    @Column(name = "note")
    private String note;  // 备注

    @Column(name = "status")
    private Integer status;  // 状态

    @Column(name = "xh")
    private Integer xh;  // 排序号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }
}
