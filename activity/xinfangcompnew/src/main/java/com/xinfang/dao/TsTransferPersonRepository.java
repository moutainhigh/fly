package com.xinfang.dao;

import com.xinfang.VO.PermissionTransferPersonVO;
import com.xinfang.model.TsTransferPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-24 0:05
 **/
public interface TsTransferPersonRepository extends JpaRepository<TsTransferPerson, Integer> {

    /**
     * 通过单位id获取收文岗人员id
     * @param dwId
     * @return
     */
    @Query("select userId from TsTransferPerson where orgId=:dwId and officeId=null and caseTypeId=null")
    List<Integer> finSwgInfoByDwId(@Param("dwId") Integer dwId);

    /**
     * 获取所有可以转发的机构收文岗
     * @return
     */
    @Query("select orgId from TsTransferPerson where officeId=null group by orgId")
    List<Integer> findAllSwg();

    @Query("select T.id from TsTransferPerson T where T.orgId = :dwId and T.caseTypeId is null")
    List<Integer> findByOrgId(@Param("dwId") Integer dwId);

    @Query("select T.id from TsTransferPerson T where T.orgId = :dwId and T.caseTypeId = :caseTypeId")
    List<Integer> findByOrgIdAndCaseTypeId(@Param("dwId") Integer dwId, @Param("caseTypeId") Integer caseTypeId);

    @Query("select new com.xinfang.VO.PermissionTransferPersonVO(" +
            "T.userId, T.fcRybAllField.ryMc, T.caseTypeId) " +
            "from TsTransferPerson T where T.orgId = :dwId")
    List<PermissionTransferPersonVO> findTransferPersonVOByDwId(@Param("dwId") Integer dwId);

    @Query("select new map(T.userId as ryId, T.fcRybAllField.ryMc as ryName, T.caseTypeId as caseTypeId," +
            "case when (T.fcRybAllField.ryImg is null) or (T.fcRybAllField.ryImg = '') " +
            "then T.fcRybAllField.rySfz  else T.fcRybAllField.ryImg end as ryImg) from TsTransferPerson T " +
            "where T.orgId = :dwId and T.caseTypeId is not null")
    List<Map> findRyInfoByDwId(@Param("dwId") Integer dwId); // 单位下接受 某案件类型的人员id

    /**
     * 通过单位id获取收文岗人员
     * @return
     */
    @Query("select new map(T.userId as ryId, T.fcRybAllField.ryMc as ryName, T.caseTypeId as caseTypeId," +
            "case when (T.fcRybAllField.ryImg is null) or (T.fcRybAllField.ryImg = '') " +
            "then T.fcRybAllField.rySfz  else T.fcRybAllField.ryImg end as ryImg, " +
            "T.fcRybAllField.dwId as dwId, T.fcRybAllField.fcDwb.dwMc as dwName) from TsTransferPerson T " +
            "where officeId=null and caseTypeId=null")
    List<Map> findDwRyInfo();

    @Query("select new map(T.userId as ryId, T.fcRybAllField.ryMc as ryName, -1 as caseTypeId," +
            "case when (T.fcRybAllField.ryImg is null) or (T.fcRybAllField.ryImg = '') " +
            "then T.fcRybAllField.rySfz  else T.fcRybAllField.ryImg end as ryImg, " +
            "T.fcRybAllField.dwId as dwId, T.fcRybAllField.fcDwb.dwMc as dwName) from TsTransferPerson T " +
            "where orgId =:dwId and officeId=null and caseTypeId=null")
    List<Map> findDwRyInfoByDwId(@Param("dwId") Integer dwId);
}
