package com.xinfang.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-06 16:22
 **/
@Entity
@Table(name = "fc_sqb")
public class FcSqbEntity {
    private Integer sqId;
    private String sqMc;
    private Double xh;
    private Timestamp cjsj;
    private Timestamp xgsj;
    private Integer qyzt;
    private Integer sfId;

    @Id
    @Column(name = "SQ_ID", nullable = false)
    public Integer getSqId() {
        return sqId;
    }

    public void setSqId(Integer sqId) {
        this.sqId = sqId;
    }

    @Basic
    @Column(name = "SQ_MC", nullable = true, length = 200)
    public String getSqMc() {
        return sqMc;
    }

    public void setSqMc(String sqMc) {
        this.sqMc = sqMc;
    }

    @Basic
    @Column(name = "XH", nullable = true, precision = 0)
    public Double getXh() {
        return xh;
    }

    public void setXh(Double xh) {
        this.xh = xh;
    }

    @Basic
    @Column(name = "CJSJ", nullable = true)
    public Timestamp getCjsj() {
        return cjsj;
    }

    public void setCjsj(Timestamp cjsj) {
        this.cjsj = cjsj;
    }

    @Basic
    @Column(name = "XGSJ", nullable = true)
    public Timestamp getXgsj() {
        return xgsj;
    }

    public void setXgsj(Timestamp xgsj) {
        this.xgsj = xgsj;
    }

    @Basic
    @Column(name = "QYZT", nullable = true)
    public Integer getQyzt() {
        return qyzt;
    }

    public void setQyzt(Integer qyzt) {
        this.qyzt = qyzt;
    }

    @Basic
    @Column(name = "SF_ID", nullable = true)
    public Integer getSfId() {
        return sfId;
    }

    public void setSfId(Integer sfId) {
        this.sfId = sfId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FcSqbEntity that = (FcSqbEntity) o;

        if (sqId != that.sqId) return false;
        if (sqMc != null ? !sqMc.equals(that.sqMc) : that.sqMc != null) return false;
        if (xh != null ? !xh.equals(that.xh) : that.xh != null) return false;
        if (cjsj != null ? !cjsj.equals(that.cjsj) : that.cjsj != null) return false;
        if (xgsj != null ? !xgsj.equals(that.xgsj) : that.xgsj != null) return false;
        if (qyzt != null ? !qyzt.equals(that.qyzt) : that.qyzt != null) return false;
        if (sfId != null ? !sfId.equals(that.sfId) : that.sfId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sqId;
        result = 31 * result + (sqMc != null ? sqMc.hashCode() : 0);
        result = 31 * result + (xh != null ? xh.hashCode() : 0);
        result = 31 * result + (cjsj != null ? cjsj.hashCode() : 0);
        result = 31 * result + (xgsj != null ? xgsj.hashCode() : 0);
        result = 31 * result + (qyzt != null ? qyzt.hashCode() : 0);
        result = 31 * result + (sfId != null ? sfId.hashCode() : 0);
        return result;
    }
}
