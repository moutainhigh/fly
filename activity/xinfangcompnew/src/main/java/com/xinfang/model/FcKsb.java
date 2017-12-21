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
@Table(name = "fc_ksb")
public class FcKsb {
	@ApiModelProperty(value = "科室ID")
    private Integer ksId;
	@ApiModelProperty(value = "科室名称",required=true)
    private String ksMc;
	@ApiModelProperty(value = "展示顺序")
    private Double xh;
	@ApiModelProperty(value = "创建时间")
    private Date cjsj;
	@ApiModelProperty(value = "更新时间")
    private Date xgsj;
	@ApiModelProperty(value = "状态",required=true)
    private Integer qyzt;
	@ApiModelProperty(value = "单位ID",required=true)
    private Integer dwId;
	@ApiModelProperty(value = "区县市ID",required=true)
    private Integer qxsId;
	@Transient
	private String strQxsName;
	@Transient
	private String strDwName;

	private FcDwb fcDwb;

	@Transient
    public String getStrQxsName() {
		return strQxsName;
	}
	@Transient
	public void setStrQxsName(String strQxsName) {
		this.strQxsName = strQxsName;
	}

	public String getStrDwName() {
		return strDwName;
	}

	public void setStrDwName(String strDwName) {
		this.strDwName = strDwName;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "KS_ID", nullable = false)
    public Integer getKsId() {
        return ksId;
    }

    public void setKsId(Integer ksId) {
        this.ksId = ksId;
    }

    @Basic
    @Column(name = "KS_MC", nullable = true, length = 200)
    public String getKsMc() {
        return ksMc;
    }

    public void setKsMc(String ksMc) {
        this.ksMc = ksMc;
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
    @Column(name = "DW_ID", nullable = true)
    public Integer getDwId() {
        return dwId;
    }

    public void setDwId(Integer dwId) {
        this.dwId = dwId;
    }

    @Basic
    @Column(name = "QXS_ID", nullable = true)
    public Integer getQxsId() {
        return qxsId;
    }

    public void setQxsId(Integer qxsId) {
        this.qxsId = qxsId;
    }

    @OneToOne
    @JoinColumn(name = "DW_ID", referencedColumnName = "DW_ID", insertable = false, updatable = false,
        foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    public FcDwb getFcDwb() {
        return fcDwb;
    }

    public void setFcDwb(FcDwb fcDwb) {
        this.fcDwb = fcDwb;
    }
}
