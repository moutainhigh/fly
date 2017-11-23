package com.xinfang.model;

import javax.persistence.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-29 22:22
 **/
@Entity
@Table(name = "user_tmp")
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "ry_id")
    private Integer ryId;

    @Column(name = "user_group_id")
    private Integer userGroupId;

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

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }
}
