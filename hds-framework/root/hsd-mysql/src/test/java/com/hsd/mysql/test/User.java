package com.hsd.mysql.test;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 
 * @author test
 *
 */
@Entity
@Table(name = "test_user")
public class User extends MySqlBasic<User> {


    @Column(name = "name")
    private String name;

    @Transient
    private int a;
    @Transient
    private int A;
    @Transient
    private long b;
    @Transient
    private short c;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
