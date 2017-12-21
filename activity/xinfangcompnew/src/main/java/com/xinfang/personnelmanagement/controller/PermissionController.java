package com.xinfang.personnelmanagement.controller;

import com.alibaba.fastjson.JSON;
import com.xinfang.VO.AuthGroupNewVO;
import com.xinfang.VO.PermissionHomePage;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.personnelmanagement.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-17 9:23
 **/
@RestController
@RequestMapping("v2/permission/")
@Api(description = "权限管理（谭周明）")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @ApiOperation(value = "根据条件显示机构人员列表(04-27) 3.1", notes = "根据条件显示机构人员列表，没有条件就显示所有")
    @RequestMapping(value = "/A_permissionList", method = RequestMethod.GET)
    Responses permissionList(
            @ApiParam(name = "type", value = "条件类型，如：1为区县市、2为单位类型、3为单位、4为科室、即点击某个区县显示区县下所有机构列表，单位同理")
            @RequestParam(name = "type", required = true) Integer type,
            @ApiParam(name = "id", value = "某个类型下id，如type=1，id就是区县市id，type为空时id无效")
            @RequestParam(name = "id", required = true) Integer id,
            @ApiParam(name = "qxsId", value = "区县市id，只有type=2是有效")
            @RequestParam(name = "qxsId", required = false) Integer qxsId,
            @ApiParam(name = "ryId", value = "人员id")
            @RequestParam(name = "ryId", required = false) Integer ryId,
            @ApiParam(name = "search", value = "模糊搜索")
            @RequestParam(name = "search", required = false) String search,
            @ApiParam(name = "startpage", value = "开始页数，建议默认设置为0，即第一页")
            @RequestParam(name = "startpage", required = false, defaultValue = "0") Integer startpage,
            @ApiParam(name = "pagesize", value = "每页条数，建议默认设置个默认值10")
            @RequestParam(name = "pagesize", required = false, defaultValue = "10") Integer pagesize){
        List<PermissionHomePage> result = null;
        try {
            result = permissionService.permissionList(type, id, qxsId, ryId, search, startpage, pagesize);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "用户角色（所具有的角色<权限>）（04-28） 3.3", notes = "用户角色（所具有的角色<权限>）")
    @RequestMapping(value = "/C_userRoleList", method = RequestMethod.POST)
    Responses userRoleList(
            @ApiParam(name = "ryId", value = "人员id")
            @RequestParam(name = "ryId") Integer ryId,
            @ApiParam(name = "request", value = "1为获取用户角色，2为设置用户角色")
            @RequestParam(name = "request") Integer request,
            @ApiParam(name = "ids", value = "权限id数组，request=2时，用于设置权限，request=1该字段为空")
            @RequestParam(name = "ids", required = false) List<Integer> ids){
        List<AuthGroupNewVO> result = null;
        try {
            result = permissionService.userRoleList(ryId, request, ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "转交办单位（04-28） 3.4", notes = "转交办单位")
    @RequestMapping(value = "/D_getForwardOrgList", method = RequestMethod.POST)
    Responses getForwardOrgList(
            @ApiParam(name = "ryId", value = "人员id")
            @RequestParam(name = "ryId") Integer ryId,
            @ApiParam(name = "request", value = "1为获取转交办单位，2为设置转交办单位")
            @RequestParam(name = "request") Integer request,
            @ApiParam(name = "dwIds", value = "可转交的单位id列表，request=2时，用于设置转交办单位，request=1该字段为空")
            @RequestParam(name = "dwIds", required = false) Set<Integer> dwIds){
        Map result = null;
        try {
            result = permissionService.getForwardOrgList(ryId, request, dwIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "该人员可以接收信访件类型包括获取类型和设置类型（05-02） 3.5", notes = "该人员可以接收信访件类型包括获取类型和设置类型")
    @RequestMapping(value = "E_getPersonCaseTypeList", method = RequestMethod.POST)
    Responses getPersonCaseTypeList(
            @ApiParam(name = "ryId",value = "登录人员id")
            @RequestParam(name = "ryId") Integer ryId,
            @ApiParam(name = "request",value = "1为获取接收信访件类型，2为设置接收信访件类型 ")
            @RequestParam(name = "request") Integer request,
            @ApiParam(name = "receiveCaseTypes",value = "1为获取接收信访件类型，2为设置接收信访件类型 ")
            @RequestParam(name = "receiveCaseTypes", required = false) List<Integer> receiveCaseTypes){
        Map result = null;
        try {
            result = permissionService.getPersonCaseTypeList(ryId, request, receiveCaseTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "该人员可以录入信访件类型包括获取类型和设置类型（05-02） 3.6", notes = "该人员可以录入信访件类型包括获取类型和设置类型")
    @RequestMapping(value = "F_getInputPersonCaseTypeList", method = RequestMethod.POST)
    Responses getInputPersonCaseTypeList(
            @ApiParam(name = "ryId",value = "登录人员id")
            @RequestParam(name = "ryId") Integer ryId,
            @ApiParam(name = "request",value = "1为获取接收信访件类型，2为设置接收信访件类型 ")
            @RequestParam(name = "request") Integer request,
            @ApiParam(name = "inputCaseTypes",value = "接收案件类型id列表")
            @RequestParam(name = "inputCaseTypes", required = false) List<Integer> receiveCaseTypes){
        Map result = null;
        try {
            result = permissionService.getInputPersonCaseTypeList(ryId, request, receiveCaseTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "为单位接收信访件类型设置收文岗（05-04） 3.7", notes = "为单位接收信访件类型设置收文岗")
    @RequestMapping(value = "G_getTransferPersonList", method = RequestMethod.POST)
    Responses getTransferPersonList(
            @ApiParam(name = "dwId",value = "单位id")
            @RequestParam(name = "dwId") Integer dwId,
            @ApiParam(name = "request",value = "1为获取接收信访件类型，2为设置接收信访件类型 ")
            @RequestParam(name = "request") Integer request,
            @ApiParam(name = "receiveCaseTypes",value = "接收信访件类型的id和收文岗人员receiverId的id，" +
                    "eg:`[{\"typeId\": 1, \"receiverId\": 1829},{......}]`" +
                    "每个类型只能设置一个收文岗人员。**注：接收信访件类型只有在办理人大于1的情况下显示**")
            @RequestParam(name = "receiveCaseTypes", required = false) String receiveCaseTypes){
        Map result = null;
        try {
            List<Map> receiveCaseTypesList = (List<Map>) JSON.parse(receiveCaseTypes);
            result = permissionService.getTransferPersonList(dwId, request, receiveCaseTypesList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "根据单位id和角色id获取该单位下人员", notes = "根据单位id和角色id获取该单位下人员" +
            "角色对应如下：<br>" +
            "1\t超级管理员\n<br>" +
            "3\t复查办理员\n<br>" +
            "4\t复核办理员\n<br>" +
            "5\t信访件办理员\n<br>" +
            "6\t信访件查询\n<br>" +
            "7\t积案办理员\n<br>" +
            "8\t登记分流人员\n<br>" +
            "10\t科室领导\n<br>" +
            "11\t科室信访件查看\n<br>" +
            "12\t责任单位领导\n<br>" +
            "13\t市县区领导或信访局领导\n<br>" +
            "14\t矛盾纠纷转来的信访件处理<br>")
    @RequestMapping(value = "H_getRyListByDwIdAndRoleId", method = RequestMethod.POST)
    Responses getRyListByDwIdAndRoleId(
            @ApiParam(name = "dwId",value = "单位id")
            @RequestParam(name = "dwId") Integer dwId,
            @ApiParam(name = "roleId",value = "角色id")
            @RequestParam(name = "roleId") Integer roleId){
        List<Map> result = null;
        try {
            result = permissionService.getRyListByDwIdAndRoleId(dwId, roleId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "根据人员id和角色id判断是否具有该权限", notes = "根据人员id和角色id判断是否具有该权限" +
            "角色对应如下：<br>" +
            "1\t超级管理员\n<br>" +
            "3\t复查办理员\n<br>" +
            "4\t复核办理员\n<br>" +
            "5\t信访件办理员\n<br>" +
            "6\t信访件查询\n<br>" +
            "7\t积案办理员\n<br>" +
            "8\t登记分流人员\n<br>" +
            "10\t科室领导\n<br>" +
            "11\t科室信访件查看\n<br>" +
            "12\t责任单位领导\n<br>" +
            "13\t市县区领导或信访局领导\n<br>" +
            "14\t矛盾纠纷转来的信访件处理<br>")
    @RequestMapping(value = "H_getRyIdsByRyIdAndRoleId", method = RequestMethod.POST)
    Responses getRyIdsByRyIdAndRoleId(
            @ApiParam(name = "ryId",value = "人员id")
            @RequestParam(name = "ryId") Integer ryId,
            @ApiParam(name = "roleId",value = "角色id")
            @RequestParam(name = "roleId") Integer roleId){
        Boolean result = false;
        try {
            result = permissionService.isByRyIdAndRoleId(ryId, roleId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "根据人员id判断人员是否有‘科室信访件查看’权限，如果有则返回该人员所在科室下的所有人员id(05-23)",
            notes = "根据人员id判断人员是否有‘科室信访件查看’权限，如果有则返回该人员所在科室下的所有人员id")
    @RequestMapping(value = "I_getKsRyIdsByRyId", method = RequestMethod.POST)
    Responses getKsRyIdsByRyId(
            @ApiParam(name = "ryId",value = "人员id")
            @RequestParam(name = "ryId") Integer ryId){
        List<Integer> result = new ArrayList<>();
        try {
            result = permissionService.getKsRyIdsByRyId(ryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "根据具有‘信访件查询’权限的人员id获取某个人员职位领导的所有人员id列表(05-23)",
            notes = "根据具有‘信访件查询’权限的人员id获取某个人员职位领导的所有人员id列表")
    @RequestMapping(value = "J_getZwRyIdsByRyId", method = RequestMethod.POST)
    Responses getZwRyIdsByRyId(
            @ApiParam(name = "ryId",value = "人员id")
            @RequestParam(name = "ryId") Integer ryId){
        List<Integer> result = new ArrayList<>();
        try {
            result = permissionService.getZwRyIdsByRyId(ryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }
}


