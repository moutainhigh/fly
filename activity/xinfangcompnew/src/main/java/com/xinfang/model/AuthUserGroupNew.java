package com.xinfang.model;

import javax.persistence.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-28 9:45
 **/

@Entity
@Table(name = "ts_auth_user_group_new")
public class AuthUserGroupNew {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "group_id")
    private Integer groupId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="group_id", referencedColumnName = "id", insertable = false, updatable = false,foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private AuthGroupNew authGroupNew;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "RY_ID", insertable = false, updatable = false,foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private FcRybAllField fcRybAllField;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public AuthGroupNew getAuthGroupNew() {
        return authGroupNew;
    }

    public void setAuthGroupNew(AuthGroupNew authGroupNew) {
        this.authGroupNew = authGroupNew;
    }

    public FcRybAllField getFcRybAllField() {
        return fcRybAllField;
    }

    public void setFcRybAllField(FcRybAllField fcRybAllField) {
        this.fcRybAllField = fcRybAllField;
    }
}
