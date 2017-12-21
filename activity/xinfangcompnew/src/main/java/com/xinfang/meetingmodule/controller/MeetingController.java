package com.xinfang.meetingmodule.controller;

import com.xinfang.context.NewModel;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.meetingmodule.VO.*;
import com.xinfang.meetingmodule.model.FhContinueRemind;
import com.xinfang.meetingmodule.model.FhMeetingPerson;
import com.xinfang.meetingmodule.model.FhMeetingUrge;
import com.xinfang.meetingmodule.service.MeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zemal-tan
 * @description     v1为以前， v2为机构管理， v3为会议调度
 * @create 2017-05-10 10:44
 **/

@RestController
@RequestMapping("v3/meeting/")
@Api(description = "会议调度模块（谭周明）")
@NewModel
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    @ApiOperation(value = "根据人员id和会议id判定该人员在该会议下的角色",
            notes = "根据人员id和会议id判定该人员在该会议下的角色<br>" +
                    "\"initiatePerson\": 会议发起人,\n<br>" +
                    "\"submitPerson\": 部门提交人，即收文岗人,\n<br>" +
                    "\"joinPerson\": 部门参会人<br>")
    @RequestMapping(value = "/AA_basePersonRole", method = RequestMethod.GET)
    Responses basePersonRole(@ApiParam(name = "peopleId", value = "人员id")
                              @RequestParam(name = "peopleId") Integer peopleId,
                              @ApiParam(name = "meetingId", value = "meetingId")
                              @RequestParam(name = "meetingId") Integer meetingId){
        PeopleRole result = null;
        try {
            result = meetingService.basePersonRole(peopleId, meetingId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "发起会议--新增会议调度",
            notes = "发起会议--新增会议调度\n<br>" +
                    "auto(1, \"选择机构人员添加\"), manaul(2,\"手动录入\"),  // 添加类型编号及名称\n<br>" +
                    "    unconfirmed(0, \"未确认\"), confirmed(1, \"已确认\"), absent(2, \"无法参加\");// 人员参会状态;<br>" +
                    "\n<br>DZH(1, \"党组会\"), BGH(2, \"办公会\"), DDH(3, \"调度会\"), XTH(4, \"协调会\"),\n" +
                    "    GBZGDH(5, \"干部职工大会\"), SPH(6, \"视频会\"), PXH(7, \"培训会\"), QT(8, \"其他\"); // 会议类型")
    @RequestMapping(value = "/A_addMeetingSchedule", method = RequestMethod.POST)
    Responses addMeetingSchedule(@ModelAttribute FhMeetingInitiateVO fhMeetingInitiate){
        FhMeetingInitiateVO result = null;
        try {
            result = meetingService.addMeetingSchedule(fhMeetingInitiate);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }
    @ApiOperation(value = "发起会议--获取单个会议详情", notes = "发起会议--获取单个会议详情")
    @RequestMapping(value = "/A_getMeetingSchedule", method = RequestMethod.POST)
    Responses getMeetingSchedule(@ApiParam(name = "meetingId", value = "会议id（如果填写会议id，则表示修改该会议）")
                                 @RequestParam(name = "meetingId") Integer meetingId){
        FhMeetingInitiateVO result = null;
        try {
            result = meetingService.getMeetingSchedule(meetingId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "会议列表--会议管理、当前会议",
            notes = "会议列表--会议管理、当前会议")
    @RequestMapping(value = "/B_meetingLists", method = RequestMethod.POST)
    Responses meetingLists(@ModelAttribute SearchRequest searchRequest){
        Page<FhMeetingListVO> result = null;
        try {
            result = meetingService.meetingLists(searchRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "添加参会人员，分为选择添加和手动添加",
            notes = "添加参会人员，分为选择添加和手动添加")
    @RequestMapping(value = "/C_addMeetingPerson", method = RequestMethod.POST)
    Responses addMeetingPerson(@ModelAttribute FhMeetingPersonVO fhMeetingPersonVO){
        FhMeetingPerson result = null;
        try {
            result = meetingService.addMeetingPerson(fhMeetingPersonVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "添加参会人员并确认回复",
            notes = "添加参会人员并确认回复")
    @RequestMapping(value = "/C_confirmReply", method = RequestMethod.POST)
    Responses confirmReply(@ApiParam(name = "fhMeetingPersonVOListStr", required = true, value = "添加的人员字符串，由添加参会人员接口C_addMeetingPerson的列表形式results的value字符串构成")
                           @RequestParam String fhMeetingPersonVOListStr){
        FhMeetingPerson result = null;
        try {
            meetingService.confirmReply(fhMeetingPersonVOListStr);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "部门无法参加会议",
            notes = "部门无法参加会议")
    @RequestMapping(value = "/C_departmentAbsent", method = RequestMethod.POST)
    Responses departmentAbsent(
            @ApiParam(name = "meetingId", value = "会议id", required = true)
            @RequestParam Integer meetingId,
            @ApiParam(name = "departmentId", value = "部门id", required = true)
            @RequestParam Integer departmentId,
            @ApiParam(name = "absentReason", value = "无法参加原因", required = true)
            @RequestParam String absentReason){
        Integer result = 0;
        try {
            result = meetingService.departmentAbsent(meetingId, departmentId, absentReason);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "某部门下某会议确认情况查看（参会人）",
            notes = "某部门下某会议确认情况查看")
    @RequestMapping(value = "/D_confirmConditionList", method = RequestMethod.GET)
    Responses confirmConditionList(@ApiParam(name = "submitPersonId", value = "确认人员id")
                           @RequestParam Integer submitPersonId,
                           @ApiParam(name = "meetingId", value = "会议id")
                           @RequestParam Integer meetingId,
                           @ApiParam(name = "startPage", value = "开始页数，可为空，默认为0，即第一页")
                           @RequestParam(defaultValue = "0") Integer startPage,
                           @ApiParam(name = "pageSize", value = "每页个数，可为空，默认为15")
                           @RequestParam(defaultValue = "15") Integer pageSize){
        Page<FhMeetingPerson> result = null;
        try {
            result = meetingService.confirmConditionList(submitPersonId, meetingId, startPage, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "某部门下某会议确认某个人员参会情况（参会人）",
            notes = "某部门下某会议确认某个人员参会情况")
    @RequestMapping(value = "/D_confirmPeopleCondition", method = RequestMethod.POST)
    Responses confirmPeopleConditionList(@ApiParam(name = "submitPersonId", value = "确认人员id", required = true)
                                   @RequestParam Integer submitPersonId,
                                   @ApiParam(name = "meetingId", value = "会议id", required = true)
                                   @RequestParam Integer meetingId,
                                   @ApiParam(name = "meetingPersonPk", value = "被确认人员id，及‘某部门下某会议确认情况查看’接口中的id字段", required = true)
                                   @RequestParam Integer meetingPersonPk,
                                   @ApiParam(name = "personState", value = "人员参会状态编号，20表示‘未确认’、21表示‘已确认’、22表示‘无法参加’", required = true)
                                   @RequestParam Integer personState,
                                   @ApiParam(name = "absentReason", value = "缺席原因，若人员状态为22表示‘无法参加’需填写无法参见原因", required = false)
                                   @RequestParam(required = false) String absentReason){
        Integer result = null;
        try {
            result = meetingService.confirmPeopleCondition(submitPersonId, meetingId, meetingPersonPk, personState, absentReason);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "确认情况,确认某会议下所有参会人员列表（派发人）",
            notes = "确认情况,确认某会议下所有参会人员列表（派发人）")
    @RequestMapping(value = "/D_confirmConditionInitiatePerson", method = RequestMethod.POST)
    Responses confirmConditionInitiatePerson(@ApiParam(name = "initiatePersonId", value = "会议发起人id")
                                             @RequestParam Integer initiatePersonId,
                                             @ApiParam(name = "meetingId", value = "会议id")
                                             @RequestParam Integer meetingId,
                                             @ApiParam(name = "startPage", value = "开始页数，可为空，默认为0，即第一页")
                                             @RequestParam(defaultValue = "0") Integer startPage,
                                             @ApiParam(name = "pageSize", value = "每页个数，可为空，默认为15")
                                             @RequestParam(defaultValue = "15") Integer pageSize){
        Page<FhMeetingPerson> result = null;
        try {
            result = meetingService.confirmConditionInitiatePerson(initiatePersonId, meetingId, startPage, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "确认情况,给未确认的人员发提醒（派发人）",
            notes = "确认情况,给未确认的人员发提醒（派发人）")
    @RequestMapping(value = "/D_confirmConditionInitiatePersonRemind", method = RequestMethod.POST)
    Responses confirmConditionInitiatePersonRemind(@ModelAttribute FhMeetingUrgeVO fhMeetingUrgeVO){
        FhMeetingUrge result = null;
        try {
            result = meetingService.confirmConditionInitiatePersonRemind(fhMeetingUrgeVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }



    @ApiOperation(value = "会议详情",
            notes = "会议详情")
    @RequestMapping(value = "/E_meetingDetail", method = RequestMethod.POST)
    Responses meetingDetail(@ApiParam(name = "meetingId", value = "会议id", required = true)
                            @RequestParam Integer meetingId){
        FhMeetingDetailVO result = null;
        try {
            result = meetingService.meetingDetail(meetingId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "会议签到列表，根据人员角色显示人员数目不同，发起人显示所有且可修改，其他显示本部门且只能查看。" +
            "<font color='read'>canModify=true</font>表示所有，false表示本部门",
            notes = "会议签到列表，根据人员角色显示人员数目不同，发起人显示所有且可修改，其他显示本部门且只能查看" +
                    "<br>'canModify=true'表示所有，false表示本部门")
    @RequestMapping(value = "/F_signInList", method = RequestMethod.POST)
    Responses signInList(@ApiParam(name = "meetingId", value = "会议id", required = true)
                         @RequestParam Integer meetingId,
                         @ApiParam(name = "ryId", value = "人员id", required = true)
                         @RequestParam Integer ryId){
        Map result = null;
        try {
            result = meetingService.signInList(meetingId, ryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "会议签到,meetingPersonPk为人员表主键id，因为有的人员没有人员id",
            notes = "会议签到,为部门的人员签到")
    @RequestMapping(value = "/F_signInMeeting", method = RequestMethod.POST)
    Responses signInMeeting(@ApiParam(name = "meetingId", value = "会议id", required = true)
                         @RequestParam Integer meetingId,
                         @ApiParam(name = "meetingPersonPk", value = "人员表主键id，即上面接口签到列表中的id", required = true)
                         @RequestParam Integer meetingPersonPk,
                         @ApiParam(name = "isRegister", value = "签到,31表示‘已签到’，30表示‘未签到’", required = true)
                         @RequestParam Byte isRegister,
                         @ApiParam(name = "registerDateStr", value = "签到时间，如果为空则表示当前时间", required = false)
                         @RequestParam(required = false) String registerDateStr){
        Integer result = null;
        try {
            result = meetingService.signInMeeting(meetingId, meetingPersonPk, isRegister, registerDateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "会议记录,获取或设置",
            notes = "会议记录")
    @RequestMapping(value = "/G_meetingRecord", method = RequestMethod.POST)
    Responses meetingRecord(@ApiParam(name = "typeId", value = "类型id（1为获取，2为设置）", required = true)
                            @RequestParam Integer typeId,
                            @ModelAttribute FhMeetingRecordVO fhMeetingRecordVO){
        FhMeetingRecordVO result = null;
        try {
            result = meetingService.meetingRecord(typeId, fhMeetingRecordVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "会议记录，该会议下可选的部门",
            notes = "会议记录，该会议下可选的部门, 落实时间：confirmDate")
    @RequestMapping(value = "/G_meetingRecordDepartment", method = RequestMethod.GET)
    Responses meetingRecordDepartment(@ApiParam(name = "meetingId", value = "会议id", required = true)
                                      @RequestParam Integer meetingId){
        Set<Map> result = null;
        try {
            result = meetingService.meetingRecordDepartment(meetingId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "会议过程,获取或设置",
            notes = "会议过程")
    @RequestMapping(value = "/H_meetingProcess", method = RequestMethod.POST)
    Responses meetingProcess(@ApiParam(name = "typeId", value = "类型id（1为获取，2为设置）", required = true)
                            @RequestParam Integer typeId,
                            @ModelAttribute FhMeetingProcessVO fhMeetingProcessVO){
        FhMeetingProcessVO result = null;
        try {
            result = meetingService.meetingProcess(typeId, fhMeetingProcessVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "落实情况,某个会议的落实情况列表",
            notes = "落实情况,某个会议的落实情况列表")
    @RequestMapping(value = "/I_continueCondition", method = RequestMethod.POST)
    Responses continueCondition(@ApiParam(name = "meetingId", value = "会议id", required = true)
                             @RequestParam Integer meetingId){
        List<FhMeetingContinueDetailVO> result = null;
        try {
            result = meetingService.continueCondition(meetingId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "落实情况提醒",
            notes = "落实情况提醒")
    @RequestMapping(value = "/I_continueConditionWarn", method = RequestMethod.POST)
    Responses continueConditionWarn(@ModelAttribute FhContinueRemindVO fhContinueRemindVO){
        FhContinueRemind result = null;
        try {
            result = meetingService.continueConditionWarn(fhContinueRemindVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "落实反馈情况列表，某个会议下某个落实编号的反馈情况列表",
            notes = "落实反馈情况列表，某个会议下某个落实编号的反馈情况列表")
    @RequestMapping(value = "/I_continueConditionFeedBackList", method = RequestMethod.GET)
    Responses continueConditionFeedBackList(@ApiParam(name = "meetingId", value = "会议id", required = true)
                                            @RequestParam Integer meetingId,
                                            @ApiParam(name = "continueId", value = "落实情况id", required = true)
                                            @RequestParam Integer continueId){
        List<FhMeetingContinueConfirmVO> result = null;
        try {
            result = meetingService.continueConditionFeedBackList(meetingId, continueId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "落实反馈情况，对分配的落实进行反馈",
            notes = "落实反馈情况，对分配的落实进行反馈")
    @RequestMapping(value = "/J_continueConditionFeedBack", method = RequestMethod.POST)
    Responses continueConditionFeedBack(@ModelAttribute FhMeetingContinueConfirmVO fhMeetingContinueConfirmVO){
        FhMeetingContinueConfirmVO result = null;
        try {
            result = meetingService.continueConditionFeedBack(fhMeetingContinueConfirmVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "会议完成",
            notes = "会议完成")
    @RequestMapping(value = "/K_meetingComplete", method = RequestMethod.POST)
    Responses meetingComplete(@ApiParam(name = "meetingId", value = "会议id", required = true)
                              @RequestParam Integer meetingId){
        Object result = null;
        try {
            result = meetingService.meetingComplete(meetingId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

}
