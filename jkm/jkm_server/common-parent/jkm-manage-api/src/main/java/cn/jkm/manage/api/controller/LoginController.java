package cn.jkm.manage.api.controller;

import cn.jkm.uis.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.jkm.common.core.controller.BaseController;
import cn.jkm.common.core.controller.ReturnMessage;
import cn.jkm.uis.facade.entities.User;


@RestController
@RequestMapping("login")
public class LoginController extends BaseController {

	@Autowired
	private UserFacade userService;

	public LoginController() {

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ReturnMessage login(User user) throws Exception {

		return new ReturnMessage(ReturnMessage.error1, userService.test());
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ReturnMessage register(User user) {

		return new ReturnMessage(ReturnMessage.error1);
	}
}
