package com.xinfang.personnelmanagement.controller;

import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.model.TsTransferPerson;
import com.xinfang.personnelmanagement.service.BaseTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-17 15:01
 **/

@RestController
@RequestMapping("v1/basetree/")
@Api(description = "左侧机构树相应的接口（谭周明）")
public class BaseTreeController {

    @Autowired
    BaseTreeService baseTreeService;

    @ApiOperation(value = "根据单位id获取‘单位--科室--人员’树",
            notes = "根据单位id获取‘单位--科室--人员’树")
    @RequestMapping(value = "/A_dwKsRyTree", method = RequestMethod.GET)
    Responses dwKsRyTree(@ApiParam(name = "dwId", value = "单位id（可为-1或空，表示全部）")
                                    @RequestParam(name = "dwId", required = false) Integer dwId){
        List<Map> result = new ArrayList<>();
        try {
            result = baseTreeService.dwKsRyTree(dwId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    @ApiOperation(value = "获取具有‘接收信访件类型’权限的机构树",
            notes = "获取具有‘接收信访件类型’权限的机构树（区县--单位--科室--人员）")
    @RequestMapping(value = "/A_getPersonCaseTypeTree", method = RequestMethod.GET)
    Responses getPersonCaseTypeTree(@ApiParam(name = "dwId", value = "单位id（可为-1或空，表示全部）")
                                    @RequestParam(name = "dwId", required = false) Integer dwId,
                                    @ApiParam(name = "typeIds", value = "接收信访件类型id数组，一个或多个（可为空，为空表示所有类型）")
                                    @RequestParam(name = "typeIds", required = false) List<Integer> typeIds){
        List<Map> result = new ArrayList<>();
        try {
            result = baseTreeService.getPersonCaseTypeTree(dwId, typeIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    @ApiOperation(value = "获取具有‘录入信访件类型’权限的机构树",
            notes = "获取具有‘录入信访件类型’权限的机构树（区县--单位--科室--人员）")
    @RequestMapping(value = "/A_getInputPersonCaseTypeTree", method = RequestMethod.GET)
    Responses getInputPersonCaseTypeTree(@ApiParam(name = "dwId", value = "单位id（可为-1或空，表示全部）")
                                         @RequestParam(name = "dwId", required = false) Integer dwId){
        List<Map> result = new ArrayList<>();
        try {
            result = baseTreeService.getInputPersonCaseTypeTree(dwId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "获取具有‘区县市-单位类型-单位-科室’的树（04-26） 1.1",
            notes = "获取具有‘区县市-单位类型-单位-科室’的树，根据人员所在的区县市显示对应树，" +
                    "区县市为‘贵阳市’的显示所有，为其它的则只显示对应的树，如观山湖区只显示观山湖区的树。")
    @RequestMapping(value = "/A_getQxsDwTypeDwKsTree", method = RequestMethod.GET)
    Responses getQxsDwTypeDwKsTree(@ApiParam(name = "ryId", value = "人员id")
                                   @RequestParam(name = "ryId") Integer ryId){
        List<Map> result = new ArrayList<>();
        try {
            result = baseTreeService.getQxsDwTypeDwKsTree(ryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "获取具有‘区县市-单位类型-单位’的树（05-10）",
            notes = "获取具有‘区县市-单位类型-单位’的树，根据人员所在的单位显示对应树，" +
                    "信访局显示对应区县下所有（贵阳市的显示所有），职能部门只显示对应单位。")
    @RequestMapping(value = "/A_getQxsDwTypeDwTree", method = RequestMethod.GET)
    Responses getQxsDwTypeDwTree(@ApiParam(name = "qxsId", value = "区县市id")
                                 @RequestParam(name = "qxsId") Integer qxsId,
                                 @ApiParam(name = "dwId", value = "单位id")
                                 @RequestParam(name = "dwId") Integer dwId){
        List<Map> result = new ArrayList<>();
        try {
            result = baseTreeService.getQxsDwTypeDwTree(qxsId, dwId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "更新区县市、单位、科室等的启用状态（04-27） 1.2",
            notes = "更新区县市、单位、科室等的启用状态，" +
                    "更新类型，1为区县、2为单位、3为科室、4为职务、5为人员、6为窗口")
    @RequestMapping(value = "/B_updateStatus", method = RequestMethod.POST)
    Responses updateStatus(
            @ApiParam(name = "type", value = "更新类型，1为区县、2为单位、3为科室、4为职务、5为人员、6为窗口")
            @RequestParam(name = "type") Integer type,
            @ApiParam(name = "id", value = "类型对应的id， 如区县id、单位id等")
            @RequestParam(name = "id") Integer id,
            @ApiParam(name = "status", value = "置为状态，默认为0，置为禁用")
            @RequestParam(name = "status", required = false, defaultValue = "0") Integer status){
        Map result = new HashMap();
        try {
            result = baseTreeService.updateStatus(type, id, status);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "获取具有‘接收信访件类型’, 如果为该类型设置了收文岗人员，则显示他 （05-07）",
            notes = "获取具有‘接收信访件类型’, 如果为该类型设置了收文岗人员，则显示他")
    @RequestMapping(value = "/C_getPersonCaseType", method = RequestMethod.GET)
    Responses getPersonCaseType(@ApiParam(name = "dwId", value = "单位id（如果为空或-1则显示单位收文岗）")
                            @RequestParam(name = "dwId", required = false) Integer dwId){
        List<Map> result = new ArrayList<>();
        try {
            result = baseTreeService.getPersonCaseType(dwId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    @ApiOperation(value = "获取具有‘接收信访件类型2’ （06-12）",
            notes = "获取具有‘接收信访件类型2’ （06-12）")
    @RequestMapping(value = "/C_getPersonCaseType2", method = RequestMethod.GET)
    Responses getPersonCaseType2(@ApiParam(name = "dwId", value = "单位id（如果为空或-1则显示单位收文岗）")
                                @RequestParam(name = "dwId", required = false) Integer dwId){
        List<Map> result = new ArrayList<>();
        try {
            result = baseTreeService.getPersonCaseType2(dwId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    @ApiOperation(value = "获取具有‘接收信访件类型’ --喻一丁（06-12）",
            notes = "获取具有‘接收信访件类型’ --喻一丁（06-12）<br/>" +
                    "说明：\n" +
                    "#目标人员ID  下一步需要用到的目标人员\n" +
                    "FH_RY_ID\n" +
                    "\n" +
                    "#分派类型   1= 直接派送到待办的   2 到收件的类型收文岗  3 单位收文岗\n" +
                    "FH_RY_FPLX\n" +
                    "\n" +
                    "#目标人员名称\n" +
                    "RY_MC\n" +
                    "\n" +
                    "#上级人员id  如果=-1 说明是直接派发 同时没有上级的  如果出现-1 就不要执行了 提示要设置该人员的上级\n" +
                    "FH_RY_FJID\n" +
                    "\n" +
                    "#上级人员名称\n" +
                    " FH_RY_FJMC\n" +
                    "\n" +
                    "#类型ID   \n" +
                    " CODE1\n" +
                    "#单位id\n" +
                    " DW_ID\n" +
                    "#单位名称\n" +
                    " DW_MC")
    @RequestMapping(value = "/C_getPersonCaseType3", method = RequestMethod.GET)
    Responses getPersonCaseType3(@ApiParam(name = "dwId", value = "单位id")
                                 @RequestParam(name = "dwId", required = false) Integer dwId,
                                 @ApiParam(name = "typeId", value = "接收信访件类型id")
                                 @RequestParam(name = "typeId", required = false) Integer typeId){
        Map<String, Object> result = new HashedMap();
        try {
            result = baseTreeService.getPersonCaseType3(dwId, typeId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取用户角色两层树信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取用户角色两层树信息成功", result);
    }

    @ApiOperation(value = "根据区县市id获取该区县市下的信访局",
            notes = "根据区县市id获取该区县市下的信访局")
    @RequestMapping(value = "/F_getXfByQxsId", method = RequestMethod.GET)
    Responses getXfByQxsId(@ApiParam(name = "qxsId", value = "区县市id", required = true)
                           @RequestParam Integer qxsId){
        Map result = new HashMap();
        try {
            result = baseTreeService.getXfByQxsId(qxsId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "根据问题属地id获取该区县市下的信访局（码表中的15）(06-17)",
            notes = "根据问题属地id获取该区县市下的信访局（码表中的15）")
    @RequestMapping(value = "/F_getXfByFdCodeId", method = RequestMethod.GET)
    Responses getXfByFdCodeId(@ApiParam(name = "areaId", value = "码表15中问题属地id", required = true)
                           @RequestParam Integer areaId){
        Map result = new HashMap();
        try {
            result = baseTreeService.getXfByFdCodeId(areaId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = " 更新单位表的checkPersonId字段--默认审核人id（05-08）",
            notes = "更新单位表的checkPersonId字段--默认审核人id")
    @RequestMapping(value = "/Z_updateCheckPersonId", method = RequestMethod.POST)
    Responses updateCheckPersonId(){
        List<Map> result = new ArrayList<>();
        try {
            baseTreeService.updateCheckPersonId();
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "设置默认审核人失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "设置默认审核人成功", result);
    }

    @ApiOperation(value = " 更新TsInputPersonCaseTypeNew录入单位类型id（05-09）禁止点击！！！",
            notes = "更新TsInputPersonCaseTypeNew录入单位类型id（05-09）禁止点击！！！")
    @RequestMapping(value = "/Z_updateInputPersonCaseTypeNew", method = RequestMethod.POST)
    Responses updateInputPersonCaseTypeNew(){
        List<Map> result = new ArrayList<>();
        try {
            baseTreeService.updateInputPersonCaseTypeNew();
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "更新TsInputPersonCaseTypeNew失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "更新TsInputPersonCaseTypeNew成功", result);
    }

    @ApiOperation(value = "为没有设置收文岗的单位，选取该单位下的有‘超级管理员’权限的任意一个存在的人员为单位收文岗人员（07-09）禁止点击！！！",
            notes = "为没有设置收文岗的单位，选取该单位下的有‘超级管理员’权限的任意一个存在的人员为单位收文岗人员（07-09）禁止点击！！！")
    @RequestMapping(value = "/Z_useSuperAdminToSetDwSwg", method = RequestMethod.POST)
    Responses useSuperAdminToSetDwSwg(@ApiParam(name = "dwIdSet", value = "单位id集合，没有设置收文岗的单位")
                                      @RequestParam Set<Integer> dwIdSet){
        List<TsTransferPerson> result = new ArrayList<>();
        try {
            result = baseTreeService.useSuperAdminToSetDwSwg(dwIdSet);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "更新失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "更新成功", result);
    }

//    @ApiOperation(value = " 更新用户角色（05-10）禁止点击！！！",
//            notes = "更新用户角色（05-10）禁止点击！！！")
//    @RequestMapping(value = "/Z_updateUserRolePermission", method = RequestMethod.POST)
//    Responses updateUserRolePermission(@ApiParam(name = "permissionId", value = "权限id")
//                                       @RequestParam(name = "permissionId") Integer permissionId,
//                                       @ApiParam(name = "ryIds", value = "人员id数组")
//                                       @RequestParam(name = "ryIds") Integer[] ryIds){
//        List<Map> result = new ArrayList<>();
//        try {
//            baseTreeService.updateUserRolePermission(ryIds, permissionId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "更新失败", e.getMessage());
//        }
//        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "更新成功", result);
//    }
}
