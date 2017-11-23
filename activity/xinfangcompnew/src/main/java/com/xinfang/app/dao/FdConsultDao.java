package com.xinfang.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinfang.app.model.FdConsult;

public interface FdConsultDao extends JpaRepository<FdConsult, Integer>, JpaSpecificationExecutor<FdConsult> {

}
