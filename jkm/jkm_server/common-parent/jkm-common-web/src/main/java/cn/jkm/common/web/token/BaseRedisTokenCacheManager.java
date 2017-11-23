package cn.jkm.common.web.token;

import java.util.UUID;
import org.springframework.data.redis.core.RedisTemplate;

public class BaseRedisTokenCacheManager<T> implements ITokenCacheManager<T> {
	private RedisTemplate<String, T> tokenRedisTemplate;

	public BaseRedisTokenCacheManager(RedisTemplate<String, T> tokenRedisTemplate) {
		this.tokenRedisTemplate = tokenRedisTemplate;
	}

	@Override
	public T getUser(String token) {
		return tokenRedisTemplate.opsForValue().get(token);
	}

	@Override
	public String getToken(T t) {
		String token = UUID.randomUUID().toString();
		tokenRedisTemplate.opsForValue().set(token, t);
		return token;
	}

	@Override
	public boolean isVaildToken(String token) {
		return tokenRedisTemplate.hasKey(token);
	}

}
