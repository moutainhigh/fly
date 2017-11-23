package com.xinfang.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @title FdDepCase.java
 * @package com.xinfang.model
 * @description 单位办案状态记录，多个协办和职能部门限期需要用到
 * @author ZhangHuaRong   
 * @update 2017年3月24日 下午4:30:34
 */


@Entity
@Table(name = "fd_dep_case")
@ApiModel(value = "单位办案状态记录", description = "FdDepCase") 
public class FdDepCase {
	
	    @Id
	    @GeneratedValue
	    @ApiModelProperty(value = "主键id")
	    private Long Id;
	    @ApiModelProperty(value = "关联案件IDs")
	    private Long caseId;
	    @ApiModelProperty(value = "创建者id")
	    private Long createrId;
	    @ApiModelProperty(value = "开始时间")
	    private Date startTime;
	    @ApiModelProperty(value = "结束时间")
	    private Date endTime;
	    @ApiModelProperty(value = "更新时间")
	    private Date updateTime;
	    @ApiModelProperty(value = "创建时间")
	    private Date createTime;
	    @ApiModelProperty(value = "办案期限单位天")
	    private Float limitTime;
	    @ApiModelProperty(value = "状态1办理中 0完结")
	    private Integer state;
	    @ApiModelProperty(value = "单位id")
	    private Integer depId;
	    
	    @ApiModelProperty(value = "是否需要回执")
	    private Integer needReceipt;
	    //对应HandleLimitState 
	    @ApiModelProperty(value = "类型  ")
	    private Integer type;
	    
	    private String note;//备注
	    
	    private String processid;
	    
	    
	    private Integer flag1;
	    private Integer flag2;
	    private Integer flag3;
	    
	    
	    

		public Integer getFlag1() {
			return flag1;
		}

		public void setFlag1(Integer flag1) {
			this.flag1 = flag1;
		}

		public Integer getFlag2() {
			return flag2;
		}

		public void setFlag2(Integer flag2) {
			this.flag2 = flag2;
		}

		public Integer getFlag3() {
			return flag3;
		}

		public void setFlag3(Integer flag3) {
			this.flag3 = flag3;
		}

		public String getProcessid() {
			return processid;
		}

		public void setProcessid(String processid) {
			this.processid = processid;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Integer getNeedReceipt() {
			return needReceipt;
		}

		public void setNeedReceipt(Integer needReceipt) {
			this.needReceipt = needReceipt;
		}

		public Long getId() {
			return Id;
		}

		public void setId(Long id) {
			Id = id;
		}

		public Long getCaseId() {
			return caseId;
		}

		public void setCaseId(Long caseId) {
			this.caseId = caseId;
		}

		public Long getCreaterId() {
			return createrId;
		}

		public void setCreaterId(Long createrId) {
			this.createrId = createrId;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

	

		public Float getLimitTime() {
			return limitTime;
		}

		public void setLimitTime(Float limitTime) {
			this.limitTime = limitTime;
		}

		public Integer getState() {
			return state;
		}

		public void setState(Integer state) {
			this.state = state;
		}

		public Integer getDepId() {
			return depId;
		}

		public void setDepId(Integer depId) {
			this.depId = depId;
		}

		@Override
		public String toString() {
			return "FdDepCase [Id=" + Id + ", caseId=" + caseId + ", createTime=" + createTime + ", state=" + state
					+ ", depId=" + depId + ", note=" + note + "]";
		}
	    
	    
	    
	    
	    

}
