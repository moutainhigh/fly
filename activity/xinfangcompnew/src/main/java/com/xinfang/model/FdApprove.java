package com.xinfang.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 领导批示
 * Created by sunbjx on 2017/3/23.
 */
@Entity
public class FdApprove {

    @Id
    @GeneratedValue
    private int id;
    // 批示内容
    private String content;
    // 批示时间
    private Date approveTime;
    // 批示领导
    private int leaderId;
    // 批示领导职务
    private int leaderDutyId;
    // 标准创建时间
    private Date gmtCreate;
    // 标准修改时间
    private Date gmtModified;


    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets approve time.
     *
     * @return the approve time
     */
    public Date getApproveTime() {
        return approveTime;
    }

    /**
     * Sets approve time.
     *
     * @param approveTime the approve time
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * Gets leader id.
     *
     * @return the leader id
     */
    public int getLeaderId() {
        return leaderId;
    }

    /**
     * Sets leader id.
     *
     * @param leaderId the leader id
     */
    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    /**
     * Gets leader duty id.
     *
     * @return the leader duty id
     */
    public int getLeaderDutyId() {
        return leaderDutyId;
    }

    /**
     * Sets leader duty id.
     *
     * @param leaderDutyId the leader duty id
     */
    public void setLeaderDutyId(int leaderDutyId) {
        this.leaderDutyId = leaderDutyId;
    }

    /**
     * Gets gmt create.
     *
     * @return the gmt create
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * Sets gmt create.
     *
     * @param gmtCreate the gmt create
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * Gets gmt modified.
     *
     * @return the gmt modified
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * Sets gmt modified.
     *
     * @param gmtModified the gmt modified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
