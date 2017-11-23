package com.xinfang.controller;

import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.service.TzmPetitionAddService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-18 15:47
 **/

@RestController
@RequestMapping("v1/petitionAdd")
@Api(description = "投诉办理完善内容接口（谭周明）")  // http://120.24.228.17:8082/index.php?s=/3&page_id=30
public class TzmPetitionAddController {

    @Autowired
    TzmPetitionAddService tzmPetitionAddService;

    @ApiOperation(value = "1.1.2 主页上的案件统计数量展示", notes = "1.1.2 主页上的案件统计数量展示")
    @RequestMapping(value = "/getDifferentCaseCount", method = RequestMethod.GET)
    Responses getDifferentCaseCount(){
        List<Map> result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "统计案件状态", notes = "统计案件状态<br>" +
            "ASSIGNED(1, \"交办\"), TRUN(2, \"转办\"),ALLOW(3,\"受理\"),REPLY(4,\"直接回复\"),REFUSE(5,\"不予受理 \"),REFUSEL(6,\"不在受理 \")\n" +
            "    ,SAVE(7,\"直接存档\"),RETURN(8,\"退回\"),FINISH(9,\"申请办结\"),DELAY(10,\"申请延期\")")
    @RequestMapping(value = "/getStatisticsCaseStatus", method = RequestMethod.GET)
    Responses getStatisticsCaseStatus(@ApiParam(name = "ryId", value = "人员id") @RequestParam(name = "ryId") Integer ryId){
        Map result = null;
        try {
            result = tzmPetitionAddService.getStatisticsCaseStatus(ryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "测试人员登录情况，返回无法登陆的人员id", notes = "测试人员登录情况<br>")
    @RequestMapping(value = "/testLogin", method = RequestMethod.GET)
    Responses testLogin(){
        Object result = null;
        try {
            result = tzmPetitionAddService.testLogin();
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }
}
