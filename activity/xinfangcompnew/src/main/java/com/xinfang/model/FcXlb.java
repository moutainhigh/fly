package com.xinfang.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 50495 on 2017/6/29.
 */
@Entity
@Table(name = "fc_xlb", schema = "xf1", catalog = "")
public class FcXlb {
    private int xlId;
    private String xlMc;
    private Double xh;
    private Timestamp cjsj;
    private Timestamp xgsj;
    private Integer qyzt;

    @Id
    @Column(name = "XL_ID", nullable = false)
    public int getXlId() {
        return xlId;
    }

    public void setXlId(int xlId) {
        this.xlId = xlId;
    }

    @Basic
    @Column(name = "XL_MC", nullable = true, length = 200)
    public String getXlMc() {
        return xlMc;
    }

    public void setXlMc(String xlMc) {
        this.xlMc = xlMc;
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

        FcXlb fcXlb = (FcXlb) o;

        if (xlId != fcXlb.xlId) return false;
        if (xlMc != null ? !xlMc.equals(fcXlb.xlMc) : fcXlb.xlMc != null) return false;
        if (xh != null ? !xh.equals(fcXlb.xh) : fcXlb.xh != null) return false;
        if (cjsj != null ? !cjsj.equals(fcXlb.cjsj) : fcXlb.cjsj != null) return false;
        if (xgsj != null ? !xgsj.equals(fcXlb.xgsj) : fcXlb.xgsj != null) return false;
        if (qyzt != null ? !qyzt.equals(fcXlb.qyzt) : fcXlb.qyzt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = xlId;
        result = 31 * result + (xlMc != null ? xlMc.hashCode() : 0);
        result = 31 * result + (xh != null ? xh.hashCode() : 0);
        result = 31 * result + (cjsj != null ? cjsj.hashCode() : 0);
        result = 31 * result + (xgsj != null ? xgsj.hashCode() : 0);
        result = 31 * result + (qyzt != null ? qyzt.hashCode() : 0);
        return result;
    }
}
