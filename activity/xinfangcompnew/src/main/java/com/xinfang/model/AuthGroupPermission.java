package com.xinfang.model;

import javax.persistence.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-30 15:21
 **/
@Entity
@Table(name = "ts_auth_group_permission")
public class AuthGroupPermission {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "permission_id")
    private Integer permissionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}
