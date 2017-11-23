package com.xinfang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xinfang.VO.TaskVO;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.ResponseOth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * 
 * @title TestController.java
 * @package com.xinfang.controller
 * @description 简单的token认证
 * @author ZhangHuaRong   
 * @update 2017年6月30日 下午4:18:21
 * @version V1.0  
 * Copyright (c)2012 chantsoft-版权所有
 */
@RestController
@RequestMapping("/test")
@Api(description = "token测试（张华荣）")
public class TestController {


	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ApiOperation(value = "任务", notes = "根据任务id获取任务")
	public ResponseOth gettask(
			@ApiParam(name = "taskid", value = "任务id") @RequestParam(name = "taskid") String taskid,
			@ApiParam(name = "userid", value = "用户id 测试1003") @RequestParam(name = "userid") String userid,
			@ApiParam(name = "token", value = "token 测试 77ea6149795f28b8ade6ef6086bd69510cf6700632f7c234b98c5318730003d57fcd60ede184c0a7b6d4683ffba3dfc52bbbe1f9dc941e8f93c401839852f9a4") @RequestParam(name = "userid") String token

	) {
		TaskVO tasks;
		try {
			tasks=new TaskVO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());

		}

		return new ResponseOth(ResponseConstants.CODE_OK, tasks, "已实现");

	}
	
	@RequestMapping(value = "/testoth", method = RequestMethod.GET)
	@ApiOperation(value = "token认证", notes = "http://localhost:9090/test/testoth?taskid=1&userid=1003&token=77ea6149795f28b8ade6ef6086bd69510cf6700632f7c234b98c5318730003d57fcd60ede184c0a7b6d4683ffba3dfc52bbbe1f9dc941e8f93c401839852f9a4")
	public ResponseOth gettask(
			@ApiParam(name = "taskid", value = "任务id") @RequestParam(name = "taskid") String taskid
			

	) {
		TaskVO tasks;
		try {
			tasks=new TaskVO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());

		}

		return new ResponseOth(ResponseConstants.CODE_OK, tasks, "已实现");

	}

}
