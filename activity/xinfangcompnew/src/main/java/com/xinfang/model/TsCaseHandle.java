package com.xinfang.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-25 11:48
 **/
@Entity
@Table(name = "ts_case_handle")
public class TsCaseHandle {
    private int id;
    private Integer caseId;
    private Integer handlePersonId;
    private Integer dwId;
    private Timestamp createTime;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "case_id", nullable = true)
    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    @Basic
    @Column(name = "handle_person_id", nullable = true)
    public Integer getHandlePersonId() {
        return handlePersonId;
    }

    public void setHandlePersonId(Integer handlePersonId) {
        this.handlePersonId = handlePersonId;
    }

    @Basic
    @Column(name = "dw_id", nullable = true)
    public Integer getDwId() {
        return dwId;
    }

    public void setDwId(Integer dwId) {
        this.dwId = dwId;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TsCaseHandle that = (TsCaseHandle) o;

        if (id != that.id) return false;
        if (caseId != null ? !caseId.equals(that.caseId) : that.caseId != null) return false;
        if (handlePersonId != null ? !handlePersonId.equals(that.handlePersonId) : that.handlePersonId != null)
            return false;
        if (dwId != null ? !dwId.equals(that.dwId) : that.dwId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (caseId != null ? caseId.hashCode() : 0);
        result = 31 * result + (handlePersonId != null ? handlePersonId.hashCode() : 0);
        result = 31 * result + (dwId != null ? dwId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
