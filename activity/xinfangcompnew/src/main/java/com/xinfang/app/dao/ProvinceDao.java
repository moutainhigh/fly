package com.xinfang.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinfang.app.model.Province;

public interface ProvinceDao extends JpaRepository<Province, Integer>, JpaSpecificationExecutor<Province> {

}
