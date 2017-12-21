package cn.jkm.core.domain.mongo.content;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.framework.core.annotation.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * ContentStore entity. @author zhengzb 2017/7/15
 * 收藏表
 */
@Document(name = "content_store")
public class ContentStore extends MongoBasic<ContentStore> {

	private String contentBaseId;	//收藏的主题id
	private String userId;			//收藏的用户id
	private Long time;				//收藏时间
	@Enumerated(EnumType.STRING)
	private ContentType type;			//收藏的类别,查看 ContentType
	
	
	public ContentStore() {
		super();
	}


	public ContentStore(String contentBaseId, String userId, Long time,
                        Short storeStatus) {
		super();
		this.contentBaseId = contentBaseId;
		this.userId = userId;
		this.time = time;
	}


	public String getContentBaseId() {
		return contentBaseId;
	}


	public void setContentBaseId(String contentBaseId) {
		this.contentBaseId = contentBaseId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Long getTime() {
		return time;
	}


	public void setTime(Long time) {
		this.time = time;
	}


	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}
}