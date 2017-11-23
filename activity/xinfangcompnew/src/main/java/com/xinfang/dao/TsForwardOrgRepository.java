package com.xinfang.dao;

import com.xinfang.model.TsForwardOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zemal-tan
 * @description  投诉转发机构
 * @create 2017-03-31 9:37
 **/
public interface TsForwardOrgRepository extends JpaRepository<TsForwardOrg, Integer> {

    @Query("select org.orgId from TsForwardOrg org where org.userId = :ryId")
    List<Integer> findDwIdByRyId(@Param("ryId") Integer ryId);

    @Query("select new map(org.orgId as dwId,org.fcDwb.dwMc as dwMc,org.fcDwb.dwType as dwType) from TsForwardOrg org " +
            "where org.userId = :ryId order by org.fcDwb.dwType")
    List<Map> findDwInfoByRyId(@Param("ryId") Integer ryId);

    @Query("select new map(org.orgId as dwId,org.fcDwb.dwMc as dwMc,org.fcDwb.dwType as dwType) from TsForwardOrg org " +
            "where org.userId = :ryId and org.fcDwb.dwType in (:dwTypes) order by org.fcDwb.dwType")
    List<Map> findDwInfoByRyIdAndDwType(@Param("ryId") Integer ryId, @Param("dwTypes") List<Integer> dwTypes);

    @Query("from TsForwardOrg org where org.userId = :ryId")
    List<TsForwardOrg> findByRyId(@Param("ryId") Integer ryId);
}
