package com.xinfang.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xinfang.app.model.City;

public interface CityDao extends JpaRepository<City, Integer>, JpaSpecificationExecutor<City> {

	 @Query("from City f where f.father=:id")
	List<City> getCityByProvinceId(@Param("id")String id);

}
