package cn.jkm.test.api.app;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/19.
 */
public class LoginTest extends AbstractAppTest{
    @Override
    protected String service() {
        return "test";
    }

    @Override
    protected Map params() {
        return new HashMap(){{
            put("accountName","123");
            put("password","asdf");
        }};
    }

    @Override
    protected Boolean decode() {
        return true;
    }

    @Override
    protected Boolean encode() {
        return true;
    }
}
