package com.zhangyingwei.cockroach.http.exception;

import com.zhangyingwei.cockroach.utils.CockroachUtils;

/**
 * Created by zhangyw on 2017/8/17.
 * 重定向
 */
public class Http302Exception extends Http30XException  {
    private static final int CODE = 302;
    public Http302Exception() {
    }

    public Http302Exception(String message) {
        super(CockroachUtils.exceptionMessage(CODE,message));
    }

    public Http302Exception(String message, Throwable cause) {
        super(CockroachUtils.exceptionMessage(CODE,message), cause);
    }

    public Http302Exception(Throwable cause) {
        super(cause);
    }

    public Http302Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(CockroachUtils.exceptionMessage(CODE,message), cause, enableSuppression, writableStackTrace);
    }
}
