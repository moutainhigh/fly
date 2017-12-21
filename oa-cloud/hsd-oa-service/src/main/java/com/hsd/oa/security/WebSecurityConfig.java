package com.hsd.oa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
/**
 * 
     * 此类描述的是: 待完善
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:42:12
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.authorizeRequests()
	//	.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/V1/**").permitAll()
		.antMatchers("/swagger-ui.html").permitAll()
        .anyRequest().authenticated()
     //   .and().formLogin().loginPage("/login")
        //设置默认登录成功跳转页面
     //   .defaultSuccessUrl("/index").failureUrl("/login?error").permitAll()
        .and()
        //开启cookie保存用户数据
        .rememberMe()
        //设置cookie有效期
        .tokenValiditySeconds(60 * 60 * 24 * 7)
        //设置cookie的私钥
        .key("")
        .and()
        .logout()
        //默认注销行为为logout，可以通过下面的方式来修改
      //  .logoutUrl("/custom-logout")
        //设置注销成功后跳转页面，默认是跳转到登录页面
      //  .logoutSuccessUrl("")
        .permitAll();*/
		
		http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll().antMatchers("/druid/**").permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		//auth.userDetailsService(userDetailsService);
	}
	
	/*@Bean
	DschedLoginService initService(){
		return new DschedLoginServiceImpl();
	}
*/
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	
	
	

}
