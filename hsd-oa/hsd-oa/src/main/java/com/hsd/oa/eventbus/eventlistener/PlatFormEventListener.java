package com.hsd.oa.eventbus.eventlistener;

import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.hsd.oa.eventbus.EventBusListener;
import com.hsd.oa.eventbus.event.CouponEvent;
/**
 * 
 * @description 事件监听总线采用同步的方式
 * @author ZhangHuaRong   
 */
@Component
public class PlatFormEventListener implements EventBusListener{
	
	
	@Subscribe
	public void postCoupon(CouponEvent couponEvent){
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("========"+couponEvent.getName());
	}
	

	
}
