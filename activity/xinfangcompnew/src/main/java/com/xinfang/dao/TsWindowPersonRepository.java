package com.xinfang.dao;

import java.util.List;

import com.xinfang.model.TsWindowPerson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-28 15:23
 **/
public interface TsWindowPersonRepository extends JpaRepository<TsWindowPerson, Integer> {

    @Query("from TsWindowPerson T where T.userId = :ryId")
    TsWindowPerson findByUserId(@Param("ryId") Integer ryId);

    /**
     * 通过用户ID查找窗口号
     * @param userId
     * @return
     */
    @Query("select T.windowId from TsWindowPerson T where T.userId =:userId")
    Integer getWindowByUserId(@Param("userId") Integer userId);
    
    @Query("from TsWindowPerson T where T.windowId = :windowId")
    List<TsWindowPerson> findByWindowId(@Param("windowId") Integer windowId);
    @Modifying
    @Transactional
    @Query(value="delete from ts_window_person where id =:id",nativeQuery=true)
    Integer deleteWindowById(@Param("id") Integer id);
    @Modifying
    @Transactional
    @Query(value="delete from ts_window_person where window_id =:id",nativeQuery=true)
    Integer deleteWindowByWindowId(@Param("id") Integer id);


}