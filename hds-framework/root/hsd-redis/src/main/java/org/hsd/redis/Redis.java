package org.hsd.redis;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.hsd.core.spring.SpringApplicationContext;


public class Redis {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(Redis.class);
    
    private RedisTemplate<String,Object> redisTemplate;

	public Redis() {
		super();
		this.redisTemplate = SpringApplicationContext.getBean("redisTemplate");
	}  
    
	  public static Redis buildRedis() {
	        return new Redis();
	    }
	
	    public  void set( String key,  Object value, long expire) {  
	        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);  
	    }  
	  
	    /** 
	     * 读取缓存 
	     *  
	     * @param key 
	     * @param clazz 
	     * @return 
	     */  
	    @SuppressWarnings("unchecked")  
	    public  <T> T get( String key, Class<T> clazz) {  
	        return (T) redisTemplate.boundValueOps(key).get();  
	    }  
	      
	    /** 
	     * 读取缓存 
	     * @param key 
	     * @return 
	     */  
	    public  Object getObj( String key){  
	        return redisTemplate.boundValueOps(key).get();  
	    }  
	  
	    /** 
	     * 删除，根据key精确匹配 
	     *  
	     * @param key 
	     */  
	    public  void del( String... key) {  
	        redisTemplate.delete(Arrays.asList(key));  
	    }  
	  
	    /** 
	     * 批量删除，根据key模糊匹配 
	     *  
	     * @param pattern 
	     */  
	    public  void delpn( String... pattern) {  
	        for (String kp : pattern) {  
	            redisTemplate.delete(redisTemplate.keys(kp + "*"));  
	        }  
	    }  
	  
	    /** 
	     * key是否存在 
	     *  
	     * @param key 
	     */  
	    public  boolean exists( String key) {  
	        return redisTemplate.hasKey(key);  
	    }  
    
    

}
