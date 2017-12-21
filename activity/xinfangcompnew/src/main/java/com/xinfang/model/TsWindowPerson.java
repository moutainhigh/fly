package com.xinfang.model;

import javax.persistence.*;

import java.sql.Timestamp;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-28 15:18
 **/
@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@Table(name = "ts_window_person")
public class TsWindowPerson {
    private int id;
    private Integer windowId;
    private Integer userId;
    private Integer isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private TsWindow tsWindow;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "window_id", insertable = false, updatable = false)
    public TsWindow getTsWindow() {
        return tsWindow;
    }

    public void setTsWindow(TsWindow tsWindow) {
        this.tsWindow = tsWindow;
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
    @Column(name = "window_id", nullable = true)
    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "is_active", nullable = true)
    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TsWindowPerson that = (TsWindowPerson) o;

        if (id != that.id) return false;
        if (windowId != null ? !windowId.equals(that.windowId) : that.windowId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (isActive != null ? !isActive.equals(that.isActive) : that.isActive != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (windowId != null ? windowId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }
}
