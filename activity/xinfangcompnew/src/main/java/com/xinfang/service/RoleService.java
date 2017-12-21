package com.xinfang.service;

import java.util.Date;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinfang.dao.RoleRepository;
import com.xinfang.dao.UserRepository;
import com.xinfang.model.FcJzb;
import com.xinfang.model.FcRyb;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	IdentityService identityService;//它可以管理（创建，更新，删除，查询...）群组和用户 Activiti中内置了用户以及组管理的功能，必须使用这些用户和组的信息才能获取到相应的Task。IdentityService提供了对Activiti 系统中的用户和组的管理功能
	
	public FcJzb addRole(FcJzb role){
		Date now = new Date();
		role.setCjsj(now);
		role.setXgsj(now);
		FcJzb result =roleRepository.save(role);
		Group group =identityService.newGroup(result.getJzId().toString());
		group.setName(role.getJzMc());// 以后单位id+“:"+角色名称
    	group.setType("xf");//此处以后可以存岗位
    	 identityService.saveGroup(group); 
    	 Group mygroup = identityService.createGroupQuery().groupId(role.getJzId().toString()).singleResult();
    	 System.out.println(mygroup);
		return result;
	}
	
	public void createMembership(Integer userid,String roleId, String depid){
		FcRyb ry = userRepository.findById(userid);
		User user = identityService.newUser(ry.getRyId().toString());
		 user.setFirstName(ry.getRyMc());
	     user.setLastName(ry.getRyMc());
	     user.setEmail("xxxxxxxx@qq.com");
	     user.setPassword(ry.getPassword());
	     user.setId(ry.getRyId().toString());
	  
	     
	       FcJzb role=    roleRepository.findById(Integer.parseInt(roleId));
	       Group group =identityService.newGroup(depid+"|"+role.getJzId());
		   group.setName(depid+":"+role.getJzMc());// 以后单位id+“:"+角色名称
	       group.setType(depid);//此处以后可以存岗位
	       identityService.saveUser(user);
	       identityService.saveGroup(group);
    	 
    	   identityService.createMembership(user.getId(),group.getId());
    	 

	}
	
	public List<Group> getrolebyuser(String userid){
		List<Group> result = identityService.createGroupQuery().groupMember(userid).list();
		return result;
	}
	
	

}
