package cn.jkm.core.domain.mysql.content;

import cn.jkm.core.domain.mysql.MySqlBasic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by zhong on 2017/7/14.
 * 频道栏目表
 */
@Entity
@Table(name = "tb_setting_channel", catalog = "jkm")
public class SettingChannel extends MySqlBasic<SettingChannel> {

	@Column(name = "level",columnDefinition = "int COMMENT '级别'")
	private Short level;
	@Column(name = "name", length = 32,columnDefinition = "varchar(32) COMMENT '名称'")
	private String name;
	@Column(name = "channel_order",columnDefinition = "int COMMENT '顺序'")
	private Short channelOrder;
	@Column(name = "pic", length = 128,columnDefinition = "varchar(128) COMMENT '图片'")
	private String pic;
	@Column(name = "parent_id", length = 64,columnDefinition = "varchar(64) COMMENT '上级id'")
	private String parentId;
	@Column(name = "user_id", length = 64,columnDefinition = "varchar(64) COMMENT '添加管理员id'")
	private String userId;


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

	@Override
	public String toString() {
		return "SettingChannel{" +
				"level=" + level +
				", name='" + name + '\'' +
				", channelOrder=" + channelOrder +
				", pic='" + pic + '\'' +
				", parentId='" + parentId + '\'' +
				", userId='" + userId + '\'' +
				'}';
	}
}