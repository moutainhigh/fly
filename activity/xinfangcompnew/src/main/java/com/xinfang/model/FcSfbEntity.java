package com.xinfang.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-06 16:22
 **/
@Entity
@Table(name = "fc_sfb")
public class FcSfbEntity {
    private Integer sfId;
    private String sfMc;
    private Double xh;
    private Timestamp cjsj;
    private Timestamp xgsj;
    private Integer qyzt;

    @Id
    @Column(name = "SF_ID", nullable = false)
    public Integer getSfId() {
        return sfId;
    }

    public void setSfId(Integer sfId) {
        this.sfId = sfId;
    }

    @Basic
    @Column(name = "SF_MC", nullable = true, length = 200)
    public String getSfMc() {
        return sfMc;
    }

    public void setSfMc(String sfMc) {
        this.sfMc = sfMc;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FcSfbEntity that = (FcSfbEntity) o;

        if (sfId != that.sfId) return false;
        if (sfMc != null ? !sfMc.equals(that.sfMc) : that.sfMc != null) return false;
        if (xh != null ? !xh.equals(that.xh) : that.xh != null) return false;
        if (cjsj != null ? !cjsj.equals(that.cjsj) : that.cjsj != null) return false;
        if (xgsj != null ? !xgsj.equals(that.xgsj) : that.xgsj != null) return false;
        if (qyzt != null ? !qyzt.equals(that.qyzt) : that.qyzt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sfId;
        result = 31 * result + (sfMc != null ? sfMc.hashCode() : 0);
        result = 31 * result + (xh != null ? xh.hashCode() : 0);
        result = 31 * result + (cjsj != null ? cjsj.hashCode() : 0);
        result = 31 * result + (xgsj != null ? xgsj.hashCode() : 0);
        result = 31 * result + (qyzt != null ? qyzt.hashCode() : 0);
        return result;
    }
}
