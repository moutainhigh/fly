package com.xinfang.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.VO.TaskVO;
//import com.github.pagehelper.PageHelper;
import com.xinfang.context.PageFinder;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.service.CaseQueryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/caseQuery")
@Api(description = "根据各种条件进行案件查询（甘建兴）")
public class CaseQueryController {
	@Autowired
	private CaseQueryService caseQueryService;

	@RequestMapping(value = "/caseQueryByCondition", method = RequestMethod.POST)
	@ApiOperation(value = "按条件查询案件", notes = "按条件查询案件")
	public Responses caseQueryByCondition(
			@ApiParam(name = "str", value = "json格式的字符串") @RequestParam(required = false) String str,
			@ApiParam(name = "startPage", value = "开始页") @RequestParam Integer startPage,
			@ApiParam(name = "pageSize", value = "每页数据量") @RequestParam Integer pageSize) {
		PageFinder<Map<String, Object>> taskList;
		try {
			System.out.println(str);
			taskList = caseQueryService.caseQueryByCondition(str, startPage, pageSize);

		} catch (Exception e) {
			e.printStackTrace();
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, null);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, taskList);
	}

	@RequestMapping(value = "/queryDbCase", method = RequestMethod.POST)
	@ApiOperation(value = "待办案件", notes = "待办案件")
	public Responses queryDbCase(@ApiParam(name = "type", value = "案件类型（转办件、交办件、待审核件、待办结件）") @RequestParam Integer type,
			@ApiParam(name = "vagueCondition", value = "条件") @RequestParam(required = false) String vagueCondition,
			@ApiParam(name = "petitionWay", value = "") @RequestParam(required = false) Integer petitionWay,
			@ApiParam(name = "questionBelongingTo", value = "问题属地") @RequestParam(required = false) Integer questionBelongingTo,
			@ApiParam(name = "userId", value = "用户ID") @RequestParam Integer userId,
			@ApiParam(name = "startPage", value = "起始页") @RequestParam Integer startPage,
			@ApiParam(name = "pageSize", value = "每页数据条数") @RequestParam Integer pageSize) {
		PageFinder<TaskVO> list = null;
		try {
			list = caseQueryService.queryDbCase(type, vagueCondition, petitionWay, questionBelongingTo, userId,
					startPage, pageSize);

		} catch (Exception e) {
			e.printStackTrace();
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, null);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, list);
	}

	@RequestMapping(value = "/custom", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ApiOperation(value = "案件自定义高级查询", notes = "案件自定义高级查询")
	@ResponseBody
	public Responses customCondition(
			@ApiParam(name = "conditions", value = "自定义条件参数<font color = 'red'>conditions?[{\"relation\":\" and \",\"singleColumn\": \" c.petition_county \",\"singleWhere\": \" = \",\"singleColumnValue\": 2},…..]</font>") @RequestBody(required = false) List<Map<String, Object>> conditions,
			@ApiParam(name = "caseTimeTag", value = "案件时间 <font color = 'red'>1 办理中 2 逾期未办结 3 按期办结</font>") @RequestParam(required = false, defaultValue = "0") int caseTimeTag,
			@ApiParam(name = "userId", value = "用户ID") @RequestParam Integer userId,
			@ApiParam(name = "startPage", value = "起始页 <font color = 'red'>必须大于 1 默认 1</font>") @RequestParam(required = false, defaultValue = "1") int startPage,
			@ApiParam(name = "pageSize", value = "每页数据条数 <font color = 'red'>默认 10</font>") @RequestParam(required = false, defaultValue = "10") int pageSize) {
		PageFinder<Map<String, Object>> list = null;
		try {
			list = caseQueryService.caseQueryByCustomCondition(conditions, caseTimeTag, userId, startPage, pageSize);

		} catch (Exception e) {
			e.printStackTrace();
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, null);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, list);
	}

}
