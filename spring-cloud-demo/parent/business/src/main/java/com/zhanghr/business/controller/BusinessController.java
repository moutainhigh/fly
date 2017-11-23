package com.zhanghr.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zhanghr.business.content.NewModel;
import com.zhanghr.business.entity.UserEntity;
import com.zhanghr.business.feign.UserFeignClient;

@RestController
@NewModel
public class BusinessController {

  @Autowired
  private UserFeignClient userFeignClient;

  @GetMapping("/movie/{id}")
  public UserEntity findById(@PathVariable Long id) {
    return this.userFeignClient.findById(id);
  }

  @GetMapping("/test")
  public UserEntity testPost(UserEntity user) {
    return this.userFeignClient.postUser(user);
  }

}
