package cn.jkm.common.web.token;

public interface ITokenCacheManager<T> {
	public T getUser(String token);

	public String getToken(T t);

	public boolean isVaildToken(String token);
}
