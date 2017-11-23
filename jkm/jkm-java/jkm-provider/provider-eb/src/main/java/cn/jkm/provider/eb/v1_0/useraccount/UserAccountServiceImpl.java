package cn.jkm.provider.eb.v1_0.useraccount;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;

import cn.jkm.core.domain.mysql.useraccount.PaymentOrder;
import cn.jkm.core.domain.mysql.useraccount.UserAccount;
import cn.jkm.core.domain.type.PaymentOrderState;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mysql.Jdbc;
import cn.jkm.service.core.exception.ProviderException;
import cn.jkm.service.eb.UserAccountService;
/**
 * 
 * @title UserAccountServiceImpl.java
 * @package cn.jkm.provider.eb.v1_0.useraccount
 * @description TODO
 * @author ZhangHuaRong   
 * @update 2017年7月19日 下午2:11:35
 */
@Service(version = "1.0")
public class UserAccountServiceImpl implements UserAccountService {
	private final static Logger LOG = LoggerFactory.getLogger("ed-provider");
	@Override
	public UserAccount find(String userId) {
		UserAccount useraccount = Jdbc.build().query(UserAccount.class).where("user_id=?", userId).first();
		return useraccount;
	}

	@Override
	public UserAccount init(String userId) {
		UserAccount account = null;
		account = find(userId);
		if(account!=null){
			return account;
		}
		Date now = new Date();
		account = new UserAccount();
		account.setStatus(Status.ACTIVE);
		account.setUserId(userId);
		account.setId(UUID.randomUUID().toString());
		account.setCreateAt(now.getTime());
		account.setUpdateAt(now.getTime());
		Jdbc.build().insert(account);
	    return account;
	}

	@Override
	public UserAccount updateSate(String userId, String state) {
		
		if(state.equalsIgnoreCase("ACTIVE")){
	        Jdbc.build().update(UserAccount.class).where("user_id = ?",userId).set("status", Status.ACTIVE.name());
		}else  if(state.equalsIgnoreCase("DELETE")){
	        Jdbc.build().update(UserAccount.class).where("user_id = ?",userId).set("status", Status.DELETE.name());
		}
		UserAccount userAccount= find(userId);
		return userAccount;
	}

	


	@Override
	public int rechargeRewardIntegral(String userId, int amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int rechargeConsumeIntegral(String userId, int amount) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public void paymentNotify(Map map){
		//TODO 更改PaymentOrder 状态 生成充值记录（流水）
		 Iterator it= map.entrySet().iterator();
		 LOG.info("========={}",map);
		  while (it.hasNext()) {
			   Map.Entry entry = (Entry) it.next();
			   LOG.info("key= " + entry.getKey() + " and value= " + entry.getValue());
			  }
		
	}
	
	
	//沙箱环境测试 https://docs.open.alipay.com/54/106370
	//app支付 https://docs.open.alipay.com/204
	@Override
	@Transient
	public Object payment(String subject,String totalAmount,String outTradeNo,String userId){
		
		String appPriveKey ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDRMpm8AUzV2v2gdpT4jSFG3e6AZN5FgzP3RTVondb/rJXQ4/4TB9BaQRLRcE9geLlnWJSIVUN9lDDsjs3clN+PR5ImIg25XFpl7c80f1mnYy/s0mybQANiXOyYMmH3bpHv22oTQOM/AzMQvTTaZIjePhJaiXCO4jg5Af4a+qnNFZvO2M7TTpQ8aQHR5TzcqmzfUHWIQEtqHo4KW+aa72nwac7E7dESlpX6I6XlNJwkwCT9GqUCDMOhlV4WHNvWkuFW27v5in1xUpm1HEvHEi7eY53qpwsL1i4ozM2IcwqS9D650JVRGdkASEH/ych3UgUy2F9QjtaWK/gVKuNhujG3AgMBAAECggEAIlFZtOTFtQNivfMWBxwnzFdV68wgRmj6mmLJGythfLnULdto77hFbrjkHGV/MAU97P00CJVsJ+qqh/P5Q2lMr1V/XTAS7kh/L99uMV9+1p3H2+K9HE7I/1bdHS6Osad62000PopTfR/KPcG8LON3LHYkTqPjSh1EOd3g0I40UscfjOtiQW3bkl1f8gF9TvOk18GYjzNoC70gJ7ymqVhq8Ybuf8wwnlRMvQqsHlqZdyfjm/7bXT1TbcTplPEF5rKZoiamNldd3QiX/vmjktPOHH7hWuIJbxMe/aS/b+LAqF7AfkhjDjwiAJQT8JPUwyc8q/TixNodJ2BUtUD7OxMKiQKBgQD50gPk7utXDLeiVm790XmlzeH3TIC42WAr87eQyRNHzF8XDP/f00HmLbcNubFOsHRnxRwMiBcL3XVrmB9fM0n1tvMoUPjzq7o3pqN3LIJT5TLHCwmGCPGkseKFjbOfQfl4A7hcI+zTU4EqULUuJzj1q22QOROHubXphhcfHYM4gwKBgQDWX1eo4/rrdYix9QKuL7gIfkO+VuT/qtZXHVBXfJaSiJP3x0uhtC1M7I9tWxhwlRmY4n/QN6bKMbLDFiH+d1ECLicxaVWlkw5MTYi/WkLfnAoZ6L/rhKdKp53Lwyqngm1ELK4QA9WOVlztbSCen4irolXEErjce1JQPdbF9K1TvQKBgQCHWlZpz1kLDQThm9r/AmPahbYMexKzFHOa3K10cL1VhVFv98cayidGQzaO5TE/5TLslksgLwvtjct0xDMbLXrA+qK8EUMRItX+GPcwNplWQzRDy6MZT/c8uHR2z9OmUeSMNdfJdF14EiJFfvzrSKz/ziXKomIGg4QgRlp4rVDPaQKBgB2HBmkB9fbmuoxghhz9/6hT0/rstyAsJNfc9ssGIytN2qu/bMB6M98rmlniuUwQjXUMhbRlZfjlvYy3iLknMCZPuyBINP7YcZi6+UH6KcnpEU8eSwkivXRAADEn0cE+6tQAH32fOZQgey5M4LhYmxehBZU7SvE7n0vkKKgdmp41AoGBAMda8hhbnHcAsehPwTJeNI8pNqnJZIG7WweymAXDATC0q5mPUwHqqGPVP1zmDc9sj5xishE5572/kKhdG8RpwYAsX70YkWrLD+aOKo/um7D+tjeg8norZXCESNUjoJrRkHceLXj4cspzaBHKogfc0DLx9gAqA+kbhc6nYe8UCh28";
		String alipayPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAojLcBLRxeCrfzaEW89Jcr9ZhzR06DwqpJGsE3kttO7t0BduoBbDaVhS6BOp0WYb6/mtNNz6ZTNK5aqA7b6XDGusi9vXSUsrBekchKXhdW0qNEOtbsPTwFUpPlgkF+PwJ2XbsTC4nByZjM4BpVCiQT3QYP6MkliabeVCauhvI7WvRqAL1VexvtiAk+hOXIsqxPFtw0kkIW4crHYhPRTNSyikEB3E7FYPSzL3Kg0z300W+Je0XaUXzD2vqJ5hkwHhAiHPH4/5HgOf/NInDdd4CAYXFQggbUDWelBxqogC0qFgFz6om9aTQuv6XjhfU97sdBixCDKsmm8sLGMvFbljVeQIDAQAB";
		String appID="2016080500170617";
		String CHARSET="utf-8";
		String notifyUrl="http://118.89.201.196:9092/api-klm-manage/manage/paymentNotify";
		
		//TODO  PaymentOrder 生成支付订单 状态是待支付
		Date time = new Date();
		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setCreateAt(time.getTime());
		paymentOrder.setId(paymentOrder.uuid());
	//	paymentOrder.setOrderId(orderId);
		
		paymentOrder.setOrderState(PaymentOrderState.PENDING);
		paymentOrder.setOutTradeNo(outTradeNo);
		paymentOrder.setStatus(paymentOrder.getStatus().ACTIVE);
		paymentOrder.setSubject(subject);
		paymentOrder.setUpdateAt(time.getTime());
		paymentOrder.setUserId(userId);
		int result = Jdbc.build().insert(paymentOrder);
		if(result ==0){
			throw ProviderException.DB_INSERT_RESULT_0;
		}
		
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", appID, appPriveKey, "json", CHARSET, alipayPublicKey, "RSA2"); 

		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
		request.setBizModel(model);
		model.setOutTradeNo(outTradeNo);
		model.setTotalAmount(totalAmount);
		model.setSubject(subject);
		model.setTimeoutExpress("30m");
		model.setBody("充值");
		request.setNotifyUrl(notifyUrl);
		//预下单接收接口
		AlipayTradePrecreateResponse response;
		try {
			response = alipayClient.sdkExecute(request);
			LOG.info("body:"+response.getBody());
			LOG.info("qrcode:"+response.getQrCode());
		} catch (AlipayApiException e) {
			LOG.info("获取支付宝支付凭证出错：{} ,outTradeNo:{},userid:{}",e.getMessage(),outTradeNo,userId);
			throw new ProviderException(5000010, e.getMessage());
		}
		return response;
		
	}

	@Override
	public int rechargeCoupon(String userId, int amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int rechargeCash(String userId, long amount, String orderId, String subject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int rechargeVipCard(String userId, long amount, String orderId, String subject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int rechargeRewardAmount(String userId, long amount, String orderId, String subject) {
		// TODO Auto-generated method stub
		return 0;
	}

}
