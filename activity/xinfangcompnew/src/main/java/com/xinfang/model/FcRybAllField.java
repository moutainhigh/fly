package com.xinfang.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-23 17:52
 **/
@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@Table(name = "fc_ryb", schema = "xf1")
public class FcRybAllField implements Serializable {
    private Integer ryId;
    private String ryMc;
    private String ryImg;
    private String rySfz;
    private String password;
    private String ryEmail;
    private String ryXb;
    private String rySjh;
    private Date ryCsrq;
    private String ryJg;
    private Date ryXfgzsj;
    private Integer xh;
    private Date cjsj;
    private Date xgsj;
    private Integer qyzt;
    private Integer mzId;
    private Integer xlId;
    private Integer zzmmId;
    private Integer zwId;
    private Integer ksId;
    private Integer dwId;
    private Integer sfId;
    private Integer sqId;
    private Integer qxsId;

    private String personBusiness;		//负责业务，注：需人员表中新加字段
    private String logInName;		//人员登录账号名，注：需人员表中新加字段
    private Integer checkPersonId;		// 默认审核人，默认为上级职务对应人员id
    private Date thePartyTime;  // 入党时间

    private FcKszwb kszwb;

    private FcQxsb fcQxsb;

    private FcDwb fcDwb;

    private FcKsb fcKsb;

    private FcRybAllField checkPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ZW_ID", referencedColumnName = "KSZW_ID", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    public FcKszwb getKszwb() {
        return kszwb;
    }

    public void setKszwb(FcKszwb kszwb) {
        this.kszwb = kszwb;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QXS_ID", referencedColumnName = "QXS_ID", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    public FcQxsb getFcQxsb() {
        return fcQxsb;
    }

    public void setFcQxsb(FcQxsb fcQxsb) {
        this.fcQxsb = fcQxsb;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="DW_ID", referencedColumnName = "DW_ID", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    public FcDwb getFcDwb() {
        return fcDwb;
    }

    public void setFcDwb(FcDwb fcDwb) {
        this.fcDwb = fcDwb;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="KS_ID", referencedColumnName = "KS_ID", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    public FcKsb getFcKsb() {
        return fcKsb;
    }

    public void setFcKsb(FcKsb fcKsb) {
        this.fcKsb = fcKsb;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)   // 主键id自增长
    @Column(name = "RY_ID", nullable = false)
    public Integer getRyId() {
        return ryId;
    }

    public void setRyId(Integer ryId) {
        this.ryId = ryId;
    }

    @Basic
    @Column(name = "RY_MC", nullable = true, length = 300)
    public String getRyMc() {
        return ryMc;
    }

    public void setRyMc(String ryMc) {
        this.ryMc = ryMc;
    }

    @Basic
    @Column(name = "RY_IMG", nullable = true, length = 2000)
    public String getRyImg() {
        return ryImg;
    }

    public void setRyImg(String ryImg) {
        this.ryImg = ryImg;
    }

    @Basic
    @Column(name = "RY_SFZ", unique = true)
    public String getRySfz() {
        return rySfz;
    }

    public void setRySfz(String rySfz) {
        this.rySfz = rySfz;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 200)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "RY_email", nullable = true, length = 200)
    public String getRyEmail() {
        return ryEmail;
    }

    public void setRyEmail(String ryEmail) {
        this.ryEmail = ryEmail;
    }

    @Basic
    @Column(name = "RY_XB", nullable = true, length = 200)
    public String getRyXb() {
        return ryXb;
    }

    public void setRyXb(String ryXb) {
        this.ryXb = ryXb;
    }

    @Basic
    @Column(name = "RY_SJH", nullable = true, length = 200)
    public String getRySjh() {
        return rySjh;
    }

    public void setRySjh(String rySjh) {
        this.rySjh = rySjh;
    }

    @Basic
    @Column(name = "RY_CSRQ", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getRyCsrq() {
        return ryCsrq;
    }

    public void setRyCsrq(Date ryCsrq) {
        this.ryCsrq = ryCsrq;
    }

    @Basic
    @Column(name = "RY_JG", nullable = true, length = 1000)
    public String getRyJg() {
        return ryJg;
    }

    public void setRyJg(String ryJg) {
        this.ryJg = ryJg;
    }

    @Basic
    @Column(name = "RY_XFGZSJ", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getRyXfgzsj() {
        return ryXfgzsj;
    }

    public void setRyXfgzsj(Date ryXfgzsj) {
        this.ryXfgzsj = ryXfgzsj;
    }

    @Basic
    @Column(name = "XH", nullable = true, precision = 0)
    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    @Basic
    @Column(name = "CJSJ", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    @Basic
    @Column(name = "XGSJ", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
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
    @Column(name = "MZ_ID", nullable = true)
    public Integer getMzId() {
        return mzId;
    }

    public void setMzId(Integer mzId) {
        this.mzId = mzId;
    }

    @Basic
    @Column(name = "XL_ID", nullable = true)
    public Integer getXlId() {
        return xlId;
    }

    public void setXlId(Integer xlId) {
        this.xlId = xlId;
    }

    @Basic
    @Column(name = "ZZMM_ID", nullable = true)
    public Integer getZzmmId() {
        return zzmmId;
    }

    public void setZzmmId(Integer zzmmId) {
        this.zzmmId = zzmmId;
    }

    @Basic
    @Column(name = "ZW_ID", nullable = true)
    public Integer getZwId() {
        return zwId;
    }

    public void setZwId(Integer zwId) {
        this.zwId = zwId;
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
    @Column(name = "DW_ID", nullable = true)
    public Integer getDwId() {
        return dwId;
    }

    public void setDwId(Integer dwId) {
        this.dwId = dwId;
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
    @Column(name = "QXS_ID", nullable = true)
    public Integer getQxsId() {
        return qxsId;
    }

    public void setQxsId(Integer qxsId) {
        this.qxsId = qxsId;
    }

    @Basic
    @Column(name = "PERSON_BUSINESS", nullable = true)
    public String getPersonBusiness() {
        return personBusiness;
    }

    public void setPersonBusiness(String personBusiness) {
        this.personBusiness = personBusiness;
    }

    @Basic
    @Column(name = "LOGIN_NAME", unique = true)
    public String getLogInName() {
        return logInName;
    }

    public void setLogInName(String logInName) {
        this.logInName = logInName;
    }

    @Column(name = "CHECK_PERSON_ID")
    public Integer getCheckPersonId() {
        return checkPersonId;
    }

    public void setCheckPersonId(Integer checkPersonId) {
        this.checkPersonId = checkPersonId;
    }

    @Basic
    @Column(name = "THE_PARTY_TIME")  // 入党时间
    @DateTimeFormat(pattern="yyyy-MM-dd")  // 后台date转换String
    @JsonFormat(pattern="yyyy-MM-dd",timezone="Asia/Shanghai") // 前端表单里的日期String转成后台的Date
    public Date getThePartyTime() {
        return thePartyTime;
    }

    public void setThePartyTime(Date thePartyTime) {
        this.thePartyTime = thePartyTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHECK_PERSON_ID", referencedColumnName = "RY_Id", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    public FcRybAllField getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(FcRybAllField checkPerson) {
        this.checkPerson = checkPerson;
    }
}
