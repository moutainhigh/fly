package cn.jkm.core.domain.mysql.content;

import cn.jkm.core.domain.mysql.MySqlBasic;

import javax.persistence.*;

/**
 * Created by zhong on 2017/7/14.
 * 频道订阅表
 */
@Entity
@Table(name = "tb_channel_subscribe")
public class ChannelSubscribe extends MySqlBasic<ChannelSubscribe>{


	@Column(name = "user_id", length = 64,columnDefinition = "varchar(64) COMMENT '订阅用户id'")
	private String userId;

	@Column(name = "channel_id", length = 64,columnDefinition = "varchar(64) COMMENT '订阅的频道id'")
	private String channelId;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getUserId() {
		return userId;
	}

	public String getChannelId() {
		return channelId;
	}
}