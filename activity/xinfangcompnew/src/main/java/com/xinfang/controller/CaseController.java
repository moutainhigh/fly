package com.xinfang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.context.Response;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.model.FdCase;
import com.xinfang.service.CaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 案件信息 Created by sunbjx on 2017/3/22.
 */
@RestController
@RequestMapping("/api/v1/case")
@Api(description = "投诉办理-案件基础信息(孙帮雄)")
public class CaseController {

	@Autowired
	private CaseService caseService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ApiOperation(value = "保存案件信息", notes = "保存案件信息")
	public Response save(@ModelAttribute FdCase fdCase,
			@ApiParam(name = "strCaseTime", value = "事件时间") @RequestParam(required = false, defaultValue = "") String strCaseTime) {

		int result = caseService.save(fdCase, strCaseTime);
		if (result == 0) {
			return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE);
		}
		return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE);
	}

	@RequestMapping(value = "/query/id", method = RequestMethod.POST)
	@ApiOperation(value = "通过案件ID查询案件信息", notes = "通过案件ID查询案件信息")
	public Responses getCaseById(@ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId) {

		FdCase fdCase = null;
		try {
			fdCase = caseService.getFdCaseById(caseId);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, fdCase);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, fdCase);
	}

	@RequestMapping(value = "/query/petitincode", method = RequestMethod.POST)
	@ApiOperation(value = "通过案件编码获取案件信息", notes = "通过案件编码获取案件信息")
	public Responses getCaseByPetitionCode(
			@ApiParam(name = "petitionCode", value = "案件编码") @RequestParam String petitionCode) {

		FdCase fdCase = null;
		try {
			fdCase = caseService.getCaseByPetitionCode(petitionCode);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, fdCase);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, fdCase);
	}

	@RequestMapping(value = "/update/casehandlestate", method = RequestMethod.POST)
	@ApiOperation(value = "通过案件ID更新案件状态", notes = "通过案件ID更新案件状态")
	public Response updateCaseHandleStateById(@ApiParam(name = "userId", value = "用户Id") @RequestParam Integer userId,
			@ApiParam(name = "caseHandleState", value = "案件状态") @RequestParam Integer caseHandleState,
			@ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId) {

		int result = caseService.updateCaseHandleStateById(userId, caseHandleState, caseId);
		if (result == 0) {
			return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE);
		}
		return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE);
	}

	@RequestMapping(value = "/update/handleduration", method = RequestMethod.POST)
	@ApiOperation(value = "通过案件ID 更新时长", notes = "通过案件ID 更新时长")
	public Response updateCaseHandleDurationById(
			@ApiParam(name = "userId", value = "用户Id") @RequestParam Integer userId,
			@ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId,
			@ApiParam(name = "handleDuration", value = "处理时长") @RequestParam Integer handleDuration) {

		int result = caseService.updateCaseHandleDurationById(userId, caseId, handleDuration);
		if (result == 0) {
			return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE);
		}
		return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE);
	}

	@RequestMapping(value = "/update/windownumber", method = RequestMethod.POST)
	@ApiOperation(value = "通过案件ID 更新分流室指定的窗口编号", notes = "通过案件ID 更新分流室指定的窗口编号")
	public Response updateWindowNumber(@ApiParam(name = "userId", value = "用户Id") @RequestParam Integer userId,
			@ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId,
			@ApiParam(name = "windowNumber", value = "窗口编号") @RequestParam Integer windowNumber,
			@ApiParam(name = "turnCaseDesc", value = "分流室指派窗口案件描述") @RequestParam String turnCaseDesc) {

		int result = caseService.updateWindowNumber(userId, caseId, windowNumber, turnCaseDesc);
		if (result == 0) {
			return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE);
		}
		return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE);
	}

	@RequestMapping(value = "/update/petitionnumbers", method = RequestMethod.POST)
	@ApiOperation(value = "通过案件ID 更新信访人数", notes = "通过案件ID 更新信访人数")
	public Response updatePetitionNumbers(@ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId,
			@ApiParam(name = "petitionNumbers", value = "信访人数") @RequestParam Integer petitionNumbers) {

		int result = caseService.updatePetitionNumbersByCaseId(caseId, petitionNumbers);
		if (result == 0) {
			return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE);
		}
		return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE);
	}

	@RequestMapping(value = "/dispatcher/appcase", method = RequestMethod.POST)
	@ApiOperation(value = "收文岗派送app案件", notes = "收文岗派送app案件")
	public Response updateSingleField(@ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId,
			@ApiParam(name = "handleUserId", value = "处理人ID") @RequestParam Integer handleUserId) {

		try {
			caseService.dispatcherAppCase(caseId, handleUserId);
			return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
					ResponseConstants.CODE_SUCCESS_VALUE);
		} catch (Exception e) {
			return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE);
		}

	}

	@RequestMapping(value = "/return/turn", method = RequestMethod.POST)
	@ApiOperation(value = "窗口退回到分流室", notes = "窗口退回到分流室\n<font color = red>results = 1 成功 0 已处理无法退回 -1 服务器异常</font>")
	public Responses returnToTurn(@ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId,
			@ApiParam(name = "doUserId", value = "申请退回人ID") @RequestParam Integer doUserId) {
		int result = 0;
		try {
			result = caseService.returnToTurn(caseId, doUserId);
			if (result == 1) {
				return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
						ResponseConstants.CODE_SUCCESS_VALUE, result);
			}

		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, -1);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_FAILED_VALUE, result);
	}

	@RequestMapping(value = "/return/auditlist", method = RequestMethod.POST)
	@ApiOperation(value = "窗口退回到分流室审核列表", notes = "窗口退回到分流室审核列表")
	public Responses listTurnAudit(@ApiParam(name = "creatorId", value = "分流室录入人ID") @RequestParam Integer creatorId) {
		List<FdCase> result = null;
		try {
			result = caseService.listTurnAudit(creatorId);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, -1);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_FAILED_VALUE, result);
	}

	@RequestMapping(value = "/return/audit", method = RequestMethod.POST)
	@ApiOperation(value = "窗口退回到分流室审核是否通过", notes = "窗口退回到分流室\n<font color = red>results = true 处理成功 false 服务器异常</font>")
	public Responses turnAudit(@ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId,
			@ApiParam(name = "isOk", value = "是否退回") @RequestParam Integer isOk,
			@ApiParam(name = "doUserId", value = "审核退回人ID") @RequestParam Integer doUserId) {
		boolean result = false;
		try {
			result = caseService.turnAudit(caseId, isOk, doUserId);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_FAILED_VALUE, result);
	}

}
