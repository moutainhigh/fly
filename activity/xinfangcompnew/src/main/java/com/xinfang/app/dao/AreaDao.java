package com.xinfang.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xinfang.app.model.Area;

public interface AreaDao extends JpaRepository<Area, Integer>, JpaSpecificationExecutor<Area> {

	 @Query("from Area f where f.father=:id")
	List<Area> getAreaByCityId(@Param("id")String id);

}
