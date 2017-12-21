package com.xinfang.VO;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-30 16:00
 **/
public class AuthUserInfo {

    private Integer ryId;

    private String ryName;

    private Integer groupId;

    private String groupName;

    public AuthUserInfo(Integer groupId) {
        this.groupId = groupId;
    }

    public AuthUserInfo(Integer ryId, String ryName, Integer groupId, String groupName) {
        this.ryId = ryId;
        this.ryName = ryName;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Integer getRyId() {
        return ryId;
    }

    public void setRyId(Integer ryId) {
        this.ryId = ryId;
    }

    public String getRyName() {
        return ryName;
    }

    public void setRyName(String ryName) {
        this.ryName = ryName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
