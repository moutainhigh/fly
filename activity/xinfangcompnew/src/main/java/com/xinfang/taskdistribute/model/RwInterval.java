package com.xinfang.taskdistribute.model;

import javax.persistence.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-01 15:03
 **/
@Entity
@Table(name = "rw_interval")
public class RwInterval {
    private int intervalId;
    private String intervalName;
    private String intervalDescribe;
    private Byte isFlag;
    private Integer intervalMin;

    @Id
    @GeneratedValue
    @Column(name = "interval_id", nullable = false)
    public int getIntervalId() {
        return intervalId;
    }

    public void setIntervalId(int intervalId) {
        this.intervalId = intervalId;
    }

    @Basic
    @Column(name = "interval_name", nullable = true, length = 50)
    public String getIntervalName() {
        return intervalName;
    }

    public void setIntervalName(String intervalName) {
        this.intervalName = intervalName;
    }

    @Basic
    @Column(name = "interval_describe", nullable = true, length = 100)
    public String getIntervalDescribe() {
        return intervalDescribe;
    }

    public void setIntervalDescribe(String intervalDescribe) {
        this.intervalDescribe = intervalDescribe;
    }

    @Basic
    @Column(name = "is_flag", nullable = true)
    public Byte getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(Byte isFlag) {
        this.isFlag = isFlag;
    }

    @Basic
    @Column(name = "interval_min", nullable = true)
    public Integer getIntervalMin() {
        return intervalMin;
    }

    public void setIntervalMin(Integer intervalMin) {
        this.intervalMin = intervalMin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RwInterval that = (RwInterval) o;

        if (intervalId != that.intervalId) return false;
        if (intervalName != null ? !intervalName.equals(that.intervalName) : that.intervalName != null) return false;
        if (intervalDescribe != null ? !intervalDescribe.equals(that.intervalDescribe) : that.intervalDescribe != null)
            return false;
        if (isFlag != null ? !isFlag.equals(that.isFlag) : that.isFlag != null) return false;
        if (intervalMin != null ? !intervalMin.equals(that.intervalMin) : that.intervalMin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = intervalId;
        result = 31 * result + (intervalName != null ? intervalName.hashCode() : 0);
        result = 31 * result + (intervalDescribe != null ? intervalDescribe.hashCode() : 0);
        result = 31 * result + (isFlag != null ? isFlag.hashCode() : 0);
        result = 31 * result + (intervalMin != null ? intervalMin.hashCode() : 0);
        return result;
    }
}
