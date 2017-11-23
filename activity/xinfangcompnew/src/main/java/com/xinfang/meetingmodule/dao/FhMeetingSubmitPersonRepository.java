package com.xinfang.meetingmodule.dao;

import com.xinfang.meetingmodule.model.FhMeetingSubmitPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-15 20:27
 **/
public interface FhMeetingSubmitPersonRepository extends JpaRepository<FhMeetingSubmitPerson, Integer> {

    @Query("select meetingPersonId from FhMeetingSubmitPerson " +
            "where meetingId = :meetingId and meetingPersonId = :meetingPersonId")
    Integer findMeetingPersonIdByMeetingIdAndMeetingPersonId(
            @Param("meetingId") Integer meetingId, @Param("meetingPersonId") Integer meetingPersonId);

    Integer deleteByMeetingId(Integer meetingId);

    @Query("select  meetingId from FhMeetingSubmitPerson where meetingPersonId = :meetingPersonId")
    Set<Integer> findMeetingIdByMeetingPersonId(@Param("meetingPersonId") Integer meetingPersonId);
}
