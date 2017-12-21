package cn.jkm.app.api.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import cn.jkm.common.web.interceptor.BaseTokenInterceptor;
import cn.jkm.common.web.token.ITokenManager;
import cn.jkm.uis.facade.entities.User;


public class TokenInterceptor extends BaseTokenInterceptor<User> {
	private static final String ignoreUrl[] = { "/login/login", "/Login/register" };

	@Autowired(required = true)
	public TokenInterceptor(@Qualifier("tokenManager") ITokenManager<User> tokenManager) {
		super(ignoreUrl, tokenManager);
	}
}
