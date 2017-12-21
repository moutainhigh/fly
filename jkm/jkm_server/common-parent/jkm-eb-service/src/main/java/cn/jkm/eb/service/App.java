package cn.jkm.eb.service;

import com.google.common.io.Resources;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jkm.eb.facade.MsgFacade;
import cn.jkm.eb.facade.OrderFacade;


/**
 * Hello world!
 *
 */
public class App {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-context.xml");
//		OrderFacade orderFacade = (OrderFacade) context.getBean("orderService");
//		orderFacade.pageList();
//		while (true) {
//			try {
//				Thread.sleep(500000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}
}