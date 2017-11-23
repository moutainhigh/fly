package com.xinfang.taskdistribute.dao;

import com.xinfang.taskdistribute.model.RwTaskZfPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-03 10:26
 **/
public interface RwTaskZfPersonRepository extends JpaRepository<RwTaskZfPerson, Integer> {

    @Query("select taskId from RwTaskZfPerson where ryId = ?1")
    List<Integer> findTaskIdByRyId(Integer ryId);
}
