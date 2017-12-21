package com.xinfang.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @description 移动端接口日志
 * @author ZhangHuaRong   
 */
public class SuperLog {
	private static Logger logger1 = LoggerFactory.getLogger(SuperLog.class.getName() + ".chargeLog");
	private static Logger logger2 = LoggerFactory.getLogger(SuperLog.class.getName() + ".chargeLog1");
	
	public static void chargeLog(String orderNo,String created,String amount){
		StringBuffer bf = new StringBuffer();
		bf.append(orderNo).append("|").append(created.toString()).append("|").append(amount.toString());
		logger1.info(bf.toString());
	}
	
	public static void chargeLog1(String omsg){
		StringBuffer bf = new StringBuffer();
		bf.append(omsg);
		logger2.info(bf.toString());
	}
	
	public static void log(String mag){
		
		logger2.info(mag);
	}

	
	
	public static void main(String[] args) {
		SuperLog.chargeLog("12345", "2324234E", "100.0");
	}
}
