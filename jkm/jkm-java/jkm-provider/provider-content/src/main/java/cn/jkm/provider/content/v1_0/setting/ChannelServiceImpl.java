package cn.jkm.provider.content.v1_0.setting;

import cn.jkm.core.domain.mysql.content.SettingChannel;
import cn.jkm.core.domain.mysql.content.SettingChannelEx;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mysql.Jdbc;
import cn.jkm.framework.mysql.utils.ClassUtils;
import cn.jkm.service.content.ChannelService;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhong on 2017/7/17.
 */
@Service(version = "1.0")
public class ChannelServiceImpl implements ChannelService {

    @Override
    public List<SettingChannel> findChannelList() {

        List<SettingChannel> channels = Jdbc.build().query(SettingChannel.class)
                .where("level = ? and status = ?", 1,Status.ACTIVE.toString())
                .order("channel_order desc")
                .order("create_at asc").all();
        return channels;
    }

    @Override
    public List<SettingChannel> findColumnList(String channelId) {
        List<SettingChannel> channels = Jdbc.build().query(SettingChannel.class)
                .where("level = ? and parent_id = ? and status = ?", 2, channelId,Status.ACTIVE.toString())
                .order("channel_order desc ")
                .order("create_at asc")
                .all();
        return channels;
    }

    @Override
    public List<SettingChannel> findAllChannelList() {

        List<SettingChannel> channels = Jdbc.build().query(SettingChannel.class)
                .where("level = ?", 1)
                .order("channel_order desc ")
                .order("create_at asc")
                .all();
        return channels;
    }

    @Override
    public List<SettingChannel> findAllColumnList(String channelId) {

        List<SettingChannel> channels = Jdbc.build().query(SettingChannel.class)
                .where("level = ? and parent_id = ?", 2, channelId)
                .order("channel_order desc ")
                .order("create_at asc")
                .all();
        return channels;
    }

    @Override
    public SettingChannel findChannelById(String channelId) {
        SettingChannel channel = Jdbc.build().query(SettingChannel.class)
                .where("id = ?", channelId)
                .first();
        return channel;
    }

    @Override
    public List<SettingChannel> findAllActiveChannelAndColumn() {

        List<SettingChannel> channels = Jdbc.build().query(SettingChannel.class)
                .where("status = ?", Status.ACTIVE.toString())
                .order("channel_order desc ")
                .order("create_at asc")
                .all();
        return channels;
    }

    @Override
    public List<SettingChannel> findAllChannelAndColumn() {

        List<SettingChannel> channels = Jdbc.build().query(SettingChannel.class)
                .order("channel_order desc ")
                .order("create_at asc")
                .all();
        return channels;
    }

    /**
     * @param channels
     * 每次保存所有的操作，包括，更改，新增，合并频道，合并栏目
     *
     * 更改，新增使用实体可以记录
     *
     * 合并频道（需要合并的频道id，被合并的栏目id）
     *
     * 最后，更改所有（帖子，专家文章）
     */
    @Override
    public void saveOrUpdate(List<SettingChannelEx> channels) {

        channels.forEach(v -> {
            if(v.getId()==null||"".equals(v.getId())){
                v.setId(v.uuid());
                Jdbc.build().insert(v);
                List<SettingChannelEx> conlums = v.getList();
                for (int j = 0; j < conlums.size(); j++) {
                    SettingChannelEx conlum = conlums.get(j);
                    if(conlum.getId()==null||"".equals(conlum.getId())){
                        conlum.setId(conlum.uuid());
                        conlum.setParentId(v.getId());
                        Jdbc.build().insert(conlum);
                    }else{
                        Jdbc.build().update(SettingChannel.class).where("id = ?",conlum.getId()).set(parseChannelToMap(conlum));
                    }
                }
            }else{
                Jdbc.build().update(SettingChannel.class).where("id = ?",v.getId()).set(parseChannelToMap(v));
            }
        });
    }

    public void mergeChannel(String fromId,String toId){
        SettingChannel channel = findChannelById(fromId);
        if(channel.getLevel()==1){//表示频道合并到栏目
            List<SettingChannel> channels = findColumnList(fromId);
            channel.setStatus(Status.DELETE);
            Jdbc.build().update(SettingChannel.class)
                    .where("id = ?",channel.getId())
                    .set("status",Status.DELETE.toString());
            channels.forEach(v->{
                v.setStatus(Status.DELETE);
                Jdbc.build().update(SettingChannel.class)
                        .where("id = ?",v.getId())
                        .set("status",Status.DELETE.toString());
                // TODO: 2017/7/21 v栏目下面的文章修改为toId下面的文章

            });
        }else if(channel.getLevel()==2){//表示栏目合并到栏目
            channel.setStatus(Status.DELETE);
            Jdbc.build().update(SettingChannel.class)
                    .where("id = ?",channel.getId())
                    .set("status",Status.DELETE.toString());
            // TODO: 2017/7/21 channel栏目下面的文章修改为toId下面的文章
        }
    }

    public Map parseChannelToMap(SettingChannelEx channelEx){
        HashMap map = new HashMap();
        map.put("level",channelEx.getLevel());
        map.put("name",channelEx.getName());
        map.put("channel_order",channelEx.getChannelOrder());
        map.put("pic",channelEx.getPic());
        map.put("parent_id",channelEx.getParentId());
        map.put("user_id",channelEx.getUserId());
        return map;
    }

    @Override
    public List<SettingChannel> getChannelAndColumnById(String columnId) {

        List<SettingChannel> channels = new ArrayList<>();
        SettingChannel column = Jdbc.build().query(SettingChannel.class)
                .where("id = ?",columnId).first();
        SettingChannel channel =  null;
        if(column!=null){
            channel = Jdbc.build().query(SettingChannel.class)
                    .where("id = ?",column.getParentId()).first();
        }
        channels.add(channel);
        channels.add(column);
        return channels;
    }
}
