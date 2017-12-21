package cn.jkm.test.api.manage.order;

import cn.jkm.core.domain.type.OrderState;
import cn.jkm.test.api.manage.AbstractManageTest;

import java.util.HashMap;
import java.util.Map;

public class OrderListTest extends AbstractManageTest {


    @Override
    protected String service() {
        return "companyAddressList";
    }

    @Override
    protected Map params() {
        return new HashMap(){{
//            put("type","POST");
            //put("limit","5975c2112d2436ba64b7e6a9");
           /* put("orderId", "5cad6ff4-ca93-4b31-b25b-e591cbb0f2c4");
            put("page",1);
            put("limit",10);*/
            /*put("orderId", "5cad6ff4-ca93-4b31-b25b-e591cbb0f2c4");
            put("page",1);
            put("limit",10);*/
            /*put("userId", "5cad6ff4-ca93-4b31-b25b-e591cbb0f2c4");
            put("page",1);
            put("limit",10);*/
            put("userId", "9bcacfb7188348cdb0c0003f33563466");
            put("type","COMPANY");
            put("page",1);
            put("limit",10);
            //put("mobile","028-25698745");
            //put("userName","找死");
            //put("addDetail","时间劳动局法拉盛大姐夫辣椒水两地分居阿拉山口江东父老贾老师的法拉解散了地方垃圾水电费啊贾老师的");
            //put("userName", "李四");
            //put("mobile","13589874587");
            //put("addDetail","四川省成都市錦江區城市中心二栋3905");
            //put("addressId","5cad6ff4-ca93-4b31-b25b-e591cbb0f2c4");
        }};
    }
}
