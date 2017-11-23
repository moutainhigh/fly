package cn.jkm.core.domain.mongo.content;

import java.sql.Timestamp;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.framework.core.annotation.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * ContentPoint entity. @author zhengzb 2017/7/15
 * 点赞表
 */
@Document(name = "content_point")
public class ContentPoint extends MongoBasic<ContentPoint> {

	// Fields

	private String contentBaseId;	//点赞主题id
	private Long time;	//点赞时间
	private String userId;		//点赞用户id
	@Enumerated(EnumType.STRING)
	private ContentType type;			//点赞的类别,查看 ContentType
	
	
	public ContentPoint() {
		super();
	}


	public ContentPoint(String contentBaseId, Long time, String userId) {
		super();
		this.contentBaseId = contentBaseId;
		this.time = time;
		this.userId = userId;
	}


	public String getContentBaseId() {
		return contentBaseId;
	}


	public void setContentBaseId(String contentBaseId) {
		this.contentBaseId = contentBaseId;
	}


	public Long getTime() {
		return time;
	}


	public void setTime(Long time) {
		this.time = time;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}
}