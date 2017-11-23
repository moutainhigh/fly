package cn.jkm.test.api.manage.content;

import cn.jkm.test.api.manage.AbstractManageTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/7/20.
 */
public class ContentPostCreateTest extends AbstractManageTest {
    @Override
    protected String service() {
        return "contentPostCreate";
    }

    @Override
    protected Map params() {
        return new HashMap(){{
            put("title","新闻2");
            put("channelId","asdsdfsdfdete");
            put("contentStatus","TOP");
            put("publishUserId","asdsdfsdgdfgedge");
            put("detail","这是新闻1的详细内容<pf></pf>,<pd></pd>");
        }};
    }
}
