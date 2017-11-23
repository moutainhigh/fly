package com.xinfang.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.service.ExternalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author sunbjx Date: 2017年6月2日上午11:07:00
 */
@RestController
@RequestMapping("/api/v1/case/external")
@Api(description = "外部接口(孙帮雄)")
public class ExternalController {

	@Autowired
	private ExternalService externalService;

	@RequestMapping(value = "/query/time", method = RequestMethod.POST)
	@ApiOperation(value = "通过案件录入时间段获取数据", notes = "通过案件录入时间段获取数据")
	public Responses getCaseById(
			@ApiParam(name = "startTime", value = "开始时间 <font color='red'>YYYY-MM-DD</font>") @RequestParam(required = false, defaultValue = "") String startTime,
			@ApiParam(name = "endTime", value = "结束时间 <font color='red'>YYYY-MM-DD</font>") @RequestParam(required = false, defaultValue = "") String endTime) {

		List<Map<String, Object>> result = null;
		try {
			result = externalService.listZhengFa(startTime, endTime);
		} catch (Exception e) {
			return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, result);
		}
		return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, result);
	}
}
