package com.xinfang.context;

/**
 * @author ZhangHuaRong
 * @title Response.java
 * @package com.xinfang.context
 * @description TODO
 * @update 2016年12月20日 下午8:29:58
 */
public class Response {

    private boolean success;

    private int code;

    private String message;

    public Response(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Response setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }
}
