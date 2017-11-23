package cn.jkm.common.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import cn.jkm.common.config.Constant;
import cn.jkm.common.core.controller.ReturnMessage;
import cn.jkm.common.web.token.ITokenManager;


public class BaseTokenInterceptor<T> extends HandlerInterceptorAdapter {
	private String[] ignoreUrl;
	private ITokenManager<T> tokenManager;

	public BaseTokenInterceptor(String[] ignoreUrl, ITokenManager<T> tokenManager) {
		this.ignoreUrl = ignoreUrl;
		this.tokenManager = tokenManager;
	}

	public BaseTokenInterceptor(ITokenManager<T> tokenManager) {
		this(new String[] {}, tokenManager);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean flag = false;
		String url = request.getRequestURL().toString();
		for (String s : ignoreUrl) {
			if (url.contains(s)) {
				flag = true;
				break;
			}
		}

		if (!flag) {
			String token = request.getParameter(Constant.TOKEN);
			if (token != null && !token.isEmpty()) {
				flag = tokenManager.isVaildToken(token);
			}

			if (!flag) {
				ReturnMessage message = new ReturnMessage(ReturnMessage.error3_token);
//>>>>>>> 2645eb3befacf696ee2b487f0b75b8fd52e795f1
				response.getWriter().write(message.toString());
			}
		}
		return flag;
	}
}
