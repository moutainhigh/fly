package cn.jkm.service.content;

import cn.jkm.core.domain.mysql.content.SettingChannel;
import cn.jkm.core.domain.mysql.content.SettingChannelEx;

import java.util.List;

/**
 * Created by zhong on 2017/7/17.
 */
public interface ChannelService {

    /**
     * 获取频道列表
     */
    public List<SettingChannel> findChannelList();

    /**
     * 根据频道id获取栏目列表
     */
    public List<SettingChannel> findColumnList(String channelId);

    /**
     * 获取所有频道列表
     */
    public List<SettingChannel> findAllChannelList();

    /**
     * 根据频道id获取所有栏目列表
     */
    public List<SettingChannel> findAllColumnList(String channelId);

    /**
     * 根据频道id获取频道的信息
     */
    public SettingChannel findChannelById(String channelId);

    /**
     * 获取所有有效的频道以及栏目
     */
    public List<SettingChannel> findAllActiveChannelAndColumn();

    /**
     * 获取所有的频道以及栏目
     */
    public List<SettingChannel> findAllChannelAndColumn();

    /**
     * 更新或者新增频道
     */
    public void saveOrUpdate(List<SettingChannelEx> channels);

    /**
     * 根据栏目id，获取栏目和频道
     * @param columnId
     * @return
     */
    public List<SettingChannel> getChannelAndColumnById(String columnId);
}
