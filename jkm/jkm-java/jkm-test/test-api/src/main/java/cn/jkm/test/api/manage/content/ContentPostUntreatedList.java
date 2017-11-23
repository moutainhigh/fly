package cn.jkm.test.api.manage.content;

import cn.jkm.test.api.manage.AbstractManageTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/26.
 */
public class ContentPostUntreatedList extends AbstractManageTest {
    @Override
    protected String service() {
        return "contentPostUntreated";
    }

    @Override
    protected Map params() {
        return new HashMap(){{
            put("id","5975c2112d2436ba64b7e6ad");
        }};
    }
}
