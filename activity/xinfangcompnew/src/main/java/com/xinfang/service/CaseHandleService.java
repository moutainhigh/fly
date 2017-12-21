package com.xinfang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinfang.dao.FdCaseFeedbackRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.model.FdCaseFeedback;
import com.xinfang.model.FdDepCase;
@Service("caseHandleService")
public class CaseHandleService {
	
	@Autowired
	FdCaseFeedbackRepository fdCaseFeedbackRepository;
	@Autowired
	FdDepCaseRepository fdDepCaseRepository;
	
	public int addFdDepCase(FdDepCase fdDepCase){
		
		return 0;
	}
	
	public int addFdCaseFeedback(FdCaseFeedback fdCaseFeedback){
		
		return 0;
	}
	
	public int updateFdDepCase(FdDepCase fdDepCase){
		
		
		return 0;
	}
	
	public int updateFdCaseFeedback(FdCaseFeedback fdCaseFeedback){
		
		return 0;
	}
	
	
	public List<FdCaseFeedback> getByCaseId(int caseId){
		
		return null;
	}
	

}
