package com.xinfang.evaluating.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;
import java.util.Map;


/**
 * Created by sunbjx on 2017/5/16.
 */
@Entity
@Table(name = "fp_assess")
@ApiModel
public class FpAssessEntity {
    private Integer assessId;
    @ApiModelProperty(value = "考核项目名称")
    private String itemName;
    @ApiModelProperty(value = "考核开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startTime;
    @ApiModelProperty(value = "考核结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endTime;
    @ApiModelProperty(value = "发起人ID")
    private Integer initiatorId;
    @ApiModelProperty(value = "考核描述")
    private String description;
    @ApiModelProperty(value = "创建者ID")
    private Integer createrId;
    private Date gmtCreate;
    private Date gmtModified;
    @ApiModelProperty(value = "发起单位ID")
    private Integer sponsorUnitId;
    @ApiModelProperty(value = "创建者头像")
    private String initiatorHeadsrc;
    @ApiModelProperty(value = "创建者姓名")
    private String initiatorName;
    @ApiModelProperty(value = "发起单位名称")
    private String sponsorUnitName;
    private Integer state;

    // 考核 A 类单位
    private Map<String, Object> unitA;
    private  String unitTypeA;
    // 考核 B 类单位
    private Map<String, Object> unitB;
    private String  unitTypeB;
    
    @Transient
    public String getUnitTypeA() {
		return unitTypeA;
	}

	public void setUnitTypeA(String unitTypeA) {
		this.unitTypeA = unitTypeA;
	}
	@Transient
	public String getUnitTypeB() {
		return unitTypeB;
	}

	public void setUnitTypeB(String unitTypeB) {
		this.unitTypeB = unitTypeB;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assess_id")
    public Integer getAssessId() {
        return assessId;
    }

    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
    }

    @Basic
    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "initiator_id")
    public Integer getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Integer initiatorId) {
        this.initiatorId = initiatorId;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "creater_id")
    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    @Basic
    @Column(name = "gmt_create")
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_modified")
    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Basic
    @Column(name = "sponsor_unit_id")
    public Integer getSponsorUnitId() {
        return sponsorUnitId;
    }

    public void setSponsorUnitId(Integer sponsorUnitId) {
        this.sponsorUnitId = sponsorUnitId;
    }

    @Basic
    @Column(name = "initiator_headsrc")
    public String getInitiatorHeadsrc() {
        return initiatorHeadsrc;
    }

    public void setInitiatorHeadsrc(String initiatorHeadsrc) {
        this.initiatorHeadsrc = initiatorHeadsrc;
    }

    @Basic
    @Column(name = "initiator_name")
    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    @Basic
    @Column(name = "sponsor_unit_name")
    public String getSponsorUnitName() {
        return sponsorUnitName;
    }

    public void setSponsorUnitName(String sponsorUnitName) {
        this.sponsorUnitName = sponsorUnitName;
    }

    @Transient
    public Map<String, Object> getUnitA() {
        return unitA;
    }

    public void setUnitA(Map<String, Object> unitA) {
        this.unitA = unitA;
    }

    @Transient
    public Map<String, Object> getUnitB() {
        return unitB;
    }

    public void setUnitB(Map<String, Object> unitB) {
        this.unitB = unitB;
    }
    @Basic
    @Column(name="state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FpAssessEntity that = (FpAssessEntity) o;

        if (assessId != that.assessId) return false;
        if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (initiatorId != null ? !initiatorId.equals(that.initiatorId) : that.initiatorId != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createrId != null ? !createrId.equals(that.createrId) : that.createrId != null) return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null) return false;
        if (gmtModified != null ? !gmtModified.equals(that.gmtModified) : that.gmtModified != null) return false;
        if (sponsorUnitId != null ? !sponsorUnitId.equals(that.sponsorUnitId) : that.sponsorUnitId != null)
            return false;
        if (initiatorHeadsrc != null ? !initiatorHeadsrc.equals(that.initiatorHeadsrc) : that.initiatorHeadsrc != null)
            return false;
        if (initiatorName != null ? !initiatorName.equals(that.initiatorName) : that.initiatorName != null)
            return false;
        if (sponsorUnitName != null ? !sponsorUnitName.equals(that.sponsorUnitName) : that.sponsorUnitName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = assessId;
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (initiatorId != null ? initiatorId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtModified != null ? gmtModified.hashCode() : 0);
        result = 31 * result + (sponsorUnitId != null ? sponsorUnitId.hashCode() : 0);
        result = 31 * result + (initiatorHeadsrc != null ? initiatorHeadsrc.hashCode() : 0);
        result = 31 * result + (initiatorName != null ? initiatorName.hashCode() : 0);
        result = 31 * result + (sponsorUnitName != null ? sponsorUnitName.hashCode() : 0);
        return result;
    }
}
