package cn.jkm.test.api.utils;

/**
 * Created by Administrator on 2017/7/19.
 */
public class HttpResponse {
    private int code;
    private String body;

    public int getCode() {
        return code;
    }

    public HttpResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getBody() {
        return body;
    }

    public HttpResponse setBody(String body) {
        this.body = body;
        return this;
    }
}
