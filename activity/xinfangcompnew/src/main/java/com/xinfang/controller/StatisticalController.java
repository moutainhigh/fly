package com.xinfang.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.service.StatisticalService;
import com.xinfang.utils.DownloadFileUtils;
import com.xinfang.utils.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 投诉办理-首页统计 Created by sunbjx on 2017/4/17.
 */
@RestController
@RequestMapping("/api/v1/statistical")
@Api(description = "投诉办理-统计信息(孙帮雄)")
public class StatisticalController {

	@Autowired
	private StatisticalService statisticalService;

	@RequestMapping(value = "/packaging", method = RequestMethod.GET)
	@ApiOperation(value = "首页统计", notes = "首页统计")
	public Responses packaging(
			@ApiParam(name = "authFlag", value = "1 仅自己") @RequestParam(required = false, defaultValue = "0") int authFlag,
			@ApiParam(name = "userId", value = "登录人ID(必填)*") @RequestParam Integer userId,
			@ApiParam(name = "startDate", value = "开始日期(YYYY-MM-DD)") @RequestParam(required = false, defaultValue = "") String startDate,
			@ApiParam(name = "endDate", value = "结束日期(YYYY-MM-DD)") @RequestParam(required = false, defaultValue = "") String endDate,
			@ApiParam(name = "handleType", value = "案件处理相关(1-6)") @RequestParam(required = false, defaultValue = "0") int handleType,
			@ApiParam(name = "registerType", value = "案件登记相关(1-8)") @RequestParam(required = false, defaultValue = "0") int registerType) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = statisticalService.packaging(authFlag, userId, startDate, endDate, handleType, registerType);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/handle/typenums", method = RequestMethod.GET)
	@ApiOperation(value = "首页图标统计", notes = "首页图标统计")
	public Responses typeNums(@ApiParam(name = "userId", value = "登录人ID") @RequestParam Integer userId) {

		Map<String, Object> result = null;
		try {
			result = statisticalService.typeNums(userId);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/report/daily", method = RequestMethod.GET)
	@ApiOperation(value = "日报", notes = "日报")
	public Responses dailyReport(
			@ApiParam(name = "startTime", value = "开始时间") @RequestParam(required = false, defaultValue = "") String startTime,
			@ApiParam(name = "endTime", value = "结束时间") @RequestParam(required = false, defaultValue = "") String endTime,
			@ApiParam(name = "keywords", value = "关键字") @RequestParam(required = false, defaultValue = "") String keywords) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = statisticalService.dailyReport(startTime, endTime, keywords);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/report/dailydetail", method = RequestMethod.GET)
	@ApiOperation(value = "日报明细", notes = "日报明细")
	public Responses dailyReportDetail(
			@ApiParam(name = "createUnitId", value = "创建单位ID<font color = red>(必填)</font>") @RequestParam Integer createUnitId,
			@ApiParam(name = "creatorId", value = "创建人ID") @RequestParam(required = false, defaultValue = "0") Integer creatorId,
			@ApiParam(name = "startTime", value = "开始时间<font color = red>(YYYY-MM-DD)</font>") @RequestParam(required = false, defaultValue = "") String startTime,
			@ApiParam(name = "endTime", value = "结束时间<font color = red>(YYYY-MM-DD)</font>") @RequestParam(required = false, defaultValue = "") String endTime,
			@ApiParam(name = "keywords", value = "关键字") @RequestParam(required = false, defaultValue = "") String keywords) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = statisticalService.dailyReportDetail(createUnitId, creatorId, startTime, endTime, keywords);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/report/zhtj", method = RequestMethod.GET)
	@ApiOperation(value = "", notes = "")
	public Responses zhtj(
			@ApiParam(name = "fl", value = "参数fl") @RequestParam(required = false, defaultValue = "") String fl,
			@ApiParam(name = "msg", value = "参数msg") @RequestParam(required = false, defaultValue = "") String msg,
			@ApiParam(name = "ct1", value = "参数1") @RequestParam(required = false, defaultValue = "") String ct1,
			@ApiParam(name = "ct2", value = "参数2") @RequestParam(required = false, defaultValue = "") String ct2,
			@ApiParam(name = "ct3", value = "参数3") @RequestParam(required = false, defaultValue = "") String ct3,
			@ApiParam(name = "ct4", value = "参数4") @RequestParam(required = false, defaultValue = "") String ct4,
			@ApiParam(name = "ct5", value = "参数5") @RequestParam(required = false, defaultValue = "") String ct5,
			@ApiParam(name = "ct6", value = "参数6") @RequestParam(required = false, defaultValue = "") String ct6,
			@ApiParam(name = "ct7", value = "参数7") @RequestParam(required = false, defaultValue = "") String ct7,
			@ApiParam(name = "ct8", value = "参数8") @RequestParam(required = false, defaultValue = "") String ct8,
			@ApiParam(name = "ct9", value = "参数9") @RequestParam(required = false, defaultValue = "") String ct9) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = statisticalService.zhtj(fl, msg, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}


	@RequestMapping(value = "/report/zhtj1", method = RequestMethod.POST)
	@ApiOperation(value = "", notes = "")
	public Responses zhtj1(
			@ApiParam(name = "fl", value = "参数fl") @RequestParam(required = false, defaultValue = "") String fl,
			@ApiParam(name = "msg", value = "参数msg") @RequestParam(required = false, defaultValue = "") String msg,
			@ApiParam(name = "ct1", value = "参数1") @RequestParam(required = false, defaultValue = "") String ct1,
			@ApiParam(name = "ct2", value = "参数2") @RequestParam(required = false, defaultValue = "") String ct2,
			@ApiParam(name = "ct3", value = "参数3") @RequestParam(required = false, defaultValue = "") String ct3,
			@ApiParam(name = "ct4", value = "参数4") @RequestParam(required = false, defaultValue = "") String ct4,
			@ApiParam(name = "ct5", value = "参数5") @RequestParam(required = false, defaultValue = "") String ct5,
			@ApiParam(name = "ct6", value = "参数6") @RequestParam(required = false, defaultValue = "") String ct6,
			@ApiParam(name = "ct7", value = "参数7") @RequestParam(required = false, defaultValue = "") String ct7,
			@ApiParam(name = "ct8", value = "参数8") @RequestParam(required = false, defaultValue = "") String ct8,
			@ApiParam(name = "ct9", value = "参数9") @RequestParam(required = false, defaultValue = "") String ct9) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = statisticalService.zhtj(fl, msg, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	
	@RequestMapping(value = "/report/daily/download", method = RequestMethod.GET)
	@ApiOperation(value = "日报下载", notes = "日报下载")
	public Responses dailyReportDownload(HttpServletResponse response,
			@ApiParam(name = "fl", value = "参数fl") @RequestParam(required = false, defaultValue = "") String fl,
			@ApiParam(name = "msg", value = "参数msg") @RequestParam(required = false, defaultValue = "") String msg,
			@ApiParam(name = "ct1", value = "参数1") @RequestParam(required = false, defaultValue = "") String ct1,
			@ApiParam(name = "ct2", value = "参数2") @RequestParam(required = false, defaultValue = "") String ct2,
			@ApiParam(name = "ct3", value = "参数3") @RequestParam(required = false, defaultValue = "") String ct3,
			@ApiParam(name = "ct4", value = "参数4") @RequestParam(required = false, defaultValue = "") String ct4,
			@ApiParam(name = "ct5", value = "参数5") @RequestParam(required = false, defaultValue = "") String ct5,
			@ApiParam(name = "ct6", value = "参数6") @RequestParam(required = false, defaultValue = "") String ct6,
			@ApiParam(name = "ct7", value = "参数7") @RequestParam(required = false, defaultValue = "") String ct7,
			@ApiParam(name = "ct8", value = "参数8") @RequestParam(required = false, defaultValue = "") String ct8,
			@ApiParam(name = "ct9", value = "参数9") @RequestParam(required = false, defaultValue = "") String ct9) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = statisticalService.zhtj(fl, msg, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9);
			String filePath = "";
			int x, y, dateX, dateY; // 横纵向值,从0开始
			if ("GC_RBTJB_DJMXB".equals(fl)) {
				filePath = System.getProperty("user.dir") + "/src/main/resources/templates/excel/日报_案件明细表.xls";
				x = 4;
				y = 0;
				dateX = 1;
				dateY = 9;
				ExcelUtil.specifiedLoopWrite(filePath, dateX, dateY, ct1, ct2, x, y, list);
			}
			
			DownloadFileUtils.downLoadFile(filePath, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, "");
	}
}
