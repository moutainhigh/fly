package com.xinfang.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-23 17:32
 **/
@Entity
@Table(name = "fc_qxsb")
public class FcQxsb {
	@ApiModelProperty(value = "区县市ID")
    private Integer qxsId;
	@ApiModelProperty(value = "区县市名称", required = true)
    private String qxsMc;
	@ApiModelProperty(value = "展示顺序")
    private Double xh;
	@ApiModelProperty(value = "创建时间")
    private Date cjsj;
	@ApiModelProperty(value = "更新时间")
    private Date xgsj;
	@ApiModelProperty(value = "启用状态", required = true)
    private Integer qyzt;
	@ApiModelProperty(value = "省份ID")
    private Integer sfId;
	@ApiModelProperty(value = "市区ID")
    private Integer sqId;
	@ApiModelProperty(value = "区县市代码", required = true)
    private String qxsCode;
	@ApiModelProperty(value = "")
    private Integer tsbz;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "QXS_ID", nullable = false)
    public Integer getQxsId() {
        return qxsId;
    }

    public void setQxsId(Integer qxsId) {
        this.qxsId = qxsId;
    }

    @Basic
    @Column(name = "QXS_MC", nullable = true, length = 200)
    public String getQxsMc() {
        return qxsMc;
    }

    public void setQxsMc(String qxsMc) {
        this.qxsMc = qxsMc;
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
    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    @Basic
    @Column(name = "XGSJ", nullable = true)
    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
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

    @Basic
    @Column(name = "SQ_ID", nullable = true)
    public Integer getSqId() {
        return sqId;
    }

    public void setSqId(Integer sqId) {
        this.sqId = sqId;
    }

    @Basic
    @Column(name = "QXS_CODE", nullable = true, length = 10)
    public String getQxsCode() {
        return qxsCode;
    }

    public void setQxsCode(String qxsCode) {
        this.qxsCode = qxsCode;
    }

    @Basic
    @Column(name = "TSBZ", nullable = true)
    public Integer getTsbz() {
        return tsbz;
    }

    public void setTsbz(Integer tsbz) {
        this.tsbz = tsbz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FcQxsb fcQxsb = (FcQxsb) o;

        if (qxsId != fcQxsb.qxsId) return false;
        if (qxsMc != null ? !qxsMc.equals(fcQxsb.qxsMc) : fcQxsb.qxsMc != null) return false;
        if (xh != null ? !xh.equals(fcQxsb.xh) : fcQxsb.xh != null) return false;
        if (cjsj != null ? !cjsj.equals(fcQxsb.cjsj) : fcQxsb.cjsj != null) return false;
        if (xgsj != null ? !xgsj.equals(fcQxsb.xgsj) : fcQxsb.xgsj != null) return false;
        if (qyzt != null ? !qyzt.equals(fcQxsb.qyzt) : fcQxsb.qyzt != null) return false;
        if (sfId != null ? !sfId.equals(fcQxsb.sfId) : fcQxsb.sfId != null) return false;
        if (sqId != null ? !sqId.equals(fcQxsb.sqId) : fcQxsb.sqId != null) return false;
        if (qxsCode != null ? !qxsCode.equals(fcQxsb.qxsCode) : fcQxsb.qxsCode != null) return false;
        if (tsbz != null ? !tsbz.equals(fcQxsb.tsbz) : fcQxsb.tsbz != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = qxsId;
        result = 31 * result + (qxsMc != null ? qxsMc.hashCode() : 0);
        result = 31 * result + (xh != null ? xh.hashCode() : 0);
        result = 31 * result + (cjsj != null ? cjsj.hashCode() : 0);
        result = 31 * result + (xgsj != null ? xgsj.hashCode() : 0);
        result = 31 * result + (qyzt != null ? qyzt.hashCode() : 0);
        result = 31 * result + (sfId != null ? sfId.hashCode() : 0);
        result = 31 * result + (sqId != null ? sqId.hashCode() : 0);
        result = 31 * result + (qxsCode != null ? qxsCode.hashCode() : 0);
        result = 31 * result + (tsbz != null ? tsbz.hashCode() : 0);
        return result;
    }
}
