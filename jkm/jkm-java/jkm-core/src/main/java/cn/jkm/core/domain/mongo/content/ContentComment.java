package cn.jkm.core.domain.mongo.content;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.framework.core.annotation.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * ContentComment entity. @author zhengzb 2017/7/15
 * 评论表
 */
@Document(name = "content_comment")
public class ContentComment extends MongoBasic<ContentComment> {

	// Fields

	private String contentBaseId;	//评论的主题id
	private String content;			//评论内容
	private String refId;			//评论的评论id
	private String userId;			//评论的用户id
	private Integer level;			//评论的级别
	@Enumerated(EnumType.STRING)
	private ContentType type;			//评论的类别,查看 ContentType

	private Integer complaintNoHandleCount;//评论的待处理投诉次数
	
	
	public ContentComment() {
		super();
	}



	public ContentComment(String contentBaseId, String content,
						  String refId, String userId, Integer level) {
		super();
		this.contentBaseId = contentBaseId;
		this.content = content;
		this.refId = refId;
		this.userId = userId;
		this.level = level;
	}


	public String getContentBaseId() {
		return contentBaseId;
	}


	public void setContentBaseId(String contentBaseId) {
		this.contentBaseId = contentBaseId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getRefId() {
		return refId;
	}


	public void setRefId(String refId) {
		this.refId = refId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Integer getLevel() {
		return level;
	}

	public Integer getComplaintNoHandleCount() {
		return complaintNoHandleCount;
	}

	public void setComplaintNoHandleCount(Integer complaintNoHandleCount) {
		this.complaintNoHandleCount = complaintNoHandleCount;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}

	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}
}