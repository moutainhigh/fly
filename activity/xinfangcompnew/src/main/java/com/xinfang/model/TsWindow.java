package com.xinfang.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-28 15:18
 **/
@Entity
@Table(name = "ts_window")
public class TsWindow {
	 @ApiModelProperty(value = "id")
    private int id;
	 @ApiModelProperty(value = "窗口名" ,required=true)
    private String windowName;
	 @ApiModelProperty(value = "单位ID",required=true)
    private Double orgId;
	 @ApiModelProperty(value = "状态",required=true)
    private Integer status;
	 @ApiModelProperty(value = "窗口描述")
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @ApiModelProperty(value = "展示顺序")
    private Integer xh; // 新增，展示顺序
    @Transient
    private Integer ryCount;//新增 ，人员数量
    @Transient
    private List<String> ryName; //新增，人员姓名
    @Transient
    private String orgName;//新增，所属机构名
    @Transient
    private List<String> rid;
    @Transient
    public List<String> getRid() {
		return rid;
	}

	public void setRid(List<String> rid) {
		this.rid = rid;
	}
	 @Transient
	public Integer getRyCount() {
		return ryCount;
	}

	public void setRyCount(Integer ryCount) {
		this.ryCount = ryCount;
	}
	 @Transient
	public List<String> getRyName() {
		return ryName;
	}

	public void setRyName(List<String> ryName) {
		this.ryName = ryName;
	}
	 @Transient
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "window_name", nullable = true, length = 200)
    public String getWindowName() {
        return windowName;
    }

    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    @Basic
    @Column(name = "org_id", nullable = true, precision = 0)
    public Double getOrgId() {
        return orgId;
    }
   
    public void setOrgId(Double orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "created_at", nullable = true)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at", nullable = true)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "xh", nullable = true)
    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TsWindow tsWindow = (TsWindow) o;

        if (id != tsWindow.id) return false;
        if (windowName != null ? !windowName.equals(tsWindow.windowName) : tsWindow.windowName != null) return false;
        if (orgId != null ? !orgId.equals(tsWindow.orgId) : tsWindow.orgId != null) return false;
        if (status != null ? !status.equals(tsWindow.status) : tsWindow.status != null) return false;
        if (description != null ? !description.equals(tsWindow.description) : tsWindow.description != null)
            return false;
        if (createdAt != null ? !createdAt.equals(tsWindow.createdAt) : tsWindow.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(tsWindow.updatedAt) : tsWindow.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (windowName != null ? windowName.hashCode() : 0);
        result = 31 * result + (orgId != null ? orgId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }
}
