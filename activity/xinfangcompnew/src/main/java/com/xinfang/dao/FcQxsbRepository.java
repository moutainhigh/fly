package com.xinfang.dao;

import com.xinfang.model.FcQxsb;
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
 * @create 2017-04-06 10:17
 **/
public interface FcQxsbRepository extends JpaRepository<FcQxsb, Integer>{

    @Query("select qxsId from FcQxsb where qxsMc = :qxsMc")
    Integer findQxsIdByQxsMc(@Param("qxsMc") String qxsMc);
    @Query("select qxsId from FcQxsb")
    List<Integer> getQxsId();

   /* @Query(value="select * from fc_qxsb WHERE QXS_ID =(:qxsId)",nativeQuery=true)
    FcQxsb findFcQxsbById(@Param("qxsId") int qxsId);*/
   /* //获取区县市列表
    @Query(value="select * from Fc_Qxsb as q ORDER BY q.XH ASC limit :startPage,:pageCount",nativeQuery=true)
    List<FcQxsb> getQxsList(@Param("startPage") Integer startPage,@Param("pageCount") Integer pageCount);
    //获取区县市记录数
    @Query(value="select count(*) from Fc_Qxsb",nativeQuery=true)
    int getQxsCount();*/

    @Modifying
    @Transactional
    @Query("update FcQxsb f set f.qyzt = :status where f.qxsId = :qxsId")
    Integer updateQxsStatus(@Param("qxsId") Integer qxsId, @Param("status") Integer status);

    @Query("select new map(f.qxsId as qxsId, f.qxsMc as qxsMc) from FcQxsb f where f.qyzt=1 order by f.xh")
    List<Map> findQxsIdAndName();

    @Query("select new map(f.qxsId as qxsId, f.qxsMc as qxsMc) from FcQxsb f where f.qxsId = :qxsId order by f.xh")
    List<Map> findQxsIdAndNameByQxsId(@Param("qxsId") Integer qxsId);
    @Modifying
    @Transactional
    @Query(value="delete from fc_qxsb where qxs_id =:id",nativeQuery=true)
    Integer deleteFcQxsb(@Param("id")Integer id);

}
