package cn.jkm.common.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReturnMessage {
	public static final int success=0;
	public static final int error1=1;/** 服务器异常错误，保留编码 */
	public static final int error3_token=3;/** token错误 */
	public static final int error100_token = 1;
	
	
	public ReturnMessage() {
		this.setStatus(error1);
		this.setMessage(null);
		this.setData(null);
	}
	
	public ReturnMessage(int status) {
		this.setStatus(status);
	}
	
	public ReturnMessage(int status,String message) {
		this.setStatus(status);
		this.setMessage(message);
	}
	
	public ReturnMessage(int status,Object data) {
		this.setStatus(status);
		this.setData(data);
		this.setData(data);
	}
	
	public ReturnMessage(int status,String message,Object data) {
		this.setStatus(status);
		this.setMessage(message);
	}
	
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	private int status;
	private String message;
	private Object data;
	
	
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

	@Override
	public String toString(){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return super.toString();
	}
}
