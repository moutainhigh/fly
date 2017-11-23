package com.hsd.oa.service.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
     * 此类描述的是:
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:47:43
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

   
    
    @Bean
    public Docket compApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("hsdOA系统api")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.hsd.oa.service.controller"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(aoInfo());
    }

    

   

    private ApiInfo aoInfo() {
        ApiInfo apiInfo = new ApiInfo("hsd_oa接口Api",
                "流程相关接口接口",
                "0.1",
                "hsd",
                "zhanghr",
                "hsd",
                "http://www.baidu,com"
        );
        return apiInfo;
    }


    /****************************************************************************************************************/


    /**
     * SpringBoot默认已经将classpath:/META-INF/resources/和classpath:/META-INF/resources/webjars/映射
     * 所以该方法不需要重写，如果在SpringMVC中，可能需要重写定义（我没有尝试）
     * 重写该方法需要 extends WebMvcConfigurerAdapter
     *
     */

     public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        
        registry.addResourceHandler("/Tleaf/**")
        .addResourceLocations("classpath:/templates/");
    }

}
