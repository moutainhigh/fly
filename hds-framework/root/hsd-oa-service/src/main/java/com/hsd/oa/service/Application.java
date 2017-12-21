package com.hsd.oa.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 
 * @title Application.java
 * @package com.xinfang
 * @description TODO
 * @author ZhangHuaRong   
 * @update 2016年12月20日 下午8:39:09
 */
/*@Controller
@EnableWebMvc
@SpringBootApplication*/

/**
 * 
     * 此类描述的是:
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:38:12  
 */


@SpringBootApplication
@ComponentScan("com.hsd.oa.service.*,org.activiti.rest,com.hsd.model.*")
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration  
@ServletComponentScan
@EnableEurekaClient
public class Application extends WebMvcConfigurerAdapter {
	
	
	
	

    @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager) {
        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        return new Object();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

  /*  @RequestMapping("/")
    String home() {
        return "redirect:countries";
    }*/
}
