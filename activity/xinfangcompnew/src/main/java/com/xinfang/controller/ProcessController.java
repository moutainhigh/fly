package com.xinfang.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.VO.TaskVO;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.ResponseOth;
import com.xinfang.service.ActivitiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/V1/process")
@Api(description = "流程控制（张华荣）")
public class ProcessController {
	 @Autowired
	 private ActivitiService myService;
	 
	@RequestMapping(value = "/startassigen", method = RequestMethod.POST)
	@ApiOperation(value = "开始办案", notes = "开始办案需传入单位信息")
	public ResponseOth  startassigen(
			@ApiParam(name = "userid", value = "用户id") @RequestParam(name = "userid") long userid ,
			@ApiParam(name = "leaderid", value = "用户领导id") @RequestParam(name = "leaderid") int leaderid,
			@ApiParam(name = "caseid", value = "案件id") @RequestParam(name = "caseid") int caseid,
			@ApiParam(name = "type", value = "1交办 2转办") @RequestParam(name = "type") int type,
			@ApiParam(name = "state", value = "流程状态2000 正办 1000代办 3000已办") @RequestParam(name = "state") int state,
			@ApiParam(name = "files", value = "附件信息") @RequestParam(name = "files",required=false) String files,
			@ApiParam(name = "note", value = "备注") @RequestParam(name = "note",required=false) String note,
			@ApiParam(name = "depid", value = "职能单位id") @RequestParam(name = "depid") int depid,
			@ApiParam(name = "handleday", value = "处理期限") @RequestParam(name = "handleday") int handleday,
			@ApiParam(name = "needReceipt", value = "是否需要回执") @RequestParam(name = "needReceipt") int needReceipt,
			@ApiParam(name = "xbdepinfo", value = "协办单位信息  格式 单位id,办理期限,是否需要回执  如   1,30,1|2,10,0|3,14,1") @RequestParam(name = "xbdepinfo",required=false) String xbdepinfo,
			@ApiParam(name = "taskid", value = "任务id,回退后调用") @RequestParam(name = "taskid",required=false,defaultValue="") String taskid
			) {
		String processid=null;
		try{
			processid =myService.startProcess(userid, caseid,leaderid,type,2000,depid,files,note,xbdepinfo,handleday,needReceipt,taskid);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED,  "操作失败", e.getMessage());
		}
		
		return new ResponseOth(ResponseConstants.CODE_OK,  processid, "已实现");
		
	}
	
	@RequestMapping(value = "/processmonitoring", method = RequestMethod.GET)
	@ApiOperation(value = "流程监控", notes = "流程监控http://120.24.228.17:8081/diagram-viewer/index.html?processDefinitionId=jiaoban2:15:45010&processInstanceId=497609")
	public ResponseOth  processMonitoring(
			@ApiParam(name = "deploymentId", value = "流程部署id") @RequestParam(name = "deploymentId") String deploymentId  ,
			HttpServletResponse respons
			
			
			) {
		OutputStream out = null;
		 InputStream in = myService.getDiagram(deploymentId);
		// respons.setContentType("multipart/form-data"); 
		 respons.setContentType("image/png"); 
		 
		 //respons.setCharacterEncoding("UTF-8");
		  try {
			out = respons.getOutputStream();
			out.write(getImgByte(in));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return new ResponseOth(ResponseConstants.MY_CODE_FAILED,  "操作失败", "暂未实现");
		
	}
	
	
	@RequestMapping(value = "/deleteprocess", method = RequestMethod.POST)
	@ApiOperation(value = "删除流程", notes = "删除流程")
	public ResponseOth deleteprocess(
			@ApiParam(name = "processid", value = "流程实列id") @RequestParam(name = "processid") String processid,
			@ApiParam(name = "reason", value = "终止原因") @RequestParam(name = "reason") String reason,
			@ApiParam(name = "caseid", value = "案件id") @RequestParam(name = "caseid") Integer caseid

	) {
		try {
			 myService.endProcess(processid,reason,caseid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseOth(ResponseConstants.MY_CODE_FAILED, "操作失败", e.getMessage());

		}

		return new ResponseOth(ResponseConstants.CODE_OK, "流程终止成功，请重新开始流程", "已实现");

	}
	
	
	private byte[] getImgByte(InputStream is) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int b;
		while((b=is.read())!=-1){
			out.write(b);
		}
		byte[] result = out.toByteArray();
		out.close();
		return result;
	}
	
}