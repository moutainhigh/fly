package com.xinfang.supervise.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xinfang.VO.LogInInfo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @title Supervise.java
 * @package com.xinfang.supervise.model
 * @description 督责问责详情表
 * @author ZhangHuaRong   
 * @update 2017年5月8日 上午10:05:13
 */
@Entity
@Table(name = "fd_supervise_detail")
public class SuperviseDetail {
	
	    @Id
	    @GeneratedValue
	    @ApiModelProperty(value = "主键id")
	    private Integer id;
	  
	    
	    @ApiModelProperty(value = "状态 0不可用  1未确认 2 确认 3 被退回")
	    private Integer state;
	   
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
	    @ApiModelProperty(value = "创建者")
        private Integer createUser;
        @ApiModelProperty(value = "被问责人员")
        private String accidentUsers;
        @ApiModelProperty(value = "被问责原因")
        private String accidentReason;
        @ApiModelProperty(value = "被问责分类")
        private String accidentType;
        @ApiModelProperty(value = "附件")
        private String enclosure;
        @ApiModelProperty(value = "案件id")
	    private Integer caseId;
        @ApiModelProperty(value = "类型1督责 2问责")
	    private Integer type;
        @ApiModelProperty(value = "问责记录")
        private String msg;
        
        @ApiModelProperty(value = "附件1")
        private String enclosure1;
        @ApiModelProperty(value = "退回原因")
        private String returnreason;
        
        @ApiModelProperty(value = "约谈时间")
	    private Date appointtime;
        
        @ApiModelProperty(value = "约谈地点")
	    private String appointaddress;
        
        @ApiModelProperty(value = "约谈人")
	    private String appointuser;
        @Transient
        private List<LogInInfo> userinfo;
        
        @Transient
        private List<LogInInfo> appointuserinfo;
        
        
        
        
       
        
        public List<LogInInfo> getAppointuserinfo() {
			return appointuserinfo;
		}

		public void setAppointuserinfo(List<LogInInfo> appointuserinfo) {
			this.appointuserinfo = appointuserinfo;
		}

		public List<LogInInfo> getUserinfo() {
			return userinfo;
		}

		public void setUserinfo(List<LogInInfo> userinfo) {
			this.userinfo = userinfo;
		}

		public String getReturnreason() {
			return returnreason;
		}

		public void setReturnreason(String returnreason) {
			this.returnreason = returnreason;
		}

		public String getEnclosure1() {
			return enclosure1;
		}

		public void setEnclosure1(String enclosure1) {
			this.enclosure1 = enclosure1;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		
        
        
        

		public Integer getCaseId() {
			return caseId;
		}

		public void setCaseId(Integer caseId) {
			this.caseId = caseId;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Date getAppointtime() {
			return appointtime;
		}

		public void setAppointtime(Date appointtime) {
			this.appointtime = appointtime;
		}

		

		public String getAppointaddress() {
			return appointaddress;
		}

		public void setAppointaddress(String appointaddress) {
			this.appointaddress = appointaddress;
		}

		public String getAppointuser() {
			return appointuser;
		}

		public void setAppointuser(String appointuser) {
			this.appointuser = appointuser;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getState() {
			return state;
		}

		public void setState(Integer state) {
			this.state = state;
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

		public Integer getCreateUser() {
			return createUser;
		}

		public void setCreateUser(Integer createUser) {
			this.createUser = createUser;
		}

		public String getAccidentUsers() {
			return accidentUsers;
		}

		public void setAccidentUsers(String accidentUsers) {
			this.accidentUsers = accidentUsers;
		}

		public String getAccidentReason() {
			return accidentReason;
		}

		public void setAccidentReason(String accidentReason) {
			this.accidentReason = accidentReason;
		}

		public String getAccidentType() {
			return accidentType;
		}

		public void setAccidentType(String accidentType) {
			this.accidentType = accidentType;
		}

		public String getEnclosure() {
			return enclosure;
		}

		public void setEnclosure(String enclosure) {
			this.enclosure = enclosure;
		}   
	    
        
        
	    
	    
	   

}
