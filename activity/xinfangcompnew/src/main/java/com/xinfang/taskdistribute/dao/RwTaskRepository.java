package com.xinfang.taskdistribute.dao;

import com.xinfang.taskdistribute.model.RwTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-01 16:05
 **/
public interface RwTaskRepository extends JpaRepository<RwTask, Integer>, JpaSpecificationExecutor<RwTask> {


    @Query("select createPersonId from RwTask where taskId = ?1")
    Integer findCreatePersonIdByTaskId(Integer taskId);

    @Modifying
    @Transactional
    @Query("delete from RwTask where taskId = ?1")
    Integer deleteByTaskId(Integer taskId);

    @Modifying
    @Transactional
    @Query("update RwTask set isFlag = ?3 where taskId = ?1 and createPersonId = ?2")
    Integer updateTaskState(Integer taskId, Integer createrId, Integer isFlag);
    
    @Modifying
    @Transactional
    @Query("update RwTask set isTop = ?2 where taskId = ?1")
    Integer updateTaskTop(Integer taskId, Byte isTop);
}
