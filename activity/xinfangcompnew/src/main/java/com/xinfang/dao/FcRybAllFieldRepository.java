package com.xinfang.dao;

import com.xinfang.VO.FcRybTreeVO;
import com.xinfang.VO.PermissionPersonDetail;
import com.xinfang.model.FcRybAllField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xinfang.VO.PermissionHomePage;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-26 10:23
 **/
public interface FcRybAllFieldRepository extends JpaRepository<FcRybAllField, Integer> {

    @Query("select new com.xinfang.VO.FcRybTreeVO(" +
            "ryb.ryId, ryb.ryMc, case when (ryb.ryImg is null) or (ryb.ryImg = '') then ryb.rySfz else ryb.ryImg end , ryb.qxsId, ryb.fcQxsb.qxsMc, ryb.fcDwb.dwType, ryb.dwId, ryb.fcDwb.dwMc, ryb.ksId, ryb.fcKsb.ksMc) " +
            "from FcRybAllField ryb where ryId = :ryId")
    FcRybTreeVO findRybById(@Param("ryId") Integer ryId);

    @Query("select new com.xinfang.VO.FcRybTreeVO(" +
            "ryb.ryId, ryb.ryMc, case when (ryb.ryImg is null) or (ryb.ryImg = '') then ryb.rySfz else ryb.ryImg end , ryb.qxsId, ryb.fcQxsb.qxsMc, ryb.fcDwb.dwType, ryb.dwId, ryb.fcDwb.dwMc, ryb.ksId, ryb.fcKsb.ksMc) " +
            "from FcRybAllField ryb where ryId in (:ryIds) and  ryb.qyzt=1")
    List<FcRybTreeVO> findRybByIds(@Param("ryIds") List<Integer> ryIds);

    @Query("select new com.xinfang.VO.FcRybTreeVO(" +
            "ryb.ryId, ryb.ryMc, case when (ryb.ryImg is null) or (ryb.ryImg = '') then ryb.rySfz else ryb.ryImg end , ryb.qxsId, ryb.fcQxsb.qxsMc, ryb.fcDwb.dwType, ryb.dwId, ryb.fcDwb.dwMc, ryb.ksId, ryb.fcKsb.ksMc) " +
            "from FcRybAllField ryb where ryb.fcDwb.dwId = :dwId and ryId in (:ryIds) and  ryb.qyzt=1")
    List<FcRybTreeVO> findRybByIdsAndDwId(@Param("ryIds") List<Integer> ryIds, @Param("dwId") Integer dwId);

    @Query("select new com.xinfang.VO.FcRybTreeVO(" +
            "ryb.ryId, ryb.ryMc, case when (ryb.ryImg is null) or (ryb.ryImg = '') then ryb.rySfz else ryb.ryImg end , ryb.qxsId, ryb.fcQxsb.qxsMc, ryb.fcDwb.dwType, ryb.dwId, ryb.fcDwb.dwMc, ryb.ksId, ryb.fcKsb.ksMc) " +
            "from FcRybAllField ryb where ryb.fcDwb.dwId = :dwId and  ryb.qyzt=1")
    List<FcRybTreeVO> findRybByDwId(@Param("dwId") Integer dwId);

    @Query("select new com.xinfang.VO.FcRybTreeVO(" +
            "ryb.ryId, ryb.ryMc, case when (ryb.ryImg is null) or (ryb.ryImg = '') then ryb.rySfz else ryb.ryImg end , ryb.qxsId, ryb.fcQxsb.qxsMc, ryb.fcDwb.dwType, ryb.dwId, ryb.fcDwb.dwMc, ryb.ksId, ryb.fcKsb.ksMc) " +
            "from FcRybAllField ryb where ryb.qxsId = :qxsId and  ryb.qyzt=1")
    List<FcRybTreeVO> findRybByQxsId(@Param("qxsId") Integer qxsId);

    @Query("select new com.xinfang.VO.FcRybTreeVO(" +
            "ryb.ryId, ryb.ryMc, case when (ryb.ryImg is null) or (ryb.ryImg = '') then ryb.rySfz else ryb.ryImg end , ryb.qxsId, ryb.fcQxsb.qxsMc, ryb.fcDwb.dwType, ryb.dwId, ryb.fcDwb.dwMc, ryb.ksId, ryb.fcKsb.ksMc) " +
            "from FcRybAllField ryb where ryb.qyzt=1")
    List<FcRybTreeVO> findRybAll();

    @Modifying
    @Transactional
    @Query("update FcRybAllField f set f.qyzt = :status where f.ryId = :ryId")
    Integer updateRybStatus(@Param("ryId") Integer ryId, @Param("status") Integer status);


    // ==========================机构人员查询=================================
    @Query("select new com.xinfang.VO.PermissionHomePage(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.fcDwb.dwMc,f.dwId,f.fcKsb.ksMc,f.ksId,f.kszwb.kszwMc,f.xh,f.qyzt)" +
            " from FcRybAllField f where f.qxsId = :qxsId")
    List<PermissionHomePage> permissionListByQxsId(@Param("qxsId") Integer qxsId, Pageable page);

    @Query("select new com.xinfang.VO.PermissionHomePage(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.fcDwb.dwMc,f.dwId,f.fcKsb.ksMc,f.ksId,f.kszwb.kszwMc,f.xh,f.qyzt)" +
            " from FcRybAllField f where f.fcDwb.dwType = :dwTypeId and f.qxsId = :qxsId")
    List<PermissionHomePage> permissionListByDwTypeId(@Param("dwTypeId") Integer dwTypeId,
                                                      @Param("qxsId") Integer qxsId, Pageable page);

    @Query("select new com.xinfang.VO.PermissionHomePage(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.fcDwb.dwMc,f.dwId,f.fcKsb.ksMc,f.ksId,f.kszwb.kszwMc,f.xh,f.qyzt)" +
            " from FcRybAllField f where f.dwId = :dwId")
    List<PermissionHomePage> permissionListByDwId(@Param("dwId") Integer dwId, Pageable page);

    @Query("select new com.xinfang.VO.PermissionHomePage(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.fcDwb.dwMc,f.dwId,f.fcKsb.ksMc,f.ksId,f.kszwb.kszwMc,f.xh,f.qyzt)" +
            " from FcRybAllField f where f.ksId = :ksId")
    List<PermissionHomePage> permissionListByKsId(@Param("ksId") Integer ksId, Pageable page);

    @Query("select new com.xinfang.VO.PermissionHomePage(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.fcDwb.dwMc,f.dwId,f.fcKsb.ksMc,f.ksId,f.kszwb.kszwMc,f.xh,f.qyzt)" +
            " from FcRybAllField f where f.ryId = :ryId")
    List<PermissionHomePage> permissionListByRyId(@Param("ryId") Integer ryId);

    // =================PermissionPersonDetail=============
    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.checkPersonId," +
            "f.personBusiness, f.thePartyTime," +
            "f.logInName, f.password)" +
            " from FcRybAllField f where f.qxsId = :qxsId order by f.xh, f.cjsj")
    Page<PermissionPersonDetail> personListByQxsId(@Param("qxsId") Integer qxsId, Pageable page);

    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.checkPersonId," +
            "f.personBusiness, f.thePartyTime," +
            "f.logInName, f.password)" +
            " from FcRybAllField f where f.fcDwb.dwType = :dwTypeId " +
            "and f.qxsId = :qxsId order by f.xh, f.cjsj")
    Page<PermissionPersonDetail> personListByDwTypeId(@Param("dwTypeId") Integer dwTypeId,
                                                      @Param("qxsId") Integer qxsId, Pageable page);

    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.checkPersonId," +
            "f.personBusiness, f.thePartyTime," +
            "f.logInName, f.password)" +
            " from FcRybAllField f where f.dwId = :dwId order by f.xh, f.cjsj")
    Page<PermissionPersonDetail> personListByDwId(@Param("dwId") Integer dwId, Pageable page);

    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.checkPersonId," +
            "f.personBusiness, f.thePartyTime," +
            "f.logInName, f.password)" +
            " from FcRybAllField f where f.ksId = :ksId order by f.xh, f.cjsj")
    Page<PermissionPersonDetail> personListByKsId(@Param("ksId") Integer ksId, Pageable page);

    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.checkPersonId," +
            "f.personBusiness, f.thePartyTime," +
            "f.logInName, f.password)" +
            " from FcRybAllField f where f.zwId = :zwId order by f.xh, f.cjsj")
    Page<PermissionPersonDetail> personListByZwId(@Param("zwId") Integer zwId, Pageable page);

    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.checkPersonId," +
            "f.personBusiness, f.thePartyTime," +
            "f.logInName, f.password)" +
            " from FcRybAllField f where f.ryId = :ryId order by f.xh, f.cjsj")
    Page<PermissionPersonDetail> personListByRyId(@Param("ryId") Integer ryId, Pageable page);

    @Query("select new map(ryb.ryId, ryb.ryMc) from FcRybAllField ryb where ryb.ryId = :ryId")
    Map findRyNameAndIdById(@Param("ryId") Integer ryId);

    // ====================密码修改======================
    @Query("select ryb.logInName from FcRybAllField ryb where ryb.ryId = :ryId")
    String findLogInNameByRyId(@Param("ryId") Integer ryId);

    @Query("select ryb.ryId from FcRybAllField ryb where ryb.logInName = :logInName")
    List<Integer> findLogInNameByLogInName(@Param("logInName") String logInName);

    @Query("select ryb.password from FcRybAllField ryb where ryb.ryId = :ryId")
    String findPasswordByRyId(@Param("ryId") Integer ryId);

    @Modifying
    @Transactional
    @Query("update FcRybAllField ryb set ryb.logInName = :newUsername " +
            "where ryb.ryId = :ryId and ryb.password = :password")
    Integer updateUsername(@Param("ryId") Integer ryId, @Param("password") String password,
                           @Param("newUsername") String newUsername);

    @Modifying
    @Transactional
    @Query("update FcRybAllField ryb set ryb.password = :newPassword " +
            "where ryb.ryId = :ryId and ryb.password = :password")
    Integer updatePassword(@Param("ryId") Integer ryId, @Param("password") String password,
                           @Param("newPassword") String newPassword);

    @Modifying
    @Transactional
    @Query("update FcRybAllField ryb set ryb.logInName = :newUsername, ryb.password = :password " +
            "where ryb.ryId = :ryId and ryb.password = :password")
    Integer updateUsernameAndPassword(@Param("ryId") Integer ryId, @Param("password") String password,
                                      @Param("newUsername") String newUsername);

    @Query("select ryMc from FcRybAllField where ryId = :ryId")
    String findRyMcByRyId(@Param("ryId") Integer ryId);

    @Query("select f.ryMc From FcRybAllField f where f.ryId=:ryId")
    String getRyNameById(@Param("ryId") Integer ryId);

    @Query("select f.checkPersonId From FcRybAllField f where f.ryId=:ryId")
    Integer getCheckPersonIdByRyId(@Param("ryId") Integer ryId);

    // ==============================暂时条件过滤  名字==================================
    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.checkPersonId," +
            "f.personBusiness, f.thePartyTime, f.logInName)" +
            " from FcRybAllField f where f.qxsId = :qxsId and " +
            "(f.rySfz like CONCAT('%',:search,'%') or f.ryMc like CONCAT('%',:search,'%') " +
            "or f.logInName like CONCAT('%',:search,'%'))" +
            "order by f.xh, f.cjsj")
    Page<PermissionPersonDetail> personListFilterByQxsId(@Param("qxsId") Integer qxsId,
                                                         @Param("search") String search, Pageable page);

    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.checkPersonId," +
            "f.personBusiness, f.thePartyTime, f.logInName)" +
            " from FcRybAllField f where f.fcDwb.dwType = :dwTypeId and " +
            "(f.ryMc like CONCAT('%',:search,'%') or f.rySfz like CONCAT('%',:search,'%') " +
            "or f.logInName like CONCAT('%',:search,'%')) " +
            "order by f.xh, f.cjsj")
    Page<PermissionPersonDetail> personListFilterByDwTypeId(@Param("dwTypeId") Integer dwTypeId,
                                                            @Param("search") String search, Pageable page);

    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.checkPersonId," +
            "f.personBusiness, f.thePartyTime, f.logInName)" +
            " from FcRybAllField f where f.dwId = :dwId and " +
            "(f.ryMc like CONCAT('%',:search,'%') or f.rySfz like CONCAT('%',:search,'%') " +
            "or f.logInName like CONCAT('%',:search,'%')) " +
            " order by f.xh, f.cjsj")
    Page<PermissionPersonDetail> personListFilterByDwId(@Param("dwId") Integer dwId,
                                                        @Param("search") String search, Pageable page);

    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.checkPersonId," +
            "f.personBusiness, f.thePartyTime, f.logInName)" +
            " from FcRybAllField f where f.ksId = :ksId and " +
            "(f.ryMc like CONCAT('%',:search,'%') or f.rySfz like CONCAT('%',:search,'%') " +
            "or f.logInName like CONCAT('%',:search,'%')) " +
            "order by f.xh, f.cjsj")
    Page<PermissionPersonDetail> personListFilterByKsId(@Param("ksId") Integer ksId,
                                                        @Param("search") String search, Pageable page);

    /**
     * 所有人员列表  f.kszwb.parentId 为职务id！！！！，该函数仅用于更新单位表的checkPersonId字段
     **/
    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.kszwb.parentId," +
            "f.personBusiness, f.thePartyTime, f.logInName)" +
            " from FcRybAllField f order by f.xh, f.cjsj")
    Set<PermissionPersonDetail> personList();

    @Modifying
    @Transactional
    @Query("update FcRybAllField f set f.checkPersonId = :checkPersonId where f.ryId = :ryId " +
            "and (f.checkPersonId is null or f.checkPersonId = -1 or f.checkPersonId = 0)")
    Integer updateCheckPersonIdByRyId(@Param("ryId") Integer ryId, @Param("checkPersonId") Integer checkPersonId);


    @Query("select ryb.ryId from FcRybAllField ryb where ryb.zwId = :zwId")
    List<Integer> findRyIdByZwId(@Param("zwId") Integer zwId);

    // ==============================权限================================
    @Query("select ryb.ryId " +
            "from FcRybAllField ryb where ryb.ksId = " +
            "(select rybInner.ksId from FcRybAllField rybInner where rybInner.ryId = :ryId)")
    List<Integer> findKsRyIdsRybById(@Param("ryId") Integer ryId);

    @Query("select ryb.ryId " +
            "from FcRybAllField ryb where ryb.zwId in (:zwIds) group by ryb.ryId")
    List<Integer> findZwRyIdsByZwIds(@Param("zwIds") List<Integer> zwIds); // 职位下所有人员

    @Query("select ryb.zwId " +
            "from FcRybAllField ryb where ryb.ryId  = :ryId")
    Integer findZwIdByRyId(@Param("ryId") Integer ryId);

    @Query("select ryb.ryId " +
            "from FcRybAllField ryb where ryb.dwId  = :dwId")
    List<Integer> findRyIdByDwId(@Param("dwId") Integer dwId);

    // ==============================================
    @Query("select ryb.dwId " +
            "from FcRybAllField ryb where ryb.ryId  = :ryId")
    Integer findDwIdByRyId(@Param("ryId") Integer ryId);

    @Query("select ryb.rySfz " +
            "from FcRybAllField ryb where ryb.rySfz  = :rySfz")
    List<String> findSfzByRySfz(@Param("rySfz") String rySfz);

    @Query("select ryb.rySfz " +
            "from FcRybAllField ryb where ryb.rySfz  = :rySfz and ryb.ryId != :ryId")
    List<String> findSfzByRySfzAndNotInRyId(@Param("rySfz") String rySfz, @Param("ryId") Integer ryId);

    @Query("select ryb.logInName " +
            "from FcRybAllField ryb where ryb.logInName  = :logInName")
    List<String> findLogInNameRyLogInName(@Param("logInName") String logInName);

    @Query("select ryb.logInName " +
            "from FcRybAllField ryb where ryb.logInName  = :logInName and ryb.ryId != :ryId")
    List<String> findLogInNameRyLogInNameAndNotInRyId(@Param("logInName") String logInName, @Param("ryId") Integer ryId);

    @Query("from FcRybAllField ryb where ryb.ryId  = :ryId")
    List<FcRybAllField> findByRyId(@Param("ryId") Integer ryId);
    @Query("select new com.xinfang.VO.PermissionPersonDetail(" +
            "f.ryId,f.ryMc, case when (f.ryImg is null) or (f.ryImg = '') then f.rySfz else f.ryImg end ," +
            "f.ryXb,f.ryJg,f.qxsId,f.fcQxsb.qxsMc," +
            "f.dwId,f.fcDwb.dwMc,f.fcDwb.dwType," +
            "f.ksId,f.fcKsb.ksMc,f.kszwb.kszwId,f.kszwb.kszwMc,f.kszwb.professionLevel,f.xh,f.qyzt," +
            "f.zzmmId, f.mzId, f.xlId, f.rySfz, f.rySjh, f.ryCsrq, f.ryEmail," +
            "f.ryXfgzsj,f.checkPersonId," +
            "f.personBusiness, f.thePartyTime, f.logInName)" +
            " from FcRybAllField f where (f.rySfz like CONCAT('%',:search,'%') or f.ryMc like CONCAT('%',:search,'%') " +
            "or f.logInName like CONCAT('%',:search,'%'))" +
            "order by f.xh, f.cjsj")
    Page<PermissionPersonDetail> personRybFuzzyQueryByRyMc(@Param("search") String search,Pageable page);
}


//            "(case when (f.kszwb.parentId is not null) then " +
//            "(select c.ryMc from FcRybAllField c where c.ryId=f.kszwb.parentId) else " +
//            " '' end) ," +
