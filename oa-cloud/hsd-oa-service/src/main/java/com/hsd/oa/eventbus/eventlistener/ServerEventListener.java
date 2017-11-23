package com.hsd.oa.eventbus.eventlistener;

import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.hsd.oa.eventbus.AsyncEventBusListener;
import com.hsd.oa.eventbus.event.CouponEvent;
import com.hsd.oa.eventbus.event.SMSEvent;
/**
 * 
 * @description 事件监听总线采用异步的方式
 * @author ZhangHuaRong   
 */
@Component
public class ServerEventListener implements AsyncEventBusListener{
	
	
	@Subscribe
	public void postCoupon(CouponEvent couponEvent){
		couponEvent.domain();
	}
	
	@Subscribe
	public void postSMSEvent(SMSEvent event){
		event.sendmsg();
	}
	

	
}
