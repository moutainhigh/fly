package com.xinfang.meetingmodule.VO;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-17 20:55
 **/
public class PeopleRole {

    private Integer peopleId;  // 人员id

    private Integer meetingId; // 会议id

    private Boolean isInitiatePerson;  // 是否为会议发起人

    private Boolean isSubmitPerson; // 是否为会议的部门接收人，即部门收文岗人员

    private Boolean isJoinPerson; // 是否为部门下的参会人员

    public void setPeopleId(Integer peopleId) {
        this.peopleId = peopleId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public void setInitiatePerson(Boolean initiatePerson) {
        isInitiatePerson = initiatePerson;
    }

    public void setSubmitPerson(Boolean submitPerson) {
        isSubmitPerson = submitPerson;
    }

    public void setJoinPerson(Boolean joinPerson) {
        isJoinPerson = joinPerson;
    }

    public Integer getPeopleId() {
        return peopleId;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public Boolean getInitiatePerson() {
        return isInitiatePerson;
    }

    public Boolean getSubmitPerson() {
        return isSubmitPerson;
    }

    public Boolean getJoinPerson() {
        return isJoinPerson;
    }
}
