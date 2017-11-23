package com.xinfang.dao;

import com.xinfang.model.FdMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-24 10:26
 **/
public interface FdMsgRepository extends JpaRepository<FdMsg, Integer>{

    List<FdMsg> findByReceiverIdAndState(Integer reveiverId, Integer state);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update FdMsg msg set msg.state = :state where msg.id in (:messageIds)")
    void changeStateByMessageId(@Param("messageIds") List<Integer> messageIds, @Param("state")Integer state);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update FdMsg msg set msg.state = :state where msg.caseId is not null and msg.caseId in (:caseIdSet)")
    void changeStateByCaseId(@Param("caseIdSet") Set<Integer> caseIdSet, @Param("state") Integer state);

    @Query("select caseId from FdMsg where caseId is not null and caseId in (:caseIdSet)")
    Set<Integer> findCaseIdByCaseId(@Param("caseIdSet") Set<Integer> caseIdSet);
}
