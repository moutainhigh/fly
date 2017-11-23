package cn.jkm.uis.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cn.jkm.uis.facade.UserFacade;


/**
 * Hello world!
 *
 */
public class App {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-context.xml");
		UserFacade userFacade = (UserFacade) context.getBean("userService");
		userFacade.test();
//		try {
//			System.out.println(userFacade.test());
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
//		while (true) {
//		}
	}
}
