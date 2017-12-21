package cn.jkm.eb.service.dao;

import cn.jkm.eb.facade.entities.OrderPay;
import cn.jkm.eb.facade.entities.OrderPayKey;

public interface OrderPayMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_orderpay
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    int deleteByPrimaryKey(OrderPayKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_orderpay
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    int insert(OrderPay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_orderpay
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    int insertSelective(OrderPay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_orderpay
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    OrderPay selectByPrimaryKey(OrderPayKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_orderpay
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    int updateByPrimaryKeySelective(OrderPay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_orderpay
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    int updateByPrimaryKey(OrderPay record);
}