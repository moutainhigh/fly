package com.xinfang.dao;

import com.xinfang.model.FdCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author tanzhouming
 * @description
 * @create 2017-03-21 18:11
 **/
public interface FdCodeRepository extends JpaRepository<FdCode, Integer>{

    @Query("from FdCode T WHERE T.type =:codeType and T.state=:state order by T.xh")
    List<FdCode> findByTypeAndState(@Param("codeType") int codeType, @Param("state") int state);

    @Query("from FdCode T WHERE T.type =:codeType and T.parentCode=:parentCode and T.state=:state order by T.xh")
    List<FdCode> findByTypeAndParentCodeAndState(@Param("codeType") int codeType, @Param("parentCode") int parentCode, @Param("state") int state);

    @Query("select name from FdCode T WHERE T.code =:code")
    String getNameByCode(@Param("code") Integer code);

    @Query("select new map(name as name, code as code) from FdCode T WHERE T.type IN (:types) and parentCode != -1")
    List<Map> getCodeAndNameByTypeFor1213(@Param("types") List<Integer> types);

    @Query("select new map(name as name, code as code) from FdCode T WHERE T.type = 7 and parentCode = -1")
    List<Map> getCodeNameByType();
}
