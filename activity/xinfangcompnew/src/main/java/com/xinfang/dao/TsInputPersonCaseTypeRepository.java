package com.xinfang.dao;

import com.xinfang.model.TsInputPersonCaseTypeNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-17 16:12
 **/
public interface TsInputPersonCaseTypeRepository extends JpaRepository<TsInputPersonCaseTypeNew, Integer> {

    @Query("select userId from TsInputPersonCaseTypeNew group by userId")
    List<Integer> findUserIdGroupByUserId();

    @Query("select type from TsInputPersonCaseTypeNew where userId=:ryId group by type")
    List<Integer> findTypeByRyId(@Param("ryId") Integer ryId);

    @Query("from TsInputPersonCaseTypeNew where userId=:ryId group by type")
    List<TsInputPersonCaseTypeNew> findByRyId(@Param("ryId") Integer ryId);

    @Query("from TsInputPersonCaseTypeNew where type < 100 order by type ")
    List<TsInputPersonCaseTypeNew> findAllOldType();
}
