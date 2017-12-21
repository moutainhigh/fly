package com.xinfang.eventbus.event;

import java.io.Serializable;
import com.xinfang.utils.HttpSender;
/**
 * 
 * @title SMSEvent.java
 * @package com.xinfang.eventbus.event
 * @description 反馈如果脱控发送短信
 * @author ZhangHuaRong   
 * @update 2017年3月2日 上午10:59:51
 */
public class SMSEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String phones;//
	
	private String msg;//


	public SMSEvent(String phones, String msg) {
		super();
		this.phones = phones;
		this.msg = msg;
	}




	public String getPhones() {
		return phones;
	}




	public void setPhones(String phones) {
		this.phones = phones;
	}




	public String getMsg() {
		return msg;
	}




	public void setMsg(String msg) {
		this.msg = msg;
	}




	public void sendmsg(){
		try {
			HttpSender.send(phones,msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
