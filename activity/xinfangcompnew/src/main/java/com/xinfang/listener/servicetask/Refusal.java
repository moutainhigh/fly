package com.xinfang.listener.servicetask;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
/**
 * 
 * @title Refusal.java
 * @package com.xinfang.listener.servicetask
 * @description 审批不通过
 * @author ZhangHuaRong   
 * @update 2017年3月25日 下午9:56:12
 * @version V1.0  
 * Copyright (c)2012 chantsoft-版权所有
 */
@Component("refusal")
public class Refusal implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
