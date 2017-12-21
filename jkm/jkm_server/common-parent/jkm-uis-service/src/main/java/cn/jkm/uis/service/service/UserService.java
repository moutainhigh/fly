package cn.jkm.uis.service.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.jkm.common.exception.ServiceException;
import cn.jkm.uis.facade.UserFacade;
import cn.jkm.uis.facade.entities.User;
import cn.jkm.uis.service.dao.UserMapper;


@Service("userService")
public class UserService implements UserFacade{
	@Autowired
	private UserMapper userMapper;

	public UserService() {

	}

	
	@Override
	public String test(){
		User user=new User();
//		user.setId(UUID.randomUUID().toString().replace("-", "").trim());
//		user.setNickname("xingerhong123");
		userMapper.insert(user);
		
		int i=10/0;
		return "test";
	}

	@Override
	public void testException() throws NullPointerException,ServiceException {
//		{throw new NullPointerException();}
		throw new ServiceException("业务异常");
	}
}
