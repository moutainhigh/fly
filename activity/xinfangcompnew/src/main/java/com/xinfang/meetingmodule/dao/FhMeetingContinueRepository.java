package com.xinfang.meetingmodule.dao;

import com.xinfang.meetingmodule.model.FhMeetingContinue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-17 11:10
 **/
public interface FhMeetingContinueRepository extends JpaRepository<FhMeetingContinue, Integer> {

    List<FhMeetingContinue> findByMeetingId(Integer meetingId);
}
