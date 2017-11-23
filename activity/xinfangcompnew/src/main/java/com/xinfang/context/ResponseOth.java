package com.xinfang.context;

/**
 * 
 * @title Response.java
 * @package com.xinfang.context
 * @description TODO
 * @author ZhangHuaRong   
 * @update 2016年12月20日 下午8:29:58
 */
public class ResponseOth {
    private int code;


    private Object data;

    private String msg;
    
    private String petitionWay;

    public ResponseOth(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

  
	public ResponseOth(int code, Object data, String msg, String petitionWay) {
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
