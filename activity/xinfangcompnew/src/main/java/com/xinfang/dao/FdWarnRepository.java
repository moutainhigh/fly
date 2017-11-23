package com.xinfang.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinfang.model.FdWarn;


public interface FdWarnRepository extends JpaRepository<FdWarn, Integer>,JpaSpecificationExecutor<FdWarn>{

}
