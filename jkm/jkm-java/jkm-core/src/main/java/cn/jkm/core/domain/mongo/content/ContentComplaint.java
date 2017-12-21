package cn.jkm.core.domain.mongo.content;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.ComplaintType;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.framework.core.annotation.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * ContentComplaint entity. @author zhengzb 2017/7/15
 * 投诉表
 */
@Document(name = "content_complaint")
public class ContentComplaint extends MongoBasic<ContentComplaint> {

	// Fields

	private String contentBaseId;		//投诉的主题id
	private String content;				//投诉内容
	private String userId;				//投诉用户
	private String handleUserId;		//处理用户
	private String commentId;			//投诉的评论id
	private Long dealTime;				//投诉的处理时间
	@Enumerated(EnumType.STRING)
	private ComplaintType complaintStatus;    //投诉的处理状态 1:正常；2:忽略；3：打回；4：处理；5：删除 查看ComplaintType
	private String replyPostUser;		//回复发贴人内容
	private String replyComplaintUser;	//回复投诉人内容
	@Enumerated(EnumType.STRING)
	private ContentType type;			//投诉的类别,查看 ContentType



	
	public ContentComplaint() {
		super();
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


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getHandleUserId() {
		return handleUserId;
	}


	public void setHandleUserId(String handleUserId) {
		this.handleUserId = handleUserId;
	}


	public String getCommentId() {
		return commentId;
	}


	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}


	public Long getDealTime() {
		return dealTime;
	}


	public void setDealTime(Long dealTime) {
		this.dealTime = dealTime;
	}


	public String getReplyPostUser() {
		return replyPostUser;
	}


	public void setReplyPostUser(String replyPostUser) {
		this.replyPostUser = replyPostUser;
	}


	public String getReplyComplaintUser() {
		return replyComplaintUser;
	}


	public void setReplyComplaintUser(String replyComplaintUser) {
		this.replyComplaintUser = replyComplaintUser;
	}

	public ComplaintType getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(ComplaintType complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ContentComplaint{" +
				"contentBaseId='" + contentBaseId + '\'' +
				", content='" + content + '\'' +
				", userId='" + userId + '\'' +
				", handleUserId='" + handleUserId + '\'' +
				", commentId='" + commentId + '\'' +
				", dealTime=" + dealTime +
				", complaintStatus=" + complaintStatus +
				", replyPostUser='" + replyPostUser + '\'' +
				", replyComplaintUser='" + replyComplaintUser + '\'' +
				", type=" + type +
				'}';
	}
}