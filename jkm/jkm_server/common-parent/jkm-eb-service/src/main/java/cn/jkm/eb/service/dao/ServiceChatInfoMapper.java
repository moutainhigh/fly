package cn.jkm.eb.service.dao;

import cn.jkm.eb.facade.entities.ServiceChatInfo;

public interface ServiceChatInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_servicechatinfo
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    int deleteByPrimaryKey(String chatid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_servicechatinfo
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    int insert(ServiceChatInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_servicechatinfo
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    int insertSelective(ServiceChatInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_servicechatinfo
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    ServiceChatInfo selectByPrimaryKey(String chatid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_servicechatinfo
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    int updateByPrimaryKeySelective(ServiceChatInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_servicechatinfo
     *
     * @mbg.generated Wed Jul 12 17:48:15 CST 2017
     */
    int updateByPrimaryKey(ServiceChatInfo record);
}