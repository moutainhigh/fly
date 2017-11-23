package com.zhanghr.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghr.user.mapper.UserMapper;
import com.zhanghr.user.model.UserEntity;

@Service
public class UserService {
	
 @Autowired
 private UserMapper userMapper;

public UserEntity findOne(Long id) {
	return userMapper.getOne(id);
}   
 
 

}
