package com.xinfang.flowcontrol.controller;

import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.context.NewModel;

import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;

import com.xinfang.flowcontrol.service.FlowControlService;

/**
 * 
 * @author zhangbo
 * 
 */
@NewModel()
@RestController
@RequestMapping("/api/v1/flowcontrol")
@Api(description = "投诉办理-流程控制(张波)")
public class FlowControlController {
	@Autowired
	private FlowControlService flowControlService;

	@RequestMapping(value = "/flowControlCount", method = RequestMethod.GET)
	@ApiOperation(value = "流程控制统计", notes = "流程控制统计")
	public Responses flowControlCount(
			@ApiParam(name = "signUserId", value = "登录人ID") @RequestParam Integer signUserId,
			@ApiParam(name = "startTime", value = "起始时间") @RequestParam(required = false, defaultValue = "") String startTime,
			@ApiParam(name = "endTime", value = "结束时间") @RequestParam(required = false, defaultValue = "") String endTime,
			@ApiParam(name = "sponsorUnitId", value = "发起单位") @RequestParam(required = false, defaultValue = "0") Integer sponsorUnitId,
			@ApiParam(name = "handleUnitId", value = "处理单位") @RequestParam(required = false, defaultValue = "0") Integer handleUnitId) {
		Map<String, Object> map = null;
		try {
			map = flowControlService.flowControlCount(signUserId, startTime,
					endTime, sponsorUnitId, handleUnitId);
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

	@RequestMapping(value = "/caseDeatilList", method = RequestMethod.GET)
	@ApiOperation(value = "案件详情列表（已办结，办理中）", notes = "案件详情列表（已办结，办理中）")
	public Responses caseDeatilList(
			@ApiParam(name = "signUserId", value = "登录人ID") @RequestParam Integer signUserId,
			@ApiParam(name = "startTime", value = "起始时间") @RequestParam(required = false, defaultValue ="") String startTime,
			@ApiParam(name = "endTime", value = "结束时间") @RequestParam(required = false, defaultValue ="") String endTime,
			@ApiParam(name = "fuzzy", value = "模糊搜素（信访人，信访编号，案件归属地）") @RequestParam(required = false, defaultValue ="") String fuzzy,
			@ApiParam(name = "state", value = "状态选择（1.正常   2.预警   3.警告  4.严重警告）") @RequestParam(required = false, defaultValue = "0") Integer state,
			@ApiParam(name = "petitionWay", value = "信访方式") @RequestParam(required = false, defaultValue = "0") Integer petitionWay,
			@ApiParam(name = "type", value = "已办结 /2  办理中/1") @RequestParam Integer type,
			@ApiParam(name = "startPage", value = "起始页数") @RequestParam(required = false, defaultValue = "0") Integer startPage,
			@ApiParam(name = "pageCount", value = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer pageCount) {
		Map<String, Object> map = null;
		try {
			map = flowControlService.caseDeatilList(signUserId, fuzzy,
					startTime, endTime, state, petitionWay, type, startPage,
					pageCount);
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

	@RequestMapping(value = "/abnormalList", method = RequestMethod.GET)
	@ApiOperation(value = "异常列表统计", notes = "异常列表统计")
	public Responses abnormalList(
			@ApiParam(name = "signUserId", value = "登录人ID") @RequestParam Integer signUserId,
			@ApiParam(name = "type", value = "已办结 /2   办理中 /1") @RequestParam Integer type,
			@ApiParam(name = "startPage", value = "起始页数") @RequestParam(required = false, defaultValue = "0") Integer startPage,
			@ApiParam(name = "pageCount", value = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer pageCount) {
		Map<String, Object> map = null;
		try {
			map = flowControlService.abnormalList(signUserId, type, startPage,
					pageCount);
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
	
	
	@RequestMapping(value = "/getDeptListByUserId", method = RequestMethod.GET)
	@ApiOperation(value = "获取单位树形图", notes = "获取单位树形图")
	public Responses getDeptListByUserId(
			@ApiParam(name = "signUserId", value = "登录人ID") @RequestParam Integer signUserId
		){
		Map<String, Object> map = null;
		try {
			map = flowControlService.getDeptListByUserId(signUserId);
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
