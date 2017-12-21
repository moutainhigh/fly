package com.xinfang.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.VO.HomeListVO;
import com.xinfang.context.PageFinder;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.service.HomeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Created by sunbjx on 2017/3/28.
 */
@RestController
@RequestMapping("/api/v1/home")
@Api(description = "投诉办理-首页信息(孙帮雄)")
public class HomeController {

	@Autowired
	private HomeService homeService;

	@RequestMapping(value = "/record/condition", method = RequestMethod.GET)
	@ApiOperation(value = "首页查询", notes = "首页汇总筛选")
	public Responses condition(
			@ApiParam(name = "flag", value = "首页列表( 1 待办  " + "2 正办  " + "3 已办  " + "4 收文岗 " + "5 已退回  "
					+ "6 汇总)") @RequestParam(required = false, defaultValue = "-1") int flag,
			@ApiParam(name = "userId", value = "当前登录人ID") @RequestParam Integer userId,
			@ApiParam(name = "caseState", value = "案件期限状态( 1 逾期未结  " + "2 期限过半  " + "3 未办理  " + "4 办理中 " + "5 逾期办结  "
					+ "6 按期办结)") @RequestParam(required = false, defaultValue = "0") int caseState,
			@ApiParam(name = "handleState", value = "过滤状态( 1 转办件  " + "2 交办件  " + "3 待审件  " + "4 待处理 " + "5 待办结信访件  "
					+ "6 不予受理 " + "7 不再受理 " + "8 直接回复 " + "9 退回 "
					+ "10 存件)") @RequestParam(required = false, defaultValue = "0") int handleState,
			@ApiParam(name = "timeProgress", value = "时间监控( 1 正常  " + "2 预警  " + "3 警告  " + "4 严重警告 "
					+ "5 超期)") @RequestParam(required = false, defaultValue = "0") int timeProgress,
			@ApiParam(name = "fuzzy", value = "模糊搜索内容") @RequestParam(required = false, defaultValue = "") String fuzzy,
			@ApiParam(name = "petitionType", value = "信访方式") @RequestParam(required = false, defaultValue = "0") Integer petitionType,
			@ApiParam(name = "caseBelongTo", value = "案件归属地") @RequestParam(required = false, defaultValue = "0") Integer caseBelongTo,
			@ApiParam(name = "dept", value = "当前处理单位") @RequestParam(required = false, defaultValue = "0") Integer dept,
			@ApiParam(name = "startPage", value = "起始页") @RequestParam(defaultValue = "1") int startPage,
			@ApiParam(name = "pageSize", value = "显示条数") @RequestParam(defaultValue = "10") int pageSize) {

		PageFinder<HomeListVO> result = null;
		try {
			result = homeService.packages(flag, userId, caseState, handleState, timeProgress, fuzzy, petitionType,
					caseBelongTo, dept, startPage, pageSize);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/record/dispatcher", method = RequestMethod.GET)
	@ApiOperation(value = "收文岗查看", notes = "收文岗查看")
	public Responses dispatcher(@ApiParam(name = "userId", value = "当前登录人ID") @RequestParam Integer userId,
			@ApiParam(name = "caseState", value = "案件期限状态( 1 逾期未结  " + "2 期限过半  " + "3 未办理  " + "4 办理中 " + "5 逾期办结  "
					+ "6 按期办结)") @RequestParam(required = false, defaultValue = "0") int caseState,
			@ApiParam(name = "handleState", value = "过滤状态( 1 转办件  " + "2 交办件  " + "3 待审件  " + "4 待处理 " + "5 待办结信访件  "
					+ "6 不予受理 " + "7 不再受理 " + "8 直接回复 " + "9 退回 "
					+ "10 存件)") @RequestParam(required = false, defaultValue = "0") int handleState,
			@ApiParam(name = "timeProgress", value = "时间监控( 1 正常  " + "2 预警  " + "3 警告  " + "4 严重警告 "
					+ "5 超期)") @RequestParam(required = false, defaultValue = "0") int timeProgress,
			@ApiParam(name = "fuzzy", value = "模糊搜索内容") @RequestParam(required = false, defaultValue = "") String fuzzy,
			@ApiParam(name = "petitionType", value = "信访方式") @RequestParam(required = false, defaultValue = "0") Integer petitionType,
			@ApiParam(name = "caseBelongTo", value = "案件归属地") @RequestParam(required = false, defaultValue = "0") Integer caseBelongTo,
			@ApiParam(name = "dept", value = "当前处理单位") @RequestParam(required = false, defaultValue = "0") Integer dept,
			@ApiParam(name = "startPage", value = "起始页") @RequestParam(defaultValue = "1") int startPage,
			@ApiParam(name = "pageSize", value = "显示条数") @RequestParam(defaultValue = "10") int pageSize) {

		Map<String, Object> result = null;
		try {
			result = homeService.dispatches(userId, caseState, handleState, timeProgress, fuzzy, petitionType,
					caseBelongTo, dept, startPage, pageSize);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}

	@RequestMapping(value = "/record/casestate", method = RequestMethod.GET)
	@ApiOperation(value = "首页案件状态查询", notes = "首页案件状态查询")
	public Responses caseStateQuery(@ApiParam(name = "userId", value = "当前登录人ID") @RequestParam Integer userId,
			@ApiParam(name = "caseState", value = "案件期限状态( 1 逾期未结  " + "2 期限过半  " + "3 未办理  " + "4 办理中 " + "5 逾期办结  "
					+ "6 按期办结)") @RequestParam(required = false, defaultValue = "0") int caseState,
			@ApiParam(name = "handleState", value = "过滤状态( 1 转办件  " + "2 交办件  " + "3 待审件  " + "4 待处理 " + "5 待办结信访件  "
					+ "6 不予受理 " + "7 不再受理 " + "8 直接回复 " + "9 退回 "
					+ "10 存件)") @RequestParam(required = false, defaultValue = "0") int handleState,
			@ApiParam(name = "timeProgress", value = "时间监控( 1 正常  " + "2 预警  " + "3 警告  " + "4 严重警告 "
					+ "5 超期)") @RequestParam(required = false, defaultValue = "0") int timeProgress,
			@ApiParam(name = "fuzzy", value = "模糊搜索内容") @RequestParam(required = false, defaultValue = "") String fuzzy,
			@ApiParam(name = "petitionType", value = "信访方式") @RequestParam(required = false, defaultValue = "0") Integer petitionType,
			@ApiParam(name = "caseBelongTo", value = "案件归属地") @RequestParam(required = false, defaultValue = "0") Integer caseBelongTo,
			@ApiParam(name = "dept", value = "当前处理单位") @RequestParam(required = false, defaultValue = "0") Integer dept) {

		List<Map<String, Object>> result = null;
		try {
			result = homeService.caseStateQuery(userId, caseState, handleState, timeProgress, fuzzy, petitionType,
					caseBelongTo, dept);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}
}
