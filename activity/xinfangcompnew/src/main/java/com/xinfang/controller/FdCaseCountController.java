package com.xinfang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.VO.FdCaseCountVO;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.log.ApiLog;
import com.xinfang.service.FdCaseCountService;

@RestController
@RequestMapping("/api/v1/count")
@Api(description = "统计类(张波)")
public class FdCaseCountController {
	@Autowired
	private FdCaseCountService fdCaseCountService;

	@RequestMapping(value = "/caseCount", method = RequestMethod.GET)
	@ApiOperation(value = "案件限期办理监控统计", notes = "案件限期办理监控统计")
	public Responses caseCount(
			@ApiParam(name = "startTime", value = "起始时间") @RequestParam String startTime,
			@ApiParam(name = "endTime", value = "结束时间") @RequestParam  String endTime,
			@ApiParam(name = "casehandling",value="案件处理相关统计") @RequestParam Integer caseHandling,
			@ApiParam(name = "caseregistration ",value="案件登记相关统计") @RequestParam Integer caseregistration,
			@ApiParam(name = " way",value="案件处理相关统计") @RequestParam Integer way
			) {
		FdCaseCountVO fdCaseCount = null;
		try {
			fdCaseCount = fdCaseCountService.countCase(startTime,endTime);
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, fdCaseCount);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, fdCaseCount);
	}
//	@RequestMapping(value = "/caseCountBack", method = RequestMethod.GET)
//	@ApiOperation(value = "案件处理类型统计", notes = "案件处理类型统计")
//	public Responses caseCountBack(
//			@ApiParam(name = "startTime", value = "起始时间") @RequestParam String startTime,
//			@ApiParam(name = "endTime", value = "结束时间") @RequestParam String endTime) {
//		FdCaseCountVO fdCaseCount = null;
//		try {
//			fdCaseCount = fdCaseCountService.countCaseBack(startTime,endTime);
//		} catch (Exception e) {
//			ApiLog.chargeLog1(e.getMessage());
//			return new Responses(ResponseConstants.SUCCESS_FAILED,
//					ResponseConstants.CODE_FAILED,
//					ResponseConstants.CODE_FAILED_VALUE, JSONUtils.getJsonObject(fdCaseCount));
//		}
//		return new Responses(ResponseConstants.SUCCESS_OK,
//				ResponseConstants.CODE_SUCCESS,
//				ResponseConstants.CODE_SUCCESS_VALUE,JSONUtils.getJsonObject(fdCaseCount));
//	}
//	@RequestMapping(value = "/caseCountTime", method = RequestMethod.GET)
//	@ApiOperation(value = "案件时间监控统计", notes = "案件时间监控统计")
//	public Responses caseCountTime(
//			@ApiParam(name = "startTime", value = "起始时间") @RequestParam String startTime,
//			@ApiParam(name = "endTime", value = "结束时间") @RequestParam String endTime) {
//		FdCaseCountVO fdCaseCount = null;
//		try {
//			fdCaseCount = fdCaseCountService.countCaseTime(endTime);
//		} catch (Exception e) {
//			ApiLog.chargeLog1(e.getMessage());
//			return new Responses(ResponseConstants.SUCCESS_FAILED,
//					ResponseConstants.CODE_FAILED,
//					ResponseConstants.CODE_FAILED_VALUE, JSONUtils.getJsonObject(fdCaseCount));
//		}
//		return new Responses(ResponseConstants.SUCCESS_OK,
//				ResponseConstants.CODE_SUCCESS,
//				ResponseConstants.CODE_SUCCESS_VALUE,JSONUtils.getJsonObject(fdCaseCount));
//	}
//	@RequestMapping(value = "/caseCountBelongTo", method = RequestMethod.GET)
//	@ApiOperation(value = "案件归属地统计", notes = "案件归属地统计")
//	public Responses caseCountBelongTo(
//			@ApiParam(name = "startTime", value = "起始时间") @RequestParam String startTime,
//			@ApiParam(name = "endTime", value = "结束时间") @RequestParam String endTime) {
//		FdCaseCountVO fdCaseCount = null;
//		try {
//			fdCaseCount = fdCaseCountService.countCaseBelongTo(startTime, endTime);
//		} catch (Exception e) {
//			ApiLog.chargeLog1(e.getMessage());
//			return new Responses(ResponseConstants.SUCCESS_FAILED,
//					ResponseConstants.CODE_FAILED,
//					ResponseConstants.CODE_FAILED_VALUE, JSONUtils.getJsonObject(fdCaseCount));
//		}
//		return new Responses(ResponseConstants.SUCCESS_OK,
//				ResponseConstants.CODE_SUCCESS,
//				ResponseConstants.CODE_SUCCESS_VALUE,JSONUtils.getJsonObject(fdCaseCount));
//	}
}
