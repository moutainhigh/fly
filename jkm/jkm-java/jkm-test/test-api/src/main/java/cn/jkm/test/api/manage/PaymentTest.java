package cn.jkm.test.api.manage;

import java.util.HashMap;
import java.util.Map;

public class PaymentTest extends AbstractManageTest {

	@Override
	protected String service() {
		return "payment";
	}

	@Override
	protected Map params() {
		return new HashMap(){{
            put("userId", "1");
            put("subject", "充值测试");
            put("totalAmount", "1000");
        }};
	}

}
