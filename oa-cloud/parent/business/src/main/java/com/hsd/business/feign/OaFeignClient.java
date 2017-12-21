package com.hsd.business.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hsd.core.Response;


@FeignClient(name="oa")
@RequestMapping("/V1/Tleaf")
public interface OaFeignClient {
  
  
  
 // @RequestLine("GET /monitor")
   @RequestMapping(value = "/monitor", method = RequestMethod.GET)
  public Response monitor(@RequestParam("id") String id); // 
   
   @RequestMapping(value = "/getUsersforfeign", method = RequestMethod.GET)
   public String getUsers(@RequestParam("id") Integer id); // 

  
   
  
}
