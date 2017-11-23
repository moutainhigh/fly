package cn.jkm.provider.eb.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alipay.api.response.AlipayTradePrecreateResponse;

import cn.jkm.core.domain.mysql.useraccount.UserAccount;
import cn.jkm.provider.eb.v1_0.useraccount.UserAccountServiceImpl;
import cn.jkm.service.eb.UserAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
//@Rollback
public class UserAccountServiceTest {
	
	UserAccountService userAccountService = new UserAccountServiceImpl();
	
	UserAccountServiceImpl userAccountService2 = new UserAccountServiceImpl();
	
	@Test
	public void init(){
		String userid= /*UUID.randomUUID().toString()*/ "ca9be09a-f10b-4e51-bf6b-abb219fe9870";
		UserAccount account=  userAccountService.init(userid);
		System.out.println(account);
	}
	
	/**
	 * 
	 * @description TODO
	 * @version 1.0
	 * @author ZhangHuaRong
	 * @update 2017年7月20日 下午4:06:27
	 */
	@Test
	public void payment(){
		Object result =	userAccountService2.payment("测试","88","10010a","1");
		System.out.println(result);
	}
	
	

}
