package cn.jkm.test.api.manage.content;

import cn.jkm.test.api.manage.AbstractManageTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/25.
 *
 */
public class ContentCommentListTest extends AbstractManageTest{

    @Override
    protected String service() {
        return "contentCommentList";
    }

    @Override
    protected Map params() {
        return new HashMap(){{
            put("type","POST");
            put("id","5975c2112d2436ba64b7e6a9");
            put("page",1);
            put("limit",10);
        }};
    }
}
