package com.xinfang.meetingmodule.dao;

import com.xinfang.meetingmodule.VO.FhMeetingDetailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-16 16:07
 **/
public interface FhMeetingDetailVORepository extends JpaRepository<FhMeetingDetailVO, Integer> {

    @Query("from FhMeetingDetailVO where meetingId = :meetingId")
    FhMeetingDetailVO findByMeetingId(@Param("meetingId") Integer meetingId);
}
