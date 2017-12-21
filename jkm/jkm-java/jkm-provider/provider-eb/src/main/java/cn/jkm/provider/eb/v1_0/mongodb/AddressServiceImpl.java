package cn.jkm.provider.eb.v1_0.mongodb;/**
 * Created by admin on 2017/7/24.
 */

import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.core.domain.mongo.order.UserAddress;
import cn.jkm.core.domain.mongo.product.MarketingRule;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.IMongoUpdate;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.core.exception.ProviderException;
import cn.jkm.service.eb.address.AddressService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collections;
import java.util.List;

/**
 * @author xeh
 * @version V1.0
 * @project jkm-java
 * @package cn.jkm.provider.eb.v1_0.mongodb
 * @description &#x6536;&#x8d27;&#x5730;&#x5740;&#x63a5;&#x53e3;&#x5b9e;&#x73b0;&#x7c7b;
 * @create 2017-07-24 10:33
 * @update 2017/7/24 10:33
 */
@Service(version = "1.0")
public class AddressServiceImpl implements AddressService {

    /**
     * 插入个人收获地址.
     * @param address
     */
    @Override
    public void addPersonalAddress(UserAddress address) {
        Mongo.buildMongo().insert(address);
    }


    /**
     * 插入公司收获地址.
     * @param address
     */
    @Override
    public void addCompanyAddress(UserAddress address) {
        Mongo.buildMongo().insert(address);
    }


    @Override
    public List<UserAddress> queryCompanyAddress(String userId, String type, int limit, int page) {
        List<UserAddress> userAddressesList =  Mongo.buildMongo().eq("addressType",type).limit(limit, page).find(UserAddress.class);
        if(userAddressesList.isEmpty()){
            throw new ProviderException("没有查询到记录哟！");
        }else{
            return userAddressesList;
        }
    }

    /**
     * 修改收获地址.
     *
     * @param address
     */
    @Override
    public void updatePersonalAddress(UserAddress address) {
        Mongo.buildMongo().eq("_id", address.getId()).updateFirst(update -> {
            // 修改字段.TODO
            // update.set("status", Status.DELETE);
            update.set("mobile", address.getMobile());
            update.set("area", address.getArea());
            update.set("road", address.getRoad());
            update.set("addDetail", address.getAddDetail());
            update.set("userName", address.getUserName());
            update.set("updateAt",address.getUpdateAt());
        }, UserAddress.class);
    }

    /**
     * 删除收货地址.
     *
     * @param addressId
     */
    @Override
    public boolean deletePersonalAddress(String addressId) {
        Mongo.buildMongo().eq("_id", addressId).updateFirst(update -> {
            // 修改字段.TODO
            update.set("status", Status.DELETE);
        }, UserAddress.class);
        return true;
    }

    @Override
    public List<UserAddress> queryPersonalAddress(String userId,String type,int limit,int page) {
       List<UserAddress> userAddressesList =  Mongo.buildMongo().eq("userId",userId).eq("addressType",type).limit(limit, page).find(UserAddress.class);
        if(userAddressesList.isEmpty()){
            throw new ProviderException("没有查询到记录哟！");
        }else{
            return userAddressesList;
        }
    }
}
