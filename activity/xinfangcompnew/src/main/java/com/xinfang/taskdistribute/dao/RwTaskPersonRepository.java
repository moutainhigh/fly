package com.xinfang.taskdistribute.dao;

import com.xinfang.taskdistribute.model.RwTaskPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-01 17:06
 **/
public interface RwTaskPersonRepository extends JpaRepository<RwTaskPerson,Integer>{

    @Query("select taskId from RwTaskPerson where ryId = ?1")
    List<Integer> findTaskIdByRyId(Integer ryId);

    @Query("select taskId from RwTaskPerson where ryId in (?1)")
    List<Integer> findTaskIdByRyIdSet(Set<Integer> ryIdSet);

    @Query("select taskId from RwTaskPerson where ryId = ?1 and taskFlag in (?2)")
    List<Integer> findTaskIdByRyIdAndTaskFlag(Integer ryId, Set<Integer> taskFlag);

    @Query("from RwTaskPerson where taskId = ?1 and ryId = ?2")
    List<RwTaskPerson> findByTaskIdAndRyId(Integer taskId, Integer ryId);

    @Query("from RwTaskPerson where taskId = ?1")
    Set<RwTaskPerson> findByTaskId(Integer taskId);

    @Query("from RwTaskPerson where taskId = ?1 and ryId = ?2")
    RwTaskPerson findByTaskIdAndCreatePersonId(Integer taskId, Integer createPersonId);

    @Query("select taskFlag from RwTaskPerson where taskId = ?1 group by taskFlag")
    List<Integer> findTaskFlagByTaskId(Integer taskId);

    @Modifying
    @Transactional
    @Query("delete from RwTaskPerson where taskId = ?1 and ryId = ?2")
    Integer deleteByTaskIdAndRyId(Integer taskId, Integer ryId);

    @Modifying
    @Transactional
    @Query("delete from RwTaskPerson where taskId = ?1")
    Integer deleteByTaskId(Integer taskId);
}
