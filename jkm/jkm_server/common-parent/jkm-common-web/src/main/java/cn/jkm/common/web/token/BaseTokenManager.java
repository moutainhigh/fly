package cn.jkm.common.web.token;

public class BaseTokenManager<T> implements ITokenManager<T> {
	private ITokenCacheManager<T> cacheManager;

	public BaseTokenManager(ITokenCacheManager<T> cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public T getUser(String token) {
		return cacheManager.getUser(token);
	}

	@Override
	public String getToken(T t) {
		return cacheManager.getToken(t);
	}

	@Override
	public boolean isVaildToken(String token) {
		return cacheManager.isVaildToken(token);
	}

}
