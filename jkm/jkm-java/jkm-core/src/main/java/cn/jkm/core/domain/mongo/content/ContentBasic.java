package cn.jkm.core.domain.mongo.content;

import cn.jkm.core.domain.mongo.MongoBasic;

/**
 * Created by zhong on 2017/7/18.
 * 基础表
 */
public class ContentBasic<T> extends MongoBasic<ContentBasic>{

    private String title;					//内容标题
    private String channelId;				//内容所属频道栏目id
    private Integer commentNum = 0;			//评论数
    private Integer browseNum = 0;				//浏览数
    private Integer pointNum = 0;				//点赞数
    private Integer collectionNum = 0;		//收藏数
    private Integer complaintCount = 0;		//投诉次数
    private Integer complaintNoHandleCount = 0;//待处理投诉次数
    private String picUrl;					//图片地址
    private String publishUserId;			//内容发布人,如果是
    private String publicUserName;          //发布用户姓名
    private String channelName;             //频道民称

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelId() {
        return channelId;
    }

    public T setChannelId(String channelId) {
        this.channelId = channelId;
        return (T)this;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public T setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
        return (T)this;
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public T setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
        return (T)this;
    }

    public Integer getPointNum() {
        return pointNum;
    }

    public T setPointNum(Integer pointNum) {
        this.pointNum = pointNum;
        return (T)this;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public T setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
        return (T)this;
    }

    public Integer getComplaintCount() {
        return complaintCount;
    }

    public T setComplaintCount(Integer complaintCount) {
        this.complaintCount = complaintCount;
        return (T)this;
    }

    public Integer getComplaintNoHandleCount() {
        return complaintNoHandleCount;
    }

    public void setComplaintNoHandleCount(Integer complaintNoHandleCount) {
        this.complaintNoHandleCount = complaintNoHandleCount;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public T setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return (T)this;
    }

    public String getPublicUserName() {
        return publicUserName;
    }

    public T setPublicUserName(String publicUserName) {
        this.publicUserName = publicUserName;
        return (T) this;
    }

    public String getChannelName() {
        return channelName;
    }

    public T setChannelName(String channelName) {
        this.channelName = channelName;
        return (T) this;
    }

	public String getPublishUserId() {
		return publishUserId;
	}

	public void setPublishUserId(String publishUserId) {
		this.publishUserId = publishUserId;
	}
}
