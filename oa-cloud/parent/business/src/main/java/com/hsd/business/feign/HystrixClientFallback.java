package com.hsd.business.feign;

import org.springframework.stereotype.Component;

import com.hsd.business.entity.UserEntity;

@Component
public class HystrixClientFallback implements UserFeignClient {

 /* @Override
  public UserEntity findById(Long id) {
	  UserEntity user = new UserEntity();
    user.setId(0L);
    user.setName("服务不可用！");
    return user;
  }

@Override
public UserEntity postUser(UserEntity user) {
	return null;
}*/


}