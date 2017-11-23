package com.helome.messagecenter.http;

/**
 * @author ZhangHuaRong
 * @title Response.java
 * @package com.xinfang.context
 * @description TODO
 * @update 2016年12月20日 下午8:29:58
 */
public class Response {

   private int code ;
   
   private Object date;
   
   private String message;
   
   

public Response(int code, Object date, String message) {
	super();
	this.code = code;
	this.date = date;
	this.message = message;
}

public int getCode() {
	return code;
}

public void setCode(int code) {
	this.code = code;
}

public Object getDate() {
	return date;
}

public void setDate(Object date) {
	this.date = date;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
   
   
    

   
}
