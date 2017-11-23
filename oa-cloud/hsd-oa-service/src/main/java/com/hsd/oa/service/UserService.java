package com.hsd.oa.service;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsd.oa.dao.UserRepository;
import com.hsd.oa.model.FcRyb;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	IdentityService identityService;
	
	public FcRyb findUser( String name){
		 return userRepository.findUser(name);
	 }
	
	public List<FcRyb> getUsers(){
		 return userRepository.findAll();
	 }
	
	public List<Group> getall(){
    	List<Group> groupList = identityService.createGroupQuery().list(); 
    	return groupList;
    }
	
	public List<Group> getbyuserid(String userid){
    	List<Group> groupList = identityService.createGroupQuery().groupMember(userid).list(); 
    	return groupList;
    }

	public User createrusere(int userid) {
		FcRyb ry = userRepository.findById(userid);
		User user = identityService.newUser(ry.getRyId().toString());
		 user.setFirstName(ry.getRyMc());
	     user.setLastName(ry.getRyMc());
	     user.setEmail("xxxxxxxx@qq.com");
	     user.setPassword(ry.getPassword());
	     user.setId(ry.getRyId().toString());
	     identityService.saveUser(user);
		return user;
	}

	public User syncusere() {
		List<FcRyb> ry = userRepository.findAll();
		for(FcRyb ryb:ry){
			try {
				createrusere(ryb.getRyId());
				System.out.println("id:"+ryb.getRyId() +" ; "+ryb.getRyMc());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	

}
