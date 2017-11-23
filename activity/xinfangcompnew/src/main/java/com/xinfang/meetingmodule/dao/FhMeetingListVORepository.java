package com.xinfang.meetingmodule.dao;

import com.xinfang.meetingmodule.VO.FhMeetingListVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-12 10:16
 **/
public interface FhMeetingListVORepository extends JpaRepository<FhMeetingListVO, Integer>,
        JpaSpecificationExecutor<FhMeetingListVO> {

}
