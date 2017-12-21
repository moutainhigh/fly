package cn.jkm.api.klm.manage.service.useraccount;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.useraccount.UserAccount;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.UserAccountService;

/**
 * 
 * @description TODO
 * @author ZhangHuaRong   
 * @update 2017年7月21日 下午1:34:05
 */
@Component("getUserAccount1.0")
@NotNull(name = {"userId"}, message = "请输入用户id")
public class GetUserAccount extends AbstractManageService {
	
	 @Reference(version = "1.0")
	  UserAccountService userAccountService;

	@Override
	public Map service(Map request) {
        String userId= formatString(request.get("userId"));
        UserAccount useraccount = userAccountService.find(userId);
        return ApiResponse.success().body(useraccount);
	}

}
