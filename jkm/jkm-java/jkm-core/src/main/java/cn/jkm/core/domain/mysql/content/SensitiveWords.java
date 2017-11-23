package cn.jkm.core.domain.mysql.content;

import cn.jkm.core.domain.mysql.MySqlBasic;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by zhong on 2017/7/14.
 * 敏感词设置表
 */
@Entity
@Table(name = "tb_sensitive_words",uniqueConstraints = {@UniqueConstraint(columnNames="name")})
public class SensitiveWords extends MySqlBasic<SensitiveWords> {

	@Column(name = "name",columnDefinition = "varchar(200) COMMENT '，敏感词'")
	private String name;
	@Column(name = "add_user_id", columnDefinition = "varchar(200) COMMENT '添加管理员id'")
	private String addUserId;

	public SensitiveWords() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
}