package com.xinfang.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.sql.Timestamp;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-25 15:11
 **/
@Entity
@Table(name = "fc_kszwb")
public class FcKszwb {
	@ApiModelProperty(value = "科室职务ID")
    private int kszwId;
	@ApiModelProperty(value = "科室职务名称",required=true)
    private String kszwMc;
	@ApiModelProperty(value = "展示顺序")
    private Double xh;
	@ApiModelProperty(value = "创建时间")
    private Timestamp cjsj;
	@ApiModelProperty(value = "更新时间")
    private Timestamp xgsj;
	@ApiModelProperty(value = "状态",required=true)
    private Integer qyzt;
	@ApiModelProperty(value = "科室ID",required=true)
    private Integer ksId;
	@ApiModelProperty(value = "上级职务ID",required=true)
    private Integer parentId;
	@ApiModelProperty(value = "职务级别",required=true)
    private Integer professionLevel; // 新增，职务级别
	@Transient
	private String parentName;
	@Transient
	private String ksName;
	@Transient
	private String DwName;
	@Transient
	private String QxsName;
	@Transient
	private String DwCode;
	
	@Transient
	public String getDwCode() {
		return DwCode;
	}

	public void setDwCode(String dwCode) {
		DwCode = dwCode;
	}

	@Transient
	public String getDwName() {
		return DwName;
	}

	public void setDwName(String dwName) {
		DwName = dwName;
	}
	@Transient
	public String getQxsName() {
		return QxsName;
	}

	public void setQxsName(String qxsName) {
		QxsName = qxsName;
	}

	@Transient
    public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	@Transient
	public String getKsName() {
		return ksName;
	}

	public void setKsName(String ksName) {
		this.ksName = ksName;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "KSZW_ID", nullable = false)
    public int getKszwId() {
        return kszwId;
    }

    public void setKszwId(int kszwId) {
        this.kszwId = kszwId;
    }

    @Basic
    @Column(name = "KSZW_MC", nullable = true, length = 200)
    public String getKszwMc() {
        return kszwMc;
    }

    public void setKszwMc(String kszwMc) {
        this.kszwMc = kszwMc;
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
    @Column(name = "KS_ID", nullable = true)
    public Integer getKsId() {
        return ksId;
    }

    public void setKsId(Integer ksId) {
        this.ksId = ksId;
    }

    @Basic
    @Column(name = "PARENT_ID", nullable = true)
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "PROFESSION_LEVEL")
    public Integer getProfessionLevel() {
        return professionLevel;
    }

    public void setProfessionLevel(Integer professionLevel) {
        this.professionLevel = professionLevel;
    }

}
