package com.xinfang.context;

import java.io.Serializable;

/**
 * Created by sunbjx on 2017/3/22.
 */
public class Respon<T> implements Serializable{


    private int code;

    private String msg;

    private T results;
    
    

	public Respon() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	public Respon(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}






	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	
	public String getMsg() {
		return msg;
	}






	public void setMsg(String msg) {
		this.msg = msg;
	}






	public T getResults() {
		return results;
	}

	public void setResults(T results) {
		this.results = results;
	}

    
   
}
