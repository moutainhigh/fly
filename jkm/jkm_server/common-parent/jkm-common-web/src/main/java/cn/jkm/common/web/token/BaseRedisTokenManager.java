package cn.jkm.common.web.token;

import org.springframework.data.redis.core.RedisTemplate;

public class BaseRedisTokenManager<T> extends BaseTokenManager<T> {
	public BaseRedisTokenManager(RedisTemplate<String, T> tokenRedisTemplate) {
		super(new BaseRedisTokenCacheManager<T>(tokenRedisTemplate));
	}
}
