package com.xinfang.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.app.VO.Address;
import com.xinfang.app.VO.ComType;
import com.xinfang.app.VO.ComplaintVO;
import com.xinfang.app.VO.Qsx;
import com.xinfang.app.VO.UserVO;
import com.xinfang.app.model.FdConsult;
import com.xinfang.app.model.FdSuggest;
import com.xinfang.app.service.AipService;
import com.xinfang.app.service.AppService;
import com.xinfang.context.AppModel;
import com.xinfang.dao.LawRulesDao;
import com.xinfang.evaluating.model.FpAppraiseEntity;
import com.xinfang.model.FdCase;
import com.xinfang.model.LawRulesEntity;
import com.xinfang.utils.RuleUtil;

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
@RequestMapping("/data")
@Api(description = "app接口（张华荣）")
@AppModel
@CrossOrigin(allowedHeaders="*",allowCredentials="true")
public class DataController {
	@Autowired
	AppService appService;
	
	@Autowired
	LawRulesDao lawRulesDao;
	@Autowired
	AipService aipService;
	
	@RequestMapping(value = "/suggest_submit", method = RequestMethod.POST)
	@ApiOperation(value = "7民意征集（建议）", notes = "民意征集（建议）")
	public Map<String,Object> suggest_submit(
			@ApiParam(name = "sf_person_id", value = "id") @RequestParam(name = "sf_person_id") Integer sf_person_id,
			@ApiParam(name = "sc_text", value = "id") @RequestParam(name = "sc_text") String sc_text,
			@ApiParam(name = "send_county", value = "id") @RequestParam(name = "send_county") Integer send_county,
			@ApiParam(name = "sc_type", value = "id") @RequestParam(name = "sc_type") Integer sc_type,
			@ApiParam(name = "title", value = "id") @RequestParam(name = "title") String title
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			FdSuggest list=appService.suggest_submit(sf_person_id,sc_text,send_county,sc_type,title);
			
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
	@ApiOperation(value = "8民意征集（咨询）", notes = "民意征集（咨询）")
	public Map<String,Object> consult_submit(
			@ApiParam(name = "sf_person_id", value = "id") @RequestParam(name = "sf_person_id") Integer sf_person_id,
			@ApiParam(name = "sc_text", value = "id") @RequestParam(name = "sc_text") String sc_text,
			@ApiParam(name = "send_county", value = "id") @RequestParam(name = "send_county") Integer send_county,
			@ApiParam(name = "sc_type", value = "id") @RequestParam(name = "sc_type") Integer sc_type,
			@ApiParam(name = "title", value = "id") @RequestParam(name = "title") String title
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			FdConsult list=appService.consult_submit(sf_person_id,sc_text,send_county,sc_type,title);
			
			result.put("result", "success");
			result.put("case_id", list.getId());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/show_complaint_type", method = RequestMethod.POST)
	@ApiOperation(value = "4政策法规分类", notes = "政策法规分类")
	public Map<String,Object> show_complaint_type(
		
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			List<ComType>  list =	RuleUtil.gettype();
			result.put("result", "success");
			result.put("type_dict", list);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	
	@RequestMapping(value = "/law_query", method = RequestMethod.POST)
	@ApiOperation(value = "5政策法规查询", notes = "政策法规查询")
	public Map<String,Object> law_query(
			@ApiParam(name = "queryType", value = "queryType=0查全部；queryType=3以类别和关键字查询；queryValue关键字|选中的类别") @RequestParam(name = "queryType") Integer queryType,
			@ApiParam(name = "queryValue", value = "用户名") @RequestParam(name = "queryValue") String queryValue,
			@ApiParam(name = "page", value = "页数") @RequestParam(name = "page") Integer page,
			@ApiParam(name = "pageCount", value = "每页条数") @RequestParam(name = "pageCount") Integer pageCount
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			List<LawRulesEntity> list=appService.law_query(queryType, queryValue, page, pageCount);
			
			result.put("result", "success");
			result.put("law_list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/get_law_content", method = RequestMethod.POST)
	@ApiOperation(value = "6政策法规详情", notes = "政策法规详情")
	public Map<String,Object> get_law_content(
			@ApiParam(name = "law_id", value = "id") @RequestParam(name = "law_id") Integer law_id
		
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			LawRulesEntity list=appService.get_law_content(law_id);
			
			result.put("result", "success");
			result.put("content", list.getContent());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/android_suggest_old", method = RequestMethod.POST)
	@ApiOperation(value = "2建议查询接口", notes = "建议查询接口")
	public Map<String,Object> android_suggest_old(
			@ApiParam(name = "queryType", value = "类型 值为1按照用户id查询 值为2按照建议id查询详情 值为4查询isopen的数据") @RequestParam(name = "queryType") Integer queryType,
			@ApiParam(name = "queryValue", value = "用户id或者建议id") @RequestParam(name = "queryValue") String queryValue,
			@ApiParam(name = "page", value = "页码") @RequestParam(name = "page") Integer page,
			@ApiParam(name = "pageCount", value = "每页条数") @RequestParam(name = "pageCount") Integer pageCount
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
             if(queryType.intValue()==2){//查询详情
				
			}
	    	List<FdSuggest> list =	appService.searchFdSuggest(queryType, queryValue, page, pageCount);
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
	@ApiOperation(value = "3咨询查询接口", notes = "咨询查询接口")
	public Map<String,Object> android_consult_old(
			@ApiParam(name = "queryType", value = "类型 值为1按照用户id查询 值为2按照咨询id查询详情 值为4查询isopen的数据") @RequestParam(name = "queryType") Integer queryType,
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
	
	@RequestMapping(value = "/complaint_submit", method = RequestMethod.POST)
	@ApiOperation(value = "9添加投诉", notes = "添加投诉")
	public Map<String,Object> complaint_submit(
			@ApiParam(name = "sf_person_id", value = "上访人员id") @RequestParam(name = "sf_person_id",required=false) Integer sf_person_id,
			@ApiParam(name = "send_county", value = "接收单位") @RequestParam(name = "send_county",required=false) String send_county,
			@ApiParam(name = "title", value = "标题") @RequestParam(name = "title",required=false) String title,
			@ApiParam(name = "dwid", value = "信访单位id") @RequestParam(name = "dwid",required=false) Integer dwid,
			@ApiParam(name = "complaint_time", value = "案件时间 格式2017-10-10") @RequestParam(name = "complaint_time",required=false) String complaint_time,
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
			@ApiParam(name = "complaint_system", value = "问题涉及部门 ") @RequestParam(name = "complaint_system",required=false) Integer complaint_system,
			@ApiParam(name = "xffs", value = "信访方式 ") @RequestParam(name = "xffs",required=false) Integer xffs,
			@ApiParam(name = "files", value = "附件信息") @RequestParam(name = "files",required=false) String files,
			@ApiParam(name = "dispatcher_to_userid", value = "收文岗人员") @RequestParam(name = "dispatcher_to_userid",required=false) Integer dispatcher_to_userid


		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			//dispatcher_to_userid
			FdCase list=aipService.complaint_submit(sf_person_id,send_county,title,complaint_time,complaint_location,complaint_reason,complaint_requirement,attachment,tf_type,tf_num,tf_idcards,tf_names,tf_phones,tf_addresses,petition_channel,complaint_system,xffs,files,null,dwid,dispatcher_to_userid);
			
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
	@ApiOperation(value = "10投诉查询", notes = "投诉查询")
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
	
	
	
	@RequestMapping(value = "/show_sf_people_info", method = RequestMethod.POST)
	@ApiOperation(value = "17个人信息", notes = "个人信息")
	public Map<String,Object> show_sf_people_info(
			@ApiParam(name = "sf_person_id", value = "用户id") @RequestParam(name = "sf_person_id") Integer sf_person_id
			
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			UserVO list=aipService.show_sf_people_info(sf_person_id);
			
			result.put("result", "success");
			result.put("person", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/modify_personInfo", method = RequestMethod.POST)
	@ApiOperation(value = "18个人信息修改", notes = "个人信息修改")
	public Map<String,Object> modify_personInfo(
			@ApiParam(name = "person_id", value = "用户id") @RequestParam(name = "person_id") String person_id,
			@ApiParam(name = "username", value = "用户名") @RequestParam(name = "username",required=false) String username,
			@ApiParam(name = "name", value = "名称") @RequestParam(name = "name",required=false) String name,
			@ApiParam(name = "phone_num", value = "手机号") @RequestParam(name = "phone_num",required=false) String phone_num,
			@ApiParam(name = "idcard", value = "身份证号") @RequestParam(name = "idcard",required=false) String idcard,
			@ApiParam(name = "sex", value = "性别") @RequestParam(name = "sex",required=false) String sex,
			@ApiParam(name = "nation", value = "名族") @RequestParam(name = "nation",required=false) String nation,
			@ApiParam(name = "provincedomain", value = "现居住地 市 ") @RequestParam(name = "provincedomain",required=false) String provincedomain,
			@ApiParam(name = "citydomain", value = "现居住地 区") @RequestParam(name = "citydomain",required=false) String citydomain,
			@ApiParam(name = "towndomain", value = "现居住地 省") @RequestParam(name = "towndomain",required=false) String towndomain,
			@ApiParam(name = "address", value = "地址") @RequestParam(name = "address",required=false) String address,
			@ApiParam(name = "work", value = "职业") @RequestParam(name = "work",required=false) String work,
			@ApiParam(name = "email", value = "身份证号") @RequestParam(name = "email",required=false) String email,
			@ApiParam(name = "attachment", value = "身份证号") @RequestParam(name = "attachment",required=false) String attachment,
			@ApiParam(name = "birth_date", value = "出生日期") @RequestParam(name = "birth_date",required=false) String birth_date,
			@ApiParam(name = "postal_address", value = "联系地址") @RequestParam(name = "postal_address",required=false) String postal_address,
			@ApiParam(name = "files", value = "附件信息") @RequestParam(name = "files",required=false) String files


		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			UserVO list=aipService.modify_personInfo(person_id,username,name,phone_num,idcard,sex,nation,provincedomain,citydomain,towndomain,address,work,email,attachment,birth_date,postal_address,files);
			
			result.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	
	
	@RequestMapping(value = "/show_qsx_name", method = RequestMethod.POST)
	@ApiOperation(value = "26问题属地", notes = "问题属地")
	public Map<String,Object> show_qsx_name(
			
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			List<Qsx> lists = appService.show_qsx_name(15);
			
			result.put("result", "success");
			result.put("qsx_name_list", lists);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/get_nation", method = RequestMethod.POST)
	@ApiOperation(value = "27省份列表", notes = "省份列表")
	public Map<String,Object> get_nation(
			@ApiParam(name = "c_id", value = "c_id") @RequestParam(name = "c_id",required=false) String c_id,
			@ApiParam(name = "type", value = "地区类型， 1、2、3  省市县、区") @RequestParam(name = "type",required=false) String type

			
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			List<Address> address = appService.get_nation(c_id,type);
			
			result.put("result", "success");
			result.put("listJson", address);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/evaluation_submit", method = RequestMethod.POST)
	@ApiOperation(value = "评价的提交", notes = "评价的提交")
	public Map<String,Object> evaluation_submit(
			@ApiParam(name = "case_id", value = "案件id") @RequestParam(name = "case_id",required=false) Integer case_id,
			@ApiParam(name = "user_id", value = "用户id") @RequestParam(name = "user_id",required=false) Integer user_id,
			@ApiParam(name = "type", value = "1是对信访部门的评价，2是对职能部门评价 3对案件的评价") @RequestParam(name = "type",required=false) Integer type,
			@ApiParam(name = "context", value = "对部门单位或者职能部门评价的内容") @RequestParam(name = "context",required=false) String context,
			@ApiParam(name = "star", value = "对办案结果的评价,star：1901 非常满意,1902比较满意,1903满意,1904一般,1905不满意") @RequestParam(name = "star",required=false) Integer star,
			@ApiParam(name = "reason", value = "不满意的原因") @RequestParam(name = "reason",required=false) String reason


		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			FpAppraiseEntity list=aipService.evaluation_submit1(case_id,user_id,type,context,star,reason);
			result.put("result", "success");
			result.put("case_id", list.getId());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/getComments", method = RequestMethod.GET)
	@CrossOrigin(allowedHeaders="*",allowCredentials="true")
	@ApiOperation(value = "查看自己案件的评价情况", notes = "查看自己案件的评价情况")
	public Map<String,Object> getComments(
			@ApiParam(name = "user_id", value = "用户id") @RequestParam(name = "user_id",required=false) Integer user_id,
			@ApiParam(name = "case_id", value = "案件id") @RequestParam(name = "case_id",required=false) Integer case_id

			
		
	) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			List<FpAppraiseEntity> address = appService.getComments(user_id,case_id);
			
			result.put("result", "success");
			result.put("listJson", address);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("error_msg", e.getMessage());
		}
		
		return result;
	}
	
}
