package cn.jkm.core.domain.mysql.content;

import cn.jkm.core.domain.mysql.MySqlBasic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zhong on 2017/7/14.
 * 系统设置表
 */
@Entity
@Table(name = "tb_setting_system", catalog = "jkm")
public class SettingSystem extends MySqlBasic<SettingSystem> {

	@Column(name = "setting_key", length = 64,columnDefinition = "varchar(64) COMMENT '键'")
	private String settingKey;
	@Column(name = "key_name", length = 128,columnDefinition = "varchar(128) COMMENT '键的名称'")
	private String keyName;
	@Column(name = "key_value", length = 1024,columnDefinition = "varchar(1024) COMMENT '键对应的值'")
	private String keyValue;

	public String getSettingKey() {
		return settingKey;
	}

	public void setSettingKey(String settingKey) {
		this.settingKey = settingKey;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	@Override
	public String toString() {
		return "SettingSystem{" +
				"settingKey='" + settingKey + '\'' +
				", keyName='" + keyName + '\'' +
				", keyValue='" + keyValue + '\'' +
				'}';
	}
}