package com.hsd.business.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;




/**
 * 
     * 此类描述的是: 测试用户表
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:42:36
 */

public class FcRyb  {
    
	 /**serialVersionUID TODO*/ 
	
	private static final long serialVersionUID = 1L;

	/**
     * 人员ID
    */
    private Integer ryId;

    /**
     * 人员名称
     */
    private String ryMc;

    /**
     * 头像名称
     */
    private String ryImg;

    /**
     * 身份证
     */
    private String rySfz;

    /**
     * 口令
     */
    private String password;

    /**
     * email
     */
    private String ryEmail;

    /**
     * 性别
     */
    private String ryXb;

    /**
     * 手机号
     */
    private String rySjh;

    /**
     * 出生日期
     */
    private Date ryCsrq;

    /**
     * 籍贯
     */
    private String ryJg;

    /**
     * 开始从事信访工作时间
     */
    private Date ryXfgzsj;

    /**
     * 排序号
     */
    private Float xh;

    /**
     * 创建时间
     */
    private Date cjsj;

    /**
     * 修改时间
     */
    private Date xgsj;

    /**
     * 是否启用
     */
    private Integer qyzt;

    /**
     * 民族ID
     */
    private Integer mzId;

    /**
     * 学历ID
     */
    private Integer xlId;

    /**
     * 政治面貌ID
     */
    private Integer zzmmId;

    /**
     * 科室ID
     */
    private Integer ksId;

    /**
     * 单位ID
     */
    private Integer dwId;

    /**
     * 省份ID
     */
    private Integer sfId;

    /**
     * 市级ID
     */
    private Integer sqId;

    /**
     * 区县市ID
     */
    private Integer qxsId;
  

	/**
     * 获取人员ID
     *
     * @return RY_ID - 人员ID
     */
    public Integer getRyId() {
        return ryId;
    }

    /**
     * 设置人员ID
     *
     * @param ryId 人员ID
     */
    public void setRyId(Integer ryId) {
        this.ryId = ryId;
    }

    /**
     * 获取人员名称
     *
     * @return RY_MC - 人员名称
     */
    public String getRyMc() {
        return ryMc;
    }

    /**
     * 设置人员名称
     *
     * @param ryMc 人员名称
     */
    public void setRyMc(String ryMc) {
        this.ryMc = ryMc;
    }

    /**
     * 获取头像名称
     *
     * @return RY_IMG - 头像名称
     */
    public String getRyImg() {
        return ryImg;
    }

    /**
     * 设置头像名称
     *
     * @param ryImg 头像名称
     */
    public void setRyImg(String ryImg) {
        this.ryImg = ryImg;
    }

    /**
     * 获取身份证
     *
     * @return RY_SFZ - 身份证
     */
    public String getRySfz() {
        return rySfz;
    }

    /**
     * 设置身份证
     *
     * @param rySfz 身份证
     */
    public void setRySfz(String rySfz) {
        this.rySfz = rySfz;
    }

    /**
     * 获取口令
     *
     * @return password - 口令
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置口令
     *
     * @param password 口令
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取email
     *
     * @return RY_email - email
     */
    public String getRyEmail() {
        return ryEmail;
    }

    /**
     * 设置email
     *
     * @param ryEmail email
     */
    public void setRyEmail(String ryEmail) {
        this.ryEmail = ryEmail;
    }

    /**
     * 获取性别
     *
     * @return RY_XB - 性别
     */
    public String getRyXb() {
        return ryXb;
    }

    /**
     * 设置性别
     *
     * @param ryXb 性别
     */
    public void setRyXb(String ryXb) {
        this.ryXb = ryXb;
    }

    /**
     * 获取手机号
     *
     * @return RY_SJH - 手机号
     */
    public String getRySjh() {
        return rySjh;
    }

    /**
     * 设置手机号
     *
     * @param rySjh 手机号
     */
    public void setRySjh(String rySjh) {
        this.rySjh = rySjh;
    }

    /**
     * 获取出生日期
     *
     * @return RY_CSRQ - 出生日期
     */
    public Date getRyCsrq() {
        return ryCsrq;
    }

    /**
     * 设置出生日期
     *
     * @param ryCsrq 出生日期
     */
    public void setRyCsrq(Date ryCsrq) {
        this.ryCsrq = ryCsrq;
    }

    /**
     * 获取籍贯
     *
     * @return RY_JG - 籍贯
     */
    public String getRyJg() {
        return ryJg;
    }

    /**
     * 设置籍贯
     *
     * @param ryJg 籍贯
     */
    public void setRyJg(String ryJg) {
        this.ryJg = ryJg;
    }

    /**
     * 获取开始从事信访工作时间
     *
     * @return RY_XFGZSJ - 开始从事信访工作时间
     */
    public Date getRyXfgzsj() {
        return ryXfgzsj;
    }

    /**
     * 设置开始从事信访工作时间
     *
     * @param ryXfgzsj 开始从事信访工作时间
     */
    public void setRyXfgzsj(Date ryXfgzsj) {
        this.ryXfgzsj = ryXfgzsj;
    }

    /**
     * 获取排序号
     *
     * @return XH - 排序号
     */
    public Float getXh() {
        return xh;
    }

    /**
     * 设置排序号
     *
     * @param xh 排序号
     */
    public void setXh(Float xh) {
        this.xh = xh;
    }

    /**
     * 获取创建时间
     *
     * @return CJSJ - 创建时间
     */
    public Date getCjsj() {
        return cjsj;
    }

    /**
     * 设置创建时间
     *
     * @param cjsj 创建时间
     */
    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取修改时间
     *
     * @return XGSJ - 修改时间
     */
    public Date getXgsj() {
        return xgsj;
    }

    /**
     * 设置修改时间
     *
     * @param xgsj 修改时间
     */
    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    /**
     * 获取是否启用
     *
     * @return QYZT - 是否启用
     */
    public Integer getQyzt() {
        return qyzt;
    }

    /**
     * 设置是否启用
     *
     * @param qyzt 是否启用
     */
    public void setQyzt(Integer qyzt) {
        this.qyzt = qyzt;
    }

    /**
     * 获取民族ID
     *
     * @return MZ_ID - 民族ID
     */
    public Integer getMzId() {
        return mzId;
    }

    /**
     * 设置民族ID
     *
     * @param mzId 民族ID
     */
    public void setMzId(Integer mzId) {
        this.mzId = mzId;
    }

    /**
     * 获取学历ID
     *
     * @return XL_ID - 学历ID
     */
    public Integer getXlId() {
        return xlId;
    }

    /**
     * 设置学历ID
     *
     * @param xlId 学历ID
     */
    public void setXlId(Integer xlId) {
        this.xlId = xlId;
    }

    /**
     * 获取政治面貌ID
     *
     * @return ZZMM_ID - 政治面貌ID
     */
    public Integer getZzmmId() {
        return zzmmId;
    }

    /**
     * 设置政治面貌ID
     *
     * @param zzmmId 政治面貌ID
     */
    public void setZzmmId(Integer zzmmId) {
        this.zzmmId = zzmmId;
    }

    /**
     * 获取科室ID
     *
     * @return KS_ID - 科室ID
     */
    public Integer getKsId() {
        return ksId;
    }

    /**
     * 设置科室ID
     *
     * @param ksId 科室ID
     */
    public void setKsId(Integer ksId) {
        this.ksId = ksId;
    }

    /**
     * 获取单位ID
     *
     * @return DW_ID - 单位ID
     */
    public Integer getDwId() {
        return dwId;
    }

    /**
     * 设置单位ID
     *
     * @param dwId 单位ID
     */
    public void setDwId(Integer dwId) {
        this.dwId = dwId;
    }

    /**
     * 获取省份ID
     *
     * @return SF_ID - 省份ID
     */
    public Integer getSfId() {
        return sfId;
    }

    /**
     * 设置省份ID
     *
     * @param sfId 省份ID
     */
    public void setSfId(Integer sfId) {
        this.sfId = sfId;
    }

    /**
     * 获取市级ID
     *
     * @return SQ_ID - 市级ID
     */
    public Integer getSqId() {
        return sqId;
    }

    /**
     * 设置市级ID
     *
     * @param sqId 市级ID
     */
    public void setSqId(Integer sqId) {
        this.sqId = sqId;
    }

    /**
     * 获取区县市ID
     *
     * @return QXS_ID - 区县市ID
     */
    public Integer getQxsId() {
        return qxsId;
    }

    /**
     * 设置区县市ID
     *
     * @param qxsId 区县市ID
     */
    public void setQxsId(Integer qxsId) {
        this.qxsId = qxsId;
    }

	

	public String getUsername() {
		return rySjh;
	}
    /**
     * 账号是否过期
     */
	public boolean isAccountNonExpired() {
		return true;
	}
    /**
     * 帐户未锁定 
     */
	public boolean isAccountNonLocked() {
		return true;
	}
	/**
     * 凭证是否过期
     */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 是否可用
	 */
	public boolean isEnabled() {
		return false;
	}
}