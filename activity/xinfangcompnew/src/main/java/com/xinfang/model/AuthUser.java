package com.xinfang.model;

import javax.persistence.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-29 15:30
 **/
@Entity
@Table(name = "ts_auth_user")
public class AuthUser {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "ry_id", unique = true)
    private Integer ryId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ry_id", referencedColumnName = "RY_ID", insertable = false, updatable = false,foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private FcRybAllField fcRyb;

    public FcRybAllField getFcRyb() {
        return fcRyb;
    }

    public void setFcRyb(FcRybAllField fcRyb) {
        this.fcRyb = fcRyb;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRyId() {
        return ryId;
    }

    public void setRyId(Integer ryId) {
        this.ryId = ryId;
    }
}
