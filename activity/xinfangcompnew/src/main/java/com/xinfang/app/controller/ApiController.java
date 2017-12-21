package com.xinfang.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.app.VO.ComplaintVO;
import com.xinfang.app.VO.UserVO;
import com.xinfang.app.model.FdConsult;
import com.xinfang.app.model.FdSuggest;
import com.xinfang.app.service.AipService;
import com.xinfang.evaluating.model.FpAppraiseEntity;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdGuest;
import com.xinfang.model.LawRulesEntity;

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
@RequestMapping("/dateotn")
@Api(description = "app接口（张华荣）")
public class ApiController {
	@Autowired
	AipService aipService;
	
	@RequestMapping(value = "/sf_sms", method = RequestMethod.POST)
	@ApiOperation(value = "1。1发送短信验证码", notes = "发送短信验证码")
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
	

	
	@RequestMapping(value = "/sf_register", method = RequestMethod.POST)
	@ApiOperation(value = "1.2注册", notes = "注册")
	public Map<String,String> register(
			@ApiParam(name = "username", value = "用户名") @RequestParam(name = "username") String username,
			@ApiParam(name = "password", value = "密码") @RequestParam(name = "password") String password,
			@ApiParam(name = "telephone", value = "电话号码") @RequestParam(name = "telephone") String telephone,
			@ApiParam(name = "code", value = "验证码") @RequestParam(name = "code") String code
	) {
		Map<String,String> result = new HashMap<String,String>();
		
		try {
			Integer userid=aipService.register(username,password,telephone,code);
			result.put("result", "success");
			result.put("person_id", userid.toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "1.3登录", notes = "登录")
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
			result.put("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ApiOperation(value = "1.4登出", notes = "登出")
	public Map<String,String> logout(
			
		
	) {
		Map<String,String> result = new HashMap<String,String>();
		
		try {
			
			result.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/android_suggest_old", method = RequestMethod.POST)
	@ApiOperation(value = "2.1建议查询接口", notes = "建议查询接口")
	public Map<String,Object> androidSuggestOld(
			@ApiParam(name = "queryType", value = "类型 值为1按照用户id查询 值为2按照建议id查询详情") @RequestParam(name = "queryType") Integer queryType,
			@ApiParam(name = "queryValue", value = "用户id或者建议id") @RequestParam(name = "queryValue") String queryValue,
			@ApiParam(name = "page", value = "页码") @RequestParam(name = "page") Integer page,
			@ApiParam(name = "pageCount", value = "每页条数") @RequestParam(name = "pageCount") Integer pageCount,
			@ApiParam(name = "userId", value = "用户id(非必填) ") @RequestParam(name = "userId",required=false) Integer userId
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
             if(queryType.intValue()==2){//查询详情
				
			}
	    	List<FdSuggest> list =	aipService.searchFdSuggest(queryType, queryValue, page, pageCount, userId);
			result.put("result", "success");
			result.put("suggest_list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/android_consult_old", method = RequestMethod.POST)
	@ApiOperation(value = "2.2咨询查询接口", notes = "咨询查询接口")
	public Map<String,Object> android_consult_old(
			@ApiParam(name = "queryType", value = "类型 值为1按照用户id查询 值为2按照咨询id查询详情") @RequestParam(name = "queryType") Integer queryType,
			@ApiParam(name = "queryValue", value = "用户id或者咨询id") @RequestParam(name = "queryValue") String queryValue,
			@ApiParam(name = "page", value = "页码") @RequestParam(name = "page") Integer page,
			@ApiParam(name = "pageCount", value = "每页条数") @RequestParam(name = "pageCount") Integer pageCount,
			@ApiParam(name = "userId", value = "用户id（非必填）") @RequestParam(name = "userId",required=false) Integer userId
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			if(queryType.intValue()==2){//查询详情
				
			}
			List<FdConsult> list=aipService.searchConsult(queryType, queryValue, page, pageCount, userId);
			
			result.put("result", "success");
			result.put("old_consult_list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	/*@RequestMapping(value = "/show_complaint_type", method = RequestMethod.POST)
	@ApiOperation(value = "2.3咨询查询接口", notes = "咨询查询接口")
	public Map<String,String> android_consult_old(
		
		
	) {
		Map<String,String> result = null;
		
		try {
			
			  result =	RuleUtil.gettype();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}*/
	
	
	
	@RequestMapping(value = "/law_query", method = RequestMethod.POST)
	@ApiOperation(value = "2.3政策法规查询", notes = "政策法规查询")
	public Map<String,Object> android_consult_old(
			@ApiParam(name = "queryType", value = "queryType=0查全部；queryType=3以类别和关键字查询；queryValue关键字|选中的类别") @RequestParam(name = "queryType") Integer queryType,
			@ApiParam(name = "queryValue", value = "用户名") @RequestParam(name = "queryValue") String queryValue,
			@ApiParam(name = "page", value = "页数") @RequestParam(name = "page") Integer page,
			@ApiParam(name = "pageCount", value = "每页条数") @RequestParam(name = "pageCount") Integer pageCount
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			List<LawRulesEntity> list=aipService.law_query(queryType, queryValue, page, pageCount);
			
			result.put("result", "success");
			result.put("old_consult_list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	//
	
	@RequestMapping(value = "/get_law_content", method = RequestMethod.POST)
	@ApiOperation(value = "2.4政策法规详情", notes = "政策法规详情")
	public Map<String,Object> get_law_content(
			@ApiParam(name = "law_id", value = "id") @RequestParam(name = "law_id") Integer law_id
		
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			LawRulesEntity list=aipService.get_law_content(law_id);
			
			result.put("result", "success");
			result.put("old_consult_list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	//
	@RequestMapping(value = "/suggest_submit", method = RequestMethod.POST)
	@ApiOperation(value = "2.5添加建议", notes = "添加建议")
	public Map<String,Object> suggest_submit(
			@ApiParam(name = "sf_person_id", value = "id") @RequestParam(name = "sf_person_id") Integer sf_person_id,
			@ApiParam(name = "sc_text", value = "id") @RequestParam(name = "sc_text") String sc_text,
			@ApiParam(name = "send_county", value = "id") @RequestParam(name = "send_county") Integer send_county,
			@ApiParam(name = "sc_type", value = "id") @RequestParam(name = "sc_type") Integer sc_type,
			@ApiParam(name = "title", value = "id") @RequestParam(name = "title") String title
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			FdSuggest list=aipService.suggest_submit(sf_person_id,sc_text,send_county,sc_type,title);
			
			result.put("result", "success");
			result.put("case_id", list.getId());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	
	@RequestMapping(value = "/consult_submit", method = RequestMethod.POST)
	@ApiOperation(value = "2.6添加建议", notes = "添加建议")
	public Map<String,Object> consult_submit(
			@ApiParam(name = "sf_person_id", value = "id") @RequestParam(name = "sf_person_id") Integer sf_person_id,
			@ApiParam(name = "sc_text", value = "id") @RequestParam(name = "sc_text") String sc_text,
			@ApiParam(name = "send_county", value = "id") @RequestParam(name = "send_county") Integer send_county,
			@ApiParam(name = "sc_type", value = "id") @RequestParam(name = "sc_type") Integer sc_type,
			@ApiParam(name = "title", value = "id") @RequestParam(name = "title") String title
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			FdConsult list=aipService.consult_submit(sf_person_id,sc_text,send_county,sc_type,title);
			
			result.put("result", "success");
			result.put("case_id", list.getId());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/complaint_submit", method = RequestMethod.POST)
	@ApiOperation(value = "2.7添加投诉", notes = "添加投诉")
	public Map<String,Object> complaint_submit(
			@ApiParam(name = "sf_person_id", value = "上访人员id") @RequestParam(name = "sf_person_id",required=false) Integer sf_person_id,
			@ApiParam(name = "send_county", value = "接收单位") @RequestParam(name = "send_county",required=false) String send_county,
			@ApiParam(name = "title", value = "标题") @RequestParam(name = "title",required=false) String title,
			@ApiParam(name = "dwid", value = "信访单位id") @RequestParam(name = "dwid",required=false) Integer dwid,
	/*		@ApiParam(name = "complaint_time", value = "投诉时间") @RequestParam(name = "complaint_time",required=false) String complaint_time,*/
			@ApiParam(name = "complaint_location", value = "事件地点") @RequestParam(name = "complaint_location",required=false) String complaint_location,
			@ApiParam(name = "complaint_reason", value = "内容及诉求 ") @RequestParam(name = "complaint_reason",required=false) String complaint_reason,
			@ApiParam(name = "complaint_requirement", value = "内容及诉求（没用的存的固定值11）") @RequestParam(name = "complaint_requirement",required=false) String complaint_requirement,
			@ApiParam(name = "attachment", value = "附件") @RequestParam(name = "attachment",required=false) String attachment,
			@ApiParam(name = "tf_type", value = "投诉类型，0表示个访，1表示群访") @RequestParam(name = "tf_type",required=false) Integer tf_type,
			@ApiParam(name = "tf_num", value = "同访人数") @RequestParam(name = "tf_num",required=false) Integer tf_num,
			@ApiParam(name = "tf_idcards", value = "同访人身份证号") @RequestParam(name = "tf_idcards",required=false) String tf_idcards,
			@ApiParam(name = "tf_names", value = "同访人姓名") @RequestParam(name = "tf_names",required=false) String tf_names,
			@ApiParam(name = "tf_phones", value = "同访人电话") @RequestParam(name = "tf_phones",required=false) String tf_phones,
			@ApiParam(name = "tf_addresses", value = "同访人地址") @RequestParam(name = "tf_addresses",required=false) String tf_addresses,
			@ApiParam(name = "petition_channel", value = "信访渠道  1 现场 2 网络 3 信件") @RequestParam(name = "petition_channel",required=false) Integer petition_channel,
			@ApiParam(name = "complaint_system", value = "问题涉及部门 ") @RequestParam(name = "complaint_system",required=false) Integer complaint_system
			
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			FdCase list=aipService.complaint_submit(sf_person_id,send_county,title,null,complaint_location,complaint_reason,complaint_requirement,attachment,tf_type,tf_num,tf_idcards,tf_names,tf_phones,tf_addresses,petition_channel,complaint_system,null,null,null,dwid,null);
			
			result.put("result", "success");
			result.put("case_id", list.getId());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/complaint_query", method = RequestMethod.POST)
	@ApiOperation(value = "2.8投诉查询", notes = "投诉查询")
	public Map<String,Object> complaint_query(
			@ApiParam(name = "queryType", value = "queryType=0查全部；queryType=3以类别和关键字查询；queryValue关键字|选中的类别") @RequestParam(name = "queryType") Integer queryType,
			@ApiParam(name = "queryValue", value = "用户名") @RequestParam(name = "queryValue") String queryValue,
			@ApiParam(name = "page", value = "页数") @RequestParam(name = "page") Integer page,
			@ApiParam(name = "pageCount", value = "每页条数") @RequestParam(name = "pageCount") Integer pageCount,
			@ApiParam(name = "userId", value = "用户id（非必填）") @RequestParam(name = "userId",required=false) Integer userId
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			List<ComplaintVO> list=aipService.complaint_query(queryType, queryValue, page, pageCount,userId);
			
			result.put("result", "success");
			result.put("complaint_list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/evaluation_submit", method = RequestMethod.POST)
	@ApiOperation(value = "2.9评价的提交", notes = "评价的提交")
	public Map<String,Object> evaluation_submit(
			@ModelAttribute  FpAppraiseEntity entity
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			FpAppraiseEntity list=aipService.evaluation_submit(entity);
			result.put("result", "success");
			result.put("case_id", list.getId());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/show_sf_people_info", method = RequestMethod.POST)
	@ApiOperation(value = "2.10个人信息", notes = "个人信息")
	public Map<String,Object> show_sf_people_info(
			@ApiParam(name = "id", value = "用户id") @RequestParam(name = "id") Integer id
			
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
		//	UserVO list=aipService.show_sf_people_info(id);
			
			result.put("result", "success");
			result.put("person", null);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/modify_personInfo", method = RequestMethod.POST)
	@ApiOperation(value = "2.11个人信息修改", notes = "个人信息修改")
	public Map<String,Object> modify_personInfo(
			@ApiParam(name = "person_id", value = "用户id") @RequestParam(name = "person_id") String person_id,
			@ApiParam(name = "username", value = "用户名") @RequestParam(name = "username") String username,
			@ApiParam(name = "name", value = "名称") @RequestParam(name = "name") String name,
			@ApiParam(name = "phone_num", value = "手机号") @RequestParam(name = "phone_num") String phone_num,
			@ApiParam(name = "idcard", value = "身份证号") @RequestParam(name = "idcard") String idcard,
			@ApiParam(name = "sex", value = "性别") @RequestParam(name = "sex") String sex,
			@ApiParam(name = "nation", value = "名族") @RequestParam(name = "nation") String nation,
			@ApiParam(name = "provincedomain", value = "身份证号") @RequestParam(name = "provincedomain") String provincedomain,
			@ApiParam(name = "citydomain", value = "身份证号") @RequestParam(name = "citydomain") String citydomain,
			@ApiParam(name = "towndomain", value = "身份证号") @RequestParam(name = "towndomain") String towndomain,
			@ApiParam(name = "address", value = "身份证号") @RequestParam(name = "address") String address,
			@ApiParam(name = "work", value = "身份证号") @RequestParam(name = "work") String work,
			@ApiParam(name = "email", value = "身份证号") @RequestParam(name = "email") String email,
			@ApiParam(name = "attachment", value = "身份证号") @RequestParam(name = "attachment") String attachment,
			@ApiParam(name = "birth_date", value = "身份证号") @RequestParam(name = "birth_date") String birth_date,
			@ApiParam(name = "postal_address", value = "身份证号") @RequestParam(name = "postal_address") String postal_address

		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			UserVO list=aipService.modify_personInfo(person_id,username,name,phone_num,idcard,sex,nation,provincedomain,citydomain,towndomain,address,work,email,attachment,birth_date,postal_address,null);
			
			result.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/update_password", method = RequestMethod.POST)
	@ApiOperation(value = "2.12修改密码接口", notes = "修改密码接口")
	public Map<String,Object> update_password(
			@ApiParam(name = "sf_person_id", value = "用户id") @RequestParam(name = "sf_person_id") Integer sf_person_id,
			@ApiParam(name = "old_password", value = "旧密码") @RequestParam(name = "old_password") String old_password,
			@ApiParam(name = "new_password", value = "新密码") @RequestParam(name = "new_password") String new_password
			
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			FdGuest list=aipService.update_password(sf_person_id,old_password,new_password);
			
			result.put("result", "success");
			result.put("person", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/forget_modify_password", method = RequestMethod.POST)
	@ApiOperation(value = "2.13重置密码", notes = "重置密码")
	public Map<String,Object> forget_modify_password(
			@ApiParam(name = "phone", value = "手机号") @RequestParam(name = "phone") String phone,
			@ApiParam(name = "sms_code", value = "验证码") @RequestParam(name = "sms_code") String sms_code,
			@ApiParam(name = "password", value = "新密码") @RequestParam(name = "password") String password
			
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			FdGuest code=aipService.forget_modify_password(phone,sms_code,password);
			
			result.put("result", "success");
			result.put("person", code);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	
	
	
	
	
}
