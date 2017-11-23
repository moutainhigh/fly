package cn.jkm.test.api.manage;

import java.util.HashMap;
import java.util.Map;

public class GetUserAccountTest extends AbstractManageTest {
	
	

	@Override
	protected String service() {
		// TODO Auto-generated method stub
		return "getUserAccount";
	}

	@Override
	protected Map params() {
		return new HashMap(){{
            put("userId", "1");
        }};
	}

}
