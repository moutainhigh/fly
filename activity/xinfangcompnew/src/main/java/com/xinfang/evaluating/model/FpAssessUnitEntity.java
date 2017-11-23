package com.xinfang.evaluating.model;

import javax.persistence.*;

/**
 * Created by sunbjx on 2017/5/16.
 */
@Entity
@Table(name = "fp_assess_unit")
public class FpAssessUnitEntity {

    private Integer assessUnitId;
    private Integer assessUnitType;
    private String assessUnitName;
    private Integer unitId;
    private int assessId;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assess_unit_id")
    public Integer getAssessUnitId() {
        return assessUnitId;
    }

    public void setAssessUnitId(Integer assessUnitId) {
        this.assessUnitId = assessUnitId;
    }

    @Basic
    @Column(name = "assess_unit_type")
    public Integer getAssessUnitType() {
        return assessUnitType;
    }

    public void setAssessUnitType(Integer assessUnitType) {
        this.assessUnitType = assessUnitType;
    }

    @Basic
    @Column(name = "assess_unit_name")
    public String getAssessUnitName() {
        return assessUnitName;
    }

    public void setAssessUnitName(String assessUnitName) {
        this.assessUnitName = assessUnitName;
    }

    @Basic
    @Column(name = "unit_id")
    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    @Basic
    @Column(name = "assess_id")
    public int getAssessId() {
        return assessId;
    }

    public void setAssessId(int assessId) {
        this.assessId = assessId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FpAssessUnitEntity that = (FpAssessUnitEntity) o;

        if (assessId != that.assessId) return false;
        if (assessUnitId != that.assessUnitId) return false;
        if (assessUnitType != null ? !assessUnitType.equals(that.assessUnitType) : that.assessUnitType != null)
            return false;
        if (assessUnitName != null ? !assessUnitName.equals(that.assessUnitName) : that.assessUnitName != null)
            return false;
        if (unitId != null ? !unitId.equals(that.unitId) : that.unitId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = assessUnitType != null ? assessUnitType.hashCode() : 0;
        result = 31 * result + (assessUnitName != null ? assessUnitName.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        result = 31 * result + assessId;
        result = 31 * result + assessUnitId;
        return result;
    }
}
