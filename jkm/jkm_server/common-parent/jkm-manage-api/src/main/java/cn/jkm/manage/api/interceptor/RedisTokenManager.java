package cn.jkm.manage.api.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import cn.jkm.common.web.token.BaseRedisTokenManager;
import cn.jkm.uis.facade.entities.User;


@Component("tokenManager")
public class RedisTokenManager extends BaseRedisTokenManager<User> {
	@Autowired(required = true)
	public RedisTokenManager(@Qualifier("tokenRedisTemplate") RedisTemplate<String, User> tokenRedisTemplate) {
		super(tokenRedisTemplate);
	}
}
