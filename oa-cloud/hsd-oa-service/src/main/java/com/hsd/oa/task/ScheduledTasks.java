package com.hsd.oa.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hsd.oa.eventbus.event.CouponEvent;
import com.hsd.oa.eventbus.eventlistener.ServerEventListener;

/**
 * 
     * 此类描述的是:
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:41:58
 */
@Component
@EnableScheduling
public class ScheduledTasks{
	@Autowired
	ServerEventListener serverEventListener;


    //每1分钟执行一次  晚上10点半以后 早上7点之前不扫描
//	@Scheduled(fixedRate = 1000 * 60)
    public void reportCurrentByCron() {
    	System.out.println("----------定时更新缓存---------------"+new Date().toLocaleString());
    	/*CouponEvent couponEvent = new CouponEvent("admin",10);
    	serverEventListener.postCoupon(couponEvent);*/
    }


  
}
