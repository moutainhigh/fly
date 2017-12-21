package com.xinfang.VO;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-28 9:56
 **/

public class AuthGroupNewVO {   // 新系统权限组表

    private Integer id;  // 主键id

    private String type;  // 类型

    private String permission;  // 权限

    private String note;  // 备注

    private Integer status;  // 状态

    private Integer xh;  // 排序号

    private Boolean tag;  // 标签，人员是否具有该权限

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

    public Boolean getTag() {
        return tag;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }
}

