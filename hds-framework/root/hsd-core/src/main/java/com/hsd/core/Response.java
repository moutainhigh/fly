package com.hsd.core;

import java.io.Serializable;

/**
 * 
     * 姝ょ被鎻忚堪鐨勬槸:
     * @author: zhanghr
     * @version: 1.0 
     * @date:2017骞�8鏈�16鏃� 涓嬪崍1:47:24
 */
public class Response implements Serializable{
	
        
	    
	private static final long serialVersionUID = 1L;


	private int code;


    private Object data;

    private String msg;
    
    private String petitionWay;

    public Response(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

  
	public Response(int code, Object data, String msg, String petitionWay) {
		super();
		this.code = code;
		this.data = data;
		this.msg = msg;
		this.petitionWay = petitionWay;
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPetitionWay() {
		return petitionWay;
	}

	public void setPetitionWay(String petitionWay) {
		this.petitionWay = petitionWay;
	}

    
    

}
