package com.xinfang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xinfang.service.CaseHandleService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v1/caselog")
@Api(description = "投诉办理操作日志记录(张华荣)")
public class CaseHandleController {
	@Autowired
	CaseHandleService caseHandleService;
	
	

}
