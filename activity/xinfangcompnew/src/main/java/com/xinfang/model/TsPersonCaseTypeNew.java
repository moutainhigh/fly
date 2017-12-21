package com.xinfang.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-27 15:28
 **/
@SuppressWarnings("ALL")
@Entity
@Table(name = "ts_person_case_type_new")
public class TsPersonCaseTypeNew {
    private int id;
    private Integer userId;
    private Integer typeId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer caseTypeId;

    private FcRybAllField fcRybAllField;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "ry_id", updatable = false, insertable = false,
            foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    public FcRybAllField getFcRybAllField() {
        return fcRybAllField;
    }

    public void setFcRybAllField(FcRybAllField fcRybAllField) {
        this.fcRybAllField = fcRybAllField;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    @Column(name = "type_id", nullable = true)
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "created_at", nullable = true)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at", nullable = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TsPersonCaseTypeNew that = (TsPersonCaseTypeNew) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;
        if (caseTypeId != null ? !caseTypeId.equals(that.caseTypeId) : that.caseTypeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (caseTypeId != null ? caseTypeId.hashCode() : 0);
        return result;
    }
}
