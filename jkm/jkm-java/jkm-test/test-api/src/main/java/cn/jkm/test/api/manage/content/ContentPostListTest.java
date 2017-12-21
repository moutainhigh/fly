package cn.jkm.test.api.manage.content;

import cn.jkm.test.api.manage.AbstractManageTest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhengzb on 2017/7/24.
 * 搜索帖子列表
 */
public class ContentPostListTest extends AbstractManageTest {
    @Override
    protected String service() {
        return "contentPostList";
    }

    @Override
    protected Map params() {
        return new HashMap(){{
            put("postType","POST");
            put("keys","帖子");
            put("limit",10);
            put("page",1);
//            put("channelId","asdsdfsdfdete");
//            put("contentStatus","TOP");
//            put("publishUserId","asdsdfsdgdfgedge");
//            put("detail","这是新闻1的详细内容<pf></pf>,<pd></pd>");
        }};
    }
}
