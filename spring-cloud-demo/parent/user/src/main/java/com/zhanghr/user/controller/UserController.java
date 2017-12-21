package com.zhanghr.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.zhanghr.user.model.UserEntity;
import com.zhanghr.user.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	 @Autowired
	   EurekaClient eurekaClient;

	  @Autowired
	   DiscoveryClient discoveryClient;
	
	 
	 @GetMapping("/simple/{id}")
	  public UserEntity findById(@PathVariable Long id) {
		 System.out.println("---user 1-----");
	    return this.userService.findOne(id);
	  }

	  @GetMapping("/eureka-instance")
	  public String serviceUrl() {
	    InstanceInfo instance = this.eurekaClient.getNextServerFromEureka("MICROSERVICE-PROVIDER-USER", false);
	    return instance.getHomePageUrl();
	  }

	  @GetMapping("/instance-info")
	  public ServiceInstance showInfo() {
	    ServiceInstance localServiceInstance = this.discoveryClient.getLocalServiceInstance();
	    return localServiceInstance;
	  }

	  @PostMapping("/user")
	  public UserEntity postUser(@RequestBody UserEntity user) {
	    return user;
	  }

	  // 该请求不会成功
	  @GetMapping("/get-user")
	  public UserEntity getUser(UserEntity user) {
	    return user;
	  }

	  @GetMapping("list-all")
	  public List<UserEntity> listAll() {
	    ArrayList<UserEntity> list = Lists.newArrayList();
	    UserEntity user = new UserEntity(1L, "zhangsan");
	    UserEntity user2 = new UserEntity(2L, "zhangsan");
	    UserEntity user3 = new UserEntity(3L, "zhangsan");
	    list.add(user);
	    list.add(user2);
	    list.add(user3);
	    return list;

	  }

}
