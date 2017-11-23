package com.xinfang.controller;

import com.xinfang.context.Response;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.model.FdGuest;
import com.xinfang.service.IdentityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 上访人基础信息
 * Created by sunbjx on 2017/3/22.
 */
@RestController
@RequestMapping("api/v1/identity")
@Api(description = "投诉办理-上访人基础信息(孙帮雄)")
public class IdentityController {


    @Autowired
    private IdentityService identityService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存上访人信息返回案件ID", notes = "保存上访人信息返回案件ID")
    public Responses save(@ModelAttribute FdGuest fdGuest,
                          @ApiParam(name = "strPetitionTime", value = "信访时间") @RequestParam String strPetitionTime,
                          @ApiParam(name = "petitionNumbers", value = "上访人数") @RequestParam(required = false, defaultValue = "0") Integer petitionNumbers,
                          @ApiParam(name = "associatedNumbers", value = "涉及人数") @RequestParam(required = false, defaultValue = "0") Integer associatedNumbers,
                          @ApiParam(name = "strFollowIds", value = "随访人IDs") @RequestParam(required = false, defaultValue = "0") String strFollowIds,
                          @ApiParam(name = "form", value = "窗口编号") @RequestParam(required = false, defaultValue = "0") Integer form) {

        Map<String, Integer> result = identityService.save(fdGuest, strPetitionTime, petitionNumbers, associatedNumbers, strFollowIds, form);
        if (result.get("no") == 0) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, result);
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE, result);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "通过案件ID和上访ID修改案件信息和上访人身份信息", notes = "通过案件ID和上访ID修改案件信息和上访人身份信息")
    public Response update(@ModelAttribute FdGuest fdGuest,
                           @ApiParam(name = "guestId", value = "上访ID") @RequestParam Integer guestId,
                           @ApiParam(name = "strPetitionTime", value = "上访时间") @RequestParam String strPetitionTime,
                           @ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId,
                           @ApiParam(name = "petitionNumbers", value = "上访人数") @RequestParam(required = false, defaultValue = "0") Integer petitionNumbers,
                           @ApiParam(name = "associatedNumbers", value = "涉及人数") @RequestParam(required = false, defaultValue = "0") Integer associatedNumbers,
                           @ApiParam(name = "strFollowIds", value = "随访人员IDs") @RequestParam(required = false, defaultValue = "0") String strFollowIds) {

        int result = identityService.update(fdGuest, guestId, strPetitionTime, caseId, petitionNumbers, associatedNumbers, strFollowIds);
        if (result == 0) {
            return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE);
        }
        return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE);
    }

    @RequestMapping(value = "/savefollow", method = RequestMethod.POST)
    @ApiOperation(value = "保存随访人信息返回ID", notes = "保存随访人信息返回ID")
    public Responses saveFollow(@ApiParam(name = "strPetitionTime", value = "信访时间") @RequestParam String strPetitionTime,
                                @ApiParam(name = "username", value = "随访代表姓名") @RequestParam String username,
                                @ApiParam(name = "idcard", value = "随访代表证件号码") @RequestParam(required = false) String idcard,
                                @ApiParam(name = "censusRegister", value = "随访代表户籍地址") @RequestParam String censusRegister,
                                @ApiParam(name = "phone", value = "随访代表联系电话") @RequestParam String phone) {

        Map<String, Object> result = null;
        try {
            result = identityService.saveFollowReturnId(strPetitionTime, username, idcard, censusRegister, phone);
        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, result);
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE, result);
    }

    @RequestMapping(value = "/removefollow", method = RequestMethod.POST)
    @ApiOperation(value = "根据随访人ID删除随访人返回删除的条数", notes = "根据随访人ID删除随访人返回删除的条数")
    public Responses removeFollowById(@ApiParam(name = "followIds", value = "随访代表ID") @RequestParam String followIds) {

        int result = identityService.removeFollowById(followIds);
        if (result == 0) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, result);
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE, result);
    }

    @RequestMapping(value = "/listfollow", method = RequestMethod.GET)
    @ApiOperation(value = "根据案件ID返回随访人信息", notes = "根据案件ID返回随访人信息")
    public Responses listFollowById(@ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId) {
        List<FdGuest> fdGuestList = new ArrayList<FdGuest>();
        try {
            fdGuestList = identityService.listFollowById(caseId);
        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, fdGuestList);
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE, fdGuestList);
    }

    @RequestMapping(value = "/query/guestId", method = RequestMethod.GET)
    @ApiOperation(value = "根据上访人ID返回上访人信息(显示Code 中文)", notes = "根据上访人ID返回上访人信息")
    public Responses getGuestById(@ApiParam(name = "guestId", value = "上访人ID") @RequestParam Integer guestId) {
        FdGuest fdGuest = null;
        try {
            fdGuest = identityService.getGuestById(guestId);
        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, fdGuest);
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE, fdGuest);
    }

    @RequestMapping(value = "/query/idcard", method = RequestMethod.GET)
    @ApiOperation(value = "根据上访人证件号码返回上访人信息(显示Code 中文)", notes = "根据上访人证件号码返回上访人信息")
    public Responses getGuestByIdcard(@ApiParam(name = "idcard", value = "证件号码") @RequestParam String idcard) {
        FdGuest fdGuest = null;
        try {
            fdGuest = identityService.getGuestByIdCard(idcard);
        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, fdGuest);
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE, fdGuest);
    }


    @RequestMapping(value = "/query/base", method = RequestMethod.GET)
    @ApiOperation(value = "根据上访ID和案件ID查询基本信息", notes = "根据上访ID和案件ID查询基本信息")
    public Responses getBaseByGuestIdAndCaseId(@ApiParam(name = "guestId", value = "上访ID") @RequestParam Integer guestId,
                                               @ApiParam(name = "caseId", value = "案件ID") @RequestParam Integer caseId) {
        Map<String, Object> map = new HashedMap();
        try {
            map = identityService.getBaseByGuestIdAndCaseId(guestId, caseId);
        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, map);
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, ResponseConstants.CODE_SUCCESS_VALUE, map);
    }
}