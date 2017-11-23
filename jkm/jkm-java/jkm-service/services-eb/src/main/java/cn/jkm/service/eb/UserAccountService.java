package cn.jkm.service.eb;

import java.util.Map;

import cn.jkm.core.domain.mysql.useraccount.UserAccount;
import cn.jkm.core.domain.type.PayChannel;

/**
 * 
 * @title UserAccountService.java
 * @package cn.jkm.serevice.eb
 * @description 用户账户操作
 * @author ZhangHuaRong   
 * @update 2017年7月19日 上午10:10:41
 */
public interface UserAccountService {
    /**
     * 
     * @param userId
     * @description 获取用户账户
     * @author ZhangHuaRong
     * @update 2017年7月27日 下午1:48:36
     */
    UserAccount find(String userId);
    /**
     * 
     * @param userId
     * @description 初始化用户账户
     * @author ZhangHuaRong
     * @update 2017年7月27日 下午1:49:39
     */
    UserAccount init(String userId);
    /**
     * 
     * @param userId
     * @param state
     * @description 账户状态设置
     * @author ZhangHuaRong
     */
    UserAccount updateSate(String userId,String state);
    
    /**
     * 
     * @param userId  用户id
     * @param amount  金额
     * @param orderId 订单id
     * @param subject 备注
     * @description 账户现金消费
     * @author ZhangHuaRong
     * @update 2017年7月27日 下午2:22:01
     */
    int rechargeCash(String userId,long amount,String orderId,String subject);
    /**
     * 
     * @param userId  用户id
     * @param amount  金额
     * @param orderId 订单id
     * @param subject 备注
     * @description 账户会员卡消费
     * @author ZhangHuaRong
     * @update 2017年7月27日 下午1:53:57
     */
    int rechargeVipCard(String userId,long amount,String orderId,String subject);
    /**
     * 
     * @param userId  用户id
     * @param amount  金额
     * @param orderId 订单id
     * @param subject 备注
     * @description 消费奖励佣金
     * @author ZhangHuaRong
     * @update 2017年7月27日 下午1:57:55
     */
    int rechargeRewardAmount(String userId,long amount,String orderId,String subject);
    /**
     * 
     * @param userId
     * @param amount
     * @description 消费奖励积分
     * @author ZhangHuaRong
     * @update 2017年7月27日 下午1:58:18
     */
    int rechargeRewardIntegral(String userId,int amount);
    /**
     * 
     * @param userId
     * @param amount
     * @description 消费消费积分
     * @author ZhangHuaRong
     * @update 2017年7月27日 下午1:58:57
     */
    int rechargeConsumeIntegral(String userId,int amount);
    /**
     * 
     * @param userId
     * @param amount
     * @description 消费消费积分
     * @author ZhangHuaRong
     * @update 2017年7月27日 下午1:59:44
     */
    int rechargeCoupon(String userId,int amount);

    /**
     * 
     * @param subject     备注
     * @param totalAmount 金额
     * @param outTradeNo 交易号
     * @param userId 用户id
     * @description 充值接口
     * @author ZhangHuaRong
     * @update 2017年7月27日 下午2:00:15
     */
	Object payment(String subject, String totalAmount, String outTradeNo, String userId);

	/**
	 * 
	 * @param map
	 * @description 充值成功后的回调接口
	 * @author ZhangHuaRong
	 * @update 2017年7月27日 下午2:00:50
	 */
	void paymentNotify(Map map);
    
    
	
    
    
    
    
}
