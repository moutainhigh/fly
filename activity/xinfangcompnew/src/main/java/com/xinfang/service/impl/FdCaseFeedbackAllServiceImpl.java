package com.xinfang.service.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xinfang.dao.FdCaseFeedbackAllRepository;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FdCaseFeedbackAll;
import com.xinfang.service.FdCaseFeedbackAllService;

@Repository("fdCaseFeedbackAllService")
@Transactional
public class FdCaseFeedbackAllServiceImpl implements FdCaseFeedbackAllService {
	@Autowired
	FdCaseFeedbackAllRepository fdCaseFeedbackAllRepository;

	@Override
	public ArrayList<FdCaseFeedbackAll> findCaseFeedbackAllById(Integer caseId) {
		ArrayList<FdCaseFeedbackAll> fdCaseFeedbackAll = null;
		try {
			fdCaseFeedbackAll = fdCaseFeedbackAllRepository
					.findCaseFeedbackAllById(caseId);			
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return fdCaseFeedbackAll;
		}

		return fdCaseFeedbackAll;
	}

	@Override
	public Date getlastUpdateTime(Integer caseId) {
		Date result = fdCaseFeedbackAllRepository.getlastUpdateTime(caseId);
		return result;
	}

}
