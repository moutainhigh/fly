package cn.jkm.service.eb.address;

import cn.jkm.core.domain.mysql.order.Order;
import cn.jkm.core.domain.mongo.order.UserAddress;

import java.util.List;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.serevice.eb.address
 * @description 收货地址service.
 * @create 2017-07-20 14:17
 * @update 2017/7/20 14:17
 */
public interface AddressService {

    /**
     *  新增个人的收货地址
     */
    public void addPersonalAddress(UserAddress address);

    /**
     *  新增公司的收货地址.
     */
    public void addCompanyAddress(UserAddress address);


    /**
     *  查询个人的收货地址.
     */
    public List<UserAddress> queryPersonalAddress(String userId,String type,int limit,int page);

    /**
     *  查询公司的收货地址.
     */
    public List<UserAddress> queryCompanyAddress(String userId,String type,int limit,int page);

    /**
     * 修改个人收货地址
     * @param address;
     */
    public void updatePersonalAddress(UserAddress address);

    /**
     * 删除个人收货地址.
     * @param addressId;
     */
    public boolean deletePersonalAddress(String addressId);
}
