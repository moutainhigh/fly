package com.xinfang.controller;

import com.xinfang.Exception.BizException;
import com.xinfang.context.Response;
import com.xinfang.context.ResponseConstants;
import com.xinfang.context.ResponseOth;
import com.xinfang.context.Responses;
import com.xinfang.model.FcJzb;
import com.xinfang.model.FcRyb;
import com.xinfang.service.ActivitiService;
import com.xinfang.service.RoleService;
import com.xinfang.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/V1/comp")
@Api(description = "流程测试（张华荣）")
public class MyRestController {
    @Autowired
    private ActivitiService myService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @ApiOperation(value = "稳控人员登录", notes = "稳控人员登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Response login(@ApiParam(name = "name", value = "名字") @RequestParam(name = "name") String name
    ) {

        FcRyb result = null;
        try {
            result = userService.findUser(name);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, e.getMessage());
        }
        return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "登录成功");

    }
    
    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
	@ApiOperation(value = "添加人员", notes = "测试")
	@RequestMapping(value = "/createrusere", method = RequestMethod.POST)
	public ResponseOth createrusere( @ApiParam(name = "userid", value = "老系统用户id") @RequestParam int userid) {
		User result = null;
        try {
            result = userService.createrusere(userid);
            if (result == null) throw BizException.DB_INSERT_RESULT_0;
        } catch (Exception e) {
            return new ResponseOth(ResponseConstants.MY_CODE_FAILED,"failure", e.getMessage());
        }
        return new ResponseOth(ResponseConstants.CODE_OK,  result, "success");
	}

    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ApiOperation(value = "创建角色", notes = "测试")
    @RequestMapping(value = "/createrRole", method = RequestMethod.POST)
    public Responses createrRole(@ApiParam(name = "role", value = "用户角色") @ModelAttribute FcJzb role) {
        FcJzb result = null;
        try {
            result = roleService.addRole(role);
            if (result == null) throw BizException.DB_INSERT_RESULT_0;
        } catch (Exception e) {
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, ResponseConstants.CODE_FAILED_VALUE, e.getMessage());
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "success", result);
    }


    @ApiOperation(value = "为用户分配角色", notes = "为用户分配角色")
    @RequestMapping(value = "/createMembership", method = RequestMethod.POST)
    public Response createMembership(@ApiParam(name = "userId", value = "用户id") @RequestParam(name = "userId") Integer userId, @ApiParam(name = "roleId", value = "角色id") @RequestParam(name = "roleId") String roleId, @ApiParam(name = "depid", value = "单位id") @RequestParam(name = "depid") String depid
    ) {

        FcRyb result = null;
        try {
            roleService.createMembership(userId, roleId, depid);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, e.getMessage());
        }
        return new Response(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "分配成功");

    }

    @ApiOperation(value = "查看用户角色", notes = "查看用户角色")
    @RequestMapping(value = "/getrolebyuser", method = RequestMethod.GET)
    public Responses getrolebyuser(@ApiParam(name = "userId", value = "用户id") @RequestParam(name = "userId") String userId
    ) {

        List<Group> result = null;
        try {
            result = roleService.getrolebyuser(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Responses(ResponseConstants.SUCCESS_FAILED, ResponseConstants.CODE_FAILED, "查看失败", e.getMessage());
        }
        return new Responses(ResponseConstants.SUCCESS_OK, ResponseConstants.CODE_SUCCESS, "查看成功", result);

    }
    
    
    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
   	@ApiOperation(value = "同步人员", notes = "同步老系统人员到activit库")
   	@RequestMapping(value = "/syncusere", method = RequestMethod.POST)
   	public ResponseOth syncusere( ) {
   		User result = null;
           try {
               result = userService.syncusere();
               if (result == null) throw BizException.DB_INSERT_RESULT_0;
           } catch (Exception e) {
               return new ResponseOth(ResponseConstants.MY_CODE_FAILED,"failure", e.getMessage());
           }
           return new ResponseOth(ResponseConstants.CODE_OK,  result, "success");
   	}


  /*  @ApiOperation(value = "查看用户角色", notes = "查看用户角色")
    @RequestMapping(value = "/process/{personId}/{caseId}", method = RequestMethod.GET)
    public void startProcessInstance(@ApiParam(name = "personId", value = "收文单位收文岗用户id") @PathVariable Long personId, @ApiParam(name = "caseId", value = "案件idid") @PathVariable Long caseId) {
        myService.startProcess(personId, caseId);
    }*/

  /*  //获取当前人的任务
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = myService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }*/
    

  /*  //完成任务
    @RequestMapping(value = "/complete/{joinApproved}/{taskId}/{personid}", method = RequestMethod.GET)
    public String complete(@PathVariable Boolean joinApproved, @PathVariable String taskId, @PathVariable String personid) {
        myService.completeTasks(joinApproved, taskId, personid);
        return "ok";
    }
*/
    //Task的dto
    static class TaskRepresentation

    {
        private String id;
        private String name;
        private String caseid;

        public TaskRepresentation(String id, String name) {
            this.id = id;
            this.name = name;
        }


        public TaskRepresentation(String id, String name, String caseid) {
            super();
            this.id = id;
            this.name = name;
            this.caseid = caseid;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}