package com.xinfang.evaluating.model;

import javax.persistence.*;

/**
 * Created by sunbjx on 2017/5/17.
 */
@Entity
@Table(name = "fp_assess_score")
public class FpAssessScoreEntity {
    private Integer scoreId;
    private Integer templetId;
    private String templetNumber;
    private String standard;
    private Double setvalue;
    private Integer number;
    private Double coefficient;
    private Integer control;
    private Integer enteringType;
    private String templetDescribe;
    private String level;
    private Integer sort;
    private Integer templetType;
    private Integer fid;
    private Integer assessId;
    private Integer assessUnitId;
    private Float realityScore;
    private String fileName;
    private String desc;
    
    @Basic
    @Column(name="file_name")
    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Basic
	@Column(name="`describe`")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Basic
    @Column(name="reality_score")
    public Float getRealityScore() {
		return realityScore;
	}

	public void setRealityScore(Float realityScore) {
		this.realityScore = realityScore;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "score_id")
    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    @Basic
    @Column(name = "templet_id")
    public Integer getTempletId() {
        return templetId;
    }

    public void setTempletId(Integer templetId) {
        this.templetId = templetId;
    }

    @Basic
    @Column(name = "templet_number")
    public String getTempletNumber() {
        return templetNumber;
    }

    public void setTempletNumber(String templetNumber) {
        this.templetNumber = templetNumber;
    }

    @Basic
    @Column(name = "standard")
    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    @Basic
    @Column(name = "setvalue")
    public Double getSetvalue() {
        return setvalue;
    }

    public void setSetvalue(Double setvalue) {
        this.setvalue = setvalue;
    }

    @Basic
    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Basic
    @Column(name = "coefficient")
    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    @Basic
    @Column(name = "control")
    public Integer getControl() {
        return control;
    }

    public void setControl(Integer control) {
        this.control = control;
    }

    @Basic
    @Column(name = "entering_type")
    public Integer getEnteringType() {
        return enteringType;
    }

    public void setEnteringType(Integer enteringType) {
        this.enteringType = enteringType;
    }

    @Basic
    @Column(name = "templet_describe")
    public String getTempletDescribe() {
        return templetDescribe;
    }

    public void setTempletDescribe(String templetDescribe) {
        this.templetDescribe = templetDescribe;
    }

    @Basic
    @Column(name = "level")
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Basic
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "templet_type")
    public Integer getTempletType() {
        return templetType;
    }

    public void setTempletType(Integer templetType) {
        this.templetType = templetType;
    }

    @Basic
    @Column(name = "fid")
    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @Basic
    @Column(name = "assess_id")
    public Integer getAssessId() {
        return assessId;
    }

    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
    }

    @Basic
    @Column(name = "assess_unit_id")
    public Integer getAssessUnitId() {
        return assessUnitId;
    }

    public void setAssessUnitId(Integer assessUnitId) {
        this.assessUnitId = assessUnitId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FpAssessScoreEntity that = (FpAssessScoreEntity) o;

        if (scoreId != that.scoreId) return false;
        if (templetId != null ? !templetId.equals(that.templetId) : that.templetId != null) return false;
        if (templetNumber != null ? !templetNumber.equals(that.templetNumber) : that.templetNumber != null)
            return false;
        if (standard != null ? !standard.equals(that.standard) : that.standard != null) return false;
        if (setvalue != null ? !setvalue.equals(that.setvalue) : that.setvalue != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (coefficient != null ? !coefficient.equals(that.coefficient) : that.coefficient != null) return false;
        if (control != null ? !control.equals(that.control) : that.control != null) return false;
        if (enteringType != null ? !enteringType.equals(that.enteringType) : that.enteringType != null) return false;
        if (templetDescribe != null ? !templetDescribe.equals(that.templetDescribe) : that.templetDescribe != null)
            return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        if (templetType != null ? !templetType.equals(that.templetType) : that.templetType != null) return false;
        if (fid != null ? !fid.equals(that.fid) : that.fid != null) return false;
        if (assessId != null ? !assessId.equals(that.assessId) : that.assessId != null) return false;
        if (assessUnitId != null ? !assessUnitId.equals(that.assessUnitId) : that.assessUnitId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = templetId != null ? templetId.hashCode() : 0;
        result = 31 * result + (templetNumber != null ? templetNumber.hashCode() : 0);
        result = 31 * result + (standard != null ? standard.hashCode() : 0);
        result = 31 * result + (setvalue != null ? setvalue.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (coefficient != null ? coefficient.hashCode() : 0);
        result = 31 * result + (control != null ? control.hashCode() : 0);
        result = 31 * result + (enteringType != null ? enteringType.hashCode() : 0);
        result = 31 * result + (templetDescribe != null ? templetDescribe.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (templetType != null ? templetType.hashCode() : 0);
        result = 31 * result + (fid != null ? fid.hashCode() : 0);
        result = 31 * result + (assessId != null ? assessId.hashCode() : 0);
        result = 31 * result + (assessUnitId != null ? assessUnitId.hashCode() : 0);
        result = 31 * result + scoreId;
        return result;
    }
}
