package com.xinfang.dao;

import com.xinfang.model.FcDwb;
import org.springframework.data.jpa.repository.JpaRepository;
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
 * @create 2017-03-31 9:56
 **/
public interface FcDwbRepository extends JpaRepository<FcDwb, Integer> {

    @Query("select new map(dwId as id,dwMc as name) from FcDwb where dwId in (:dwIds)")
    List<Map> findDwMcAndDwIdByDwIds(@Param("dwIds") List<Integer> dwIds);
    
    @Query("select dwMc as name from FcDwb where dwId =:id")
    String findDwMcAndDwIdByDwId(@Param("id") Integer id);

    @Query("select new map(dwId as id, dwMc as name) from FcDwb where fcQxsb.qxsId = :qxsId")
    List<Map> findDwMcAndDwIdByQxsId(@Param("qxsId") Integer qxsId);

    @Query("select dwId from FcDwb where dwMc = :dwMc")
    Integer findDwIdByDwMc(@Param("dwMc") String dwMc);

    @Modifying
    @Transactional
    @Query("update FcDwb f set f.qyzt = :status where f.dwId = :dwId")
    Integer updateDwStatus(@Param("dwId") Integer dwId, @Param("status") Integer status);

    @Query("select dwId from FcDwb")
    List<Integer> findDwIds();

    @Query("select dwId from FcDwb where dwId in (:dwIds) group by dwId")
    Set<Integer> findDwIdsByDwIds(@Param("dwIds") Set<Integer> dwIds);

    @Query("select new map(qxsId as qxsId, dwId as dwId, dwMc as dwMc, dwType as dwType) from FcDwb where qxsId = :qxsId and qxsId is not null and qyzt = 1 order by xh")
    List<Map> findDwIdMcTypeByQxsId(@Param("qxsId") Integer qxsId);

    @Query("select new map(qxsId as qxsId, dwId as dwId, dwMc as dwMc, dwType as dwType) from FcDwb " +
            "where qxsId = :qxsId and dwId = :dwId and qxsId is not null")
    List<Map> findDwIdMcTypeByQxsIdAndDwId(@Param("qxsId") Integer qxsId, @Param("dwId") Integer dwId);

    @Query("select new map(qxsId as qxsId, dwId as dwId, dwMc as dwMc, dwType as dwType) from FcDwb where qxsId is not null  and qyzt = 1 order by xh")
    List<Map> findDwIdMcType();

    @Query("select new map(qxsId as qxsId, dwId as dwId, dwMc as dwMc, dwType as dwType) from FcDwb " +
            "where dwId = :dwId and qxsId is not null")
    List<Map> findDwIdMcTypeByDwId(@Param("dwId") Integer dwId);

    @Query("select new map(qxsId as qxsId, dwType as dwType) from FcDwb where qxsId = :qxsId and qxsId is not null and qyzt = 1 group by dwType, qxsId order by dwType,xh")
    List<Map> findDwTypeAndQxsIdByQxsId(@Param("qxsId") Integer qxsId);

    @Query("select new map(qxsId as qxsId, dwType as dwType) from FcDwb " +
            "where qxsId = :qxsId and dwId = :dwId and qxsId is not null group by dwType, qxsId")
    List<Map> findDwTypeAndQxsIdByQxsIdAndDwId(@Param("qxsId") Integer qxsId, @Param("dwId") Integer dwId);

    @Query("select new map(qxsId as qxsId, dwType as dwType) from FcDwb where qxsId is not null and qyzt = 1 group by dwType, qxsId order by dwType,xh")
    List<Map> findDwTypeAndQxsId();

    @Query("select new map(qxsId as qxsId, dwType as dwType) from FcDwb where dwId = :dwId and qxsId is not null group by dwType, qxsId")
    List<Map> findDwTypeAndQxsIdByDwId(@Param("dwId") Integer dwId);

    // ===============================================
    @Query("select new map(qxsId as qxsId, dwId as dwId, dwMc as dwMc, dwType as dwType) from FcDwb " +
            "where dwMc like concat('%','信访局','%') and dwId = :dwId and qxsId is not null")
    List<Map> isXFJByDwId(@Param("dwId") Integer dwId); // 判断单位是否为信访局
    @Modifying
    @Transactional
    @Query(value="delete from fc_dwb  where dw_id = :id",nativeQuery=true)
    Integer deleteFcDwb(@Param("id") Integer id);
    //===================流程控制==========
    @Query("From FcDwb where qxsId=:qxsId")
    List<FcDwb> getDwListByQxsId(@Param("qxsId") Integer qxsId);

    /*@Query("select new map(dwId as id, dwMc as name) from FcDwb where qxsId = ?1 " +
            "and dwMc LIKE CONCAT('%','信访局','%') " +
            "and qyzt = 1 order by dwId")*/

//    @Query("select new map(dwId as id,dwMc as name) from (select * from FcDwb where qxsId = ?1 " +
//            "and dwMc LIKE CONCAT('%','信访局','%') " +
//            "and qyzt = 1 " +
//            "order by dwId) t group by dwId "
//    )

    @Query("select new map(f.dwId as id, f.dwMc as name) from FcDwb f " +
            "where f.dwId = (select min(d.dwId) from FcDwb d " +
            "where d.qxsId =:qxsId " +
            "and d.qyzt=1 " +
            "and d.dwMc like CONCAT('%','信访局','%'))")
    Map findXfByQxsId(@Param("qxsId") Integer qxsId);
}
