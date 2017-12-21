package com.xinfang.listener.servicetask;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * 
 * @title DealyRefusal.java
 * @package com.xinfang.listener.servicetask
 * @description 延期申请不通过
 * @author ZhangHuaRong   
 * @update 2017年3月25日 下午10:04:26
 * @version V1.0  
 * Copyright (c)2012 chantsoft-版权所有
 */
@Component("dealyRefusal")
public class DealyRefusal implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
