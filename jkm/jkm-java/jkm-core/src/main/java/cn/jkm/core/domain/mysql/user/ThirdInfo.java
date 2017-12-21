package cn.jkm.core.domain.mysql.user;

import cn.jkm.core.domain.mysql.MySqlBasic;
import javax.persistence.*;

/**
 * Created by Machine on 2017/7/17.
 * 第三方平台账号
 */
@Entity
@Table(name = "tb_third_info", catalog = "jkm")
public class ThirdInfo extends MySqlBasic<ThirdInfo> {

	public enum ThirdInfoType {
		WECHAT, QQ, WEIBO; //1.wechat 2.qq 3.weibo
	}

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private ThirdInfoType type;

	@Column(name = "open_id")
	private String openId;

	public ThirdInfoType getType() {
		return type;
	}

	public void setType(ThirdInfoType type) {
		this.type = type;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
