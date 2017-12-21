package com.hsd.portal.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hsd.core.VO.TaskVO;






@FeignClient(name="oa"/*,fallback = HystrixClientFallback.class*/)
@RequestMapping("/V1/Tleaf")
public interface OaFeignClient {
  
  
  
 
   
   @RequestMapping(value = "/getUsersforfeign", method = RequestMethod.GET)
   public String getUsers(@RequestParam("id") Integer id); // 
   
   @RequestMapping(value = "/getmytasksfeign", method = RequestMethod.GET)
   public  List<TaskVO> getmytasksfeign(@RequestParam("userid") String userid); // 

  
   
  
}
