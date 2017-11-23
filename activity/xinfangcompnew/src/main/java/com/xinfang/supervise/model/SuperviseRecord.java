package com.xinfang.supervise.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @title Supervise.java
 * @package com.xinfang.supervise.model
 * @description 督责问责记录表
 * @author ZhangHuaRong   
 * @update 2017年5月8日 上午10:05:13
 */
@Entity
@Table(name = "fd_supervise_record")
public class SuperviseRecord {
	
	    @Id
	    @GeneratedValue
	    @ApiModelProperty(value = "主键id")
	    private Integer id;
	  
	    
	    @ApiModelProperty(value = "状态 ")
	    private Integer state;
	    
	    @ApiModelProperty(value = "状态 ")
	    private Integer superviseId;
		
	    @ApiModelProperty(value = "创建时间")
        private Date createTime;
	    @ApiModelProperty(value = "约谈记录")
        private String records;
	    
	    @ApiModelProperty(value = "附件")
        private String enclosure;
	    
	    
		public String getEnclosure() {
			return enclosure;
		}
		public void setEnclosure(String enclosure) {
			this.enclosure = enclosure;
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
		public Integer getSuperviseId() {
			return superviseId;
		}
		public void setSuperviseId(Integer superviseId) {
			this.superviseId = superviseId;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public String getRecords() {
			return records;
		}
		public void setRecords(String records) {
			this.records = records;
		}
        
        
        
        

        
	    
	    
	   

}
