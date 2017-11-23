package cn.jkm.test.api.manage;

import java.util.HashMap;
import java.util.Map;

public class PaymentNotifyTest extends AbstractManageTest {

	@Override
	protected String service() {
		// TODO Auto-generated method stub
		return "paymentNotify";
	}

	@Override
	protected Map params() {
		return new HashMap(){{
            put("total_amount", "88");
            put("buyer_id", "2088102116773037");
            put("body", "测试");
            put("trade_no", "2016071921001003030200089909");
            put("refund_fee", "0");
            put("sign", "kPbQIjX+xQc8F0/A6/AocELIjhhZnGbcBN6G4MM/HmfWL4ZiHM6fWl5NQhzXJusaklZ1LFuMo+lHQUELAYeugH8LYFvxnNajOvZhuxNFbN2LhF0l/KL8ANtj8oyPM4NN7Qft2kWJTDJUpQOzCzNnV9hDxh5AaT9FPqRS6ZKxnzM=");
            put("version", "1.0");
            put("notify_id", "------");
            //put........
        }};
	}

}
