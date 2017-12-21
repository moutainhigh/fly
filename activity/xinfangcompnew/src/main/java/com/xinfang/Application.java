package com.xinfang;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.xinfang.log.ApiLog;
import com.xinfang.log.SuperLog;
import com.xinfang.utils.DateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Date;

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
 * @description http://demo.kafeitu.me:8080/kft-activiti-demo/login/ activit学习资料
 * @author ZhangHuaRong   
 * @update 2017年3月28日 下午6:58:33
 */

@SpringBootApplication
@ComponentScan("com.xinfang.*,org.activiti.rest")
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration  
@ServletComponentScan
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
        Date now = new Date();
        SuperLog.chargeLog1("3 : "+now.toLocaleString());
		SuperLog.chargeLog1("4 : "+DateUtils.formatDateTime(now));
        return new Object();
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
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
