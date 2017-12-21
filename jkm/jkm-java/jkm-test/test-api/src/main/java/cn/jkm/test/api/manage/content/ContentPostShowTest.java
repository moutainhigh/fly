package cn.jkm.test.api.manage.content;

import cn.jkm.test.api.manage.AbstractManageTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/26.
 */
public class ContentPostShowTest extends AbstractManageTest {
    @Override
    protected String service() {
        return "contentPostShow";
    }

    @Override
    protected Map params() {
        //SHOW("显示"),TOP("置顶"),HIDDEN("隐藏")
        return new HashMap(){{
            put("id","5975c2112d2436ba64b7e6ae");
            put("status","SHOW");
        }};
    }
}
