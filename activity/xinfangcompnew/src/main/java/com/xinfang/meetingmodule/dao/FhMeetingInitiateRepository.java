package com.xinfang.meetingmodule.dao;

import com.xinfang.meetingmodule.model.FhMeetingInitiate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-10 17:07
 **/
public interface FhMeetingInitiateRepository extends JpaRepository<FhMeetingInitiate, Integer> {

//    @Query("select new com.xinfang.meetingmodule.VO.FhMeetingDetailVO(" +
//            "m.meetingId," +
//            "m.meetingDate," +
//            "m.meetingAddress," +
//            "m.meetingEmcee," +
//            "m.meetingTypeId," +
//            "m.meetingTypeName," +
//            "m.departmentId," +
//            "m.departmentName," +
//            "m.meetingTitle," +
//            "m.meetingContent," +
//            "m.updateFile," +
//            "m.caseIdList," +
//            "m.caseBhList," +
//            "m.dispatchIdList," +
//            "m.dispatchBhList," +
//            "count(p.meetingPersonId) as meetingPersonAmount," +
//            "count(p.meetingPersonId) as signInPersonAmount) " +
//            "from FhMeetingInitiate m left join " +
//            "com.xinfang.meetingmodule.model.FhMeetingPerson p ON m.meetingId = p.meetingId " +
//            "where m.meetingId = :meetingId group by m.meetingId")
//    FhMeetingDetailVO findByMeetingId(@Param("meetingId") Integer meetingId);

    @Query("select meetingId from FhMeetingInitiate where meetingId = :meetingId and meetingOperateId = :meetingOperateId")
    Integer findMeetingIdByMeetingIdAndMeetingOperateId(
            @Param("meetingId") Integer meetingId, @Param("meetingOperateId") Integer meetingOperateId);


    FhMeetingInitiate findByMeetingId(Integer meetingId);

}
//    private int meetingId;
//    private Date meetingDate;
//    private String meetingAddress;
//    private String meetingEmcee;
//    private Integer meetingTypeId;
//    private String meetingTypeName;
//    private Integer departmentId;
//    private String departmentName;
//    private String meetingTitle;
//    private String meetingContent;
//    private String updateFile;
//    private String caseIdList;
//    private String caseBhList;
//    private String dispatchIdList;
//    private String dispatchBhList;
//
//    private Integer meetingPersonAmount; // 参会人数
//    private Integer signInPersonAmount;  // 签到人数