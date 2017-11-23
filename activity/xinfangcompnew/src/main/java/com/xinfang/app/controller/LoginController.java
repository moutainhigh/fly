package com.xinfang.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.app.service.AipService;
import com.xinfang.app.service.AppService;
import com.xinfang.context.AppModel;
import com.xinfang.model.FdGuest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * 
 * @description http://www.cnblogs.com/woshimrf/p/5863318.html
 * @author ZhangHuaRong   
 * @update 2017年5月8日 上午11:20:38
 */
@RestController
@RequestMapping("/login")
@Api(description = "app接口（张华荣）")
@AppModel
@CrossOrigin(allowedHeaders="*",allowCredentials="true")
public class LoginController {
	@Autowired
	AipService aipService;
	@Autowired
	AppService appService;
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "16登录", notes = "登录")
	public Map<String,Object> login(
			@ApiParam(name = "username", value = "用户名") @RequestParam(name = "username") String username,
			@ApiParam(name = "password", value = "密码") @RequestParam(name = "password") String password
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			FdGuest user=aipService.login(username,password);
			Map<String,String> map = new HashMap<String,String>();
			map.put("person_id", user.getId()+"");
			map.put("username", user.getUsername());
			map.put("user_name", user.getUsername());
			map.put("user_id", user.getId()+"");
			map.put("phone_num", user.getPhone());
			map.put("idcard ", user.getIdcard());
			result.put("result", "success");
			result.put("sf_person", map);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	

	
	@RequestMapping(value = "/sf_register", method = RequestMethod.POST)
	@ApiOperation(value = "19注册", notes = "注册")
	public Map<String,String> register(
			@ApiParam(name = "password", value = "密码") @RequestParam(name = "password") String password,
			@ApiParam(name = "telephone", value = "电话号码") @RequestParam(name = "telephone") String telephone,
			@ApiParam(name = "name", value = "用户名") @RequestParam(name = "name") String name
	) {
		Map<String,String> result = new HashMap<String,String>();
		
		try {
			Integer userid=appService.register(null,password,telephone,name);
			result.put("result", "success");
			result.put("person_id", userid.toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/sf_sms", method = RequestMethod.POST)
	@ApiOperation(value = "20.发送短信验证码", notes = "发送短信验证码")
	public Map<String,String> send(@ApiParam(name = "telephone", value = "电话号码") @RequestParam(name = "telephone") String telephone
	) {
		Map<String,String> result = new HashMap<String,String>();
		
		try {
			String code = aipService.sendcode(telephone);
			result.put("result", "success");
			result.put("sms_result", code);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "failure");
			result.put("sms_result", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/compare_code", method = RequestMethod.POST)
	@ApiOperation(value = "21.检验证码是否正确", notes = "检验证码是否正确")
	public Map<String,String> compare_code(
			@ApiParam(name = "telephone", value = "电话号码") @RequestParam(name = "telephone") String telephone,
			@ApiParam(name = "sms_code", value = "验证码") @RequestParam(name = "sms_code") String sms_code
	) {
		Map<String,String> result = new HashMap<String,String>();
		
		try {
			boolean flag = appService.compare_code(telephone,sms_code);
			
			if(flag){
				result.put("result", "success");
				result.put("sms_result", sms_code);
			}else{
				result.put("result", "error");
				result.put("error_msg", sms_code);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "failure");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/update_password", method = RequestMethod.POST)
	@ApiOperation(value = "21.修改密码", notes = "修改密码")
	public Map<String,Object> update_password(
			@ApiParam(name = "sf_person_id", value = "电话号码") @RequestParam(name = "sf_person_id") String sf_person_id,
			@ApiParam(name = "old_password", value = "电话号码") @RequestParam(name = "old_password") String old_password,
			@ApiParam(name = "new_password", value = "电话号码") @RequestParam(name = "new_password") String new_password
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			FdGuest code = appService.appService(sf_person_id,old_password,new_password);
			Map<String,String> map = new HashMap<String,String>();
			map.put("user_id", code.getId().toString());
			map.put("username", code.getUsername());
			map.put("idcard ", code.getIdcard());
			result.put("result", "success");
			result.put("sf_person", map);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "failure");
			result.put("sms_result", e.getMessage());
		}
		
		return result;
	}
	
	
	
	
}
