package cn.jkm.api.klm.manage.service.useraccount;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.service.eb.UserAccountService;
@Component("paymentNotify1.0")
public class PaymentNotify extends AbstractManageService{

	@Reference(version = "1.0")
	 UserAccountService userAccountService;
	
	@Override
	public Map service(Map request) {
		userAccountService.paymentNotify(request);
		return request;
	}

}
