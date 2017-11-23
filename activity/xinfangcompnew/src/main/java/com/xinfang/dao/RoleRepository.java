package com.xinfang.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xinfang.model.FcJzb;

public interface RoleRepository extends JpaRepository<FcJzb, Integer>{

	@Query("from FcJzb r where r.jzId=:id")
	FcJzb findById(@Param("id") int id);
	
    

   

}
