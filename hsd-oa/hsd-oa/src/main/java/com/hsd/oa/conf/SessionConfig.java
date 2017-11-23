package com.hsd.oa.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
/**
 * 
     * 此类描述的是:
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:48:02
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {
	
}
