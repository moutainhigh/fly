package com.xinfang.dao;

import com.xinfang.VO.AuthUserInfo;
import com.xinfang.model.AuthUserGroupNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-30 15:52
 **/
public interface AuthUserGroupRepository extends JpaRepository<AuthUserGroupNew, Integer> {

//    @Query("select new com.xinfang.VO.AuthUserInfo(a.userId, a.fcRybAllField.ryMc, a.groupId, a.authGroup.name) " +
//            "from AuthUserGroup as a where a.authGroup.id = :groupId")
//    List<AuthUserInfo> findAuthUserInfoByGroupId(@Param("groupId") Integer groupId);
//
//
//    @Query("select new com.xinfang.VO.AuthUserInfo(a.userId, a.fcRybAllField.ryMc, a.groupId, a.authGroup.name) " +
//            "from AuthUserGroup as a where a.userId = :ryId")
//    List<AuthUserInfo> findAuthUserInfoByRyId(@Param("ryId") Integer ryId);

//    @Query("select new com.xinfang.VO.AuthUserInfo(a.userId, a.fcRybAllField.ryMc, a.groupId, a.authGroup.name) " +
//            "from AuthUserGroup as a where a.userId = :ryId and a.authGroup.id = :groupId")
//    AuthUserInfo findAuthUserInfoByRyIdAndGroupId(@Param("ryId") Integer ryId, @Param("groupId") Integer groupId);

    @Query("select new com.xinfang.VO.AuthUserInfo(a.userId, a.fcRybAllField.ryMc, a.groupId, '') " +
            "from com.xinfang.model.AuthUserGroupNew as a where a.authGroupNew.id = :groupId")
    List<AuthUserInfo> findAuthUserInfoByGroupId(@Param("groupId") Integer groupId);

    @Query("select new com.xinfang.VO.AuthUserInfo(a.userId, a.fcRybAllField.ryMc, a.groupId, '') " +
            "from com.xinfang.model.AuthUserGroupNew as a where a.userId = :ryId")
    List<AuthUserInfo> findAuthUserInfoByRyId(@Param("ryId") Integer ryId);

    @Query("select new com.xinfang.VO.AuthUserInfo(a.userId, a.fcRybAllField.ryMc, a.groupId, '') " +
            "from com.xinfang.model.AuthUserGroupNew as a where a.userId = :ryId and a.authGroupNew.id = :groupId")
    AuthUserInfo findAuthUserInfoByRyIdAndGroupId(@Param("ryId") Integer ryId, @Param("groupId") Integer groupId);
}
