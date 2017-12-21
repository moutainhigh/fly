package com.hsd.oa.service.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
/**
 * 
     * 此类描述的是:测试
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:44:02
 */
@Component("qingjia")
public class Qingjia implements TaskListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask arg0) {
		System.out.println("请假通过准许休假。。。。。");
		
	}

}
