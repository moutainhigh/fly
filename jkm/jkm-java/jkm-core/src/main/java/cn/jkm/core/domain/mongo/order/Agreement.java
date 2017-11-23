package cn.jkm.core.domain.mongo.order;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.framework.core.annotation.Document;

import javax.persistence.*;

/**
 * @author xeh
 * @create 2017-07-17 8:59
 * @desc 协议实体类
 **/
@Document(name = "agreement")
public class Agreement   extends MongoBasic<Agreement> {

    private String content;

    private String title;

	@Column(name = "content", columnDefinition = "协议内容")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "title", columnDefinition = "协议标题")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}