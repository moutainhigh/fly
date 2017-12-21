package com.xinfang.evaluating.controller;

import java.util.List;
import java.util.Map;

import com.xinfang.context.NewModel;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.evaluating.model.TemplateMaintenanceEntity;
import com.xinfang.evaluating.service.ScoreService;
import com.xinfang.evaluating.service.TemplateMaintenanceService;
import com.xinfang.log.ApiLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 考核分数视图控制器
 * Created by sunbjx on 2017/5/16.
 */
@NewModel()
@RestController
@RequestMapping("/api/v1/score")
@Api(description = "投诉办理-考核分数(孙帮雄)")
public class ScoreController {
	@Autowired
	private TemplateMaintenanceService templateMaintenanceService;
	@Autowired
	private ScoreService scoreService;
	@RequestMapping(value = "/deleteTemlate", method = RequestMethod.GET)
    @ApiOperation(value = "通过ID删除记录(模板维护)", notes = "通过ID删除记录")

    public Responses getListByisRepeatPetition(
    		 @ApiParam(name = "id", value = "ID") @RequestParam Integer id
    		) {

		Map<String, Object> map=null;
	     try {
	            map = templateMaintenanceService.deleteTemlate(id);
	                    

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
	@RequestMapping(value = "/saveAndupdateTemlate", method = RequestMethod.POST)
    @ApiOperation(value = "添加修改评分记录(模板维护)", notes = "添加修改评分记录")
    public Responses saveAndupdateTemlate(
            @ModelAttribute TemplateMaintenanceEntity tem)
            {
        Map<String, Object> map = null;
        try {
            map =templateMaintenanceService.saveAndupdateTemlate(tem) ;

        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, map);
        }

        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, map);
    }
	@RequestMapping(value = "/getTemplateMaintenanceList", method = RequestMethod.GET)
    @ApiOperation(value = "获取评分列表(模板维护)", notes = "获取评分列表")
    public Responses getTemplateMaintenanceList(
            @ApiParam(name = "type", value = "种类（0 A类，1 B类）") @RequestParam Integer type
            ) {
          Map<String, Object> map = null;
          try {
              map = templateMaintenanceService.getTemplateMaintenanceList(type);

          } catch (Exception e) {
              return new Responses(ResponseConstants.SUCCESS_FAILED,
                      ResponseConstants.CODE_FAILED,
                      ResponseConstants.CODE_FAILED_VALUE, map);
          }

          return new Responses(ResponseConstants.SUCCESS_OK,
                  ResponseConstants.CODE_SUCCESS,
                  ResponseConstants.CODE_SUCCESS_VALUE, map);
      }
	@RequestMapping(value = "/getScoreList", method = RequestMethod.GET)
    @ApiOperation(value = "获取评分描述", notes = "获取评分描述")
    public Responses getScoreList(
    		 @ApiParam(name = "assessId", value = "考核ID") @RequestParam Integer assessId,
    		 @ApiParam(name = "unitId", value = "考核单位ID") @RequestParam Integer unitId,
    		 @ApiParam(name = "unitType", value = "考核单位类型") @RequestParam Integer unitType   		 
    		) {

		Map<String, Object> map=null;
	     try {
	            map = scoreService.scoreList(assessId, unitId, unitType);
	                  
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
	@RequestMapping(value = "/getAssessDetail", method = RequestMethod.GET)
    @ApiOperation(value = "获取考核项目详情", notes = "获取考核项目详情")
    public Responses getAssessDetail(
    		 @ApiParam(name = "assessId", value = "考核项目ID") @RequestParam Integer assessId
    		) {

		Map<String, Object> map=null;
	     try {
	            map = scoreService.getAssessDetail(assessId);
	                    

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
	
	@RequestMapping(value = "/getUpdateScore", method = RequestMethod.POST)
    @ApiOperation(value = "修改评分项参数", notes = "修改评分项参数")
    public Responses getUpdateScore(
    		@RequestParam(required=false,defaultValue="")List<String> list
    		) {
		Map<String, Object> returnMap=null;
		try {
		String lists=list.toString().replaceAll("\\[", "").replaceAll("\\]", "");
		String listsSubString=lists.toString();
		
		System.err.println("listString "+listsSubString);
		List<Map> listMap=com.alibaba.fastjson.JSON.parseArray("["+listsSubString+"]", Map.class);
	    	 returnMap=scoreService.updateScore(listMap);
	        } catch (Exception e) {
	        	ApiLog.chargeLog1(e.getMessage());
	        	System.err.println(e.getMessage());
	            return new Responses(ResponseConstants.SUCCESS_FAILED,
	                    ResponseConstants.CODE_FAILED,
	                    ResponseConstants.CODE_FAILED_VALUE, returnMap);
	        }

	        return new Responses(ResponseConstants.SUCCESS_OK,
	                ResponseConstants.CODE_SUCCESS,
	                ResponseConstants.CODE_SUCCESS_VALUE, returnMap);
	    }
	@RequestMapping(value = "/getScoreDetail", method = RequestMethod.GET)
    @ApiOperation(value = "通过考核积分ID获取详情", notes = "通过考核积分ID获取详情")

    public Responses getScoreDetail(
    		 @ApiParam(name = "scoreId", value = "考核积分ID") @RequestParam Integer scoreId
    		) {

		Map<String, Object> map=null;
	     try {
	            map = scoreService.getScoreDetail(scoreId);
	                    

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
	@RequestMapping(value = "/getScoreBetailById", method = RequestMethod.GET)
    @ApiOperation(value = "通过ID获取说明评价编辑详情", notes = "通过ID获取说明评价编辑详情")

    public Responses getScoreBetailById(
    		 @ApiParam(name = "scoreId", value = "考核积分ID") @RequestParam Integer scoreId
    		) {

		Map<String, Object> map=null;
	     try {
	            map = scoreService.getScoreBetailById(scoreId);
	                    

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
	@RequestMapping(value = "/updateFileNameAndDesc", method = RequestMethod.GET)
    @ApiOperation(value = "通过考核ID修改文件名及描述", notes = "通过考核ID修改文件名及描述")

    public Responses updateFileNameAndDesc(
    		 @ApiParam(name = "scoreId", value = "考核积分ID") @RequestParam Integer scoreId,
    		 @ApiParam(name = "fileName", value = "文件名") @RequestParam(required=false,defaultValue="") String  fileName,
    		 @ApiParam(name = "desc", value = "描述") @RequestParam(required=false,defaultValue="") String desc
    		) {

		Map<String, Object> map=null;
	     try {
	            map = scoreService.updateFileNameAndDesc(fileName, desc, scoreId);

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
}
