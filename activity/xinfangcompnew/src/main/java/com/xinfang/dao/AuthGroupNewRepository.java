package com.xinfang.dao;

import com.xinfang.model.AuthGroupNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-28 9:39
 **/
public interface AuthGroupNewRepository extends JpaRepository<AuthGroupNew, Integer>{

    @Query("from AuthGroupNew a where a.status=:status order by a.xh")
    List<AuthGroupNew> findAllPermission(@Param("status") Integer status);
}
