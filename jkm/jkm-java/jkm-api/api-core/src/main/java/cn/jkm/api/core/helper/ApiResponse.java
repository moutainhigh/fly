package cn.jkm.api.core.helper;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.util.HashMap;

/**
 * Created by werewolf on 2017/7/15.
 */
public class ApiResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static ValueFilter filter = (obj, s, v) -> {
        if (v == null || "null".equals(v)) {
            return "";
        }
        return v;
    };

    public ApiResponse() {
        put("status", HttpStatus.SUCCESS.getCode());
        put("message", HttpStatus.SUCCESS.getMessage());
    }

    public static ApiResponse success() {
        return new ApiResponse();
    }

    public static ApiResponse success(String message) {
        ApiResponse r = new ApiResponse();
        r.put("message", message);
        return r;
    }


    public static ApiResponse fail(Integer status, String message) {
        ApiResponse r = new ApiResponse();
        r.put("status", status);
        r.put("message", message);
        return r;
    }

    public static ApiResponse fail(String message) {
        ApiResponse r = new ApiResponse();
        r.put("status", HttpStatus.INTERNAL_SERVER_ERROR.getCode());
        r.put("message", message);
        return r;
    }

    public static ApiResponse fail() {
        ApiResponse r = new ApiResponse();
        r.put("status", HttpStatus.INTERNAL_SERVER_ERROR.getCode());
        r.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getMessage());
        return r;
    }

    public ApiResponse body(Object data) {
        put("body", data);
        return this;
    }

    public static void main(String[] args) {

        System.out.println(JSON.toJSON(ApiResponse.success().body("12221")));
    }
}
