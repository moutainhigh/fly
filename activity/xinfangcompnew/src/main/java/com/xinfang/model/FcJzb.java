package com.xinfang.model;

import java.util.Date;
import javax.persistence.*;
@Entity
@Table(name = "FC_JZB")
public class FcJzb {
    /**
     * 角色ID
     */
    @Id
    @Column(name = "JZ_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jzId;

    /**
     * 角色名称
     */
    @Column(name = "JZ_MC")
    private String jzMc;

    /**
     * 排序号
     */
    @Column(name = "XH")
    private Float xh;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private Date cjsj;

    /**
     * 修改时间
     */
    @Column(name = "XGSJ")
    private Date xgsj;

    /**
     * 是否启用
     */
    @Column(name = "QYZT")
    private Integer qyzt;
   /* @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name="FC_RYJSB",joinColumns ={@JoinColumn(name="JZ_ID",referencedColumnName="JZ_ID")} , inverseJoinColumns={
    		@JoinColumn(name="RY_ID",referencedColumnName="RY_ID")}) 
    private Set<FcRybAllField> users;*/
    
    
    
  /*  public Set<FcRybAllField> getUsers() {
		return users;
	}

	public void setUsers(Set<FcRybAllField> users) {
		this.users = users;
	}*/

	/**
     * 获取角色ID
     *
     * @return JZ_ID - 角色ID
     */
    public Integer getJzId() {
        return jzId;
    }

    /**
     * 设置角色ID
     *
     * @param jzId 角色ID
     */
    public void setJzId(Integer jzId) {
        this.jzId = jzId;
    }

    /**
     * 获取角色名称
     *
     * @return JZ_MC - 角色名称
     */
    public String getJzMc() {
        return jzMc;
    }

    /**
     * 设置角色名称
     *
     * @param jzMc 角色名称
     */
    public void setJzMc(String jzMc) {
        this.jzMc = jzMc;
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
}