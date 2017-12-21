package com.xinfang.controller;

import com.xinfang.VO.AuthUserInfo;
import com.xinfang.VO.LogInInfo;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.model.*;
import com.xinfang.service.FdCaseFeedbackAllService;
import com.xinfang.service.TzmPetitionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author tanzhouming
 * @description
 * @create 2017-03-21 12:02
 **/
@RestController
@RequestMapping("v1/petition")
@Api(description = "投诉办理基础接口（谭周明）")
public class TzmPetitionController {
	@Autowired
	private FdCaseFeedbackAllService fdCaseFeedbackAllService;

    @Autowired
    private TzmPetitionService tzmPetitionService;

//    @Autowired
//    private JsonConvert jsonConvert;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "用户登录", notes = "用户登录的成功后返回用户基本信息")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    Responses loginInfo(@ApiParam(name = "logInName", value = "用户名")
                       @RequestParam(name = "logInName") String logInName,
                       @ApiParam(name = "password", value = "密码")
                       @RequestParam(name = "password") String password){
        LogInInfo logInInfo = null;
        try {
            logInInfo = tzmPetitionService.selectLogInInfo(logInName, password);
            if(logInInfo!=null){
            	 ValueOperations<String, LogInInfo> operations=redisTemplate.opsForValue();
            	 operations.set("user:"+logInInfo.getRyId().toString(), logInInfo,600,TimeUnit.SECONDS);
            	 Thread.sleep(1000);
                 //redisTemplate.delete("com.neo.f");
                 boolean exists=redisTemplate.hasKey("user:"+logInInfo.getRyId().toString());
                 System.out.println("---"+exists);
            }
           
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取登录信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取登录信息成功", logInInfo);
    }

    @ApiOperation(value = "根据ryId获取登录信息", notes = "根据ryId获取登录信息")
    @RequestMapping(value = "/loginInByRyId", method = RequestMethod.POST)
    Responses loginInByRyId(@ApiParam(name = "ryId", value = "人员id")
                        @RequestParam(name = "ryId") Integer ryId){
        LogInInfo logInInfo = null;
        try {
            logInInfo = tzmPetitionService.selectLogInInfoByRyId(ryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取码表信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", logInInfo);
    }

    @ApiOperation(value = "根据登录名logInName获取登录信息", notes = "根据登录名logInName获取登录信息")
    @RequestMapping(value = "/loginInByLogInName", method = RequestMethod.POST)
    Responses loginInByLogInName(@ApiParam(name = "logInName", value = "登录名")
                            @RequestParam(name = "logInName") String logInName){
        LogInInfo logInInfo = null;
        try {
            logInInfo = tzmPetitionService.selectLogInInfoByLogInName(logInName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取码表信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", logInInfo);
    }

    @ApiOperation(value = "码表信息获取", notes = "获取码表信息（包括“<br/><font color=\"red\"><strong>" +
            "1.区域<br/>" +
            "2.行为状态<br/>" +
            "3.信访渠道<br/>" +
            "4.内容分类<br/>" +
            "5.案件分类<br/>" +
            "6. 13大类<br/>" +
            "7.热点问题<br/>" +
            "8.所属系统<br/>" +
            "9.信访目的<br/>" +
            "10.（无）<br/>" +
            "11.揭发对象<br/>" +
            "12.来访方式（到市）<br/>" +
            "13.来访方式（区县）舍弃,被整合到12中去了<br/>" +
            "14.证件类型<br/>" +
            "15.问题属地<br/>" +
            "16.信访原因<br/>" +
            "17.信访性质<br/>" +
            "18.从业情况<br/>" +
            "19.评价<br/>" +
            "20.重大资金、重要的人事任免、重大项目<br/>" +
            "21.消息提醒时间间隔<br/>" +
            "</strong></font>”等） codeType对应编号")
    @RequestMapping(value = "/getFdCode", method = RequestMethod.GET)
    Responses getFdCode(@ApiParam(name = "codeType", value = "编码类型（对应上面编号）")
                        @RequestParam(name = "codeType") Integer codeType){
        List<Map> data = new ArrayList<>();
        try {
            data = tzmPetitionService.selectByCodeType(codeType);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取码表信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", data);
    }

    @ApiOperation(value = "根据码表的parent_code码表信息获取（单层）", notes = "根据码表的parent_code码表信息获取")
    @RequestMapping(value = "/getFdCodeByParentCode", method = RequestMethod.GET)
    Responses getFdCodeByParentCode(@ApiParam(name = "codeType", value = "编码类型")
                        @RequestParam(name = "codeType") Integer codeType,
                        @ApiParam(name = "parentCode", value = "父编码code（默认为 -1）")
                        @RequestParam(name = "parentCode", required = false) Integer parentCode){
        List<Map> data = null;
        try {
            data = tzmPetitionService.selectByCodeTypeSingle(codeType, parentCode);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "获取码表信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", data);
    }

    @ApiOperation(value = "录入码表信息", notes = "已确定码表信息（包括\n***3.信访渠道***、" +
            "\n***4.内容分类***、\n***5.案件分类***、\n***6.13大类***、\n***7.热点问题***、\n***8.所属系统***、" +
            "\n***9.信访目的***、\n***11.揭发对象***\n、\n***12.来访方式（到市）***\n" +
            "、\n***13.来访方式（区县）舍弃***\n、\n***14.证件类型***\n等......） 目前不能用codeType=10，否则会冲突，codeType对应编号")
    @RequestMapping(value = "/postFdCode", method = RequestMethod.GET)
    Responses postFdCode(@ApiParam(name = "codeType", value = "编码类型（对应上面编号）,单层方式录入" +
            "<font color=\"red\"><strong>***为使码表干净管理员专门录入，不轻易开放，也勿请勿轻易使用！！！***</strong></font>")
                       @RequestParam(name = "codeType") Integer codeType,
                       @ApiParam(name = "codeContent", value = "编码内容")
                       @RequestParam(name = "codeContent") List<String> codeContent,
                       @ApiParam(name = "parentCode", value = "父编码（可为空，如果为空则默认根节点parent_code=-1）")
                       @RequestParam(name = "parentCode", required = false) Integer parentCode){
        List<FdCode> data = new ArrayList<>();
        try {
            data = tzmPetitionService.inputFdCode(codeContent, codeType, parentCode);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "录入码表信息失败,该信息已经存在", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "录入码表信息成功", data);
    }

    @ApiOperation(value = "发送消息", notes = "发送消息")
    @RequestMapping(value = "/postMsg", method = RequestMethod.POST)
    Responses postMsg(@ApiParam(name = "senderId", value = "发送人id", required = true) @RequestParam(name = "senderId") Integer senderId,
             @ApiParam(name = "receiverId", value = "接收人id", required = true) @RequestParam(name = "receiverId") Integer receiverId,
             @ApiParam(name = "msgContent", value = "消息内容", required = true) @RequestParam(name = "msgContent") String msgContent,
             @ApiParam(name = "msgType", value = "消息类型（暂未有确切类型）") @RequestParam(name = "msgType", required = false) Integer msgType,
             @ApiParam(name = "caseId", value = "案件id，消息是案件类消息则需要录入案件id")
             @RequestParam(name = "caseId", required = false) Integer caseId
        ){
        FdMsg result = null;
        try {
            result = tzmPetitionService.sendMessage(senderId, receiverId, msgContent, msgType, caseId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "发送消息失败，" +
                    "请检查录入数据是否正确，比如信息发送人是否存在", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "发送消息成功", result);
    }

    @ApiOperation(value = "消息类表", notes = "消息接收人消息列表")
    @RequestMapping(value = "/msgList", method = RequestMethod.GET)
    Responses msgList(@ApiParam(name = "ryId", value = "消息接收人id") @RequestParam(name = "ryId") Integer ryId,
                     @ApiParam(name = "msgState", value = "消息状态 0.未读 1.已读 -1.删除 null为未读")
                     @RequestParam(name = "msgState") Integer msgState
                ){
        List<FdMsg> result = null;
        try {
            result = tzmPetitionService.listMessageByReceiverIdAndState(ryId, msgState);
           /* if(result != null || result.size()>0){
                for (FdMsg fdMsg: result){
                    ValueOperations<String, FdMsg> operations=redisTemplate.opsForValue();
                    operations.set("msg:"+fdMsg.getId(), fdMsg,600,TimeUnit.SECONDS);
                    Thread.sleep(1000);
                    boolean exists=redisTemplate.hasKey("msg:"+fdMsg.getId());
                    System.out.println("---"+exists);
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, "获取码表信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", result);
    }

    @ApiOperation(value = "消息删除", notes = "消息删除")
    @RequestMapping(value = "/deleteMsg", method = RequestMethod.POST)
    Responses deleteMsg(@ApiParam(name = "msgIds", value = "消息id数组，可以填一个id，也可以填id数组")
                       @RequestParam(name = "msgIds") List<Integer> msgIds){
        Boolean result = false;
        try {
            tzmPetitionService.changeStateByMessageId(msgIds, -1);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取码表信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", result);
    }

    @ApiOperation(value = "消息置为已读", notes = "消息置为已读")
    @RequestMapping(value = "/readMsg", method = RequestMethod.POST)
    Responses readMsg(@ApiParam(name = "msgIds", value = "消息id数组，可以填一个id，也可以填id数组")
                      @RequestParam(name = "msgIds") List<Integer> msgIds){
        Boolean result = false;
        try {
            tzmPetitionService.changeStateByMessageId(msgIds, 1);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @ApiOperation(value = "通过案件id将消息置为已读", notes = "通过案件id将消息置为已读")
    @RequestMapping(value = "/readMsgByCaseId", method = RequestMethod.POST)
    Responses readMsgByCaseId(@ApiParam(name = "caseIds", value = "案件id数组，可以填一个id，也可以填id数组", required = true)
                      @RequestParam(name = "caseIds") Set<Integer> caseIds){
        Boolean result = false;
        try {
            tzmPetitionService.changeStateByCaseId(caseIds, 1);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取码表信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", result);
    }

    @ApiOperation(value = "根据单位id 获得目标单位的收文岗人员id<font color='read'>（单位收文岗而非科室收文岗）</font>",
            notes = "根据单位id 获得目标单位的收文岗人员id")
    @RequestMapping(value = "/getSwgInfoByDwId", method = RequestMethod.GET)
    Responses getSwgInfoByDwId(
            @ApiParam(name = "dwId", value = "单位（机构）id")
            @RequestParam(name = "dwId") Integer dwId){
        List<Integer> result = null;
        try {
            result = tzmPetitionService.selectSwgInfoByDwId(dwId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取收文岗人员id失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取收文岗人员id成功", result);
    }

    @ApiOperation(value = "根据单位id 获得目标单位的处理人员id 以及处理人员的领导id", notes = "根据单位id 获得目标单位的处理人员id 以及处理人员的领导id")
    @RequestMapping(value = "/getSwgHandleInfoByDwId", method = RequestMethod.GET)
    Responses getSwgHandleInfoByDwId(@ApiParam(name = "dwId", value = "单位id")
                     @RequestParam(name = "dwId") Integer dwId){
        List<Map> result = new ArrayList<>();
        try {
            result = tzmPetitionService.getRyIdAndRyLeaderIdByDwid(dwId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取码表信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", result);
    }

//    @ApiOperation(value = "录入案件类别", notes = "录入案件类别")
//    @RequestMapping(value = "/inputCaseCategory", method = RequestMethod.POST)
//    Responses inputCaseCategory(){
//        List<Map> result = new ArrayList<>();
//        try {
//            jsonConvert.inputFdCode();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取码表信息失败", e.getMessage());
//        }
//        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", result);
//    }

    @ApiOperation(value = "当某个人员确定办案，录入案件信息和办理人员id", notes = "当某个人员确定办案，录入案件信息和办理人员id")
    @RequestMapping(value = "/inputInfoWhenHandleCase", method = RequestMethod.POST)
    Responses inputInfoWhenHandleCase(@ApiParam(name = "caseId", value = "案件id")
                                     @RequestParam(name = "caseId") Integer caseId,
                                     @ApiParam(name = "handlePersonId", value = "办案人id")
                                     @RequestParam(name = "handlePersonId") Integer handlePersonId){
        TsCaseHandle result = null;
        try {
            result = tzmPetitionService.inputInfoWhenHandleCase(caseId, handlePersonId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取码表信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", result);
    }

    @ApiOperation(value = "根据人员id获取该职位上级领导id--现改为人员表的默认审核人（05-09）", notes = "根据人员id获取该职位上级领导id")
    @RequestMapping(value = "/getLeaderIdByRyid", method = RequestMethod.GET)
    Responses getLeaderIdByRyid(@ApiParam(name = "ryId", value = "人员id")
                    @RequestParam(name = "ryId") Integer ryId){
        List<Integer> result = null;
        try {
            result = tzmPetitionService.getLeaderIdByRyid(ryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "通过案件ID更新案件状态后和添加反馈信息", notes = "通过案件ID更新案件状态后和添加反馈信息")
    @RequestMapping(value = "/updateCaseStatusAndFeedbackMessage", method = RequestMethod.POST)
    Responses updateCaseStatusAndFeedbackMessage(
            @ApiParam(name = "ryId", value = "当前登录人id", required = true)
            @RequestParam(required = true) Integer ryId,
    		@ApiParam(name = "delayDay", value = "处理期限")
            @RequestParam(name = "delayDay",required=false,defaultValue="9") Integer delayDay,
            @ApiParam(name = "caseId", value = "案件id")
            @RequestParam(name = "caseId") Integer caseId,
            @ApiParam(name = "caseHandleState", value = "案件状态")
            @RequestParam Integer caseHandleState,
            @ApiParam(name = "caseType", value = "案件类型（存放到反馈表，回复、不予受理等） 4直接回复 5 不予受理  6 不在受理  7直接存档 9 申请办结  10 申请延期")
            @RequestParam Integer caseType,
            @ApiParam(name = "limitTime", value = "办理期限（天数）")
            @RequestParam(required = false) Integer limitTime,
            @ApiParam(name = "note", value = "备注(可为空)")
            @RequestParam(required = false) String note,
            @ApiParam(name = "enclosure1", value = "附件1(可为空)")
            @RequestParam(required = false) String enclosure1,
            @ApiParam(name = "enclosure2", value = "取证人姓名（可为空）")
            @RequestParam(required = false) String enclosure2,
            @ApiParam(name = "enclosure3", value = "附件3(可为空)")
            @RequestParam(required = false) String enclosure3,
            @ApiParam(name = "currentHandleState", value = "当前案件处理状态")
            @RequestParam(required = false) String currentHandleState){
        Map result = null;
        try {
            result = tzmPetitionService.updateCaseStatusAndFeedbackMessage(ryId, caseId, caseHandleState, caseType,limitTime,
                    note, enclosure1, enclosure2, enclosure3, currentHandleState,delayDay);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "更新失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "更新成功", result);
    }

    @ApiOperation(value = "根据类型id获取人员列表(收文岗人员可以转发给办理的人员)", notes = "机构人员可以接收的信访件类型")
    @RequestMapping(value = "/getRyIdByTypeIdAndDwid", method = RequestMethod.GET)
    Responses getRyIdByTypeId(@ApiParam(name = "typeIds", value = "类型id")
                                @RequestParam(name = "typeIds") List<Integer> typeIds,
                              @ApiParam(name = "dwId", value = "单位id")
                              @RequestParam(name = "dwId") Integer dwId){
        List<Integer> result = null;
        try {
            result = tzmPetitionService.getRyIdByTypeId(typeIds, dwId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取码表信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", result);
    }

    @ApiOperation(value = "根据单位id获取可以接收案件的人员id（该单位下收文岗可以转送的人员id）", notes = "根据单位id获取可以接收案件的人员id")
    @RequestMapping(value = "/getRyIdByDwId", method = RequestMethod.GET)
    Responses getRyIdByDwId(@ApiParam(name = "dwId", value = "单位id")
                            @RequestParam(name = "dwId") Integer dwId){
        List<Integer> result = null;
        try {
            result = tzmPetitionService.getRyIdByDwId(dwId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取码表信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取码表信息成功", result);
    }
    
    
    @ApiOperation(value = "案件处理", notes = "案件处理（联系上访人,调查取证,沟通协调）")
    @RequestMapping(value = "/handleCase", method = RequestMethod.POST)
    Responses handleCase(@ApiParam(name = "caseId", value = "案件id")
                                @RequestParam(name = "caseId") Integer caseId,
                                @ApiParam(name = "enclosure1", value = "附件（非必填）")
                                @RequestParam(name = "enclosure1", required = false) String enclosure1,
                                @ApiParam(name = "type", value = "1001联系上访人 ；1002调查取证；1003沟通协调；1004延期申请；1005案件退回；1006案件办结")
                                @RequestParam(name = "type") Integer type,
                                @ApiParam(name = "userId", value = "用户id")
                                @RequestParam(name = "userId") Integer userId,
                                @ApiParam(name = "witnessName", value = "取证人姓名（非必填）")
                                @RequestParam(name = "witnessName",required=false) String witnessName,
                                @ApiParam(name = "handleId", value = "非必填案件处理人id 调查取证需要")
                                @RequestParam(name = "handleId",required=false) Integer handleId,
                                @ApiParam(name = "note", value = "联系情况录入|取证内容|诉求信息补充")
                                @RequestParam(name = "note") String note,
                                @ApiParam(name = "noteoth", value = "信访人需求（非必填）")
                                @RequestParam(name = "noteoth",required=false) String noteoth,
                                @ApiParam(name = "updateTime", value = "联系时间,取证时间,沟通时间  ")
                                @RequestParam(name = "updateTime") String updateTime,
                                @ApiParam(name = "currentHandleState", value = "更新当前案件处理状态")
                                @RequestParam(name = "currentHandleState", required = false) String currentHandleState,
                                @ApiParam(name = "caseHandleType", value = "更新当前案件处理类型")
                                @RequestParam(name = "caseHandleType", required = false) Integer caseHandleType,
                                @ApiParam(name = "state", value = "更新状态，反馈表")
                                @RequestParam(name = "state", required = false) Integer state
    		){
    	FdCaseFeedbackAll result = null;
        try {
            result = tzmPetitionService.handleCse(caseId, type, note,
                    enclosure1, witnessName,userId,handleId,noteoth,updateTime, currentHandleState, caseHandleType, state);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "更新失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "更新成功", result);
    }

    @ApiOperation(value = "根据人员id获取该人员所在窗口", notes = "根据人员id获取该人员所在窗口")
    @RequestMapping(value = "/getWindowByRyId", method = RequestMethod.GET)
    Responses getWindowByRyId(@ApiParam(name = "ryId", value = "人员id")
                              @RequestParam(name = "ryId") Integer ryId){
        Map result = null;
        try {
            result = tzmPetitionService.getWindowByRyId(ryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "获取所有可以转发的机构收文岗（单位id和单位name）", notes = "获取所有可以转发的机构收文岗")
    @RequestMapping(value = "/getAllSwg", method = RequestMethod.GET)
    Responses getAllSwg(){
        List<Map> result = null;
        try {
            result = tzmPetitionService.getAllSwg();
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "根据区县市id获取职能部门（单位）id和name --3.31", notes = "根据区县市id获取职能部门")
    @RequestMapping(value = "/getDwMcAndDwIdByQxsId", method = RequestMethod.GET)
    Responses getDwMcAndDwIdByQxsId(@ApiParam(name = "qxsId", value = "区县市id")
                                    @RequestParam(name = "qxsId") Integer qxsId){
        List<Map> result = null;
        try {
            result = tzmPetitionService.getDwMcAndDwIdByQxsId(qxsId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "根据权限分组group_id获取人员信息（group_id=10为登记分流室）", notes = "根据权限分组group_id获取人员信息")
    @RequestMapping(value = "/getAuthUserInfoByGroupId", method = RequestMethod.GET)
    Responses getAuthUserInfoByGroupId(@ApiParam(name = "groupId", value = "权限分组id")
                                       @RequestParam(name = "groupId") Integer groupId){
        List<AuthUserInfo> result = null;
        try {
            result = tzmPetitionService.getAuthUserInfoByGroupId(groupId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "根据人员id判断用户是否在“贵阳市信访局登记分流室”组中", notes = "根据人员id判断用户是否在“贵阳市信访局登记分流室组中")
    @RequestMapping(value = "/isInGroup", method = RequestMethod.GET)
    Responses isInGroup(@ApiParam(name = "ryId", value = "人员id")
                        @RequestParam(name = "ryId") Integer ryId){
        Boolean result = false;
        try {
            AuthUserInfo authUserInfo =tzmPetitionService.isInGroup(ryId, 8); // 8为登记分流人员 ts_auth_group_new
            if (authUserInfo != null){
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "根据人员id获取可以转发的单位（机构）id --3.31", notes = "根据人员id获取可以转发的单位（机构）id")
    @RequestMapping(value = "/getTransferDwByRyId", method = RequestMethod.GET)
    Responses getTransferDwByRyId(
            @ApiParam(name = "ryId", value = "人员id")
            @RequestParam(name = "ryId") Integer ryId,
            @ApiParam(name = "dwTypes", value = "机构类型，如：‘市信访局’、‘市级职能部门’等，可为空，为空表示所有机构类型")
            @RequestParam(name = "dwTypes", required = false) List<Integer> dwTypes){
        List<Map> result = null;
        try {
            result = tzmPetitionService.getTransferDwByRyId(ryId, dwTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }

    @ApiOperation(value = "根据人员id获取单位信息(可以获取单位类型如：市级信访局、市级职能部门等) --4.1",
            notes = "根据人员id获取单位信息<font color='red'>字段dwType与之对应</font>" +
                    "(1, '市委'),\n" +
                    "(2, '市人民政府'),\n" +
                    "(3, '市信访局'),\n" +
                    "(4, '市级职能部门'),\n" +
                    "(5, '区委'),\n" +
                    "(6, '区政府'),\n" +
                    "(7, '区信访局'),\n" +
                    "(8, '区级职能部门'),\n" +
                    "(9, '乡镇社区'),\n" +
                    ")")
    @RequestMapping(value = "/getDwInfoRyId", method = RequestMethod.GET)
    Responses getDwInfoRyId(@ApiParam(name = "ryId", value = "人员id")
                            @RequestParam(name = "ryId") Integer ryId){
        Map result = null;
        try {
            result = tzmPetitionService.getDwInfoRyId(ryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED,  "获取信息失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "获取信息成功", result);
    }


    @ApiOperation(value = "根据id查询案件反馈信息", notes = "根据id查询案件反馈信息")
	@RequestMapping(value = "/queryFeedback/id", method = RequestMethod.POST)
	Responses findCaseFeedbackAllById(
			@ApiParam(name = "id", value = "主键id") @RequestParam(name = "id") Integer id) {
		ArrayList<FdCaseFeedbackAll> fdCaseFeedbackAll = null;
		try {
			fdCaseFeedbackAll = fdCaseFeedbackAllService
					.findCaseFeedbackAllById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new Responses(ResponseConstants.SUCCESS_FAILED,
					ResponseConstants.CODE_FAILED,
					ResponseConstants.CODE_FAILED_VALUE, fdCaseFeedbackAll);
		}
		return new Responses(ResponseConstants.SUCCESS_OK,
				ResponseConstants.CODE_SUCCESS,
				ResponseConstants.CODE_SUCCESS_VALUE, fdCaseFeedbackAll);
	}

    @ApiOperation(value = "根据码表id获取码表name --4.6", notes = "根据码表id获取码表name")
    @RequestMapping(value = "getFdCodeNameByCode", method = RequestMethod.GET)
    Responses getFdCodeNameByCode(
            @ApiParam(name = "areaId", value = "问题属地id") @RequestParam(name = "areaId") Integer areaId) {
        String result = null;
        try {
            result = tzmPetitionService.getFdCodeNameByCode(areaId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, result);
        }
        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, result);
    }

    @ApiOperation(value = "根据问题属地id获取职能部门id和name --4.6", notes = "根据问题属地id获取职能部门id和name")
    @RequestMapping(value = "getDwIdAndNameByProblemArea", method = RequestMethod.GET)
    Responses getDwIdAndNameByProblemArea(
            @ApiParam(name = "areaId", value = "问题属地id") @RequestParam(name = "areaId") Integer areaId) {
        List<Map> results = null;
        try {
            results = tzmPetitionService.getDwIdAndNameByProblemArea(areaId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, results);
        }
        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, results);
    }

    @ApiOperation(value = "根据单位id获取单位名称 --4.8", notes = "根据单位id获取单位名称")
    @RequestMapping(value = "getDwMcByDwId", method = RequestMethod.GET)
    Responses getDwMcByDwId(
            @ApiParam(name = "dwIds", value = "单位id数组") @RequestParam(name = "dwIds") List<Integer> dwIds) {
        List<Map> results = null;
        try {
            results = tzmPetitionService.getDwMcByDwId(dwIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,
                    ResponseConstants.CODE_FAILED,
                    ResponseConstants.CODE_FAILED_VALUE, results);
        }
        return new Responses(ResponseConstants.SUCCESS_OK,
                ResponseConstants.CODE_SUCCESS,
                ResponseConstants.CODE_SUCCESS_VALUE, results);
    }

}
