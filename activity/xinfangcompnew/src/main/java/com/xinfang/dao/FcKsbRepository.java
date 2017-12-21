package com.xinfang.dao;

import com.xinfang.VO.FcRybTreeVO;
import com.xinfang.model.FcKsb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-27 9:57
 **/
public interface FcKsbRepository extends JpaRepository<FcKsb, Integer> {

    @Modifying
    @Transactional
    @Query("update FcKsb f set f.qyzt = :status where f.ksId = :ksId")
    Integer updateKsbStatus(@Param("ksId") Integer ksId, @Param("status") Integer status);

    @Query("select new com.xinfang.VO.FcRybTreeVO(" +
            "ksb.fcDwb.fcQxsb.qxsId, ksb.fcDwb.fcQxsb.qxsMc, ksb.fcDwb.dwType, ksb.fcDwb.dwId, " +
            "ksb.fcDwb.dwMc, ksb.ksId, ksb.ksMc) " +
            "from FcKsb ksb where ksb.fcDwb.fcQxsb.qxsId = :qxsId")
    List<FcRybTreeVO> findTreeByQxsId(@Param("qxsId") Integer qxsId);

    @Query("select new com.xinfang.VO.FcRybTreeVO(" +
            "ksb.fcDwb.fcQxsb.qxsId, ksb.fcDwb.fcQxsb.qxsMc, ksb.fcDwb.dwType, ksb.fcDwb.dwId, " +
            "ksb.fcDwb.dwMc, ksb.ksId, ksb.ksMc) " +
            "from FcKsb ksb")
    List<FcRybTreeVO> findTreeAll();

    @Query("From FcKsb f where f.dwId=:dwId")
    List<FcKsb> getKsByUnitId(@Param("dwId") Integer dwId);

    @Query("select new map(ksb.dwId as dwId, ksb.ksId as ksId, ksb.ksMc as ksMc) "+
            "from FcKsb ksb where ksb.qyzt=1 order by ksb.xh")
    List<Map> findKsIdAndName();

    @Query("select new map(ksb.dwId as dwId, ksb.ksId as ksId, ksb.ksMc as ksMc) "+
            "from FcKsb ksb where ksb.fcDwb.fcQxsb.qxsId = :qxsId and ksb.qyzt = 1 order by ksb.xh")
    List<Map> findKsIdAndNameByQxsId(@Param("qxsId") Integer qxsId);
    @Query("select dwId from FcKsb where ksId = :ksId")
    Integer getUnitIdByDepartmentId(@Param("ksId") Integer ksId);

    @Modifying
    @Transactional
    @Query(value="delete from fc_ksb where ks_id =:id",nativeQuery=true)
    Integer deleteFcKsb(@Param("id")Integer id);
}
