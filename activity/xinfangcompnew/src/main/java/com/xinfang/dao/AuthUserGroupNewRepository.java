package com.xinfang.dao;

import com.xinfang.model.AuthUserGroupNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-28 9:48
 **/
public interface AuthUserGroupNewRepository extends JpaRepository<AuthUserGroupNew, Integer> {

    List<AuthUserGroupNew> findByUserId(Integer ryId);

    @Query("select new map(a.fcRybAllField.ryId as ryId, a.fcRybAllField.ryMc as ryMc) " +
            "from AuthUserGroupNew a where a.groupId = :groupId and a.fcRybAllField.dwId = :dwId")
    List<Map> findByDwIdAndRoleId(@Param("dwId") Integer dwId, @Param("groupId") Integer groupId);

    @Query("select a.fcRybAllField.ryId " +
            "from AuthUserGroupNew a where a.groupId = :groupId and a.fcRybAllField.ryId = :ryId")
    Integer findByRyIdAndRoleId(@Param("ryId") Integer ryId, @Param("groupId") Integer groupId);

    @Query("select new map(userId as userId, fcRybAllField.dwId as dwId) from AuthUserGroupNew " +
            "where groupId = :groupId and fcRybAllField.dwId is not null " +
            "group by userId")
    List<Map> findUserIdByGroupId(@Param("groupId") Integer groupId);
}
