package com.hsd.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.Request;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//http://www.roncoo.com/article/detail/128836#0-qzone-1-6256-d020d2d2a4e8d1a374a433f596ad1440 单位测试 代码覆盖率
public class PortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}
	
	   @Bean
	    Request.Options feignOptions() {
	        return new Request.Options(/**connectTimeoutMillis**/1 * 10000, /** readTimeoutMillis **/1 * 10000);
	    }
}
