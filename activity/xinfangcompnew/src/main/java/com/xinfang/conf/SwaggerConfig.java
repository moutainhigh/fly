package com.xinfang.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.xinfang.context.AppModel;
import com.xinfang.context.NewModel;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @title SwaggerConfig.java
 * @package com.xinfang.conf
 * @description swagger配置
 * @author ZhangHuaRong   
 * @update 2016年12月20日 下午8:29:26
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

   // @Bean
    public Docket memberApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("敏感调度")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.xinfang.controller"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(loginApiInfo());
    }
    
     @Bean
    public Docket actApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("流程监控")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.xinfang.activit"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(loginApiInfo());
    }

     
     @Bean
     public Docket newApi() {
    	 RequestHandlerSelectors.basePackage("com.xinfang.supervise");
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("新模块")
                 .select()  // 选择那些路径和api会生成document
                 .apis(RequestHandlerSelectors.withClassAnnotation(NewModel.class))
                 //.apis(RequestHandlerSelectors.basePackage("com.xinfang.supervise"))
                // .apis(RequestHandlerSelectors.basePackage("com.xinfang.activit"))
                 .paths(PathSelectors.any()) // 对所有路径进行监控
                 .build()
                 .apiInfo(loginApiInfo());
     }
     
     
     @Bean
     public Docket apiApi() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("外网app接口")
                 .select()  // 选择那些路径和api会生成document
                 .apis(RequestHandlerSelectors.withClassAnnotation(AppModel.class))
                 .paths(PathSelectors.any()) // 对所有路径进行监控
                 .build()
                 .apiInfo(appInfo());
     }
     
     @Bean
     public UiConfiguration getUiConfig() {
    	 UiConfiguration conf = UiConfiguration.DEFAULT;
         return conf;
     }
   
   // @Bean
    public Docket dschedApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("日常调度")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.xinfang.dsched.controller"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(dschInfo());
    }
    
    @Bean
    public Docket compApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("投诉办理")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.xinfang.controller"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(compApiInfo());
    }

    @Bean
    public Docket personManageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("机构人员管理")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.xinfang.personnelmanagement.controller"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(personManageApiInfo());
    }
    
    private ApiInfo appInfo() {
        ApiInfo apiInfo = new ApiInfo("信访接口Api",
                "外网app接口",
                "0.1",
                "汉图",
                "汉图",
                "汉图",
                "http://www.baidu,com"
        );
        return apiInfo;
    }

    private ApiInfo loginApiInfo() {
        ApiInfo apiInfo = new ApiInfo("信访接口Api",
                "调度app接口",
                "0.1",
                "汉图",
                "汉图",
                "汉图",
                "http://www.baidu,com"
        );
        return apiInfo;
    }

   /**
    * 
    * @return
    * @description 日常调度
    * @version 1.0
    * @author ZhangHuaRong
    * @update 2017年3月6日 上午10:37:02
    */
    private ApiInfo dschInfo() {
        ApiInfo apiInfo = new ApiInfo("信访接口Api",
                "日常调度接口",
                "0.1",
                "汉图",
                "zhanghr",
                "汉图",
                "http://www.baidu,com"
        );
        return  apiInfo;
    }
    private ApiInfo compApiInfo() {
        ApiInfo apiInfo = new ApiInfo("信访接口Api",
                "投诉办理接口",
                "0.1",
                "汉图",
                "zhanghr",
                "汉图",
                "http://www.baidu,com"
        );
        return apiInfo;
    }

    private ApiInfo personManageApiInfo() {
        ApiInfo apiInfo = new ApiInfo("信访接口Api",
                "机构人员管理接口",
                "0.1",
                "汉图",
                "zhanghr",
                "汉图",
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
