package com.xinfang.evaluating.controller;

import com.xinfang.context.NewModel;
import com.xinfang.context.PageFinder;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.evaluating.service.AppraiseService;
import com.xinfang.evaluating.vo.AppraiseDetailVO;
import com.xinfang.evaluating.vo.AppraiseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 投诉办理评价控制器
 * Created by sunbjx on 2017/5/8.
 */
@NewModel()
@RestController
@RequestMapping("/api/v1/appraise")
@Api(description = "投诉办理-评价(孙帮雄)")
public class AppraiseController {

    @Autowired
    private AppraiseService appraiseService;

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation(value = "评价详情", notes = "评价详情")
    public Responses detail(@ApiParam(name = "caseId", value = "投诉办理案件ID") @RequestParam Integer caseId) {

        AppraiseDetailVO result = null;
        try {
            result = appraiseService.getByCaseId(caseId);
        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, result);
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE, result);
    }


    @RequestMapping(value = "/record", method = RequestMethod.GET)
    @ApiOperation(value = "当前登录人处理过的相关案件评价列表过滤", notes = "当前登录人处理过的相关案件评价列表过滤")
    public Responses record(@ApiParam(name = "signInUserUnitId", value = "当前登录用户所属单位ID") @RequestParam Integer signInUserUnitId,
                            @ApiParam(name = "flag", value = "1 信访部门评价 2 责任单位评价") @RequestParam(required = false, defaultValue = "0") int flag,
                            @ApiParam(name = "petitionPurpose", value = "信访目的") @RequestParam(required = false, defaultValue = "0") Integer petitionPurpose,
                            @ApiParam(name = "caseType", value = "案件类别") @RequestParam(required = false, defaultValue = "0") Integer caseType,
                            @ApiParam(name = "caseBelongTo", value = "问题属地") @RequestParam(required = false, defaultValue = "0") Integer caseBelongTo,
                            @ApiParam(name = "unitId", value = "单位ID") @RequestParam(required = false, defaultValue = "0") Integer unitId,
                            @ApiParam(name = "petitionAppraiseOk", value = "是否满意信访单位 0 否 1 是") @RequestParam(required = false, defaultValue = "-1") Byte petitionAppraiseOk,
                            @ApiParam(name = "dutyAppraiseOk", value = "是否满意责任单位 0 否 1 是") @RequestParam(required = false, defaultValue = "-1") Byte dutyAppraiseOk,
                            @ApiParam(name = "caseAppraiseOk", value = "是否满意案件处理 0 否 1 是") @RequestParam(required = false, defaultValue = "-1") Byte caseAppraiseOk,
                            @ApiParam(name = "startTime", value = "评价开始时间 2017-09-09") @RequestParam(required = false, defaultValue = "") String startTime,
                            @ApiParam(name = "endTime", value = "评价结束时间 2017-09-15") @RequestParam(required = false, defaultValue = "") String endTime,
                            @ApiParam(name = "startPage", value = "起始页") @RequestParam(required = false, defaultValue = "1") int startPage,
                            @ApiParam(name = "pageSize", value = "每页大小") @RequestParam(required = false, defaultValue = "10") int pageSize) {

        PageFinder<AppraiseVO> result = null;
        try {
            result = appraiseService.listBySignInUser(signInUserUnitId, flag, petitionPurpose, caseType, caseBelongTo,
                    unitId, petitionAppraiseOk, dutyAppraiseOk, caseAppraiseOk, startTime, endTime, startPage, pageSize);
        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, result);
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE, result);
    }

    @RequestMapping(value = "/statistical/okrate", method = RequestMethod.GET)
    @ApiOperation(value = "群众满意度统计", notes = "群众满意度统计")
    public Responses okrate(@ApiParam(name = "petitionPurpose", value = "信访目的") @RequestParam(required = false, defaultValue = "0") Integer petitionPurpose,
                            @ApiParam(name = "caseType", value = "案件类别") @RequestParam(required = false, defaultValue = "0") Integer caseType,
                            @ApiParam(name = "caseBelongTo", value = "问题属地") @RequestParam(required = false, defaultValue = "0") Integer caseBelongTo,
                            @ApiParam(name = "unitId", value = "单位ID") @RequestParam(required = false, defaultValue = "0") Integer unitId,
                            @ApiParam(name = "startTime", value = "评价开始时间 2017-09-09") @RequestParam(required = false, defaultValue = "") String startTime,
                            @ApiParam(name = "endTime", value = "评价结束时间 2017-09-15") @RequestParam(required = false, defaultValue = "") String endTime) {

        Map<String, Object> result = null;
        try {
            result = appraiseService.okRateStatistical(petitionPurpose, caseType, caseBelongTo, unitId, startTime, endTime);
        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, result);
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE, result);
    }
}
