package com.xinfang.foursectionsdata.controller;

import com.xinfang.context.NewModel;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.foursectionsdata.service.ExpertCaseService;
import com.xinfang.foursectionsdata.service.FourSectionsDataService;
import com.xinfang.foursectionsdata.service.LedgeService;
import com.xinfang.foursectionsdata.service.PolicyLawService;
import com.xinfang.foursectionsdata.service.SpecialPopulationService;
import com.xinfang.log.ApiLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;





@RestController
@RequestMapping("/V1/api/foursectionsdataController")
@Api(description = "四库数据接口(张波)")
@NewModel
public class FourSectionsDataController {
	@Autowired
	private FourSectionsDataService fourSectionsDataService;
	@Autowired
	private PolicyLawService policyLawService;
	@Autowired
	private SpecialPopulationService specialPopulationService;
	@Autowired
	private ExpertCaseService expertCaseService;
	@Autowired
	private LedgeService ledgeService;
	@RequestMapping(value = "/getListByisRepeatPetition", method = RequestMethod.GET)
    @ApiOperation(value = "获取初访重访事项列表", notes = "获取初访重访事项列表")
    public Responses getListByisRepeatPetition(
            @ApiParam(name = "fuzzy", value = "模糊搜索") @RequestParam(required=false,defaultValue="") String fuzzy,
            @ApiParam(name = "belongTo", value = "案件归属地") @RequestParam(required=false,defaultValue="0") Integer belongTo,
            @ApiParam(name = "unitId", value = "单位ID(需同案件归属地一起选)") @RequestParam(required=false,defaultValue="0") Integer unitId,
            @ApiParam(name = "petitionWay", value = "到访方式") @RequestParam(required=false,defaultValue="0") Integer petitionWay,
            @ApiParam(name = "startPage", value = "起始页数 (以0开始)") @RequestParam Integer startPage,
            @ApiParam(name = "pageCount", value = "每页条数") @RequestParam Integer pageCount,
            @ApiParam(name = "isRepeatPetition", value = "(0/初访  1/重访)") @RequestParam Integer isRepeatPetition,
            @ApiParam(name = "startTime", value = "起始时间") @RequestParam(required=false,defaultValue="") String startTime,
            @ApiParam(name = "endTime", value = "结束时间") @RequestParam(required=false,defaultValue="") String endTime
            
    		) {

		Map<String, Object> map=null;
	     try {
	            map = fourSectionsDataService.getListByisRepeatPetition(isRepeatPetition, startPage, pageCount, unitId, belongTo, fuzzy,petitionWay,startTime,endTime);
	                    

	        } catch (Exception e) {
	        	ApiLog.chargeLog1(e.getMessage());
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	    }
	@RequestMapping(value = "/getListByType", method = RequestMethod.GET)
    @ApiOperation(value = "获取个访群体访事项列表", notes = "获取个访群体访事项列表")
    public Responses getListByType(
            @ApiParam(name = "fuzzy", value = "模糊搜索") @RequestParam(name="fuzzy",required=false,defaultValue="") String fuzzy,
            @ApiParam(name = "belongTo", value = "案件归属地") @RequestParam(name="belongTo",required=false,defaultValue="0") Integer belongTo,
            @ApiParam(name = "startPage", value = "起始页数 (以0开始)") @RequestParam(name="startPage") Integer startPage,
            @ApiParam(name = "pageCount", value = "每页条数") @RequestParam(name="pageCount") Integer pageCount,
            @ApiParam(name = "type", value = "(1/个访  2/群体访)") @RequestParam(name="type") Integer type,
            @ApiParam(name = "petitionWay", value = "到访方式") @RequestParam(name="petitionWay",required=false,defaultValue="0") Integer petitionWay,
            @ApiParam(name = "unitId", value = "单位ID(需同案件归属地一起选)") @RequestParam(name="unitId",required=false,defaultValue="0") Integer unitId,
            @ApiParam(name = "startTime", value = "起始时间") @RequestParam(required=false,defaultValue="") String startTime,
            @ApiParam(name = "endTime", value = "结束时间") @RequestParam(required=false,defaultValue="") String endTime
    		) {

		Map<String, Object> map=null;
	     try {
	            map = fourSectionsDataService.getListByType(startPage, pageCount, fuzzy, unitId, belongTo, type, petitionWay,startTime,endTime);
	                    

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	    }
	@RequestMapping(value = "/getListByPetitionType", method = RequestMethod.GET)
    @ApiOperation(value = "获取正常访非正常访列表", notes = "获取正常访非正常访列表")
    public Responses getListByPetitionType(
            @ApiParam(name = "fuzzy", value = "模糊搜索") @RequestParam(name="fuzzy",required=false,defaultValue="") String fuzzy,
            @ApiParam(name = "belongTo", value = "案件归属地") @RequestParam(name="belongTo",required=false,defaultValue="0") Integer belongTo,
            @ApiParam(name = "unitId", value = "单位ID(需同案件归属地一起选)") @RequestParam(name="unitId",required=false,defaultValue="0") Integer unitId,
            @ApiParam(name = "petitionWay", value = "到访方式") @RequestParam(required=false,defaultValue="0") Integer petitionWay,
            @ApiParam(name = "startPage", value = "起始页数 (以0开始)") @RequestParam(name="startPage" ,defaultValue="0") Integer startPage,
            @ApiParam(name = "pageCount", value = "每页条数") @RequestParam(name="pageCount") Integer pageCount,
            @ApiParam(name = "petitionType", value = "信访方式(1,正常访，2非正常访)") @RequestParam(name="petitionType") Integer petitionType,
            @ApiParam(name = "startTime", value = "起始时间") @RequestParam(required=false,defaultValue="") String startTime,
            @ApiParam(name = "endTime", value = "结束时间") @RequestParam(required=false,defaultValue="") String endTime
    		) {

		Map<String, Object> map=null;
	     try {
	            map = fourSectionsDataService.getListByPetitionType(startPage, pageCount, fuzzy, unitId, belongTo,petitionWay, petitionType,startTime,endTime);
	                    

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	    }
	@RequestMapping(value = "/getListByQuestionHot", method = RequestMethod.GET)
    @ApiOperation(value = "获取热点问题列表", notes = "获取热点问题列表")
    public Responses getListByQuestionHot(
            @ApiParam(name = "fuzzy", value = "模糊搜索") @RequestParam(required=false,defaultValue="") String fuzzy,
            @ApiParam(name = "belongTo", value = "案件归属地") @RequestParam(name="belongTo",required=false,defaultValue="0") Integer belongTo,
            @ApiParam(name = "unitId", value = "单位ID(需同案件归属地一起选)") @RequestParam(name="unitId",required=false,defaultValue="0") Integer unitId,
            @ApiParam(name = "startPage", value = "起始页数 (以0开始)") @RequestParam(name="startPage") Integer startPage,
            @ApiParam(name = "pageCount", value = "每页条数") @RequestParam(name="pageCount") Integer pageCount,
            @ApiParam(name = "questionHot", value = "热点问题") @RequestParam(required=false,defaultValue="0") Integer questionHot,
            @ApiParam(name = "petitionWay", value = "到访方式") @RequestParam(required=false,defaultValue="0") Integer petitionWay,
            @ApiParam(name = "startTime", value = "起始时间") @RequestParam(required=false,defaultValue="") String startTime,
            @ApiParam(name = "endTime", value = "结束时间") @RequestParam(required=false,defaultValue="") String endTime
    		) {

		Map<String, Object> map=null;
	     try {
	            map = fourSectionsDataService.getListByQuestionHot(startPage, pageCount, fuzzy, unitId, belongTo, questionHot, petitionWay,startTime,endTime);
	                    

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	    }
	
	@RequestMapping(value = "/getLawList", method = RequestMethod.GET)
    @ApiOperation(value = "获取法规列表", notes = "获取法规列表")
    public Responses getLawList(
    		@ApiParam(name = "fuzzy", value = "模糊搜索") @RequestParam(required=false,defaultValue="") String fuzzy,
            @ApiParam(name = "startPage", value = "起始页数 (以0开始)") @RequestParam(name="startPage") Integer startPage,
            @ApiParam(name = "pageCount", value = "每页条数") @RequestParam(name="pageCount") Integer pageCount,
            @ApiParam(name = "lawType", value = "法规种类") @RequestParam(required=false,defaultValue="0") Integer lawType,
            @ApiParam(name = "startTime", value = "起始时间") @RequestParam(required=false,defaultValue="") String startTime,
            @ApiParam(name = "endTime", value = "结束时间") @RequestParam(required=false,defaultValue="") String endTime
    		) {

		Map<String, Object> map=null;
	     try {
	            map = policyLawService.getLawList(startPage, pageCount, lawType, fuzzy, startTime, endTime);

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	}
	@RequestMapping(value = "/getTypeList", method = RequestMethod.GET)
    @ApiOperation(value = "获取法规种类列表", notes = "获取法规种类列表")
    public Responses getTypeList(
           
    		) {

		Map<String, Object> map=null;
	     try {
	            map = policyLawService.getTypeList();

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	}
	
	@RequestMapping(value = "/getLawDetailsById", method = RequestMethod.GET)
    @ApiOperation(value = "通过ID获取法规详情", notes = "通过ID获取法规详情")
    public Responses getLawDetailsById(
    		 @ApiParam(name = "lawId", value = "Id") @RequestParam Integer lawId
    		) {

		Map<String, Object> map=null;
	     try {
	            map = policyLawService.getLawDetailsById(lawId);

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	}
	@RequestMapping(value = "/getQuestionHotList", method = RequestMethod.GET)
    @ApiOperation(value = "通过Type获取热点问题列表", notes = "通过Type获取热点问题列表")
    public Responses getQuestionHotList(
    	
    		) {

		Map<String, Object> map=null;
	     try {
	            map = fourSectionsDataService.getQuestionHotList();

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	}
	
	@RequestMapping(value = "/countPopulation", method = RequestMethod.GET)
    @ApiOperation(value = "人员统计分析", notes = "人员统计分析")
    public Responses countPopulation(
    		@ApiParam(name = "QXSId", value = "区县市Id") @RequestParam(required=false,defaultValue="0") Integer QXSId,
   		 @ApiParam(name="populationType" ,value="人员类型（1 重点人员 2一般信访人员 3矛盾纠纷人员 ）")@RequestParam(required=false,defaultValue="0") Integer populationType,
   		 @ApiParam(name="sex" ,value="显示性别比例（默认0不显示，1显示）")@RequestParam(required=false,defaultValue="0") Integer sex
    		) {

		Map<String, Object> map=null;
	     try {
	            map = specialPopulationService.countPopulation(QXSId, populationType, sex);

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	}	
	@RequestMapping(value = "/getCaseList", method = RequestMethod.GET)
    @ApiOperation(value = "获取案件列表（专家案例库）", notes = "获取案件列表（专家案例库）")
    public Responses getCaseList(
    	 @ApiParam(name="fuzzy",value="模糊搜索内容")@RequestParam(required=false,defaultValue="")String fuzzy,
		 @ApiParam(name ="startTime", value = "起始时间") @RequestParam(required=false,defaultValue="") String startTime,
  		 @ApiParam(name="endTime" ,value="结束时间")@RequestParam(required=false,defaultValue="") String endTime,
  		 @ApiParam(name="petitionWay" ,value="信访方式")@RequestParam(required=false,defaultValue="0") Integer petitionWay,
  		@ApiParam(name="belongTo" ,value="案件归属地")@RequestParam(required=false,defaultValue="0") Integer belongTo,
  		@ApiParam(name="ResponsibilityUnitId",value="责任单位单位ID")@RequestParam(required=false,defaultValue="0") Integer ResponsibilityUnitId,
  		@ApiParam(name="petitionUnitId",value="信访部门")@RequestParam(required=false,defaultValue="0")Integer petitionUnitId,
  		@ApiParam(name="startPage",value="起始页数")@RequestParam(required=false,defaultValue="0")Integer startPage,
  		@ApiParam(name="pageCount",value="每页条数")@RequestParam(required=false,defaultValue="10")Integer pageCount
  		
   		) {

		Map<String, Object> map=null;
	     try {
	            map = expertCaseService.getCaseList(fuzzy, startTime, endTime, petitionWay, belongTo, petitionUnitId, ResponsibilityUnitId, startPage, pageCount);

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	}
	@RequestMapping(value = "/getExpertList", method = RequestMethod.GET)
    @ApiOperation(value = "获取专家列表（专家案例库）", notes = "获取专家列表（专家案例库）")
    public Responses getExpertList(
    	 @ApiParam(name="fuzzy",value="模糊搜索内容")@RequestParam(required=false,defaultValue="")String fuzzy,
		 @ApiParam(name ="record", value = "学历") @RequestParam(required=false,defaultValue="") String record,
  		 @ApiParam(name="terrytory" ,value="13大类")@RequestParam(required=false,defaultValue="0") Integer terrytory,
  		 @ApiParam(name="ksId" ,value="科室ID")@RequestParam(required=false,defaultValue="0") Integer ksId,
  		@ApiParam(name="departmentId" ,value="部门ID")@RequestParam(required=false,defaultValue="0") Integer departmentId,
  		@ApiParam(name="startPage",value="起始页数")@RequestParam(required=false,defaultValue="0")Integer startPage,
  		@ApiParam(name="pageCount",value="每页条数")@RequestParam(required=false,defaultValue="10")Integer pageCount
  		
   		) {

		Map<String, Object> map=null;
	     try {
	            map = expertCaseService.getExpertList(fuzzy, record, terrytory, ksId, departmentId, startPage, pageCount);

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	}
	@RequestMapping(value = "/getCase", method = RequestMethod.GET)
    @ApiOperation(value = "获取案件详情", notes = "获取案件详情")
    public Responses getExpertList(
    	 @ApiParam(name="caseId",value="案件ID")@RequestParam Integer caseId
   		) {

		Map<String, Object> map=null;
	     try {
	            map = expertCaseService.getCase(caseId);

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	}
	
	
	
	@RequestMapping(value = "/getLedgeList", method = RequestMethod.GET)
    @ApiOperation(value = "三个四级个案台账", notes = "三个四级个案台账")
    public Responses getLedgeList(
    	 @ApiParam(name="fuzzy",value="模糊搜索内容")@RequestParam(required=false,defaultValue="")String fuzzy,
		 @ApiParam(name ="populationType", value = "人员性质（0 一般 1 重点 3矛盾纠纷）") @RequestParam(required=false,defaultValue="0") Integer populationType,
  		 @ApiParam(name="orgLevel" ,value="级别（1 国家级 2 省级 3 市级 4 区县级）")@RequestParam(required=false,defaultValue="0") Integer orgLevel,
  		 @ApiParam(name="petitionWay" ,value="信访方式")@RequestParam(required=false,defaultValue="0") Integer petitionWay,
  		@ApiParam(name="departmentId" ,value="部门ID")@RequestParam(required=false,defaultValue="0") Integer departmentId,
  		@ApiParam(name="belongToId" ,value="归属地ID")@RequestParam(required=false,defaultValue="0") Integer belongToId,
  		 @ApiParam(name ="startTime", value = "起始时间") @RequestParam(required=false,defaultValue="") String startTime,
  		 @ApiParam(name="endTime" ,value="结束时间")@RequestParam(required=false,defaultValue="") String endTime,
  		@ApiParam(name="startPage",value="起始页数")@RequestParam(required=false,defaultValue="0")Integer startPage,
  		@ApiParam(name="pageCount",value="每页条数")@RequestParam(required=false,defaultValue="10")Integer pageCount
  		
   		) {

		Map<String, Object> map=null;
	     try {
	            map = ledgeService.getLedgeList(populationType, orgLevel, fuzzy, petitionWay, belongToId, departmentId, startTime, endTime, startPage, pageCount);

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	}


	@RequestMapping(value = "/getCaseDecs", method = RequestMethod.GET)
    @ApiOperation(value = "获取案件详情(三个四季个案台账)", notes = "获取案件详情(三个四季个案台账)")
    public Responses getCaseDecs(
    	 @ApiParam(name="caseId",value="案件ID")@RequestParam Integer caseId
   		) {

		Map<String, Object> map=null;
	     try {
	            map = ledgeService.getCaseDetailById(caseId);

	        } catch (Exception e) {
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, map);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, map);
	}



}
