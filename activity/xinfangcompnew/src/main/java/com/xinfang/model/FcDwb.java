package com.xinfang.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-23 20:50
 **/
@Entity
@Table(name = "fc_dwb")
public class FcDwb {
	@ApiModelProperty(value = "单位ID")
    private Integer dwId;
	@ApiModelProperty(value = "单位名称",required=true)
    private String dwMc;
	@ApiModelProperty(value = "展示顺序")
    private Double xh;
	@ApiModelProperty(value = "创建时间")
    private Date cjsj;
	@ApiModelProperty(value = "更新时间")
    private Date xgsj;
	@ApiModelProperty(value = "启用状态",required=true)
    private Integer qyzt;
	@ApiModelProperty(value = "区县市ID",required=true)
    private Integer qxsId;
	@ApiModelProperty(value = "单位类型",required=true)
    private Integer dwType;
	
	
	@Transient
    private String strDwType;
	@Transient
    public String getStrDwType() {
		return strDwType;
	}

	public void setStrDwType(String strDwType) {
		this.strDwType = strDwType;
	}
	@ApiModelProperty(value = "单位简称")
	private String dwJc;
	
    private FcQxsb fcQxsb;
    @ApiModelProperty(value = "单位代码")
    private String orgCode; // 新增，机构代码
    @ApiModelProperty(value = "行政级别",required=true)
    private Integer orgLevel; // 新增，行政级别
    @ApiModelProperty(value = "指数评分类型",required=true)
    private Integer orgGradeType; // 新增，指数评分类型

    @OneToOne
    @JoinColumn(name = "QXS_ID", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    public FcQxsb getFcQxsb() {
        return fcQxsb;
    }

    public void setFcQxsb(FcQxsb fcQxsb) {
        this.fcQxsb = fcQxsb;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DW_ID", nullable = false)
    public Integer getDwId() {
        return dwId;
    }

    public void setDwId(Integer dwId) {
        this.dwId = dwId;
    }

    @Basic
    @Column(name = "DW_MC", nullable = true, length = 200)
    public String getDwMc() {
        return dwMc;
    }

    public void setDwMc(String dwMc) {
        this.dwMc = dwMc;
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
    @Column(name = "QXS_ID", nullable = true)
    public Integer getQxsId() {
        return qxsId;
    }

    public void setQxsId(Integer qxsId) {
        this.qxsId = qxsId;
    }

    @Basic
    @Column(name = "DW_TYPE", nullable = true)
    public Integer getDwType() {
        return dwType;
    }

    public void setDwType(Integer dwType) {
        this.dwType = dwType;
    }

    @Basic
    @Column(name = "DW_JC", nullable = true, length = 255)
    public String getDwJc() {
        return dwJc;
    }

    public void setDwJc(String dwJc) {
        this.dwJc = dwJc;
    }


    //=========================以下是评审机构人员管理后新增字段=================
    @Basic
    @Column(name = "ORG_CODE", nullable = true)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Basic
    @Column(name = "ORG_LEVEL")
    public Integer getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Integer orgLevel) {
        this.orgLevel = orgLevel;
    }

    @Basic
    @Column(name = "ORG_GRADE_TYPE", nullable = true)
    public Integer getOrgGradeType() {
        return orgGradeType;
    }

    public void setOrgGradeType(Integer orgGradeType) {
        this.orgGradeType = orgGradeType;
    }
}
