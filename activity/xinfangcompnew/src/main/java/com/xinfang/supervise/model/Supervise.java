package com.xinfang.supervise.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @title Supervise.java
 * @package com.xinfang.supervise.model
 * @description 督责问责
 * @author ZhangHuaRong   
 * @update 2017年5月8日 上午10:05:13
 */
@ApiModel(value = "督责统计表",description="supervise") 
public class Supervise {
	
	   
	    @ApiModelProperty(value = "创建时间")
	    private Date createTime;
	    @ApiModelProperty(value = "更新时间")
	    private Date updateTime;
	    @ApiModelProperty(value = "发起问责单位id")
	    private Integer createDepId;
	    @ApiModelProperty(value = "发起问责单位名称")
	    private String createDepName;
	    @ApiModelProperty(value = "被问责单位id")
	    private Integer accidentDepId;
	    @ApiModelProperty(value = "被问责单位名称")
	    private String accidentDepName;
	    @ApiModelProperty(value = "超期次数")
	    private Integer overdue;
	    @ApiModelProperty(value = "群访次数")
	    private Integer groupVisit;
	    @ApiModelProperty(value = "非访次数")
	    private Integer feiVisit;
	    @ApiModelProperty(value = "退回次数")
	    private Integer returnNum;
	    @ApiModelProperty(value = "占时退回次数")
	    private Integer overTimereturnNum;
	    
	    @ApiModelProperty(value = "已问责次数")
	    private Integer superviseNum;
	    @ApiModelProperty(value = "状态")
	    private Integer state;
	    @ApiModelProperty(value = "督责详情ids")
	    private List<Integer> superviseDetailIds;
	    private Date starttime;
	    private Date endtime;
	    @ApiModelProperty(value = "关联案件ids")
	    private Set<Integer> caseIds;
	    @Transient
	    private String key;
	    
	    
	    
	    
		
	    

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public void setCaseIds(Set<Integer> caseIds) {
			this.caseIds = caseIds;
		}

		public Date getStarttime() {
			return starttime;
		}

		public void setStarttime(Date starttime) {
			this.starttime = starttime;
		}

		public Date getEndtime() {
			return endtime;
		}

		public void setEndtime(Date endtime) {
			this.endtime = endtime;
		}

		public List<Integer> getSuperviseDetailIds() {
			return superviseDetailIds;
		}

		public void setSuperviseDetailIds(List<Integer> superviseDetailIds) {
			this.superviseDetailIds = superviseDetailIds;
		}

		

		public Set<Integer> getCaseIds() {
			return caseIds;
			/*Set<Integer> test = new HashSet<Integer>();
			test.add(1);
			test.add(2);
			test.add(3);
			test.add(4);
			test.add(5);
			return test;*/
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public Integer getCreateDepId() {
			return createDepId;
		}

		public void setCreateDepId(Integer createDepId) {
			this.createDepId = createDepId;
		}

		public String getCreateDepName() {
			return createDepName;
		}

		public void setCreateDepName(String createDepName) {
			this.createDepName = createDepName;
		}

		public Integer getAccidentDepId() {
			return accidentDepId;
		}

		public void setAccidentDepId(Integer accidentDepId) {
			this.accidentDepId = accidentDepId;
		}

		public String getAccidentDepName() {
			return accidentDepName;
		}

		public void setAccidentDepName(String accidentDepName) {
			this.accidentDepName = accidentDepName;
		}

		public Integer getOverdue() {
			return overdue;
		}

		public void setOverdue(Integer overdue) {
			this.overdue = overdue;
		}

		public Integer getGroupVisit() {
			return groupVisit;
		}

		public void setGroupVisit(Integer groupVisit) {
			this.groupVisit = groupVisit;
		}

		public Integer getFeiVisit() {
			return feiVisit;
		}

		public void setFeiVisit(Integer feiVisit) {
			this.feiVisit = feiVisit;
		}

		public Integer getReturnNum() {
			return returnNum;
		}

		public void setReturnNum(Integer returnNum) {
			this.returnNum = returnNum;
		}

		public Integer getOverTimereturnNum() {
			return overTimereturnNum;
		}

		public void setOverTimereturnNum(Integer overTimereturnNum) {
			this.overTimereturnNum = overTimereturnNum;
		}

		public Integer getSuperviseNum() {
			return superviseNum;
		}

		public void setSuperviseNum(Integer superviseNum) {
			this.superviseNum = superviseNum;
		}

		public Integer getState() {
			return state;
		}

		public void setState(Integer state) {
			this.state = state;
		}
	    
	    
	    
	    
	    
	   

}
