package com.xinfang.taskdistribute.dao;

import com.xinfang.taskdistribute.model.RwTaskPersonGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-03 17:26
 **/
public interface RwTaskPersonGroupRepository extends JpaRepository<RwTaskPersonGroup, Integer> {

    Integer deleteByGroupId(Integer groupId);

    @Modifying
    @Transactional
    @Query("delete from RwTaskPersonGroup where groupId = ?1 and ryId in (?2)")
    Integer deleteByGroupIdAndRyIds(Integer groupId, Set<Integer> ryIdSet);

    @Query("select ryId from RwTaskPersonGroup where groupId = ?1 and ryId in (?2)")
    Set<Integer> findByGroupIdAndRyIds(Integer groupId, Set<Integer> ryIdSet);
}
