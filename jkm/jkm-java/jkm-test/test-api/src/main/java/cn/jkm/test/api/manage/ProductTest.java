package cn.jkm.test.api.manage;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description:
 * @Date: 13:54 2017/7/24
 */
public class ProductTest extends AbstractManageTest  {
    @Override
    protected String service() {
        return "branchUpdate";
    }

    @Override
    protected Map params() {
//        return new HashMap(){{
//            put("json", "{'name':'jkm', 'sortOrder':'1'}");
//        }};

        return new HashMap(){{
            put("json", "{'id':'5975ae268f34c37fe7c8877f','name':'klm', 'sortOrder':'3'}");
        }};
    }
}
