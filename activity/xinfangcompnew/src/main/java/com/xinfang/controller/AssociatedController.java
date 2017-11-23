package com.xinfang.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.VO.HistoryCaseVO;
import com.xinfang.VO.LawRulesVO;
import com.xinfang.context.Response;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.service.CaseAssociatedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 关联信息 Created by sunbjx on 2017/3/22.
 */
@RestController
@RequestMapping("/api/v1/associated")
@Api(description = "投诉办理-案件关联信息(孙帮雄)")
public class AssociatedController {

	@Autowired
	private CaseAssociatedService caseAssociatedService;

	@RequestMapping(value = "/idcard2", method = RequestMethod.GET)
	@ApiOperation(value = "通过证件号码关联历史案件", notes = "通过证件号码关联历史案件")
	public Responses listByIdcard2(@ApiParam(name = "idcard", value = "证件号码") @RequestParam String idcard) {

		List<HistoryCaseVO> result = new ArrayList<HistoryCaseVO>();
		try {
			result = caseAssociatedService.listHistoryCaseByIdcard2(idcard);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/username2", method = RequestMethod.GET)
	@ApiOperation(value = "通过用户名关联历史案件", notes = "通过用户名关联历史案件")
	public Responses listByUsername2(@ApiParam(name = "username", value = "用户名") @RequestParam String username) {

		List<HistoryCaseVO> result = new ArrayList<HistoryCaseVO>();
		try {
			result = caseAssociatedService.listHistoryCaseByUsername2(username);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/idcard", method = RequestMethod.GET)
	@ApiOperation(value = "通过证件号码关联历史案件", notes = "通过证件号码关联历史案件")
	public Responses listByIdcard(@ApiParam(name = "idcard", value = "证件号码") @RequestParam String idcard) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = caseAssociatedService.listHistoryCaseByIdcard(idcard);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	@ApiOperation(value = "通过用户名关联历史案件", notes = "通过用户名关联历史案件")
	public Responses listByUsername(@ApiParam(name = "username", value = "用户名") @RequestParam String username) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = caseAssociatedService.listHistoryCaseByUsername(username);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/lawrules", method = RequestMethod.POST)
	@ApiOperation(value = "通过法律法规code值关联法律法规信息", notes = "通过法律法规code值关联法律法规信息")
	public Responses referenceLawRules(
			@ApiParam(name = "categoryIds", value = "法律法规code值") @RequestBody Integer[] categoryIds) {

		List<LawRulesVO> result = new ArrayList<LawRulesVO>();
		try {
			result = caseAssociatedService.referenceLawRules(categoryIds);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/lawrules/content", method = RequestMethod.POST)
	@ApiOperation(value = "通过法律法规id获取内容", notes = "通过法律法规id获取内容")
	public Responses referenceLawRulesContent(
			@ApiParam(name = "lawId", value = "法律法规id值") @RequestParam Integer lawId) {

		String result = "";
		try {
			result = caseAssociatedService.getByLawId(lawId);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/relation", method = RequestMethod.POST)
	@ApiOperation(value = "关联信息", notes = "关联信息")
	public Response relation(@ApiParam(name = "caseId", value = "被关联的案件ID") @RequestParam Integer caseId,
			@ApiParam(name = "relationId", value = "关联的案件ID") @RequestParam String relationId) {

		try {
			caseAssociatedService.relationCase(caseId, relationId);
		} catch (Exception e) {
			return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE);
		}
		return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE);
	}

	@RequestMapping(value = "/isrelation", method = RequestMethod.POST)
	@ApiOperation(value = "是否有关联", notes = "是否有关联")
	public Responses isRelation(@ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId) {

		boolean result = false;
		try {
			result = caseAssociatedService.isRelation(caseId);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}
}
