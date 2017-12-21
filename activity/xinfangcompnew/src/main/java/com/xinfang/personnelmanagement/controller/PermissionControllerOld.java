package com.xinfang.personnelmanagement.controller;

import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-26 16:18
 **/
public class PermissionControllerOld {
    // =============================   用户角色 userRoles   ===========================
    @ApiOperation(value = "用户角色两层树", notes = "用户角色两层树")
    @RequestMapping(value = "/A_getRolesTree", method = RequestMethod.GET)
    Responses getRolesTree(){
        //  从ts_auth_group中获取，第一层type_id,第二层id
        Object result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    @ApiOperation(value = "根据人员id获取用户角色", notes = "根据人员id获取用户角色")
    @RequestMapping(value = "/A_getRolesByRyId", method = RequestMethod.GET)
    Responses getRolesByRyId(@ApiParam(name = "ryId", value = "人员id")
                             @RequestParam(name = "ryId") Integer ryId){
        Object result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取人员角色信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取人员角色信息成功", result);
    }

    @ApiOperation(value = "为某个人员设置用户角色", notes = "为某个人员设置一个或多个用户角色")
    @RequestMapping(value = "/A_setRolesByRyId", method = RequestMethod.POST)
    Responses setRolesByRyId(@ApiParam(name = "ryId", value = "人员id")
                             @RequestParam(name = "ryId") Integer ryId,
                             @ApiParam(name = "rolesId", value = "角色id列表")
                             @RequestParam(name = "rolesId") List<Integer> rolesId){
        Object result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取人员角色信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取人员角色信息成功", result);
    }


    // =============================   转交办单位   ==============================
    @ApiOperation(value = "获取某个人员的转交办单位", notes = "获取某个人员的转交办单位")
    @RequestMapping(value = "/B_getForwardOrgByRyId", method = RequestMethod.GET)
    Responses getForwardOrg(@ApiParam(name = "ryId", value = "人员id")
                            @RequestParam(name = "ryId") Integer ryId){
        Object result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    @ApiOperation(value = "设置某个人员转交办单位", notes = "设置某个人员转交办单位")
    @RequestMapping(value = "/B_setForwardOrgByRyId", method = RequestMethod.POST)
    Responses setForwardOrg(@ApiParam(name = "ryId", value = "人员id")
                            @RequestParam(name = "ryId") Integer ryId){
        Object result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    // =============================   接收信访件类型   ===========================
    @ApiOperation(value = "获取接受信访件类型列表", notes = "获取接受信访件类型列表")
    @RequestMapping(value = "/C_getPersonCaseType", method = RequestMethod.GET)
    Responses getPersonCaseType(){
        Object result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    @ApiOperation(value = "获取某个人员的接受信访件类型", notes = "获取某个人员的接受信访件类型")
    @RequestMapping(value = "/C_getPersonCaseTypeByRyId", method = RequestMethod.POST)
    Responses getPersonCaseTypeByRyId(@ApiParam(name = "ryId", value = "人员id")
                                      @RequestParam(name = "ryId") Integer ryId){
        Object result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    @ApiOperation(value = "设置某个人员的接受信访件类型", notes = "设置某个人员的接受信访件类型")
    @RequestMapping(value = "/C_setPersonCaseTypeByRyId", method = RequestMethod.POST)
    Responses setPersonCaseTypeByRyId(@ApiParam(name = "ryId", value = "人员id")
                                      @RequestParam(name = "ryId") Integer ryId,
                                      @ApiParam(name = "personCaseTypes", value = "接受信访件类型id")
                                      @RequestParam(name = "personCaseTypes") List<Integer> personCaseTypes){
        Object result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    // =============================   录入信访件类型   ===========================
    @ApiOperation(value = "获取录入信访件类型列表", notes = "获取接受信访件类型列表")
    @RequestMapping(value = "/D_getInputPersonCaseType", method = RequestMethod.GET)
    Responses getInputPersonCaseType(){
        Object result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    @ApiOperation(value = "获取某个人员的录入信访件类型", notes = "获取某个人员的接受信访件类型")
    @RequestMapping(value = "/D_getInputPersonCaseTypeByRyId", method = RequestMethod.POST)
    Responses getInputPersonCaseTypeByRyId(@ApiParam(name = "ryId", value = "人员id")
                                           @RequestParam(name = "ryId") Integer ryId){
        Object result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    @ApiOperation(value = "设置某个人员的录入信访件类型", notes = "设置某个人员的接受信访件类型")
    @RequestMapping(value = "/D_setInputPersonCaseTypeByRyId", method = RequestMethod.POST)
    Responses setInputPersonCaseTypeByRyId(@ApiParam(name = "ryId", value = "人员id")
                                           @RequestParam(name = "ryId") Integer ryId,
                                           @ApiParam(name = "personCaseTypes", value = "接受信访件类型id")
                                           @RequestParam(name = "personCaseTypes") List<Integer> personCaseTypes){
        Object result = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }
}
