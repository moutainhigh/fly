package com.hsd.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hsd.business.content.NewModel;
import com.hsd.business.entity.UserEntity;
import com.hsd.business.feign.OaFeignClient;
import com.hsd.business.feign.UserFeignClient;
import com.hsd.core.Response;

import feign.Param;

@RestController
@NewModel
public class BusinessController {

  @Autowired
  private UserFeignClient userFeignClient;
  @Autowired
  private OaFeignClient oaFeignClient;

 /* @GetMapping("/movie/{id}")
  public UserEntity findById(@PathVariable Long id) {
    return this.userFeignClient.findById(id);
  }

  @GetMapping("/test")
  public UserEntity testPost(UserEntity user) {
    return this.userFeignClient.postUser(user);
    
  }*/
  @GetMapping("/monitor/{id}")
  public Response monitor(@PathVariable("id") String id) {
	  System.out.println("id==="+id);
	  return this.oaFeignClient.monitor(id);
  }; // 
  
  @GetMapping("/getUsers/{id}")
  public String monitor1(@PathVariable("id") Integer id) {
	  System.out.println("id==="+id);
	  return this.oaFeignClient.getUsers(id);
  }; // 

}
