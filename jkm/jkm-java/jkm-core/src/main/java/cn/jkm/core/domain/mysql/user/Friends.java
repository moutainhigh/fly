package cn.jkm.core.domain.mysql.user;

import cn.jkm.core.domain.mysql.MySqlBasic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Machine on 2017/7/17.
 * 好友
 */
@Entity
@Table(name = "tb_friends")
public class Friends extends MySqlBasic<Friends> {

	@Column(name = "send_user_id")
	private long sendUserId;

	@Column(name = "received_user_id")
	private long receivedUserId;

	@Column(name = "send_at")
	private long sendAt;

	public long getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(long sendUserId) {
		this.sendUserId = sendUserId;
	}

	public long getReceivedUserId() {
		return receivedUserId;
	}

	public void setReceivedUserId(long receivedUserId) {
		this.receivedUserId = receivedUserId;
	}

	public long getSendAt() {
		return sendAt;
	}

	public void setSendAt(long sendAt) {
		this.sendAt = sendAt;
	}

}
