package com.xinfang.meetingmodule.dao;

import com.xinfang.meetingmodule.model.FhMeetingPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-15 1:57
 **/
public interface FhMeetingPersonRepository extends JpaRepository<FhMeetingPerson,Integer>,
        JpaSpecificationExecutor<FhMeetingPerson> {

    @Modifying
    @Transactional
    @Query("update FhMeetingPerson p set p.personState = :personState, p.personStateName = :personStateName, p.absentReason = :absentReason " +
           "where p.submitId = :submitPersonId and p.meetingId = :meetingId and " +
            "p.id = :meetingPersonPk")
    Integer updatePersonState(@Param("submitPersonId") Integer submitPersonId, @Param("meetingId") Integer meetingId,
        @Param("meetingPersonPk") Integer meetingPersonPk, @Param("personState") Byte personState,
        @Param("personStateName") String personStateName, @Param("absentReason") String absentReason);

    @Query("from FhMeetingPerson p " +
            "where p.meetingId = :meetingId and p.fhMeetingInitiate.meetingOperateId = :initiatePersonId " +
            "and p.personState != 22 and p.personState = 21")  // 非‘不参加’、‘已确认’
    List<FhMeetingPerson> findByMeetingIdAndInitiatePersonId(@Param("meetingId") Integer meetingId,
                                                             @Param("initiatePersonId") Integer initiatePersonId);

    @Query("from FhMeetingPerson p " +
            "where p.meetingId = :meetingId " +
            "and p.meetingDepartmentId = :departmentId")
    List<FhMeetingPerson> findByMeetingIdAndOtherPersonId(@Param("meetingId") Integer meetingId,
                                                          @Param("departmentId") Integer departmentId);

    @Query("select c.meetingDepartmentId from FhMeetingPerson c " +
            "where c.meetingPersonId = :ryId or c.submitId = :personId")
    List<Integer> findDepartmentIdByPeopleId(@Param("personId") Integer personId);


    @Modifying
    @Transactional
    @Query("update FhMeetingPerson p set p.isRegister = :isRegister, p.registerDate = :registerDate " +
           "where p.meetingId = :meetingId and p.id = :meetingPersonPk ")
    Integer signInPerson(@Param("meetingId") Integer meetingId,
                                   @Param("meetingPersonPk") Integer meetingPersonPk,
                                   @Param("isRegister") Byte isRegister,
                                   @Param("registerDate") Date registerDate);

    @Query("select  meetingId from FhMeetingPerson where meetingPersonId = :meetingPersonId and meetingPersonId != -1")
    Set<Integer> findMeetingIdByMeetingPersonId(@Param("meetingPersonId") Integer meetingPersonId);

}
