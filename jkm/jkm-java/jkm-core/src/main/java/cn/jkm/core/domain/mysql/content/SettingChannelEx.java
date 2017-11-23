package cn.jkm.core.domain.mysql.content;

import cn.jkm.core.domain.mysql.MySqlBasic;

import javax.persistence.Column;
import java.util.List;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.core.domain.mysql.content
 * @description //栏目频道扩展表
 * @update 2017/7/20 16:25
 */
public class SettingChannelEx extends MySqlBasic{

    private Short level;
    private String name;
    private Short channelOrder;
    private String pic;
    private String parentId;
    private String userId;

    private List<SettingChannelEx> list;

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getChannelOrder() {
        return channelOrder;
    }

    public void setChannelOrder(Short channelOrder) {
        this.channelOrder = channelOrder;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<SettingChannelEx> getList() {
        return list;
    }

    public void setList(List<SettingChannelEx> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "SettingChannelEx{" +
                "level=" + level +
                ", name='" + name + '\'' +
                ", channelOrder=" + channelOrder +
                ", pic='" + pic + '\'' +
                ", parentId='" + parentId + '\'' +
                ", userId='" + userId + '\'' +
                ", list=" + list +
                '}';
    }
}
