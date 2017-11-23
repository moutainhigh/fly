package com.xinfang.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-24 0:01
 **/
@SuppressWarnings("ALL")
@Entity
@Table(name = "ts_transfer_person")
public class TsTransferPerson {
    private Integer id;
    private Integer userId;
    private Integer orgId;
    private Integer officeId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer caseTypeId;

    private FcRybAllField fcRybAllField; // 人员，与userId对应

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "org_id", nullable = true)
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "office_id", nullable = true)
    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    @Basic
    @Column(name = "created_at", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "case_type_id", nullable = true)
    public Integer getCaseTypeId() {
        return caseTypeId;
    }

    public void setCaseTypeId(Integer caseTypeId) {
        this.caseTypeId = caseTypeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "ry_id", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    public FcRybAllField getFcRybAllField() {
        return fcRybAllField;
    }

    public void setFcRybAllField(FcRybAllField fcRybAllField) {
        this.fcRybAllField = fcRybAllField;
    }
}
