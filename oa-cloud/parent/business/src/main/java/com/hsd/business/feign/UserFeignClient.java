package com.hsd.business.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

import com.hsd.business.entity.UserEntity;

import feign.Param;
import feign.RequestLine;

@FeignClient(name="user",fallback = HystrixClientFallback.class)
public interface UserFeignClient {
  
  
  
/*  @RequestLine("GET /simple/{id}")
  public UserEntity findById(@Param("id") Long id); // 两个坑：1. @GetMapping不支持   2. @PathVariable得设置value

  @RequestLine("POST /user")
  public UserEntity postUser(@Param("user") UserEntity user);*/

  
}
