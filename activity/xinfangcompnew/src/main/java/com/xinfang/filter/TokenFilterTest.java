package com.xinfang.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
@WebFilter(filterName="myFilter",urlPatterns="/test/*")
public class TokenFilterTest implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		   String msg = null;
		   boolean flag=true;
		   String token = request.getParameter("token");
		   // String token = request.getParameter("userid");
		   // 从redis取 
		  // 签名认证
		   //.......
		   //....
		 
		    HttpServletResponse httpResponse = (HttpServletResponse) response;  
	        httpResponse.setCharacterEncoding("UTF-8");    
	        httpResponse.setContentType("application/json; charset=utf-8");   
	        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
	        ObjectMapper mapper = new ObjectMapper();  
	        
	        if(StringUtils.isBlank(token)){
	        	msg="请登录";
	        	flag=false;
	        }
	          
	        if(flag){
	        	  chain.doFilter(request, response);  
                  return; 
	        }
	        httpResponse.getWriter().write(mapper.writeValueAsString(msg));  
	        return;  
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
