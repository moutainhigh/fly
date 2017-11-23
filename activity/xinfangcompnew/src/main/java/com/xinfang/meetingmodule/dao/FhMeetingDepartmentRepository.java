package com.xinfang.meetingmodule.dao;

import com.xinfang.meetingmodule.model.FhMeetingDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-11 17:03
 **/
public interface FhMeetingDepartmentRepository extends JpaRepository<FhMeetingDepartment, Integer>,
        JpaSpecificationExecutor<FhMeetingDepartment> {

//    Page<FhMeetingListVO> findBySearchRequest(Specification<FhMeetingListVO> specification, PageRequest pageRequest);

    @Query("select new map(departmentId as departmentId, departmentName as departmentName) " +
            "from FhMeetingDepartment where meetingId = :meetingId")
    Set<Map> findIdAndNameByMeetingId(@Param("meetingId") Integer meetingId);

    Integer deleteByMeetingId(Integer meetingId);

    List<FhMeetingDepartment> findByMeetingId(Integer meetingId);

    @Modifying
    @Transactional
    @Query("update FhMeetingDepartment set isAttend = 22, isCause = ?3 where meetingId = ?1 and departmentId = ?2")
    Integer updateDepartmentAbsent(Integer meetingId, Integer departmentId, String absentReason); // 22表示无法参加

    @Query("select isAttend from FhMeetingDepartment where meetingId = ?1 and departmentId = ?2")
    Byte findIsAttendByMeetingId(Integer meetingId, Integer departmentId);
}
