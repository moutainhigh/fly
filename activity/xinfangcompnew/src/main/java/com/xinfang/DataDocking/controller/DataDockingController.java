package com.xinfang.DataDocking.controller;

import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.DataDocking.service.DataDockingInformationService;
import com.xinfang.context.NewModel;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;




@NewModel()
@RestController
@RequestMapping("/api/v1/DataDockingControl")
@Api(description = "接口对接(张波)")
public class DataDockingController {
	@Autowired
	private DataDockingInformationService dataDockingInformationService;
	
	
	@RequestMapping(value = "/getKeyPersonInformation", method = RequestMethod.GET)
	@ApiOperation(value = "获取重点人员信息列表", notes = "获取重点人员信息列表")
	public Responses getKeyPersonInformation(
			@ApiParam(name = "keyPersonId", value = "ID") @RequestParam(required = false, defaultValue = "0") Integer keyPersonId,
			@ApiParam(name = "startTime", value = "起始时间") @RequestParam(required = false, defaultValue = "") String startTime,
			@ApiParam(name = "endTime", value = "结束时间") @RequestParam(required = false, defaultValue = "") String endTime,
			@ApiParam(name = "startPage", value = "起始页数") @RequestParam(required = false, defaultValue = "0") Integer startPage,
			@ApiParam(name = "pageCount", value = "每页条数") @RequestParam(required = false, defaultValue = "0") Integer pageCount) {
		Map<String, Object> map = null;
		try {
			map = dataDockingInformationService.getKeyPersonInformation(startTime, endTime, keyPersonId, startPage, pageCount);
					
					
		} catch (Exception e) {
			System.err.println("Controller:" + e.getMessage());
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, map);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, map);
	}
	@RequestMapping(value = "/getContradictionInformation", method = RequestMethod.GET)
	@ApiOperation(value = "获取矛盾问题详细信息", notes = "获取矛盾问题详细信息")
	public Responses getContradictionInformation(
			@ApiParam(name = "keyPersonId", value = "ID") @RequestParam(required = false, defaultValue = "0") Integer keyPersonId,
			@ApiParam(name = "startTime", value = "起始时间") @RequestParam(required = false, defaultValue = "") String startTime,
			@ApiParam(name = "endTime", value = "结束时间") @RequestParam(required = false, defaultValue = "") String endTime,
			@ApiParam(name = "startPage", value = "起始页数") @RequestParam(required = false, defaultValue = "0") Integer startPage,
			@ApiParam(name = "pageCount", value = "每页条数") @RequestParam(required = false, defaultValue = "0") Integer pageCount) {
		Map<String, Object> map = null;
		try {
			map = dataDockingInformationService.getContradictionInformation(startTime, endTime, keyPersonId, startPage, pageCount);
					
					
		} catch (Exception e) {
			System.err.println("Controller:" + e.getMessage());
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, map);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, map);
	}
	
	@RequestMapping(value = "/getIntellgenceInformation", method = RequestMethod.GET)
	@ApiOperation(value = "获取情报信息列表", notes = "获取情报信息列表")
	public Responses getIntellgenceInformation(
			@ApiParam(name = "keyPersonId", value = "ID") @RequestParam(required = false, defaultValue = "0") Integer keyPersonId,
			@ApiParam(name = "startTime", value = "起始时间") @RequestParam(required = false, defaultValue = "") String startTime,
			@ApiParam(name = "endTime", value = "结束时间") @RequestParam(required = false, defaultValue = "") String endTime,
			@ApiParam(name = "startPage", value = "起始页数") @RequestParam(required = false, defaultValue = "0") Integer startPage,
			@ApiParam(name = "pageCount", value = "每页条数") @RequestParam(required = false, defaultValue = "0") Integer pageCount) {
		Map<String, Object> map = null;
		try {
			map = dataDockingInformationService.getIntellgenceInformation(startTime, endTime, keyPersonId, startPage, pageCount);
					
					
		} catch (Exception e) {
			System.err.println("Controller:" + e.getMessage());
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, map);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, map);
	}
}
