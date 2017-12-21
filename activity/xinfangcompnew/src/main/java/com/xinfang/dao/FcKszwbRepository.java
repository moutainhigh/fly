package com.xinfang.dao;

import java.util.List;

import com.xinfang.model.FcKszwb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-27 9:56
 **/
public interface FcKszwbRepository extends JpaRepository<FcKszwb, Integer> {

    @Modifying
    @Transactional
    @Query("update FcKszwb f set f.qyzt = :status where f.kszwId = :kszwId")
    Integer updateKszwbStatus(@Param("kszwId") Integer kszwId, @Param("status") Integer status);
    @Query(value="select kszw_id From Fc_Kszwb where parent_id is null AND KS_ID = ANY (SELECT ks_id FROM fc_ksb where DW_ID =:dwId)",nativeQuery=true)
    Integer getZwById(@Param("dwId")Integer dwId);
   @Query("from FcKszwb f where f.ksId=:ksId")
   List<FcKszwb> getZwByKsId(@Param("ksId") Integer ksId);

    @Query("select f.parentId from FcKszwb f where f.kszwId=:zwId")
    Integer getParentIdByZwId(@Param("zwId") Integer zwId);

    @Query("select f.kszwId from FcKszwb f where f.parentId=:parentId")
    List<Integer> findZwIdByParentId(@Param("parentId") Integer parentId);
    
    @Modifying    
    @Transactional
    @Query(value="delete from fc_kszwb  where kszw_id = :id",nativeQuery=true)
    Integer deleteFcKszwb(@Param("id") Integer id);
}
