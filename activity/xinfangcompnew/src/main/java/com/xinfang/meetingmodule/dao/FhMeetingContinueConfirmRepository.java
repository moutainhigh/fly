package com.xinfang.meetingmodule.dao;

import com.xinfang.meetingmodule.VO.FhMeetingContinueConfirmVO;
import com.xinfang.meetingmodule.model.FhMeetingContinueConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-17 14:58
 **/
public interface FhMeetingContinueConfirmRepository extends JpaRepository<FhMeetingContinueConfirm, Integer> {

    List<FhMeetingContinueConfirmVO> findByMeetingIdAndContinueId(Integer meetingId, Integer continueId);

    @Query("select new " +
            "map(confirmId as confirmId, continueId as continueId, meetingId as meetingId," +
            "confirmId as confirmId,max(continueDate) as continueDate, continuePersonId as continuePersonId," +
            "continuePersonName as  continuePersonName, continueContent as continueContent) " +
            "from FhMeetingContinueConfirm where meetingId = :meetingId group by meetingId")
    List<Map> findByMeetingId(@Param("meetingId") Integer meetingId);
}
