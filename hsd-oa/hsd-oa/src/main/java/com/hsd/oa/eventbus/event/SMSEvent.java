package com.hsd.oa.eventbus.event;

import java.io.Serializable;
import java.util.List;

import com.hsd.oa.utils.HttpSender;
/**
 * 
     * 此类描述的是: 测试
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017年8月16日 下午1:46:03
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
