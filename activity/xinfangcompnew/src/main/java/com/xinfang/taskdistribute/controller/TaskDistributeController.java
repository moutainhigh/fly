package com.xinfang.taskdistribute.controller;

import com.xinfang.Exception.BizException;
import com.xinfang.context.NewModel;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.Responses;
import com.xinfang.taskdistribute.VO.RwTaskInputVO;
import com.xinfang.taskdistribute.VO.RwTaskListVO;
import com.xinfang.taskdistribute.VO.SearchList;
import com.xinfang.taskdistribute.model.RwTaskGroup;
import com.xinfang.taskdistribute.service.TaskDistributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-01 9:42
 **/
@RestController
@RequestMapping("v4/taskDistribute/")
@Api(description = "任务派发（谭周明）")
@NewModel
public class TaskDistributeController {

    @Autowired
    TaskDistributeService taskDistributeService;

    @RequestMapping(value = "A_add", method = RequestMethod.POST)
    @ApiOperation(value = "发起任务，修改和删除任务", notes = "发起任务，修改和删除任务")
    Responses addTaskDistribute(@ApiParam(name = "requestType", value = "请求方式。1发起任务、2编辑任务、3删除任务、4根据任务id查询任务", required = true)
                                @RequestParam(defaultValue = "1") Integer requestType,
                                @ModelAttribute RwTaskInputVO rwTaskInputVO,
                                @ApiParam(name = "taskReceiverSet", value = "接收人员id集合")
                                @RequestParam(required = false) Set<Integer> taskReceiverSet){
        Object result = null;
        try {
            result = taskDistributeService.addTaskDistribute(requestType, rwTaskInputVO, taskReceiverSet);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @RequestMapping(value = "A_getAllTaskGroup", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有可用任务组", notes = "获取所有可用任务组")
    Responses getAllTaskGroup(){
        List<RwTaskGroup> result = null;
        try {
            result = taskDistributeService.getAllTaskGroup();
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @RequestMapping(value = "A_editTaskGroup", method = RequestMethod.POST)
    @ApiOperation(value = "编辑任务组，包括添加、修改、删除任务组等操作", notes = "编辑任务组，包括添加、修改、删除任务组等操作")
    Responses editTaskGroup(@ApiParam(name = "requestType", value = "请求类型。1新增任务组、2修改任务组、3删除任务组",required = true)
                            @RequestParam Integer requestType,
                            @ApiParam(name = "groupId", value = "任务组id。(如果requestType=1则不用录入groupId)")
                            @RequestParam(required = false) Integer groupId,
                            @ApiParam(name = "groupName", value = "任务组名称")
                            @RequestParam(required = false) String groupName,
                            @ApiParam(name = "groupDescribe", value = "任务组描述")
                            @RequestParam(required = false) String groupDescribe,
                            @ApiParam(name = "ryRequestType", value = "人员集合处理类型。 1表示添加人员，2表示删除人员")
                            @RequestParam(required = false) Integer ryRequestType,
                            @ApiParam(name = "rySet", value = "人员集合")
                            @RequestParam(required = false) Set<Integer> rySet){
        RwTaskGroup result = null;
        try {
            result = taskDistributeService.editTaskGroup(requestType, groupId, groupName, groupDescribe, ryRequestType, rySet);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @RequestMapping(value = "B_list", method = RequestMethod.GET)
    @ApiOperation(value = "任务列表", notes = "任务列表")
    Responses taskDistributeList(@ModelAttribute SearchList searchList){
        Page<RwTaskListVO> result = null;
        try {
            result = taskDistributeService.taskDistributeList(searchList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @RequestMapping(value = "C_taskReceive", method = RequestMethod.POST)
    @ApiOperation(value = "任务接收（包括‘任务’、‘非任务’2中不同类型任务）", notes = "任务接收（包括‘任务’、‘非任务’2中不同类型任务）<br/>" +
            "<font color='red'>当requestType=1时， taskOperate字段为可选项，为空表示任务接收主页。以下是任务类型为‘任务’的选择：<br/>" +
            "taskOperate=1表示点击‘接收任务’按钮显示内容;<br/>" +
            "</font>")
    Responses taskReceive(@ApiParam(name = "taskId", value = "任务id", required = true)
                          @RequestParam Integer taskId,
                          @ApiParam(name = "receiverId", value = "接收人员id")
                          @RequestParam(required = false) Integer receiverId,
                          @ApiParam(name = "requestType", value = "请求类型，1获取任务的内容、2设置任务状态")
                          @RequestParam(required = false) Integer requestType,
                          @ApiParam(name = "taskOperate", value = "任务操作，requestType=2时有用，‘任务’状态包括’ 0未操作、1接收、2转发、3退回、4完成，而‘非任务’只有0和4")
                          @RequestParam(required = false) Integer taskOperate,
                          @ApiParam(name = "content", value = "taskOperate=1，表示处理结果；taskOperate=2，表示转发的人员集合，格式必须为：[1,3]； taskOperate=3，表示退回原因")
                          @RequestParam(required = false) String content,
                          @ApiParam(name = "attachment", value = "附件。taskOperate=1时可以上传附件")
                          @RequestParam(required = false) String attachment){
        Object result = null;
        try {
            result = taskDistributeService.taskReceive(taskId, receiverId, requestType, taskOperate, content, attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @RequestMapping(value = "D_taskDetail", method = RequestMethod.GET)
    @ApiOperation(value = "任务详情，包括‘任务’类型和非‘任务’类型")
    Responses taskDetail(@ApiParam(name = "taskId", value = "任务id", required = true)
                          @RequestParam Integer taskId){
        Object result = null;
        try {
            result = taskDistributeService.taskDetail(taskId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @RequestMapping(value = "E_taskStatistics", method = RequestMethod.GET)
    @ApiOperation(value = "任务统计",
            notes = "任务统计，开始时间可结束时间判断优先级更高,<br/>" +
                    "1.1 taskSendCount 发送任务总数目<br/>" +
                    "1.2 taskReceiveCount 收到任务总数目<br/>" +
                    "1.3 taskUncheckedCount 收到的并且未查看的任务数目,即未处理的任务<br/>" +
                    "1.4 taskDoingCount 进行中任务数目<br/>" +
                    "1.5 taskCompleteCount 已完成任务数目<br/>" +
                    "-------------------------------------------------<br/>" +
                    "2.1 taskCountMap '1任务'类型数目<br/>" +
                    "2.2 notificationCountMap '2通知'类型数目<br/>" +
                    "2.3 announcementCountMap '3公告'类型数目<br/>" +
                    "2.4 shareCountMap '4共享'类型数目<br/>" +
                    "2.5 othersCountMap '5其他'类型数目<br/>" +
                    "--------------------------------------------------<br/>" +
                    "3.1 taskComplteDegree 接收任务完成度，精确到了小数点后4位<br/>" +
                    "3.2 taskRemainCount 剩余任务数<br/>")
    Responses taskStatistics(@ApiParam(name = "createPersonIdSet", value = "人员id集合，" +
            "如果是员工则是的该人员id，如果是单位领导则为单位下所有人员id集合，科室则为科室人员id集合", required = true)
             @RequestParam Set<Integer> createPersonIdSet,
             @ApiParam(name = "startTime", value = "开始时间 yyyy-MM-dd HH:mm:ss")
             @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
             @RequestParam(required = false) Date startTime,
             @ApiParam(name = "endTime", value = "结束时间 yyyy-MM-dd HH:mm:ss")
             @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
             @RequestParam(required = false) Date endTime,
             @ApiParam(name = "taskSendOrReceive", value = "任务统计类型，1为发出的任务、2为接受的任务", required = true)
             @RequestParam(defaultValue = "1") Integer taskSendOrReceive,
             @ApiParam(name = "taskCondition", value = "任务统计状况 1本日、2本周、3本月")
             @RequestParam(defaultValue = "1") Integer taskCondition){
        Object result = null;
        try {
            result = taskDistributeService.taskStatistics(createPersonIdSet, startTime, endTime, taskSendOrReceive, taskCondition);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }

    @RequestMapping(value = "F_taskComplete", method = RequestMethod.GET)
    @ApiOperation(value = "任务完结，整个任务完结，而非分派到某个人的任务完结", notes = "任务完结，整个任务完结，而非分派到某个人的任务完结")
    Responses taskComplete(@ApiParam(name = "taskId", value = "任务id", required = true)
                         @RequestParam Integer taskId,
                           @ApiParam(name = "createrId", value = "任务创建人id", required = true)
                           @RequestParam Integer createrId){
        Object result = null;
        try {
            result = taskDistributeService.taskComplete(taskId, createrId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }
    
    @RequestMapping(value = "G_setTaskTop", method = RequestMethod.GET)
    @ApiOperation(value = "任务置顶设置，0为不置顶、1为置顶", notes = "任务置顶设置，0为不置顶、1为置顶")
    Responses setTaskTop(@ApiParam(name = "taskId", value = "任务id", required = true)
                         @RequestParam Integer taskId,
                           @ApiParam(name = "taskTop", value = "任务置顶，0为不置顶、1为置顶", required = true)
                           @RequestParam Byte taskTop){
        Object result = null;
        try {
            result = taskDistributeService.setTaskTop(taskId, taskTop);
            if(result == null || (int)result == 0){
            	throw new BizException("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED,ResponseConstants.CODE_FAILED,  "失败", e.getMessage());
        }
        return  new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "成功", result);
    }
}
