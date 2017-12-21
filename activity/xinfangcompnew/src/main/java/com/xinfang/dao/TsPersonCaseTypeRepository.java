package com.xinfang.dao;

import com.xinfang.model.TsPersonCaseTypeNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zemal-tan
 * @description 设置机构人员可以接收的信访件类型TS_PERSON_CASE_TYPE
 * @create 2017-03-27 15:29
 **/
public interface TsPersonCaseTypeRepository extends JpaRepository<TsPersonCaseTypeNew, Integer> {

    /**
     * @param typeIds  接收类型 1-22
     * @param dwId
     * @return
     */
    @Query("from TsPersonCaseTypeNew ts where ts.fcRybAllField.dwId=:dwId and ts.typeId in (:typeIds)")
    List<TsPersonCaseTypeNew> findByTypeId(@Param("typeIds") List<Integer> typeIds, @Param("dwId") Integer dwId);

    /**
     * 根据单位id 查询可以接收案件的人员id
     * @param dwId
     * @return
     */
    @Query("select ts.fcRybAllField.ryId from TsPersonCaseTypeNew ts where ts.fcRybAllField.dwId=:dwId group by ts.fcRybAllField.ryId")
    List<Integer> findRyIdByDwId(@Param("dwId") Integer dwId);

    @Query("select userId from TsPersonCaseTypeNew group by userId")
    List<Integer> findUserIdGroupByUserId();

    @Query("select userId from TsPersonCaseTypeNew where typeId in (:typeIds) group by userId")
    List<Integer> findUserIdByTypeIds(@Param("typeIds") List<Integer> typeIds);

    @Query("select typeId from TsPersonCaseTypeNew where userId=:ryId group by typeId")
    List<Integer> findTypeIdByRyId(@Param("ryId") Integer ryId);

    @Query("from TsPersonCaseTypeNew where userId=:ryId")
    List<TsPersonCaseTypeNew> findByRyId(@Param("ryId") Integer ryId);

    @Query("select typeId from TsPersonCaseTypeNew " +
            "where fcRybAllField.dwId = :dwId group by typeId having COUNT(typeId)>1")
    List<Integer> findTypeIdByDwId(@Param("dwId") Integer dwId);  // 人员数目大于1的接受类型

    @Query("select new map(typeId as typeId, fcRybAllField.ryId as ryId, fcRybAllField.ryMc as ryMc) " +
            "from TsPersonCaseTypeNew " +
            "where fcRybAllField.dwId = :dwId")
    List<Map> findTypeIdByDwId2(@Param("dwId") Integer dwId);

}
