package com.xinfang.supervise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.context.NewModel;
import com.xinfang.context.PageFinder;
import com.xinfang.context.Respon;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.ResponseOth;
import com.xinfang.supervise.VO.FdCaseVO;
import com.xinfang.supervise.model.Supervise;
import com.xinfang.supervise.model.SuperviseDetail;
import com.xinfang.supervise.service.SuperviseService;

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
@RequestMapping("/V1/supervise")
@Api(description = "督责问责（张华荣）")
@NewModel
public class SuperviseController {
	
	@Autowired
	SuperviseService superviseService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ApiOperation(value = "1 督责管理", notes = "督责管理")
	public PageFinder<Supervise> index(@ApiParam(name = "startdate", value = "开始日期") @RequestParam(name = "startdate",required=false) String startdate,
			@ApiParam(name = "endDate", value = "结束日期") @RequestParam(name = "endDate",required=false) String endDate,
			@ApiParam(name = "key", value = "关键词") @RequestParam(name = "key",required=false) String key,
			@ApiParam(name = "page", value = "当前页数") @RequestParam(name = "page") Integer page,
			@ApiParam(name = "pagesize", value = "每页条数") @RequestParam(name = "pagesize") Integer pagesize,
			@ApiParam(name = "state", value = "1查询全部,0按照条件查询",required=false,defaultValue="1") @RequestParam(name = "state") Integer state,
			@ApiParam(name = "type", value = "1督责列表  ,2历史督责列表 ,3问责列表 ,4历史问责列表 ,5被退回问责列表,6问责过程列表") @RequestParam(name = "type") Integer type,
			@ApiParam(name = "endstate", value = "0查询全部 1已经问责 2未问责",required=false,defaultValue="0") @RequestParam(name = "endstate") Integer endstate
			

	) {
		PageFinder<Supervise> result = null;
		try {
			result=superviseService.query(startdate,endDate,key,page,pagesize,state,type,endstate);
			//result.getResult();
		} catch (Exception e) {
			e.printStackTrace();
			result = new PageFinder<Supervise>(0,e.getMessage());
		}
		result.setCode(1);
		result.setMsg("操作成功");
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ApiOperation(value = "2发起督责", notes = "发起督责")
	public ResponseOth create(
			/*@ApiParam(name = "caseId", value = "案件id") @RequestParam(name = "caseId") 	Integer caseId,*/
			@ApiParam(name = "createDepId", value = "发起者单位id") @RequestParam(name = "createDepId") Integer createDepId,
			@ApiParam(name = "createDepName", value = "发起者单位名称") @RequestParam(name = "createDepName") String createDepName,
			@ApiParam(name = "userId", value = "创建者id") @RequestParam(name = "userId") Integer userId,
			@ApiParam(name = "accidentDepId", value = "被问责单位id") @RequestParam(name = "accidentDepId") Integer accidentDepId,
			@ApiParam(name = "accidentDepName", value = "被问责单位名称") @RequestParam(name = "accidentDepName") String accidentDepName,
			@ApiParam(name = "accidentUsers", value = "被问责人员用多个用逗号隔开") @RequestParam(name = "accidentUsers") String accidentUsers,
			@ApiParam(name = "accidentReason", value = "督责原因") @RequestParam(name = "accidentReason") String accidentReason,
			@ApiParam(name = "accidentType", value = "督责分类") @RequestParam(name = "accidentType") String accidentType,
			@ApiParam(name = "enclosure", value = "附件") @RequestParam(name = "enclosure",required=false) String enclosure
			

	) {
		try {
			superviseService.create(createDepId,createDepName,userId,accidentDepId,accidentDepName,accidentUsers,accidentReason,accidentType,enclosure);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, e.getMessage(), "暂未实现");
		}

		return new ResponseOth(ResponseConstants.CODE_OK, 1, "已经实现");
	}
	
	
	@RequestMapping(value = "/getFdCases", method = RequestMethod.GET)
	@ApiOperation(value = "3获取关联案件", notes = "获取关联案件")
	public PageFinder<FdCaseVO> getFdCases(
			
			@ApiParam(name = "caseIds", value = "案件ids") @RequestParam(name = "caseIds") Integer[] caseIds
	) {
		PageFinder<FdCaseVO> result =null;
		try {
			result = superviseService.getcases(caseIds);
		} catch (Exception e) {
			e.printStackTrace();
			result = new PageFinder<FdCaseVO>(0,e.getMessage());
		}
		result.setCode(1);
		result.setMsg("操作成功");
		return result;
	}
	

	@RequestMapping(value = "/getSuperviseDetails", method = RequestMethod.GET)
	@ApiOperation(value = "4 督责问责详情列表", notes = "督责问责详情列表")
	public PageFinder<SuperviseDetail> getSuperviseDetails(
			@ApiParam(name = "superviseDetailIds", value = "督责ids") @RequestParam(name = "superviseDetailIds") Integer[] superviseDetailIds

	) {
		PageFinder<SuperviseDetail> result = null;
		try {
			result=superviseService.queryDetails(superviseDetailIds,1);
			//result.getResult();
		} catch (Exception e) {
			e.printStackTrace();
			result = new PageFinder<SuperviseDetail>(0,e.getMessage());
		}
		result.setCode(1);
		result.setMsg("操作成功");
		return result;

	}
	
	

	@RequestMapping(value = "/getSuperviseDetail", method = RequestMethod.GET)
	@ApiOperation(value = "5 督责问责详情", notes = "督责问责详情")
	public Respon<SuperviseDetail> getSuperviseDetail(
			
			@ApiParam(name = "superviseDetailId", value = "督责id") @RequestParam(name = "superviseDetailId") Integer superviseDetailId
			
			

	) {
		Respon<SuperviseDetail> result =null;
		try {
		result = superviseService.getSuperviseDetail(superviseDetailId);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Respon<SuperviseDetail>(0,e.getMessage());
		}
		result.setCode(1);
		result.setMsg("操作成功");
		return result;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/wzcreate", method = RequestMethod.POST)
	@ApiOperation(value = "6发起问责", notes = "发起问责")
	public ResponseOth wzcreate(
			@ApiParam(name = "createDepId", value = "发起者单位id") @RequestParam(name = "createDepId") Integer createDepId,
			@ApiParam(name = "createDepName", value = "发起者单位名称") @RequestParam(name = "createDepName") String createDepName,
			@ApiParam(name = "userId", value = "创建者id") @RequestParam(name = "userId") Integer userId,
			@ApiParam(name = "accidentDepId", value = "被问责单位id") @RequestParam(name = "accidentDepId") Integer accidentDepId,
			@ApiParam(name = "accidentDepName", value = "被问责单位名称") @RequestParam(name = "accidentDepName") String accidentDepName,
			@ApiParam(name = "accidentUsers", value = "被问责人员用多个用逗号隔开") @RequestParam(name = "accidentUsers") String accidentUsers,
			@ApiParam(name = "accidentReason", value = "问责原因") @RequestParam(name = "accidentReason") String accidentReason,
			@ApiParam(name = "accidentType", value = "问责内容") @RequestParam(name = "accidentType") String accidentType,
			@ApiParam(name = "enclosure", value = "附件") @RequestParam(name = "enclosure") String enclosure,
			@ApiParam(name = "appointtime", value = "约谈时间") @RequestParam(name = "appointtime") String appointtime,
			@ApiParam(name = "appointaddress", value = "约谈地点") @RequestParam(name = "appointaddress") String appointaddress,
			@ApiParam(name = "appointuser", value = "约谈人多个用逗号隔开") @RequestParam(name = "appointuser") String appointuser
			

	) {
		try {
			superviseService.create(createDepId,createDepName,userId,accidentDepId,accidentDepName,accidentUsers,accidentReason,accidentType,enclosure,appointtime,appointaddress,appointuser);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, e.getMessage(), "暂未实现");
		}
		return new ResponseOth(ResponseConstants.CODE_OK, 1, "已经实现");
	}
	
	
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	@ApiOperation(value = "7回复确认", notes = "回复确认")
	public ResponseOth confirm(
			
			@ApiParam(name = "superviseDetailId", value = "问责详情id") @RequestParam(name = "superviseDetailId") Integer superviseDetailId,
			@ApiParam(name = "state", value = "2确认 3退回") @RequestParam(name = "state") Integer state,
			@ApiParam(name = "reason", value = "退回原因") @RequestParam(name = "reason",required=false) String reason,
			@ApiParam(name = "user", value = "问责对象") @RequestParam(name = "user",required=false) String user
			
			

	) {
		try {
			superviseService.confirm(superviseDetailId,state,reason,user);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, e.getMessage(), "暂未实现");
		}

		return new ResponseOth(ResponseConstants.CODE_OK, 1, "已经实现");

	}
	
	
	
	@RequestMapping(value = "/recordcreate", method = RequestMethod.POST)
	@ApiOperation(value = "9 问责记录", notes = "问责记录")
	public ResponseOth recordcreate(
			
			@ApiParam(name = "superviseDetailId", value = "问责详情id") @RequestParam(name = "superviseDetailId") Integer superviseDetailId,
			@ApiParam(name = "msg", value = "问责记录") @RequestParam(name = "msg") String msg,
			@ApiParam(name = "enclosure1", value = "问责记录附件") @RequestParam(name = "enclosure1") String enclosure1
			
			

	) {
		
		{
			try {
				superviseService.recordcreate(superviseDetailId,msg,enclosure1);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseOth(ResponseConstants.MY_CODE_FAILED, e.getMessage(), "暂未实现");
			}

			return new ResponseOth(ResponseConstants.CODE_OK, 1, "已经实现");
	}
	}
	
	

	@RequestMapping(value = "/indexoth", method = RequestMethod.GET)
	@ApiOperation(value = "10回复确认列表,被退回问责列表,问责过程记录列表", notes = "回复确认列表,被退回问责列表,问责过程记录列表")
	public PageFinder<SuperviseDetail> indexoth(@ApiParam(name = "startdate", value = "开始日期") @RequestParam(name = "startdate",required=false) String startdate,
			@ApiParam(name = "endDate", value = "结束日期") @RequestParam(name = "endDate",required=false) String endDate,
			@ApiParam(name = "key", value = "关键词") @RequestParam(name = "key",required=false) String key,
			@ApiParam(name = "page", value = "当前页数") @RequestParam(name = "page") Integer page,
			@ApiParam(name = "pagesize", value = "每页条数") @RequestParam(name = "pagesize") Integer pagesize,
			@ApiParam(name = "type", value = "1回复确认列表 ,2被退回问责列表,3问责过程记录列表") @RequestParam(name = "type") Integer type,
			@ApiParam(name = "depId", value = "单位id") @RequestParam(name = "depId") Integer depId,
			@ApiParam(name = "endstate", value = "0查询全部 1已经问责 2未问责",required=false,defaultValue="0") @RequestParam(name = "endstate") Integer endstate


	) {
		PageFinder<SuperviseDetail> result = null;
		try {
			result=superviseService.queryoth(startdate,endDate,key,page,pagesize,type,depId,endstate);
			//result.getResult();
		} catch (Exception e) {
			e.printStackTrace();
			result = new PageFinder<SuperviseDetail>(0,e.getMessage());
		}
		result.setCode(1);
		result.setMsg("操作成功");
		return result;
	}
	

	
	
}
