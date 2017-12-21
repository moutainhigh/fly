package cn.jkm.main.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.jkm.common.core.controller.BaseController;
import cn.jkm.common.core.controller.ReturnMessage;
import cn.jkm.eb.facade.mq.PayResultMsgFacade;
import cn.jkm.uis.facade.UserFacade;
import cn.jkm.uis.facade.entities.User;


@RestController
@RequestMapping("login")
public class LoginController extends BaseController {
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private PayResultMsgFacade payResultMsgFacade;
	
	public LoginController() {

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ReturnMessage login(User user) {

		return new ReturnMessage(ReturnMessage.error1,userFacade.test());
	}
	
	@RequestMapping(value = "/sendMsg", method = RequestMethod.GET)
	public ReturnMessage sendMsg(String orderId,String payStatu) {
		try {
			payResultMsgFacade.mqProduce(orderId, payStatu);
//			for (int i = 0; i < 10; i++) {
//				payResultMsgFacade.mqProduce(orderId+i, payStatu+i);
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ReturnMessage(ReturnMessage.error1,"success");
	}
	
	@RequestMapping(value = "/testExcetpion", method = RequestMethod.GET)
	public ReturnMessage testExcetpion(){
		userFacade.testException();
		return new ReturnMessage(ReturnMessage.error1,"success");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ReturnMessage register(User user) {

		return new ReturnMessage(ReturnMessage.error1);
	}
}
