package com.hsd.oa.service.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
     * 此类描述的是: 分模块记录日志 用于以后的统计分析
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:43:06
 */
public class ApiLog {
	private static Logger logger = LoggerFactory.getLogger(ApiLog.class.getName() + ".ApiLog");
	
	public static void chargeLog(String orderNo,String created,String amount){
		StringBuffer bf = new StringBuffer();
		bf.append(orderNo).append("|").append(created.toString()).append("|").append(amount.toString());
		logger.info(bf.toString());
	}
	
	public static void chargeLog1(String omsg){
		StringBuffer bf = new StringBuffer();
		bf.append(omsg);
		logger.info(bf.toString());
	}
	
	public static void log(String mag){
		
		logger.info(mag);
	}

	
	
	public static void main(String[] args) {
		ApiLog.chargeLog("12345", "2324234E", "10099999.0");
	}
}
