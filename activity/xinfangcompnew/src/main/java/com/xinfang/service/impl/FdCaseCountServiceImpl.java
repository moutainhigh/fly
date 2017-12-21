package com.xinfang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xinfang.VO.FdCaseCountVO;
import com.xinfang.dao.FdCaseCountRepository;
import com.xinfang.log.ApiLog;
import com.xinfang.service.FdCaseCountService;
import com.xinfang.utils.DateUtils;

/**
 * 
 * @author zhangbo
 * 
 */
@Service("fdCaseCountService")
@Transactional
public class FdCaseCountServiceImpl implements FdCaseCountService {
	@Autowired
	private FdCaseCountRepository fdCaseCountRepository;

	@Override
	public FdCaseCountVO countCase(String startTime, String endTime) {
		FdCaseCountVO fdCaseCount = new FdCaseCountVO();
		try {
			fdCaseCount.setNotHandled(fdCaseCountRepository
					.CountCasenotHandled(
							DateUtils.strToDate(startTime),
							DateUtils.strToDate(endTime)));
			fdCaseCount.setHandled(fdCaseCountRepository.CountCasehandled(
					DateUtils.strToDate(startTime),
					DateUtils.strToDate(endTime)));
			fdCaseCount.setLimitMore(fdCaseCountRepository
					.CountCaselimitMore(DateUtils.strToDate(endTime
							)));
			fdCaseCount.setOverdue(fdCaseCountRepository
					.CountCaseoverdue(DateUtils
							.strToDate(endTime)));
			fdCaseCount.setOverdueHandled(fdCaseCountRepository
					.CountCaseoverdueHandled(DateUtils.strToDate(endTime
							)));
			fdCaseCount.setLimitHandled(fdCaseCountRepository
					.CountCaselimitHandled(DateUtils.strToDate(endTime
							)));
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return fdCaseCount;
		}

		return fdCaseCount;
	}

	@Override
	public FdCaseCountVO countCaseBack(String startTime, String endTime) {
		FdCaseCountVO fdCaseCount = new FdCaseCountVO();
		try {
			fdCaseCount.setAssign(fdCaseCountRepository.countCaseBackAssign(
					DateUtils.strToDate(startTime),
					DateUtils.strToDate(endTime)));
			fdCaseCount.setReply(fdCaseCountRepository.countCaseBackReply(
					DateUtils.strToDate(startTime),
					DateUtils.strToDate(endTime)));
			fdCaseCount.setAllow(fdCaseCountRepository.countCaseBackAllow(
					DateUtils.strToDate(startTime),
					DateUtils.strToDate(endTime)));
			fdCaseCount.setRefuse(fdCaseCountRepository.countCaseBackRefuse(
					DateUtils.strToDate(startTime),
					DateUtils.strToDate(endTime)));
			fdCaseCount.setRefusel(fdCaseCountRepository.countCaseBackRefusel(
					DateUtils.strToDate(startTime),
					DateUtils.strToDate(endTime)));
			fdCaseCount.setPending(fdCaseCountRepository.countCaseBackPending(
					DateUtils.strToDate(startTime),
					DateUtils.strToDate(endTime)));
			fdCaseCount.setSave(fdCaseCountRepository.countCaseBackSave(
					DateUtils.strToDate(startTime),
					DateUtils.strToDate(endTime)));
			fdCaseCount.setTurn(fdCaseCountRepository.countCaseBackTurn(
					DateUtils.strToDate(startTime),
					DateUtils.strToDate(endTime)));
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return fdCaseCount;
		}
		return fdCaseCount;
	}

	@Override
	public FdCaseCountVO countCaseSituation(String startTime, String endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FdCaseCountVO countCaseState(String startTime, String endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FdCaseCountVO countCaseTime(String endTime) {
		FdCaseCountVO fdCaseCountVO = new FdCaseCountVO();
		try {
			fdCaseCountVO
					.setNormal(fdCaseCountRepository.countCaseNormal(DateUtils
							.strToDate(endTime)));
			fdCaseCountVO.setPre(fdCaseCountRepository.countCasePre(DateUtils
					.strToDate(endTime)));
			fdCaseCountVO.setWarning(fdCaseCountRepository
					.countCaseWarning(DateUtils
							.strToDate(endTime)));
			fdCaseCountVO.setSerious(fdCaseCountRepository
					.countCaseSerious(DateUtils
							.strToDate(endTime)));
			fdCaseCountVO.setSuperDate(fdCaseCountRepository
					.countCaseSuperDate(DateUtils.strToDate(endTime
							)));
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return fdCaseCountVO;
		}
		return fdCaseCountVO;
	}

	@Override
	public FdCaseCountVO countCaseBelongTo(String startTime, String endTime) {
		FdCaseCountVO fdCaseCountVO = new FdCaseCountVO();
		try {
			fdCaseCountVO.setGuanShanHu(fdCaseCountRepository
					.countCaseBelongToGuanShanHu(
							DateUtils.strToDate(startTime),
							DateUtils.strToDate(endTime)));
			fdCaseCountVO.setBaiYun(fdCaseCountRepository
					.countCaseBelongToBaiYun(
							DateUtils.strToDate(startTime),
							DateUtils.strToDate(endTime)));
			fdCaseCountVO.setEconomic(fdCaseCountRepository
					.countCaseBelongToEconomic(
							DateUtils.strToDate(startTime),
							DateUtils.strToDate(endTime)));
			fdCaseCountVO.setBond(fdCaseCountRepository.countCaseBelongToBond(
					DateUtils.strToDate(startTime),
					DateUtils.strToDate(endTime)));
			fdCaseCountVO.setHighTech(fdCaseCountRepository
					.countCaseBelongToHighTech(
							DateUtils.strToDate(startTime),
							DateUtils.strToDate(endTime)));
			fdCaseCountVO.setShuangLong(fdCaseCountRepository
					.countCaseBelongToShuangLong(
							DateUtils.strToDate(startTime),
							DateUtils.strToDate(endTime)));
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return fdCaseCountVO;
		}
		return fdCaseCountVO;
	}

	@Override
	public FdCaseCountVO countCase(String startTime, String endTime,
			Integer caseHandling, Integer caseregistration, Integer way) {
		
		FdCaseCountVO fdCaseCountVO = new FdCaseCountVO();
		try {
			if (caseHandling == 1 && caseregistration == null) {
				fdCaseCountVO = countCase(startTime, endTime);
			}
			if (caseHandling == 2 && caseregistration == null) {
				fdCaseCountVO = countCaseBack(startTime, endTime);
			}
			if(caseHandling==3 &&caseregistration==null){
				fdCaseCountVO =countCaseSituation(startTime, endTime);
			}
			if(caseHandling==4 &&caseregistration==null){
				fdCaseCountVO =countCaseState(startTime, endTime);
			}
			if(caseHandling==5 &&caseregistration==null){
				fdCaseCountVO =countCaseTime(endTime);
			}
			if(caseHandling==6 &&caseregistration==null){
				fdCaseCountVO =countCaseBelongTo(startTime,endTime);
			}
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return fdCaseCountVO;
		}

		return fdCaseCountVO;
	}

}
