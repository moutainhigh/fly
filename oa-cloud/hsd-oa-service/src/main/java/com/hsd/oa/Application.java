package com.hsd.oa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.hsd.oa.log.ApiLog;

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
@ComponentScan("com.hsd.oa.*,org.activiti.rest")
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration  
@ServletComponentScan
@EnableEurekaClient
public class Application extends WebMvcConfigurerAdapter {
	
	
	
	/*@Bean
	 * @Bean(name = "txManager1")
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }*/
	
	//   @Bean
	    public Module jacksonAfterBurnerModule() {
	        return new AfterburnerModule();
	    }

	//    @Bean
	    public HttpMessageConverter httpSmileJackson2MessageConverter() {
	        return new AbstractJackson2HttpMessageConverter(
	                new ObjectMapper(new SmileFactory()).registerModule(new AfterburnerModule()),
	                new MediaType("application", "x-jackson-smile")) {
	        };
	    }

    @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager) {
        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        ApiLog.chargeLog1(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        return new Object();
    }

    public static void main(String[] args) {
    	ApiLog.chargeLog1("===============================");
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    String home() {
        return "redirect:countries";
    }
}
