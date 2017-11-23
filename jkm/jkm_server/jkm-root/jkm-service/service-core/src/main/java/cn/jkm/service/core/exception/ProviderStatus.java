package cn.jkm.service.core.exception;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by werewolf on 2017/4/5.
 */
public class ProviderStatus {

    //显示给用户异常
    public static final Integer USER_PASSWORD_REEOR = 301;


    //需要处理异常
    public static final Integer USER_NOT_EXISTS = 404;


    static Map<Integer, String> messages = new HashMap() {{
        put(USER_PASSWORD_REEOR, "");
        put(USER_NOT_EXISTS, "");
    }};


}
