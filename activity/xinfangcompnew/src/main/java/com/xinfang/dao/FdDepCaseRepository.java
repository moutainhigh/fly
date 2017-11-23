package com.xinfang.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xinfang.model.FdDepCase;

public interface FdDepCaseRepository extends JpaRepository<FdDepCase, Long>, JpaSpecificationExecutor<FdDepCase>{
	
	public List<FdDepCase> findByCaseId(long id);
	
	public int countByCaseIdAndDepId(long caseId,int depId);
	
	public List<FdDepCase> findByCaseIdAndDepId(long caseId,int depId);
	
	  @Query("from FdDepCase u where u.caseId=:caseId and u.state=:state and u.type=:type")
	public List<FdDepCase> findByCaseIdAndType(@Param("caseId") long caseId,@Param("state") int state,@Param("type") int type);
	  
	  @Query("from FdDepCase u where u.caseId=:caseId and u.state=:state ")
		public List<FdDepCase> findByCaseIdAndState(@Param("caseId") long caseId,@Param("state") int state);
	  
	  @Query("from FdDepCase u where  u.state=:state ")
		public List<FdDepCase> findByState(@Param("state") int state);
	  
	  public void deleteByCaseId(Long caseId);
	  
	  

}
