package com.xinfang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.VO.FdCaseFeedbackVO;
import com.xinfang.VO.TaskVO;
import com.xinfang.context.PageFinder;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.ResponseOth;
import com.xinfang.dao.FcDwbRepository;
import com.xinfang.dao.FdCaseFeedbackAllRepository;
import com.xinfang.dao.FdDepCaseRepository;
import com.xinfang.model.FdCase;
import com.xinfang.model.FdDepCase;
import com.xinfang.service.BusinessService;
import com.xinfang.service.CaseService;
import com.xinfang.service.FdCaseFeedbackAllService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/V1/business")
@Api(description = "业务类（张华荣）")
public class BusinessController {
	@Autowired
	FdCaseFeedbackAllService fdCaseFeedbackAllService;
	@Autowired
	CaseService caseService;
	@Autowired
	BusinessService businessService;
	
	@Autowired
	FdCaseFeedbackAllRepository fdCaseFeedbackAllRepository;
	@Autowired
	FdDepCaseRepository fdDepCaseRepository;
	@Autowired
	FcDwbRepository fcDwbRepository;
	
	
	
	@RequestMapping(value = "/flowdetails", method = RequestMethod.GET)
	@ApiOperation(value = "根据案件id获取流程信息", notes = "根据案件id获取流程信息按照部门排序")
	public ResponseOth gettask(@ApiParam(name = "caseid", value = "案件id") @RequestParam(name = "caseid") Integer caseid

	) {
		List<FdCaseFeedbackVO> lists = null;
		try {
			
			lists=businessService.flowdetails(caseid);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", "暂未实现");

		}

		return new ResponseOth(ResponseConstants.CODE_OK, lists, "已经实现");

	}
	
	@RequestMapping(value = "/flowdetailsbytime", method = RequestMethod.GET)
	@ApiOperation(value = "根据案件id获取流程信息安时间排序", notes = "根据案件id获取流程信息安时间排序")
	public ResponseOth flowdetailsbytime(@ApiParam(name = "caseid", value = "案件id") @RequestParam(name = "caseid") Integer caseid

	) {
		List<FdCaseFeedbackVO> lists = null;
		try {
			
			lists=businessService.flowdetailsbytimenew(caseid);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", "暂未实现");

		}

		return new ResponseOth(ResponseConstants.CODE_OK, lists, "已经实现");

	}
	
	
	@RequestMapping(value = "/getreceivecase", method = RequestMethod.GET)
	@ApiOperation(value = "根据单位id获取收文岗案件", notes = "根据单位id获取收文岗案件")
	public ResponseOth getreceivecase(@ApiParam(name = "depid", value = "单位id") @RequestParam(name = "depid") Integer depid,
			@ApiParam(name = "startpage", value = "开始页数") @RequestParam(name = "startpage") int startpage,
			@ApiParam(name = "pagesize", value = "每页条数") @RequestParam(name = "pagesize") int pagesize,
			@ApiParam(name = "state", value = "0 全部  1交办 2转办 3待审核 4待处理 5待办结") @RequestParam(name = "state",defaultValue="0") int state

	) {
		PageFinder<TaskVO> tasks = null;
		try {
			 tasks=businessService.getreceivecase(depid,startpage,pagesize,0);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", "暂未实现");

		}

		return new ResponseOth(ResponseConstants.CODE_OK, tasks, "操作成功");

	}
	
	
	@RequestMapping(value = "/getreturncase", method = RequestMethod.GET)
	@ApiOperation(value = "根据用户id获取退回案件", notes = "根据用户id获取退回案件")
	public ResponseOth getreturncase(@ApiParam(name = "userId", value = "用户id") @RequestParam(name = "userId") Integer userId,
			@ApiParam(name = "startpage", value = "开始页数") @RequestParam(name = "startpage") int startpage,
			@ApiParam(name = "pagesize", value = "每页条数") @RequestParam(name = "pagesize") int pagesize

	) {
		PageFinder<FdCase> tasks=null;
		try {
			 tasks=businessService.getreturncase(userId,startpage,pagesize);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());

		}

		return new ResponseOth(ResponseConstants.CODE_OK, tasks, "成功");

	}
	
	
	
	/*@RequestMapping(value = "/getlastUpdateTime", method = RequestMethod.GET)
	@ApiOperation(value = "获取案件最新的更新时间", notes = "获取案件最新的更新时间")
	public ResponseOth getlastUpdateTime(@ApiParam(name = "caseid", value = "案件id") @RequestParam(name = "caseid") long caseid,
			@ApiParam(name = "depid", value = "部门id") @RequestParam(name = "depid") int depid
			

	) {
		int tasks=0;
		try {
			 tasks=fdDepCaseRepository.countByCaseIdAndDepId(caseid,depid);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());

		}

		return new ResponseOth(ResponseConstants.CODE_OK, tasks, "成功");

	}*/
	
	
	@RequestMapping(value = "/fddepcase", method = RequestMethod.GET)
	@ApiOperation(value = "获取单位处理期限以及处理情况", notes = "获取单位处理期限以及处理情况")
	public ResponseOth fddepcase(@ApiParam(name = "caseid", value = "案件id") @RequestParam(name = "caseid") long caseid,
			@ApiParam(name = "depid", value = "单位id") @RequestParam(name = "depid") int depid

	) {
		FdDepCase lists = null;
		try {
			
			lists=businessService.fddepcase(caseid,depid);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", "暂未实现");

		}

		return new ResponseOth(ResponseConstants.CODE_OK, lists, "已经实现");

	}
	
	
}