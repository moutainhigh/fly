package com.xinfang.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @description 移动端接口日志
 * @author ZhangHuaRong   
 */
public class ApiLog {
	private static String ss = ApiLog.class.getName();
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
		ApiLog.chargeLog("12345", "2324234E", "100.0");
	}
}
