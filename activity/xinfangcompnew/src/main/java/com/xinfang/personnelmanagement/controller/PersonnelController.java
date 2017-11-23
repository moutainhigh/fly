package com.xinfang.personnelmanagement.controller;

import com.xinfang.VO.PermissionPersonDetail;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.personnelmanagement.service.PersonnelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zemal-tan
 * @description
 * @create 2017-04-17 14:55
 **/
@RestController
@RequestMapping("v2/personnel/")
@Api(description = "人员管理（谭周明）")
public class PersonnelController {

    @Autowired
    PersonnelService personnelService;

    @ApiOperation(value = "根据条件显示机构人员列表(04-27) 3.2、2.3和2.1", notes = "根据条件显示机构人员列表")
    @RequestMapping(value = "/A_personList", method = RequestMethod.GET)
    Responses personList(
            @ApiParam(name = "type", value = "条件类型，如：1为区县市、2为单位类型、3为单位、4为科室、5为职务、即点击某个区县显示区县下所有机构列表，单位同理")
            @RequestParam(name = "type", required = false ,defaultValue="0") Integer type,
            @ApiParam(name = "id", value = "某个类型下id，如type=1，id就是区县市id，type为空时id无效")
            @RequestParam(name = "id", required = false,defaultValue="0") Integer id,
            @ApiParam(name = "qxsId", value = "区县市id，只有type=2是有效")
            @RequestParam(name = "qxsId", required = false,defaultValue="0") Integer qxsId,
            @ApiParam(name = "ryId", value = "人员id")
            @RequestParam(name = "ryId", required = false,defaultValue="0") Integer ryId,
            @ApiParam(name = "search", value = "模糊搜索")
            @RequestParam(name = "search", required = false,defaultValue="0") String search,
            @ApiParam(name = "fuzzyState", value = "单独使用模糊搜索时填此参数(1)")
            @RequestParam(name = "fuzzyState", required = false,defaultValue="0") Integer fuzzyState,
            @ApiParam(name = "startpage", value = "开始页数，建议默认设置为0，即第一页")
            @RequestParam(name = "startpage", required = false, defaultValue = "0") Integer startpage,
            @ApiParam(name = "pagesize", value = "每页条数，建议默认设置个默认值10")
            @RequestParam(name = "pagesize", required = false, defaultValue = "10") Integer pagesize){
        Page<PermissionPersonDetail> result = null;
        try {
                result=personnelService.personRybAll(type, id, qxsId, ryId, search, startpage, pagesize,fuzzyState);
              // result = personnelService.personList(type, id, qxsId, ryId, search, startpage, pagesize);
           } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "添加人员信息(05-03) 2.2", notes = "添加人员信息")
    @RequestMapping(value = "/C_addPerson", method = RequestMethod.POST)
    Responses addPerson(
            @ApiParam(name = "request", value = "请求方法，1为添加人员，2为修改人员")
            @RequestParam(name = "request") Integer request,
            @ApiParam(name = "ryId", value = "人员id, 当request=2时，需要录入修改的人员id")
            @RequestParam(name = "ryId", required = false) Integer ryId,
            @ApiParam(name = "name", value = "姓名")
            @RequestParam(name = "name") String name,
            @ApiParam(name = "img", value = "人员头像（非必填）")
            @RequestParam(name = "img", required = false) String img,
            @ApiParam(name = "sex", value = "性别")
            @RequestParam(name = "sex") String sex,
            @ApiParam(name = "placeOfBirth", value = "户籍、籍贯")
            @RequestParam(name = "placeOfBirth") String placeOfBirth,
            @ApiParam(name = "qxsId", value = "区县市id，即区县市id （选择树）")
            @RequestParam(name = "qxsId") Integer qxsId,
            @ApiParam(name = "workUnitId", value = "工作单位名称id，即机构名称id （选择树）")
            @RequestParam(name = "workUnitId") Integer workUnitId,
            @ApiParam(name = "officeId", value = "科室Id （选择树）")
            @RequestParam(name = "officeId") Integer officeId,
            @ApiParam(name = "dutyId", value = "职务Id （选择树）")
            @RequestParam(name = "dutyId") Integer dutyId,
            @ApiParam(name = "orderBy", value = "展示顺序 （非必选,默认为1）")
            @RequestParam(name = "orderBy", defaultValue = "1") Integer orderBy,
            @ApiParam(name = "enabled", value = "状态 （非必选,默认为1，启用）")
            @RequestParam(name = "enabled", defaultValue = "1") Integer enabled,
            @ApiParam(name = "politicalId", value = "政治面貌id，如党员（选择树）")
            @RequestParam(name = "politicalId") Integer politicalId,
            @ApiParam(name = "national", value = "民族  （选择树）")
            @RequestParam(name = "national") Integer national,
            @ApiParam(name = "schooling", value = "学历id，如本科（选择树）")
            @RequestParam(name = "schooling") Integer schooling,
            @ApiParam(name = "idcard", value = "身份证号")
            @RequestParam(name = "idcard") String idcard,
            @ApiParam(name = "telephone", value = "手机号")
            @RequestParam(name = "telephone") String telephone,
            @ApiParam(name = "birthDate", value = "出生日期")
            @RequestParam(name = "birthDate") String birthDate,
            @ApiParam(name = "email", value = "邮箱(非必填)")
            @RequestParam(name = "email", required = false) String email,
            @ApiParam(name = "startWorkTime", value = "开始工作时间（非必填）")
            @RequestParam(name = "startWorkTime", required = false) String startWorkTime,
//            @ApiParam(name = "windowId", value = "所在窗口id（选择树,可为空）")
//            @RequestParam(name = "windowId", required = false) Integer windowId,
            @ApiParam(name = "thePartyTime", value = "入党时间,格式：yyyy-MM-dd（非必填）")
            @RequestParam(name = "thePartyTime", required = false) String thePartyTime,
            @ApiParam(name = "checkPersonId", value = "默认审核人，非必填，可根据所填职位获取上层审核人，即直属上级领导id,（如果上级职务有多个则选择第一个）")
            @RequestParam(name = "checkPersonId", required = false) Integer checkPersonId,
            @ApiParam(name = "personBusiness", value = "负责业务(可为空)")
            @RequestParam(name = "personBusiness", required = false) String personBusiness,
            @ApiParam(name = "logInName", value = "账户登录名(可为空，默认为身份证号)")
            @RequestParam(name = "logInName", required = false) String logInName,
            @ApiParam(name = "password", value = "账户密码(可为空，默认为身份证号倒数第二位到倒数第七位,共6位树)")
            @RequestParam(name = "password", required = false) String password,
            @ApiParam(name = "sfId", value = "省id，可为空，默认为520000")
            @RequestParam(name = "sfId", required = false) Integer sfId,
            @ApiParam(name = "sqId", value = "市id，可为空，默认为520100")
            @RequestParam(name = "sqId", required = false) Integer sqId){
        Boolean result = null;
        try {
            String loginname;
            String initPassword;
            if ((logInName == null || logInName.trim().equals("")) && request == 1){
                loginname = idcard;
            }else {
                loginname = logInName;
            }

            if ((password == null || password.trim().equals("")) && request == 1){
                String idcardPassword = idcard.substring(idcard.length()-1-6, idcard.length()-1); // 包含start但不包含end
                String reverseIdcardPassword = reverse(idcardPassword); // 倒置
                initPassword = reverseIdcardPassword;
            }else {
                initPassword = password;
            }
            Integer windowId = null;
            result = personnelService.addPerson(request, ryId, name, img,sex, placeOfBirth, qxsId, workUnitId, officeId, dutyId,
                    orderBy, enabled, politicalId, national, schooling, idcard, telephone, birthDate, email,
                    startWorkTime, windowId, thePartyTime, checkPersonId, personBusiness, loginname, initPassword, sfId, sqId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "需要修改的人员信息详情", notes = "需要修改的人员信息详情")
    @RequestMapping(value = "C_modifyPersonDetail", method = RequestMethod.POST)
    Responses modifyPersonDetail(@ApiParam(name = "ryId", value = "人员id")
                           @RequestParam(name = "ryId") Integer ryId){
        Object result = null;
        try {
            result = personnelService.modifyPersonDetail(ryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "机构人员删除", notes = "机构人员删除,单位表人员删除")
    @RequestMapping(value = "D_deletePerson", method = RequestMethod.POST)
    Responses deletePerson(@ApiParam(name = "ryId", value = "人员id")
                           @RequestParam(name = "ryId") Integer ryId){
        Object result = null;
        try {
            personnelService.deletePerson(ryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    /**
     * 字符串倒置函数
     * @param s
     * @return
     */
    public String reverse(String s) {
        int length = s.length();
        if (length <= 1)
            return s;
        String left = s.substring(0, length / 2);
        String right = s.substring(length / 2, length);
        return reverse(right) + reverse(left);
    }

    @ApiOperation(value = "登录用户修改用户名或密码(05-03) 2.4", notes = "登录用户修改用户名或密码")
    @RequestMapping(value = "/D_updateLogInPerson", method = RequestMethod.POST)
    Responses updateLogInPerson(
            @ApiParam(name = "ryId", value = "人员id")
            @RequestParam(name = "ryId") Integer ryId,
            @ApiParam(name = "password", value = "密码")
            @RequestParam(name = "password") String password,
            @ApiParam(name = "newUsername", value = "新用户名")
            @RequestParam(name = "newUsername", required = false) String newUsername,
            @ApiParam(name = "newPassword", value = "新密码")
            @RequestParam(name = "newPassword", required = false) String newPassword){
        Map result = null;
        try {
            result = personnelService.updateLogInPerson(ryId, password, newUsername, newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

}
