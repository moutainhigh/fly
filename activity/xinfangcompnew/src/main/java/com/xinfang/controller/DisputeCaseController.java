package com.xinfang.controller;

import com.xinfang.context.PageFinder;
import com.xinfang.context.Response;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.model.DisputeCaseEntity;
import com.xinfang.service.DisputeCaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by sunbjx on 2017/5/17.
 */
@RestController
@RequestMapping("/api/v1/case/dispute")
@Api(description = "投诉办理-矛盾纠纷基础信息(孙帮雄)")
public class DisputeCaseController {

	@Autowired
	private DisputeCaseService disputeCaseService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ApiOperation(value = "保存案件信息", notes = "保存案件信息")
	public Response save(@ModelAttribute DisputeCaseEntity fdCase,
			@ApiParam(name = "strCaseTime", value = "事件时间") @RequestParam(required = false, defaultValue = "") String strCaseTime) {

		int result = disputeCaseService.save(fdCase, strCaseTime);
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

		DisputeCaseEntity fdCase = null;
		try {
			fdCase = disputeCaseService.getById(caseId);
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

		DisputeCaseEntity fdCase = null;
		try {
			fdCase = disputeCaseService.getByPetitionCode(petitionCode);
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

		int result = disputeCaseService.updateCaseHandleStateById(userId, caseHandleState, caseId);
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

		int result = disputeCaseService.updatePetitionNumbersById(caseId, petitionNumbers);
		if (result == 0) {
			return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE);
		}
		return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE);
	}

	@RequestMapping(value = "/handle", method = RequestMethod.POST)
	@ApiOperation(value = "案件处理", notes = "案件处理\n" + "0  表示程序错误\n" + "-1  表示type值不对 禁止访问\n" + "1  表示请求操作成功")
	public Response handle(@ApiParam(name = "disputeCaseId", value = "矛盾纠纷案件ID") @RequestParam Integer disputeCaseId,
			@ApiParam(name = "type", value = "案件处理类型  4 直接回复, 7 存件, 14 转为信访件") @RequestParam int type,
			@ApiParam(name = "note", value = "案件处理说明") @RequestParam(required = false, defaultValue = "") String note,
			@ApiParam(name = "userId", value = "当前登陆用户ID") @RequestParam Integer userId,
			@ApiParam(name = "replyFileSrc", value = "直接回复附件") @RequestParam(required = false, defaultValue = "") String replyFileSrc,
			@ApiParam(name = "otherFileSrc", value = "其它附件") @RequestParam(required = false, defaultValue = "") String otherFileSrc,
			@ApiParam(name = "flagPetitionUnit", value = "信访局类别") @RequestParam(required = false, defaultValue = "0") int flagPetitionUnit) {

		int result = disputeCaseService.handleDisputeCase(disputeCaseId, type, note, userId, replyFileSrc, otherFileSrc,
				flagPetitionUnit);

		if (result == 0)
			return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE);
		if (result == -1)
			return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_BAN,
					ResponseConstants.CODE_BAN_VALUE);

		return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE);
	}

	@RequestMapping(value = "/query/warning", method = RequestMethod.POST)
	@ApiOperation(value = "矛盾纠纷案件预警查询", notes = "矛盾纠纷案件预警查询")
	public Responses warningQuery(
			@ApiParam(name = "handleType", value = "处理类型 1 转到区信访局 2 转到市信访局 3 转到调度指挥 4 直接回复 5 存件") @RequestParam(required = false, defaultValue = "0") byte handleType,
			@ApiParam(name = "handState", value = "处理状态 0 待处理 1 处理中 2 已办结") @RequestParam(required = false, defaultValue = "0") int handState,
			@ApiParam(name = "countyCityId", value = "区县市ID") @RequestParam(required = false, defaultValue = "0") Integer countyCityId,
			@ApiParam(name = "fuzzy", value = "模糊查询字符") @RequestParam(required = false, defaultValue = "") String fuzzy,
			@ApiParam(name = "time_progress", value = "预警状态 0.5 正常 0.75 预警 0.85 警告 0.99 严重警告 1 超期") @RequestParam(required = false, defaultValue = "") String time_progress,
			@ApiParam(name = "startTime", value = "开始时间(YYYY-MM-DD)") @RequestParam(required = false, defaultValue = "") String startTime,
			@ApiParam(name = "endTime", value = "结束时间(YYYY-MM-DD)") @RequestParam(required = false, defaultValue = "") String endTime,
			@ApiParam(name = "startPage", value = "起始页(必须大于等于 1 )") @RequestParam(required = false, defaultValue = "1") int startPage,
			@ApiParam(name = "pageSize", value = "每页数量") @RequestParam(required = false, defaultValue = "10") int pageSize) {

		PageFinder<Map<String, Object>> disputeCase = null;
		try {
			disputeCase = disputeCaseService.warningQuery(handleType, handState, countyCityId, fuzzy, time_progress,
					startTime, endTime, startPage, pageSize);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, new ArrayList<>());
		}
		if (null == disputeCase)
			return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
					ResponseConstants.CODE_SUCCESS_VALUE, new ArrayList<>());

		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, disputeCase);
	}
}
