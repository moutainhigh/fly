package cn.jkm.test.api.manage.content;

import cn.jkm.test.api.manage.AbstractManageTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/27.
 */
public class ContentComplaintListTest extends AbstractManageTest {
    @Override
    protected String service() {
        return "contentComplaintList";
    }

    @Override
    protected Map params() {
        return new HashMap(){{
            put("keys","帖子");
            put("type","POST");
            put("page",1);
            put("limit",10);
        }};
    }
}
