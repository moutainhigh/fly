package com.xinfang.context;

/**
 * Created by sunbjx on 2017/3/22.
 */
public class Responses {

    private boolean success;

    private int code;

    private String message;

    private Object results;

    public Responses(boolean success, int code,  String message, Object results) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.results = results;
    }

    public boolean isSuccess() {
        return success;
    }

    public Responses setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Responses setCode(int code) {
        this.code = code;
        return this;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public Responses setMessage(String message) {
        this.message = message;
        return this;
    }
}
