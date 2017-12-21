package com.xinfang.supervise.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinfang.supervise.model.SuperviseDetail;

public interface SuperviseDetailDao extends JpaRepository<SuperviseDetail, Integer>, JpaSpecificationExecutor<SuperviseDetail>{
	
	List<SuperviseDetail> findByCaseId(Integer id);
	
	List<SuperviseDetail> findByIdIn(Integer[] ids);

}
