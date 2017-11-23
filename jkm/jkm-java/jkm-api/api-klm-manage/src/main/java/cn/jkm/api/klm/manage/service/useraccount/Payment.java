package cn.jkm.api.klm.manage.service.useraccount;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.UserAccountService;
@Component("payment1.0")
@NotNull(name = {"userId"}, message = "请输入用户id")
public class Payment extends AbstractManageService{


	 @Reference(version = "1.0")
	 UserAccountService userAccountService;
	
	@Override
	public Map service(Map request) {
		String userId= formatString(request.get("userId"));
		String subject= formatString(request.get("subject"));
		String totalAmount= formatString(request.get("totalAmount"));
		String outTradeNo= getOrderIdByUUId();
		Object obj = userAccountService.payment(subject, totalAmount, outTradeNo, userId);
		return ApiResponse.success().body(obj);
	}
	
	 private  String getOrderIdByUUId() {
         int machineId = 1;//最大支持1-9个集群机器部署
         int hashCodeV = UUID.randomUUID().toString().hashCode();
         if(hashCodeV < 0) {//有可能是负数
             hashCodeV = - hashCodeV;
         }
         // 0 代表前面补充0     
         // 4 代表长度为4     
         // d 代表参数为正数型
         return machineId + String.format("%015d", hashCodeV);
     }

}
