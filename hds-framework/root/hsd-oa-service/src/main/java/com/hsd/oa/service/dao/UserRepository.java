package com.hsd.oa.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hsd.oa.service.entity.FcRyb;


/**
 * 
     * 此类描述的是:
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:46:25
 */
public interface UserRepository extends JpaRepository<FcRyb, Integer>{
	
    

    @Query("from FcRyb u where u.ryMc=:name")
    FcRyb findUser(@Param("name") String name);
    
    @Query("from FcRyb u where u.ryId=:id")
    FcRyb findById(@Param("id") Integer id);



}
