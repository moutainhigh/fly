package com.hsd.oa.eventbus;

import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
/**
 * 
     * 此类描述的是:
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:44:59
 */
public class EventBusFactory {
	
	private static AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));
	
	private final static EventBus eventBus = new EventBus();
	
	public static AsyncEventBus getAsyncEventBus(){
		return asyncEventBus;
	}

	public static EventBus getEventBus(){
		return eventBus;
	}

}
