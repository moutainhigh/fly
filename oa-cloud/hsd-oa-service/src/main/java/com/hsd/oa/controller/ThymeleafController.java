package com.hsd.oa.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hsd.core.Response;
import com.hsd.oa.VO.TaskVO;
import com.hsd.oa.model.FcRyb;
import com.hsd.oa.service.ActivitiService;
import com.hsd.oa.service.UserService;
import com.hsd.oa.utils.JSONUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * 
     * 此类描述的是:测试
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:46:47
 */
@RestController
@RequestMapping("/V1/Tleaf")
@Api(description = "流程监控测试（张华荣）")
public class ThymeleafController {
	
	@Autowired
	UserService userService;
	
	
	@Autowired
	ActivitiService activitiService;

	@ApiOperation("测试Thymeleaf模板页面")
	@RequestMapping(value = "/monitor", method = RequestMethod.GET)
	public ModelAndView  greeting(@RequestParam(name = "id") String id) {
		ModelAndView modelview = new 	ModelAndView("index");
		modelview.addObject("xname", "http://127.0.0.1:8081/V1/process/processmonitoring?deploymentId="+id);
		return modelview;
	}

	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有用户信息（测试）", notes = "获取所有用户信息（测试）")
	public Response getUsers(@ApiParam(name = "userId", value = "用户id") @RequestParam(name = "userId",required=false) Integer caseid

	) {
		List<FcRyb> lists = null;
		try {
			
			lists=userService.getUsers();
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(0, null, "暂未实现");

		}

		return new Response(1, lists, "已经实现");

	}
	
	@RequestMapping(value = "/getUsersforfeign", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有用户信息（测试）", notes = "获取所有用户信息（测试）")
	public String getUsersforfeign(@ApiParam(name = "userId", value = "用户id") @RequestParam(name = "userId",required=false) Integer caseid

	) {
		Response res=null;
		List<FcRyb> lists = null;
		try {
			
			lists=userService.getUsers();
			
		} catch (Exception e) {
			e.printStackTrace();
			res= new Response(0, null, "暂未实现");

		}

		res= new Response(1, lists, "已经实现");
		
		return JSONUtils.getJsonString(res);

	}
	
	
	@RequestMapping(value = "/startProcess", method = RequestMethod.GET)
	@ApiOperation(value = "开始流程", notes = "开始流程")
	public Response startProcess(
			@ApiParam(name = "key", value = "流程key（qingjia请假,baoxiao报销）") @RequestParam(name = "key",required=false) String key,
			@ApiParam(name = "formjson", value = "流程表单数据（流程不同要展示的数据可能不同需前端组装）") @RequestParam(name = "formjson",required=false) String formjson

	) {
		String result = null;
		try {
			result = activitiService.startProcess(key, formjson);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(0, null, "暂未实现");
		}

		return new Response(1, result, "已经实现");

	}
	
	
	@RequestMapping(value = "/getmytasks", method = RequestMethod.GET)
	@ApiOperation(value = "我的待办", notes = "我的待办")
	public Response getmytasks(
			@ApiParam(name = "userid", value = "用户id") @RequestParam(name = "userid") String userid

	) {
		List<TaskVO> result = null;
		try {
			result = activitiService.getTasks(userid);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(0, null, "暂未实现");
		}

		return new Response(1, result, "已经实现");

	}
	
	
	@RequestMapping(value = "/processmonitoring", method = RequestMethod.GET)
	@ApiOperation(value = "流程监控", notes = "流程监控http://120.24.228.17:8081/diagram-viewer/index.html?processDefinitionId=jiaoban2:15:45010&processInstanceId=497609")
	public Response  processMonitoring(
			@ApiParam(name = "processDefinitionId", value = "流程部署id") @RequestParam(name = "processDefinitionId") String processDefinitionId ,
			@ApiParam(name = "processInstanceId", value = "流程实例id") @RequestParam(name = "processInstanceId") String processInstanceId ) {
		    StringBuffer sb=new StringBuffer();
		    try {
				String ip= InetAddress.getLocalHost().getHostAddress();
				sb.append("http://").append(ip).append(":9090").append("/diagram-viewer/index.html?processDefinitionId=").append(processDefinitionId).append("&processInstanceId=").append(processInstanceId);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		    
			return new Response(1, sb.toString(), "请访问这个地址");
		
	}
	
	
	@RequestMapping(value = "/sqlmonitoring", method = RequestMethod.GET)
	@ApiOperation(value = "数据库监控", notes = "数据库监控")
	public Response  sqlmonitoring()
			 {
		    StringBuffer sb=new StringBuffer();
		    try {
				String ip= InetAddress.getLocalHost().getHostAddress();
				sb.append("http://").append(ip).append(":9090").append("/druid/index.html");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		    
			return new Response(1, sb.toString(), "用户名zhanghr密码zhanghr");
		
	}
	
}
