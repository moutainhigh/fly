package cn.jkm.app.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.jkm.common.core.controller.BaseController;
import cn.jkm.common.core.controller.ReturnMessage;
import cn.jkm.uis.facade.entities.User;


@RestController
@RequestMapping("login")
public class LoginController extends BaseController {
	public LoginController() {

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ReturnMessage login(User user) {

		return new ReturnMessage(ReturnMessage.error1);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ReturnMessage register(User user) {

		return new ReturnMessage(ReturnMessage.error1);
	}
}
