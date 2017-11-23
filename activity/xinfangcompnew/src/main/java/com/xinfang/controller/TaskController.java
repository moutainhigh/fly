package com.xinfang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.Exception.BizException;
import com.xinfang.VO.HistoryTaskVO;
import com.xinfang.VO.NodeVO;
import com.xinfang.VO.TaskVO;
import com.xinfang.context.PageFinder;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.ResponseOth;
import com.xinfang.service.ActivitiService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/V1/task")
@Api(description = "任务控制（张华荣）")
public class TaskController {
	@Autowired
	private ActivitiService myService;

	@RequestMapping(value = "/mytask", method = RequestMethod.GET)
	@ApiOperation(value = "我的待办", notes = "我的待办列表")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public ResponseOth mytask(@ApiParam(name = "userid", value = "用户id") @RequestParam(name = "userid") String userid,
			@ApiParam(name = "startpage", value = "开始页数") @RequestParam(name = "startpage") int startpage,
			@ApiParam(name = "pagesize", value = "每页条数") @RequestParam(name = "pagesize") int pagesize,
			@ApiParam(name = "state", value = "0 全部  1交办 2转办 3待审核 4待处理 5待办结") @RequestParam(name = "state",defaultValue="0") int state
	) {
		PageFinder<TaskVO> tasks;
		try {
			System.out.println("state="+state);
			tasks = myService.getTasks(userid, startpage, pagesize,state);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());

		}

		return new ResponseOth(ResponseConstants.CODE_OK, tasks, "已实现");

	}

	@RequestMapping(value = "/gettask", method = RequestMethod.GET)
	@ApiOperation(value = "任务", notes = "根据任务id获取任务")
	public ResponseOth gettask(@ApiParam(name = "taskid", value = "任务id") @RequestParam(name = "taskid") String taskid

	) {
		TaskVO tasks;
		try {
			tasks = myService.getTask(taskid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());

		}

		return new ResponseOth(ResponseConstants.CODE_OK, tasks, "已实现");

	}
	
	@RequestMapping(value = "/getcurrentnode", method = RequestMethod.GET)
	@ApiOperation(value = "根据流程实列id获取当前处理人", notes = "根据流程实列id获取当前处理人")
	public ResponseOth getcurrentnode(@ApiParam(name = "processInstanceId", value = "流程实列id") @RequestParam(name = "processInstanceId") String processInstanceId

	) {
		NodeVO nodevo;
		try {
			nodevo = myService.getcurrentnode(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());

		}

		return new ResponseOth(ResponseConstants.CODE_OK, nodevo, "已实现");

	}

	@RequestMapping(value = "/historytask", method = RequestMethod.GET)
	@ApiOperation(value = "我的已办", notes = "我的已办列表")
	public ResponseOth historytask(
			@ApiParam(name = "userid", value = "用户id") @RequestParam(name = "userid") String userid,
			@ApiParam(name = "startpage", value = "开始页数") @RequestParam(name = "startpage") int startpage,
			@ApiParam(name = "pagesize", value = "每页条数") @RequestParam(name = "pagesize") int pagesize

	) {
		PageFinder<HistoryTaskVO> tasks;
		try {
			tasks = myService.historytask(userid, startpage, pagesize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());
		}

		return new ResponseOth(ResponseConstants.MY_CODE_FAILED, tasks, "已实现");

	}

	@RequestMapping(value = "/historytaskover", method = RequestMethod.GET)
	@ApiOperation(value = "我的已办", notes = "我的已办列表(已经完成的流程)")
	public ResponseOth historytaskover(
			@ApiParam(name = "userid", value = "用户id") @RequestParam(name = "userid") String userid,
			@ApiParam(name = "startpage", value = "开始页数") @RequestParam(name = "startpage") int startpage,
			@ApiParam(name = "pagesize", value = "每页条数") @RequestParam(name = "pagesize") int pagesize

	) {
		PageFinder<HistoryTaskVO> tasks;
		try {
			tasks = myService.historytask(userid, startpage, pagesize);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());
		}

		return new ResponseOth(ResponseConstants.MY_CODE_FAILED, tasks, "已实现");

	}

	@RequestMapping(value = "/historytaskin", method = RequestMethod.GET)
	@ApiOperation(value = "我的已办", notes = "我的已办列表(正在进行的流程)")
	public ResponseOth historytaskin(
			@ApiParam(name = "userid", value = "用户id") @RequestParam(name = "userid") String userid,
			@ApiParam(name = "startpage", value = "开始页数") @RequestParam(name = "startpage") int startpage,
			@ApiParam(name = "pagesize", value = "每页条数") @RequestParam(name = "pagesize") int pagesize

	) {
		PageFinder<HistoryTaskVO> tasks;
		try {
			tasks = myService.historytaskin(userid, startpage, pagesize);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());
		}

		return new ResponseOth(ResponseConstants.MY_CODE_FAILED, tasks, "已实现");

	}

	@RequestMapping(value = "/completetask", method = RequestMethod.GET)
	@ApiOperation(value = "审批", notes = "审批")
	public ResponseOth completetask(
			@ApiParam(name = "userId", value = "用户id") @RequestParam(name = "userId") int userId,
			@ApiParam(name = "taskid", value = "任务id") @RequestParam(name = "taskid") String taskid,
			@ApiParam(name = "approval", value = "1审批通过  0审批不通过") @RequestParam(name = "approval") int approval,
			@ApiParam(name = "files", value = "（非必填）附件信息 可以为json格式 ") @RequestParam(name = "files", required = false) String files,
			@ApiParam(name = "note", value = "（非必填备注信息 ") @RequestParam(name = "note", required = false) String note,
			@ApiParam(name = "ryid", value = "（选择人员编号 ") @RequestParam(name = "ryid", required = false) String ryid

	) {

		try {
			myService.completetask(taskid, approval, files, note, userId,ryid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());
		}

		return new ResponseOth(ResponseConstants.CODE_OK, "操作成功", "已实现");

	}

	@RequestMapping(value = "/forwardtask", method = RequestMethod.POST)
	@ApiOperation(value = "收文岗派送案件", notes = "收文岗派送案件")
	public ResponseOth forwardtask(
			@ApiParam(name = "taskid", value = "任务id") @RequestParam(name = "taskid") String taskid,
			@ApiParam(name = "myid", value = "登录的用户id") @RequestParam(name = "myid") int myid,
			@ApiParam(name = "userid", value = "被指派的人员id") @RequestParam(name = "userid") int userid,
			@ApiParam(name = "files", value = "（非必填附件信息 可以为json格式 ") @RequestParam(name = "files", required = false) String files,
			@ApiParam(name = "note", value = "（非必填备注信息 ") @RequestParam(name = "note", required = false) String note

	) {

		try {
			myService.forwardtask(myid, taskid, userid, files, note);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());
		}

		return new ResponseOth(ResponseConstants.CODE_OK, "操作成功", "已实现");

	}

	@RequestMapping(value = "/handletask", method = RequestMethod.POST)
	@ApiOperation(value = "职能部门处理案件", notes = "职能部门处理案件")
	public ResponseOth handletask(@ApiParam(name = "userId", value = "用户id") @RequestParam(name = "userId") int userId,
			@ApiParam(name = "taskid", value = "任务id") @RequestParam(name = "taskid") String taskid,
			@ApiParam(name = "proId", value = "流程实列id") @RequestParam(name = "proId") String proId,
			@ApiParam(name = "type", value = "1 交办  2转办  3受理  4直接回复  5 不予受理  6 不在受理  7直接存档  8退回   9 申请办结  10 申请延期") @RequestParam(name = "type") int type,
			@ApiParam(name = "depid", value = "非必填 交办 转办的时候需要 ") @RequestParam(name = "depid", required = false) Integer depid,
			@ApiParam(name = "files", value = "（非必填 办结报告 可以为json格式 ") @RequestParam(name = "files", required = false) String files,
			@ApiParam(name = "files1", value = "（非必填 回执单  可以为json格式 ") @RequestParam(name = "files1", required = false) String files1,
			@ApiParam(name = "files2", value = "（非必填 其它附件  可以为json格式 ") @RequestParam(name = "files2", required = false) String files2,
			@ApiParam(name = "note", value = "（非必填备注信息 ") @RequestParam(name = "note", required = false) String note,
			@ApiParam(name = "delayDay", value = "（非必填 办理天数") @RequestParam(name = "delayDay", required = false) Integer delayDay,
			@ApiParam(name = "shrid", value = "（必填提交审核人编号") @RequestParam(name = "shrid") Integer shrid){
String msg = null;
		try {
			
			msg=	myService.handletask(proId,taskid, type, files, note, depid, delayDay, userId, files1, files2,shrid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());
		}

		return new ResponseOth(ResponseConstants.CODE_OK, "操作成功", msg);

	}

	@RequestMapping(value = "/getCurrentTask", method = RequestMethod.GET)
	@ApiOperation(value = "获取当前流程任务", notes = "获取当前流程任务")
	public ResponseOth getCurrentTask(
			@ApiParam(name = "processInstanceId", value = "流程实列id") @RequestParam(name = "processInstanceId") String processInstanceId

	) {
		TaskVO tasks;
		try {
			tasks = myService.getCurrentTask(processInstanceId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());

		}

		return new ResponseOth(ResponseConstants.CODE_OK, tasks, "已实现");

	}
	
	
	@RequestMapping(value = "/transferAssignee", method = RequestMethod.GET)
	@ApiOperation(value = "转办", notes = "获取当前流程任务")
	public ResponseOth transferAssignee(
			@ApiParam(name = "myid", value = "操作人id") @RequestParam(name = "myid") String myid,
			@ApiParam(name = "taskid", value = "任务id") @RequestParam(name = "taskid") String taskid,
			@ApiParam(name = "userid", value = "用户id") @RequestParam(name = "userid") String userid,
			@ApiParam(name = "caseid", value = "案件id") @RequestParam(name = "caseid") Integer caseid,
			@ApiParam(name = "note", value = "备注") @RequestParam(name = "note",required=false) String note,
			@ApiParam(name = "file", value = "附件") @RequestParam(name = "file",required=false) String file,
			@ApiParam(name = "processid", value = "流程id") @RequestParam(name = "processid") String processid

	) {
		
		try {
			 myService.transferAssignee(myid,taskid,userid,caseid,note,file,processid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());

		}

		return new ResponseOth(ResponseConstants.CODE_OK, "转办成功", "已实现");

	}
	@RequestMapping(value = "/addsign", method = RequestMethod.GET)
	@ApiOperation(value = "加签", notes = "获取当前流程任务")
	public ResponseOth addsign(
			@ApiParam(name = "myid", value = "操作人id") @RequestParam(name = "myid") String myid,
			@ApiParam(name = "taskid", value = "任务id") @RequestParam(name = "taskid") String taskid,
			@ApiParam(name = "userid", value = "用户id") @RequestParam(name = "userid") String userid,
			@ApiParam(name = "caseid", value = "案件id") @RequestParam(name = "caseid") Integer caseid,
			@ApiParam(name = "note", value = "备注") @RequestParam(name = "note",required=false) String note,
			@ApiParam(name = "file", value = "附件") @RequestParam(name = "file",required=false) String file,
			@ApiParam(name = "processid", value = "流程id") @RequestParam(name = "processid") String processid

	) {
		
		try {
			 myService.addsign(myid,taskid,userid,caseid,note,file,processid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());

		}

		return new ResponseOth(ResponseConstants.CODE_OK, "转办成功", "已实现");

	}
	
	

}