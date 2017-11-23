package com.hsd.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hsd.core.VO.TaskVO;
import com.hsd.portal.content.NewModel;
import com.hsd.portal.feign.OaFeignClient;

import feign.Param;

@RestController
@NewModel
public class BusinessController {

 
  @Autowired
  private OaFeignClient oaFeignClient;

 /* @GetMapping("/movie/{id}")
  public UserEntity findById(@PathVariable Long id) {
    return this.userFeignClient.findById(id);
  }

  @GetMapping("/test")
  public UserEntity testPost(UserEntity user) {
    return this.userFeignClient.postUser(user);
    
  }*/
 
  
  @GetMapping("/getUsers/{id}")
  public String monitor1(@PathVariable("id") Integer id) {
	  System.out.println("id==="+id);
	  return this.oaFeignClient.getUsers(id);
  }; // 
  
  @GetMapping("/getmytasksfeign/{id}")
  public List<TaskVO> getmytasksfeign(@PathVariable("id") String id) {
	  System.out.println("id==="+id);
	  return this.oaFeignClient.getmytasksfeign(id);
  }; // 
  
  
  @RequestMapping(value = "/monitor", method = RequestMethod.GET)
	public ModelAndView  greeting(@RequestParam(name = "id") String id) {
		ModelAndView modelview = new 	ModelAndView("index");
		modelview.addObject("xname", "http://127.0.0.1:8081/V1/process/processmonitoring?deploymentId="+id);
		return modelview;
	}

}
