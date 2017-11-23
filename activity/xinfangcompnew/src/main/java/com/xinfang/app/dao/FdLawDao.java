package com.xinfang.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinfang.model.LawRulesEntity;

public interface FdLawDao extends JpaRepository<LawRulesEntity, Integer>, JpaSpecificationExecutor<LawRulesEntity> {

}
