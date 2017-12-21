package com.xinfang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * 
 * @description 参考资料  http://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html
 *  http://www.bootcss.com/   
 * @author ZhangHuaRong   
 * @update 2017年2月12日 下午11:44:39
 */
@RestController
@RequestMapping("/V1/Tleaf")
@Api(description = "流程监控测试（张华荣）")
public class ThymeleafController {

	@ApiOperation("测试页面")
	@RequestMapping(value = "/monitor", method = RequestMethod.GET)
	public ModelAndView  greeting(@RequestParam(name = "id") String id) {
		ModelAndView modelview = new 	ModelAndView("index");
		modelview.addObject("xname", "http://127.0.0.1:8081/V1/process/processmonitoring?deploymentId="+id);
		return modelview;
	}

}
