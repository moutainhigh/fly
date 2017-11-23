package com.xinfang.foursectionsdata.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xinfang.foursectionsdata.model.ExpertCase;

public interface ExpertCaseRepository extends JpaRepository<ExpertCase, Integer>{
	
	@Query("FROM ExpertCase  E WHERE E.id =:caseId")
	ExpertCase getExpertCaseById(@Param("caseId") Integer caseId);

}
