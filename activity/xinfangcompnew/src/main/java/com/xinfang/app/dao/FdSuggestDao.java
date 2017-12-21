package com.xinfang.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinfang.app.model.FdSuggest;

public interface FdSuggestDao extends JpaRepository<FdSuggest, Integer>, JpaSpecificationExecutor<FdSuggest> {

}
