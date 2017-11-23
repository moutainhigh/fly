package com.xinfang.evaluating.controller;

import com.alibaba.fastjson.JSON;
import com.xinfang.context.*;
import com.xinfang.evaluating.model.FpAssessEntity;
import com.xinfang.evaluating.service.AssessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by sunbjx on 2017/5/16.
 */
@NewModel()
@RestController
@RequestMapping("/api/v1/assess")
@Api(description = "投诉办理-考核项目(孙帮雄)")
public class AssessController {

	@Autowired
	private AssessService assessService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ApiOperation(value = "添加考核项目", notes = "添加考核项目")
	public Response save(
			@ModelAttribute FpAssessEntity assess,
			@RequestParam(required = false, defaultValue = "") List<String> unitTypeA,
			@RequestParam(required = false, defaultValue = "") List<String> unitTypeB) {
		try {
			List<Map> unitTypeAMap = null;
			List<Map> unitTypeBMap = null;
		
			String a = unitTypeA.toString().replaceAll("\\[", "")
					.replaceAll("\\]", "");
			String b = unitTypeB.toString().replaceAll("\\[", "")
					.replaceAll("\\]", "");
			String A = "[" + a + "]";
			String B = "[" + b + "]";

			// String A=StringUtils.strip(unitTypeA.toString(), "[]");
			// String B=StringUtils.strip(unitTypeB.toString(), "[]");

			unitTypeAMap = JSON.parseArray(A, Map.class);

			// unitTypeAMap=(List<Map<String,Object>>)jsonA;
			// JSONArray jsonB=JSONArray.fromObject(unitTypeB);
			// unitTypeBMap=(List<Map<String,Object>>)jsonB;

			unitTypeBMap = JSON.parseArray(B, Map.class);

			assessService.assessSave(assess, unitTypeAMap, unitTypeBMap);
		} catch (Exception e) {
			System.err.println("Controller:" + e.getMessage());
			return new Response(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE);
		}
		return new Response(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE);
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@ApiOperation(value = "更新考核项目", notes = "更新考核项目")
	public Responses update(
			@ApiParam(name = "assessId", value = "考核ID") @RequestParam Integer assessId,
			@ModelAttribute FpAssessEntity assess,
			@RequestParam(required = false, defaultValue = "") List<String> unitTypeA,
			@RequestParam(required = false, defaultValue = "") List<String> unitTypeB) {
		Map<String, Object> map=null;
		try {
			String a = unitTypeA.toString().replaceAll("\\[", "")
					.replaceAll("\\]", "");
			String b = unitTypeB.toString().replaceAll("\\[", "")
					.replaceAll("\\]", "");
			String A = "[" + a + "]";
			String B = "[" + b + "]";

			List<Map> unitTypeMapA = JSON.parseArray(A,
					Map.class);
			List<Map> unitTypeMapB = JSON.parseArray(B,
					Map.class);
		  map=assessService.assessUpdate(assessId, assess, unitTypeMapA,
					unitTypeMapB);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE,map);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE,map);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	@ApiOperation(value = "通过考核ID删除考核信息", notes = "通过考核ID删除考核信息")
	public Response remove(
			@ApiParam(name = "assessId", value = "考核ID") @RequestParam Integer assessId) {
		try {
			assessService.remove(assessId);
		} catch (Exception e) {
			return new Response(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE);
		}
		return new Response(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ApiOperation(value = "考核项目详情", notes = "考核项目详情")
	public Responses detail(
			@ApiParam(name = "assessId", value = "考核ID") @RequestParam Integer assessId) {
		FpAssessEntity result = null;
		try {
			result = assessService.assessDetail(assessId);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiOperation(value = "考核列表", notes = "考核列表")
	public Responses list(
			@ApiParam(name = "signInUserId", value = "当前登陆人ID") @RequestParam Integer signInUserId,
			@ApiParam(name = "startTime", value = "考核开始时间") @RequestParam(required = false, defaultValue = "") String startTime,
			@ApiParam(name = "endTime", value = "考核结束时间") @RequestParam(required = false, defaultValue = "") String endTime,
			@ApiParam(name = "petitionUnitId", value = "信访单位ID") @RequestParam(required = false, defaultValue = "0") Integer petitionUnitId,
			@ApiParam(name = "fuzzy", value = "模糊搜索") @RequestParam(required = false, defaultValue = "") String fuzzy,
			@ApiParam(name = "startPage", value = "起始页") @RequestParam(required = false, defaultValue = "0") int startPage,
			@ApiParam(name = "pageSize", value = "每页条数") @RequestParam(required = false, defaultValue = "10") int pageSize) {

		Map<String, Object> result = null;
		try {
			result = assessService.getAssessList(signInUserId, startTime,
					endTime, petitionUnitId, startPage, pageSize, fuzzy);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	@ApiOperation(value = "考核统计", notes = "考核统计")
	public Responses statistics(
			@ApiParam(name = "assessId", value = "考核ID") @RequestParam Integer assessId,
			@ApiParam(name = "unitType", value = "考核单位类型") @RequestParam(required = false, defaultValue = "-1") Integer unitType,
			@ApiParam(name = "unitId", value = "考核单位ID") @RequestParam(required = false, defaultValue = "0") Integer unitId,
			@ApiParam(name = "itemType", value = "考核项") @RequestParam(required = false, defaultValue = "") String itemType) {

		Map<String, Object> result = null;
		try {
			result = assessService.assessStatistics(assessId, unitType, unitId,
					itemType);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	@ApiOperation(value = "考核报表", notes = "考核报表")
	public Responses report(
			@ApiParam(name = "assessId", value = "考核ID") @RequestParam Integer assessId) {

		Map<String, Object> result = null;
		try {
			result = assessService.assessReport2(assessId);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/getAssessUnit", method = RequestMethod.GET)
	@ApiOperation(value = "获取单位列表", notes = "获取单位列表")
	public Responses list(
			@ApiParam(name = "assessId", value = "考核ID") @RequestParam Integer assessId,
			@ApiParam(name = "type", value = "0 A类,1 B类") @RequestParam(required = false, defaultValue = "-1") Integer type) {

		Map<String, Object> result = null;
		try {
			result = assessService.getAssessUnit(assessId, type);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}
	@RequestMapping(value = "/getAunitList", method = RequestMethod.GET)
	@ApiOperation(value = "获取考核A类单位列表", notes = "获取考核A类单位列表")
	public Responses getAunitList(){
		Map<String, Object> result = null;
		try {
			result = assessService.getAunitList();
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

}
